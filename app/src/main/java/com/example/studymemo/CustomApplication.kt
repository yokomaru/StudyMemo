package com.example.studymemo

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import androidx.recyclerview.widget.RecyclerView

class CustomApplication :Application(){
    override fun onCreate(){
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}