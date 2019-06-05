package br.com.rubensrodrigues.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formatoParaBrasileiro() : String{
    val formatoBrasilerio = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBrasilerio)
    return format.format(this.time)
}