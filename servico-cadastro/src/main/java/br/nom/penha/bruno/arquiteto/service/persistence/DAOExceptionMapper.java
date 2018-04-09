package br.nom.penha.bruno.arquiteto.service.persistence;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.nom.penha.bruno.arquiteto.dao.DAOException;

/**
 *
 * Classe que captura as exceções do tipo DAOException e transforma em uma resposta
 * Http adequada.
 * 
 * @author Arquiteto
 */
@Provider
public class DAOExceptionMapper implements ExceptionMapper<DAOException>{

    @Override
    public Response toResponse(DAOException exception) {
        return Response.serverError().header("x-reason", exception.getMessage()).build();
    }
    
}
