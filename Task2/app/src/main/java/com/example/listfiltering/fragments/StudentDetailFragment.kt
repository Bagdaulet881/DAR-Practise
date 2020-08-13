package com.example.listfiltering.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.listfiltering.Data
import com.example.listfiltering.R
import com.example.listfiltering.model.Student
import kotlinx.android.synthetic.main.fragment_student_detail.view.*


/**
 * A simple [Fragment] subclass.
 */
class StudentDetailFragment : Fragment {
    var data = Data()
    var student = Data.selectedStudent

    constructor(){
    }
    private var name: TextView? = null
    private var lastname: TextView? = null
    private var email: TextView? = null
    private var textView7: TextView? = null
    private var textView8: TextView? = null
    private var textView9: TextView? = null
    private var textView10: TextView? = null
    private var textView11: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        name = findViewById<View>(R.id.name) as TextView
        val view = inflater.inflate(R.layout.fragment_student_detail, container, false)
        view.name.setText(student.name)
        view.lastname.setText(student.surname)
        view.email.setText(student.email)
        view.textView7.setText(student.age.toString())
        view.textView8.setText(student.city)
        view.textView9.setText(student.univer)
        view.textView10.setText(student.hobby)
        view.textView11.setText(student.dream)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.textView8?.setOnClickListener(){
            Toast.makeText(getContext(), "Students from ${student.city}", Toast.LENGTH_SHORT).show()
            Data.savedKeyWord = student.city
            val fragmentBack: Fragment =
                BlankFragment(
                    data.sortingBy(student.city)
                )
            fragmentManager?.beginTransaction()?.replace(R.id.main_container, fragmentBack)?.commitAllowingStateLoss()
        }
        view.textView9?.setOnClickListener(){
            Toast.makeText(getContext(), "Students of ${student.univer}", Toast.LENGTH_SHORT).show()
            Data.savedKeyWord = student.univer
            val fragmentBack: Fragment =
                BlankFragment(
                    data.sortingBy(student.univer)
                )
            fragmentManager?.beginTransaction()?.replace(R.id.main_container, fragmentBack)?.commitAllowingStateLoss()
        }
    }


}
