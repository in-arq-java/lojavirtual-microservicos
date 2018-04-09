package br.nom.penha.bruno.arquiteto.service.persistence;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;

import br.nom.penha.bruno.arquiteto.dao.ClienteDAO;
import br.nom.penha.bruno.arquiteto.model.Cliente;

/**
 *
 * @author Arquiteto
 */
@Path("clientes")
public class ClientesResource {

    @EJB
    private ClienteDAO dao;

    @POST
    public void save(Cliente entity) {
        dao.save(entity);
    }

    @PUT
    @Path("{id}")
    public void update(@PathParam("id") Long id, Cliente entity) {
        dao.save(entity);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id) {
        Cliente cat = new Cliente();
        cat.setId(id);
        dao.delete(cat);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml","application/json"})
    public Cliente getByPrimaryKey(@PathParam("id") Integer id) {
        Cliente cli = dao.findByPrimaryKey(id);
        if(cli == null) {
            cli = new Cliente();
        }
        return cli;
    }

    @GET
    @Produces({"application/xml","application/json"})
    public Cliente[] getAll() {
        Collection<Cliente> membros = dao.findAll();
        return membros.toArray(new Cliente[0]);
    }

}
