package CustomInterface

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

interface ExitApps {
//  Fungsi akan dijalankan ketika kita klik tombol back pada aplikasi
    fun onBackPressed()
}

fun ExitApps.onBackExitPressed(activity: Activity) {
    val builder = AlertDialog.Builder(activity)
    builder.setMessage("Apakah Anda yakin ingin keluar?")
    builder.setCancelable(true)

    // Jika pengguna menekan tombol "Ya"
    builder.setPositiveButton("Ya") { _, _ ->
        // Menutup activity dan aplikasi
        ActivityCompat.finishAffinity(activity)
    }

    // Jika pengguna menekan tombol "Tidak"
    builder.setNegativeButton("Tidak") { dialog, _ ->
        // Menutup dialog dan kembali ke aplikasi
        dialog.cancel()
    }

    val dialog = builder.create()
    dialog.show()
}