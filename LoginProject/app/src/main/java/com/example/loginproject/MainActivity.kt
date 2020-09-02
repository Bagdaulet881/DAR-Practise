package com.example.loginproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.loginproject.data.Database
import com.example.loginproject.main.login.LoginFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

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
//        var str = savedInstanceState?.getString("database")
//        Log.i("MSG", "json " + gson.fromJson(str, Database::class.java))
//        db = gson.fromJson(str, Database::class.java)
        db.isTimeLeft = savedInstanceState.getBoolean("timeBool")
        db.remainingTime = savedInstanceState.getInt("time")

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        val json = gson.toJson(db)
//        outState.putString("database", json)
//        Log.i("MSG", "json " + json)
        outState.putInt("time", db.remainingTime)
        outState.putBoolean("timeBool", db.isTimeLeft)
        super.onSaveInstanceState(outState, outPersistentState)

    }

}
