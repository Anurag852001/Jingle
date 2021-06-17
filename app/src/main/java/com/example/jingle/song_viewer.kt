package com.example.jingle


import android.content.Intent

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.provider.MediaStore

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

import kotlin.time.ExperimentalTime



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
        val seekBar=findViewById<SeekBar>(R.id.seekBar)
        val backButton=findViewById<ImageView>(R.id.back_button)


        mp.setDataSource(listSongs[position].url)
        songName.text=listSongs[position].title
        author.text=listSongs[position].author
        mp.prepare()
        seekBar.progress=0
        seekBar.max=mp.duration

        mp.start()


        backButton.setOnClickListener{
            mp.release()
            val intent=Intent(this, MainActivity::class.java)
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
            playbutton.setImageResource(R.drawable.pause_button)
            mp.prepare()
            seekBar.max=mp.duration
            mp.start()
        }
        previousButton.setOnClickListener {
            mp.reset()
            position--
            mp.setDataSource(listSongs[position].url)
            songName.text=listSongs[position].title
            author.text=listSongs[position].author
            seekBar.progress=0
            mp.prepare()
            playbutton.setImageResource(R.drawable.pause_button)
            seekBar.max=mp.duration
            mp.start()
        }
        repeatButton.setOnClickListener {
            if(!mp.isLooping) {
                mp.isLooping = true

                repeatButton.background=resources.getDrawable(R.drawable.drawable_play)
            }
            else {
                mp.isLooping=false
                repeatButton.setBackgroundColor(resources.getColor(R.color.black))
            }
            }
        runnable= Runnable {
            seekBar.progress=mp.currentPosition
            handler.postDelayed(runnable,0)

        }
         seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser)
                    mp.seekTo(progress)
            }

       override fun onStartTrackingTouch(seekBar: SeekBar?) {
           mp.pause()
       }

       override fun onStopTrackingTouch(seekBar: SeekBar?) {
           mp.start()
       }})



       handler.postDelayed(runnable,500)
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


