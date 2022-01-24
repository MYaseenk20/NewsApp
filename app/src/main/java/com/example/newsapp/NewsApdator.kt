package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.listview.view.*

class NewsApdator(val listener : NewsItemClicked) : RecyclerView.Adapter<newsViewHolder>() {

    val items : ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listview,parent,false)
        var viewholder = newsViewHolder(view)
        view.setOnClickListener {
            listener.OnItemClicked(items[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        holder.title.text ="Title : "+items[position].title
        holder.source_id.text = "Source : "+items[position].source_id
        Glide.with(holder.itemView.context).load(items[position].image_url).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateNews(updateNews : ArrayList<News>){
        items.clear()
        items.addAll(updateNews)
        notifyDataSetChanged()

    }
}
class newsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.title
    val source_id = itemView.sourceid
    val image = itemView.image
}
interface NewsItemClicked{
    fun OnItemClicked(item: News)
}