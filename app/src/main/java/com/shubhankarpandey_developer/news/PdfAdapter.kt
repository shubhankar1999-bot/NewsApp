package com.shubhankarpandey_developer.news

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PdfAdapter(

    private val listener: NewbieClicked
) : RecyclerView.Adapter<ViewHolder0>() {
    private var items: ArrayList<Pdf> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder0 {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.pdf_view, parent, false)
        val viewHolder = ViewHolder0(v)
        v.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        Log.d("JMSS", items.size.toString())
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder0, position: Int) {
        holder.textView.text = items[position].pdf_name
        holder.textView2.text = items[position].pdf_category
        Glide.with(holder.itemView.context).load(items[position].img_src).into(holder.imageView)
    }
    fun updateItem(updatedInfo:ArrayList<Pdf>){
        items.clear()
        items.addAll(updatedInfo)
        notifyDataSetChanged()
    }
}
class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView = itemView.findViewById(R.id.pdf_title) as TextView
    val textView2 = itemView.findViewById(R.id.pdf_category) as TextView
    val imageView: ImageView = itemView.findViewById(R.id.pdf_image)
}
interface NewbieClicked {
    fun onItemClicked(item: Pdf)
}