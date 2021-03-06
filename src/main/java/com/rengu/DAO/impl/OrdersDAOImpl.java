package com.rengu.DAO.impl;

import com.rengu.DAO.OrdersDAO;
import com.rengu.entity.RG_AdjustOrderEntity;
import com.rengu.entity.RG_OrderEntity;
import com.rengu.entity.RG_ScheduleEntity;
import com.rengu.util.DAOFactory;
import com.rengu.util.MySessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by hanchangming on 2017/5/22.
 */
public class OrdersDAOImpl extends SuperDAOImpl implements OrdersDAO<RG_OrderEntity> {
    @Override
    public List<RG_OrderEntity> findAll() {
        MySessionFactory.getSessionFactory().getCurrentSession().close();
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();

        if (!transaction.isActive()) {
            session.beginTransaction();
        }
        String hql = "from RG_OrderEntity rg_orderEntity";
        Query query = session.createQuery(hql);
        List list = query.list();
        return list;
    }

    @Override
    public List<RG_OrderEntity> findAllByUsername(String username) {
        try {
            MySessionFactory.getSessionFactory().getCurrentSession().close();
            Session session = MySessionFactory.getSessionFactory().getCurrentSession();
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                session.beginTransaction();
            }
            String hql = "from RG_OrderEntity rg_orderEntity where rg_orderEntity.clubByIdClub.name =:nameClub";
            Query query = session.createQuery(hql);
            query.setParameter("nameClub", username);
            List list = query.list();
            return list;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public RG_OrderEntity findAllById(String id) {
        try {
            MySessionFactory.getSessionFactory().getCurrentSession().close();
            Session session = MySessionFactory.getSessionFactory().getCurrentSession();
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                session.beginTransaction();
            }
            String hql = "from RG_OrderEntity rg_orderEntity where rg_orderEntity.id =:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            if (!query.list().isEmpty()) {
                RG_OrderEntity rg_orderEntity = (RG_OrderEntity) query.list().get(0);
                return rg_orderEntity;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public List<RG_OrderEntity> findByState(Byte state) {
        try {
            MySessionFactory.getSessionFactory().getCurrentSession().close();
            Session session = MySessionFactory.getSessionFactory().getCurrentSession();
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                session.beginTransaction();
            }
            String hql = "from RG_OrderEntity rg_orderEntity where rg_orderEntity.state =:state";
            Query query = session.createQuery(hql);
            query.setParameter("state", state);
            List list = query.list();
            return list;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RG_OrderEntity> findAllByisFinishedAndDate(Date startDate, Date endDate, boolean isFinished) {
        MySessionFactory.getSessionFactory().getCurrentSession().close();
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        if (!transaction.isActive()) {
            session.beginTransaction();
        }
        String hql = "from RG_OrderEntity rg_orderEntity where rg_orderEntity.t2 between ? and ? and rg_orderEntity.state =:state";
//        String hql = "from RG_OrderEntity rg_orderEntity where rg_orderEntity.state =:state";
        Query query = session.createQuery(hql);
        query.setParameter(0, startDate);
        query.setParameter(1, endDate);
        byte stateByte = 0;
        query.setParameter("state", stateByte);
        List list = query.list();
        return list;
    }

    public boolean delete(Object object) {
        RG_OrderEntity rg_orderEntity;
        if (object instanceof RG_OrderEntity) {
            rg_orderEntity = (RG_OrderEntity) object;
            String orderId = rg_orderEntity.getId();
            //从排程记录里面删除订单
            Set<RG_ScheduleEntity> rg_scheduleEntitySet = rg_orderEntity.getSchedules();
            if (rg_scheduleEntitySet != null) {
                for (RG_ScheduleEntity rg_scheduleEntity : rg_scheduleEntitySet) {
                    rg_scheduleEntity.getOrders().remove(rg_orderEntity);
                }
            }
            //从订单异常里面删除订单。
            RG_AdjustOrderEntity rg_adjustOrderEntity = DAOFactory.getAdjustOrderDAOImplInstance().findAllByOrderId(orderId);
            if (rg_adjustOrderEntity != null) {
                if (DAOFactory.getAdjustOrderDAOImplInstance().delete(rg_adjustOrderEntity) && super.delete(rg_orderEntity)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                //直接删除
                return super.delete(rg_orderEntity);
            }
        } else {
            //参数错误
            return false;
        }
    }
}
