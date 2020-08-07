package com.example.mvparticle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvparticle.model.Post
import com.example.mvparticle.model.Student
import com.example.mvparticle.presenter.ArticlePresenter
import com.example.mvparticle.presenter.ArticleView

/**
 * A simple [Fragment] subclass.
 */
class ArticleFragment : Fragment(), ArticleView {

    val presenter = ArticlePresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        info("onViewCreated")
        presenter.getStudents()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }
    override fun showStudents(students: List<Student>) {
        Log.i("MSG","Show students ${students.toString()}")
    }
    private fun info(msg: String){
        Log.i("MSG","$msg")
    }

}
