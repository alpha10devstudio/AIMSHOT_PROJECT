// File: AESCrypto.java
package com.alpha10.aimshot;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypto {

    // গোপন সিক্রেট কি (এটি আপনার প্রজেক্ট অনুযায়ী পরিবর্তন করে নিতে পারেন)
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "1234567890123456"; // Exactly 16 bytes

    // এনক্রিপ্টেড পেলোড ডিক্রিপ্ট করার মেথড
    public static File decryptPayload(Context context, String assetFileName, String outputFileName) {
        try {
            // অ্যাসেট ফোল্ডার থেকে এনক্রিপ্টেড ফাইল রিড করা
            InputStream inputStream = context.getAssets().open(assetFileName);
            int size = inputStream.available();
            byte[] encryptedBytes = new byte[size];
            inputStream.read(encryptedBytes);
            inputStream.close();

            // AES ডিক্রিপশন ইনিশিয়ালাইজেশন
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // ডিক্রিপ্ট করা ফাইলটি ক্যাশ ডিরেক্টরিতে সেভ করা
            File outputFile = new File(context.getCacheDir(), outputFileName);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(decryptedBytes);
            outputStream.close();

            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
