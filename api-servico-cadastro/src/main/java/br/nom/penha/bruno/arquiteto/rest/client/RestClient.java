package br.nom.penha.bruno.arquiteto.rest.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Superclasse abstrata com as funcionalidades básicas para acessar serviços
 * REST. Cada classe de serviço específica estende esta classe.
 * 
 * @author Arquiteto
 */
public abstract class RestClient<E> {

    protected Client client;
    protected WebTarget webTarget;

    /*
        Na criação do cliente forçamos a utilização do Jackson como provedor JSON,
        pois a implementação default no Glassfish 4 é o Moxy, que não funciona
        bem quando trabalhamos com arrays JSON.
    */
    public RestClient() {
        client = ClientBuilder.newClient()
                    .register(JacksonFeature.class);
    }
    
    public abstract void update(E entidade);
    public abstract void save(E entidade);
    public abstract E findByPrimaryKey(Integer id);
    public abstract E[] findAll();

    public void delete(int id) throws ClientErrorException {
        Response response = webTarget
                .path(id + "")
                .request()
                .delete();
        validaResponse(response);
    }

    protected void validaResponse(Response response) {
        if (response.getStatus() >= 400) {
            throw new RuntimeException("resposta invalida " + response);
        }
    }

    protected WebTarget getWebTarget() {
        return webTarget;
    }

    public void close() {
        client.close();
    }

}
