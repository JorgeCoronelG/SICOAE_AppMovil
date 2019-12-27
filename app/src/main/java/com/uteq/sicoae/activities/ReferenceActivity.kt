package com.uteq.sicoae.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.uteq.sicoae.R
import com.uteq.sicoae.util.Constans
import com.uteq.sicoae.util.Notification
import java.io.File
import java.io.FileOutputStream
import java.util.*


class ReferenceActivity : AppCompatActivity(), View.OnClickListener, MediaScannerConnection.OnScanCompletedListener{

    var llShare: LinearLayout? = null
    var llCapture: LinearLayout? = null
    var notification: Notification? = null

    var bitmap: Bitmap? = null
    var imageFile: File? = null

    companion object {
        const val STORAGE_PERMISSION_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reference)

        llShare = findViewById(R.id.ll_share)
        llCapture = findViewById(R.id.ll_capture)

        notification = Notification(this)

        llShare?.setOnClickListener(this)
        llCapture?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ll_share -> {
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            }
            R.id.ll_capture -> {
                checkPermission()
            }
        }
    }

    fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            takeScreenshot(R.id.ll_capture)
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            val positiveButton = AlertDialog.Builder(this)
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
                takeScreenshot(R.id.ll_capture)
            }else{
                Toast.makeText(this, resources.getString(R.string.denied_permission), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun takeScreenshot(item: Int){
        val reference = "referencia_20191125LMF83901"

        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        path.mkdir()

        val v1 = window.decorView.rootView
        v1.isDrawingCacheEnabled = true
        bitmap = Bitmap.createBitmap(v1.drawingCache)
        v1.isDrawingCacheEnabled = false

        imageFile = File(path, "${reference}.png")

        val outputStream = FileOutputStream(imageFile)
        val quality = 100
        bitmap?.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
        outputStream.flush()
        outputStream.close()

        if(item == R.id.ll_capture){
            MediaScannerConnection.scanFile(this, arrayOf(imageFile?.absolutePath), null, this)
        }
    }

    override fun onScanCompleted(path: String?, uri: Uri?) {
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

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("section", R.id.nav_reference)
        startActivity(intent)
        finish()
    }
}