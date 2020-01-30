package com.uteq.sicoae.model

class Student(
    var matricula: String?,
    var tarjeta: String?,
    var nombre: String?,
    var grado: Int?,
    var grupo: String?,
    var tutor: Tutor?) {

    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null
    )

}