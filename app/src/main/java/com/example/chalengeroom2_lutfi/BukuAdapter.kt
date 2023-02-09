package com.example.chalengeroom2_lutfi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_buku_adapter.view.*


class BukuAdapter (private val buku:ArrayList<tbBuku>, private val listener: OnAdapterListener)
    : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>(){
    class BukuViewHolder(val view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        return BukuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_buku_adapter,parent, false)
        )
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val buk = buku[position]
        holder.view.TBuku.text = buk.kategor
        holder.view.TBuku.setOnClickListener{ listener.onClick(buk)}
        holder.view.imgEdit.setOnClickListener{listener.onUpdate(buk)}
        holder.view.imgDelete.setOnClickListener{listener.onDelete(buk)}
    }

    override fun getItemCount() = buku.size

    fun setData(list: List<tbBuku>){
        buku.clear()
        buku.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(tbbuk: tbBuku)
        fun onUpdate(tbbuk: tbBuku)
        fun onDelete(tbbuk: tbBuku)
    }

}