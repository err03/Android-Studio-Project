package rit.edu.student.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class message_contact : Fragment() {
    private var communicateWithID = ""      //for database's path
    private var userMessageList = arrayListOf<String>()     //store all the message to this arraylist
    private var messageSequenceList = arrayListOf<String>() //store the sequence sign, example: a(self) & b(friend)

    private var messageLayoutManager: RecyclerView.LayoutManager? = null
    private var messageAdapter :messageRecycleAdapter? = null

    //database
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_message_contact, container, false)
        initRequireData(view)       //init the require data
        initRecycleView(view)
        sendMessageButtonClick(view)
        updateMessageListener()
        return view
    }

    private fun initRequireData(view: View){
        database = Firebase.database.reference      //database

        val args:message_contactArgs by navArgs()   //define the args, use navigation pass argument
        for(element in args.messageListArgument!!){     //for the argument then add to the list
            userMessageList.add(element)
        }

        for (element in args.messageSequenceArgument!!){    //for the argument then add to the list
            messageSequenceList.add(element)
        }

        communicateWithID = args.cWithPathID     //get the user who communicate with's ID number

        val friendName = view.findViewById<TextView>(R.id.tv_publicFriendName)  //get the screen top name
        friendName.setText(args.cWithName)  //set the communication top title to target friend's name
    }//init the require database

    /**
     * init the recycleView, and layoutManager, assgin the target Adapter's list to this.list
     */
    private fun initRecycleView(view: View){
        val recycleView = view.findViewById<RecyclerView>(R.id.messageRecycleView)
        messageLayoutManager = LinearLayoutManager(this.context)
        recycleView.layoutManager = messageLayoutManager
        messageAdapter = messageRecycleAdapter()
        messageAdapter!!.user1messageList = this.userMessageList
        messageAdapter!!.messageSequenceList = this.messageSequenceList
        recycleView.adapter = messageAdapter
    }//initRecycleView

    /**
     * update the message communication screen. Dynamic
     */
    private fun updateMessageListener(){
        val user1Listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userMessageList.clear()     //clear the list, because this will loop again when data change. I don't want the repeat data
                messageSequenceList.clear() //clear the list, because this will loop again when data change. I don't want the repeat data

                val post = snapshot.children.map {          //get the list
                    userMessageList.add(it.value.toString())    //add message value from databsse
                    messageSequenceList.add(it.key!!.get(it.key!!.length - 1).toString())   //add sequence sign value from database
                    messageAdapter!!.notifyDataSetChanged()     //notify the data change, so screeen will update
                }//val post
            }//onDataChange

            override fun onCancelled(error: DatabaseError) {
                println("messageRecycleAdapter data faill")
            }//onCalcelled
        }

        //assign the listener, database path "cWithXXXXX"
        database.child("cWith"+communicateWithID).addValueEventListener(user1Listener)
//        println("here we go : cWith"+communicateWithID)
    }//update message listener

    //button click
    private fun sendMessageButtonClick(view: View){
        val sendMsgButton: Button = view.findViewById(R.id.buttonSendMessage)   //get the button view
        var message:EditText
        sendMsgButton.setOnClickListener {
            message = view.findViewById(R.id.tf_message)    //get the message
//            println("ddd:${message.text}")
            val messageID = "m"+(messageSequenceList.size + 1) + "_a"   //id will like this m2_a, m3_a ...; a(self) b(friend)
            database.child("cWith"+communicateWithID).child(messageID).setValue(message.text.toString())    //add new value to database
        }
    }//send message button click
}//Class