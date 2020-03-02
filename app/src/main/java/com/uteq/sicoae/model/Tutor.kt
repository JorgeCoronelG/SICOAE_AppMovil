package com.uteq.sicoae.model

class Tutor(
    var id: Int? = null,
    var nombre: String? = null,
    var telefono: String? = null,
    var usuario: User? = null,
    var estudiantes: ArrayList<Student>? = null)