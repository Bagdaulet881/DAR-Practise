package com.example.loginproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.loginproject.data.Database
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    companion object{
        var db = Database()
        val gson = Gson()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)
        db.isTimeLeft = savedInstanceState.getBoolean("timeBool")
        db.remainingTime = savedInstanceState.getInt("time")

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt("time", db.remainingTime)
        outState.putBoolean("timeBool", db.isTimeLeft)
        super.onSaveInstanceState(outState, outPersistentState)

    }

}
