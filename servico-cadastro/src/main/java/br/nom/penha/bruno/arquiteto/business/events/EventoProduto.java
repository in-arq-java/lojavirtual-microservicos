package br.nom.penha.bruno.arquiteto.business.events;

import br.nom.penha.bruno.arquiteto.model.Produto;

/**
 *
 * @author Arquiteto
 */
public class EventoProduto {

    private final Produto produto;
    private final TipoEventoProduto tipo;

    public EventoProduto(TipoEventoProduto tipo, Produto produto) {
        this.tipo = tipo;
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }

    public TipoEventoProduto getTipo() {
        return tipo;
    }
}
