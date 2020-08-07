package com.example.listfiltering.model

class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
){
    override fun toString(): String {
        val str: String = this.id.toString() + this.userId.toString() + this.title
        return str
    }
}