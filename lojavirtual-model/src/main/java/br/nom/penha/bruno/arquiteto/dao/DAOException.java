package br.nom.penha.bruno.arquiteto.dao;

public class DAOException extends RuntimeException {

    private static final long serialVersionUID = -1432728222378235769L;

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable t) {
        super(message, t);
    }

    public DAOException(Throwable t) {
        super(t);
    }
}
