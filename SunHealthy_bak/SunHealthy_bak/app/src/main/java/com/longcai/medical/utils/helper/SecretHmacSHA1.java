package com.longcai.medical.utils.helper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jingkang on 2017/10/14
 */

public class SecretHmacSHA1 implements Secret<byte[], Void> {
    private SecretKeySpec c;
    private Mac m;

    public SecretHmacSHA1(String secretKey) {
        try {
            this.m = Mac.getInstance("HmacSHA1");
            this.m.init(this.c = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA1"));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public byte[] encrypt(String plaintext) {
        try {
            return this.m.doFinal(plaintext.getBytes("UTF-8"));
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public Void decrypt(String ciphertext) {
        return null;
    }
}
