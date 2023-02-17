package com.example.mobileapplication

import android.content.Context
import android.graphics.Rect
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class CustomLayout(private val context: Context) {
    //  Fungsi untuk hide/unhide password
    fun passwordToggle(editText: EditText, imageView: ImageView) {
        imageView.setOnClickListener {
            if (editText.transformationMethod == null) {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                imageView.setImageResource(R.drawable.ic_baseline_visibility_off_24)
            } else {
                editText.transformationMethod = null
                imageView.setImageResource(R.drawable.ic_baseline_visibility_24)
            }
        }
    }

    fun showCustomToast(textInput: String, toast_custom: Int) {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val layout: View = inflater.inflate(toast_custom, null)
        val text: TextView = layout.findViewById(R.id.text)
        text.text = textInput
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

    fun isEditTextEmpty(vararg editTexts: EditText): Boolean {
        var isEmpty = false
        for (editText in editTexts) {
            if (editText.text.toString().trim().isEmpty()) {
                isEmpty = true
            }
        }
        showCustomToast("Form Tidak Boleh Kosong", R.layout.toast_custom_layout_failed)
        return isEmpty
    }
}