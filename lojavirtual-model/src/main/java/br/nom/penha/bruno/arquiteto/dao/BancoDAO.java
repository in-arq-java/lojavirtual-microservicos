package br.nom.penha.bruno.arquiteto.dao;


import java.util.List;

import br.nom.penha.bruno.arquiteto.model.Banco;

public interface BancoDAO {
    void delete(Banco banco);

    List getAll();

    Banco getByPrimaryKey(Integer id);

    void save(Banco banco);
    
}
