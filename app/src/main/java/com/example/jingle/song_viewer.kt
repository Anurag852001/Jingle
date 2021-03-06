package com.example.jingle


import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.Layout
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.example.jingle.fragments.home
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.random.Random
import kotlin.time.ExperimentalTime


class song_viewer (): AppCompatActivity()  {


    private val listSongs= splashScreen.listSongs

    lateinit var runnable: Runnable
    private var handler = Handler()
    lateinit var mp: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_viewer)





         mp= MediaPlayer()
        val playbutton=findViewById<ImageButton>(R.id.play_button)
        var position =intent.getIntExtra("position",1)





        val nextButton=findViewById<ImageButton>(R.id.next_button)
        val previousButton=findViewById<ImageButton>(R.id.previous_button)
        val repeatButton=findViewById<ImageButton>(R.id.repeatButton)
        val songName=findViewById<TextView>(R.id.song_name)
        val author=findViewById<TextView>(R.id.singer_name)
        val seekBar=findViewById<SeekBar>(R.id.seekBar)
        val backButton=findViewById<ImageView>(R.id.back_button)
        val startTime=findViewById<TextView>(R.id.start_time)
        val endTime=findViewById<TextView>(R.id.end_time)
        val songImage=findViewById<ImageView>(R.id.song_image)
        val mmr=MediaMetadataRetriever()
        val background=findViewById<View>(R.id.view_song)
        background.setBackgroundResource(generateResource())

        loadImage(mmr,position,songImage)

        mp.setDataSource(listSongs[position].url)

        author.text=listSongs[position].author
        songName.text=listSongs[position].title

        mp.prepare()
        seekBar.progress=0
        seekBar.max=mp.duration
        startTime.text= startTime()
        endTime.text=endTime(mp.duration)
        mp.start()


        backButton.setOnClickListener{

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
            loadImage(mmr,position,songImage)
            background.setBackgroundResource(generateResource())
            seekBar.max=mp.duration
            startTime.text= startTime()
            endTime.text=endTime(mp.duration)
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
            loadImage(mmr,position,songImage)
            background.setBackgroundResource(generateResource())
            startTime.text= startTime()
            endTime.text=endTime(mp.duration)
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

                startTime.text=endTime(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mp.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mp.start()
            }})



        handler.postDelayed(runnable,500)
        mp.setOnCompletionListener {
            seekBar.progress=0
            position++
            mp.reset()
            playbutton.setImageResource(R.drawable.pause_button)
            songName.text=listSongs[position].title
            author.text=listSongs[position].author
            mp.setDataSource(listSongs[position].url)
            mp.prepare()
            mp.start()
        }


    }



    private fun endTime(duration: Int): String {

        val seconds=duration/1000
        val minutes=seconds/60
        val reascends = seconds-minutes*60

        if(reascends/10==0)
            return "$minutes:0$reascends"

        return "$minutes:$reascends"

    }


    private fun startTime(): String {
        return "0:00"
    }



private fun loadImage(mmr: MediaMetadataRetriever, position:Int,songImage:ImageView)
{
    mmr.setDataSource(listSongs[position].url)
    val artBytes: ByteArray? = mmr.embeddedPicture
    if (artBytes != null) {
        val image = ByteArrayInputStream(mmr.embeddedPicture)
        val bm = BitmapFactory.decodeStream(image)
        songImage.setImageBitmap(bm)
    } else {
        songImage.setImageDrawable(resources.getDrawable(R.drawable.default_cover))
    }
}


    private fun generateResource():Int
     {
         val r= Random.nextInt(1,5)
         when(r)
        {
             1-> return (R.drawable.one)
             2->return (R.drawable.two)
             3->return (R.drawable.three)
             4->return (R.drawable.sdgs)
             else-> return R.drawable.four
        }
    }


}


