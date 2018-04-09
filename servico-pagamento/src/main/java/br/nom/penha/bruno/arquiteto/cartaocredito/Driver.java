package br.nom.penha.bruno.arquiteto.cartaocredito;

import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;

public interface Driver {
    public boolean aprovaPagamento(PagamentoCartaoCredito pagamento);
}
