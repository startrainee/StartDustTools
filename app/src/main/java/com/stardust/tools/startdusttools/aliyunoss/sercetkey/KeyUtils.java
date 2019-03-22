package com.stardust.tools.startdusttools.aliyunoss.sercetkey;

import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;

/**
 * Created on 2018/10/16.
 *
 * @author siasun-wangchongyang
 */
public class KeyUtils {

    public static OSSCustomSignerCredentialProvider getOSSCustomSignerCredentialProvider() {


        return new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                // 您需要在这里依照OSS规定的签名算法，实现加签一串字符内容，并把得到的签名传拼接上AccessKeyId后返回
                // 一般实现是，将字符内容post到您的业务服务器，然后返回签名
                // 如果因为某种原因加签失败，描述error信息后，返回nil

                // 以下是用本地算法进行的演示
                //return "OSS " + BuildConfig.ACCESSID + ":" + base64(hmac-sha1(BuildConfig.ACCESSKEY, content));
                return "";
            }
        };
    }
}
