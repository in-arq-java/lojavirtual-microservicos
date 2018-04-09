package br.nom.penha.bruno.arquiteto.dao;

import java.util.Collection;

import br.nom.penha.bruno.arquiteto.model.Cliente;

public interface ClienteDAO {
    public void delete(Cliente cliente) throws DAOException;

    public Collection<Cliente> findAll() throws DAOException;

    public Cliente findByEmail(String email) throws DAOException;

    public Collection<Cliente> findByNome(String nome) throws DAOException;

    public Cliente findByPrimaryKey(Integer id) throws DAOException;

    public void save(Cliente cliente) throws DAOException;
    
}
