package br.nom.penha.bruno.arquiteto.cartaocredito;

import br.nom.penha.bruno.arquiteto.cartaocredito.amex.AmexDriver;
import br.nom.penha.bruno.arquiteto.cartaocredito.diners.DinersDriver;
import br.nom.penha.bruno.arquiteto.cartaocredito.mastercard.MastercardDriver;
import br.nom.penha.bruno.arquiteto.cartaocredito.visa.VisaDriver;
import br.nom.penha.bruno.arquiteto.exception.LojavirtualException;
import br.nom.penha.bruno.arquiteto.model.BandeiraCartaoCredito;

public class DriverFactory {
    
    private static final DriverFactory instance = new DriverFactory();
    
    private DriverFactory() {
    }
    
    public static DriverFactory getInstance() {
        return instance;
    }
    
    public Driver getDriver(BandeiraCartaoCredito bandeira) throws LojavirtualException{
        Driver driver = null;
        String nome = bandeira.getNome();
        if(nome == null) {
            throw new LojavirtualException("Nao foi fornecido o nome da bandeira do cartao de credito");
        }
        if("Visa".equals(nome)) {
            driver = new VisaDriver();
        }
        else if("Mastercard".equals(nome)) {
            driver = new MastercardDriver();
        }
        else if("American Express".equals(nome)) {
            driver = new AmexDriver();
        }
        else if("Diners".equals(nome)) {
            driver = new DinersDriver();
        }
        
        return driver;
        
    }
}
