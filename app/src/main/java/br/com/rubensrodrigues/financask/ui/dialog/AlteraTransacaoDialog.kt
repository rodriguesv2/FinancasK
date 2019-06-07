package br.com.rubensrodrigues.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.rubensrodrigues.financask.R
import br.com.rubensrodrigues.financask.extension.formatoParaBrasileiro
import br.com.rubensrodrigues.financask.model.Tipo
import br.com.rubensrodrigues.financask.model.Transacao

class AlteraTransacaoDialog(
    viewGroup: ViewGroup,
    private val context: Context) : FormularioTransacaoDialog(context, viewGroup){

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.altera_receita
        } else {
            R.string.altera_despesa
        }
    }

    fun chama(transacao: Transacao, delegate: (transacao: Transacao) -> Unit) {
        val tipo = transacao.tipo
        super.chama(tipo, delegate)

        inicializaCampos(transacao)
    }

    private fun inicializaCampos(transacao: Transacao) {
        val tipo = transacao.tipo

        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(tipo, transacao)
    }

    private fun inicializaCampoCategoria(
        tipo: Tipo,
        transacao: Transacao
    ) {
        val categoriaRetornadas = context.resources.getStringArray(categoriasPor(tipo))
        val posicaoCategoria = categoriaRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formatoParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }
}