package br.com.rubensrodrigues.financask.delegate

import br.com.rubensrodrigues.financask.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacaoCriada: Transacao)
}