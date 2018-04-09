package br.nom.penha.bruno.arquiteto.dao.jpa;

import javax.ejb.Stateful;
import javax.inject.Named;

import br.nom.penha.bruno.arquiteto.dao.PagamentoDAO;
import br.nom.penha.bruno.arquiteto.model.Pagamento;

@Named("pagamentoDAO")
@Stateful
public class PagamentoJPA extends DaoSupport<Pagamento, Integer>
        implements PagamentoDAO {

    @Override
    public void save(Pagamento pagamento) {
        super.save(pagamento);
    }

    @Override
    public void delete(Pagamento pagamento) {
        super.delete(pagamento);
    }
}
