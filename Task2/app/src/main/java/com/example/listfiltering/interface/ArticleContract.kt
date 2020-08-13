package com.example.listfiltering.`interface`

import com.example.listfiltering.model.Student

interface ArticleContract {
    interface Presenter{
        fun getStudents()
        fun destroy()
    }

    interface View{
        fun showStudents(posts: List<Student>)
    }
}