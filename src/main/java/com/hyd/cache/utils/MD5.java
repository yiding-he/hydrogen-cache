package com.hyd.cache.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 签名算法
 *
 * @author yiding.he
 */
public class MD5 {

    private MD5() {

    }

    /**
     * 生成字符串的 MD5 签名
     *
     * @param str 要签名的字符串
     *
     * @return 签名
     */
    public static String digest(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(str.getBytes("UTF-8"));
            return Bytes.toString(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
