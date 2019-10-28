package com.example.studymemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*
import java.text.ParseException
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.Date

class EditActivity : AppCompatActivity() {
    private val tag = "StudyRecording"
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        realm = Realm.getDefaultInstance()

        val bpId = intent.getLongExtra("id", 0L)
        if (bpId > 0L) {
            val studyRecording = realm.where<StudyRecording>()
                .equalTo("id", bpId).findFirst()
            studySubEdit.setText(studyRecording?.studysub.toString())
            dateEdit.setText(studyRecording?.recordingdate.toString())
            timeEdit.setText(studyRecording?.studytime.toString())
            memoEdit.setText(studyRecording?.memo.toString())
            deleteBtn.visibility = View.VISIBLE
        } else {
            deleteBtn.visibility = View.INVISIBLE
        }

        saveBtn.setOnClickListener {
            var studySub: String = ""
            var recordingDate: Date = Date()
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            var studyTime:Double = 0.0
            var memo:String = ""

            if (!studySubEdit.text.isNullOrEmpty()) {
                studySub = studySubEdit.text.toString()
            }

            if (!dateEdit.text.isNullOrEmpty()) {
                recordingDate = df.parse(dateEdit.text.toString())
            }

            if (!timeEdit.text.isNullOrEmpty()) {
                studyTime = timeEdit.text.toString().toLong().toDouble()
            }

            memo  = memoEdit.text.toString()

            when (bpId) {
                0L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<StudyRecording>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1L
                        val studyRecording = realm.createObject<StudyRecording>(nextId)

                        studyRecording.recordingdate = recordingDate
                        studyRecording.studysub = studySub
                        studyRecording.studytime = studyTime
                        studyRecording.memo = memo
                        studyRecording.dateTime = Date()
                    }
                }
                // 修正処理
                else -> {
                    realm.executeTransaction {
                        val studyRecording = realm.where<StudyRecording>()
                            .equalTo("id", bpId).findFirst()
                        studyRecording?.recordingdate = recordingDate
                        studyRecording?.studysub = studySub
                        studyRecording?.studytime = studyTime
                        studyRecording?.memo = memo
                        studyRecording?.dateTime = Date()
                    }
                }
            }
            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }

        deleteBtn.setOnClickListener {
            realm.executeTransaction {
                val studyRecording = realm.where<StudyRecording>()
                    .equalTo("id", bpId)
                    ?.findFirst()
                    ?.deleteFromRealm()
            }
            Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
