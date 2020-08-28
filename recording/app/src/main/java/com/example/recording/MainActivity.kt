package com.example.recording

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.os.Bundle
import android.widget.Button


import android.widget.Toast
import android.Manifest
import android.media.MediaRecorder



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button) as Button
        button.setOnClickListener{
            Toast.makeText(this, "clicked!", Toast.LENGTH_SHORT).show()
        }
        var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
        val REQUEST_RECORD_AUDIO_PERMISSION = 200
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        
    }
}
