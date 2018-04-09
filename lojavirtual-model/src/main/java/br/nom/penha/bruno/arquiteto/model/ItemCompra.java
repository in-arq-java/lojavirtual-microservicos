package br.nom.penha.bruno.arquiteto.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pedidos_itens")
public class ItemCompra implements Serializable{

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private int quantidade;
    private int desconto;

    @ManyToOne()
    @JoinColumn(name="id_produto")
    private Produto produto;

    @ManyToOne()
    @JoinColumn(name="numero_pedido")
    private Pedido pedido;
    
    public ItemCompra(){
    }
    
    public ItemCompra(Produto produto, int quantidade, int desconto) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.desconto = desconto;
    }
    
    public ItemCompra(Integer id, Produto produto, int quantidade, int desconto) {
        this(produto, quantidade, desconto);
        this.id = id;
    }
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public int getQuantidade(){
        return quantidade;
    }
    
    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }
    
    public int getDesconto(){
        return desconto;
    }
    
    public void setDesconto(int desconto){
        this.desconto = desconto;
    }
    
    public Produto getProduto(){
        return produto;
    }
    
    public void setProduto(Produto produto){
        this.produto = produto;
    }
    
    @Override
    public String toString() {
        return getId() != null ? getId().toString() : null;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    

    @Override
    public boolean equals(Object o) {
    	if (o == null) return false;
      	if (this == o) return true;      
        boolean result = false;
        if (o instanceof ItemCompra){
            ItemCompra outro = (ItemCompra) o;
            result = this.getQuantidade() == outro.getQuantidade() && 
                     this.getProduto() != null && 
                     this.getProduto().equals(outro.getProduto());
        }
        return result;
    }
    
    @Override
    public int hashCode() {
        int result = getQuantidade(); 
        result = (getProduto() != null ? getProduto().hashCode() : 41) + 23 * result; 
        return result;
    }
    
    
}