package br.nom.penha.bruno.arquiteto.rest.client.json;

import br.nom.penha.bruno.arquiteto.json.converter.ProdutoJsonConverter;
import br.nom.penha.bruno.arquiteto.model.Produto;
import br.nom.penha.bruno.arquiteto.rest.client.ProdutosRestClient;

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
public class ProdutosRestJSONClient extends ProdutosRestClient {

    @Override
    public void update(Produto produto) {
        JsonObject produtoJson = ProdutoJsonConverter.convertToJson(produto);
        Response response = getWebTarget()
                .path(produto.getId() + "")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(produtoJson, MediaType.APPLICATION_JSON));
        validaResponse(response);
    }

    @Override
    public void save(Produto produto) {
        JsonObject produtoJson = ProdutoJsonConverter.convertToJson(produto);
        Response response = getWebTarget()
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(produtoJson, MediaType.APPLICATION_JSON));
        validaResponse(response);
    }

    @Override
    public Produto findByPrimaryKey(Integer id) {
                return getWebTarget()
                .path(id + "")
                .request(MediaType.APPLICATION_JSON)
                .get(Produto.class);
    }

    @Override
    public Produto[] findAll() {
        JsonArray produtosJson = getWebTarget()
                .request(MediaType.APPLICATION_JSON)
                .get(JsonArray.class);
        List<Produto> produtos = new ArrayList<>();
        for (JsonValue produtoJson : produtosJson) {
            produtos.add(ProdutoJsonConverter.convertToJava((JsonObject) produtoJson));
        }
        return produtos.toArray(new Produto[produtos.size()]);
    }

}
