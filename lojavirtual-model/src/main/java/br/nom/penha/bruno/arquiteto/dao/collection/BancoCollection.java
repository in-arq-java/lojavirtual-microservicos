package br.nom.penha.bruno.arquiteto.dao.collection;

import java.util.ArrayList;
import java.util.List;

import br.nom.penha.bruno.arquiteto.dao.BancoDAO;
import br.nom.penha.bruno.arquiteto.dao.DAOException;
import br.nom.penha.bruno.arquiteto.model.Banco;


public class BancoCollection implements BancoDAO {
    
    private final List<Banco> bancos = new ArrayList<>();
    
    private static final BancoCollection instance = new BancoCollection();
    
    protected BancoCollection(){
        bancos.add(new Banco(1,"Banco do Brasil"));
        bancos.add(new Banco(2,"Bradesco"));
        bancos.add(new Banco(3,"Itau"));
    }
    
    public static BancoCollection getInstance() {
        return instance;
    }
    
    protected List getManyByCriteria(String sql) throws DAOException {
        return null;
    }
    
    @Override
    public List getAll(){
        return bancos;
    }
    
    @Override
    public Banco getByPrimaryKey(Integer id){
        Banco banco = null;
        for (Banco bancoDaLista : bancos) {
            if(bancoDaLista.getId().equals(id))
                banco = bancoDaLista;
        }
        return banco;
    }
    
    @Override
    public void delete(Banco banco){
    }
    
    @Override
    public void save(Banco banco){
    }
    
}