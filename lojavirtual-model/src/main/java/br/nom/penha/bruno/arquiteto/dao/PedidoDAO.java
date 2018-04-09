package br.nom.penha.bruno.arquiteto.dao;

import java.util.Collection;

import br.nom.penha.bruno.arquiteto.model.Cliente;
import br.nom.penha.bruno.arquiteto.model.Pedido;

public interface PedidoDAO {
    void delete(Pedido pedido) throws DAOException;

    Collection<Pedido> findAll() throws DAOException;

    Collection<Pedido> findByCliente(Cliente cliente) throws DAOException;

    Collection<Pedido> findByPeriodo(String strDataInicio, String strDataFinal) throws DAOException;

    Pedido findByPrimaryKey(Long numero) throws DAOException;

    void save(Pedido pedido) throws DAOException;
    
}
