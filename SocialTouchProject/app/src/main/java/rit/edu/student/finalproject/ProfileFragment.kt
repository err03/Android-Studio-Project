package rit.edu.student.finalproject

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    //buttons
    private lateinit var saveButton:Button
    private lateinit var editButton:Button
    private lateinit var cancelButton:Button

    //editText
    private lateinit var et_name:EditText
    private lateinit var et_privateId:EditText
    private lateinit var et_age:EditText
    private lateinit var et_dob:EditText
    private lateinit var et_location:EditText
    private lateinit var et_sex:EditText

    //Firebase
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //database
        database = Firebase.database.reference
        //view
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        //init the EditText
        initEditText(view)
        //init the Buttons
        initButtons(view)

        return view
    }//onCreateView

    /**
     * init the EditText view from profile layout.
     */
    private fun initEditText(view:View){
        //editText
        et_name = view.findViewById(R.id.ET_userName)
        et_age = view.findViewById(R.id.ET_age)
        et_dob = view.findViewById(R.id.ET_dob)
        et_privateId = view.findViewById(R.id.ET_privateID)
        et_location = view.findViewById(R.id.ET_location)
        et_sex = view.findViewById(R.id.ET_sex)

        getDatabaseProfileInfo()
    }//initEditText

    /**
     * get the all need data from database
     */
    private fun getDatabaseProfileInfo(){
        //get profile data
        database.child("profile").child("age")
            .get().addOnSuccessListener { databaseSuccess(et_age,it.value) }    //when success, assign the target EditText and match

        database.child("profile").child("dob")
            .get().addOnSuccessListener { databaseSuccess(et_dob,it.value) }

        database.child("profile").child("location")
            .get().addOnSuccessListener { databaseSuccess(et_location,it.value) }

        database.child("profile").child("name")
            .get().addOnSuccessListener { databaseSuccess(et_name,it.value) }

        database.child("profile").child("private_id")
            .get().addOnSuccessListener { databaseSuccess(et_privateId,it.value) }

        database.child("profile").child("sex")
            .get().addOnSuccessListener { databaseSuccess(et_sex,it.value) }
    }//getDatabaseProfileInfo

    /**
     * reduce the repeating code.
     */
    private fun databaseSuccess(editText: EditText, value:Any?){
        editText.setText(value as String)
    }//databaseSuccess
    //--------------------------------------------------  Buttons
    /**
     * init the button and set the onClickListener{}
     */
    private fun initButtons(view:View){
        //editButton
        editButton = view.findViewById(R.id.button_edit)
        editButton.setOnClickListener { editButton(view) }

        //saveButton
        saveButton = view.findViewById(R.id.button_save)
        saveButton.setOnClickListener { saveButton(view) }

        //editButton
        cancelButton = view.findViewById(R.id.button_cancel)
        cancelButton.setOnClickListener { cancelButton(view) }
    }//initButtons

    /**
     * when edit button click, other two save and cancel button will display
     */
    private fun editButton(view:View){
        println("Edit Button click")
        editButton.visibility = View.INVISIBLE
        cancelButton.visibility = View.VISIBLE
        saveButton.visibility = View.VISIBLE
        showEdit()
    }//editButton

    /**
     * when save button click, cancel and save hide, edit diplay
     */
    private fun saveButton(view:View){
        println("Save Button click")
        saveButton.visibility = View.INVISIBLE
        cancelButton.visibility = View.INVISIBLE
        editButton.visibility = View.VISIBLE
        hideEdit()
        saveProfileInfo()
    }//saveButton

    /**
     * when cancel button click, save and cancel hide, edit display
     */
    private fun cancelButton(view:View){
        println("cancel Button click")
        cancelButton.visibility = View.INVISIBLE
        saveButton.visibility = View.INVISIBLE
        editButton.visibility = View.VISIBLE
        hideEdit()
        getDatabaseProfileInfo()
    }//cancelButton

    /**
     * save the data to the database(network)
     */
    private fun saveProfileInfo(){
        database.child("profile").child("age").setValue(et_age.text.toString())
        database.child("profile").child("dob").setValue(et_dob.text.toString())
        database.child("profile").child("location").setValue(et_location.text.toString())
        database.child("profile").child("name").setValue(et_name.text.toString())
        database.child("profile").child("sex").setValue(et_sex.text.toString())
        database.child("profile").child("private_id").setValue(et_privateId.text.toString())
    }//saveProfile

    //--------------------------------------------------------- show and hide Edit
    /**
     * when edit click, show the color, which mean that is editable
     */
    private fun showEdit(){
        et_sex.setBackgroundColor(Color.LTGRAY)
        et_location.setBackgroundColor(Color.LTGRAY)
        et_privateId.setBackgroundColor(Color.LTGRAY)
        et_dob.setBackgroundColor(Color.LTGRAY)
        et_age.setBackgroundColor(Color.LTGRAY)
        et_name.setBackgroundColor(Color.LTGRAY)

        et_sex.inputType = InputType.TYPE_CLASS_TEXT
        et_location.inputType = InputType.TYPE_CLASS_TEXT
        et_privateId.inputType = InputType.TYPE_CLASS_TEXT
        et_dob.inputType = InputType.TYPE_CLASS_TEXT
        et_age.inputType = InputType.TYPE_CLASS_TEXT
        et_name.inputType = InputType.TYPE_CLASS_TEXT
    }//showEditLine

    /**
     * when cancel or save, info will not editable
     */
    private fun hideEdit(){
        et_age.setBackgroundColor(Color.TRANSPARENT)
        et_name.setBackgroundColor(Color.TRANSPARENT)
        et_dob.setBackgroundColor(Color.TRANSPARENT)
        et_privateId.setBackgroundColor(Color.TRANSPARENT)
        et_location.setBackgroundColor(Color.TRANSPARENT)
        et_sex.setBackgroundColor(Color.TRANSPARENT)

        et_sex.inputType = InputType.TYPE_NULL
        et_location.inputType = InputType.TYPE_NULL
        et_privateId.inputType = InputType.TYPE_NULL
        et_dob.inputType = InputType.TYPE_NULL
        et_age.inputType = InputType.TYPE_NULL
        et_name.inputType = InputType.TYPE_NULL
    }//hideEditLine
}//Class