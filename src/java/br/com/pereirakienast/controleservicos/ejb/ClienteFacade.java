package br.com.pereirakienast.controleservicos.ejb;

import br.jus.trt12.paulopinheiro.cpfcnpjutil.Validador;
import br.com.pereirakienast.controleservicos.entity.Cliente;
import br.com.pereirakienast.controleservicos.entity.Documento;
import br.com.pereirakienast.controleservicos.entity.Historico;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {
    public ClienteFacade() {
        super(Cliente.class);
    }

    @Override
    public void salvar(Cliente entity) throws LogicalException {
        if (entity != null) {
            if (entity.getNome()==null||entity.getNome().trim().isEmpty())
                throw new LogicalException("Informe o nome do Cliente");
            if (entity.getAdvogado()==null)
                throw new LogicalException("Informe o advogado preferencial do cliente");
            if ((entity.getCpfCnpj()!=null)&&(!entity.getCpfCnpj().isEmpty())&& !Validador.isCpfOuCnpjValido(entity.getCpfCnpj())) throw new LogicalException("CPF/CNPJ inválido");

            entity.setNome(entity.getNome().trim().toUpperCase());

            if (entity.getId()==null) super.create(entity);
            else super.edit(entity);
        }
    }

    @Override
    public void excluir(Cliente entity) throws LogicalException {
        if (entity != null) {
            if (!entity.getServicosPrestados().isEmpty())
                throw new LogicalException("Não é possível excluir cliente " + entity.getNome() + ", pois existem cadastrados serviços prestados ao mesmo ");

            super.remove(entity);
        }
    }

    @Override
    protected CriteriaQuery<Cliente> getCq(Cliente filtro) throws LogicalException {
        CriteriaQuery<Cliente> cq = super.getCq(filtro);

        Predicate cpfCnpj = getPredicateLike(filtro.getCpfCnpj(),"cpfCnpj");
        Predicate nome = getPredicateLike(filtro.getNome(),"nome");
        Predicate qualificacao = getPredicateLike(filtro.getQualificacao(),"qualificacao");
        Predicate telefone = getPredicateLike(filtro.getTelefone(),"telefone");
        Predicate email = getPredicateLike(filtro.getEmail(),"email");
        Predicate redeSocial = getPredicateLike(filtro.getRedeSocial(),"redeSocial");
        Predicate ativo = getPredicateBoolean(filtro.isAtivo(),"ativo");
        Predicate advogado = getPredicateEqual(filtro.getAdvogado(),"advogado");

        cq.where(cpfCnpj,nome,qualificacao,telefone,email,redeSocial,ativo,advogado);

        return cq;
    }

    public List<Cliente> findAtivos() {
        List<Cliente> resposta;

        Query pesquisa = getEntityManager().createNamedQuery("Cliente.findAtivos");
        resposta = pesquisa.getResultList();

        if (resposta== null) {
            
        } else {
            Collections.sort(resposta);
        }

        return resposta;
    }

    public void salvarDocumento(Documento documento) throws LogicalException {
        if (!(documento==null)) {
            if (documento.getTipo()==null) throw new LogicalException("Informe o tipo do documento");
            if (documento.getCliente()==null) throw new LogicalException("Informe o cliente titular do documento");
            if ((documento.getNumero()==null)||(documento.getNumero().isEmpty())) throw new LogicalException("Informe o número do documento");

            if (documento.getId()==null) {
                getEntityManager().persist(documento);
            } else getEntityManager().merge(documento);
            getEntityManager().getEntityManagerFactory().getCache().evict(Cliente.class);
        }
    }

    public List<Documento> refreshListaDocumentos(Cliente cliente) {
        List<Documento> resposta;
        Query pesquisa = getEntityManager().createNamedQuery("Documento.findByCliente");
        pesquisa.setParameter("cliente", cliente);
        resposta = pesquisa.getResultList();
        return resposta;
    }

    public void excluirDocumento(Documento documento) throws LogicalException {
        if (!(documento==null)) {
            getEntityManager().remove(getEntityManager().merge(documento));
            getEntityManager().getEntityManagerFactory().getCache().evict(Cliente.class);
        }
    }

    public void salvarHistorico(Historico historico) throws LogicalException {
        if (historico != null) {
            if (historico.getDataHistorico()==null) throw new LogicalException("Informe a data do histórico");
            if (historico.getCliente()==null) throw new LogicalException("Informe o cliente ao qual se refere o histórico");
            if (historico.getDescricao()==null||historico.getDescricao().trim().isEmpty()) throw new LogicalException("Forneça uma descrição do histórico");
            if (historico.getId()==null) getEntityManager().persist(historico);
            else getEntityManager().merge(historico);
            getEntityManager().getEntityManagerFactory().getCache().evict(Cliente.class);
        }
    }

    public List<Historico> refreshListaHistoricos(Cliente cliente) {
        List<Historico> resposta;
        Query pesquisa = getEntityManager().createNamedQuery("Historico.findByCliente");
        pesquisa.setParameter("cliente", cliente);
        resposta = pesquisa.getResultList();
        return resposta;
    }

    public void excluirHistorico(Historico historico) throws LogicalException {
        if (!(historico==null)) {
            getEntityManager().remove(getEntityManager().merge(historico));
            getEntityManager().getEntityManagerFactory().getCache().evict(Cliente.class);
        }
    }
}
