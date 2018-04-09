package br.nom.penha.bruno.arquiteto.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import br.nom.penha.bruno.arquiteto.dao.PersistentEntity;

@Entity
@Table(name = "produtos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_produto")
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p ORDER BY p.id", hints =
    @QueryHint(name = "org.hibernate.cacheable", value = "true"))})
public abstract class Produto implements Serializable, PersistentEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    protected String nome;
    protected double preco;
    protected String marca;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    protected Categoria categoria;
    protected boolean ativo = true;

    public Produto() {
    }

    public Produto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        this.marca = produto.getMarca();
        this.categoria = produto.getCategoria();
        this.ativo = produto.isAtivo();
    }

    public Produto(Categoria categoria, String marca, String nome, double preco, boolean ativo) {
        this.categoria = categoria;
        this.marca = marca;
        this.nome = nome;
        this.preco = preco;
        this.ativo = ativo;
    }

    public Produto(Integer id, Categoria categoria, String marca, String nome, double preco, boolean ativo) {
        this(categoria, marca, nome, preco, ativo);
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return getNome() + " - " + getId();
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
        if (o instanceof Produto) {
            Produto outro = (Produto) o;
            result = this.getNome() != null
                    && this.getMarca() != null
                    && this.getNome().equals(outro.getNome())
                    && this.getMarca().equals(outro.getMarca());
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = getNome() != null ? getNome().hashCode() : 41;
        result = (getMarca() != null ? getMarca().hashCode() : 37) + 17 * result;
        return result;
    }
    
    @Override
    public boolean isNew() {
        return id == null;
    }
}
