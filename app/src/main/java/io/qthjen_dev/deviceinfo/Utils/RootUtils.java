package io.qthjen_dev.deviceinfo.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by q-thjen on 2/2/18.
 */

public class RootUtils {

    public static boolean isDeviceRooted() {

        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    public static boolean checkRootMethod1() {

        String buildTag = android.os.Build.TAGS;
        return buildTag != null && buildTag.contains("test-keys");
    }

    public static boolean checkRootMethod2() {

        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su"
                , "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su"};

        for ( String path : paths) {
            if ( new File(path).exists()) return true;
        }

        return false;
    }

    public static boolean checkRootMethod3() {

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if ( reader.readLine() != null ) return true;
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if ( process != null ) process.destroy();
        }

    }

}
