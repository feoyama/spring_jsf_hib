package com.noriakijr.spring_jsf_hib.persistence.implementation;

import java.io.Serializable;
import java.util.List;

import com.noriakijr.spring_jsf_hib.persistence.PersistenceException;

public interface DAO <T extends Serializable, I>{

    T salvar(T entity) throws PersistenceException;

    void excluir(Long id) throws PersistenceException;

    List<T> list();
    
    T find(Long id);
}
