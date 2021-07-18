package com.example.jingle

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.google.gson.Gson

class splashScreen : AppCompatActivity() {
    companion object {
        var listSongs = ArrayList<SongInfo>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            loadSongs()
        }

        else requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)

        setContentView(R.layout.activity_splash_screen)
        val time:Long=2500

        Handler().postDelayed(
        {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },time)
    }

    private fun loadSongs() {
        var uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var selection: String = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        var c = this.contentResolver.query(uri, null, selection, null, null)
        if (c != null) {
            while (c.moveToNext()) {
                var url = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))
                var author = c.getString((c.getColumnIndex(MediaStore.Audio.Media.ARTIST)))
                var title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                var duration=c.getInt(c.getColumnIndex(MediaStore.Audio.Media.DURATION))


                listSongs.add(SongInfo(url, author, title,duration))


            }
        }



    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults[0]==PackageManager.PERMISSION_GRANTED && requestCode==1)
        {
            loadSongs()
        }

    }




}