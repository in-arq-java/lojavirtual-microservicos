package br.nom.penha.bruno.arquiteto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.nom.penha.bruno.arquiteto.dao.PersistentEntity;

@Entity
@Table(name = "pedidos")
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p ORDER BY p.numero", hints =
    @QueryHint(name = "org.hibernate.cacheable", value = "true"))})
@Cacheable(true)
public class Pedido implements Serializable, PersistentEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "cliente_IP")
    private String clienteIP = "";
    @Column(name = "cliente_browser")
    private String clienteBrowser = "";
    private String status;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemCompra> itensPedido;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "id_cliente")
    public Cliente cliente;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pagamento")
    public Pagamento pagamento;

    public Pedido() {
        itensPedido = new ArrayList();
    }

    public Pedido(Cliente cliente, String clienteBrowser, String clienteIP, Date data, List itensPedido,
            Pagamento pagamento, String status) {
        this.cliente = cliente;
        this.clienteBrowser = clienteBrowser;
        this.clienteIP = clienteIP;
        this.data = data;
        setItensPedido(itensPedido);
        this.pagamento = pagamento;
        this.status = status;
    }

    public Pedido(Long numero, Cliente cliente, String clienteBrowser, String clienteIP, Date data,
            List itensPedido, Pagamento pagamento, String status) {
        this(cliente, clienteBrowser, clienteIP, data, itensPedido, pagamento, status);
        this.numero = numero;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getClienteIP() {
        return clienteIP;
    }

    public void setClienteIP(String clienteIP) {
        this.clienteIP = clienteIP;
    }

    public String getClienteBrowser() {
        return clienteBrowser;
    }

    public void setClienteBrowser(String clienteBrowser) {
        this.clienteBrowser = clienteBrowser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemCompra> getItensPedido() {
        return itensPedido;
    }

    public final void setItensPedido(List<ItemCompra> itensPedido) {
        for (ItemCompra item : itensPedido) {
            item.setPedido(this);
        }
        this.itensPedido = itensPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    @Override
    public String toString() {
        return getNumero() != null ? getNumero().toString() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        boolean result = false;
        if (o instanceof Pedido) {
            Pedido outro = (Pedido) o;
            result = this.getCliente() != null &&
                    this.getData() != null &&
                    this.getCliente().equals(outro.getCliente()) &&
                    this.getData().equals(outro.getData());
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = getCliente() != null ? getCliente().hashCode() : 41;
        result = (getData() != null ? getData().hashCode() : 37) + 23 * result;
        return result;
    }

    @Override
    public Long getId() {
        return this.numero;
    }

    @Override
    public boolean isNew() {
        return numero == null;
    }
}