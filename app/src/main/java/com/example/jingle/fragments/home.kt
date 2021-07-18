package com.example.jingle.fragments


import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jingle.*

import java.util.*
import kotlin.collections.ArrayList


class home : Fragment(), SongClicked,onMenuItemClicked {

    companion object{
        var flag=0
        var topPosition=0
    }
    var listSongs = splashScreen.listSongs
        private var displaylist=ArrayList<SongInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val thisContext=view.getContext()



        val searchView=view.findViewById<SearchView>(R.id.searchView)


        val searchPlateId = searchView.context.resources.getIdentifier("android:id/search_plate", null, null)
        val v=searchView.findViewById<View>(searchPlateId)
        v.setBackgroundColor(Color.TRANSPARENT)
        val recyclerView:RecyclerView=view.findViewById(R.id.songs_list)
        displaylist.addAll(listSongs)
        recyclerView.layoutManager= LinearLayoutManager(thisContext)
        val adapter:SongListAdapter= SongListAdapter(displaylist,this,this)
        recyclerView.adapter=adapter
        val Menu =(R.menu.item_menu_file)
        val menu_button=view.findViewById<ImageButton>(R.id.song_menu)
        val bottom_nav_menu=view.findViewById<View>(R.id.bottom_iv_play_bar)
        var check_ScrollingUp = false
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                    if (check_ScrollingUp) {
                        searchView.startAnimation(
                            AnimationUtils.loadAnimation(
                                context,
                                R.anim.trans_up
                            )
                        )
                        searchView.startAnimation(
                            AnimationUtils.loadAnimation(
                                context,
                                R.anim.trans_up
                            )
                        )
                        check_ScrollingUp = false
                    }
                } else {
                    // User scrolls down
                    if (!check_ScrollingUp) {
                        searchView
                            .startAnimation(
                                AnimationUtils
                                    .loadAnimation(context, R.anim.trans_down)
                            )
                        check_ScrollingUp = true
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })


        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!!.isNotEmpty()) {
                    displaylist.clear()
                    val search = newText.toLowerCase(Locale.getDefault())

                    listSongs.forEach {
                        if (it.title!!.toLowerCase(Locale.getDefault()).contains(search)) {
                            displaylist.add(it)
                            recyclerView.adapter!!.notifyDataSetChanged()
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()

                }
                else {

                            displaylist.clear()
                            displaylist.addAll(listSongs)
                            recyclerView.adapter!!.notifyDataSetChanged()
                        }

                    return true
                    }





            })



return view
}







    override fun onItemClicked(item: String,url:String,position: Int) {
        super.onItemClicked(item,url,position)
        val intent = Intent(requireContext(),song_viewer::class.java)


        intent.putExtra("position",position)
        startActivity(intent)
    }

    override fun onMenuItemClicked(position: Int,itemView: View) {


            val popup= PopupMenu(requireContext(),itemView)
            val inflater:MenuInflater=popup.menuInflater
            inflater.inflate(R.menu.item_menu_file,popup.menu)


        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener{ item->
                if(item.itemId==R.id.library_add ) {
                addToLibrary(position)
                }

            true



            })
        popup.show()
    }

    private fun addToLibrary(position: Int): Boolean {
        val intent =Intent(requireContext(),playlist2::class.java)
    flag=1
        topPosition=position
        startActivity(intent)
        return true

    }


}



