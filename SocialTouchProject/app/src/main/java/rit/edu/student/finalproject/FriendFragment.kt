package rit.edu.student.finalproject

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FriendFragment : Fragment() {

    //recycle
    private lateinit var friendLayoutManager: RecyclerView.LayoutManager
    private lateinit var friendAdapter:FriendRecycleAdapter

    //friend list
    var friendList = mutableListOf<Friend>()

    //database
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initRequireData()       //always init the data first

        val view = inflater.inflate(R.layout.fragment_friend, container, false)
        getFriendList(view)     //get the list from database
        initFriendRecycleView(view)     //init pass the data to adapter

        //button
        val addFriendButton = view.findViewById<TextView>(R.id.addFriendButton)
        addFriendButton.setOnClickListener { addFriendButtonClick(view) }

        return view
    }

    private fun initRequireData(){      //init the database
        database = Firebase.database.reference
    }//init require data

    /**
     * get the data from database, then add the value to the firendList<Friend>()
     */
    private fun getFriendList(view:View){
        val friendListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                friendList.clear()          //when data change, always clear it. because I don't want the repeat data
                val post = snapshot.children.map {
                    val name = it.child("name").value.toString()    //get the name from database
                    val id = it.child("id").value.toString()        //get the id from database
                    val friend = Friend(null,name,id)           //new a Friend(image,name,id) ; image has it default icon
                    friendList.add(friend)                                 //add new friend to friendList
                    println("Friend List list:${friendList}")
                    updateFriendRecycleView(view)                   //when add friend to list, update the Recycle view, so it is dynamic
                }//val post
            }//onDataChange
            override fun onCancelled(error: DatabaseError) {
                println("messageRecycleAdapter data faill")
            }//onCalcelled
        }//friendListener

        database.child("friendList").addValueEventListener(friendListener)      //listener
    }//getFriendListv

    /**
     * init the recycle view, assign the data to the match recycle view layout.
     */
    fun initFriendRecycleView(view:View){
        val friendRecycleView:RecyclerView = view.findViewById(R.id.friendRecycleView)  //get the view layout
        friendAdapter = FriendRecycleAdapter()      //init the friendAdapter = FriendRecycleAdapter()
        friendLayoutManager = LinearLayoutManager(this.context)     //init the friendLayout manager

        friendAdapter.friendList = this.friendList          //target recycle class' list will equal to this.list
        friendRecycleView.layoutManager = friendLayoutManager   //then viewlayout will equal to the target layout
        friendRecycleView.adapter = friendAdapter       //adapter = this friendAdapter
    }//initFriendRecycleView

    fun updateFriendRecycleView(view: View){
        initFriendRecycleView(view)
    }//update friend recycle view

    fun addFriendButtonClick(view: View){
        val addFriendDialog = AddFriendDialogFragment()
        addFriendDialog.show(this.childFragmentManager,"addFriendDialog")   //show the dialog
    }//addFriendButtonClick

}//Class Fragment