package br.nom.penha.bruno.arquiteto.rest.client.json;

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
public class CategoriasRestJSONClient extends CategoriasRestClient{

    @Override
    public Categoria[] findAll() throws ClientErrorException {
        return getWebTarget()
                .request(MediaType.APPLICATION_JSON)
                .get(Categoria[].class);
    }

    @Override
    public void update(Categoria categoria) throws ClientErrorException {
        Response response = getWebTarget()
                    .path(categoria.getId() + "")
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(categoria, MediaType.APPLICATION_JSON));
        validaResponse(response);
    }

    @Override
    public void save(Categoria categoria) throws ClientErrorException {
        Response response = getWebTarget()
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(categoria, MediaType.APPLICATION_JSON));
        validaResponse(response);
    }

    @Override
    public Categoria findByPrimaryKey(Integer id) throws ClientErrorException {
        return getWebTarget()
                .path(id + "")
                .request(MediaType.APPLICATION_JSON)
                .get(Categoria.class);
    }
}
