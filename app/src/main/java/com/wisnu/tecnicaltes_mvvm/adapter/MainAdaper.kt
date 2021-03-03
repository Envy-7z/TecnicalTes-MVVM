package com.wisnu.tecnicaltes_mvvm.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wisnu.tecnicaltes_mvvm.R
import com.wisnu.tecnicaltes_mvvm.model.MainResponse
import com.wisnu.tecnicaltes_mvvm.utils.RVAdapter
import com.wisnu.tecnicaltes_mvvm.view.DetailActivity
import kotlinx.android.synthetic.main.item_main.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainAdaper(rv: RecyclerView, context: Context, mList: List<MainResponse.Article>) :

    RVAdapter<MainResponse.Article>(rv, context, mList) {




    override fun onCreateEditViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_main,
            parent,
            false
        )
        return ItemHolder(view)
    }
    @Throws(ParseException::class)

    private fun modifyDateLayout(inputDate: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(inputDate)
        return SimpleDateFormat("EEEE, dd MMM yyyy HH:mm a").format(date)
    }
    override fun onBindMyViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        val newDate: String = modifyDateLayout(data.publishedAt)
        Glide.with(mContext)
            .load(data.urlToImage)
            .into(holder.itemView.ivGambar)
        holder.itemView.tvTitle.text = data.title
        holder.itemView.tvSumber.text = data.source.name
        holder.itemView.tvTanggal.text = newDate
        holder.itemView.tvDesc.text = data.description

        holder.itemView.tvNext.setOnClickListener {
            val myIntent = Intent(mContext, DetailActivity::class.java)
            myIntent.putExtra("gambar", data.urlToImage)
            myIntent.putExtra("title", data.title)
            myIntent.putExtra("name", data.source.name)
            myIntent.putExtra("tanggal", data.publishedAt)
            myIntent.putExtra("desc", data.description)
            mContext.startActivity(myIntent)
        }
    }




    override fun getMyItemViewType(position: Int): Int {
        return 0
    }

    override fun filterCondition(originalData: List<MainResponse.Article>, constraint: String): MutableList<MainResponse.Article> {
        return ArrayList()
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}