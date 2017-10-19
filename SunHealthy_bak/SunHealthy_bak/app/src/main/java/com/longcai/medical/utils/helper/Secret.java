package com.longcai.medical.utils.helper;

/**
 * Created by jingkang on 2017/10/14
 */

public interface Secret<T, Y> {
    T encrypt(String var1);

    Y decrypt(String var1);
}
