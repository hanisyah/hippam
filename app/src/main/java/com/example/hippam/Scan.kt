package com.example.hippam

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class Scan : AppCompatActivity(), ZXingScannerView.ResultHandler, View.OnClickListener {
    private lateinit var keluar: View
    private lateinit var btnreset: Button
    private lateinit var text_view_qr_code_value: TextView
    private lateinit var frame: FrameLayout
    private lateinit var mScannerView: ZXingScannerView
    private var isCaptured = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        keluar = findViewById(R.id.back)
        keluar.setOnClickListener {
            onBackPressed()
        }
        initScannerView()
        initDefaultView()
        btnreset = findViewById(R.id.button_reset)
        btnreset.setOnClickListener(this)
    }
    private fun initScannerView() {
        mScannerView = ZXingScannerView(this)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame = findViewById(R.id.frame_layout_camera)
        frame.addView(mScannerView)
    }

    override fun onStart() {
        mScannerView.startCamera()
        doRequestPermission()
        super.onStart()
    }
    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            100 -> {
                initScannerView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    private fun initDefaultView() {
        btnreset = findViewById(R.id.button_reset)
        text_view_qr_code_value.text = "QR Code Value"
        btnreset.visibility = View.GONE
    }

    override fun handleResult(rawResult: Result?) {
        text_view_qr_code_value.text = rawResult?.text
        btnreset.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button_reset -> {
                mScannerView.resumeCameraPreview(this)
                initDefaultView()
            }
            else -> {

            }
        }
    }
}