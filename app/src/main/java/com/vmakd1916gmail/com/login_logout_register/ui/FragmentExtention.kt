package com.vmakd1916gmail.com.login_logout_register.ui

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.vmakd1916gmail.com.login_logout_register.repositories.auth.Variables


fun Fragment.snackbar(text: String) {
    Snackbar.make(
        requireView(),
        text,
        Snackbar.LENGTH_SHORT
    ).show()
}

fun Fragment.noInternetAlert() {
    if (!Variables.isNetworkConnected) {
        Snackbar.make(
            requireView(),
            "No Internet Connection",
            Snackbar.LENGTH_SHORT
        ).show()
    }
}