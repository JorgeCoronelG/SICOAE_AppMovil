package com.uteq.sicoae.communication

enum class CommunicationPath(val index: Int) {

    SERVER(0),
    LOGIN(1),
    LOGOUT(2),
    RESET_PASSWORD(3),
    UPDATE_TOKEN(4),
    GET_TUTOR(5),
    CHANGE_PASSWORD(6),
    GET_DAY_REGISTER(7),
    ERROR(404);

    fun getPath(): String{
        var path = ""
        when(index){
            SERVER.index -> path = "http://192.168.0.4:8000/api/"
            LOGIN.index -> path = "usuario/login"
            LOGOUT.index -> path = "token/delete/"
            RESET_PASSWORD.index -> path = "usuario/reset/password/"
            UPDATE_TOKEN.index -> path = "token/update"
            GET_TUTOR.index -> path = "tutor/get/"
            CHANGE_PASSWORD.index -> path = "usuario/change/password"
            GET_DAY_REGISTER.index -> path = "registro/find/all"
        }
        if(index != 0) path = SERVER.getPath() + path

        return path
    }

}