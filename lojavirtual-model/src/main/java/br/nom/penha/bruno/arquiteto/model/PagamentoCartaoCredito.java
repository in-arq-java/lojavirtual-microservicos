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
@Table(name = "pagamentos_cartao_credito")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class PagamentoCartaoCredito extends Pagamento {

    @Transient
    private BandeiraCartaoCredito bandeiraCartaoCredito;
    @Column(name = "nome_titular")
    private String nomeTitular;
    @Column(name = "numero_cartao")
    private String numeroCartao;
    @Column(name = "numero_confirmacao")
    private long numeroConfirmacao;
    @Column(name = "data_validade_cartao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataValidadeCartao;

    public PagamentoCartaoCredito() {
    }

    public PagamentoCartaoCredito(Pagamento pagamento) {
        super(pagamento);
        if (pagamento instanceof PagamentoCartaoCredito) {
            this.nomeTitular =(((PagamentoCartaoCredito) pagamento).getNomeTitular());
            this.numeroConfirmacao = (((PagamentoCartaoCredito) pagamento).getNumeroConfirmacao());
            this.numeroCartao =(((PagamentoCartaoCredito) pagamento).getNumeroCartao());
            this.dataValidadeCartao =(((PagamentoCartaoCredito) pagamento).getDataValidadeCartao());
            this.bandeiraCartaoCredito = (((PagamentoCartaoCredito) pagamento).getBandeiraCartaoCredito());
        }
    }

    public PagamentoCartaoCredito(Date dataPagamento, double valor, BandeiraCartaoCredito bandeiraCartaoCredito,
            String nomeTitular, String numeroCartao, long numeroConfirmacao, Date dataValidadeCartao) {
        super(dataPagamento, valor);
        this.bandeiraCartaoCredito = bandeiraCartaoCredito;
        this.nomeTitular = nomeTitular;
        this.numeroCartao = numeroCartao;
        this.numeroConfirmacao = numeroConfirmacao;
        this.dataValidadeCartao = dataValidadeCartao;
    }

    public PagamentoCartaoCredito(Integer id, Date dataPagamento, double valor, BandeiraCartaoCredito bandeiraCartaoCredito,
            String nomeTitular, String numeroCartao, long numeroConfirmacao, Date dataValidadeCartao) {
        this(dataPagamento, valor, bandeiraCartaoCredito, nomeTitular, numeroCartao, numeroConfirmacao, dataValidadeCartao);
        this.id = id;
    }

    public BandeiraCartaoCredito getBandeiraCartaoCredito() {
        return bandeiraCartaoCredito;
    }

    public void setBandeiraCartaoCredito(BandeiraCartaoCredito bandeiraCartaoCredito) {
        this.bandeiraCartaoCredito = bandeiraCartaoCredito;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public long getNumeroConfirmacao() {
        return numeroConfirmacao;
    }

    public void setNumeroConfirmacao(long numeroConfirmacao) {
        this.numeroConfirmacao = numeroConfirmacao;
    }

    public Date getDataValidadeCartao() {
        return dataValidadeCartao;
    }

    public void setDataValidadeCartao(Date dataValidadeCartao) {
        this.dataValidadeCartao = dataValidadeCartao;
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
        if (o instanceof PagamentoCartaoCredito) {
            PagamentoCartaoCredito outro = (PagamentoCartaoCredito) o;
            result = this.getNumeroCartao() != null
                    && this.getDataPagamento() != null
                    && this.getNumeroCartao().equals(outro.getNumeroCartao())
                    && this.getDataPagamento().equals(outro.getDataPagamento());
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = getNumeroCartao() != null ? getNumeroCartao().hashCode() : 47;
        result = (getDataPagamento() != null ? getDataPagamento().hashCode() : 41) + 13 * result;
        return result;
    }
}