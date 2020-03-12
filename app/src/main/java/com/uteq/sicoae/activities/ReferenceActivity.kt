package com.uteq.sicoae.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.uteq.sicoae.R
import com.uteq.sicoae.util.Constans
import com.uteq.sicoae.util.Notification
import java.io.File
import java.io.FileOutputStream


class ReferenceActivity : AppCompatActivity(), View.OnClickListener{

    var llShare: LinearLayout? = null
    var llCapture: LinearLayout? = null
    var etReference: EditText? = null
    var notification: Notification? = null
    var bitmap: Bitmap? = null
    var imageFile: File? = null
    var share = false
    var screenshot = false

    companion object {
        const val STORAGE_PERMISSION_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reference)

        llShare = findViewById(R.id.ll_share)
        llCapture = findViewById(R.id.ll_capture)
        etReference = findViewById(R.id.et_reference)

        notification = Notification(this)

        val intent = intent
        if(intent != null){
            etReference?.setText(intent.getStringExtra("referencia"))
        }

        llShare?.setOnClickListener(this)
        llCapture?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ll_share -> {
                screenshot = false
                share = true
                checkPermission()
            }
            R.id.ll_capture -> {
                share = false
                screenshot = true
                checkPermission()
            }
        }
    }

    fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            takeScreenshot()
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.required_permission))
                .setMessage(resources.getString(R.string.required_permission_text))
                .setPositiveButton(resources.getString(R.string.ok), { dialog, which ->
                    ActivityCompat.requestPermissions(this@ReferenceActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                })
                .setNegativeButton(resources.getString(R.string.cancel), {dialog, which ->
                    dialog.dismiss()
                })
                .create().show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takeScreenshot()
            }else{
                Toast.makeText(this, resources.getString(R.string.denied_permission), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun takeScreenshot(){
        var reference = "referencia_${etReference?.text}"

        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        path.mkdir()

        val v1 = window.decorView.rootView
        v1.isDrawingCacheEnabled = true
        bitmap = Bitmap.createBitmap(v1.drawingCache)
        v1.isDrawingCacheEnabled = false

        imageFile = File(path, "${reference}.jpg")

        val outputStream = FileOutputStream(imageFile)
        val quality = 100
        bitmap?.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        outputStream.flush()
        outputStream.close()

        MediaScannerConnection.scanFile(this, arrayOf(imageFile?.absolutePath), null, { path: String?, uri: Uri? ->
            if(share){
                var intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.reference))
                intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.access_reference))
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                startActivity(Intent.createChooser(intent, resources.getString(R.string.share)))
            }else if(screenshot){
                notification?.createNotification(
                    resources.getString(R.string.screenshot),
                    resources.getString(R.string.description_screenshot),
                    Constans.CHANNEL_ID_SCREENSHOT,
                    R.drawable.notification_screenshot,
                    resources.getString(R.string.title_notification_screenshot),
                    resources.getString(R.string.content_notification_screenshot))
                notification?.addLargeIconAndStyle(bitmap!!)
                notification?.clickToImage(imageFile!!)
                notification?.showNotification(Constans.NOTIFICATION_ID_SCREENSHOT)
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("section", R.id.nav_reference)
        startActivity(intent)
        finish()
    }
}