package com.example.e_shop.core.extensions

import android.content.Context
import android.widget.Toast

fun toastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}