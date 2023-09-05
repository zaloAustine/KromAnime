package com.zalo.kromanime.utils


/**
Created by zaloaustine in 9/5/23.
 */
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.zalo.kromanime.R
import java.io.IOException

object InternetUtils {

     fun isInternetAvailable(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            // Ping Google's public DNS server (8.8.8.8) with a timeout of 5 seconds.
            val process = runtime.exec("/system/bin/ping -c 1 -w 5 8.8.8.8")
            val exitValue = process.waitFor()

            // If the exit value is 0, then the ping was successful.
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }

}
