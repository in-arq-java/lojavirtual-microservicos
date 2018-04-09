package br.nom.penha.bruno.arquiteto.service;

import java.util.Collection;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.nom.penha.bruno.arquiteto.dao.PedidoDAO;
import br.nom.penha.bruno.arquiteto.json.converter.PedidoJsonConverter;
import br.nom.penha.bruno.arquiteto.lojavirtual.session.PedidoSessionLocal;
import br.nom.penha.bruno.arquiteto.model.Pedido;

/**
 * Recurso JAX-RS para gerenciamento de pedidos e compras.
 *
 * A anotação @RequestScoped é utilizada para transformar o recurso em um bean
 * CDI e permitir a utilização da injeção via @EJB sem a necessidade de
 * configurações adicionais.
 *
 * @author Arquiteto
 */
@Path("pedidos")
@RequestScoped
public class PedidosResource {

    @EJB
    PedidoDAO pedidoDAO;

    @EJB
    PedidoSessionLocal pedidoEJB;
    
    @POST
    @Path("envio")
    @Consumes("application/json")
    public void enviarPedido(JsonObject pedidoJson) {
        Pedido pedido = PedidoJsonConverter.convertToJava(pedidoJson);
        pedidoEJB.enviarPedido(pedido);
    }
    
    @GET
    @Produces("application/json")
    public JsonArray getAll() {
        Collection<Pedido> pedidos = pedidoDAO.findAll();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Pedido pedido : pedidos) {
            arrayBuilder.add(PedidoJsonConverter.convertToJson(pedido));
        }
        return arrayBuilder.build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public JsonObject getByPrimaryKey(@PathParam("id") Long id) {
        Pedido pedido = pedidoDAO.findByPrimaryKey(id);
        JsonObject jsonObject = PedidoJsonConverter.convertToJson(pedido);
        return jsonObject;
    }

    @POST
    @Consumes("application/json")
    public Response save(JsonObject pedidoJson) {
        Pedido pedido = PedidoJsonConverter.convertToJava(pedidoJson);
        pedidoDAO.save(pedido);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public void update(@PathParam("id") Long id, JsonObject pedidoJson) {
        Pedido pedido = PedidoJsonConverter.convertToJava(pedidoJson);
        pedidoDAO.save(pedido);
    }

}
