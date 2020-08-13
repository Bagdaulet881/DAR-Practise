package com.example.listfiltering

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.listfiltering.fragments.BlankFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //--------------------------------------------------------------------------------------------------
        val fragment: Fragment =
            BlankFragment()

        supportFragmentManager
            .beginTransaction() //
            .replace(R.id.main_container, fragment)
            .commitAllowingStateLoss()


    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        super.onRestoreInstanceState(savedInstanceState)
        Data.savedKeyWord = savedInstanceState?.getString("keyword")!!
        Data.selectedStudent = Data.getObjectFromJson(savedInstanceState?.getString("studentString")!!)
    }
//TODO make student parceable

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("keyword", Data.savedKeyWord.toString())
        outState.putString("studentString", Data.getJsonString(Data.selectedStudent))
        super.onSaveInstanceState(outState)

    }
}
