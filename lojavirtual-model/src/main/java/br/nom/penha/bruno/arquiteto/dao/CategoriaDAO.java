package br.nom.penha.bruno.arquiteto.dao;

import java.util.Collection;

import br.nom.penha.bruno.arquiteto.model.Categoria;

public interface CategoriaDAO {
    void delete(Categoria categoria) throws DAOException;

    Collection<Categoria> findAll() throws DAOException;

    Categoria findByPrimaryKey(Integer id) throws DAOException;

    void save(Categoria categoria) throws DAOException;
    
}
