package com.example.listfiltering

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import java.net.URL
import com.beust.klaxon.Json
import okhttp3.OkHttpClient
import okhttp3.*

import okhttp3.Request
import java.io.IOException

class Data {
    constructor()
    companion object{
        var savedKeyWord: String = "empty"
        var selectedStudentId: Int = -1
        var savedData: ArrayList<Student> = ArrayList()
    }
    var students: ArrayList<Student> = ArrayList()


    //-------------------------------------SORTING------------------------------------------------------
    fun sortingBy(word: String):ArrayList<Student>{
        val newSortedList: ArrayList<Student> = ArrayList()
        val originalList: ArrayList<Student> = getData2()

        for (i in 0..originalList.size-1){
            val st: Student = originalList.get(i)
            if(st.sorting(word)){
                newSortedList.add(st)
            }
        }
        return newSortedList
    }
//--------------------------------------------------------------------------------------------------
    fun getData2():ArrayList<Student>{
        return students
    }

//---------------------------------------OLD-PARSING------------------------------------------------
    //EZ parsing by json
//    private val client = OkHttpClient()
    //works only in console,  because of android emulator security))))))))))))))))))
//    fun getData():ArrayList<Student>{
//        val myUrl: String = "https://spreadsheets.google.com/feeds/cells/1BDqmMnzyhKPjMK3izSsMBnOiCPLgaHU-IuYRpTsjCRk/1/public/full?alt=json"
//        val result: String = URL(myUrl).readText()
//        //val result = run(myUrl)
//        val parser: com.beust.klaxon.Parser = com.beust.klaxon.Parser()
//        val stringBuilder: StringBuilder = StringBuilder(result)
//        val json: JsonObject = parser.parse(stringBuilder) as JsonObject
//        val json2: JsonObject = json.get("feed") as JsonObject
//        //ARRAY
//        val json3: JsonArray<Any> = json2.get("entry") as JsonArray<Any>
//        //FORic
//        val students: ArrayList<Student> = ArrayList()
//        for (i in 2..json3.size/8){
//            val cnt0: Int = (i-1)*8
//            val cnt: Int = i*8-1
//            val studentData: ArrayList<String> = ArrayList()
//
//            for (j in cnt0..cnt){
//                //  8->15
//                val json4: JsonObject = json3.get(j) as JsonObject
//                val jsonContent: JsonObject = json4.get("content") as JsonObject
//                val str: String = jsonContent.get("\$t").toString()
//                studentData.add(str)
////            print(str)
//            }
//            val newStudent = Student(studentData.get(0),studentData.get(1),studentData.get(2),
//                studentData.get(3),studentData.get(4),studentData.get(5),studentData.get(6),studentData.get(7))
//
//            students.add(newStudent)
////    println()
//        }
//        return students
//    }
//    fun run(url: String):String {
//        var res:String="nothing"
//        val request = Request.Builder()
//            .url(url)
//            .build()
//       client.newCall(request).execute().use { response ->
//            res = response.body()!!.string()
//        }
//        return res
//    }
}