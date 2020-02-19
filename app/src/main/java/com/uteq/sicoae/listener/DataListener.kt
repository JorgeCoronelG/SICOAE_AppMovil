package com.uteq.sicoae.listener

interface DataListener {

    fun success(code: Int, obj: Object?)
    fun error(error: String)

}