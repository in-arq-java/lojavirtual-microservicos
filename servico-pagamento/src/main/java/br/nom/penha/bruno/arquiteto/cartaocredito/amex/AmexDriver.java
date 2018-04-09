package br.nom.penha.bruno.arquiteto.cartaocredito.amex;

import br.nom.penha.bruno.arquiteto.cartaocredito.Driver;
import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;

public class AmexDriver implements Driver{
    
    @Override
    public boolean aprovaPagamento(PagamentoCartaoCredito pagamento) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
