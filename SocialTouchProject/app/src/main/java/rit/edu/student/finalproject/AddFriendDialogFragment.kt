package rit.edu.student.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddFriendDialogFragment: DialogFragment() {

    private lateinit var database: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        database = Firebase.database.reference
        return inflater.inflate(R.layout.addfriend_dialog,container,false)
    }//onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addFriendButton = view.findViewById<Button>(R.id.addButton)
        addFriendButton.setOnClickListener { addButtonClick(view) }
    }//OnViewCreated

    fun addButtonClick(view: View){
        val addID = view.findViewById<EditText>(R.id.et_friendID)
        val addMessage:TextView = view.findViewById(R.id.addMessage)

        if(addID.text.toString().equals("")){       //if user didn't put id, return it
            return
        }else{
            database.child("publicFriend").get().addOnSuccessListener {
                it.children.forEach {               // foreach loop to get the value
                    val foundId = it.child("id").value.toString()       //get the value
//                    println("id:${foundId}")
                    if(addID.text.toString().equals(foundId)){      //when equal, found
                        addFriendSuccess(it,addMessage)     //pass value
                    }else{
                        addMessage.setText("Friend not found")
                    }
                }//it.children
            }//database successlistner
        }//if..else

    }//addButtonClick

    fun addFriendSuccess(addFriendData:DataSnapshot,message:TextView){
        var friendCount = 0;    //for record the friend count,use for sign and create new value
        database.child("friendList").get().addOnSuccessListener {
            it.children.forEach {       //loop it
                friendCount++       //add
            }//it.children

            val friendSign = "f"+(friendCount+1)        //+1, make new path
            val idValue = addFriendData.child("id").value.toString()    //get id from database
            val nameValue = addFriendData.child("name").value.toString()    //get name
//            println("count:${friendCount}")
//            println("id:${idValue}")
//            println("name:${nameValue}")
            val newfriend = database.child("friendList").child(friendSign)  //make new value to database
            newfriend.child("id").setValue(idValue)     //make new value to database
            newfriend.child("name").setValue(nameValue) //make new value to database
            message.setText("Add Success")      //diaplay the textview
        }//add success
        .addOnFailureListener {             //if fail
            message.setText("Add fail")
        }//add fail
    }//add friend success

}//class dialog fragment