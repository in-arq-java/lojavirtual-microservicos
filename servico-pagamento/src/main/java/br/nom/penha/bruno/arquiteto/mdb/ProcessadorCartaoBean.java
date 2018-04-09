package br.nom.penha.bruno.arquiteto.mdb;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import br.nom.penha.bruno.arquiteto.cartaocredito.Driver;
import br.nom.penha.bruno.arquiteto.cartaocredito.DriverFactory;
import br.nom.penha.bruno.arquiteto.exception.LojavirtualException;
import br.nom.penha.bruno.arquiteto.jms.ClienteJMS;
import br.nom.penha.bruno.arquiteto.json.converter.PedidoJsonConverter;
import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;
import br.nom.penha.bruno.arquiteto.model.Pedido;

@MessageDriven(mappedName = "java:app/queue/ProcessarPedido",
        activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
        })
public class ProcessadorCartaoBean implements MessageListener {

    private static final Logger logger = Logger.getLogger(ProcessadorCartaoBean.class.getName());

    @Override
    public void onMessage(Message mensagem) {
        try {
            logger.info("Processando pedido recebido");
            if (!(mensagem instanceof TextMessage)) {
                logger.severe("Tipo de mensagem invalida recebida");
                return;
            }
            String pedidoString = mensagem.getBody(String.class);
            Pedido pedido = convertToJava(pedidoString);

            PagamentoCartaoCredito pagamento = (PagamentoCartaoCredito) pedido.getPagamento();
            Driver driver = DriverFactory.getInstance().getDriver(pagamento.getBandeiraCartaoCredito());
            boolean aprovado = driver.aprovaPagamento(pagamento);
            if (aprovado) {
                pedido.setStatus("Aprovado");
            } else {
                pedido.setStatus("Reprovado");
            }
            ClienteJMS.getInstance().enviarMensagem(pedido, "java:app/queue/Resultado");
        } catch (JMSException | LojavirtualException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Transforma uma representação String Json em um objeto Pedido.
     * 
     * Primeiro faz a conversão de String para JsonObject e depois utiliza a classe
     * PedidoJsonConverter para gerar a instãncia de Pedido.
     * 
     * @param pedidoString
     * @return string convertida em um objeto Pedido
     */
    private Pedido convertToJava(String pedidoString) {
        JsonObject pedidoJson;
        try (JsonReader reader = Json.createReader(new StringReader(pedidoString))) {
            pedidoJson = reader.readObject();
        }
        return PedidoJsonConverter.convertToJava(pedidoJson);
    }
}
