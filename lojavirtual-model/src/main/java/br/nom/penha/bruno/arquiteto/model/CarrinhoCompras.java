package br.nom.penha.bruno.arquiteto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoCompras implements Serializable{
    
    private List<ItemCompra> itens;
    private double precoTotal;
    
    public CarrinhoCompras(){
    }
    
    public CarrinhoCompras(ItemCompra item) {
        itens = new ArrayList();
        itens.add(item);
        precoTotal += item.getProduto().getPreco();
    }
    
    public java.util.List getItens() {
        return itens;
    }
    
    public void addItem(ItemCompra item) {
        for (ItemCompra temp : itens) {
            if(temp.getProduto().equals(item.getProduto())){
                temp.setQuantidade(temp.getQuantidade()+1);
                precoTotal += item.getProduto().getPreco();
                return;
            }
        }
        itens.add(item);
        precoTotal += item.getProduto().getPreco();        
    }
    
    public void removeItem(ItemCompra item) {
        for(int i=0;i<itens.size();i++) {
            ItemCompra temp = (ItemCompra) itens.get(i);
            if(temp.getProduto().equals(item.getProduto())){
                int quantidade = temp.getQuantidade();
                itens.remove(i);
                precoTotal -= item.getProduto().getPreco()*quantidade;                
                return;
            }
        }
    }
    
    public Pedido gerarPedido(){
        return null;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }
    
}
