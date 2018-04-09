package br.nom.penha.bruno.arquiteto.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "pagamentos_boleto")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class PagamentoBoleto extends Pagamento {

    private String cedente;
    private String sacado;
    @Column(name = "codigo_barras")
    private String numeroCodigoBarras;
    @Column(name = "data_vencimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVencimento;
    @Transient
    private Banco banco;

    public PagamentoBoleto() {
    }

    public PagamentoBoleto(Pagamento pagamento) {
        super(pagamento);
        if (pagamento instanceof PagamentoBoleto) {
            this.cedente = (((PagamentoBoleto) pagamento).getCedente());
            this.sacado = (((PagamentoBoleto) pagamento).getSacado());
            this.numeroCodigoBarras =(((PagamentoBoleto) pagamento).getNumeroCodigoBarras());
            this.dataVencimento = (((PagamentoBoleto) pagamento).getDataVencimento());
            this.banco = (((PagamentoBoleto) pagamento).getBanco());
        }
    }

    public PagamentoBoleto(Date dataPagamento, double valor, String cedente, String sacado,
            String numeroCodigoBarras, Date dataVencimento, Banco banco) {
        super(dataPagamento, valor);
        this.cedente = cedente;
        this.sacado = sacado;
        this.numeroCodigoBarras = numeroCodigoBarras;
        this.dataVencimento = dataVencimento;
        this.banco = banco;
    }

    public PagamentoBoleto(Integer id, Date dataPagamento, double valor, String cedente, String sacado,
            String numeroCodigoBarras, Date dataVencimento, Banco banco) {
        this(dataPagamento, valor, cedente, sacado, numeroCodigoBarras, dataVencimento, banco);
        this.id = id;
    }

    public String getCedente() {
        return cedente;
    }

    public void setCedente(String cedente) {
        this.cedente = cedente;
    }

    public String getSacado() {
        return sacado;
    }

    public void setSacado(String sacado) {
        this.sacado = sacado;
    }

    public String getNumeroCodigoBarras() {
        return numeroCodigoBarras;
    }

    public void setNumeroCodigoBarras(String numeroCodigoBarras) {
        this.numeroCodigoBarras = numeroCodigoBarras;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
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
        if (o instanceof PagamentoBoleto) {
            PagamentoBoleto outro = (PagamentoBoleto) o;
            result = this.getNumeroCodigoBarras() != null
                    && this.getDataPagamento() != null
                    && this.getNumeroCodigoBarras().equals(outro.getNumeroCodigoBarras())
                    && this.getDataPagamento().equals(outro.getDataPagamento());
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = getNumeroCodigoBarras() != null ? getNumeroCodigoBarras().hashCode() : 41;
        result = (getDataPagamento() != null ? getDataPagamento().hashCode() : 37) + 23 * result;
        return result;
    }
}