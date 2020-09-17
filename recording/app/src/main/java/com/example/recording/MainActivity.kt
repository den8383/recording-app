package com.example.recording

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.os.Bundle


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import java.io.IOException

import java.io.File


class MainActivity : AppCompatActivity() {

    private val LOG_TAG = "AudioRecordTest"
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    private var fileName: String = ""

    private var recordButton: RecordButton? = null
    private var recorder: MediaRecorder? = null

    private var playButton: PlayButton? = null
    private var player: MediaPlayer? = null

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecording()
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }
        }
    }

    private fun stopPlaying() {
        player?.release()
        player = null
    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    internal inner class RecordButton(ctx: Context) : AppCompatButton(ctx) {

        var mStartRecording = true
        fun clickButton(){

            onRecord(mStartRecording)
            text = when (mStartRecording) {
                true -> "Stop recording"
                false -> "Start recording"
            }
            mStartRecording = !mStartRecording
        }


        init {
            text = "Start recording"
        }
    }

    internal inner class PlayButton(ctx: Context) : AppCompatButton(ctx) {
        var mStartPlaying = true

        fun clickButton(){

            onPlay(mStartPlaying)
            text = when (mStartPlaying) {
                true -> "Stop playing"
                false -> "Start playing"
            }
            mStartPlaying = !mStartPlaying
        }


        init {
            text = "Start playing"
        }
    }

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        // Record to the external cache directory for visibility
        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest1.3gp"

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        recordButton = RecordButton(this)
        playButton = PlayButton(this)

        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.record_button) as Button
        button1.setOnClickListener{
            Toast.makeText(this, recordButton!!.getText(), Toast.LENGTH_SHORT).show()
            recordButton!!.clickButton()
        }


        val button2 = findViewById<Button>(R.id.play_button) as Button
        button2.setOnClickListener{
            Toast.makeText(this, playButton!!.getText(), Toast.LENGTH_SHORT).show()
            playButton!!.clickButton()
        }




        val testButton = findViewById<Button>(R.id.test_button) as Button
        testButton.setOnClickListener{
            val listView = findViewById<ListView>(R.id.list_view)
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, File("${externalCacheDir?.absolutePath}").list())
            listView.setAdapter(adapter)
        }

    }

    override fun onStop() {
        super.onStop()
        recorder?.release()
        recorder = null
        player?.release()
        player = null
    }
}
