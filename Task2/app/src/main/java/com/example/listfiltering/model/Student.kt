package com.example.listfiltering.model

data class Student(val id:Int,val name:String, val surname:String, val email:String, val age:Int,
              val city:String, val univer:String, val hobby:String, val dream:String) {

    override fun toString(): String {
        return "Student(name='$name', lastname='$surname', email='$email', age=$age, city='$city', university='$univer', hobby='$hobby', dream='$dream')"
    }

    fun sorting(keyWord: String): Boolean{
        if(this.city.equals(keyWord)){
            return true
        }else if(this.univer.equals(keyWord)){
                return true
            }else
                return false
    }

}