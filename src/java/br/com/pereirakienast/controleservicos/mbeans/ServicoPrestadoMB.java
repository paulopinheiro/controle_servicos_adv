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
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.mbeans.comum.AbBasicoMB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ServicoPrestadoMB extends AbBasicoMB<ServicoPrestado> implements Serializable {
    @EJB private ServicoFacade servicoFacade;
    @EJB private AdvogadoFacade advogadoFacade;
    @EJB private ClienteFacade clienteFacade;
    @EJB private TipoServicoFacade tipoServicoFacade;
    @Inject private SessaoMB sessaoMB;

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

    public void limparAssessoria(ActionEvent evt) {
        setAssessoriaServico(null);
    }

    public void alterarAssessoria(ActionEvent evt) {
        try {
            validarAssessoria();
            addAssessoria();
            setAssessoriaServico(null);
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        }
    }

    private void validarAssessoria() throws LogicalException {
        if (this.getAssessoriaServico().getAdvogado()==null) throw new LogicalException("Escolha o advogado que fará a assessoria");
        if ((this.getServico().getAdvogado() != null)&&(this.getAssessoriaServico().getAdvogado().equals(this.getServico().getAdvogado())))
            throw new LogicalException("O advogado " + getServico().getAdvogado() + " já é prestador do serviço");
    }

    private void addAssessoria() throws LogicalException  {
        if (this.getServico().getAssessorias()==null) this.getServico().setAssessorias(new ArrayList<AssessoriaServico>());
        if (this.getServico().getAssessorias().contains(this.getAssessoriaServico()))
            throw new LogicalException("O advogado " + this.getAssessoriaServico().getAdvogado() + " já é assessor no serviço");
        this.getServico().getAssessorias().add(this.getAssessoriaServico());
    }

    public void removerAssessoria(AssessoriaServico assessoria) {
        this.getServico().getAssessorias().remove(assessoria);
        setAssessoriaServico(null);
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
        return new ServicoPrestado(sessaoMB.getAdvogadoSessao());
    }
}
