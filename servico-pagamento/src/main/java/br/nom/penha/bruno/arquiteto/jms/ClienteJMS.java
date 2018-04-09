package br.nom.penha.bruno.arquiteto.jms;

import java.io.Serializable;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.nom.penha.bruno.arquiteto.exception.LojavirtualException;

public class ClienteJMS {
    
    private static final ClienteJMS instance = new ClienteJMS();
    private Context ctx;
    
    /** Creates a new instance of ClienteJMS */
    private ClienteJMS() {
        try {
            initContext();
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }
    
    public static ClienteJMS getInstance() {
        return instance;
    }
    
    public void enviarMensagem(Serializable objeto, String destino) throws LojavirtualException {
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
            e.printStackTrace();
            throw new LojavirtualException(e.getMessage(),e);
        }
    }
   
    private void initContext() throws NamingException {
        ctx = new InitialContext();
    }
}
