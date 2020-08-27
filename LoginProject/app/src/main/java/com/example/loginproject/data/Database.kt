package com.example.loginproject.data

import com.example.loginproject.data.network.ClientInfo

class Database {
    lateinit var clientInfo: ClientInfo

    fun getClientInfo(): Any{
        return clientInfo
    }
    fun setClientInfo(clt: Any){
        clientInfo = clt as ClientInfo
    }
//TODO sharedPref

}