package com.rengu.DAO;

import java.util.List;

/**
 * Created by hanchangming on 2017/6/16.
 */
public interface AdjustProcessDAO<T> {
    List<T> findAll();

    List<T> findAllByErrorState(Integer errorState);

    T findAllById(String id);
}
