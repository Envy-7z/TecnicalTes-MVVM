package com.wisnu.tecnicaltes_mvvm.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wisnu.tecnicaltes_mvvm.R
import com.wisnu.tecnicaltes_mvvm.view.adapter.MainAdaper
import com.wisnu.tecnicaltes_mvvm.model.MainResponse
import com.wisnu.tecnicaltes_mvvm.viewModel.ViewModelMainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel : ViewModelMainActivity
    private var adapterMenu: MainAdaper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(ViewModelMainActivity::class.java)

        viewModel.getListData()

        swipeContainer.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.purple_200))
        swipeContainer.setColorSchemeColors(Color.WHITE)

        swipeContainer.setOnRefreshListener {
            viewModel.getListData()
            swipeContainer.isRefreshing = false

        }
        attachObserve()
    }

    private fun attachObserve() {
        viewModel.responData.observe(this , Observer { showData(it)  })
        viewModel.isError.observe(this , Observer {
            showError(it)
        })

        viewModel.isLoading.observe(this , Observer { showLoading(it) })
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) pb.visibility = View.VISIBLE else pb.visibility = View.GONE
    }

    private fun showError(it: Throwable?) {
        Toast.makeText(applicationContext, it?.message, Toast.LENGTH_LONG).show()
    }

    private fun showData(it: MainResponse) {
        adapterMenu = MainAdaper(rv_main, this, it.articles)
        rv_main.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_main.adapter = adapterMenu
    }
}