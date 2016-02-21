package br.com.pereirakienast.controleservicos.mbeans.interfaces;

import java.util.List;

public abstract class AbListaRestritaMB<T> extends AbListaMB<T> {
    protected abstract List<T> getListaRestrita();

    @Override
    protected List<T> getLista() {
        if (this.listaNula()) setLista(this.getListaRestrita());
        return super.getLista();
    }
}
