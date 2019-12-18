package com.uteq.sicoae.Model

class Student(
    var id: Int?,
    var card: String?,
    var matricula: Int?,
    var nombre: String?,
    var fechaNacimiento: String?,
    var curp: String?) {

    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null
    )

}