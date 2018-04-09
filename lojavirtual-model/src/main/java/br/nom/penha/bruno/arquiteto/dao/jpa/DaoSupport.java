package br.nom.penha.bruno.arquiteto.dao.jpa;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.nom.penha.bruno.arquiteto.dao.*;

/**
 * Classe abstrata que define os métodos principais de CRUD:
 * 
 * <ul>
 * 	<li> delete </li>
 * 	<li> findAll </li>
 * 	<li> findByPrimaryKey </li>
 * 	<li> save </li>
 * </ul>
 * 
 *  Seu uso simplifica a escrita das classes DAO, permitindo que se concentrem
 *  em implementar somente os métodos relativos a consultas. Mesmo a escrita
 *  desses métodos é facilitada pois a execução efetiva da consulta também é feita
 *  pela classe DaoSupport.
 *  
 *  Assim, uma subclasse precisa apenas definir o nome e parâmetros da consulta e
 *  chamar um dos métodos protected findByNamedQuery de DaoSupport.
 * 
 *
 * @param <E> classe da entidade de negócio
 * @param <PK> tipo da chave primária da entidade de negócio
 */
public abstract class DaoSupport<E extends PersistentEntity<PK>, PK extends Serializable> {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    protected EntityManager entityManager;
    private Class<E> entityClass = null;

    /**
     * metodo que utiliza reflection para descobrir com que classe de entidade
     * foi parametrizado o DAO
     * 
     * @return classe da entidade
     */
    @SuppressWarnings("unchecked")
    private Class<E> getEntityClass() {
        if (entityClass == null) {
            ParameterizedType paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
            entityClass = (Class<E>) paramType.getActualTypeArguments()[0];
        }
        return entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * metodo para recuperar do banco de dados a entidade através da chave
     * primária
     * 
     * @param pk
     *            valor da chave primária
     * @return entidade
     */
    public E findByPrimaryKey(PK pk) {
        System.out.println("============= Chamando findByPrimarykey ==========");
        if (pk == null) {
            throw new DAOException("A chave primaria não pode ser nula");
        }
        try {
            System.out.println("EntityManager = " + entityManager);
            System.out.println("Entity class = " + getEntityClass());
            return entityManager.find(getEntityClass(), pk);
        } catch (javax.persistence.PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /**
     * metodo chamado para inserir ou atualizar a entidade no banco de dados
     * 
     * @param entity
     *            entidade a ser persistida
     */
    public void save(E entity) {
        try {
            if (entity.isNew()) {
                entityManager.persist(entity);
            } else {
                entityManager.merge(entity);
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /**
     * método para remover uma entidade do banco de dados
     * 
     * @param entity
     *            entidade a ser removida
     */
    public void delete(E entity) {
        try {
            if (entityManager.contains(entity)) {
                entityManager.remove(entity);
            } else if (!entity.isNew()) {
                entityManager.remove(entityManager.merge(entity));
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /**
     * método para listar todos os registros da entidade no banco de dados
     * 
     * @return coleção com as entidades localizadas
     */
    public Collection<E> findAll() {
        return this.findByNamedQuery(getEntityClass().getSimpleName() + ".findAll");
    }
    
    /*=============================================================================================
     * 
     * Metodos genéricos de consultas chamados pelas sub-classes
     * 
    ==============================================================================================*/
    /**
     * 
     * metodo chamado pelas subclasses para execução de consultas com parametros
     * nomeados
     * 
     * @param query
     *            consulta em JPA QL
     * @param parameters
     *            Map com os parametros da consulta
     * @return resultado da consulta
     */
    protected Collection<E> findByQuery(String query,
            Map<String, Object> parameters) {
        try {
            Query q = entityManager.createQuery(query);
            return execQueryNamedParameters(q, parameters);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /**
     * 
     * metodo chamado pelas subclasses para execução de consultas com parametros
     * nomeados que retornam uma única entidade
     * 
     * @param query
     *            consulta em JPA QL
     * @param parameters
     *            Map com os parametros da consulta
     * @return resultado da consulta
     */
    protected E findSingleByQuery(String query, Map<String, Object> parameters) {
        try {
            Query q = entityManager.createQuery(query);
            return execQueryNamedParametersSingleResult(q, parameters);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /**
     * 
     * metodo chamado pelas subclasses para execução de consultas nomeadas com
     * parametros nomeados
     * 
     * @param namedQuery
     *            nome da consulta
     * @param parameters
     *            Map com os parametros da consulta
     * @return resultado da consulta
     */
    protected Collection<E> findByNamedQuery(String namedQuery,
            Map<String, Object> parameters) {
        try {
            Query q = entityManager.createNamedQuery(namedQuery);
            return execQueryNamedParameters(q, parameters);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /**
     * 
     * metodo chamado pelas subclasses para execução de consultas nomeadas com
     * parametros nomeados que retornam uma única entidade
     * 
     * @param namedQuery
     *            nome da consulta
     * @param parameters
     *            Map com os parametros da consulta
     * @return resultado da consulta
     */
    protected E findSingleByNamedQuery(String namedQuery,
            Map<String, Object> parameters) {
        try {
            Query q = entityManager.createNamedQuery(namedQuery);
            return execQueryNamedParametersSingleResult(q, parameters);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /**
     * 
     * metodo chamado pelas subclasses para execução de consultas com parametros
     * indexados
     * 
     * @param query
     *            consulta em JPA QL
     * @param parameters
     *            varargs com os parametros da consulta
     * @return resultado da consulta
     */
    protected Collection<E> findByQuery(String query, Object... parameters) {
        try {
            Query q = entityManager.createQuery(query);
            return this.execQueryIndexedParameters(q, parameters);
        } catch (javax.persistence.PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /**
     * 
     * metodo chamado pelas subclasses para execução de consultas com parametros
     * indexados que retornam uma única entidade
     * 
     * @param query
     *            consulta em JPA QL
     * @param parameters
     *            varargs com os parametros da consulta
     * @return resultado da consulta
     */
    protected E findSingleByQuery(String query, Object... parameters) {
        try {
            Query q = entityManager.createQuery(query);
            return this.execQueryIndexedParametersSingleResult(q, parameters);
        } catch (javax.persistence.PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /**
     * 
     * metodo chamado pelas subclasses para execução de consultas nomeadas com
     * parametros indexados
     * 
     * @param namedQuery
     *            nome da consulta
     * @param parameters
     *            varargs com os parametros da consulta
     * @return resultado da consulta
     */
    protected Collection<E> findByNamedQuery(String namedQuery,
            Object... parameters) {
        try {
            Query q = entityManager.createNamedQuery(namedQuery);
            return this.execQueryIndexedParameters(q, parameters);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /**
     * 
     * metodo chamado pelas subclasses para execução de consultas nomeadas com
     * parametros indexados que retornam uma única entidade
     * 
     * @param namedQuery
     *            nome da consulta
     * @param parameters
     *            varargs com os parametros da consulta
     * @return resultado da consulta
     */
    protected E findSingleByNamedQuery(String namedQuery,
            Object... parameters) {
        try {
            Query q = entityManager.createNamedQuery(namedQuery);
            return this.execQueryIndexedParametersSingleResult(q, parameters);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    /*=============================================================================================
     * 
     * Metodos de uso privativo da classe
     * 
    ==============================================================================================*/
    /**
     * 
     * metodo que  executa consultas com parametros indexados
     * 
     * @param q
     *            consulta a ser executada
     * @param parameters
     *            varargs com os parametros da consulta
     * @return resultado da consulta
     */
    @SuppressWarnings("unchecked")
    private Collection<E> execQueryIndexedParameters(Query q,
            Object... parameters) {
        fillIndexedParameters(q, parameters);
        return q.getResultList();
    }

    /**
     * 
     * metodo que executa consultas com parametros indexados, retornando uma única entidade
     * 
     * @param q
     *            consulta a ser executada
     * @param parameters
     *            varargs com os parametros da consulta
     * @return resultado da consulta
     */
    @SuppressWarnings("unchecked")
    private E execQueryIndexedParametersSingleResult(Query q,
            Object... parameters) {
        fillIndexedParameters(q, parameters);
        return (E) q.getSingleResult();
    }

    /**
     * 
     * metodo que executa consultas com parametros nomeados
     * 
     * @param q
     *            consulta a ser executada
     * @param parameters
     *            Map com os parametros da consulta
     * @return resultado da consulta
     */
    @SuppressWarnings("unchecked")
    private Collection<E> execQueryNamedParameters(Query q,
            Map<String, Object> parameters) {
        fillNamedParameters(q, parameters);
        return q.getResultList();
    }

    /**
     * 
     * metodo que executa consultas com parametros nomeados, retornando uma única entidade
     * 
     * @param q
     *            consulta a ser executada
     * @param parameters
     *            Map com os parametros da consulta
     * @return resultado da consulta
     */
    @SuppressWarnings("unchecked")
    private E execQueryNamedParametersSingleResult(Query q,
            Map<String, Object> parameters) {
        fillNamedParameters(q, parameters);
        return (E) q.getSingleResult();
    }

    /**
     * metodo utilitario para preencher parametros nomeados de uma consulta
     * 
     * @param q consulta
     * @param parameters Map com os parametros nomeados
     */
    private void fillNamedParameters(Query q, Map<String, Object> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * metodo utilitario para preencher parametros indexados de uma consulta
     * 
     * @param q consulta
     * @param parameters varargs com os parametros
     */
    private void fillIndexedParameters(Query q, Object... parameters) {
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                q.setParameter(i + 1, parameters[i]);
            }
        }
    }
}
