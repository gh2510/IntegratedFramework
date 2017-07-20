package com.rengu.util;

import com.rengu.entity.RG_ScheduleEntity;

/**
 * Created by hanch on 2017/7/20.
 */
public class EventLogTools {
    public static final Short OrderEvent = 0;//订单事件
    public static final Short ScheduleEvent = 1;//排程时间

    public static String createScheduleStartEventContent(RG_ScheduleEntity rg_scheduleEntity) {
        String contentString = rg_scheduleEntity.getName() + "开始进行APS排程，滚动周期为：" + rg_scheduleEntity.getRollTime() + "天，排程时间窗为：" + rg_scheduleEntity.getScheduleWindow() + "天。APS排程模式设置为：'" + rg_scheduleEntity.getApsModel() + "'";
        return contentString;
    }
}