package br.nom.penha.bruno.arquiteto.rest.client.xml;

import br.nom.penha.bruno.arquiteto.model.Categoria;
import br.nom.penha.bruno.arquiteto.rest.client.CategoriasRestClient;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Arquiteto
 */
public class CategoriasRestXMLClient extends CategoriasRestClient{

    @Override
    public Categoria[] findAll() throws ClientErrorException {
        return getWebTarget()
                .request(MediaType.APPLICATION_XML)
                .get(Categoria[].class);
    }

    @Override
    public void update(Categoria categoria) throws ClientErrorException {
        Response response = getWebTarget()
                    .path(categoria.getId() + "")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.entity(categoria, MediaType.APPLICATION_XML));
        validaResponse(response);
    }

    @Override
    public void save(Categoria categoria) throws ClientErrorException {
        Response response = getWebTarget()
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(categoria, MediaType.APPLICATION_XML));
        validaResponse(response);
    }

    @Override
    public Categoria findByPrimaryKey(Integer id) throws ClientErrorException {
        return getWebTarget()
                .path(id + "")
                .request(MediaType.APPLICATION_XML)
                .get(Categoria.class);
    }
}
