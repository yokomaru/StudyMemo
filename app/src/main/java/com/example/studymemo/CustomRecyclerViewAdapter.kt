package com.example.studymemo

import android.content.Intent
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import io.realm.RealmResults

class CustomRecyclerViewAdapter(realmResults: RealmResults<StudyRecording>) :
    RecyclerView.Adapter<ViewHolder>() {
    private val rResults: RealmResults<StudyRecording> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_result, parent, false)
        val viewholder = ViewHolder(view)
        return viewholder
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val StudyRecording = rResults[position]
        holder.dateText?.text = DateFormat.format("yyyy/MM/dd kk:mm", StudyRecording?.recordingdate)
        holder.studySubText?.text = StudyRecording?.studysub.toString()
        holder.timeText?.text = "${StudyRecording?.studytime.toString()}h"
        holder.memoText?.text = StudyRecording?.memo.toString()
        holder.itemView.setBackgroundColor(if (position % 2 == 0) Color.LTGRAY else Color.WHITE)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditActivity::class.java)
            intent.putExtra("id", StudyRecording?.id)
            it.context.startActivity(intent)
        }
    }
}