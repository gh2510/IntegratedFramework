package com.rengu.actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.rengu.DAO.ResourceDAO;
import com.rengu.DAO.impl.ResourceDAOImpl;
import com.rengu.entity.RG_ResourceEntity;
import com.rengu.util.DAOFactory;
import com.rengu.util.Tools;
import com.rengu.util.WebSocketNotification;

import java.util.List;

/**
 * Created by hanchangming on 2017/5/31.
 */
public class ResourceAction extends SuperAction {

    public void getAllResource() throws Exception {
        ResourceDAO resourceDAO = DAOFactory.getResourceInstance();
        List list = resourceDAO.findAll();
        String jsonString = Tools.entityConvertToJsonString(list);
        Tools.jsonPrint(jsonString, this.httpServletResponse);
    }

    public void save() throws Exception {
        String jsonString = Tools.getHttpRequestBody(httpServletRequest);
        RG_ResourceEntity rg_resourceEntity = Tools.jsonConvertToEntity(jsonString, RG_ResourceEntity.class);
        rg_resourceEntity.setIdR(Tools.getUUID());
        ResourceDAOImpl resourceDAOInstance = DAOFactory.getResourceInstance();
        if (resourceDAOInstance.save(rg_resourceEntity)) {
        } else {
            WebSocketNotification.broadcast(Tools.creatNotificationMessage("Resource保存失败", "alert"));
        }
    }

    public void delete() throws Exception {
        String jsonString = Tools.getHttpRequestBody(httpServletRequest);
        RG_ResourceEntity rg_resourceEntity = new RG_ResourceEntity();
        rg_resourceEntity.setIdR(Tools.jsonTreeModelParse(jsonString).get("id").asText());
        ResourceDAOImpl resourceDAOInstance = DAOFactory.getResourceInstance();
        if (resourceDAOInstance.delete(rg_resourceEntity)) {
        } else {
            WebSocketNotification.broadcast(Tools.creatNotificationMessage("Resource删除失败", "alert"));
        }
    }

    public void update() throws Exception {
        String jsonString = Tools.getHttpRequestBody(httpServletRequest);
        RG_ResourceEntity rg_resourceEntity = Tools.jsonConvertToEntity(jsonString, RG_ResourceEntity.class);
        ResourceDAOImpl resourceDAOInstance = DAOFactory.getResourceInstance();
        if (resourceDAOInstance.update(rg_resourceEntity)) {
        } else {
            WebSocketNotification.broadcast(Tools.creatNotificationMessage("Resource更新失败", "alert"));
        }
    }

    public void findAllById() throws Exception {
        String jsonString = Tools.getHttpRequestBody(httpServletRequest);
        JsonNode jsonNode = Tools.jsonTreeModelParse(jsonString);
        String resourceId = jsonNode.get("id").asText();
        ResourceDAOImpl resourceDAO = DAOFactory.getResourceInstance();
        RG_ResourceEntity rg_resourceEntity = resourceDAO.findAllById(resourceId);
        String resultString = Tools.entityConvertToJsonString(rg_resourceEntity);
        Tools.jsonPrint(resultString, this.httpServletResponse);
    }
}
