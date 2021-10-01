package rit.edu.student.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class messageRecycleAdapter : RecyclerView.Adapter<messageRecycleAdapter.ViewHolder>() {

    var user1messageList = arrayListOf<String>()
    var messageSequenceList = arrayListOf<String>()

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var friendMessage:TextView
        var userMessage:TextView
        init{
            friendMessage = itemView.findViewById(R.id.friendMessageCardView)
            userMessage = itemView.findViewById(R.id.userMessageCardView)
        }//init
    }//view holder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_card,parent,false)
//        println("adapter size : ${messageSequenceList.size}")
        return ViewHolder(v)
    }//onCreate ViewHolder

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("adatper position: ${position}")
        if(messageSequenceList[position] == "b"){   //if this is b, then friend message card will visibile,
            holder.friendMessage.visibility = View.VISIBLE
            holder.userMessage.visibility = View.INVISIBLE
            holder.friendMessage.text = user1messageList[position]  //text assign
        }else{                                  //else is self, user message card will visible.
            holder.userMessage.visibility = View.VISIBLE
            holder.friendMessage.visibility = View.INVISIBLE
            holder.userMessage.text = user1messageList[position]    //text assign
        }

    }//onBind ViewHodler

    override fun getItemCount(): Int {
        return messageSequenceList.size
    }//get item count

}//message recycleAdapter