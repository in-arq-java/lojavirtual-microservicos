package br.nom.penha.bruno.arquiteto.dao.jpa;

import javax.ejb.Stateful;
import javax.inject.Named;

import br.nom.penha.bruno.arquiteto.dao.CategoriaDAO;
import br.nom.penha.bruno.arquiteto.model.Categoria;

@Named("categoriaDAO")
@Stateful
public class CategoriaJPA extends DaoSupport<Categoria,Integer>
        implements CategoriaDAO {
    
    @Override
    public void save(Categoria categoria) {
        super.save(categoria);
    }
    
    @Override
    public void delete(Categoria categoria) {
        super.delete(categoria);
    }
    
}
