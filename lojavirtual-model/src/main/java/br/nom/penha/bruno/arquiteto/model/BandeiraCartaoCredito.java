package br.nom.penha.bruno.arquiteto.model;

import java.io.Serializable;

public class BandeiraCartaoCredito implements Serializable {

    private Integer id;
    private String nome;

    public BandeiraCartaoCredito() {
    }

    public BandeiraCartaoCredito(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

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
        if (o instanceof BandeiraCartaoCredito) {
            BandeiraCartaoCredito outro = (BandeiraCartaoCredito) o;
            result = this.getNome() != null
                    && this.nome.equals(outro.nome);
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = getNome() != null ? getNome().hashCode() : 41;
        return result;
    }

}
