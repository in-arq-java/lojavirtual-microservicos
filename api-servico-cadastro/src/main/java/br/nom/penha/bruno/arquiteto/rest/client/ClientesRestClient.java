package br.nom.penha.bruno.arquiteto.rest.client;

import br.nom.penha.bruno.arquiteto.model.Cliente;

/**
 * Superclasse abstrata com as funcionalidades básicas para acessar os serviços
 * REST relacionados a entidade Cliente. Possui especializações para clientes
 * XML ou JSON.
 * 
 * @author Arquiteto
 */
public abstract class ClientesRestClient extends RestClient<Cliente> {

    public ClientesRestClient() {
        webTarget = client.target("http://localhost:8080/cadastro-service/resources/clientes");
    }
}
