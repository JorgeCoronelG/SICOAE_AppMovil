package com.uteq.sicoae.Model

class Reference(
    var id: String?,
    var fecha: String?,
    var estatus: Int?,
    var student: Student?) {

    constructor() : this(
        null,
        null,
        null,
        null
    )
}