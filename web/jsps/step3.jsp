<%--
  Created by IntelliJ IDEA.
  User: XY
  Date: 2017/5/11
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>计划排程向导</title>

    <link rel="stylesheet" href="../lib/UIKit/css/uikit.min.css"/>
    <script src="../lib/jquery/jquery-3.2.1.min.js"></script>
    <script src="../lib/UIKit/js/uikit.min.js"></script>
    <script src="../lib/UIKit/js/uikit-icons.min.js"></script>
    <style type="text/css">
        #progressbar {
            width: 90%;
            height: 25px;
            margin-top: 15%;
            margin-left: 5%;
        }
    </style>
</head>
<body>
<br/>
<div id="all" style="margin-left: 10px;">
    <div id="hs">
        <p uk-margin>
            <button class="uk-button uk-button-primary uk-button-small">Step 1</button>
            >
            <button class="uk-button uk-button-primary uk-button-small">Step 2</button>
            >
            <button class="uk-button uk-button-primary uk-button-small" style="background-color: #3873D6">Step 3
            </button>
        </p>
    </div>
    <br/>
    <div style="border: 1px solid #e6e451;width: 80%;height: 350px;">
        <progress id="progressbar" class="uk-progress" value="10" max="100"></progress>

        <script>

            jQuery(function () {

                var animate = setInterval(function () {

                    window.progressbar && (progressbar.value += 10);

                    if (!window.progressbar || progressbar.value >= progressbar.max) {
                        clearInterval(animate);
                    }

                }, 200);

            });

        </script>
    </div>

    <br/>
    <div id="next">
        <a href="step2.jsp">
            <button class="uk-button uk-button-primary uk-button-small" style="background-color: orange;color: white;">
                上一步
            </button>
        </a>
    </div>
</div>

</body>
</html>