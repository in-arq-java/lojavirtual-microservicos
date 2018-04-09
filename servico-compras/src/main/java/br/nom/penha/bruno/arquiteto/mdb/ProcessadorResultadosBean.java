package br.nom.penha.bruno.arquiteto.mdb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import br.nom.penha.bruno.arquiteto.dao.DAOException;
import br.nom.penha.bruno.arquiteto.dao.PedidoDAO;
import br.nom.penha.bruno.arquiteto.model.Pedido;

@MessageDriven(mappedName = "java:app/queue/Resultado",
activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ProcessadorResultadosBean implements MessageListener {
    
    private static final Logger logger = Logger.getLogger(ProcessadorResultadosBean.class.getName());
    
   
    @EJB
    PedidoDAO pedidoDAO;

    @Override
    public void onMessage(Message aMessage) {
        try{
            logger.info("Processando resultado da analise do pagamento");
            if(!(aMessage instanceof ObjectMessage)) {
                logger.severe("Tipo de mensagem invalida recebida");
                return;
            }
            ObjectMessage mensagem = (ObjectMessage) aMessage;
            Object objeto = mensagem.getObject();
            
            if(!(objeto instanceof Pedido)) {
                logger.severe("Objeto invalido recebido na mensagem");
                return;
            }
            Pedido pedido = (Pedido)objeto;
            pedidoDAO.save(pedido);
        } catch(JMSException | DAOException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
    }
}
