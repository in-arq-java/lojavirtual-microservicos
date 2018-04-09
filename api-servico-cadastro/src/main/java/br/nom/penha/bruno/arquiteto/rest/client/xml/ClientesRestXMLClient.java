package br.nom.penha.bruno.arquiteto.rest.client.xml;

import br.nom.penha.bruno.arquiteto.model.Cliente;
import br.nom.penha.bruno.arquiteto.rest.client.ClientesRestClient;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Arquiteto
 */
public class ClientesRestXMLClient extends ClientesRestClient{

    @Override
    public Cliente[] findAll() throws ClientErrorException {
        return getWebTarget()
                .request(MediaType.APPLICATION_XML)
                .get(Cliente[].class);
    }

    @Override
    public void update(Cliente produto) throws ClientErrorException {
        Response response = getWebTarget()
                    .path(produto.getId() + "")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.entity(produto, MediaType.APPLICATION_XML));
        validaResponse(response);
    }

    @Override
    public void save(Cliente produto) throws ClientErrorException {
        Response response = getWebTarget()
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(produto, MediaType.APPLICATION_XML));
        validaResponse(response);
    }

    @Override
    public Cliente findByPrimaryKey(Integer id) throws ClientErrorException {
        return getWebTarget()
                .path(id + "")
                .request(MediaType.APPLICATION_XML)
                .get(Cliente.class);
    }
}
