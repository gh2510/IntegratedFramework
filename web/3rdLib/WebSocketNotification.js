/**
 * Created by hanchangming on 2017/5/17.
 */
var webSocket;
var username;
var webSocketUrl;
var notyf;

function getUsername() {
    username = navigator.appName + new Date().getTime();
    // webSocketUrl = "ws://localhost:8080/IntegratedFramework/notification/" + username;
    webSocketUrl = "ws://localhost:8080/notification/" + username;
}

function webSocketInit() {

    notyf = new Notyf();

    getUsername();
    if (typeof WebSocket !== 'undefined') {
        webSocket = new WebSocket(webSocketUrl);
        //连接发生错误的回调方法
        webSocket.onerror = function () {
            showNotification("通知系统出现错误。", "alert")
        };
        //连接成功建立的回调方法
        webSocket.onopen = function () {
            showNotification("通知系统开启。", "confirm");
        }
        //接收到消息的回调方法
        webSocket.onmessage = function (event) {
            var webSocketObject = JSON.parse(event.data);
            if (webSocketObject.MessageType == "notification") {
                showNotification(webSocketObject.Message, webSocketObject.NotificationType)
            }
            if (webSocketObject.MessageType == "3DMessage") {
                //Todo 3D车间消T息发送函数需要补充，暂时用打印代替。
                console.log(webSocketObject.Message);
            }
        }
        //连接关闭的回调方法
        webSocket.onclose = function () {
            showNotification("通知系统关闭。", "alert");
        }
        window.onbeforeunload = function () {
            webSocket.close();
        }
    } else {
        showNotification("您的浏览器不支持消息通知模块，请升级浏览器。", "alert");
    }
}

function showNotification(message, status) {

    if (status === "alert") {
        notyf.alert(message);
    }
    if (status === "confirm") {
        notyf.confirm(message);
    }
}