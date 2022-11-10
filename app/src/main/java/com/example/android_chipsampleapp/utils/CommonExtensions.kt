package com.example.android_chipsampleapp.utils

import android.view.View
import androidx.core.view.isVisible

fun stateProgressLoading(isloading: Boolean, loadingView: View, mainLayout: View) {
    loadingView.isVisible = isloading
    mainLayout.isVisible = !isloading
}