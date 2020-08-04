package com.example.listfiltering

import com.beust.klaxon.Json
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL
//--------------------------------PARSING-from-GOOGLE-SHEETS----------------------------------------
//EZ parsing by json
fun getData():ArrayList<Student>{
    val myUrl: String = "https://spreadsheets.google.com/feeds/cells/1BDqmMnzyhKPjMK3izSsMBnOiCPLgaHU-IuYRpTsjCRk/1/public/full?alt=json"
//    val result = URL(myUrl).readText()
    val result = run(myUrl)
    val parser: com.beust.klaxon.Parser = com.beust.klaxon.Parser()
    val stringBuilder: StringBuilder = StringBuilder(result)
    val json: JsonObject = parser.parse(stringBuilder) as JsonObject
    val json2: JsonObject = json.get("feed") as JsonObject
    //ARRAY
    val json3: JsonArray<Any> = json2.get("entry") as JsonArray<Any>
    //FORic
    val students: ArrayList<Student> = ArrayList()
    for (i in 2..json3.size/8){
        val cnt0: Int = (i-1)*8
        val cnt: Int = i*8-1
        val studentData: ArrayList<String> = ArrayList()

        for (j in cnt0..cnt){
            //  8->15
            val json4: JsonObject = json3.get(j) as JsonObject
            val jsonContent: JsonObject = json4.get("content") as JsonObject
            val str: String = jsonContent.get("\$t").toString()
            studentData.add(str)
//            print(str)
        }
        val newStudent = Student(studentData.get(0),studentData.get(1),studentData.get(2),
            studentData.get(3),studentData.get(4),studentData.get(5),studentData.get(6),studentData.get(7))

        students.add(newStudent)
//    println()
    }
    return students
}
//-------------------------------------SORTING------------------------------------------------------
fun sortingBy(word: String):ArrayList<Student>{
    val newSortedList: ArrayList<Student> = ArrayList()
    val originalList: ArrayList<Student> = getData()

    for (i in 0..originalList.size-1){
        val st: Student = originalList.get(i)
        if(st.sorting(word)){
            newSortedList.add(st)
        }
    }
    return newSortedList
}
//--------------------------------------------------------------------------------------------------
private val client = OkHttpClient()

fun run(url: String):String {
    var res:String="nothing"
    val request = Request.Builder()
        .url(url)
        .build()



    client.newCall(request).execute().use { response ->
//        if (!response.isSuccessful) throw IOException("Unexpected code $response")

//        for ((name, value) in response.headers) {
//            println("$name: $value")
//        }

       res = response.body()!!.string()
    }
    return res
}
fun main(array: Array<String>){

//    val originalList2: ArrayList<Student> = getData()

//    print(sortingBy("Almaty").size)
//lateinit var data: Data
//    data = Data()
//    print(data.getData().toString())

    val myUrl: String = "https://spreadsheets.google.com/feeds/cells/1BDqmMnzyhKPjMK3izSsMBnOiCPLgaHU-IuYRpTsjCRk/1/public/full?alt=json"
    print(run(myUrl))

}