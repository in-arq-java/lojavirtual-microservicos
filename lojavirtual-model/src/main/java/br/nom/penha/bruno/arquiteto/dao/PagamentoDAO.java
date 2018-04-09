package br.nom.penha.bruno.arquiteto.dao;

import java.util.Collection;

import br.nom.penha.bruno.arquiteto.model.Pagamento;

public interface PagamentoDAO {
    void delete(Pagamento pagamento) throws DAOException;

    Collection<Pagamento> findAll() throws DAOException;

    Pagamento findByPrimaryKey(Integer id) throws DAOException;

    void save(Pagamento pagamento) throws DAOException;
    
}
