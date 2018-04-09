package br.nom.penha.bruno.arquiteto.rest.client.json;

import br.nom.penha.bruno.arquiteto.json.converter.PedidoJsonConverter;
import br.nom.penha.bruno.arquiteto.model.Pedido;
import br.nom.penha.bruno.arquiteto.rest.client.PedidosRestClient;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Arquiteto
 */
public class PedidosRestJsonClient extends PedidosRestClient{

    
    public void enviarPedido(Pedido pedido)  {
        JsonObject pedidoJson = PedidoJsonConverter.convertToJson(pedido);
        Response response = getWebTarget()
                .path("envio")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pedidoJson, MediaType.APPLICATION_JSON));
        validaResponse(response);
    }
    
    @Override
    public void update(Pedido pedido) {
        JsonObject pedidoJson = PedidoJsonConverter.convertToJson(pedido);
        Response response = getWebTarget()
                .path(pedido.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(pedidoJson, MediaType.APPLICATION_JSON));
        validaResponse(response);
    }

    @Override
    public void save(Pedido pedido) {
        JsonObject pedidoJson = PedidoJsonConverter.convertToJson(pedido);
        Response response = getWebTarget()
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pedidoJson, MediaType.APPLICATION_JSON));
        validaResponse(response);
    }

    @Override
    public Pedido findByPrimaryKey(Integer id) {
                        return getWebTarget()
                .path(id + "")
                .request(MediaType.APPLICATION_JSON)
                .get(Pedido.class);
    }

    @Override
    public Pedido[] findAll() {
        JsonArray pedidosJson = getWebTarget()
                .request(MediaType.APPLICATION_JSON)
                .get(JsonArray.class);
        List<Pedido> pedidos = new ArrayList<>();
        for (JsonValue produtoJson : pedidosJson) {
            pedidos.add(PedidoJsonConverter.convertToJava((JsonObject) produtoJson));
        }
        return pedidos.toArray(new Pedido[pedidos.size()]);
    }
    
}
