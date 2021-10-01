package rit.edu.student.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class publicSocialRecycleAdapter: RecyclerView.Adapter<publicSocialRecycleAdapter.ViewHolder>()  {
    var publicSocialList = mutableListOf<PublicSocial>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var publicFriendImageView:ImageView
        var publicFriendMessage: TextView
        var publicFriendName: TextView
        init{
            publicFriendMessage = itemView.findViewById(R.id.tv_friendPublicMessage)
            publicFriendName = itemView.findViewById(R.id.tv_publicFriendName)
            publicFriendImageView = itemView.findViewById(R.id.publicFriendImageView)
        }//init
    }//view holder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): publicSocialRecycleAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.public_card,parent,false)
        println("adapter size : ${publicSocialList.size}")
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.publicFriendName.text = publicSocialList[position].name
        holder.publicFriendMessage.text = publicSocialList[position].message
    }

    override fun getItemCount(): Int {
        return publicSocialList.size
    }

}//class recycleView