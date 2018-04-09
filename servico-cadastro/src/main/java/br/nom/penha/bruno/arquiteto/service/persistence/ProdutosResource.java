package br.nom.penha.bruno.arquiteto.service.persistence;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;

import br.nom.penha.bruno.arquiteto.business.ProdutoFacade;
import br.nom.penha.bruno.arquiteto.json.converter.ProdutoJsonConverter;
import br.nom.penha.bruno.arquiteto.model.Produto;

import static javax.ws.rs.core.Response.Status;

/**
 * Recurso JAX-RS para gerenciamento de produtos.
 * 
 * A anotação @RequestScoped é utilizada para transformar o recurso em um
 * bean CDI e permitir a utilização da injeção via @Inject sem a necessidade 
 * de configurações adicionais.
 * 
 * @author Arquiteto
 */
@Path("produtos")
@RequestScoped
public class ProdutosResource {

    @Inject
    private ProdutoFacade facade;

    @POST
    @Consumes("application/json")
    public Response save(JsonObject entity) {
        Produto prod = ProdutoJsonConverter.convertToJava(entity);
        facade.save(prod);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public void update(@PathParam("id") Long id, JsonObject entity) {
        Produto prod = ProdutoJsonConverter.convertToJava(entity);
        facade.save(prod);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id) {
        Produto prod = facade.findByPrimaryKey(id);
        facade.delete(prod);
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public JsonObject getByPrimaryKey(@PathParam("id") Integer id) {
        Produto produto = facade.findByPrimaryKey(id);
        JsonObject jsonObject = ProdutoJsonConverter.convertToJson(produto);
        return jsonObject;
    }

    @GET
    @Produces("application/json")
    public JsonArray getAll() {
        Collection<Produto> produtos = facade.findAll();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Produto produto : produtos) {
            arrayBuilder.add(ProdutoJsonConverter.convertToJson(produto));
        }
        return arrayBuilder.build();
    }

}
