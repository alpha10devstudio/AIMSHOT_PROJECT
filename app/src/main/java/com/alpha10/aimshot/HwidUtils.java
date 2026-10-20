// File: HwidUtils.java
package com.alpha10.aimshot;

import android.content.Context;
import android.provider.Settings;
import android.os.Build;
import java.security.MessageDigest;

public class HwidUtils {

    // ডিভাইসের ইউনিক HWID জেনারেট করার মেথড
    public static String getDeviceHWID(Context context) {
        try {
            String androidId = Settings.Secure.getString(
                context.getContentResolver(), 
                Settings.Secure.ANDROID_ID
            );
            
            String board = Build.BOARD;
            String brand = Build.BRAND;
            String device = Build.DEVICE;
            String hardware = Build.HARDWARE;
            String product = Build.PRODUCT;

            String combinedData = androidId + board + brand + device + hardware + product;
            return hashToSHA256(combinedData);
            
        } catch (Exception e) {
            e.printStackTrace();
            return "UNKNOWN_HWID";
        }
    }

    // SHA-256 হ্যাশ জেনারেটর হেল্পার
    private static String hashToSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
