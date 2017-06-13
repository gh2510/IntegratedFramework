package com.rengu.entity;

/**
 * Created by wey580231 on 2017/5/23.
 */
public class RG_PlanEntity {
    private int id;
    private String idTask;
    private String idJob;
    private String nameTask;
    private String nameOrder;
    private String nameJob;
    private String nameResource;
    private String nameGroupResource;
    private String nameTypeResource;
    private String nameSite;
    private String nameProductOrder;
    private String nameProvider;
    private Short ordToParentTask;
    private String idTaskResourceSucc;
    private String preemptiveTask;
    private String divisibleTask;
    private String continuousTask;
    private Short quantityTask;
    private Short quantityResourceTask;
    private Short quantityBatchTask;
    private Short qtySequence;
    private String t1Task;
    private String t2Task;
    private String t2ExtendedTask;
    private String advice;
    private String estimateTask;
    private String timeTask;
    private String initTimeTask;
    private String unitTimeTask;
    private String postTimeTask;
    private String checkTimeTask;
    private String idGroupResource0Task;
    private String idResource0Task;
    private String idSite0Task;
    private Short quantity0Task;
    private String t10Task;
    private String t20Task;
    private String t20ExtendedTask;
    private String t1Job;
    private String t2Job;
    private Short quantityJob;
    private Short nbTaskJob;
    private String refProductJob;
    private Short ordToRootJob;
    private String ordToRootChildJob;
    private String t1Order;
    private String t2Order;
    private Short quantityOrder;
    private Short priorityOrder;
    private String colorOrder;
    private Byte state;

    private RG_ClubEntity clubByIdClub;
    private RG_ProcessEntity processByIdProcess;
    private RG_OrderEntity orderByIdOrder;
    private RG_ResourceEntity resourceByIdResource;
    private RG_SiteEntity siteByIdSite;
    private RG_GroupresourceEntity groupresourceByIdGroupResource;
    private RG_TyperescourceEntity typerescourceByIdTypeResource;
    private RG_ProviderEntity providerByIdProvider;
    private RG_ProductEntity productByIdProduct;
    private RG_ScheduleEntity scheduleByIdSchedule;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdTask() {
        return idTask;
    }

    public void setIdTask(String idTask) {
        this.idTask = idTask;
    }

    public String getIdJob() {
        return idJob;
    }

    public void setIdJob(String idJob) {
        this.idJob = idJob;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getNameOrder() {
        return nameOrder;
    }

    public void setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
    }

    public String getNameJob() {
        return nameJob;
    }

    public void setNameJob(String nameJob) {
        this.nameJob = nameJob;
    }

    public String getNameResource() {
        return nameResource;
    }

    public void setNameResource(String nameResource) {
        this.nameResource = nameResource;
    }

    public String getNameGroupResource() {
        return nameGroupResource;
    }

    public void setNameGroupResource(String nameGroupResource) {
        this.nameGroupResource = nameGroupResource;
    }

    public String getNameTypeResource() {
        return nameTypeResource;
    }

    public void setNameTypeResource(String nameTypeResource) {
        this.nameTypeResource = nameTypeResource;
    }

    public String getNameSite() {
        return nameSite;
    }

    public void setNameSite(String nameSite) {
        this.nameSite = nameSite;
    }

    public String getNameProductOrder() {
        return nameProductOrder;
    }

    public void setNameProductOrder(String nameProductOrder) {
        this.nameProductOrder = nameProductOrder;
    }

    public String getNameProvider() {
        return nameProvider;
    }

    public void setNameProvider(String nameProvider) {
        this.nameProvider = nameProvider;
    }

    public Short getOrdToParentTask() {
        return ordToParentTask;
    }

    public void setOrdToParentTask(Short ordToParentTask) {
        this.ordToParentTask = ordToParentTask;
    }

    public String getIdTaskResourceSucc() {
        return idTaskResourceSucc;
    }

    public void setIdTaskResourceSucc(String idTaskResourceSucc) {
        this.idTaskResourceSucc = idTaskResourceSucc;
    }

    public String getPreemptiveTask() {
        return preemptiveTask;
    }

    public void setPreemptiveTask(String preemptiveTask) {
        this.preemptiveTask = preemptiveTask;
    }

    public String getDivisibleTask() {
        return divisibleTask;
    }

    public void setDivisibleTask(String divisibleTask) {
        this.divisibleTask = divisibleTask;
    }

    public String getContinuousTask() {
        return continuousTask;
    }

    public void setContinuousTask(String continuousTask) {
        this.continuousTask = continuousTask;
    }

    public Short getQuantityTask() {
        return quantityTask;
    }

    public void setQuantityTask(Short quantityTask) {
        this.quantityTask = quantityTask;
    }

    public Short getQuantityResourceTask() {
        return quantityResourceTask;
    }

    public void setQuantityResourceTask(Short quantityResourceTask) {
        this.quantityResourceTask = quantityResourceTask;
    }

    public Short getQuantityBatchTask() {
        return quantityBatchTask;
    }

    public void setQuantityBatchTask(Short quantityBatchTask) {
        this.quantityBatchTask = quantityBatchTask;
    }

    public Short getQtySequence() {
        return qtySequence;
    }

    public void setQtySequence(Short qtySequence) {
        this.qtySequence = qtySequence;
    }

    public String getT1Task() {
        return t1Task;
    }

    public void setT1Task(String t1Task) {
        this.t1Task = t1Task;
    }

    public String getT2Task() {
        return t2Task;
    }

    public void setT2Task(String t2Task) {
        this.t2Task = t2Task;
    }

    public String getT2ExtendedTask() {
        return t2ExtendedTask;
    }

    public void setT2ExtendedTask(String t2ExtendedTask) {
        this.t2ExtendedTask = t2ExtendedTask;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getEstimateTask() {
        return estimateTask;
    }

    public void setEstimateTask(String estimateTask) {
        this.estimateTask = estimateTask;
    }

    public String getTimeTask() {
        return timeTask;
    }

    public void setTimeTask(String timeTask) {
        this.timeTask = timeTask;
    }

    public String getInitTimeTask() {
        return initTimeTask;
    }

    public void setInitTimeTask(String initTimeTask) {
        this.initTimeTask = initTimeTask;
    }

    public String getUnitTimeTask() {
        return unitTimeTask;
    }

    public void setUnitTimeTask(String unitTimeTask) {
        this.unitTimeTask = unitTimeTask;
    }

    public String getPostTimeTask() {
        return postTimeTask;
    }

    public void setPostTimeTask(String postTimeTask) {
        this.postTimeTask = postTimeTask;
    }

    public String getCheckTimeTask() {
        return checkTimeTask;
    }

    public void setCheckTimeTask(String checkTimeTask) {
        this.checkTimeTask = checkTimeTask;
    }

    public String getIdGroupResource0Task() {
        return idGroupResource0Task;
    }

    public void setIdGroupResource0Task(String idGroupResource0Task) {
        this.idGroupResource0Task = idGroupResource0Task;
    }

    public String getIdResource0Task() {
        return idResource0Task;
    }

    public void setIdResource0Task(String idResource0Task) {
        this.idResource0Task = idResource0Task;
    }

    public String getIdSite0Task() {
        return idSite0Task;
    }

    public void setIdSite0Task(String idSite0Task) {
        this.idSite0Task = idSite0Task;
    }

    public Short getQuantity0Task() {
        return quantity0Task;
    }

    public void setQuantity0Task(Short quantity0Task) {
        this.quantity0Task = quantity0Task;
    }

    public String getT10Task() {
        return t10Task;
    }

    public void setT10Task(String t10Task) {
        this.t10Task = t10Task;
    }

    public String getT20Task() {
        return t20Task;
    }

    public void setT20Task(String t20Task) {
        this.t20Task = t20Task;
    }

    public String getT20ExtendedTask() {
        return t20ExtendedTask;
    }

    public void setT20ExtendedTask(String t20ExtendedTask) {
        this.t20ExtendedTask = t20ExtendedTask;
    }

    public String getT1Job() {
        return t1Job;
    }

    public void setT1Job(String t1Job) {
        this.t1Job = t1Job;
    }

    public String getT2Job() {
        return t2Job;
    }

    public void setT2Job(String t2Job) {
        this.t2Job = t2Job;
    }

    public Short getQuantityJob() {
        return quantityJob;
    }

    public void setQuantityJob(Short quantityJob) {
        this.quantityJob = quantityJob;
    }

    public Short getNbTaskJob() {
        return nbTaskJob;
    }

    public void setNbTaskJob(Short nbTaskJob) {
        this.nbTaskJob = nbTaskJob;
    }

    public String getRefProductJob() {
        return refProductJob;
    }

    public void setRefProductJob(String refProductJob) {
        this.refProductJob = refProductJob;
    }

    public Short getOrdToRootJob() {
        return ordToRootJob;
    }

    public void setOrdToRootJob(Short ordToRootJob) {
        this.ordToRootJob = ordToRootJob;
    }

    public String getOrdToRootChildJob() {
        return ordToRootChildJob;
    }

    public void setOrdToRootChildJob(String ordToRootChildJob) {
        this.ordToRootChildJob = ordToRootChildJob;
    }

    public String getT1Order() {
        return t1Order;
    }

    public void setT1Order(String t1Order) {
        this.t1Order = t1Order;
    }

    public String getT2Order() {
        return t2Order;
    }

    public void setT2Order(String t2Order) {
        this.t2Order = t2Order;
    }

    public Short getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(Short quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public Short getPriorityOrder() {
        return priorityOrder;
    }

    public void setPriorityOrder(Short priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    public String getColorOrder() {
        return colorOrder;
    }

    public void setColorOrder(String colorOrder) {
        this.colorOrder = colorOrder;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RG_PlanEntity that = (RG_PlanEntity) o;

        if (id != that.id) return false;
        if (nameTask != null ? !nameTask.equals(that.nameTask) : that.nameTask != null) return false;
        if (nameOrder != null ? !nameOrder.equals(that.nameOrder) : that.nameOrder != null) return false;
        if (nameJob != null ? !nameJob.equals(that.nameJob) : that.nameJob != null) return false;
        if (nameResource != null ? !nameResource.equals(that.nameResource) : that.nameResource != null) return false;
        if (nameGroupResource != null ? !nameGroupResource.equals(that.nameGroupResource) : that.nameGroupResource != null)
            return false;
        if (nameTypeResource != null ? !nameTypeResource.equals(that.nameTypeResource) : that.nameTypeResource != null)
            return false;
        if (nameSite != null ? !nameSite.equals(that.nameSite) : that.nameSite != null) return false;
        if (nameProductOrder != null ? !nameProductOrder.equals(that.nameProductOrder) : that.nameProductOrder != null)
            return false;
        if (nameProvider != null ? !nameProvider.equals(that.nameProvider) : that.nameProvider != null) return false;
        if (ordToParentTask != null ? !ordToParentTask.equals(that.ordToParentTask) : that.ordToParentTask != null)
            return false;
        if (idTaskResourceSucc != null ? !idTaskResourceSucc.equals(that.idTaskResourceSucc) : that.idTaskResourceSucc != null)
            return false;
        if (preemptiveTask != null ? !preemptiveTask.equals(that.preemptiveTask) : that.preemptiveTask != null)
            return false;
        if (divisibleTask != null ? !divisibleTask.equals(that.divisibleTask) : that.divisibleTask != null)
            return false;
        if (continuousTask != null ? !continuousTask.equals(that.continuousTask) : that.continuousTask != null)
            return false;
        if (quantityTask != null ? !quantityTask.equals(that.quantityTask) : that.quantityTask != null) return false;
        if (quantityResourceTask != null ? !quantityResourceTask.equals(that.quantityResourceTask) : that.quantityResourceTask != null)
            return false;
        if (quantityBatchTask != null ? !quantityBatchTask.equals(that.quantityBatchTask) : that.quantityBatchTask != null)
            return false;
        if (qtySequence != null ? !qtySequence.equals(that.qtySequence) : that.qtySequence != null) return false;
        if (t1Task != null ? !t1Task.equals(that.t1Task) : that.t1Task != null) return false;
        if (t2Task != null ? !t2Task.equals(that.t2Task) : that.t2Task != null) return false;
        if (t2ExtendedTask != null ? !t2ExtendedTask.equals(that.t2ExtendedTask) : that.t2ExtendedTask != null)
            return false;
        if (advice != null ? !advice.equals(that.advice) : that.advice != null) return false;
        if (estimateTask != null ? !estimateTask.equals(that.estimateTask) : that.estimateTask != null) return false;
        if (timeTask != null ? !timeTask.equals(that.timeTask) : that.timeTask != null) return false;
        if (initTimeTask != null ? !initTimeTask.equals(that.initTimeTask) : that.initTimeTask != null) return false;
        if (unitTimeTask != null ? !unitTimeTask.equals(that.unitTimeTask) : that.unitTimeTask != null) return false;
        if (postTimeTask != null ? !postTimeTask.equals(that.postTimeTask) : that.postTimeTask != null) return false;
        if (checkTimeTask != null ? !checkTimeTask.equals(that.checkTimeTask) : that.checkTimeTask != null)
            return false;
        if (idGroupResource0Task != null ? !idGroupResource0Task.equals(that.idGroupResource0Task) : that.idGroupResource0Task != null)
            return false;
        if (idResource0Task != null ? !idResource0Task.equals(that.idResource0Task) : that.idResource0Task != null)
            return false;
        if (idSite0Task != null ? !idSite0Task.equals(that.idSite0Task) : that.idSite0Task != null) return false;
        if (quantity0Task != null ? !quantity0Task.equals(that.quantity0Task) : that.quantity0Task != null)
            return false;
        if (t10Task != null ? !t10Task.equals(that.t10Task) : that.t10Task != null) return false;
        if (t20Task != null ? !t20Task.equals(that.t20Task) : that.t20Task != null) return false;
        if (t20ExtendedTask != null ? !t20ExtendedTask.equals(that.t20ExtendedTask) : that.t20ExtendedTask != null)
            return false;
        if (t1Job != null ? !t1Job.equals(that.t1Job) : that.t1Job != null) return false;
        if (t2Job != null ? !t2Job.equals(that.t2Job) : that.t2Job != null) return false;
        if (quantityJob != null ? !quantityJob.equals(that.quantityJob) : that.quantityJob != null) return false;
        if (nbTaskJob != null ? !nbTaskJob.equals(that.nbTaskJob) : that.nbTaskJob != null) return false;
        if (refProductJob != null ? !refProductJob.equals(that.refProductJob) : that.refProductJob != null)
            return false;
        if (ordToRootJob != null ? !ordToRootJob.equals(that.ordToRootJob) : that.ordToRootJob != null) return false;
        if (ordToRootChildJob != null ? !ordToRootChildJob.equals(that.ordToRootChildJob) : that.ordToRootChildJob != null)
            return false;
        if (t1Order != null ? !t1Order.equals(that.t1Order) : that.t1Order != null) return false;
        if (t2Order != null ? !t2Order.equals(that.t2Order) : that.t2Order != null) return false;
        if (quantityOrder != null ? !quantityOrder.equals(that.quantityOrder) : that.quantityOrder != null)
            return false;
        if (priorityOrder != null ? !priorityOrder.equals(that.priorityOrder) : that.priorityOrder != null)
            return false;
        if (colorOrder != null ? !colorOrder.equals(that.colorOrder) : that.colorOrder != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idTask != null ? idTask.hashCode() : 0);
        result = 31 * result + (nameTask != null ? nameTask.hashCode() : 0);
        result = 31 * result + (nameOrder != null ? nameOrder.hashCode() : 0);
        result = 31 * result + (nameJob != null ? nameJob.hashCode() : 0);
        result = 31 * result + (nameResource != null ? nameResource.hashCode() : 0);
        result = 31 * result + (nameGroupResource != null ? nameGroupResource.hashCode() : 0);
        result = 31 * result + (nameTypeResource != null ? nameTypeResource.hashCode() : 0);
        result = 31 * result + (nameSite != null ? nameSite.hashCode() : 0);
        result = 31 * result + (nameProductOrder != null ? nameProductOrder.hashCode() : 0);
        result = 31 * result + (nameProvider != null ? nameProvider.hashCode() : 0);
        result = 31 * result + (ordToParentTask != null ? ordToParentTask.hashCode() : 0);
        result = 31 * result + (idTaskResourceSucc != null ? idTaskResourceSucc.hashCode() : 0);
        result = 31 * result + (preemptiveTask != null ? preemptiveTask.hashCode() : 0);
        result = 31 * result + (divisibleTask != null ? divisibleTask.hashCode() : 0);
        result = 31 * result + (continuousTask != null ? continuousTask.hashCode() : 0);
        result = 31 * result + (quantityTask != null ? quantityTask.hashCode() : 0);
        result = 31 * result + (quantityResourceTask != null ? quantityResourceTask.hashCode() : 0);
        result = 31 * result + (quantityBatchTask != null ? quantityBatchTask.hashCode() : 0);
        result = 31 * result + (qtySequence != null ? qtySequence.hashCode() : 0);
        result = 31 * result + (t1Task != null ? t1Task.hashCode() : 0);
        result = 31 * result + (t2Task != null ? t2Task.hashCode() : 0);
        result = 31 * result + (t2ExtendedTask != null ? t2ExtendedTask.hashCode() : 0);
        result = 31 * result + (advice != null ? advice.hashCode() : 0);
        result = 31 * result + (estimateTask != null ? estimateTask.hashCode() : 0);
        result = 31 * result + (timeTask != null ? timeTask.hashCode() : 0);
        result = 31 * result + (initTimeTask != null ? initTimeTask.hashCode() : 0);
        result = 31 * result + (unitTimeTask != null ? unitTimeTask.hashCode() : 0);
        result = 31 * result + (postTimeTask != null ? postTimeTask.hashCode() : 0);
        result = 31 * result + (checkTimeTask != null ? checkTimeTask.hashCode() : 0);
        result = 31 * result + (idGroupResource0Task != null ? idGroupResource0Task.hashCode() : 0);
        result = 31 * result + (idResource0Task != null ? idResource0Task.hashCode() : 0);
        result = 31 * result + (idSite0Task != null ? idSite0Task.hashCode() : 0);
        result = 31 * result + (quantity0Task != null ? quantity0Task.hashCode() : 0);
        result = 31 * result + (t10Task != null ? t10Task.hashCode() : 0);
        result = 31 * result + (t20Task != null ? t20Task.hashCode() : 0);
        result = 31 * result + (t20ExtendedTask != null ? t20ExtendedTask.hashCode() : 0);
        result = 31 * result + (t1Job != null ? t1Job.hashCode() : 0);
        result = 31 * result + (t2Job != null ? t2Job.hashCode() : 0);
        result = 31 * result + (quantityJob != null ? quantityJob.hashCode() : 0);
        result = 31 * result + (nbTaskJob != null ? nbTaskJob.hashCode() : 0);
        result = 31 * result + (refProductJob != null ? refProductJob.hashCode() : 0);
        result = 31 * result + (ordToRootJob != null ? ordToRootJob.hashCode() : 0);
        result = 31 * result + (ordToRootChildJob != null ? ordToRootChildJob.hashCode() : 0);
        result = 31 * result + (t1Order != null ? t1Order.hashCode() : 0);
        result = 31 * result + (t2Order != null ? t2Order.hashCode() : 0);
        result = 31 * result + (quantityOrder != null ? quantityOrder.hashCode() : 0);
        result = 31 * result + (priorityOrder != null ? priorityOrder.hashCode() : 0);
        result = 31 * result + (colorOrder != null ? colorOrder.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    public RG_ClubEntity getClubByIdClub() {
        return clubByIdClub;
    }

    public void setClubByIdClub(RG_ClubEntity clubByIdClub) {
        this.clubByIdClub = clubByIdClub;
    }

    public RG_ProcessEntity getProcessByIdProcess() {
        return processByIdProcess;
    }

    public void setProcessByIdProcess(RG_ProcessEntity processByIdProcess) {
        this.processByIdProcess = processByIdProcess;
    }

    public RG_OrderEntity getOrderByIdOrder() {
        return orderByIdOrder;
    }

    public void setOrderByIdOrder(RG_OrderEntity orderByIdOrder) {
        this.orderByIdOrder = orderByIdOrder;
    }

    public RG_ResourceEntity getResourceByIdResource() {
        return resourceByIdResource;
    }

    public void setResourceByIdResource(RG_ResourceEntity resourceByIdResource) {
        this.resourceByIdResource = resourceByIdResource;
    }

    public RG_SiteEntity getSiteByIdSite() {
        return siteByIdSite;
    }

    public void setSiteByIdSite(RG_SiteEntity siteByIdSite) {
        this.siteByIdSite = siteByIdSite;
    }

    public RG_GroupresourceEntity getGroupresourceByIdGroupResource() {
        return groupresourceByIdGroupResource;
    }

    public void setGroupresourceByIdGroupResource(RG_GroupresourceEntity groupresourceByIdGroupResource) {
        this.groupresourceByIdGroupResource = groupresourceByIdGroupResource;
    }

    public RG_TyperescourceEntity getTyperescourceByIdTypeResource() {
        return typerescourceByIdTypeResource;
    }

    public void setTyperescourceByIdTypeResource(RG_TyperescourceEntity typerescourceByIdTypeResource) {
        this.typerescourceByIdTypeResource = typerescourceByIdTypeResource;
    }

    public RG_ProviderEntity getProviderByIdProvider() {
        return providerByIdProvider;
    }

    public void setProviderByIdProvider(RG_ProviderEntity providerByIdProvider) {
        this.providerByIdProvider = providerByIdProvider;
    }

    public RG_ProductEntity getProductByIdProduct() {
        return productByIdProduct;
    }

    public void setProductByIdProduct(RG_ProductEntity productByIdProduct) {
        this.productByIdProduct = productByIdProduct;
    }

    public RG_ScheduleEntity getScheduleByIdSchedule() {
        return scheduleByIdSchedule;
    }

    public void setScheduleByIdSchedule(RG_ScheduleEntity scheduleByIdSchedule) {
        this.scheduleByIdSchedule = scheduleByIdSchedule;
    }

}
