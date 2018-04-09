package br.nom.penha.bruno.arquiteto.comparator;

import java.util.Comparator;

import br.nom.penha.bruno.arquiteto.model.Cliente;
import br.nom.penha.bruno.arquiteto.model.Pedido;

public class ComparatorPedidoClienteNumero implements Comparator<Pedido> {

    @Override
    public int compare(Pedido p1, Pedido p2) {

        int compId = compareCliente(p1.getCliente(), p2.getCliente());
        if (compId != 0) {
            return compId;
        } else {
            return compareNumero(p1, p2);
        }

    }

    private int compareCliente(Cliente cli1, Cliente cli2) {

        Integer idCli1 = cli1 != null ? cli1.getId() : null;
        Integer idCli2 = cli2 != null ? cli2.getId() : null;

        if (idCli1 == null && idCli2 == null) {
            return 0;
        } else if (idCli1 == null) {
            return -1;
        } else if (idCli2 == null) {
            return +1;
        } else {
            return idCli1.compareTo(idCli2);
        }

    }

    private int compareNumero(Pedido p1, Pedido p2) {

        if (p1.getNumero() == null && p2.getNumero() == null) {
            return 0;
        } else if (p1.getNumero() == null) {
            return -1;
        } else if (p2.getNumero() == null) {
            return +1;
        } else {
            return p1.getNumero().compareTo(p2.getNumero());
        }

    }
}
