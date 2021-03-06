package com.rengu.DAO.impl;

import com.rengu.DAO.LayoutDAO;
import com.rengu.entity.RG_LayoutEntity;
import com.rengu.util.MySessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by hanchangming on 2017/6/5.
 */
public class LayoutDAOImpl extends SuperDAOImpl implements LayoutDAO<RG_LayoutEntity> {

    @Override
    public List<RG_LayoutEntity> findAll() {
        MySessionFactory.getSessionFactory().getCurrentSession().close();
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();

        if (!transaction.isActive()) {
            session.beginTransaction();
        }
        String hql = "from RG_LayoutEntity rg_layoutEntity";
        Query query = session.createQuery(hql);

        List list = query.list();

        return list;
    }

    @Override
    public RG_LayoutEntity findAllById(String id) {
        try {
            MySessionFactory.getSessionFactory().getCurrentSession().close();
            Session session = MySessionFactory.getSessionFactory().getCurrentSession();
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                session.beginTransaction();
            }
            String hql = "from RG_LayoutEntity rg_layoutEntity where rg_layoutEntity.id =:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            if (!query.list().isEmpty()) {
                RG_LayoutEntity rg_layoutEntity = (RG_LayoutEntity) query.list().get(0);
                return rg_layoutEntity;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
