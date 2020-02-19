package com.uteq.sicoae.communication

enum class CommunicationPath(val index: Int) {

    SERVER(1),
    LOGIN(2),
    ERROR(404);

    fun getPath(): String{
        var path = ""
        when(index){
            SERVER.index -> path = "http://192.168.0.7:8000/api/"
            LOGIN.index -> path = "usuario/login"
        }
        return path
    }

}