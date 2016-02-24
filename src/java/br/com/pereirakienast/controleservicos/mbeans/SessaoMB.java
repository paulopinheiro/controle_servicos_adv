package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.util.ContextoJSF;
import br.com.pereirakienast.controleservicos.util.DadosSessao;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author paulopinheiro
 */
@ManagedBean
@SessionScoped
public class SessaoMB implements Serializable {
    public SessaoMB(){}

    public String desconectar() {
        ContextoJSF.getFacesSession().invalidate();
        return "/faces/index.xhtml";
    }

    public boolean isAdvogadoSessaoEmpty() {
        return DadosSessao.getAdvogadoSessao()==null || DadosSessao.getAdvogadoSessao().getId()==null;
    }

    public boolean isAdvogadoAdmin() {
        if (isAdvogadoSessaoEmpty()) return false;
        return DadosSessao.getAdvogadoSessao().isAdministrador();
    }

    public Advogado getAdvogadoSessao() {
        return DadosSessao.getAdvogadoSessao();
    }
}
