package br.com.pereirakienast.controleservicos.mbeans.interfaces;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.util.ContextoJSF;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

public abstract class AbBasicoMB<T> {
    private T elemento;

    protected abstract AbstractFacade getFacade();
    public abstract boolean isNovoElemento();
    protected abstract T novainstanciaElemento();

    public void salvar(ActionEvent evt) {
        try {
            getFacade().salvar(this.getElemento());
            mensagemSucesso("Registro salvo com sucesso");
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            mensagemErro(ex.getLocalizedMessage());
            Logger.getGlobal().log(Level.SEVERE, ex.getLocalizedMessage());
        }
    }

    public void limparElemento(ActionEvent evt) {
        setElemento(null);
    }

    public void excluir(ActionEvent evt) {
        try {
            getFacade().excluir(this.getElemento());
            mensagemSucesso("Registro removido com sucesso");
            setElemento(null);
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            mensagemErro(ex.getLocalizedMessage());
            Logger.getGlobal().log(Level.SEVERE, ex.getLocalizedMessage());
        }
    }

    protected T getElemento() {
        if (this.elemento==null) this.elemento = novainstanciaElemento();
        return elemento;
    }

    protected void setElemento(T elemento) {
        this.elemento = elemento;
    }

    protected void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
        System.out.println(mensagem);
    }

    protected void mensagemSucesso(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,null));
    }

    protected void mensagemAviso(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,mensagem,null));
    }
}
