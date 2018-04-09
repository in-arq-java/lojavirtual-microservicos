/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.penha.bruno.arquiteto.web.controller;

import br.nom.penha.bruno.arquiteto.model.Cliente;
import br.nom.penha.bruno.arquiteto.rest.client.json.ClientesRestJSONClient;
import br.nom.penha.bruno.arquiteto.web.util.JSFHelper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("clienteController")
@RequestScoped
public class ClienteController implements Serializable{

    private final static Logger logger = Logger.getLogger(ClienteController.class.getName());
    private Cliente cliente = new Cliente();
    private Collection<Cliente> clientes;

    @Inject
    ClientesRestJSONClient clienteService;

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
    /**
     * Retorna o último cliente selecionado. Caso o atributo
     * cliente seja null ele é inicializado para uma nova entidade
     * 
     * 
     * @return um cliente
     */
    public Cliente getCliente() {
        if (cliente == null) {
            cliente = new Cliente();
        }
        return cliente;
    }

    /**
     * inicializa o atributo cliente para uma nova entidade
     * 
     * @return "/cliente/Edit"
     */
    public String setup() {
        cliente = new Cliente();
        return "/cliente/Edit";
    }

    /** 
     * método vinculado ao botão salvar do formulário de criação/edição de
     * cliente. Aciona o dao correspondente a cliente
     * 
     * @return null ou "/cliente/List"
     */
    public String salvar() {
        try {
            clienteService.save(cliente);
            reset();
            JSFHelper.addSuccessMessage("cliente foi salvo com sucesso");
        } catch (Exception e) {
            String msg = "Erro ao salvar o cliente";
            logger.log(Level.SEVERE, msg, e);
            JSFHelper.addErrorMessage(msg);
            return null;
        }
        return "/cliente/List";
    }

    /**
     * Método vinculado ao link de edição de um cliente. Atualiza
     * o atributo cliente com o cliente selecionado e navega para a página
     * de edição.
     * 
     * @param cliente
     * @return /cliente/Edit
     */
    public String verDetalhes(Cliente cliente) {
        this.cliente = cliente;
        return "/cliente/Edit";
    }

    /**
     * Método vinculado ao link de remoção de um cliente. Aciona 
     * o DAO correspondente a cliente
     * 
     * @param cliente
     * @return null ou "/cliente/List"
     */
    public String remover(Cliente cliente) {
        try {
            clienteService.delete(cliente.getId());
            reset();
            JSFHelper.addSuccessMessage("O cliente foi removido com sucesso");
        } catch (Exception e) {
            String msg = "Erro ao remover o cliente";
            logger.log(Level.SEVERE, msg, e);
            JSFHelper.addErrorMessage(msg);
            return null;
        }
        return "/cliente/List";
    }

    /**
     * joga os valores dos atributos para null
     */
    private void reset() {
        clientes = null;
        cliente = null;
    }

    /**
     * retorna todos os clientes. Aciona o DAO correspondente a cliente
     * 
     * @return ListDataModel com todas os protótipos
     */
    public Collection<Cliente> getClientes() {
        try {
            if (clientes == null) {
                clientes = Arrays.asList(clienteService.findAll());
            }
        } catch (Exception e) {
            String msg = "Erro ao carregar clientes";
            logger.log(Level.SEVERE, msg, e);
            JSFHelper.addErrorMessage(msg);
            return null;
        }
        return clientes;
    }
}
