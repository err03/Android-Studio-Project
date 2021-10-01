package rit.edu.student.finalproject

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FriendRecycleAdapter: RecyclerView.Adapter<FriendRecycleAdapter.ViewHolder>() {
    var friendList = mutableListOf<Friend>()
    var messageList = arrayListOf<String>()
    var messageSequenceList = arrayListOf<String>()

    private lateinit var database: DatabaseReference

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var friendImage:ImageView
        var friendName:TextView
        var friendID:TextView
        init{
            friendImage = itemView.findViewById(R.id.friendImageView)
            friendName = itemView.findViewById(R.id.tv_publicFriendName)
            friendID = itemView.findViewById(R.id.friendID)
            database = Firebase.database.reference

            //when user click target friend, then will navigate to the match friend
            itemView.setOnClickListener {
                val action = FriendFragmentDirections.actionFriendFragmentToMessageContact(
                        messageList.toTypedArray(),messageSequenceList.toTypedArray(),friendID.text.toString(),friendName.text.toString())
                Navigation.findNavController(it).navigate(action)
            }//itemView. onClick. Navigation

            //when user click delete button, then will do delete friend function
            itemView.findViewById<Button>(R.id.deleteFriendButton).setOnClickListener {
                deleteFriend(friendID.text.toString(),itemView)     //pass the friendID is to match the position
            }//delete button onClick
        }//init

    }//view holder

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.friend_card,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.friendName.text = friendList[position].name
        viewHolder.friendImage.setImageResource(friendList[position].image!!)
        viewHolder.friendID.text = friendList[position].id
    }//onbind view holder

    override fun getItemCount(): Int {
        return friendList.size
    }//getItemCount

    fun getUpdateDatabaseListener(){
        val user1Listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                messageSequenceList.clear()
                val post = snapshot.children.map {
                    messageList.add(it.value.toString())
                    println("message recycle adapeter value: ${it.key!!.get(0)}")
                    messageSequenceList.add(it.key!!.get(it.key!!.length-1).toString())
                    println("recycle arraylist ${messageList}")
                    println("message arraylist ${messageSequenceList}")
                }//val post
            }//onDataChange

            override fun onCancelled(error: DatabaseError) {
                println("messageRecycleAdapter data faill")
            }//onCalcelled
        }//postListener
        database.child("c1").addValueEventListener(user1Listener)
    }//get Update database listener


    fun deleteFriend(id:String,view:View){
        var friendPosition = 0      //set positoin to 0

        database.child("friendList").get().addOnSuccessListener {
            it.children.forEach {           //loop the database
                friendPosition++        //loop it, position ++

                val targetID = it.child("id").value.toString()  //get the targetID from database
                if(id.equals(targetID)){    //if id(user click) is equal the targetID(from database)
                    DeleteFriendDialogFragment(friendPosition)      //show the dialog, yes or no?
                            .show((view.context as AppCompatActivity).supportFragmentManager,"deleteDialog")
                }//if
            }//it.foreach
        }//database
    }//delete friend
}//class recycle adapter