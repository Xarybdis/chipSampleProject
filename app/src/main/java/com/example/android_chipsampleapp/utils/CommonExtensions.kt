package com.example.android_chipsampleapp.utils

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible

fun stateProgressLoading(isloading: Boolean, loadingView: View, mainLayout: View) {
    loadingView.isVisible = isloading
    mainLayout.isVisible = !isloading
}

fun SearchView.onQueryTextChanged(listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })
}