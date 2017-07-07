/**
 * Created by XY on 2017/7/6.
 */
'use strict';
angular.module("IntegratedFramework.ScheduleSnapController", ['ngRoute'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/ScheduleSnap', {
            templateUrl: 'pages/PlanSchedule/ScheduleSnap.html',
            controller: 'ScheduleSnapController'
        })
    }])
    .controller('ScheduleSnapController', function ($scope, $http, myHttpService, serviceList) {

        $(function () {
            //初始化下拉数据
            $(".select2").select2();
        });

        var dataTrue = {"level": "top"};
        myHttpService.post(serviceList.isRootLevel, dataTrue).then(function successCallback(response) {
            var setting = {
                view: {
                    dblClickExpand: false,
                    showIcon: false,
                    showLine: false
                },
                callback: {
                    onRightClick: OnRightClick,
                    onClick:onClick
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
            var zNodes = [];

            console.log("*****" + response.status);
            console.log(response.data);
            var dataArr = response.data;
            $scope.dataArr = response.data;


            for (var i = 0; i < dataArr.length; i++) {
                console.log("根节点");
                console.log(dataArr[i]);
                var datas = dataArr[i];
                console.log("是否有一级");
                console.log(datas.hasOwnProperty("childs"));
                if (datas.hasOwnProperty("childs") == true) {
                    var childList = dataArr[i].childs;
                    console.log("根节点下的一级");
                    console.log(childList);
                    console.log("根节点下的一级长度");
                    console.log(childList.length);
                    for (var j = 0; j < childList.length; j++) {
                        console.log("一级节点下是否有");
                        console.log(childList[j].hasOwnProperty("childs"));
                        var childLists = childList[j];
                        if (childLists.hasOwnProperty("childs") == true) {
                            console.log("一级节点下删除前的二级");
                            console.log(childLists);
                            var temps = childLists.childs;
                            console.log(temps);
                            delete(childLists.childs);
                            childLists.children = temps;
                            console.log("一级节点下删除后的二级");
                            console.log(childLists);
                            childList[j] = childLists;
                        }
                    }
                    console.log(childList);
                    var temp = datas.childs;
                    delete(datas.childs);
                    datas.children = temp;
                }
            }
            console.log(dataArr);
            //zNodes = data;
            zNodes = dataArr;
            console.log("&&&&&&&&&");
            console.log(zNodes);

            function OnRightClick(event, treeId, treeNode) {
                var x = event.screenX;
                var y = event.screenY;
                /*var x = event.pageX || (event.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft));
                 var y = event.pageY || (event.clientY + (document.documentElement.scrollTop || document.body.scrollTop));*/
                if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
                    zTree.cancelSelectedNode();
                    hideRMenu("root", x, y);

                }else {
                    zTree.selectNode(treeNode);
                        showRMenu("node", x, y);

                }
            }

            function onClick(treeNode) {
                zTree.expandNode(treeNode);
            }

            //显示右键操作
            function showRMenu(x, y) {

                rMenu.css({"top": (y-210) + "px", "left": (x+200)+ "px", "visibility": "visible"});
                $("#container").bind("mousedown", onBodyMouseDown);
            }


            //隐藏右键操作
            function hideRMenu() {
                if (rMenu) rMenu.css({"visibility": "hidden"});
                $("#container").unbind("mousedown", onBodyMouseDown);
            }



            //鼠标按下操作
            function onBodyMouseDown(event) {
                if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0 || event.target.id == "lMenu" || $(event.target).parents("#lMenu").length > 0)) {
                    rMenu.css({"visibility": "hidden"});
                    lMenu.css({"visibility": "hidden"});
                }
            }


            var addCount = 1;
            //增加节点
            $scope.addTreeNode = function () {
                hideRMenu();
                var newNode = {name: "增加" + (addCount++)};
                if (zTree.getSelectedNodes()[0]) {
                    console.log("@@@@@@");
                    console.log(zTree.getSelectedNodes());
                    console.log(zTree.getSelectedNodes()[0]);//当前右击的节点（包含自己的）子节点
                    console.log(zTree.getSelectedNodes()[0].id);//当前点击节点的id值
                    zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
                } else {
                    zTree.addNodes(null, newNode);
                }
                getChildNodes();
                console.log("当前操作的节点id:" + zTree.getSelectedNodes()[0].id);
            };

            //删除节点
            $scope.removeTreeNode = function () {
                hideRMenu();
                hideLMenu();
                console.log("当前操作的节点id:" + zTree.getSelectedNodes()[0].id);
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
                hideLMenu();
                console.log("当前操作的节点id:" + zTree.getSelectedNodes()[0].id);
                var nodes = zTree.getSelectedNodes();
                zTree.editName(nodes[0]);
                var newName = nodes[0].name;
                if (newName.length == 0) {
                    alert("节点不能为空！");
                    return false;
                }
                console.log(zTree.getSelectedNodes()[0]);
            };


            var zTree, rMenu;
            //初始化BOM树
            $(document).ready(function () {
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                zTree = $.fn.zTree.getZTreeObj("treeDemo");
                rMenu = $("#rMenu");

            });
        });

    });

