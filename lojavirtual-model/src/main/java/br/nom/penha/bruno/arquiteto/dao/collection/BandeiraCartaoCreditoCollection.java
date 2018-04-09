package br.nom.penha.bruno.arquiteto.dao.collection;

import java.util.ArrayList;
import java.util.List;

import br.nom.penha.bruno.arquiteto.dao.BandeiraCartaoCreditoDAO;
import br.nom.penha.bruno.arquiteto.model.BandeiraCartaoCredito;


public class BandeiraCartaoCreditoCollection implements BandeiraCartaoCreditoDAO {
    
    private final List<BandeiraCartaoCredito> bandeiras = new ArrayList<>();
    
    private static final BandeiraCartaoCreditoCollection instance = new BandeiraCartaoCreditoCollection();
    
    protected BandeiraCartaoCreditoCollection(){
        bandeiras.add(new BandeiraCartaoCredito(1,"Visa"));
        bandeiras.add(new BandeiraCartaoCredito(2,"Mastercard"));
        bandeiras.add(new BandeiraCartaoCredito(3,"American Express"));
        bandeiras.add(new BandeiraCartaoCredito(4,"Diners"));
    }
    
    public static BandeiraCartaoCreditoCollection getInstance() {
        return instance;
    }
    
    @Override
    public List getAll(){
        return bandeiras;
    }
    
    @Override
    public BandeiraCartaoCredito getByPrimaryKey(Integer id){
        BandeiraCartaoCredito bandeira = null;
        for (BandeiraCartaoCredito bandeiraDaLista : bandeiras) {
            if(bandeiraDaLista.getId().equals(id))
                bandeira = bandeiraDaLista;
        }
        return bandeira;
    }
    
    @Override
    public void delete(BandeiraCartaoCredito bandeira){
    }
    
    @Override
    public void save(BandeiraCartaoCredito bandeira){
    }
    
}