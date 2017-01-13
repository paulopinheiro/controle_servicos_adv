package br.com.pereirakienast.controleservicos.entity.cobranca;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "dispensa", catalog = "controladv", schema = "cobranca")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("D")
public class Dispensa extends Baixa implements Serializable {
    @Column(name="motivo",nullable=false,length=150)
    private String motivo;

    public Dispensa() {}

    public Dispensa(Obrigacao obrigacao) {
        super(obrigacao);
    }

    @Override
    public Dispensa copia() {
        Dispensa resposta = new Dispensa(this.getObrigacao());
        resposta.setDataBaixa(this.getDataBaixa());
        resposta.setObservacao(this.getObservacao());
        resposta.setMotivo(this.getMotivo());

        return resposta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        String dataFormatada;
        if (this.getDataBaixa()==null) dataFormatada = null;
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada = sdf.format(this.getDataBaixa());
        }
        return "Dispensado em " + dataFormatada + " (" + motivo + ')';
    }
}
