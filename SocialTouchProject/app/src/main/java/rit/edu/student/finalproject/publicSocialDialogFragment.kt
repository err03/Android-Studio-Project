package rit.edu.student.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class publicSocialDialogFragment(_publicSocialList:ArrayList<PublicSocial>): DialogFragment() {

    lateinit var database: DatabaseReference
    var publicSocicalList = _publicSocialList   //list from paramater, use for new key as p1,p2,p3...

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.publishsocial_dialog,container,false)
    }//onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = Firebase.database.reference

        val publishMessage = view.findViewById<EditText>(R.id.et_publishMessage)    //get the publish EditText view

        //button set the onclickListener
        view.findViewById<Button>(R.id.publishYesButton).setOnClickListener { publishButtonClick(publishMessage) }
        view.findViewById<Button>(R.id.publishCancelButton).setOnClickListener { cancelButtonClick() }
    }//onViewCreate

    fun publishButtonClick(publishMessage:EditText){
        var personalName = ""
        val publicLength = publicSocicalList.size + 1   //use for when add new value, need key as p1,p2,p3...

        database.child("profile").child("name").get().addOnSuccessListener {
            personalName = it.value.toString()  //get the user's personal name

            val publicDatabase = database.child("publicSocial").child("p"+publicLength)
            publicDatabase.child("message").setValue(publishMessage.text.toString())    //set the message value to database
            publicDatabase.child("name").setValue(personalName)     //set the name value to the database
        }//database

        this.dismiss()  //dismiss the dialog
    }//yesButtonClick

    fun cancelButtonClick(){
        println("no button click")
        this.dismiss()  //dismiss the dialog
    }//noButtonClick
}