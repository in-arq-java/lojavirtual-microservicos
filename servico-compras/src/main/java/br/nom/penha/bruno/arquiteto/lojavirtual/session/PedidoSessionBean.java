/*
 * PedidoSessionBean.java
 *
 */
package br.nom.penha.bruno.arquiteto.lojavirtual.session;

import java.io.StringWriter;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import br.nom.penha.bruno.arquiteto.dao.DAOException;
import br.nom.penha.bruno.arquiteto.dao.PedidoDAO;
import br.nom.penha.bruno.arquiteto.exception.LojavirtualException;
import br.nom.penha.bruno.arquiteto.jms.ClienteJMS;
import br.nom.penha.bruno.arquiteto.json.converter.PedidoJsonConverter;
import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;
import br.nom.penha.bruno.arquiteto.model.Pedido;

@Stateless(mappedName = "ejb/PedidoSession")
public class PedidoSessionBean implements PedidoSessionLocal {

    @EJB
    PedidoDAO pedidoDAO;

    @Override
    public Pedido enviarPedido(Pedido pedido) throws LojavirtualException {
        try {
            pedido.setStatus("Recebido");
            pedidoDAO.save(pedido);
            if (pedido.getPagamento() instanceof PagamentoCartaoCredito) {
                JsonObject pedidoJson = PedidoJsonConverter.convertToJson(pedido);
                ClienteJMS.getInstance().enviarMensagem(pedidoJson.toString(), "java:app/queue/ProcessarPedido");
            }
        } catch (DAOException e) {
            throw new LojavirtualException("Erro ao enviar pedido", e);
        }
        return pedido;
    }
}
