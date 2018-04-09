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

import br.nom.penha.bruno.arquiteto.dao.CategoriaDAO;
import br.nom.penha.bruno.arquiteto.model.Categoria;

/**
 *
 * @author Arquiteto
 */
@Path("categorias")
public class CategoriasResource {

    @EJB
    private CategoriaDAO dao;

    @POST
    public void save(Categoria entity) {
        dao.save(entity);
    }

    @PUT
    @Path("{id}")
    public void update(@PathParam("id") Long id, Categoria entity) {
        dao.save(entity);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id) {
        Categoria cat = new Categoria();
        cat.setId(id);
        dao.delete(cat);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Categoria getByPrimaryKey(@PathParam("id") Integer id) {
        Categoria cat = dao.findByPrimaryKey(id);
        if (cat == null) {
            cat = new Categoria();
        }
        return cat;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public Categoria[] getAll() {
        Collection<Categoria> membros = dao.findAll();
        return membros.toArray(new Categoria[0]);
    }

}
