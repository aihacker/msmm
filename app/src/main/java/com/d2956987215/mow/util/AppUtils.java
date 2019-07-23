package com.d2956987215.mow.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

public class AppUtils {

    public static boolean checkHasInstalledApp(Context context, String pkgName) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(pkgName, PackageManager.GET_GIDS);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        } catch (RuntimeException e) {
            app_installed = false;
        }
        return app_installed;
    }

}
