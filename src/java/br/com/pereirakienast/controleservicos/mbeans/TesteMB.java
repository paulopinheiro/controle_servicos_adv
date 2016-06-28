package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.entity.ServicoPrestado;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named
@ViewScoped
public class TesteMB implements Serializable {
    private ServicoPrestado servico;

    public TesteMB() {}

    public ServicoPrestado getServico() {
        if (this.servico==null) return new ServicoPrestado();
        return servico;
    }

    public void setServico(ServicoPrestado servico) {
        this.servico = servico;
    }

}
