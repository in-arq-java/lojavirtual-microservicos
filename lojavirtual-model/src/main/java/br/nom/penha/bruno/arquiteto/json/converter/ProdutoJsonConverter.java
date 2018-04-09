package br.nom.penha.bruno.arquiteto.json.converter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import br.nom.penha.bruno.arquiteto.model.Categoria;
import br.nom.penha.bruno.arquiteto.model.Produto;
import br.nom.penha.bruno.arquiteto.model.ProdutoDigital;
import br.nom.penha.bruno.arquiteto.model.ProdutoMaterial;

/**
 *
 * @author Arquiteto
 */
public class ProdutoJsonConverter {

    public static JsonObject convertToJson(Produto produto) {
        Categoria categoria = produto.getCategoria();
        boolean material = produto instanceof ProdutoMaterial;

        //Fornece um valor default quando o produto é novo (id == null)
        Integer idProduto = produto.getId() != null ? produto.getId() : 0;

        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("id", idProduto)
                .add("nome", produto.getNome())
                .add("preco", produto.getPreco())
                .add("marca", produto.getMarca())
                .add("categoria", Json.createObjectBuilder()
                        .add("id", categoria.getId())
                        .add("descricao", categoria.getDescricao()))
                .add("ativo", produto.isAtivo());

        //adiciona os atributos específicos das subclasses
        if (material) {
            ProdutoMaterial prodMaterial = (ProdutoMaterial) produto;
            builder.add("type", "material")
                    .add("taxaEntrega", prodMaterial.getTaxaEntrega());
        } else {
            ProdutoDigital prodDigital = (ProdutoDigital) produto;
            builder.add("type", "digital")
                    .add("nomeArquivo", prodDigital.getNomeArquivo())
                    .add("tamanho", prodDigital.getTamanho());
        }
        return builder.build();
    }

    public static Produto convertToJava(JsonObject produtoJson) {
        boolean produtoMaterial = produtoJson.getString("type").equals("material");
        Produto produto;
        JsonObject categoriaJson = produtoJson.getJsonObject("categoria");
        Categoria categoria = new Categoria(categoriaJson.getInt("id"), categoriaJson.getString("descricao"), true);

        //define um valor default quando o id não é fornecido
        Integer id = produtoJson.containsKey("id") ? produtoJson.getInt("id") : null;

        //define um valor default quando o preco não é fornecido
        double preco = produtoJson.containsKey("preco") ? produtoJson.getJsonNumber("preco").doubleValue() : 0.0;

        if (produtoMaterial) {
            //define um valor default quando a taxa de entrega não é fornecida
            double taxaEntrega = produtoJson.containsKey("taxaEntrega") ? produtoJson.getJsonNumber("taxaEntrega").doubleValue() : 0.0;

            produto = new ProdutoMaterial(id,
                    categoria,
                    produtoJson.getString("marca", ""),
                    produtoJson.getString("nome", ""),
                    preco,
                    produtoJson.getBoolean("ativo", true),
                    taxaEntrega);
        } else {
            produto = new ProdutoDigital(id,
                    categoria,
                    produtoJson.getString("marca", ""),
                    produtoJson.getString("nome", ""),
                    preco,
                    produtoJson.getBoolean("ativo", true),
                    produtoJson.getString("nomeArquivo", ""),
                    produtoJson.getInt("tamanho", 0));
        }
        return produto;
    }

}
