package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.ejb.ClienteFacade;
import br.com.pereirakienast.controleservicos.ejb.ServicoFacade;
import br.com.pereirakienast.controleservicos.ejb.TipoServicoFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.entity.AssessoriaServico;
import br.com.pereirakienast.controleservicos.entity.Cliente;
import br.com.pereirakienast.controleservicos.entity.ServicoPrestado;
import br.com.pereirakienast.controleservicos.entity.TipoServico;
import br.com.pereirakienast.controleservicos.mbeans.comum.AbBasicoMB;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

@ManagedBean
@ViewScoped
public class ServicoPrestadoMB extends AbBasicoMB<ServicoPrestado> implements Serializable {
    @EJB private ServicoFacade servicoFacade;
    @EJB private AdvogadoFacade advogadoFacade;
    @EJB private ClienteFacade clienteFacade;
    @EJB private TipoServicoFacade tipoServicoFacade;

    private AssessoriaServico assessoriaServico;

    public ServicoPrestadoMB() {}

    public List<Advogado> getListaAdvogados() {
        return advogadoFacade.findAtivos();
    }

    public List<Cliente> getListaClientes() {
        return clienteFacade.findAtivos();
    }

    public List<TipoServico> getListaTiposServico() {
        return tipoServicoFacade.findAll();
    }

    public AssessoriaServico getAssessoriaServico() {
        if (this.assessoriaServico==null) this.assessoriaServico = new AssessoriaServico(getServico());
        return assessoriaServico;
    }

    public void setAssessoriaServico(AssessoriaServico assessoriaServico) {
        this.assessoriaServico = assessoriaServico;
    }

    public void alterarAssessoria(ActionEvent evt) {
        if (this.getAssessoriaServico().getAdvogado() == null) {
            mensagemErro("Escolha o advogado que fará a assessoria");
        } else {
            if ((this.getServico().getAdvogado() != null) && (getAssessoriaServico().getAdvogado().equals(this.getServico().getAdvogado()))) {
                mensagemErro("O advogado " + getServico().getAdvogado() + " já é prestador do serviço");
            } else {
                this.getServico().getAssessorias().add(this.getAssessoriaServico());
            }
        }
    }

    public void removerAssessoria(ActionEvent evt) {
        if (this.getAssessoriaServico().getId()!=null)
            this.getServico().getAssessorias().remove(this.getAssessoriaServico());
    }

    public ServicoPrestado getServico() {
        return this.getElemento();
    }

    public void setServico(ServicoPrestado servico) {
        this.setElemento(servico);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.servicoFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getElemento().getId()==null || this.getElemento().getId()==0;
    }

    @Override
    protected ServicoPrestado novainstanciaElemento() {
        return new ServicoPrestado();
    }
}
