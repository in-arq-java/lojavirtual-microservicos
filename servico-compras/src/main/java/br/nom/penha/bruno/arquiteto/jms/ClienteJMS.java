package br.nom.penha.bruno.arquiteto.jms;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.nom.penha.bruno.arquiteto.exception.LojavirtualException;

public class ClienteJMS {

    private static final Logger logger = Logger.getLogger(ClienteJMS.class.getName());
    private static final ClienteJMS instance = new ClienteJMS();
    private Context ctx;
    
    /** Creates a new instance of ClienteJMS */
    private ClienteJMS() {
        try {
            initContext();
        } catch (NamingException ex) {
            logger.log(Level.SEVERE,ex.getMessage(),ex);
        }
    }
    
    public static ClienteJMS getInstance() {
        return instance;
    }
    
    public void enviarMensagem(String objeto, String destino) throws LojavirtualException {
        try {
            if(ctx==null) {
                initContext();
            }
            ConnectionFactory factory = (ConnectionFactory) ctx.lookup("jms/__defaultConnectionFactory");
            Destination dest = (Destination) ctx.lookup(destino);
            try (JMSContext jmsContext = factory.createContext()) {
                JMSProducer producer = jmsContext.createProducer();
                producer.send(dest, objeto);
            }
        } catch(Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new LojavirtualException(e.getMessage(),e);
        }
    }
   
    private void initContext() throws NamingException {
        ctx = new InitialContext();
    }
}
