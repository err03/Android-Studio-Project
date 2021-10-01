package rit.edu.student.finalproject

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import rit.edu.student.finalproject.databinding.FragmentMessageContactBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set the bottom navigation
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.BottomNavigationView)
        val navConfig = findNavController(R.id.fragment)
        bottomNavigation.setupWithNavController(navConfig)

        //set navigation bar title change - able to change link
        val appBar = AppBarConfiguration(
            setOf(
                R.id.friendFragment,
                R.id.publicFragment,
                R.id.profileFragment
            )
        )

        setupActionBarWithNavController(navConfig, appBar)
    }//onCreate

    /**
     * this function is for communicate screen that top right "back" button, navigate back to the Friend screen
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val action = message_contactDirections.actionMessageContactToFriendFragment()
        Navigation.findNavController(this,R.id.fragment).navigate(action)
        return super.onOptionsItemSelected(item)
    }//on options item selected

    //create menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }//onCreateOptionsMenu
    
}