<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="common/head.jsp"/> <!-- 动态包含 -->
<%@include file="common/tag.jsp"%> <!-- 静态包含-->
<html>
<head>
    <title>秒杀商品详细页</title>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="pannel-heading">
                <h1>${seckill.name}</h1>
            </div>
        </div>
    </div>

    <%-- 登录弹出层 输入电话 --%>
    <div id="killPhoneModel" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title text-center">
                        <span class="glyphicon" gyphicon-phone></span>秒杀电话
                    </h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" name="killPhone" id="killPhoneKey" placeholder="请填写手机号^_^" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <%-- 验证信息 --%>
                    <span id="killPhoneMessage" class="glyphicon"></span>
                    <button type="button" id="killPhoneBtn" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone"></span>
                        Submit
                    </button>
                </div>
            </div>
        </div>
    </div>

</body>
<%-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) --%>
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<%-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
<%--jQuery Cookie操作插件--%>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>--%>
<%--jQuery countDown倒计时插件--%>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>
<%--<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>--%>
<%--开始编写交互逻辑--%>

<script src="/resoueces/script/seckill.js" type="text/javascript"/>
<script>
    $(function(){
        // 使用EL表达式传入参数
        seckill.detail.init({ //初始化程序
            seckillId : ${seckill.seckillId},
            startTime : ${seckill.startTime.time},//系统毫秒时间方便JS解析
            endTime:${seckill.endTime.time}
        });
    });
</script>
</html>
