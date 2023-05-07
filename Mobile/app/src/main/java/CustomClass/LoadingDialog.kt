package CustomClass

import android.app.Activity
import android.app.AlertDialog
import com.example.mobileapplication.R

class LoadingDialog(private var activity: Activity) {
    private var dialog: AlertDialog? = null

    fun startLoadingDialog(){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    fun dismissDialog(){
        dialog?.dismiss()
    }
}