package com.example.githubuserverme.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.githubuserverme.R
import com.example.githubuserverme.response.UserData

class MainAdapter(private val results: ArrayList<UserData>, private val listener: OnAdapterListener?) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false)
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.bind(result)
        holder.view.setOnClickListener {
            listener?.onClick(result)
        }
    }

    fun updateData(newResults: List<UserData>) {
        results.clear()
        results.addAll(newResults)
        notifyDataSetChanged()
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvUsername: TextView = view.findViewById(R.id.tv_item_username)
        val imageView: ImageView = view.findViewById(R.id.img_avatar)


        fun bind(userData : UserData) {
            tvUsername.text = userData.username
            Glide.with(view.context)
                .load(userData.avatarurl)
                .placeholder(R.drawable.ic_launcher_background)
                .transform(CircleCrop())
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }

    }

    interface OnAdapterListener {
        fun onClick(data: UserData)
    }
}
