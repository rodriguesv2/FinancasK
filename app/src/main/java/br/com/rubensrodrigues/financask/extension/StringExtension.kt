package br.com.rubensrodrigues.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracteres: Int) : String{
    if (this.length > caracteres) {
        val startIndex = 0
        return "${this.substring(startIndex, caracteres)}..."
    }
    return this;
}

fun String.converteParaCalendar() : Calendar {
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}