package com.longcai.medical.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/9/28.
 */

public class FileUtils {
    public static void writeToFile(Context context, String fileName, String content) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(Context context, String fileName) {
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            byte[] buffer = new byte[1024];
            int hasRead = 0;
            StringBuilder builder = new StringBuilder();
            while ((hasRead = inputStream.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, hasRead));
            }
            inputStream.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
