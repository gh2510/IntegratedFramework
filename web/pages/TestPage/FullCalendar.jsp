<%--
  Created by IntelliJ IDEA.
  User: hanchangming
  Date: 2017/5/16
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel='stylesheet' href='lib/FullCalendar-3.4.0/fullcalendar.min.css'/>
</head>
<body>
<div id='calendar'></div>
<script src='lib/jquery/jquery-3.2.1.min.js'></script>
<script src='lib/Moment/moment-with-locales.js'></script>
<script src='lib/FullCalendar-3.4.0/fullcalendar.min.js'></script>
<script>
    //当前排程时间长度（b）
    var scheduleDays = 30;
    //上次排程时间长度（c）
    var lastScheduleDays = 30;
    //距上次开始排程的日期差(c)
    var tempDays = 7;
    //排程开始时间
    var startTime = moment().format("YYYY-MM-DD");
    //排程结束时间
    var endTime = moment().add(scheduleDays, 'day').format("YYYY-MM-DD");
    $(document).ready(function () {
        // page is now ready, initialize the calendar...
        $('#calendar').fullCalendar({
            // put your options and callbacks here
            buttonText: {
                today: '今天',
                month: '月',
                week: '周',
                day: '天'
            },
            allDayText: '全天',
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
            dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
            eventSources: [
                {
                    url: 'http://localhost:8080/FullCalendar/getAllFullCalendarEvents.action',
                    type: 'POST',
                    data: {
                        startTime: startTime,
                        endTime: endTime
                    },
                    error: function () {
                        alert('there was an error while fetching events!');
                    }
                }
            ],
            viewRender: function (view, element) {
                //已执行时间窗口染色
                for (var i = 1; i <= tempDays; i++) {
                    $("td[data-date='" + moment().add(-i, "day").format('YYYY-MM-DD') + "']").css('backgroundColor', 'red');
                }
                //时间窗口染色
                for (var i = 0; i < lastScheduleDays - tempDays; i++) {
                    $("td[data-date='" + moment().add(i, "day").format('YYYY-MM-DD') + "']").css('backgroundColor', 'blue');
                }
                //剩余窗口染色
                for (var i = 0; i < scheduleDays - (lastScheduleDays - tempDays); i++) {
                    $("td[data-date='" + moment().add((lastScheduleDays - tempDays) + i, "day").format('YYYY-MM-DD') + "']").css('backgroundColor', 'green');
                }
            }
        });
    });
</script>
</body>
</html>
