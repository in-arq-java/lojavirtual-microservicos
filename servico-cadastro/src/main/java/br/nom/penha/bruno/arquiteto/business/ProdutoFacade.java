package br.nom.penha.bruno.arquiteto.business;

import static br.nom.penha.bruno.arquiteto.business.events.TipoEventoProduto.*;

import java.io.Serializable;
import java.util.Collection;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import br.nom.penha.bruno.arquiteto.business.events.EventoProduto;
import br.nom.penha.bruno.arquiteto.dao.ProdutoDAO;
import br.nom.penha.bruno.arquiteto.model.Produto;

/**
 *
 * @author Arquiteto
 */
@ConversationScoped
public class ProdutoFacade implements Serializable {

    @EJB
    private ProdutoDAO produtoDAO;

    @Inject
    private Event<EventoProduto> eventoProduto;

    public Collection<Produto> findAll() {
        return produtoDAO.findAll();
    }

    public void save(Produto produto) {
        //normaliza o Id do produto para null. Isso permite a recuperação do id gerado no caso de inserção
        if(produto.getId()!= null && produto.getId() == 0) {
            produto.setId(null);
        } 
        EventoProduto evento = produto.getId() == null ? new EventoProduto(INSERIDO, produto) : new EventoProduto(ALTERADO, produto);
        produtoDAO.save(produto);
        eventoProduto.fire(evento);
    }

    public void delete(Produto produto) {
        produtoDAO.delete(produto);
        eventoProduto.fire(new EventoProduto(REMOVIDO, produto));
    }

    public Produto findByPrimaryKey(Integer id) {
        return produtoDAO.findByPrimaryKey(id);
    }
}
