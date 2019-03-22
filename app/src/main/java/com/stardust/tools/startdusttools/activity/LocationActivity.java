package com.stardust.tools.startdusttools.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.stardust.tools.startdusttools.R;
import com.stardust.tools.startdusttools.utils.AppUtils;

public class LocationActivity extends AppCompatActivity implements AMapLocationListener {

    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 0x10;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //android 8 适配-通知
    private static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";
    private NotificationManager notificationManager = null;
    boolean isCreateChannel = false;

    TextView mLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mLocation = findViewById(R.id.location);
        initLocationClient();
    }

    private boolean checkPermission(){
        //这里以ACCESS_COARSE_LOCATION为例
        if (checkLocationPermission()) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
            return false;
        }
        return true;
    }

    private void initLocationClient() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);

        //给定位客户端对象设置定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        ///获取一次定位结果：该方法默认为false。
        //mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动
        //定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置
        //其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        mLocationClient.setLocationOption(mLocationOption);


        if(checkPermission()){
            //启动定位
            mLocationClient.startLocation(); //启动后台定位，第一个参数为通知栏ID，建议整个APP使用一个
            mLocationClient.enableBackgroundLocation(2001, buildNotification());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if(requestCode == WRITE_COARSE_LOCATION_REQUEST_CODE){

            if(checkLocationPermission()){
                //启动定位
                mLocationClient.startLocation(); //启动后台定位，第一个参数为通知栏ID，建议整个APP使用一个
                mLocationClient.enableBackgroundLocation(2001, buildNotification());
                return;
            }

            Toast.makeText(this,"定位功能需要定位权限",Toast.LENGTH_SHORT).show();

        }
    }

    private boolean checkLocationPermission() {

        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED;

    }

    private Notification buildNotification() {

        Notification.Builder builder;
        Notification notification;
        if(android.os.Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (null == notificationManager) {
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }
            String channelId = getPackageName();
            if(!isCreateChannel) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
                notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(getApplicationContext(), channelId);
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(AppUtils.getAppName(this))
                .setContentText("正在后台运行")
                .setWhen(System.currentTimeMillis());

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            return builder.build();
        }
        return notification;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        // amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
        // amapLocation.getLatitude();//获取纬度
       //  amapLocation.getLongitude();//获取经度
        // amapLocation.getAccuracy();//获取精度信息
       //  amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
        // amapLocation.getCountry();//国家信息
       //  amapLocation.getProvince();//省信息
       //  amapLocation.getCity();//城市信息
       //  amapLocation.getDistrict();//城区信息
       //  amapLocation.getStreet();//街道信息
        // amapLocation.getStreetNum();//街道门牌号信息
       //  amapLocation.getCityCode();//城市编码
       //  amapLocation.getAdCode();//地区编码
       //  amapLocation.getAoiName();//获取当前定位点的AOI信息
       //  amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
       //  amapLocation.getFloor();//获取当前室内定位的楼层
       //  amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
        //获取定位时间
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Date date = new Date(amapLocation.getTime());
        // df.format(date);

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                Log.e("AmapError","location success, data: " + aMapLocation.toString());
                String locationStr = aMapLocation.toString();
                final double lat = aMapLocation.getLatitude();
                final double lon = aMapLocation.getLongitude();
                mLocation.setText(locationStr.replace("#","\n"));
                mLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LocationActivity.this,MapActivity.class);
                        intent.putExtra("lat",lat);
                        intent.putExtra("lon",lon);
                        startActivity(intent);
                    }
                });
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭后台定位，参数为true时会移除通知栏，为false时不会移除通知栏，但是可以手动移除
        mLocationClient.disableBackgroundLocation(true);
    }
}
