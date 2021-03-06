package com.rengu.DAO;

import com.rengu.entity.*;
import com.rengu.util.ApsTools;
import com.rengu.util.MySessionFactory;
import com.rengu.util.SnapshotLevel;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wey580231 on 2017/6/27.
 */
public class SnapshotDao {

    //将所有订单一起转换
    public boolean switchToEmulateData(String userId, String id) {
        boolean result = false;
        Session session = MySessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        RG_SnapshotNodeEntity bottomSnapshot = session.get(RG_SnapshotNodeEntity.class, id);
        bottomSnapshot = session.get(RG_SnapshotNodeEntity.class, id);
        if (bottomSnapshot != null && bottomSnapshot.getLevel().equals(SnapshotLevel.BOTTOM)) {

            //【1】查找此次排程对应的所有订单结果信息
            NativeQuery nquery = session.createNativeQuery("select * from rg_plan where idSnapshort=:id  order by t1Task asc", RG_PlanEntity.class);
            nquery.setParameter("id", id);
            List<RG_PlanEntity> plans = nquery.list();

            if (plans.size() > 0) {
                //【2】将plan表转换至模拟数据
                result = convertPlanTo3DEmulateData(plans, id);

                if (result) {
                    RG_SnapshotNodeEntity rootParent = bottomSnapshot.getRootParent();

                    try {
                        String layoutName = rootParent.getSchedule().getLayout().getName();

                        //【3】更新3D车间的对应状态，将布局、仿真、快照节点更新
                        NativeQuery nativeQuery = session.createNativeQuery("update rg_state3d set layoutState = ?, layoutId = ? ,model = ? , snapshotId = ? where id = ?");
                        nativeQuery.setParameter(1, 1);
                        nativeQuery.setParameter(2, layoutName);
                        nativeQuery.setParameter(3, 1);
                        nativeQuery.setParameter(4, id);
                        nativeQuery.setParameter(5, 1);
                        if (nativeQuery.executeUpdate() > 0) {
                            result = true;
                        }
                        tx.commit();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        tx.rollback();
                    }
                }
            }
        }

        session.close();

        return result;
    }

    //将plan表转换成3d车间的模拟数据，中间存在的字段需要框架来补充
    private boolean convertPlanTo3DEmulateData(List<RG_PlanEntity> plans, String snapShotId) {
        boolean flag = false;

        Session session = MySessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        Query queryObject = session.createNativeQuery("truncate table rg_emulateresult");

        if (queryObject.executeUpdate() >= 0) {

            try {
                //【1】将当前快照对应的所有订单按照时间升序转换
                RG_PlanEntity startPlan = plans.get(0);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                Date initialDate = sdf.parse(startPlan.getT1Task());

                for (int i = 0; i < plans.size(); i++) {

                    RG_PlanEntity plan = plans.get(i);

                    RG_ResourceEntity res = plan.getResourceByIdResource();

                    if (res != null && res.getCritical() != null && res.getCritical().equals("T")) {
                        String processId = plan.getProcessByIdProcess().getId();

                        NativeQuery nquery = session.createNativeQuery("select * from rg_processassisant where processId = ? ", RG_ProcessAssisantEntity.class);
                        nquery.setParameter(1, processId);
                        List<RG_ProcessAssisantEntity> list = nquery.list();

                        if (list.size() > 0) {
                            RG_EmulateResultEntity result = new RG_EmulateResultEntity();

                            RG_ProcessAssisantEntity entity = list.get(0);

                            //任务名
                            result.setTask(entity.getTask());
                            //货物名
                            result.setGoods(entity.getGoods());
                            //地点名(若不存在为null)
                            result.setSite(entity.getSite());

                            Date startDate = sdf.parse(plan.getT1Task());
                            Date endDate = sdf.parse(plan.getT2Task());

                            //开始时间
                            result.setStartTime((int) (startDate.getTime() - initialDate.getTime()) / 1000);
                            //结束时间
                            result.setEndTime((int) ((endDate.getTime() - initialDate.getTime()) / 1000));

                            result.setOrderEntity(plan.getOrderByIdOrder());

                            result.setSnapshotNodeEntity(plan.getSnapShort());

                            session.save(result);

                            //Yang 20170725调整对3D车间的结果转换规则
                            if (entity.getAutoCreateProcess() != null && entity.getNextProcessTask() != null
                                    && entity.getNextProcessDistnces() != null && entity.getNextProcessMobility() != null && entity.getAutoCreateProcess().equals("Y")) {

                                String[] taskList = entity.getNextProcessTask().split(",");
                                String[] distances = entity.getNextProcessDistnces().split(",");
                                String[] mobility = entity.getNextProcessMobility().split(",");

                                if (taskList.length == distances.length) {
                                    for (int m = 0; m < taskList.length; m++) {
                                        RG_EmulateResultEntity nextResult = new RG_EmulateResultEntity();
                                        //任务名
                                        nextResult.setTask(taskList[m]);
                                        //货物名
                                        nextResult.setGoods(entity.getGoods());
                                        //地点名(若不存在为null)
                                        if (entity.getNextProcessSites() != null) {
                                            String[] sites = entity.getNextProcessSites().split(",");
                                            nextResult.setSite(sites[m]);
                                        } else {
                                            nextResult.setSite(null);
                                        }

                                        long stime = 0;

                                        if (entity.getNextProcessRefetTime() != null) {
                                            String[] referTime = entity.getNextProcessRefetTime().split(",");
                                            if (referTime[m].toLowerCase().equals("e")) {
                                                stime = (endDate.getTime() - initialDate.getTime()) / 1000 + 1;
                                            } else if (referTime[m].toLowerCase().equals("s")) {
                                                stime = (startDate.getTime() - initialDate.getTime()) / 1000 + 1;
                                            }
                                        } else {
                                            stime = (endDate.getTime() - initialDate.getTime()) / 1000 + 1;
                                        }

                                        long lastTime = 2;

                                        int currDis = Integer.parseInt(distances[m]);
                                        int currPeed = Integer.parseInt(mobility[m]);

                                        if (currDis > 0 && currPeed > 0) {
                                            lastTime = currDis / currPeed;
                                        }

                                        //开始时间
                                        nextResult.setStartTime((int) stime);
                                        //结束时间
                                        nextResult.setEndTime((int) (stime + lastTime));
                                        nextResult.setOrderEntity(plan.getOrderByIdOrder());

                                        nextResult.setSnapshotNodeEntity(plan.getSnapShort());
                                        session.save(nextResult);
                                    }
                                }
                            }
                        }
                    }
                }

                //【2】将快照对应的所有订单，按照订单逐个转换
                NativeQuery nativeQuery = session.createNativeQuery("select idOrder from rg_plan where idSnapshort =:id group by idOrder");
                nativeQuery.setParameter("id", snapShotId);
                List<String> orders = nativeQuery.list();
                for (int i = 0; i < orders.size(); i++) {
                    NativeQuery nQuery = session.createNativeQuery("select * from rg_plan where idOrder = ? and idSnapshort = ? order by t1Task asc", RG_PlanEntity.class);
                    nQuery.setParameter(1, orders.get(i));
                    nQuery.setParameter(2, snapShotId);
                    List<RG_PlanEntity> orderPlans = nQuery.list();

                    startPlan = orderPlans.get(0);

                    initialDate = sdf.parse(startPlan.getT1Task());

                    for (int j = 0; j < orderPlans.size(); j++) {

                        RG_PlanEntity plan = orderPlans.get(j);

                        RG_ResourceEntity res = plan.getResourceByIdResource();

                        if (res != null && res.getCritical() != null && res.getCritical().equals("T")) {
                            String processId = plan.getProcessByIdProcess().getId();

                            NativeQuery nquery = session.createNativeQuery("select * from rg_processassisant where processId = ? ", RG_ProcessAssisantEntity.class);
                            nquery.setParameter(1, processId);
                            List<RG_ProcessAssisantEntity> list = nquery.list();

                            if (list.size() > 0) {
                                RG_EmulateResultEntity result = new RG_EmulateResultEntity();

                                RG_ProcessAssisantEntity entity = list.get(0);

                                //任务名
                                result.setTask(entity.getTask());
                                //货物名
                                result.setGoods(entity.getGoods());
                                //地点名(若不存在为null)
                                result.setSite(entity.getSite());

                                Date startDate = sdf.parse(plan.getT1Task());
                                Date endDate = sdf.parse(plan.getT2Task());

                                //开始时间
                                result.setStartTime((int) ((startDate.getTime() - initialDate.getTime()) / 1000));
                                //结束时间
                                result.setEndTime((int) ((endDate.getTime() - initialDate.getTime()) / 1000));

                                result.setOrderEntity(plan.getOrderByIdOrder());

                                session.save(result);

                                //Yang 20170725调整对3D车间的结果转换规则
                                if (entity.getAutoCreateProcess() != null && entity.getNextProcessTask() != null
                                        && entity.getNextProcessDistnces() != null && entity.getNextProcessMobility() != null && entity.getAutoCreateProcess().equals("Y")) {

                                    String[] taskList = entity.getNextProcessTask().split(",");
                                    String[] distances = entity.getNextProcessDistnces().split(",");
                                    String[] mobility = entity.getNextProcessMobility().split(",");

                                    if (taskList.length == distances.length) {
                                        for (int m = 0; m < taskList.length; m++) {
                                            RG_EmulateResultEntity nextResult = new RG_EmulateResultEntity();
                                            //任务名
                                            nextResult.setTask(taskList[m]);
                                            //货物名
                                            nextResult.setGoods(entity.getGoods());

                                            //地点名(若不存在为null)
                                            if (entity.getNextProcessSites() != null) {
                                                String[] sites = entity.getNextProcessSites().split(",");
                                                nextResult.setSite(sites[m]);
                                            } else {
                                                nextResult.setSite(null);
                                            }

                                            long stime = 0;

                                            if (entity.getNextProcessRefetTime() != null) {
                                                String[] referTime = entity.getNextProcessRefetTime().split(",");
                                                if (referTime[m].toLowerCase().equals("e")) {
                                                    stime = (endDate.getTime() - initialDate.getTime()) / 1000 + 1;
                                                } else if (referTime[m].toLowerCase().equals("s")) {
                                                    stime = (startDate.getTime() - initialDate.getTime()) / 1000 + 1;
                                                }
                                            } else {
                                                stime = (endDate.getTime() - initialDate.getTime()) / 1000 + 1;
                                            }

                                            long lastTime = 2;

                                            int currDis = Integer.parseInt(distances[m]);
                                            int currPeed = Integer.parseInt(mobility[m]);

                                            if (currDis > 0 && currPeed > 0) {
                                                lastTime = currDis / currPeed;
                                            }
                                            //开始时间
                                            nextResult.setStartTime((int) stime);
                                            //结束时间
                                            nextResult.setEndTime((int) (stime + lastTime));
                                            nextResult.setOrderEntity(plan.getOrderByIdOrder());

                                            nextResult.setOrderEntity(plan.getOrderByIdOrder());
                                            session.save(nextResult);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                flag = true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        session.getTransaction().commit();
        session.close();

        return flag;
    }

    //将结果下发给MES，添加对订单状态的修改
    public boolean switchResultToMess(String s, String id) {
        boolean result = false;

        Session session = MySessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            RG_SnapshotNodeEntity snapshot = session.get(RG_SnapshotNodeEntity.class, id);

            if (snapshot != null && snapshot.getLevel().equals(SnapshotLevel.BOTTOM)) {

                RG_SnapshotNodeEntity parent = snapshot.getParent();
                RG_SnapshotNodeEntity rootParent = snapshot.getRootParent();

                if (!parent.getApply() && !snapshot.getApply()) {

                    List<String> orderList = new ArrayList<String>();

                    Set<RG_OrderEntity> orders = rootParent.getSchedule().getOrders();
                    Iterator<RG_OrderEntity> iter = orders.iterator();
                    while (iter.hasNext()) {
                        RG_OrderEntity tmpOrder = iter.next();
                        tmpOrder.setState(Byte.parseByte("2"));
                        orderList.add(tmpOrder.getId());
                        session.update(tmpOrder);
                    }

                    //aps接口
                    if (ApsTools.instance().publishOrder(orderList) == ApsTools.STARTED) {
                        parent.setApply(true);
                        parent.setDispatchMesTime(new Date());
                        snapshot.setApply(true);
                        snapshot.setDispatchMesTime(new Date());

                        session.update(snapshot);
                        session.update(parent);

                        NativeQuery query = session.createNativeQuery("update rg_userconfig set dispatchMesSnapshotId = ? where idUser = ?");
                        query.setParameter(1, id);
                        query.setParameter(2, "1");
                        if (query.executeUpdate() > 0) {
                            result = true;
                            tx.commit();
                        } else {
                            result = false;
                            tx.rollback();
                        }
                    }
                }
            }

        } catch (Exception e) {
            result = false;
            tx.rollback();
        }
        session.close();

        return result;
    }

    //恢复aps快照
    public boolean recoverSnapshot(String s, String id) {
        boolean result = false;

        if (ApsTools.instance().recoverSnapshot(id) == ApsTools.STARTED) {
            result = true;

            Session session = MySessionFactory.getSessionFactory().openSession();
            session.beginTransaction();

            NativeQuery query = session.createNativeQuery("update rg_userconfig set apsCurrSnapshotId = ? where idUser = ?");
            query.setParameter(1, id);
            query.setParameter(2, "1");
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        }

        return result;
    }

    //查询对应节点的快照是否恢复
    public boolean queryRecoverSnapshot(String s, String id) {
        boolean result = false;

        Session session = MySessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        RG_SnapshotNodeEntity entity = session.get(RG_SnapshotNodeEntity.class, id);
        result = entity.getApsRecoverSnapshot();

        session.flush();

        session.getTransaction().commit();
        session.close();

        return result;
    }
}
