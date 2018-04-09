package br.nom.penha.bruno.arquiteto.dao.jpa;

import java.util.Collection;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateful;
import javax.inject.Named;

import br.nom.penha.bruno.arquiteto.dao.ClienteDAO;
import br.nom.penha.bruno.arquiteto.dao.DAOException;
import br.nom.penha.bruno.arquiteto.model.Cliente;

@Named("clienteDAO")
@Stateful
public class ClienteJPA extends DaoSupport<Cliente,Integer>
        implements ClienteDAO {
    
    @Override
    public void save(Cliente cliente) {
        super.save(cliente);
    }
    
    @Override
    public void delete(Cliente cliente) {
        super.delete(cliente);
    }
    
    
    @Override
    public Collection<Cliente> findByNome(String nome) throws DAOException{
        String jpql = "select cli from Cliente cli where cli.nome like :nome";
        Map params = new HashMap();
        params.put("nome",nome + "%");
        return findByQuery(jpql, params);
    }
    
    @Override
    public Cliente findByEmail(String email) throws DAOException{
        String jpql = "select cli from Cliente cli where cli.email = :email";
        Map params = new HashMap();
        params.put("email",email);
        return findSingleByQuery(jpql, params);
    }
    
    
}

