package com.uteq.sicoae.Model

class User(
    var correo: String?,
    var clave: String?,
    var tipoUsuario: String?,
    var estatus: Int?) {

    constructor() : this(
        null,
        null,
        null,
        null
    )

}