package com.uteq.sicoae.model

class Student(
    var matricula: String?,
    var tarjeta: String?,
    var nombre: String?,
    var fechaNacimiento: String?,
    var curp: String?) {

    constructor() : this(
        null,
        null,
        null,
        null,
        null
    )

}