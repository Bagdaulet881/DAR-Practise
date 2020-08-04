package com.example.listfiltering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.myquote_list.*
import kotlinx.android.synthetic.main.myquote_list.view.*


class BlankFragment : Fragment {
    private var listener: MyQuoteAdapter.ItemClickListener? = null

    private var clear: Button? = null
    private lateinit var adapter: MyQuoteAdapter
    lateinit var data: Data
    constructor()
    constructor(list: ArrayList<Student>){
        setSortedList(list)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = object : MyQuoteAdapter.ItemClickListener {
            override fun itemClick(position: Int, item: Student?) {
                Toast.makeText(getContext(), "Student ${position}", Toast.LENGTH_SHORT).show()
                val studentDetail: Student
                if(getSortedList().size==0){
                    studentDetail = data.getData2().get(position)
//              if theres no sorted list
                }else{
                    studentDetail = getSortedList().get(position)
                }
                val fragment2: Fragment = StudentDetailFragment(studentDetail)
                fragmentManager?.beginTransaction()?.replace(R.id.main_container, fragment2)
                    ?.commitAllowingStateLoss()
            }


            override fun onClick(v: View?, position: Int, item: Student?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        view.clear?.setOnClickListener(){
            if(getSortedList().size!=0){
                newSortedList.clear()
                Data.savedKeyWord = "empty"
                Toast.makeText(getContext(), "Cleared! size: ${newSortedList.size}", Toast.LENGTH_SHORT).show()
                val fragmentBack: Fragment = BlankFragment()
                fragmentManager?.beginTransaction()?.replace(R.id.main_container, fragmentBack)?.commitAllowingStateLoss()
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(getContext(), "Nothing to clear!", Toast.LENGTH_SHORT).show()

            }
        }
    }

//    noneed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        listener = object : MyQuoteAdapter.ItemClickListener {
//            override fun itemClick(position: Int, item: Student?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                Toast.makeText(getContext(), "Your answer is correct!", Toast.LENGTH_SHORT).show()
//
//            }
//
//            override fun onClick(v: View?, position: Int, item: Student?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        }

    }


    //    noneed
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.myquote_list, container, false)
    }
//    noneed
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment.apply {

            }
    }
//    need
    private var newSortedList: ArrayList<Student> = ArrayList()
    fun getSortedList(): ArrayList<Student>{
        return this.newSortedList
    }
    fun setSortedList(list: ArrayList<Student>){
        this.newSortedList = list
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//TODO change items to getData your func
        data = Data()
        var items: ArrayList<Student>

        if(Data.savedKeyWord != "empty"){
            setSortedList(data.sortingBy(Data.savedKeyWord))
            items = getSortedList()
        }else{
            if(getSortedList().size==0){
                 items = data.getData2()
    //            if theres no sorted list
            }else{
                 items = getSortedList()
            }
        }

        adapter = MyQuoteAdapter(listener)
        adapter.replaceItems(items)
        list.adapter = adapter
    }



}
