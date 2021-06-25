package com.example.jingle


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jingle.fragments.Library
import com.example.jingle.fragments.Search
import com.example.jingle.fragments.home
import com.ismaeldivita.chipnavigation.ChipNavigationBar


class MainActivity : AppCompatActivity(),SongClicked {


    private val searchFragment = Search()
    private val homeFragment = home()
    private val libraryFragment = Library()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)
        var bottomNav:ChipNavigationBar=findViewById(R.id.bottom_nav_bar)
        bottomNav.setOnItemSelectedListener{ id->
            when(id) {
                R.id.nav_home -> replaceFragment(homeFragment)
                R.id.nav_search -> replaceFragment(searchFragment)
                R.id.nav_library -> replaceFragment(libraryFragment)
            }
        }


    }





    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()

    }


}