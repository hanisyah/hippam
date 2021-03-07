package com.example.hippam

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isEmpty
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class Scan : AppCompatActivity() {
    private val requestCodeCameraPermission = 1001
    private lateinit var CameraSurfaceView: SurfaceView
    private lateinit var textScanResult: TextView
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        CameraSurfaceView = findViewById(R.id.CameraSurfaceView)
        textScanResult = findViewById(R.id.textScanResult)
        if (ContextCompat.checkSelfPermission(
                this@Scan,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }
    }


    private fun setupControls() {
        detector = BarcodeDetector.Builder(this@Scan).build()
        cameraSource = CameraSource.Builder(this@Scan, detector)
            .setAutoFocusEnabled(true)
            .build()
        CameraSurfaceView.holder.addCallback(surgaceCallBack)
        detector.setProcessor(processor)
    }

    private fun askForCameraPermission(){
        ActivityCompat.requestPermissions(
            this@Scan,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == requestCodeCameraPermission && grantResults.isEmpty())
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                setupControls()
            } else{
                Toast.makeText(applicationContext, "Permission Danied", Toast.LENGTH_SHORT).show()
            }
    }

    private val surgaceCallBack = object : SurfaceHolder.Callback {

        override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

        }

        override fun surfaceDestroyed(p0: SurfaceHolder) {
            cameraSource.stop()
        }

        @SuppressLint("MissingPermission")
        override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
            try {
                if (ActivityCompat.checkSelfPermission(
                        this@Scan,
                        android.Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                cameraSource.start(surfaceHolder)
            } catch (exception: Exception) {
                Toast.makeText(applicationContext, "Permission Danied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val processor = object  : Detector.Processor<Barcode>{
        override fun release() {

        }

        override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
            if (detections != null && detections.detectedItems.isEmpty()){
                val qrCodes: SparseArray<Barcode> = detections.detectedItems
                val code = qrCodes.valueAt(0)
                textScanResult.text = code.displayValue
            } else{
                textScanResult.text = " "
            }
        }
    }
}