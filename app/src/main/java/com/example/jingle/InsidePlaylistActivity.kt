package com.example.jingle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class InsidePlaylistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inside_playlist)


        val name=intent.getStringExtra("playlistName")

    }
}