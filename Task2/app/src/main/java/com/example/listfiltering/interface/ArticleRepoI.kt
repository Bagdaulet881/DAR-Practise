package com.example.listfiltering.`interface`

import com.example.listfiltering.model.Student
import io.reactivex.Single

interface ArticleRepoI {
    fun getStudentsObs(): Single<List<Student>>
}