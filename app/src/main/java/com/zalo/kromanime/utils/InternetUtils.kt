package com.zalo.kromanime.utils


/**
Created by zaloaustine in 9/5/23.
 */
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.zalo.kromanime.R

object InternetUtils {

    fun checkAndShowRetrySnackbar(
        fragment: Fragment,
        message: String = "No internet",
        duration: Int = Snackbar.LENGTH_LONG,
        actionText: String = "Retry",
        action: () -> Unit
    ) {
        if (!isInternetAvailable(fragment.requireContext())) {
            showRetrySnackbar(fragment, message, duration, actionText, action)
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    private fun showRetrySnackbar(
        fragment: Fragment,
        message: String,
        duration: Int,
        actionText: String,
        action: () -> Unit
    ) {
        val snackbar = Snackbar.make(
            fragment.requireView(),
            message,
            duration
        )

        snackbar.setAction(actionText) {
            snackbar.dismiss()
            action()
        }

        snackbar.setActionTextColor(ContextCompat.getColor(fragment.requireContext(), R.color.purple_200))
        snackbar.show()
    }
}
