package br.nom.penha.bruno.arquiteto.json.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import br.nom.penha.bruno.arquiteto.model.Banco;
import br.nom.penha.bruno.arquiteto.model.BandeiraCartaoCredito;
import br.nom.penha.bruno.arquiteto.model.Pagamento;
import br.nom.penha.bruno.arquiteto.model.PagamentoBoleto;
import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;

/**
 * Classe utilizada para fazer a conversão entre objetos Pagamento e sua
 * representação Json utilizando JSON-P.
 *
 * @author Arquiteto
 */
public class PagamentoJsonConverter {

    private final static Logger logger = Logger.getLogger(PagamentoJsonConverter.class.getName());

    public static JsonValue convertToJson(Pagamento pagamento) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        if (pagamento != null) {
            boolean boleto = pagamento instanceof PagamentoBoleto;

            //formatador para exportar as datas do pagamento como Strings Json
            SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatadorValidadeCartao = new SimpleDateFormat("MM/yy");

            //Fornece um valor default quando o pagamento é novo (id == null)
            Integer idPagamento = pagamento.getId() != null ? pagamento.getId() : 0;

            builder.add("id", idPagamento)
                    .add("dataPagamento", formatadorData.format(pagamento.getDataPagamento()))
                    .add("valor", pagamento.getValor());

            //adiciona os atributos específicos das subclasses
            if (boleto) {
                PagamentoBoleto pagBoleto = (PagamentoBoleto) pagamento;
                Banco banco = pagBoleto.getBanco();
                builder.add("type", "boleto")
                        .add("cedente", pagBoleto.getCedente())
                        .add("sacado", pagBoleto.getSacado())
                        .add("numeroCodigoBarras", pagBoleto.getNumeroCodigoBarras())
                        .add("dataVencimento", formatadorData.format(pagBoleto.getDataVencimento()));

                //como o banco não é persistido seu valor pode ser null se o pedido foi lido do banco de dados
                if (banco != null) {
                    builder.add("banco", Json.createObjectBuilder()
                            .add("id", banco.getId())
                            .add("nome", banco.getNome()));
                }
            } else {
                PagamentoCartaoCredito pagCartao = (PagamentoCartaoCredito) pagamento;
                BandeiraCartaoCredito bandeira = pagCartao.getBandeiraCartaoCredito();
                builder.add("type", "cartao")
                        .add("nomeTitular", pagCartao.getNomeTitular())
                        .add("numeroCartao", pagCartao.getNumeroCartao())
                        .add("numeroConfirmacao", pagCartao.getNumeroConfirmacao())
                        .add("dataValidadeCartao", formatadorValidadeCartao.format(pagCartao.getDataValidadeCartao()));

                //como a bandeira não é persistida seu valor pode ser null se o pedido foi lido do banco de dados
                if (bandeira != null) {
                    builder.add("bandeiraCartaoCredito", Json.createObjectBuilder()
                            .add("id", bandeira.getId())
                            .add("nome", bandeira.getNome()));
                }
            }
        }
        return builder.build();
    }

    public static Pagamento convertToJava(JsonObject pagamentoJson) {
        Pagamento pagamento = null;
        if (pagamentoJson != null) {
            boolean pagamentoBoleto = pagamentoJson.getString("type", "").equals("boleto");
            try {
                //define um valor default quando o id não é fornecido
                Integer id = pagamentoJson.getInt("id",0) == 0 ? null : pagamentoJson.getInt("id");

                //define um valor default quando o valor não é fornecido
                double valor = pagamentoJson.containsKey("valor") ? pagamentoJson.getJsonNumber("valor").doubleValue() : 0.0;

                SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatadorValidadeCartao = new SimpleDateFormat("MM/yy");

                Date dataPagamento = formatadorData.parse(pagamentoJson.getString("dataPagamento", "01/01/1970"));

                if (pagamentoBoleto) {
                    //Faz a leitura das propriedades especificas de pagamento com boleto
                    Date dataVencimento = formatadorData.parse(pagamentoJson.getString("dataVencimento", "01/01/1970"));
                    JsonObject bancoJson = pagamentoJson.getJsonObject("banco");
                    Banco banco;
                    if (bancoJson != null) {
                        banco = new Banco(bancoJson.getInt("id", 0), bancoJson.getString("nome", ""));
                    } else {
                        banco = new Banco();
                    }

                    pagamento = new PagamentoBoleto(id,
                            dataPagamento,
                            valor,
                            pagamentoJson.getString("cedente", ""),
                            pagamentoJson.getString("sacado", ""),
                            pagamentoJson.getString("numeroCodigoBarras", ""),
                            dataVencimento,
                            banco);
                } else {
                    //Faz a leitura das propriedades especificas de pagamento com cartao
                    Date dataValidadeCartao = formatadorValidadeCartao.parse(pagamentoJson.getString("dataValidadeCartao", "01/70"));
                    JsonObject cartaoJson = pagamentoJson.getJsonObject("bandeiraCartaoCredito");
                    BandeiraCartaoCredito bandeira;
                    if (cartaoJson != null) {
                        bandeira = new BandeiraCartaoCredito(cartaoJson.getInt("id", 0), cartaoJson.getString("nome", ""));
                    } else {
                        bandeira = new BandeiraCartaoCredito();
                    }

                    pagamento = new PagamentoCartaoCredito(id,
                            dataPagamento,
                            valor,
                            bandeira,
                            pagamentoJson.getString("nomeTitular", ""),
                            pagamentoJson.getString("numeroCartao", ""),
                            pagamentoJson.getInt("numeroConfirmacao", 0),
                            dataValidadeCartao);
                }
            } catch (ParseException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } 
        return pagamento;

    }

}
