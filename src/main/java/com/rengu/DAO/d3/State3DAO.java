package com.rengu.DAO.d3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rengu.entity.RG_AdjustLayoutEntity;
import com.rengu.entity.RG_LayoutDetailEntity;
import com.rengu.entity.RG_LayoutEntity;
import com.rengu.entity.RG_State3DEntity;
import com.rengu.util.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 3D车间状态设置操作
 * Created by wey580231 on 2017/6/5.
 */
public class State3DAO {

    RG_State3DEntity state3DEntity;

    //由3D车间定时查询当前的设置状态
    public String getCurrentState() {
        Session session = MySessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from RG_State3DEntity entity where entity.id =:id");
        query.setParameter("id", 1);
        List<RG_State3DEntity> list = query.list();

        String jsonString = "";
        if (list.size() == 1 && list.get(0) instanceof RG_State3DEntity) {
            RG_State3DEntity entity = (RG_State3DEntity) list.get(0);

            ObjectMapper mapper = new ObjectMapper();                //定义转换类
            ObjectNode root = mapper.createObjectNode();             //创建根节点
            root.put("result", "0");
            root.put("layoutState", entity.getLayoutState());
            root.put("layoutId", entity.getLayoutId());
            root.put("model", entity.getModel());
            root.put("snapshotId", entity.getSnapshotId());

            try {
                jsonString = mapper.writeValueAsString(root);

                System.out.println("*********Yang********" + jsonString);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                jsonString = Tools.resultCode("1", "Can't execute operation");
            }

        } else {
            jsonString = Tools.resultCode("1", "Can't execute operation");
        }
        tx.commit();
        session.close();
        return jsonString;
    }

    //根据ID名获取对应ID的所有信息
    public String getLayoutById(String s) {
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }

        String jsonString = "";

        Query query = session.createQuery("from RG_LayoutEntity layout where layout.name = :name");
        query.setParameter("name", s);
        List list = query.list();
        if (list.size() == 1 && list.get(0) instanceof RG_LayoutEntity) {
            RG_LayoutEntity layout = (RG_LayoutEntity) list.get(0);
            Set<RG_LayoutDetailEntity> details = layout.getDetails();
            Iterator<RG_LayoutDetailEntity> iter = details.iterator();

            ObjectMapper mapper = new ObjectMapper();                //定义转换类
            ObjectNode root = mapper.createObjectNode();             //创建根节点
            root.put("result", "0");
            root.put("id", layout.getName());

            ArrayNode arryaNode = mapper.createArrayNode();

            while (iter.hasNext()) {
                RG_LayoutDetailEntity entity = iter.next();

                ObjectNode dataNode = mapper.createObjectNode();
                dataNode.put("id", entity.getId());
                dataNode.put("item", entity.getItem());
                dataNode.put("pos", entity.getPos());
                dataNode.put("state", entity.getState());
                dataNode.put("exist", entity.getExist());

                arryaNode.add(dataNode);
            }

            root.put("data", arryaNode);

            try {
                jsonString = mapper.writeValueAsString(root);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        session.getTransaction().commit();

        return jsonString;
    }

    //更新布局信息
    public boolean updateLayout(String layoutName, String data) {
        ObjectMapper objectMapper = new ObjectMapper();

        RG_LayoutDetailEntity[] arr = new RG_LayoutDetailEntity[0];
        try {
            String newData = URLDecoder.decode(data);
            arr = objectMapper.readValue(newData, RG_LayoutDetailEntity[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        boolean flag = false;

        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }

        Query query = session.createQuery("from RG_LayoutEntity entity where entity.name=:name");
        query.setParameter("name", layoutName);
        List<RG_LayoutEntity> layout = query.list();
        if (layout.size() == 1 && layout.get(0) instanceof RG_LayoutEntity) {
            RG_LayoutEntity entity = (RG_LayoutEntity) layout.get(0);
            Set<RG_LayoutDetailEntity> details = entity.getDetails();
            Iterator<RG_LayoutDetailEntity> iter = details.iterator();
            while (iter.hasNext()) {
                RG_LayoutDetailEntity detail = iter.next();
                for (int i = 0; i < arr.length; i++) {
                    if (detail.getId().equals(arr[i].getId())) {
                        System.out.println(detail.getId() + "__" + arr[i].getId() + "__" + arr[i].getExist());
                        detail.setPos(arr[i].getPos());
                        detail.setState(arr[i].getState());
                        detail.setItem(arr[i].getItem());
                        detail.setExist(arr[i].getExist());
                        session.update(detail);
                        break;
                    }
                }
            }

            RG_AdjustLayoutEntity adjustLayoutEntity = new RG_AdjustLayoutEntity();
            adjustLayoutEntity.setId(Tools.getUUID());
            adjustLayoutEntity.setName(entity.getName());
            adjustLayoutEntity.setLayoutDesc(entity.getLayoutDesc());
            adjustLayoutEntity.setReportTime(new Date());
            adjustLayoutEntity.setOrigin("3D车间");
            adjustLayoutEntity.setType("更新");
            adjustLayoutEntity.setState(ErrorState.ERROR_UNSOLVED);
            adjustLayoutEntity.setLayout(entity);
            session.save(adjustLayoutEntity);
            WebSocketNotification.broadcast(Tools.creatNotificationMessage("3D车间更新布局", "confirm"));
            Tools.createEventLog(session, EventLogTools.AdjustLayoutExceptionCreatedEvent, EventLogTools.SimpleTimeLineItem, "工厂布局调整异常", "：通过3D车间更新工厂布局", adjustLayoutEntity.getId());
            flag = true;
        }
        session.getTransaction().commit();
        return flag;
    }

    //插入新布局
    public boolean insertLayout(String layoutName, String data) {
        ObjectMapper objectMapper = new ObjectMapper();

        RG_LayoutDetailEntity[] arr = new RG_LayoutDetailEntity[0];
        try {
            String newData = URLDecoder.decode(data);
            arr = objectMapper.readValue(newData, RG_LayoutDetailEntity[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }

        RG_LayoutEntity layout = new RG_LayoutEntity();
        layout.setId(Tools.getUUID());
        layout.setName(layoutName);

        Set<RG_LayoutDetailEntity> sets = new HashSet<RG_LayoutDetailEntity>();

        for (int i = 0; i < arr.length; i++) {
            arr[i].setId(Tools.getUUID());
            arr[i].setLayout(layout);

            layout.getDetails().add(arr[i]);
        }

        RG_AdjustLayoutEntity adjustLayoutEntity = new RG_AdjustLayoutEntity();
        adjustLayoutEntity.setId(Tools.getUUID());
        adjustLayoutEntity.setName(layoutName);
        adjustLayoutEntity.setLayoutDesc("");
        adjustLayoutEntity.setReportTime(new Date());
        adjustLayoutEntity.setOrigin("3D车间");
        adjustLayoutEntity.setType("新增");
        adjustLayoutEntity.setState(ErrorState.ERROR_UNSOLVED);
        session.save(adjustLayoutEntity);
        WebSocketNotification.broadcast(Tools.creatNotificationMessage("3D车间插入新布局", "confirm"));
        session.save(layout);
        Tools.createEventLog(session, EventLogTools.AdjustLayoutExceptionCreatedEvent, EventLogTools.SimpleTimeLineItem, "工厂布局调整异常", "：通过3D车间创建工厂布局", adjustLayoutEntity.getId());
        session.getTransaction().commit();
        return true;
    }

    //查询所有布局信息
    public String queryAllLayout() {
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }

        Query query = session.createQuery("from RG_LayoutEntity entity ");
        List<RG_LayoutEntity> list = query.list();

        String jsonString = "";
        if (list.size() > 0) {

            ObjectMapper mapper = new ObjectMapper();                //定义转换类
            ObjectNode root = mapper.createObjectNode();             //创建根节点
            root.put("result", "0");

            Iterator<RG_LayoutEntity> iter = list.iterator();

            ArrayNode arrayNode = mapper.createArrayNode();

            while (iter.hasNext()) {
                RG_LayoutEntity entity = iter.next();

                ObjectNode dataNode = mapper.createObjectNode();
                dataNode.put("layoutId", entity.getName());

                arrayNode.add(dataNode);
            }

            root.put("data", arrayNode);

            try {
                jsonString = mapper.writeValueAsString(root);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                jsonString = Tools.resultCode("1", "Can't execute operation");
            }

        } else {
            jsonString = Tools.resultCode("1", "Can't execute operation");
        }
        session.getTransaction().commit();
        return jsonString;
    }

    //根据布局和设备ID来更新设备状态
    public boolean updateDevice(String layoutName, String data) {
        ObjectMapper objectMapper = new ObjectMapper();

        RG_LayoutDetailEntity arr = new RG_LayoutDetailEntity();
        try {
            String newData = URLDecoder.decode(data);
            System.out.println(newData);
            arr = objectMapper.readValue(newData, RG_LayoutDetailEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        boolean flag = false;

        Session session = MySessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("from RG_LayoutEntity entity where entity.name=:name");
        query.setParameter("name", layoutName);
        List<RG_LayoutEntity> layout = query.list();
        if (layout.size() == 1 && layout.get(0) instanceof RG_LayoutEntity) {
            RG_LayoutEntity entity = (RG_LayoutEntity) layout.get(0);
            Set<RG_LayoutDetailEntity> details = entity.getDetails();
            Iterator<RG_LayoutDetailEntity> iter = details.iterator();
            while (iter.hasNext()) {
                RG_LayoutDetailEntity detail = iter.next();
                System.out.println(detail.getId() + "++++" + arr.getId());
                if (detail.getId().equals(arr.getId())) {
                    detail.setPos(arr.getPos());
                    detail.setState(arr.getState());
                    detail.setItem(arr.getItem());
                    detail.setExist(arr.getExist());
                    session.update(detail);
                    break;
                }
            }
            flag = true;
        }
        session.getTransaction().commit();
        session.close();
        return flag;
    }

    //前端查询所有布局详细信息
    public String queryAllLayoutFullInfo() {
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }

        Query query = session.createQuery("from RG_LayoutEntity entity ");
        List<RG_LayoutEntity> list = query.list();

        String jsonString = "";
        if (list.size() > 0) {

            ObjectMapper mapper = new ObjectMapper();                //定义转换类
            ObjectNode root = mapper.createObjectNode();             //创建根节点
            root.put("result", "0");

            Iterator<RG_LayoutEntity> iter = list.iterator();

            ArrayNode arrayNode = mapper.createArrayNode();

            while (iter.hasNext()) {
                RG_LayoutEntity entity = iter.next();

                ObjectNode dataNode = mapper.createObjectNode();
                dataNode.put("id", entity.getId());
                dataNode.put("name", entity.getName());
                dataNode.put("layoutDesc", entity.getLayoutDesc());
                dataNode.put("path", entity.getImgPath());

                arrayNode.add(dataNode);
            }

            root.put("data", arrayNode);

            try {
                jsonString = mapper.writeValueAsString(root);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                jsonString = Tools.resultCode("1", "Can't execute operation");
            }

        } else {
            jsonString = Tools.resultCode("1", "Can't execute operation");
        }
        session.getTransaction().commit();
        return jsonString;
    }
}
