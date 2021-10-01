package rit.edu.student.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

class PublicFragment : Fragment() {

    //list
    private var publicSocicalList = arrayListOf<PublicSocial>()

    private var publicSocialLayoutManager: RecyclerView.LayoutManager? = null
    private var publicSocialAdapter :publicSocialRecycleAdapter? = null

    //database
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initRequireData()   //always init the data
        val publicView = inflater.inflate(R.layout.fragment_public, container, false);
        getPublicSocial(publicView)     //get the data from database
        initRecycleView(publicView)

        publicView.findViewById<Button>(R.id.publishPublicSocialButton).setOnClickListener { publishButtonClick() }
        return publicView
    }//onCreateView

    private fun initRequireData(){
        database = Firebase.database.reference

    }//init the require database

    private fun getPublicSocial(view:View){
        val publicSocialListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                publicSocicalList.clear()   //always clear the list when data change, because I don't want the repeat data

                val post = snapshot.children.map {      //map list
                    val name = it.child("name").value.toString()    //get the name from database
                    val message = it.child("message").value.toString()  //get the message from database
                    val publicsocial = PublicSocial(null,name,message)  //new PublicSocial(image,name,message) ; image has it default icon
                    publicSocicalList.add(publicsocial)         //add it to the list
//                    println("public social list:${publicSocicalList}")
                    updateRecycleView(view) //when add it, update the data(re-assign the data to adapter)
                }//val post
            }//onDataChange
            override fun onCancelled(error: DatabaseError) {
                println("publicSocial data faill")
            }//onCalcelled
        }//friendListener

        //listnere
        database.child("publicSocial").addValueEventListener(publicSocialListener)
    }//getPublicSocial

    /**
     * init the layout manager and assign the data to adapter
     */
    private fun initRecycleView(view: View){
        val recycleView = view.findViewById<RecyclerView>(R.id.publicSocialRecycleView)
        publicSocialLayoutManager = LinearLayoutManager(this.context)
        recycleView.layoutManager = publicSocialLayoutManager
        publicSocialAdapter = publicSocialRecycleAdapter()
        publicSocialAdapter!!.publicSocialList = this.publicSocicalList
        recycleView.adapter = publicSocialAdapter
    }//initRecycleView

    /**
     * just re-use initRecycleView()
     */
    private fun updateRecycleView(view:View){
        initRecycleView(view)
    }//update it

    /**
     * publish button click
     */
    private fun publishButtonClick(){
        val publicSocialDialog = publicSocialDialogFragment(publicSocicalList)  //pass the list, I need the size, so I can add new key as p1,p2,p3...
        publicSocialDialog.show(this.childFragmentManager,"publicSocialDialog") //display the dialog
    }//click publish Button
}//class fragment