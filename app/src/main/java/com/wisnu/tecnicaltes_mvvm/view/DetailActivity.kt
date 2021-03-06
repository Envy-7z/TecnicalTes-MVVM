package com.wisnu.tecnicaltes_mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wisnu.tecnicaltes_mvvm.R
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val mIntent = intent
        val gambar = mIntent.getStringExtra("gambar")
        val title = mIntent.getStringExtra("title")
        val sumber = mIntent.getStringExtra("name")
        val tanggal = mIntent.getStringExtra("tanggal")
        val desc = mIntent.getStringExtra("desc")

        Glide.with(this)
            .load(gambar)
            .into(ivGambar)
        tvTitle.text = title
        tvSumber.text = sumber
        val newDate: String = modifyDateLayout(tanggal.toString())

        tvTanggal.text = newDate
        tvDesc.text = desc

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Berita"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


    }
    private fun modifyDateLayout(inputDate: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(inputDate)
        return SimpleDateFormat("EEEE, dd MMM yyyy HH:mm a").format(date)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}