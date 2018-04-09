package br.nom.penha.bruno.arquiteto.web.controller;

import br.nom.penha.bruno.arquiteto.comparator.ComparatorPedidoClienteNumero;
import br.nom.penha.bruno.arquiteto.model.Pagamento;
import br.nom.penha.bruno.arquiteto.model.PagamentoBoleto;
import br.nom.penha.bruno.arquiteto.model.Pedido;
import br.nom.penha.bruno.arquiteto.rest.client.json.PedidosRestJsonClient;
import br.nom.penha.bruno.arquiteto.web.util.JSFHelper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("pedidoController")
@RequestScoped
public class PedidoController implements Serializable {

    private final static Logger logger = Logger.getLogger(PedidoController.class.getName());
    private List<Pedido> pedidos;
    private Pedido pedido;

    @Inject
    PedidosRestJsonClient pedidosService;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public boolean isBoleto() {
        Pagamento pagamento = pedido.getPagamento();
        if (pagamento == null) {
            return false;
        } else {
            return pagamento instanceof PagamentoBoleto;
        }
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<Pedido> getPedidos() {
        try {
            pedidos =  Arrays.asList(pedidosService.findAll());
            if (pedidos != null && pedidos.size() > 0) {
                Collections.sort(pedidos, new ComparatorPedidoClienteNumero());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Erro na listagem de pedidos",e);
            JSFHelper.addErrorMessage("Erro na listagem de pedidos" + e.getMessage());
        }
        return pedidos;
    }

    public String verDetalhes(Pedido pedido) {
        this.pedido = pedido;
        return "/pedido/View";
    }
}
