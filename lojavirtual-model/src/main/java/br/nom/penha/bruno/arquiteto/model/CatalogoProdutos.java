package br.nom.penha.bruno.arquiteto.model;

import java.io.Serializable;
import java.util.Date;

public class CatalogoProdutos implements Serializable{
    
    private String nome;
    private Date validade;
    private java.util.List produtos;
    
    public CatalogoProdutos(){
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public Date getValidade(){
        return validade;
    }
    
    public void setValidade(Date validade){
        this.validade = validade;
    }
    
    public java.util.List getProdutos() {
        return produtos;
    }
    
    public void setProdutos(java.util.List produtos) {
        this.produtos = produtos;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }
    
}