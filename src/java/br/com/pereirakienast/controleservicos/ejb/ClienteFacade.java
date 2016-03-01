package br.com.pereirakienast.controleservicos.ejb;

import br.jus.trt12.paulopinheiro.cpfcnpjutil.Validador;
import br.com.pereirakienast.controleservicos.entity.Cliente;
import br.com.pereirakienast.controleservicos.entity.Documento;
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
    @PersistenceContext(unitName = "pkServicosPU")
    private EntityManager em;

    public ClienteFacade() {
        super(Cliente.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
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
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
        Root<Cliente> root = cq.from(Cliente.class);
        cq.select(root);

        Predicate cpfCnpj = cb.conjunction();
        Predicate nome = cb.conjunction();
        Predicate qualificacao = cb.conjunction();
        Predicate telefone = cb.conjunction();
        Predicate email = cb.conjunction();
        Predicate redeSocial = cb.conjunction();
        Predicate ativo;
        Predicate advogado = cb.conjunction();

        if ((filtro.getCpfCnpj()!=null)&&(!filtro.getCpfCnpj().isEmpty())) {
            Expression<String> a_cpfCnpj = root.get("cpfCnpj");
            cpfCnpj = cb.like(cb.upper(a_cpfCnpj), filtro.getCpfCnpj().toUpperCase());
        }
        if ((filtro.getNome()!=null)&&(!filtro.getNome().isEmpty())) {
            Expression<String> a_nome = root.get("nome");
            nome = cb.like(cb.upper(a_nome), filtro.getNome().toUpperCase());
        }
        if ((filtro.getQualificacao()!=null)&&(!filtro.getQualificacao().isEmpty())) {
            Expression<String> a_qualificacao = root.get("qualificacao");
            qualificacao = cb.like(cb.upper(a_qualificacao), filtro.getQualificacao().toUpperCase());
        }
        if ((filtro.getTelefone()!=null)&&(!filtro.getTelefone().isEmpty())) {
            Expression<String> a_telefone = root.get("telefone");
            telefone = cb.like(cb.upper(a_telefone), filtro.getTelefone().toUpperCase());
        }
        if ((filtro.getEmail()!=null)&&(!filtro.getEmail().isEmpty())) {
            Expression<String> a_email = root.get("email");
            email = cb.like(cb.upper(a_email), filtro.getEmail().toUpperCase());
        }
        if ((filtro.getRedeSocial()!=null)&&(!filtro.getRedeSocial().isEmpty())) {
            Expression<String> a_redeSocial = root.get("redeSocial");
            redeSocial = cb.like(cb.upper(a_redeSocial), filtro.getRedeSocial().toUpperCase());
        }
        if (filtro.getAdvogado()!=null) {
            Expression<String> a_advogado = root.get("advogado");
            advogado = cb.equal(a_advogado, filtro.getAdvogado());
        }

        ativo = cb.equal(root.get("ativo"), filtro.isAtivo());

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
            if (documento.getCliente()==null) throw new LogicalException("Informe o cliente titular do documento");
            if (documento.getTipo()==null) throw new LogicalException("Informe o tipo do documento");
            if ((documento.getNumero()==null)||(documento.getNumero().isEmpty())) throw new LogicalException("Informe o número do documento");

            if (documento.getId()==null) {
                getEntityManager().persist(documento);
                getEntityManager().getEntityManagerFactory().getCache().evict(Cliente.class);
            }
            else getEntityManager().merge(documento);
        }
    }

    public void excluirDocumento(Documento documento) throws LogicalException {
        if (!(documento==null)) {
            getEntityManager().remove(getEntityManager().merge(documento));
            getEntityManager().getEntityManagerFactory().getCache().evict(Cliente.class);
        }
    }

    public List<Documento> refreshListaDocumentos (Cliente cliente)  {
        List<Documento> resposta;
        Query pesquisa = getEntityManager().createNamedQuery("Documento.findByCliente");
        pesquisa.setParameter("cliente", cliente);
        resposta = pesquisa.getResultList();
        if (!(resposta==null)) Collections.sort(resposta);
        return resposta;
    }
}
