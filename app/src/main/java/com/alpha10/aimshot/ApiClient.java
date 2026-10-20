// File: ApiClient.java
package com.alpha10.aimshot;

import android.os.Handler;
import android.os.Looper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiClient {

    public interface ApiCallback {
        void onSuccess(String response);
        void onError(String error);
    }

    // সার্ভারে লাইসেন্স ও HWID পাঠানোর মেথড
    public static void verifyLicenseOnline(String serverUrl, String licenseKey, String hwid, ApiCallback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(serverUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);

                String postData = "license_key=" + licenseKey + "&hwid=" + hwid;
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(response.toString()));
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onError("Server Error: " + responseCode));
                }
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError("Network Error: " + e.getMessage()));
            }
        }).start();
    }
}
