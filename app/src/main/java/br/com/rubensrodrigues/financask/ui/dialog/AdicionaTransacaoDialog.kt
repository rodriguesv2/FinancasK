package br.com.rubensrodrigues.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.rubensrodrigues.financask.R
import br.com.rubensrodrigues.financask.model.Tipo

class AdicionaTransacaoDialog(viewGroup: ViewGroup,
                              context: Context) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }
    }
}