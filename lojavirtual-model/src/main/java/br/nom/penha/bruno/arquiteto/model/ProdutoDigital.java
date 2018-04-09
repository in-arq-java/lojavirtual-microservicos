package br.nom.penha.bruno.arquiteto.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("D")
public class ProdutoDigital extends Produto {

    @Column(name = "nome_arquivo")
    private String nomeArquivo;
    @Column(name = "tamanho_arquivo")
    private int tamanho;

    public ProdutoDigital() {
    }

    public ProdutoDigital(Produto produto) {
        super(produto);
        if (produto instanceof ProdutoDigital) {
            this.nomeArquivo = (((ProdutoDigital) produto).getNomeArquivo());
            this.tamanho = (((ProdutoDigital) produto).getTamanho());
        }
    }

    public ProdutoDigital(Categoria categoria, String marca, String nome, double preco, boolean ativo,
            String nomeArquivo, int tamanho) {
        super(categoria, marca, nome, preco, ativo);
        this.nomeArquivo = nomeArquivo;
        this.tamanho = tamanho;
    }

    public ProdutoDigital(Integer id, Categoria categoria, String marca, String nome, double preco, boolean ativo,
            String nomeArquivo, int tamanho) {
        this(categoria, marca, nome, preco, ativo, nomeArquivo, tamanho);
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}