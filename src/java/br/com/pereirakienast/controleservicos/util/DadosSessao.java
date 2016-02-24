package br.com.pereirakienast.controleservicos.util;

import br.com.pereirakienast.controleservicos.entity.Advogado;

public class DadosSessao {
public synchronized static Advogado getAdvogadoSessao() {
        Advogado advogadoSessao = (Advogado) ContextoJSF.getSessionAttribute("advogadoSessao");
        return advogadoSessao;
    }

    public synchronized static void setAdvogadoSessao(Advogado advogadoSessao) {
        ContextoJSF.setSessionAttribute("advogadoSessao", advogadoSessao);
    }    
}
