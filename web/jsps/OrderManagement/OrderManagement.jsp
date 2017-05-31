<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
    /*
        所有input的背景色
    */
    /*.uk-form input:not([type="radio"]):not([type="checkbox"]), .uk-form select {
        vertical-align: middle;
        background-color: #eef7fc;
        height: 23px;
    }*/
    /*
        表格奇数行颜色
    */
    /*.uk-table-striped tbody tr:nth-of-type(2n+1) {
        background: #f3f8fb;
    }*/


    /*tr{
        height: 45px;
    }*/

    /*.uk-tab-grid > li:first-child > a:hover {
        background: url("../../images/bom_img/gongxu.png") no-repeat 30px;
        background-position: 15px;
    }
    .uk-tab > li:nth-child(n+2) > a:hover{
        background: url("../../images/bom_img/gongxu.png") no-repeat 30px;
        background-position: 20px;
    }*/


    /*.bg{
        background-color: #c1edfa;
    }*/

    /*
           页码li样式
    */
    /*.uk-pagination > li {
        display: inline-block;
        font-size: 1rem;
        vertical-align: middle;
    }*/

    /*
           页码样式
    */
    /*.my {
        background-color: #009dd8;
        color: #fff;
        background-image:url("../../images/bom_img/ye3.png");
        border-color: rgba(0,0,0,.2);
        border-bottom-color: rgba(0,0,0,.4);
        text-shadow: 0 -1px 0 rgba(0,0,0,.2);
    }*/
    /*.my:hover{
        background-color: #50FCF9;;
    }*/

    /*
        表格固定
    */

    /*.fixtable-head table,.table-body table{width:100%;text-align:left;}
    .fixtable-body{width:100%; height:250px;overflow-y:scroll;text-align:left;}
    */

    .uk-table td {
        vertical-align: top;
        width: 30px;
        margin-left: 11px;
    }



</style>

<link href="../../mycss/mycss.css" type="text/css" rel="stylesheet">

<script>
    function changeColor(obj) {
        var f = obj.checked;
        var chkColor = "#c1edfa"; //选中后颜色
        var ouColor = "#f3f8fb";  //偶数行取消选中后的颜色
        var jiColor = "#FFFFFF";
        if(f)
            obj.parentElement.parentElement.style.backgroundColor = chkColor;
        else
            obj.parentElement.parentElement.style.backgroundColor = jiColor;
    }
</script>

<div class="<%--uk-grid--%>" style="height: 8%;margin-top: 10px;background-color: white;margin-left: 0px;width: 100%;">

    <%--<img src="../../images/bom_img/shu.png" style="margin-left: -35px;width: 40px;">--%>
    <%--<div class="uk-width-1-4 ">--%>
    <img src="../../images/bom_img/shu.png" style="width: 5px;float: left;height: 35px;">


    &nbsp;
    <span style="font-size: 18px;font-weight: 700;margin-top: 10px;/*margin-left: -24px;*/font-family: 微软雅黑">订单管理</span>
    <%--</div>--%>
    <%--<img src="../../images/bom_img/shu.png" style="width: 5px;float: left;height: 35px;">


&nbsp;
<span style="font-size: 18px;font-weight: 700;margin-top: 10px;/*margin-left: -24px;*/font-family: 微软雅黑">制造BOM管理</span>--%>
    <!--搜索-->
    <%--<div class="uk-width-1-4 " >--%>
    <%--<div class="uk-autocomplete" data-uk-autocomplete="{source:'/jsps/OrderManagement/_Aotu.json' }">--%>
    <form class="uk-search" data-uk-search style="margin-left: 2%;margin-top: 5px;background-color: #e8edf1;">
        <input class="uk-search-field" type="search" placeholder="请输入搜索项"
               style="width: 150px;border: 1px solid lightgray;">
    </form>
    <%--            </div>--%>
    <%--</div>--%>

        <!--日历-->

        <%--<div class="uk-width-1-4" style="float: left;margin-top: 10px;margin-left: -70px;">--%>
        <form class="uk-form" style="margin-left: 2%;margin-top: 5px;">
            <div class="uk-form-icon">
                <i class="uk-icon-calendar"></i>
                <input type="text" id="datepicker"  data-uk-datepicker="{format:'YYYY/MM/DD'}"
                       placeholder="2017/5/17">
            </div>
        </form>
        <%--</div>--%>

    <!--按钮-->
    <%--<div class="uk-width-1-4" style="margin-left: 36%;">--%>
    <div class="data-uk-button-radio" style="margin-top: 5px;float: right;margin-right: 1%;">
        <button class="uk-button uk-icon-plus uk-button-primary">新增</button>
        <button class="uk-button uk-icon-edit uk-button-primary" data-uk-modal="{target:'#edit'}">修改</button>
        <button class="uk-button uk-icon-trash uk-button-primary">删除</button>
    </div>
    <%--</div>--%>
</div>
<%--<hr class="uk-article-divider">--%>
<br/>
<%--<div class="uk-grid" style="margin-top: -25px;">--%>

    <!--右侧表格-->
    <div class="uk-width-4-5" style="width: 98%;border: 1px solid lightgray;margin-left: 1%;height: 85%;background-color: white;">
        <div style="margin-top: 1%;margin-left: 1%;">
            <!--表格上部-->
            <form class="uk-form">
                <fieldset data-uk-margin>
                    <div class="uk-form-row" style="margin: 3px">
                        <label style="margin-right: 3px;">产品名称</label>
                        <input type="text" placeholder="零件1">&nbsp;&nbsp;
                        <label style="margin-right: 3px">产品编码</label>
                        <input type="text" placeholder="CH1">&nbsp;&nbsp;
                        <label style="margin-right: 3px">规格型号</label>
                        <input type="text" placeholder="60*80">&nbsp;&nbsp;
                        <label style="margin-right: 3px">计量单位</label>
                        <input type="text" placeholder="套">
                    </div>

                    <br>
                </fieldset>
            </form>
            <!--tab-->
            <div style="width: 98%;border: 0px;margin-top: -10px;">


                <div id="tabs-2" style="width: 100%;height: 86%;">

                    <!--表格-->
                    <div class="uk-overflow-container" style="height: 93%;">
                        <div class="fixtable-head">
                            <table class="uk-table uk-table-striped uk-table-hover ">
                                <thead class="uk-text-center">
                                <tr style="background-color: #e1eaf1;">
                                    <td>
                                        <div style="border: 1px solid lightgray;margin-left: 25%;width: 30px;height: 15px;background-color: #cddae3;">
                                            <img src="../../images/bom_img/select.png" style="width: 15px;width: 12px;margin-left: 16px;margin-top: 3px;">
                                        </div>

                                    </td>
                                    <td>编码</td>
                                    <td>名称</td>
                                    <td>来源</td>
                                    <td>产品名</td>
                                    <td>数量</td>
                                    <td>优先级</td>
                                    <td>下单时间</td>
                                    <td>最早开工</td>
                                    <td>最晚开工</td>
                                </tr>
                                </thead>
                            </table>
                        </div>

                        <div class="fixtable-body" style="height: 83%;">
                            <table class="uk-table uk-table-striped uk-table-hover " id="order">
                               <%-- <thead type="hidden">
                                &lt;%&ndash;<tbody>&ndash;%&gt;
                                <tr style="background-color: #e1eaf1;">
                                    <td>
                                        <div style="border: 1px solid lightgray;width: 30px;height: 15px;background-color: #cddae3;">
                                            <img src="../../images/bom_img/select.png" style="width: 15px;width: 12px;margin-left: 16px;margin-top: 3px;">
                                        </div>

                                    </td>
                                    <td>编码</td>
                                    <td>名称</td>
                                    <td>来源</td>
                                    <td>产品名</td>
                                    <td>数量</td>
                                    <td>优先级</td>
                                    <td>下单时间</td>
                                    <td>最早开工时间</td>
                                    <td>最晚开工时间</td>
                                </tr>
                                </thead>--%>
                                <tbody class="uk-text-center">
                                <tr>
                                    <td><input type="checkbox" onclick="changeColor(this)"></td>
                                    <td>1001</td>
                                    <td>待定</td>
                                    <td>...</td>
                                    <td>待定</td>
                                    <td>4</td>
                                    <td>5</td>
                                    <td>待定</td>
                                    <td>待定</td>
                                    <td>待定</td>

                                </tr>
                                <tr>
                                    <td><input type="checkbox" onclick="changeColor(this)"></td>
                                    <td>1001</td>
                                    <td>待定</td>
                                    <td>...</td>
                                    <td>待定</td>
                                    <td>4</td>
                                    <td>5</td>
                                    <td>待定</td>
                                    <td>待定</td>
                                    <td>待定</td>

                                </tr>
                                <tr>
                                    <td><input type="checkbox" onclick="changeColor(this)"></td>
                                    <td>1001</td>
                                    <td>待定</td>
                                    <td>...</td>
                                    <td>待定</td>
                                    <td>4</td>
                                    <td>5</td>
                                    <td>待定</td>
                                    <td>待定</td>
                                    <td>待定</td>

                                </tr>
                                <tr>
                                    <td><input type="checkbox" onclick="changeColor(this)"></td>
                                    <td>1001</td>
                                    <td>待定</td>
                                    <td>...</td>
                                    <td>待定</td>
                                    <td>4</td>
                                    <td>5</td>
                                    <td>待定</td>
                                    <td>待定</td>
                                    <td>待定</td>

                                </tr>
                                <tr>
                                    <td><input type="checkbox" onclick="changeColor(this)"></td>
                                    <td>1001</td>
                                    <td>待定</td>
                                    <td>...</td>
                                    <td>待定</td>
                                    <td>4</td>
                                    <td>5</td>
                                    <td>待定</td>
                                    <td>待定</td>
                                    <td>待定</td>

                                </tr>
                                <tr>
                                    <td><input type="checkbox" onclick="changeColor(this)"></td>
                                    <td>1001</td>
                                    <td>待定</td>
                                    <td>...</td>
                                    <td>待定</td>
                                    <td>4</td>
                                    <td>5</td>
                                    <td>待定</td>
                                    <td>待定</td>
                                    <td>待定</td>

                                </tr>
                                <tr>
                                    <td><input type="checkbox" onclick="changeColor(this)"></td>
                                    <td>1001</td>
                                    <td>待定</td>
                                    <td>...</td>
                                    <td>待定</td>
                                    <td>4</td>
                                    <td>5</td>
                                    <td>待定</td>
                                    <td>待定</td>
                                    <td>待定</td>

                                </tr>
                                <tr>
                                    <td><input type="checkbox" onclick="changeColor(this)"></td>
                                    <td>1001</td>
                                    <td>待定</td>
                                    <td>...</td>
                                    <td>待定</td>
                                    <td>4</td>
                                    <td>5</td>
                                    <td>待定</td>
                                    <td>待定</td>
                                    <td>待定</td>

                                </tr>
                                </tbody>

                            </table>

                            <!--底部页码-->
                            <div style="margin-top: -25px;">
                                <ul class="uk-pagination" style="margin-top: 7%;" data-uk-pagination="{currentPage:50}">
                                    <li><button class="uk-button" style="background-image: url('../../images/bom_img/ye1.png');color: white;"><a href="" style="color: white;">首页</a></button></li>
                                    <li><button class="uk-button my"><a href="">上一页</a></button></li>
                                    <li><button class="uk-button my"><a href="">下一页</a></button></li>
                                    <li><button class="uk-button my"><a href="">尾页</a></button></li>
                                    <%--<li><a href="#">上一页</a></li>
                                    <li><a href="#">下一页</a></li>
                                    <li><a href="#">尾页</a></li>--%>
                                    <li>共88</li>&nbsp;
                                    <li>
                                        到第<input type="text" value="2" style="width: 28px;background-color: #EEF7FC;">页
                                    </li>
                                    <li>
                                        <button class="uk-button" style="background-image: url('../../images/bom_img/ye2.png');color: white;">确定</button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <%--<table class="uk-table uk-table-striped uk-table-hover " id="order">
                            <thead>
                            <tr style="background-color: #e1eaf1;">
                                <td>
                                    <div style="border: 1px solid lightgray;width: 30px;height: 15px;background-color: #cddae3;">
                                        <img src="../../images/bom_img/select.png" style="width: 15px;width: 12px;margin-left: 16px;margin-top: 3px;">
                                    </div>

                                </td>
                                <td>编码</td>
                                <td>名称</td>
                                <td>来源</td>
                                <td>产品名</td>
                                <td>数量</td>
                                <td>优先级</td>
                                <td>下单时间</td>
                                <td>最早开工时间</td>
                                <td>最晚开工时间</td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><input type="checkbox" onclick="changeColor(this)"></td>
                                <td>1001</td>
                                <td>待定</td>
                                <td>...</td>
                                <td>待定</td>
                                <td>4</td>
                                <td>5</td>
                                <td>待定</td>
                                <td>待定</td>
                                <td>待定</td>

                            </tr>
                            <tr>
                                <td><input type="checkbox" onclick="changeColor(this)"></td>
                                <td>1001</td>
                                <td>待定</td>
                                <td>...</td>
                                <td>待定</td>
                                <td>4</td>
                                <td>5</td>
                                <td>待定</td>
                                <td>待定</td>
                                <td>待定</td>

                            </tr>
                            <tr>
                                <td><input type="checkbox" onclick="changeColor(this)"></td>
                                <td>1001</td>
                                <td>待定</td>
                                <td>...</td>
                                <td>待定</td>
                                <td>4</td>
                                <td>5</td>
                                <td>待定</td>
                                <td>待定</td>
                                <td>待定</td>

                            </tr>
                            <tr>
                                <td><input type="checkbox" onclick="changeColor(this)"></td>
                                <td>1001</td>
                                <td>待定</td>
                                <td>...</td>
                                <td>待定</td>
                                <td>4</td>
                                <td>5</td>
                                <td>待定</td>
                                <td>待定</td>
                                <td>待定</td>

                            </tr>
                            <tr>
                                <td><input type="checkbox" onclick="changeColor(this)"></td>
                                <td>1001</td>
                                <td>待定</td>
                                <td>...</td>
                                <td>待定</td>
                                <td>4</td>
                                <td>5</td>
                                <td>待定</td>
                                <td>待定</td>
                                <td>待定</td>

                            </tr>
                            <tr>
                                <td><input type="checkbox" onclick="changeColor(this)"></td>
                                <td>1001</td>
                                <td>待定</td>
                                <td>...</td>
                                <td>待定</td>
                                <td>4</td>
                                <td>5</td>
                                <td>待定</td>
                                <td>待定</td>
                                <td>待定</td>

                            </tr>
                            <tr>
                                <td><input type="checkbox" onclick="changeColor(this)"></td>
                                <td>1001</td>
                                <td>待定</td>
                                <td>...</td>
                                <td>待定</td>
                                <td>4</td>
                                <td>5</td>
                                <td>待定</td>
                                <td>待定</td>
                                <td>待定</td>

                            </tr>
                            <tr>
                                <td><input type="checkbox" onclick="changeColor(this)"></td>
                                <td>1001</td>
                                <td>待定</td>
                                <td>...</td>
                                <td>待定</td>
                                <td>4</td>
                                <td>5</td>
                                <td>待定</td>
                                <td>待定</td>
                                <td>待定</td>

                            </tr>
                            </tbody>

                        </table>--%>

                        <%--<!--底部页码-->
                        <div style="margin-top: 7%;">
                            <ul class="uk-pagination" style="margin-top: 7%;" data-uk-pagination="{currentPage:50}">
                                <li><button class="uk-button uk-button-primary"><a href="" style="color: white;">首页</a></button></li>
                                <li><button class="uk-button my"><a href="" style="color: white;">上一页</a></button></li>
                                <li><button class="uk-button my"><a href="" style="color: white;">下一页</a></button></li>
                                <li><button class="uk-button my"><a href="" style="color: white;">尾页</a></button></li>
                                &lt;%&ndash;<li><a href="#">上一页</a></li>
                                <li><a href="#">下一页</a></li>
                                <li><a href="#">尾页</a></li>&ndash;%&gt;
                                <li>共88</li>&nbsp;
                                <li>
                                    到第<input type="text" value="2" style="width: 28px;background-color: #EEF7FC;">页
                                </li>
                                <li>
                                    <button class="uk-button uk-button-primary">确定</button>
                                </li>
                            </ul>



                        </div>--%>
                    </div>
                </div>

            </div>
        </div>


        <div class="uk-clearfix" style="margin-top: -3%;">
            <button class="uk-button uk-float-right " id="create-order"
                    <%--data-uk-sticky="{top:500,boundary:'#add-a-delay'}"
                    data-uk-toggle="{target:'#button'}"
                    data-uk-tooltip="{pos:'top'}"--%>
                    style="border-radius:50%;background: rgba(0,0,0,0.3);color: #fff;top:220px;width: 45px;height: 45px;margin-top: -8px;"
                    title="快捷菜单">+
            </button>
            <div class=" uk-hidden uk-float-right" id="button">
                <div class="uk-panel uk-panel-box">
                    <div class="data-uk-button-radio">
                        <button class="uk-button" style="margin: 3px" data-uk-modal="{target:'#group'}">DAG面板</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
<%--</div>--%>

<script>
    $(function () {
        $("#tabs").tabs();
    });
</script>

<script>
    $(".item-1").click(function () {
        $(this).parent().find(".nav-second").slideToggle(500);
        $(this).children("i").toggleClass("unfold");
    });
    $(".item-2").click(function () {
        $(this).parent().find(".nav-three").slideToggle(500);
        $(this).children("i").toggleClass("unfold");
    });

</script>
