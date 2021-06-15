package com.example.jingle


import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore
import android.renderscript.Sampler
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.jingle.fragments.home
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration


class song_viewer : AppCompatActivity()  {

        private val listSongs=ArrayList<SongInfo>()

        lateinit var runnable: Runnable
        private var handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_viewer)
       loadSongs()
        val mp= MediaPlayer()
        val playbutton=findViewById<ImageButton>(R.id.play_button)
    var position =intent.getIntExtra("position",0)





        val nextButton=findViewById<ImageButton>(R.id.next_button)
        val previousButton=findViewById<ImageButton>(R.id.previous_button)
        val repeatButton=findViewById<ImageButton>(R.id.repeatButton)
        val songName=findViewById<TextView>(R.id.song_name)
        val author=findViewById<TextView>(R.id.singer_name)
        val seekBar=findViewById<SeekBar>(R.id.SeekBar)
        val backButton=findViewById<ImageView>(R.id.back_button)


        mp.setDataSource(listSongs[position].url)
        songName.text=listSongs[position].title
        author.text=listSongs[position].author
        seekBar.progress=0
        seekBar.max=listSongs[position].duration!!.toInt()
        mp.prepare()
        mp.start()


        backButton.setOnClickListener{
            val intent=Intent(this, MainActivity::class.java)
            mp.stop()
            startActivity(intent)


        }


        playbutton.setImageResource(R.drawable.pause_button)
        playbutton.setOnClickListener {
            if(!mp.isPlaying)
            {
                mp.start()
                playbutton.setImageResource(R.drawable.pause_button)

            }
            else
            {
                mp.pause()
                playbutton.setImageResource(R.drawable.play_button)
            }
        }
        nextButton.setOnClickListener {
            mp.reset()
            position++
            mp.setDataSource(listSongs[position].url)
            songName.text=listSongs[position].title
            author.text=listSongs[position].author
            seekBar.progress=0
            seekBar.max=listSongs[position].duration!!.toInt()
            mp.prepare()
            mp.start()
        }
        previousButton.setOnClickListener {
            mp.reset()
            position--
            mp.setDataSource(listSongs[position].url)
            songName.text=listSongs[position].title
            author.text=listSongs[position].author
            seekBar.progress=0
            seekBar.max=mp.duration
            mp.prepare()
            mp.start()
        }
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, pos: Int, changed: Boolean) {
             if(changed) {
                 mp.seekTo(pos)
             }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

        })
        runnable= Runnable {
            seekBar.progress=mp.currentPosition
            handler.postDelayed(runnable,1000)

        }
        handler.postDelayed(runnable,1000 )
        mp.setOnCompletionListener {
            playbutton.setImageResource(R.drawable.play_button)
            seekBar.progress=0
        }


    }



    @OptIn(ExperimentalTime::class)
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






    }}



