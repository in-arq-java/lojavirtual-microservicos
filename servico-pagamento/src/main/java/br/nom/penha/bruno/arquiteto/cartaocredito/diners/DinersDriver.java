package br.nom.penha.bruno.arquiteto.cartaocredito.diners;

import br.nom.penha.bruno.arquiteto.cartaocredito.Driver;
import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;

public class DinersDriver implements Driver{
    
    @Override
    public boolean aprovaPagamento(PagamentoCartaoCredito pagamento) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
