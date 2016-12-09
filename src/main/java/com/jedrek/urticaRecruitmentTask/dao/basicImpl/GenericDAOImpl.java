package com.jedrek.urticaRecruitmentTask.dao.basicImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jedrek.urticaRecruitmentTask.dao.GenericDAO;

public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
	
	@PersistenceContext
	protected EntityManager em;
	
	private Class<T> type;

	@Override
	@Transactional
    public T save(T newInstance){
        em.persist(newInstance);
        return newInstance;
    }

	@Override
    public T update(T transientObject){
    	return em.merge(transientObject);
    }
    
	@Override
    public void delete(T persistentObject){
    	em.remove(persistentObject);
    }

	@Override
    public T findById(Object id){
    	return em.find(type, id);
    }
    
	@Override
    public List<T> findAll(){
    	String queryString = String.format("Select a From %s a", type.getName());
        return em.createQuery(queryString, type).getResultList();
    }
}
