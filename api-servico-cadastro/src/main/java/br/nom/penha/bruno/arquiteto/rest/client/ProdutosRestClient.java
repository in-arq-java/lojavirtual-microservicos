package br.nom.penha.bruno.arquiteto.rest.client;

import br.nom.penha.bruno.arquiteto.model.Produto;

/**
 * Superclasse abstrata com as funcionalidades básicas para acessar os serviços
 * REST relacionados a entidade Produto. Possui especialização para clientes
 * JSON utilizando JSON-P.
 * 
 * @author Arquiteto
 */public abstract class ProdutosRestClient extends RestClient<Produto> {

    public ProdutosRestClient() {
        webTarget = client.target("http://localhost:8080/cadastro-service/resources/produtos");
    }
}
