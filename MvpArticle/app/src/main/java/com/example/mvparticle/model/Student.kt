package com.example.mvparticle.model

class Student(val name:String, val surname:String, val email:String, val age: Int,
                   val city:String, val univer:String, val hobby:String, val dream:String)
{
    override fun toString(): String {
        return "Student(name='$name', lastname='$surname', email='$email', age=$age, city='$city', university='$univer', hobby='$hobby', dream='$dream')"
    }


}