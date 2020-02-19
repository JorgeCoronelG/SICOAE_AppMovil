package com.uteq.sicoae.model

class Tutor(
    var id: Int? = null,
    var nombre: String? = null,
    var telefono: String? = null,
    var user: User? = null,
    var students: ArrayList<Student>? = null)