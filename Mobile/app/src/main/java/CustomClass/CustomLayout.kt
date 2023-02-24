package CustomClass

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

    //  Fungsi untuk memunculkan Custom Pop Up Message
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

//  Fungsi untuk cek apakah semua editText kosong
    fun isEditTextInputEmpty(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            if (editText.text.toString().trim().isEmpty()) {
                return false
            }
        }
        return true
    }

//  Fungsi untuk Clear TextView
    fun setTextViewNull(vararg textViews: TextView){
        for (textView in textViews){
            textView.text = null
        }
    }

//  Fungsi ini untuk resize imageView yang ada pada bagian atas setiap masing - masing page
//--> Fungsi ini masih uji coba
    fun resizeAndSetImage(imageView: ImageView, imageResourceId: Int) {
        // Mendapatkan ukuran layar
        val screenWidth = imageView.resources.displayMetrics.widthPixels
        val screenHeight = imageView.resources.displayMetrics.heightPixels

        // Mendapatkan bitmap dari sumber gambar
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(imageView.resources, imageResourceId, options)
        val originalWidth = options.outWidth
        val originalHeight = options.outHeight
        val aspectRatio = originalWidth.toFloat() / originalHeight.toFloat()

        // Menentukan ukuran gambar baru dengan mempertahankan rasio aspek gambar asli
        val newWidth: Int
        val newHeight: Int
        if (originalWidth > originalHeight) {
            newWidth = screenWidth
            newHeight = (screenWidth / aspectRatio).toInt()
        } else {
            newWidth = (screenHeight * aspectRatio).toInt()
            newHeight = screenHeight
        }

        // Membuat bitmap baru dengan ukuran yang disesuaikan
        val myImageBitmap = BitmapFactory.decodeResource(imageView.resources, imageResourceId)
        val newImageBitmap = Bitmap.createScaledBitmap(myImageBitmap, newWidth, newHeight, false)

        // Menampilkan gambar pada ImageView
        imageView.setImageBitmap(newImageBitmap)
    }
//-->

}