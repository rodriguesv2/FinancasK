package br.com.rubensrodrigues.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import br.com.rubensrodrigues.financask.R
import br.com.rubensrodrigues.financask.delegate.TransacaoDelegate
import br.com.rubensrodrigues.financask.extension.converteParaCalendar
import br.com.rubensrodrigues.financask.extension.formatoParaBrasileiro
import br.com.rubensrodrigues.financask.model.Tipo
import br.com.rubensrodrigues.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(private val viewGroup: ViewGroup,
                              private val context: Context) {

    private val viewCriada = criaLayout()

    private val campoValor = viewCriada.form_transacao_valor
    private val campoCategoria = viewCriada.form_transacao_categoria
    private val campoData = viewCriada.form_transacao_data

    fun chama(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)
    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton("Adicionar", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val valorEmTexto = campoValor.text.toString()
                    val dataEmTexto = campoData.text.toString()
                    val categoriaEmTexto = campoCategoria.selectedItem.toString()

                    val valor = converteCampoValor(valorEmTexto)

                    val data = dataEmTexto.converteParaCalendar()

                    val transacaoCriada =
                        Transacao(tipo = tipo, valor = valor, data = data, categoria = categoriaEmTexto)

                    transacaoDelegate.delegate(transacaoCriada)
                }
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }
    }

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, "Falha na conversão de valor", Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {
        val categoria = categoriasPor(tipo)

        val adapter = ArrayAdapter.createFromResource(
            context,
            categoria,
            android.R.layout.simple_spinner_dropdown_item
        )
        campoCategoria.adapter = adapter
    }

    private fun categoriasPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.array.categorias_de_receita
        } else {
            R.array.categorias_de_despesa
        }
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formatoParaBrasileiro())

        campoData.setOnClickListener {
            DatePickerDialog(context, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    val dataSeleciona = Calendar.getInstance()
                    dataSeleciona.set(year, month, dayOfMonth)

                    campoData.setText(dataSeleciona.formatoParaBrasileiro())
                }

            }, ano, mes, dia).show()
        }
    }

    private fun criaLayout() : View{
        return LayoutInflater.from(context).inflate(R.layout.form_transacao, viewGroup, false)
    }
}