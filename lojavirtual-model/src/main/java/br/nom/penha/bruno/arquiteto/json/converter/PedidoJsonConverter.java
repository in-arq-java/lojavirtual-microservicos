package br.nom.penha.bruno.arquiteto.json.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import br.nom.penha.bruno.arquiteto.model.Cliente;
import br.nom.penha.bruno.arquiteto.model.ItemCompra;
import br.nom.penha.bruno.arquiteto.model.Pagamento;
import br.nom.penha.bruno.arquiteto.model.Pedido;

/**
 *
 * @author Arquiteto
 */
public class PedidoJsonConverter {
    
    public static JsonObject convertToJson(Pedido pedido) {
        //formatador para exportar a data do pedido como uma String Json
        SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

        //Fornece um valor default quando o pedido é novo (id == null)
        Long numeroPedido = pedido.getNumero() != null ? pedido.getNumero() : 0;
        
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("numero", numeroPedido)
                .add("data", formatadorData.format(pedido.getData()))
                .add("clienteIP", pedido.getClienteIP())
                .add("clienteBrowser", pedido.getClienteBrowser())
                .add("status", pedido.getStatus())
                .add("cliente", ClienteJsonConverter.convertToJson(pedido.getCliente()))
                .add("pagamento", PagamentoJsonConverter.convertToJson(pedido.getPagamento()));

        //Criação do array Json para os itens de compra. Esse passo é importante pois as entidades JPA
        //tipicamente utilizam outras implementações que não as coleções padrões do Java, o que pode gerar
        //problemas na desserialização dos objetos.
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (ItemCompra item : pedido.getItensPedido()) { 
            Integer idItem = item.getId() != null ? item.getId() : 0;
            arrayBuilder.add(Json.createObjectBuilder()
                    .add("id", idItem)
                    .add("quantidade", item.getQuantidade())
                    .add("desconto", item.getDesconto())
                    .add("produto", ProdutoJsonConverter.convertToJson(item.getProduto())));
        }
        builder.add("itensPedido", arrayBuilder);
        
        return builder.build();
    }
    
    public static Pedido convertToJava(JsonObject pedidoJson) {
        Cliente cliente = ClienteJsonConverter.convertToJava(pedidoJson.getJsonObject("cliente"));
        Pagamento pagamento = PagamentoJsonConverter.convertToJava(pedidoJson.getJsonObject("pagamento"));
        List<ItemCompra> items = new ArrayList<>();
        JsonArray itemsJson = pedidoJson.getJsonArray("itensPedido");
        if (itemsJson != null) {
            for (JsonObject itemJson : itemsJson.getValuesAs(JsonObject.class)) {
                Integer idItem = itemJson.getInt("id",0) == 0 ? null : itemJson.getInt("id");
                ItemCompra item = new ItemCompra(idItem,
                        ProdutoJsonConverter.convertToJava(itemJson.getJsonObject("produto")),
                        itemJson.getInt("quantidade", 1),
                        itemJson.getInt("desconto", 0));
                items.add(item);
            }
        }

        //define um valor default quando o numero não é fornecido
        Long numero = pedidoJson.getInt("numero",0) == 0 ? null : (long)pedidoJson.getInt("numero");

        //formatador para ler a data do pedido como uma String
        SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        
        try {
            data = formatadorData.parse(pedidoJson.getString("data", "01/01/1970"));
        } catch (ParseException ex) {
            Logger.getLogger(PedidoJsonConverter.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        Pedido pedido = new Pedido(numero,
                cliente,
                pedidoJson.getString("clienteBrowser", ""),
                pedidoJson.getString("clienteIP", ""),
                data,
                items,
                pagamento,
                pedidoJson.getString("status", ""));
        return pedido;
    }
    
}
