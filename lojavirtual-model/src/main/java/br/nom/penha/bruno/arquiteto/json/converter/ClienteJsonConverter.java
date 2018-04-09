package br.nom.penha.bruno.arquiteto.json.converter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import br.nom.penha.bruno.arquiteto.model.Cliente;

/**
 * Classe utilizada para fazer a conversão entre objetos Cliente e sua representação Json
 * utilizando JSON-P.
 * 
 * @author Arquiteto
 */
public class ClienteJsonConverter {

    public static JsonObject convertToJson(Cliente cliente) {
        //Fornece um valor default quando o cliente é novo (id == null)
        Integer idCliente = cliente.getId()!= null ? cliente.getId() : 0;

        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("id", idCliente)
                .add("nome", cliente.getNome())
                .add("telefone", cliente.getTelefone())
                .add("email", cliente.getEmail())
                .add("senha", cliente.getSenha())
                .add("ativo", cliente.isAtivo())
                .add("enderecoRua", cliente.getEnderecoRua())
                .add("enderecoNumero", cliente.getEnderecoNumero())
                .add("enderecoBairro", cliente.getEnderecoBairro())
                .add("enderecoCep", cliente.getEnderecoCep())
                .add("enderecoCidade", cliente.getEnderecoCidade())
                .add("enderecoEstado", cliente.getEnderecoEstado());

        return builder.build();
    }

    public static Cliente convertToJava(JsonObject clienteJson) {
        //retorna o valor do id para null quando é um novo cliente
        Integer idCliente = clienteJson.getInt("id",0) == 0 ? null : clienteJson.getInt("id");
        
        Cliente cliente = new Cliente(idCliente,
                    clienteJson.getString("nome", ""),
                    clienteJson.getString("email", ""),
                    clienteJson.getString("senha",""),
                    clienteJson.getString("telefone",""),
                    clienteJson.getBoolean("ativo", false),
                    clienteJson.getString("enderecoRua",""), 
                    clienteJson.getString("enderecoNumero",""),
                    clienteJson.getString("enderecoBairro",""),
                    clienteJson.getString("enderecoCep",""),
                    clienteJson.getString("enderecoCidade",""),
                    clienteJson.getString("enderecoEstado",""));
        
        return cliente;
    }
    
}
