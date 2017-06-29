/**
 * Created by hanchangming on 2017/5/16.
 */
'use strict';
angular.module("IntegratedFramework.BOMManagementController", ['ngRoute'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/BOMManagement', {
            templateUrl: '/jsps/OrderManagement/BOMManagement.jsp',
            controller: 'BOMManagementController'
        })
    }])
    .controller("BOMManagementController", function ($scope, myHttpService, serviceList) {
        var zNodes = [];
        var dataArr = [];


        /*var zNodes = [
         {
         id: 1, name: "右键菜单 1", iconSkin: "diy01", open: true,
         children: [
         {
         id: 11, name: "节点 1-1", iconSkin: "diy02",
         children: [
         {id: 21, name: "节点 1-11", iconSkin: "diy03"},
         {id: 22, name: "节点 1-12", iconSkin: "diy03"},
         {id: 23, name: "节点 1-13", iconSkin: "diy03"},
         {id: 24, name: "节点 1-14", iconSkin: "diy03"}
         ]
         },
         {id: 12, name: "节点 1-2", iconSkin: "diy02"}
         ]
         },
         {
         id: 2, name: "右键操作 2", iconSkin: "diy01", open: true,
         children: [
         {id: 21, name: "节点 2-1", iconSkin: "diy02"},
         {id: 22, name: "节点 2-2", iconSkin: "diy02"},
         {id: 23, name: "节点 2-3", iconSkin: "diy02"},
         {id: 24, name: "节点 2-4", iconSkin: "diy02"}
         ]
         },
         {
         id: 3, name: "右键操作 3", iconSkin: "diy01", open: true,
         children: [
         {id: 31, name: "节点 3-1", iconSkin: "diy02"},
         {id: 32, name: "节点 3-2", iconSkin: "diy02"},
         {id: 33, name: "节点 3-3", iconSkin: "diy02"},
         {id: 34, name: "节点 3-4", iconSkin: "diy02"}
         ]
         }
         ]; */
        var dataTrue = {"isRootNode": true};
        myHttpService.post(serviceList.isRootNode, dataTrue).then(function successCallback(response) {
            console.log("*****" + response.status);
            console.log(response.data);
            var rootdata = response.data;
            //showRoot(rootdata);
            showRoot(rootdata);

        });

        var showRoot = function (rootdata) {
            var rootLength = rootdata.length;
            for (var i = 0; i < rootLength; i++) {
                var params = {};
                var id = rootdata[i].id;
                params.id = id;
                /*var idList=[];
                 idList.push(params);*/
                console.log(params);
                var dataId = JSON.stringify(params);
                console.log("根节点的id");
                console.log(dataId);
                myHttpService.post(serviceList.isChildNode, dataId).then(function successCallback(response) {
                    console.log("$$$$$$" + response.status);
                    console.log(response.data);
                    var data = response.data;
                    console.log(data);
                    dataArr.push(data);
                    console.log(dataArr.length);
                    console.log(dataArr);
                    if (dataArr.length > 0) {
                        showChild();
                    }
                });
            }
        };

        var showChild = function () {

            var setting = {
                view: {
                    dblClickExpand: false,
                    showIcon: false,
                    showLine: false
                },
                check: {
                    enable: false
                },
                callback: {
                    onRightClick: OnRightClick
                },
                data: {
                    keep: {
                        parent: true
                    }
                },
                edit: {
                    enable: true,
                    showRenameBtn: false,
                    showRemoveBtn: false
                },
            };
            console.log("++++++" + dataArr);
            console.log(dataArr.length);
            for (var i = 0; i < dataArr.length; i++) {
                console.log("第一个根节点");
                console.log(dataArr[i]);
                var datas = dataArr[i];
                console.log("是否有一级");
                console.log(datas.hasOwnProperty("childProcess"));
                if (datas.hasOwnProperty("childProcess") == true) {
                    var childList = dataArr[i].childProcess;
                    console.log("根节点下的一级");
                    console.log(childList);
                    console.log("根节点下的一级长度");
                    console.log(childList.length);
                    for (var j = 0; j < childList.length; j++) {
                        console.log("一级节点下是否有");
                        console.log(childList[j].hasOwnProperty("childProcess"));
                        var childLists = childList[j];
                        if (childLists.hasOwnProperty("childProcess") == true) {
                            console.log("一级节点下删除前的二级");
                            console.log(childLists);
                            var temps = childLists.childProcess;
                            console.log(temps);
                            delete(childLists.childProcess);
                            childLists.children = temps;
                            console.log("一级节点下删除后的二级");
                            console.log(childLists);
                            childList[j] = childLists;
                        }
                    }
                    console.log(childList);
                    var temp = datas.childProcess;
                    delete(datas.childProcess);
                    datas.children = temp;
                }
            }
            console.log(dataArr);
            //zNodes = data;
            zNodes = dataArr;
            console.log("&&&&&&&&&" + zNodes);


            function OnRightClick(event, treeId, treeNode) {
                if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
                    zTree.cancelSelectedNode();
                    showRMenu("root", event.clientX, event.clientY);
                } else if (treeNode && !treeNode.noR) {
                    zTree.selectNode(treeNode);
                    showRMenu("node", event.clientX, event.clientY);
                }
            }


            //显示右键操作
            function showRMenu(type, x, y) {
                $("#rMenu").show();
                rMenu.css({"top": (y - 70) + "px", "left": (x - 220) + "px", "visibility": "visible"});
                $("#container").bind("mousedown", onBodyMouseDown);
            }


            //隐藏右键操作
            function hideRMenu() {
                if (rMenu) rMenu.css({"visibility": "hidden"});
                $("#container").unbind("mousedown", onBodyMouseDown);
            }


            //鼠标按下操作
            function onBodyMouseDown(event) {
                if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
                    rMenu.css({"visibility": "hidden"});
                }
            }

            var addCount = 1;

            //增加节点
            $scope.addTreeNode = function () {
                hideRMenu();
                var newNode = {name: "增加" + (addCount++)};
                if (zTree.getSelectedNodes()[0]) {
                    console.log(zTree.getSelectedNodes()[0]);
                    zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
                    console.log(zTree.getSelectedNodes()[0]);
                } else {
                    zTree.addNodes(null, newNode);
                }
            };


            //删除节点
            $scope.removeTreeNode = function () {
                hideRMenu();
                var nodes = zTree.getSelectedNodes();
                if (nodes && nodes.length > 0) {
                    if (nodes[0].children && nodes[0].children.length > 0) {
                        var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n确定删除？";
                        if (confirm(msg) == true) {
                            zTree.removeNode(nodes[0]);
                        }
                    } else {
                        zTree.removeNode(nodes[0]);
                    }
                }
            };


            //重命名节点
            $scope.renameTreeNode = function () {
                hideRMenu();
                var nodes = zTree.getSelectedNodes();
                zTree.editName(nodes[0]);
                var newName = nodes[0].name;
                if (newName.length == 0) {
                    alert("节点不能为空！");
                    return false;
                }
            };

            var zTree, rMenu;
            //初始化BOM树
            $(document).ready(function () {
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                zTree = $.fn.zTree.getZTreeObj("treeDemo");
                rMenu = $("#rMenu");
            });

        };

        /*var data=[
         {
         "id": "1",
         "name": "han",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": true,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "childProcess": [
         {
         "id": "2",
         "name": "zhang",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": {
         "id": "1",
         "name": "han",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": true,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "childProcess": [
         {
         "id": "2",
         "name": "zhang",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": 1,
         "childProcess": [
         {
         "id": "5",
         "name": "song",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": {
         "id": "2",
         "name": "zhang",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": 1,
         "childProcess": [
         {
         "id": "5",
         "name": "song",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": 2
         },
         {
         "id": "4",
         "name": "gao",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": 2
         }
         ]
         }
         },
         {
         "id": "4",
         "name": "gao",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": 2
         }
         ]
         },
         {
         "id": "3",
         "name": "wu",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": 1
         }
         ]
         },
         "childProcess": [
         {
         "id": "5",
         "name": "song",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": 2
         },
         {
         "id": "4",
         "name": "gao",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": 2
         }
         ]
         },
         {
         "id": "3",
         "name": "wu",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": 1
         }
         ]
         },
         {
         "id": "6",
         "name": "yun",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": true,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "childProcess": [
         {
         "id": "7",
         "name": "min",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": {
         "id": "6",
         "name": "yun",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": true,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "childProcess": [
         {
         "id": "7",
         "name": "min",
         "idRoot": "1",
         "ordToRoot": 1,
         "ordToRootChild": "1",
         "ordToParent": 1,
         "typeShift": 1,
         "preemptive": "1",
         "exclusiveJob": "1",
         "exclusiveOrder": "1",
         "coupledTypeOrder": "1",
         "idCoupled": "1",
         "idCoupledT1": "1",
         "idCoupledT2": "1",
         "idCoupledTypeShift": "1",
         "idCoupledShift": "1",
         "idCoupledGroupResource": "1",
         "idCoupledTypeResource": "1",
         "idCoupledResouce": "1",
         "idCoupledTypeSite": "1",
         "idCoupledSite": "1",
         "slot1": "1",
         "slot2": "1",
         "initTime": 1,
         "unitTime": 1,
         "postTime": 1,
         "checkTime": 1,
         "delta": 1,
         "estimate": 1,
         "continuous": "1",
         "idExclusive": "1",
         "idPrec": "1",
         "idSucc": "1",
         "minTimeSucc": 1,
         "maxTimeSucc": 1,
         "quantity": 1,
         "unitQuantity": 1,
         "rgSwitch": 1,
         "minQtySwitch": 1,
         "maxQtySwitch": 1,
         "modQtySwitch": 1,
         "idSwitch": "1",
         "maxResourceDivision": 1,
         "minResourceDivision": 1,
         "modResourceDivision": 1,
         "minTimeDivision": 1,
         "maxTimeDivision": 1,
         "modTimeDivision": 1,
         "minQtyDivision": 1,
         "maxQtyDivision": 1,
         "modQtyDivision": 1,
         "minQtyBatch": 1,
         "maxQtyBatch": 1,
         "modQtyBatch": 1,
         "minTimeBatch": 1,
         "maxTimeBatch": 1,
         "modTimeBatch": 1,
         "batch": "1",
         "idIcon": "1",
         "nbTask": 1,
         "rootProcess": false,
         "productByIdProduct": {
         "id": "1",
         "name": "1",
         "type": "1",
         "ref": "1",
         "depth": "1",
         "stock": 1,
         "unit": "1",
         "state": "1",
         "supplyMethod": "1"
         },
         "processByIdProcess": 3
         }
         ]
         }
         }
         ]
         }
         ];
         console.log(data.length);
         for(var i=0;i<data.length;i++){
         console.log("根节点");
         console.log(data[i]);
         var datas=data[i];
         console.log("是否有一级");
         console.log(datas.hasOwnProperty("childProcess"));
         if(datas.hasOwnProperty("childProcess")==true){
         var childList=data[i].childProcess;
         console.log("根节点下的一级");
         console.log(childList);
         console.log("根节点下的一级长度");
         console.log(childList.length);
         for(var j=0;j<childList.length;j++){
         console.log("一级节点下是否有");
         console.log(childList[j].hasOwnProperty("childProcess"));
         var childLists=childList[j];
         if(childLists.hasOwnProperty("childProcess")==true){
         console.log("一级节点下删除前的二级");
         console.log(childLists);
         var temps=childLists.childProcess;
         console.log(temps);
         delete(childLists.childProcess);
         childLists.children=temps;
         console.log("一级节点下删除后的二级");
         console.log(childLists);
         childList[j]=childLists;
         }
         }
         console.log(childList);
         var temp=datas.childProcess;
         delete(datas.childProcess);
         datas.children=temp;
         }

         }
         console.log(data);
         zNodes=data;*/


        /* var zNodes = [
         {
         id: 1, name: "右键菜单 1", open: true,
         children: [
         {
         id: 11, name: "节点 1-1",
         children: [
         {id: 21, name: "节点 1-11"},
         {id: 22, name: "节点 1-12"},
         {id: 23, name: "节点 1-13"},
         {id: 24, name: "节点 1-14"}
         ]
         },
         {id: 12, name: "节点 1-2"}
         ]
         },
         {
         id: 2, name: "右键操作 2", open: true,
         children: [
         {id: 21, name: "节点 2-1"},
         {id: 22, name: "节点 2-2"},
         {id: 23, name: "节点 2-3"},
         {id: 24, name: "节点 2-4"}
         ]
         }
         ];*/

        //右键操作
        /*function OnRightClick(event,treeNode) {
         var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
         var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
         var x = event.pageX || event.clientX + scrollX;
         var y = event.pageY || event.clientY + scrollY;
         if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
         zTree.cancelSelectedNode();
         showRMenu("root", x, y);
         } else if (treeNode && !treeNode.noR) {
         zTree.selectNode(treeNode);
         showRMenu("node", x, y);
         }
         }*/


    });

