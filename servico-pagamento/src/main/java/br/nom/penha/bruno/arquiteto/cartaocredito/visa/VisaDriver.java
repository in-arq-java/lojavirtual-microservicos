package br.nom.penha.bruno.arquiteto.cartaocredito.visa;

import br.nom.penha.bruno.arquiteto.cartaocredito.Driver;
import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;

public class VisaDriver implements Driver{
    
    @Override
    public boolean aprovaPagamento(PagamentoCartaoCredito pagamento) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    
}
