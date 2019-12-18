package com.uteq.sicoae.model

class Reference(
    var id: String?,
    var fecha: String?,
    var estatus: Int?,
    var persona: String?,
    var student: Student?) {

    constructor() : this(
        null,
        null,
        null,
        null,
        null
    )
}