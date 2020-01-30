package com.uteq.sicoae.communication

enum class CommunicationPath(val index: Int) {

    SERVER(1),
    LOGIN(2);

    fun getPath(): String{
        var path = ""
        when(index){
            1 -> path = "http://192.168.0.9/SCAE/"
            2 -> path = "UserC/login"
        }
        return path
    }

}