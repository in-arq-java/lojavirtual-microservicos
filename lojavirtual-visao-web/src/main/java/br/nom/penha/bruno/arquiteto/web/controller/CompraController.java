/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.penha.bruno.arquiteto.web.controller;

import br.nom.penha.bruno.arquiteto.dao.BancoDAO;
import br.nom.penha.bruno.arquiteto.dao.BandeiraCartaoCreditoDAO;
import br.nom.penha.bruno.arquiteto.dao.collection.BancoCollection;
import br.nom.penha.bruno.arquiteto.dao.collection.BandeiraCartaoCreditoCollection;
import br.nom.penha.bruno.arquiteto.model.Banco;
import br.nom.penha.bruno.arquiteto.model.BandeiraCartaoCredito;
import br.nom.penha.bruno.arquiteto.model.CarrinhoCompras;
import br.nom.penha.bruno.arquiteto.model.Cliente;
import br.nom.penha.bruno.arquiteto.model.ItemCompra;
import br.nom.penha.bruno.arquiteto.model.Pagamento;
import br.nom.penha.bruno.arquiteto.model.PagamentoBoleto;
import br.nom.penha.bruno.arquiteto.model.PagamentoCartaoCredito;
import br.nom.penha.bruno.arquiteto.model.Pedido;
import br.nom.penha.bruno.arquiteto.model.Produto;
import br.nom.penha.bruno.arquiteto.rest.client.json.PedidosRestJsonClient;
import br.nom.penha.bruno.arquiteto.rest.client.json.ProdutosRestJSONClient;
import br.nom.penha.bruno.arquiteto.web.util.JSFHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("compraController")
@ConversationScoped
public class CompraController implements Serializable{

    private final static Logger logger = Logger.getLogger(CompraController.class.getName());
    private static final String CARTAO_CREDITO = "cartao";
    private static final String BOLETO = "boleto";
    private Collection<Produto> produtos;
    private CarrinhoCompras carrinho;
    private String formaPagamentoSelecionada = "cartao";
    private Cliente cliente;
    private boolean boleto = false;
    private List<String> formasPagamento;
    private Pagamento pagamento = new PagamentoCartaoCredito();
    
    @Inject
    transient PedidosRestJsonClient pedidosService;

    @Inject
    transient ProdutosRestJSONClient produtosService;
    
    @Inject
    private Conversation conversation;

    public CompraController() {
        pagamento = new PagamentoCartaoCredito();
        HttpSession session =
                (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        cliente = (Cliente) session.getAttribute("usuario");
        if (cliente == null) {
            cliente = new Cliente();
        }
    }

    public void setProdutos(Collection<Produto> produtos) {
        this.produtos = produtos;
    }

    public Collection<Produto> getProdutos() {
        try {
            if (produtos == null) {
                produtos = Arrays.asList(produtosService.findAll());
            }
        } catch (Exception e) {
            String msg = "Erro ao carregar produtos";
            logger.log(Level.SEVERE,msg, e);
            JSFHelper.addErrorMessage(msg);
            return null;
        }
        return produtos;
    }

    public void setCarrinho(CarrinhoCompras carrinho) {
        this.carrinho = carrinho;
    }

    public CarrinhoCompras getCarrinho() {
        return carrinho;
    }

    public void setFormaPagamentoSelecionada(String formaPagamento) {
        this.formaPagamentoSelecionada = formaPagamento;
        boleto = BOLETO.equals(formaPagamento);
    }

    public List getBancos() {
        BancoDAO dao = BancoCollection.getInstance();
        List bancos = dao.getAll();
        return bancos;
    }

    public void setBoleto(boolean boleto) {
        this.boleto = boleto;
    }

    public boolean isBoleto() {
        return boleto;
    }

    public void setFormasPagamento(List formasPagamento) {
        this.formasPagamento = formasPagamento;
    }

    public List getFormasPagamento() {
        formasPagamento = new ArrayList();
        formasPagamento.add(BOLETO);
        formasPagamento.add(CARTAO_CREDITO);
        return formasPagamento;
    }

    public String getFormaPagamentoSelecionada() {
        return formaPagamentoSelecionada;
    }

    public List getBandeiras() {
        BandeiraCartaoCreditoDAO dao = BandeiraCartaoCreditoCollection.getInstance();
        List<BandeiraCartaoCredito> bandeiras = dao.getAll();
        return bandeiras;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void atualizarFormaPagamento(ValueChangeEvent valueChangeEvent) {
        logger.log(Level.INFO, "[ValueChangeListener] - pagamento = {0}", pagamento);
        if (valueChangeEvent.getNewValue().equals("boleto")) {
            this.setBoleto(true);
            pagamento = new PagamentoBoleto(pagamento);
        } else {
            this.setBoleto(false);
            pagamento = new PagamentoCartaoCredito(pagamento);
        }
        FacesContext.getCurrentInstance().renderResponse();
    }

    public String adicionarCarrinho(Produto produto) {
        if(conversation.isTransient()) {
            conversation.begin();
        }
        logger.info("Adicionar produto no carrinho");
        logger.log(Level.INFO, "Produto selecionado pelo cliente {0}", produto);
        ItemCompra item = new ItemCompra(produto, 1, 0);
        //caso não exista um carrinho, cria-se um e adiciona-se o item desejado
        if (carrinho == null) {
            carrinho = new CarrinhoCompras(item);
        } else {
            carrinho.addItem(item);
        }
        return "/compra/carrinhoCompras";
    }

    public String enviarPedido() {
        logger.log(Level.INFO, "[CompraController - enviarPedido] {0}", pagamento);

        try {

            pagamento.setDataPagamento(new Date());
            pagamento.setValor(carrinho.getPrecoTotal());
            if (pagamento instanceof PagamentoCartaoCredito) {
                ((PagamentoCartaoCredito) pagamento).setBandeiraCartaoCredito(new BandeiraCartaoCredito(1, "Visa"));
            } else if (pagamento instanceof PagamentoBoleto) {
                ((PagamentoBoleto) pagamento).setBanco(new Banco(1, "Banco do Brasil"));
            }

            //Criacao do pedido
            String clienteBrowser = "";
            String clienteIP = "";
            String status = "";

            Pedido pedido =
                    new Pedido(cliente, clienteBrowser, clienteIP, new Date(),
                    carrinho.getItens(), pagamento, status);
            logger.log(Level.INFO, "[CompraController] Enviando pedido para o banco de dados {0}", pedido);
            pedidosService.enviarPedido(pedido);
            if(!conversation.isTransient()) {
                conversation.end();
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "erro no processamento de envio de pedido", ex);
            JSFHelper.addErrorMessage("Erro no processamento de envio de pedido");
        }
        return "/compra/pedidoEnviado";
    }
}
