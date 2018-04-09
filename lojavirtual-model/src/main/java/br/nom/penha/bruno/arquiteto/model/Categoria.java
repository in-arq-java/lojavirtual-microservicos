package br.nom.penha.bruno.arquiteto.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import br.nom.penha.bruno.arquiteto.dao.PersistentEntity;

@XmlRootElement
@Entity
@Table(name = "categorias")
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c ORDER BY c.id", hints =
    @QueryHint(name = "org.hibernate.cacheable", value = "true"))})
public class Categoria implements Serializable, PersistentEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(max=255)
    @NotNull
    private String descricao;
    private boolean ativo = true;

    public Categoria() {
    }

    public Categoria(Integer id, String descricao, boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return getDescricao() + " - " + getId();
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
        if (o instanceof Categoria) {
            Categoria outro = (Categoria) o;
            result = this.getDescricao() != null
                    && this.getDescricao().equals(outro.getDescricao());
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = getDescricao() != null ? getDescricao().hashCode() : 41;
        return result;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}