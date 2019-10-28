package com.example.studymemo

//import android.support.v7.widget.RycyclerView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.one_result.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var dateText:TextView? = null
    var studySubText:TextView? = null
    var timeText:TextView? = null
    var memoText:TextView? = null

    init {
        dateText = itemView.dateText
        studySubText = itemView.studySubText
        timeText = itemView.timeText
        memoText = itemView.memoText
    }
}