/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.penha.bruno.arquiteto.rest.client;

import br.nom.penha.bruno.arquiteto.model.Pedido;

/**
 * Superclasse abstrata com as funcionalidades básicas para acessar os serviços
 * REST relacionados a entidade Pedido. Possui especialização para clientes
 * JSON utilizando JSON-P.
 * 
 * @author Arquiteto
 */
public abstract class PedidosRestClient extends RestClient<Pedido> {

    public PedidosRestClient() {
        webTarget = client.target("http://localhost:8080/compras-service/resources/pedidos");
    }

}
