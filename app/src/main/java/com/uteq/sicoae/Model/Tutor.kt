package com.uteq.sicoae.Model

class Tutor(
    var id: Int?,
    var nombre: String?,
    var telefono: String?,
    var user: User?,
    var students: List<Student>?) {

    constructor() : this(
        null,
        null,
        null,
        null,
        null
    )

}