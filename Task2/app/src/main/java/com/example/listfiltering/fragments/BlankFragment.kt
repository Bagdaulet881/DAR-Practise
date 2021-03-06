package com.example.listfiltering.fragments

import android.app.SharedElementCallback
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.listfiltering.Data
import com.example.listfiltering.MyQuoteAdapter
import com.example.listfiltering.R
import com.example.listfiltering.`interface`.ArticleContract
import com.example.listfiltering.model.Student
import com.example.listfiltering.presenter.Provider
import kotlinx.android.synthetic.main.myquote_list.*
import kotlinx.android.synthetic.main.myquote_list.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class BlankFragment : Fragment, ArticleContract.View {
    private var listener: MyQuoteAdapter.ItemClickListener? = null

//    var presenter: ArticleContract.Presenter = Provider().getArticlePresenter(this)
    private val presenter: ArticleContract.Presenter by inject{ parametersOf(this)}

    private var clear: Button? = null
    private lateinit var adapter: MyQuoteAdapter
    lateinit var data: Data
    var lastClickedPosition = 0
    constructor()
    constructor(list: ArrayList<Student>){
        setSortedList(list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        info("check view")
        presenter.getStudents()
//----------------------------------------LISTENERS-------------------------------------------------

        listener = object :
            MyQuoteAdapter.ItemClickListener {
            override fun itemClick(position: Int, item: Student?) {
                Toast.makeText(getContext(), "Student ${position+1}", Toast.LENGTH_SHORT).show()
                lastClickedPosition = position
                val studentDetail: Student
                if(getSortedList().size==0){
                    studentDetail = data.getData2().get(position)
//              if theres no sorted list
                }else{
                    studentDetail = getSortedList().get(position)
                }
                Data.selectedStudent = studentDetail
                val fragment2: Fragment =
                    StudentDetailFragment(
                    )
                val transitionView = view.findViewById(R.id.emailView) as TextView
                fragmentManager?.beginTransaction()
                    ?.setReorderingAllowed(true)
                    ?.addSharedElement(transitionView, transitionView.transitionName)
//                    ?.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right)
                    ?.replace(R.id.main_container, fragment2)
                    ?.addToBackStack(null)
                    ?.commit()
//                    ?.commitAllowingStateLoss()
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
//                val fragmentBack: Fragment = BlankFragment()
//                fragmentManager?.beginTransaction()?.replace(R.id.main_container, fragmentBack)?.commitAllowingStateLoss()
//                adapter.notifyDataSetChanged()
                adapter.replaceItems(data.getData2())
            }else{
                Toast.makeText(getContext(), "Nothing to clear!", Toast.LENGTH_SHORT).show()

            }
        }
    }

//------------------------------------------------------------------------------------FOR-TRANSITION


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        prepareTransition()
        postponeEnterTransition()
        return inflater.inflate(R.layout.myquote_list, container, false)
    }

//------------------------------------------NEW-SORTED-LIST-----------------------------------------
    private var newSortedList: ArrayList<Student> = ArrayList()
    fun getSortedList(): ArrayList<Student>{
        return this.newSortedList
    }
    fun setSortedList(list: ArrayList<Student>){
        this.newSortedList = list
    }
//--------------------------------------------------------------------------------------------------

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        data = Data()
        info("check present")

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
        info("check activity")
    }
//-------------------------------------DOWNLOAD-DATA-------------------------------------------------
    override fun showStudents(students: List<Student>) {
        data.students = students as ArrayList<Student>
        Log.i("MSG","Show studentsUPD ${data.students.toString()}")
        var items: ArrayList<Student>

        if(Data.savedKeyWord != "empty"){
            setSortedList(data.sortingBy(Data.savedKeyWord))
            items = getSortedList()
        }else{
            if(getSortedList().size==0){
                items = data.getData2()
                //if theres no sorted list
            }else{
                items = getSortedList()
            }
        }
        adapter.replaceItems(items)
        list.adapter = adapter

        startPostponedEnterTransition()
    }
//-----------------------------------------DESIGN---------------------------------------------------
    private fun prepareTransition(){
    exitTransition = TransitionInflater.from(context)
        .inflateTransition(R.transition.grid_exit_transation)

    setExitSharedElementCallback(object: androidx.core.app.SharedElementCallback(){
        override fun onMapSharedElements(
            names: List<String>,
            sharedElements: MutableMap<String, View>
        ) {
            val selectedViewHolder: RecyclerView.ViewHolder = list.findViewHolderForAdapterPosition(lastClickedPosition)!!

            sharedElements[names[0]] = selectedViewHolder.itemView.findViewById(R.id.emailView)
        }
    })
}
    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }
    private fun info(msg: String){
        Log.i("MSG","$msg")
    }
}
