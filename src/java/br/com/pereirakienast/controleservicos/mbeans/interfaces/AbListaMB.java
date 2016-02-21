package br.com.pereirakienast.controleservicos.mbeans.interfaces;

import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;

public abstract class AbListaMB<T> extends AbBasicoMB<T> {
    private List<T> lista;

    @Override
    public void salvar(ActionEvent evt) {
        try {
            getFacade().salvar(this.getElemento());
            setElemento(null);
            setLista(null);
            mensagemSucesso("Registro salvo com sucesso");
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            mensagemErro(ex.getLocalizedMessage());
            Logger.getGlobal().log(Level.SEVERE, ex.getLocalizedMessage());
        }
    }

    @Override
    public void excluir(ActionEvent evt) {
        try {
            getFacade().excluir(this.getElemento());
            setElemento(null);
            setLista(null);
            mensagemSucesso("Registro removido com sucesso");
        } catch (Exception ex) {
            mensagemErro(ex.getLocalizedMessage());
            Logger.getGlobal().log(Level.SEVERE, ex.getLocalizedMessage());
        }
    }

    public void filtrar(ActionEvent evt) {
        try {
            this.setLista(this.getFacade().findFiltro(this.getElemento()));
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            mensagemErro(ex.getLocalizedMessage());
            Logger.getGlobal().log(Level.SEVERE, ex.getLocalizedMessage());
        }
    }

    public int getQuantLista() {
        if (this.getLista()==null) return 0;
        return this.getLista().size();
    }

    protected List<T> getLista() {
        if (this.listaNula()) {
            this.lista = getFacade().findAll();
        }
        return lista;
    }

    protected void setLista(List<T> lista) {
        this.lista = lista;
    }

    protected boolean listaNula() {
        return this.lista==null;
    }
}
