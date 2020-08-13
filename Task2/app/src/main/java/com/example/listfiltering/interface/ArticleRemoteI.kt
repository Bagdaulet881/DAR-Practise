package com.example.listfiltering.`interface`

import com.example.listfiltering.model.Student
import io.reactivex.Single

interface ArticleRemoteI {
    fun downloadStudentsObs(): Single<List<Student>>
}
