package com.example.studymemo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class StudyRecording :RealmObject(){
    @PrimaryKey
    var id: Long = 0
    var recordingdate: Date = Date()
    var studysub:String = ""
    var studytime:Double = 0.0
    var memo:String = ""
    var dateTime: Date = Date()
}