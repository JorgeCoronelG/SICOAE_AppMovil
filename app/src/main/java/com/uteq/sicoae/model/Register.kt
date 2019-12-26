package com.uteq.sicoae.model

class Register(
    var id: Int?,
    var fecha: String?,
    var entrada: String?,
    var salida: String?,
    var student: Student?){

    constructor() : this(
        null,
        null,
        null,
        null,
        null
    )

}