package br.com.pereirakienast.controleservicos.mbeans.comum;

import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.util.List;
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
            mensagemErro("Erro de sistema. Favor contatar o responsável. Mensagem: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void excluir(ActionEvent evt) {
        try {
            System.out.println("Vamos excluir o serviço (Managed Bean)");
            getFacade().excluir(this.getElemento());
            setElemento(null);
            setLista(null);
            mensagemSucesso("Registro removido com sucesso");
        } catch (Exception ex) {
            mensagemErro("Erro de sistema. Favor contatar o responsável. Mensagem: " + ex.getLocalizedMessage());
        }
    }

    public void filtrar(ActionEvent evt) {
        try {
            this.setLista(this.getFacade().findFiltro(this.getElemento()));
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            mensagemErro("Erro de sistema. Favor contatar o responsável. Mensagem: " + ex.getLocalizedMessage());
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
