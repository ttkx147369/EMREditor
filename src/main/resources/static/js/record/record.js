//初始化layui插件
var layer, form;
layui.use(['layer', 'form'], function () {
    layer = layui.layer;
    form = layui.form;
    form.on('submit(emr)', function(data){
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});
$(function () {
    /**
     * 获取模板标题
     */
    $.ajax({
        url: '/dpage/getPageData',
        async: false,
        data: {idpage: getQueryString('idpage')},
        type: "POST",
        success: function (res) {
            $('#optfile').append('<h1 style="' + (res[0].textcss == null ? '' : res[0].textcss) + (res[0].cssstyle == null ? '' : res[0].cssstyle) + '" data_id="">' +
                res[0].page_title + '</h1>');//title样式不能修改么？？？
        },
        error: function (res) {
            console.log(res);
        }
    });
    /**
     * 获取模板内容
     */
    $.ajax({
        url: '/dpage/getPageEle',
        async: false,
        data: {idpage: getQueryString('idpage')},
        type: "POST",
        success: function (res) {
            $('#optfile').find('h1').after('<form class="layui-form">'+makePageEle(res)+'</form>');
            $('#optfile').find("form").find("div:last").after('<div style="text-align: center;">' +
                '<button lay-submit lay-filter="emr" class="layui-btn layui-btn-normal layui-btn-lg">提交</button></div>');
        },
        error: function (res) {
            console.log(res);
        }
    });
})
