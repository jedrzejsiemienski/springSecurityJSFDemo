package com.jedrek.urticaRecruitmentTask.dao;

import java.util.List;

public interface GenericDAO<T> {
    T save(T newInstance);
    T update(T transientObject);
    void delete(T persistentObject);

    T findById(Object id);
    List<T> findAll();
}
