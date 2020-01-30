package com.uteq.sicoae.model

class Tutor(
    var id: Int?,
    var nombre: String?,
    var telefono: String?,
    var user: User?) {

    constructor() : this(
        null,
        null,
        null,
        null
    )

}