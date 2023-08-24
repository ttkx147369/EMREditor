layui.use(['layer', 'form'], function(){
    var layer = layui.layer
        ,form = layui.form;
});
var editor=getQueryString("editor");
$(function () {
    $.ajax({
        url:'/dpage/getPageData',
        async:true,
        data:{},
        type:"POST",
        success:function (res) {
            var str='';
            for(var i=0,len=res.length;i<len;i++){
                var pageType='';
                if(res[i].page_type==1)
                    pageType='信息展示*只有信息展示，没有任何表单内容';
                else if(res[i].page_type==2)
                    pageType='表单填写*没有任何计算和评估内容，所有保存项均在表单项中';
                else if(res[i].page_type==3)
                    pageType='简单评估*表单页面（包含计算公式），只有一个结果或多个结果，结果包含在表单项中';
                else if(res[i].page_type==4)
                    pageType='复杂评估*包含多个结果，评估结果不仅仅在表单项中，还包含其他内容';
                else
                    pageType='';
                str+='<tr>';
                str+='<td>'+res[i].idpage+'</td><td>'+res[i].page_title+'</td>';
                str+='<td title="'+pageType.split("*")[1]+'">'+pageType.split("*")[0]+'</td>';
                str+='<td>'+res[i].page_create+'</td><td>'+res[i].page_update+'</td>';
                str+='<td>'+res[i].pagecol+'</td><td>'+res[i].pagecol1+'</td>';
                str+='<td>'+res[i].pagecol2+'</td><td>'+res[i].pagecol3+'</td>';
                str+='<td>'+res[i].pagecol4+'</td><td>'+res[i].pagecol5+'</td>';
                str+='<td>';
                if(editor||editor==='true') {
                    str += '<button class="layui-btn layui-btn-xs layui-btn-danger" data_id="' + res[i].idpage + '">删除</button>';
                    str += '<button datalist class="layui-btn layui-btn-xs" data_id="' + res[i].idpage + '">修改</buttondatalist>';
                    str += '<button class="layui-btn layui-btn-xs layui-btn-warm" data_id="' + res[i].idpage + '" page_type="'+res[i].page_type+'">设计病历</button></td>';
                }else{
                    str+='<a href="/record/record?idpage='+res[i].idpage+'" class="layui-btn layui-btn-xs layui-btn-danger">填写病历/评测</a>';
                }
                str+='</tr>';
            }
            $('#datalist').append(str);
            $('#datalist').find('tr').each(function(){
                $(this).find('button').each(function(index,element){
                    if(index==0){
                        $(element).click(function(){
                            $.ajax({
                                url:'/dpage/delPageData',
                                async:true,
                                data:{'idpage':$(element).attr("data_id")},
                                type:"POST",
                                success:function (res) {
                                    if(res>0) alert("删除成功");
                                    else alert("删除失败")
                                },
                                error:function (res) {
                                    console.log(res);
                                }
                            })
                        });
                    }else if(index==1){
                        $(element).click(function(){
                            location.href='/ppage/addpAddData?idpage='+$(element).attr("data_id");
                        })
                    }else if(index==2){
                        $(element).click(function(){
                            location.href='/ppage/todesign?idpage='+$(element).attr("data_id")+'&page_type='+$(element).attr("page_type");
                        })
                    }
                })
            });
        },
        error:function (res) {
            console.log(res);
        }
    })
});