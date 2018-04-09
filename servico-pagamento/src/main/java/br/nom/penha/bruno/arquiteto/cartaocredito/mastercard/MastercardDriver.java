package br.nom.penha.bruno.arquiteto.cartaocredito.mastercard;

import br.nom.penha.bruno.arquiteto.cartaocredito.Driver;
import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;

public class MastercardDriver implements Driver{
    
    @Override
    public boolean aprovaPagamento(PagamentoCartaoCredito pagamento) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return true;
    }

}
