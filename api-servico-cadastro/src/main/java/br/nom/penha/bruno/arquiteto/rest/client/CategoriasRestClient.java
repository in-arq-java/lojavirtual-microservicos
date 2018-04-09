package br.nom.penha.bruno.arquiteto.rest.client;

import br.nom.penha.bruno.arquiteto.model.Categoria;

/**
 * Superclasse abstrata com as funcionalidades básicas para acessar os serviços
 * REST relacionados a entidade Categoria. Possui especializações para clientes
 * XML ou JSON.
 * 
 * @author Arquiteto
 */
public abstract class CategoriasRestClient extends RestClient<Categoria> {

    public CategoriasRestClient() {
        webTarget = client.target("http://localhost:8080/cadastro-service/resources/categorias");
    }
}