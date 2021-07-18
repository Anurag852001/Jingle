package com.example.jingle.fragments

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jingle.*
import com.google.gson.Gson
import java.lang.reflect.Type
import com.google.gson.reflect.TypeToken as TypeToken


class Library : Fragment(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view= inflater.inflate(R.layout.fragment_library, container, false)
        val playlists= view.findViewById<View>(R.id.iv_playlist)
        playlists.setOnClickListener{
            val intent=Intent(requireContext(),playlist2::class.java)
            startActivity(intent)
        }








        return view
    }


}