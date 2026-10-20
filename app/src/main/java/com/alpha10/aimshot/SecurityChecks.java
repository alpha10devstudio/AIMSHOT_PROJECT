// File: SecurityChecks.java
package com.alpha10.aimshot;

import android.os.Build;
import java.io.File;

public class SecurityChecks {

    // ডিভাইস রুট করা আছে কি না চেক করার মেথড
    public static boolean isDeviceRooted() {
        return checkRootFiles() || checkSuperuserBinary();
    }

    private static boolean checkRootFiles() {
        String[] paths = {
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/full.su",
            "/bin/su"
        };
        for (String path : paths) {
            if (new File(path).exists()) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkSuperuserBinary() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"which", "su"});
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
        } catch (Exception e) {
            // Ignore
        }
        return false;
    }

    // এমুলেটর ডিটেক্ট করার মেথড
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.toLowerCase().contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.HARDWARE.toLowerCase().contains("goldfish")
                || Build.HARDWARE.toLowerCase().contains("ranchu")
                || Build.PRODUCT.toLowerCase().contains("sdk")
                || Build.PRODUCT.toLowerCase().contains("emulator");
    }
}
