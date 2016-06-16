package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.ejb.ClienteFacade;
import br.com.pereirakienast.controleservicos.ejb.ServicoFacade;
import br.com.pereirakienast.controleservicos.ejb.TipoServicoFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.entity.ParceriaServico;
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

    private ParceriaServico parceriaServico;

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

    public ParceriaServico getParceriaServico() {
        if (this.parceriaServico==null) this.parceriaServico = new ParceriaServico(getServico());
        return parceriaServico;
    }

    public void setParceriaServico(ParceriaServico parceriaServico) {
        this.parceriaServico = parceriaServico;
    }

    public void limparParceria(ActionEvent evt) {
        setParceriaServico(null);
    }

    public void alterarParceria(ActionEvent evt) {
        try {
            validarParceria();
            addParceria();
            setParceriaServico(null);
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        }
    }

    private void validarParceria() throws LogicalException {
        if (this.getParceriaServico().getAdvogado()==null) throw new LogicalException("Escolha o advogado que fará a parceria");
        if ((this.getServico().getAdvogado() != null)&&(this.getParceriaServico().getAdvogado().equals(this.getServico().getAdvogado())))
            throw new LogicalException("O(A) advogado(a) " + getServico().getAdvogado() + " já é prestador(a) do serviço");
    }

    private void addParceria() throws LogicalException  {
        if (this.getServico().getParcerias()==null) this.getServico().setParcerias(new ArrayList<ParceriaServico>());
        if (this.getServico().getParcerias().contains(this.getParceriaServico()))
            throw new LogicalException("O(A) advogado(a) " + this.getParceriaServico().getAdvogado() + " já é parceiro(a) no serviço");
        this.getServico().getParcerias().add(this.getParceriaServico());
    }

    public void removerParceria(ParceriaServico parceria) {
        this.getServico().getParcerias().remove(parceria);
        setParceriaServico(null);
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
