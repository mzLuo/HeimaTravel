/*
使用jquery后台加载头部和尾部
 */
$(function() {
    //加载头部
    $.get({
        url:"header.html",
        async:false,//同步加载，如果这个页面没有加载完毕，不继续向后执行
        success:function(html) {//返回整个网页
            //将整个网页显示在div中
            $("#header").html(html);
        }
    });

    $.get({
        url:"footer.html",
        async:false,
        success:function(html) {
            //将整个网页显示在div中
            $("#footer").html(html);
        }
    });
});