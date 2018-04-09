package br.nom.penha.bruno.arquiteto.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.nom.penha.bruno.arquiteto.dao.PersistentEntity;

@Entity
@Table(name = "pagamentos")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(name = "Pagamento.findAll", query = "SELECT p FROM Pagamento p ORDER BY p.id", hints =
    @QueryHint(name = "org.hibernate.cacheable", value = "true"))})
public abstract class Pagamento implements Serializable, PersistentEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "data_pagamento")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dataPagamento;
    protected double valor;

    public Pagamento() {
    }

    public Pagamento(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.dataPagamento = pagamento.getDataPagamento();
        this.valor = pagamento.getValor();
    }

    public Pagamento(Date dataPagamento, double valor) {
        this.dataPagamento = dataPagamento;
        this.valor = valor;
    }

    public Pagamento(Integer id, Date dataPagamento, double valor) {
        this(dataPagamento, valor);
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return getId() != null ? getId().toString() : null;
    }
    
    @Override
    public boolean isNew() {
        return id == null;
    }    
}