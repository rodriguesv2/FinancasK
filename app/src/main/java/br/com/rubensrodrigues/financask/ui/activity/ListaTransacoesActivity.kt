package br.com.rubensrodrigues.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import br.com.rubensrodrigues.financask.R
import br.com.rubensrodrigues.financask.delegate.TransacaoDelegate
import br.com.rubensrodrigues.financask.model.Tipo
import br.com.rubensrodrigues.financask.model.Transacao
import br.com.rubensrodrigues.financask.ui.ResumoView
import br.com.rubensrodrigues.financask.ui.adapter.ListaTransacoesAdapter
import br.com.rubensrodrigues.financask.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogDeAdicao(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogDeAdicao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
            .chama(tipo, object : TransacaoDelegate {
                override fun delegate(transacaoCriada: Transacao) {
                    atualizaTransacoes(transacaoCriada)
                    lista_transacoes_adiciona_menu.close(true)
                }

            })
    }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view: View = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

//    private fun transacoesDeExemplo(): List<Transacao> {
//        return listOf(
//            Transacao(valor = BigDecimal(20.5), tipo = Tipo.DESPESA),
//            Transacao(BigDecimal(135.0), "Economia", Tipo.RECEITA),
//            Transacao(BigDecimal(415.0), "Freela da padoca", Tipo.RECEITA),
//            Transacao(BigDecimal(200.0), "Motel com a minha gostosa", Tipo.DESPESA)
//        )
//    }
}