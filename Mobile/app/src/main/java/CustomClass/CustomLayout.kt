package CustomClass

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileapplication.R

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

    fun isEditTextInputEmpty(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            if (editText.text.toString().trim().isEmpty()) {
                return false
            }
        }
        return true
    }

    fun customBackground(image: ImageView, windowManager: WindowManager){
        // Mengatur gambar latar belakang agar sesuai dengan ukuran layar
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        val aspectRatio = screenWidth.toFloat() / screenHeight.toFloat()
        image.layoutParams.width = (screenWidth / aspectRatio).toInt()
    }
}