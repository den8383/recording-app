package com.example.recording

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button) as Button
        button.setOnClickListener{
            Toast.makeText(this, "clicked!", Toast.LENGTH_SHORT).show()
        }
        
    }
}
