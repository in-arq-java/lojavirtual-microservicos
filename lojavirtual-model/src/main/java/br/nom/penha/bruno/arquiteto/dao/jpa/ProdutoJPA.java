package br.nom.penha.bruno.arquiteto.dao.jpa;

import java.util.Collection;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateful;
import javax.inject.Named;

import br.nom.penha.bruno.arquiteto.dao.DAOException;
import br.nom.penha.bruno.arquiteto.dao.ProdutoDAO;
import br.nom.penha.bruno.arquiteto.model.Categoria;
import br.nom.penha.bruno.arquiteto.model.Produto;

@Named("produtoDAO")
@Stateful
public class ProdutoJPA extends DaoSupport<Produto, Integer>
        implements ProdutoDAO {

    @Override
    public void save(Produto produto) {
        super.save(produto);
    }

    @Override
    public void delete(Produto produto) {
        super.delete(produto);
    }

    @Override
    public Collection<Produto> findByMarca(String marca) throws DAOException {
        String jpql = "select p from Produto p where marca like :marca";
        Map params = new HashMap();
        params.put("marca", marca + "%");
        return findByQuery(jpql, params);
    }

    @Override
    public Collection<Produto> findByCategoria(Categoria categoria) throws DAOException {
        String jpql = "select p from Produto p where p.categoria = :categoria";
        Map params = new HashMap();
        params.put("categoria", categoria);
        return findByQuery(jpql, params);
    }

    @Override
    public Collection<Produto> findProdutos(String nome, Integer idCategoria, String marca) throws DAOException {

        String conector = " where ";
        String jpql = "select p from Produto p";
        Map params = new HashMap();

        if (nome != null && !nome.equals("")) {
            jpql += conector + " p.nome like :nome ";
            params.put("nome", nome + "%");
            if (conector.trim().equals("where")) {
                conector = " and ";
            }
        }
        if (idCategoria != null && idCategoria.intValue() != 0) {
            jpql += conector + " p.categoria.id = :idCategoria ";
            params.put("idCategoria", idCategoria);
            if (conector.trim().equals("where")) {
                conector = " and ";
            }
        }
        if (marca != null && !marca.equals("")) {
            jpql += conector + " p.marca like :marca ";
            params.put("marca", marca + "%");
        }

        return findByQuery(jpql, params);
    }
}
