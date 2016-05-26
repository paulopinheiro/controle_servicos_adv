package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.entity.TipoDocumento;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

@Stateless
public class TipoDocumentoFacade extends AbstractFacade<TipoDocumento> {
    public TipoDocumentoFacade() {
        super(TipoDocumento.class);
    }

    @Override
    public void salvar(TipoDocumento entity) throws LogicalException {
        if (entity !=null) {
            if (entity.getNome()==null||entity.getNome().isEmpty())
                throw new LogicalException("Informe o nome do tipo de documento");

            TipoDocumento pesq = this.getByNome(entity.getNome());
            if (pesq==null || pesq.getId().equals(entity.getId())) {
            } else {
                throw new LogicalException("Já existe um tipo de documento cadastrado com esse nome");
            }

            entity.setNome(entity.getNome().trim().toUpperCase());

            if (entity.getId()==null) super.create(entity);
            else super.edit(entity);
        }
    }

    private TipoDocumento getByNome(String nome) throws LogicalException {
        if (nome==null) return null;
        Query query = getEntityManager().createNamedQuery("TipoDocumento.findByNome");
        query.setParameter("nomeUpper", nome.trim().toUpperCase());
         try {
            return (TipoDocumento) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            throw new LogicalException("Há uma inconsistência no banco de dados. Dois tipos de documento estão cadastrados com o nome " + nome);
        }
    }

    @Override
    public void excluir(TipoDocumento entity) throws LogicalException {
        if (entity !=null) {
            if (!entity.getListaDocumentos().isEmpty()) {
                throw new LogicalException("Não é possível excluir o tipo de documento " + entity.getNome()+ ", pois existem documentos de clientes cadastrados desse tipo");
            }
            super.remove(entity);
        }
    }

    @Override
    protected CriteriaQuery<TipoDocumento> getCq(TipoDocumento filtro) throws LogicalException {
        CriteriaQuery<TipoDocumento> cq = super.getCq(filtro);

        Predicate nome = getPredicateLike(filtro.getNome(),"nome");
        cq.where(nome);

        return cq;
    }
    
}
