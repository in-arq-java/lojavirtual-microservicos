package br.nom.penha.bruno.arquiteto.dao;

import java.util.List;

import br.nom.penha.bruno.arquiteto.model.BandeiraCartaoCredito;

public interface BandeiraCartaoCreditoDAO {
    void delete(BandeiraCartaoCredito bandeira);

    List getAll();

    BandeiraCartaoCredito getByPrimaryKey(Integer id);

    void save(BandeiraCartaoCredito bandeira);
    
}
