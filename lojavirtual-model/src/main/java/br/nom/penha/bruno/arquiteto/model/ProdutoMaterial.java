package br.nom.penha.bruno.arquiteto.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
public class ProdutoMaterial extends Produto {

    @Column(name = "taxa_entrega")
    private double taxaEntrega;

    public ProdutoMaterial() {
    }

    public ProdutoMaterial(Produto produto) {
        super(produto);
        if (produto instanceof ProdutoMaterial) {
            this.taxaEntrega = (((ProdutoMaterial) produto).getTaxaEntrega());
        }
    }

    public ProdutoMaterial(Categoria categoria, String marca, String nome, double preco, boolean ativo,
            double taxaEntrega) {
        super(categoria, marca, nome, preco, ativo);
        this.taxaEntrega = taxaEntrega;
    }

    public ProdutoMaterial(Integer id, Categoria categoria, String marca, String nome, double preco, boolean ativo,
            double taxaEntrega) {
        this(categoria, marca, nome, preco, ativo, taxaEntrega);
        this.id = id;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }
}
