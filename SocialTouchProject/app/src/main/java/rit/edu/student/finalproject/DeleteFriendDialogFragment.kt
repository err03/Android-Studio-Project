package rit.edu.student.finalproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DeleteFriendDialogFragment(_targetIDPosition:Int): DialogFragment() {

    var position = _targetIDPosition    //get the position from paramater

    lateinit var database: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.deletefriend_dialog,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference

        view.findViewById<Button>(R.id.yesDeleteButton).setOnClickListener { yesButtonClick() }
        view.findViewById<Button>(R.id.noDeleteButton).setOnClickListener { noButtonClick() }
    }

    fun yesButtonClick(){
//        println("yes button click")
        val targetPath = ("f"+position)  //target's position
        database.child("friendList").child(targetPath).removeValue()    //when confirm, delete the value from database
        this.dismiss()
    }//yesButtonClick

    fun noButtonClick(){
//        println("no button click")
        this.dismiss()
    }//noButtonClick
}