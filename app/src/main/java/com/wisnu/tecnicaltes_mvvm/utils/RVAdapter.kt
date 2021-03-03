package com.wisnu.tecnicaltes_mvvm.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList



abstract class RVAdapter<T>(protected val rv: RecyclerView, protected var mContext: Context, List: List<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var mList: MutableList<T> = ArrayList()
    private val mListOriginal = ArrayList<T>()
    private var isRefreshed = false
    var change: Change? = null

    //animation
    private val mDuration = 300
    private val mInterpolator = LinearInterpolator()
    private var mLastPosition = -1
    private val isFirstOnly = true

    //cache data removed
    var removedData : T?= null
    var removedPosition : Int? = null

    val list: MutableList<T>
        get() = mList

    private fun clear(v: View) {
        v.alpha = 1f
        v.scaleY = 1f
        v.scaleX = 1f
        v.translationY = 0f
        v.translationX = 0f
        v.rotation = 0f
        v.rotationY = 0f
        v.rotationX = 0f
        v.pivotY = (v.measuredHeight / 2).toFloat()
        v.pivotX = (v.measuredWidth / 2).toFloat()
        ViewCompat.animate(v).setInterpolator(null).startDelay = 0
    }

    private fun getAnimators(view: View): Array<Animator> {
        return arrayOf(ObjectAnimator.ofFloat(view, "translationY", view.measuredHeight.toFloat(), 0f))//                ObjectAnimator.ofFloat(view, "translationX", view.getMeasuredHeight(), 0)
    }
    //

    fun addData(t: T) {
        mList.add(t)
        mListOriginal.add(t)
        rv.post { notifyDataSetChanged() }
    }

    fun addData(t: T, pos: Int) {
        mList.add(t)
        mListOriginal.add(pos,t)
        rv.post { notifyDataSetChanged() }
    }

    fun removeData(pos : Int){
        //save to cache
        removedData = list[pos]
        removedPosition = pos

        mList.removeAt(pos)
        mListOriginal.removeAt(pos)
        rv.post {
            notifyItemRemoved(pos)
        }
    }

    fun restoreData(){
        mList.add(removedPosition!!, removedData!!)
        mListOriginal.add(removedPosition!!, removedData!!)
        rv.post {
            notifyItemInserted(removedPosition!!)
        }
    }

    fun addData(t: List<T>) {
        mList.clear()
        mListOriginal.clear()
        mList.addAll(t)
        mListOriginal.addAll(t)
        rv.post { notifyDataSetChanged() }
    }

    fun addMoreData(t: List<T>){
        mList.addAll(t)
        mListOriginal.addAll(t)
        rv.post { notifyDataSetChanged() }
    }

    init {
        this.mList.addAll(List)
        this.mListOriginal.addAll(List)

        this.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isRefreshed) {
                    return
                }
                if (rv.layoutManager is LinearLayoutManager) {
                    val layoutManager = rv
                            .layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                        //End of list
                        if(!isRefreshed){
                            isRefreshed = true
                        }

                        isRefreshed = false
                    }
                } else if (rv.layoutManager is GridLayoutManager) {
                    val layoutManager = recyclerView
                            .layoutManager as GridLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                        //End of list
                        if(!isRefreshed){
                            isRefreshed = true
                        }

                        isRefreshed = false
                    }
                }

            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateEditViewHolder(parent, viewType)
    }

    abstract fun onCreateEditViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindMyViewHolder(holder, position)

        val adapterPosition = holder.adapterPosition
        if (!isFirstOnly || adapterPosition > mLastPosition) {
            for (anim in getAnimators(holder.itemView)) {
                anim.setDuration(mDuration.toLong()).start()
                anim.interpolator = mInterpolator
            }
            mLastPosition = adapterPosition
        } else {
            clear(holder.itemView)
        }
    }

    abstract fun onBindMyViewHolder(holder: RecyclerView.ViewHolder, position: Int)


    abstract fun getMyItemViewType(position: Int): Int

    override fun getItemViewType(position: Int): Int {
        return getMyItemViewType(position)
    }

    override fun getItemCount(): Int {
        change?.onCountChange(mList.size)
        return mList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            public override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
                var filteredResults: MutableList<T>? = ArrayList()
                if (constraint.isEmpty()) {
                    filteredResults!!.addAll(mListOriginal)
                } else {
                    filteredResults = filterCondition(mListOriginal, constraint.toString().toLowerCase())
                }
                val results = Filter.FilterResults()
                results.values = filteredResults
                return results
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                if (results.values != null) {
                    mList = results.values as MutableList<T>
                    notifyDataSetChanged()
                }
            }
        }
    }

    abstract fun filterCondition(originalData: List<T>, constraint: String): MutableList<T>

    interface Change {
        fun onCountChange(count: Int)
    }
}
