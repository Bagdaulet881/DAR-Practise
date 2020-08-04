package com.example.listfiltering

data class Student(val name:String, val lastname:String, val email:String, val age:String,
              val city:String, val university:String, val hobby:String, val dream:String) {

    override fun toString(): String {
        return "Student(name='$name', lastname='$lastname', email='$email', age=$age, city='$city', university='$university', hobby='$hobby', dream='$dream')"
    }

    fun sorting(keyWord: String): Boolean{
        if(this.city.equals(keyWord)){
            return true
        }else if(this.university.equals(keyWord)){
                return true
            }else
                return false
    }

}