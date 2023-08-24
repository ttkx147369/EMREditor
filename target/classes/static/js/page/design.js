/**
 * 普通的表单元素和表格是分开的，
 * 弹窗也是两个不同的方法体系
 */
var courrentId = null;
//被选中的元素
var selectedEle = null;
var pageSize = {width: document.documentElement.clientWidth, height: document.documentElement.clientHeight};
var preview = getQueryString("preview");//记录是否是设计预览
var page_type = getQueryString("page_type");
var url = getQueryString("url") == null ? null : getQueryString("url").replace(/\*\*/ig, "&");
var idpage = getQueryString('idpage');//模板表主表主键

$(function(){
    initDesignPage();
    //初始化layui插件
    layui.use(['layer', 'form'], function () {
        layer = layui.layer;
        form = layui.form;

        form.on('select(ele_type)', function (data) {
            showAlertEle(data.value, 'addform'); //得到被选中的值
        });
        //添加计算公式输入框点击事件
        $("input[name=calculate]").each(function () {
            $(this).click(function () {
                var THIS = this;
                layer.open({
                    type: 1,
                    title: '计算公式',
                    area: ['1020px', '90%'],
                    content: '<div class="layui-form"><textarea id="calculate" class="layui-textarea" style="position: fixed;z-index: 1;width: 1000px;"></textarea>'
                        + '<div style="height: 35px;"></div><div id="calculatecon">' + $("#optfile").find("form").html() + "</div></div>",
                    btn: ['确定', '取消'],
                    yes: function (index, layero) {
                        THIS.value = layero.find('textarea')[0].value;
                    },
                    btn2: function (index, layero) {

                    }
                });
                var inputName;
                $('#calculatecon').find('input').each(function () {
                    if (this.type !== 'checkbox') {//没有多选，只有单选的情况下，当只有一个选项的时候选中了就取消不了，怎么处理
                        if (inputName !== this.name) {
                            $(this).parent().click(function () {
                                calculateSelect($(this).find('input')[0])
                            });
                            inputName = this.name;
                        }
                    }
                });
                $('#calculatecon').find('select').each(function () {
                    $(this).parent().click(function () {
                        calculateSelect($(this).find('select')[0])
                    });
                });
                //textarea暂时不要
                /*$('#calculatecon').find('textarea').each(function () {
                    $(this).parent().click(function () {
                        calculateSelect($(this).find('textarea'))
                    });
                });*/
            });
        });

        /**
         * 计算公式里面如果有括号再去掉其中一些元素可能会出错（还需要增加计算公式校验的功能）
         * @param obj
         */
        function calculateSelect(obj) {
            var c = $('#calculate').val();
            var obj1 = $(obj).parent();
            if (obj1.hasClass('tdEleSel')) {
                var c1 = c.split(obj.name);
                if (/[\+\-\*\/]/.test(c1[0].charAt(c1[0].length - 1)))
                    $('#calculate').val(c1[0].substring(0, c1[0].length - 1) + c1[1]);
                else
                    $('#calculate').val(c1[0] + c1[1].substring(1, c1[1].length));
                obj1.removeClass('tdEleSel');
            } else {
                if (c === undefined || c === '') $('#calculate').val(obj.name);
                else $('#calculate').val(c + '+' + obj.name);
                obj1.addClass('tdEleSel');
            }
        }
    });
});

/**
 * 初始化设计界面的各项功能内容
 */
function initDesignPage() {
    //预览工具条不可见，所有td和普通元素点击时间不可用
    if (preview === 'true' || preview === true) {
        $('.tool-bar').hide();
    }
    initlimit();
    initpage(data.dataadd, 'addform');
    initpage(data.tableadd, 'addtable');
    initpage(data.textcss, 'textcss');
    initpage(data.cssstyle, 'cssstyle');
    initpage(data.tableFormEle, 'tableFormEle');
    initdata();
    $('.tool-bar').find('button').click(function () {
        optalert($(this).attr('optype'), $(this).attr("ele_type"))
    });
}

/**
 * 不同的页面类型对应不同的操作处理
 * 3.4只是保存步骤不同，在保存按钮执行时间中处理
 */
function initlimit() {
    if (page_type == 1 || page_type == 4) {
        $("#formele").hide();
        if (page_type == 1)
            for (var i = 0; i < tabledata.rightMenu.length; i++) {
                if (tabledata.rightMenu[i].id !== 'tableFormEle') continue;
                tabledata.rightMenu.remove(i--);
            }
        else if (page_type == 4) {
            $('.pageele').hide();
            /*for(var i=0;i<data.rightMenu.length;i++){
                if(data.rightMenu[i].id!=='delform') continue;
                data.rightMenu.remove(i--);
            }*/
        }

    } else if (page_type == 2) {
        for (var i = 0; i < data.dataadd.length; i++) {
            if (data.dataadd[i].id !== "calculate") continue;
            data.dataadd.remove(i--);
        }
        for (var i = 0; i < data.tableFormEle.length; i++) {
            if (data.tableFormEle[i].id !== "calculate") continue;
            data.tableFormEle.remove(i--);
        }
    }
}

/**
 * 打开操作弹窗
 * @param type 弹窗div的id值，即是按钮的optype属性值
 * @param ele_type 更详细的optype分类
 */
function optalert(type, ele_type) {
    var title = '';
    var ele_id = $(selectedEle).attr('ele_id');
    //for (var i in data.rightMenu) {//从rightMenu常量中取出弹窗title
    for (var i=0;i<data.rightMenu.length;i++){//从rightMenu常量中取出弹窗title
        if (data.rightMenu[i].id === type) {
            if (ele_type === '1') title = '分类标题';
            else if (ele_type === '2') title = 'label';
            else if (ele_type === '9') title = '说明性文字';
            else title = data.rightMenu[i].text;
            break;
        }
    }
    menuFun[type](type, ele_id, title, ele_type);
}

/**
 * 根据不同的元素类型设置展示弹窗中的不同项及必填项
 * @param ele_type
 * @param type
 */
function showAlertEle(ele_type, type) {
    //3:'输入框', 4:'下拉框', 5:'单选框', 6:'多选框', 7:'文本域
    //label ele_id ele_type ele_conn ele_value limit_type limit_length limit_range limit_char occupy_col occupy_row
    var formobj = $('#' + type).find("form");
    for (var i = 0, len = data.dataadd.length; i < len; i++) {
        if (data.dataadd[i].hidden) continue;
        formobj.find('#div' + data.dataadd[i].id).show().attr('required', 'true');
    }
    var hiddenele = [];
    if(ele_type === undefined) hiddenele=['textarea'];
    if (ele_type === '1' || ele_type === '2' || ele_type === '9') {
        hiddenele = ['label', 'ele_id', 'ele_type', 'ele_conn', 'ele_value', 'limit_type', 'limit_length', 'limit_range', 'limit_char', 'occupy_col', 'occupy_row', 'calculate'];
    } else if (ele_type === '4' || ele_type === '5' || ele_type === '6') {
        hiddenele = ['textarea', 'limit_type', 'limit_length', 'limit_range', 'limit_char', 'occupy_col', 'occupy_row', 'show_seq', 'calculate'];
        formobj.find('#divele_conn').removeAttr('required');
    } else if (ele_type === '3') {
        hiddenele = ['textarea', 'ele_value', 'occupy_col', 'occupy_row'];
        formobj.find('#divele_conn').removeAttr('required');
        formobj.find('#divlimit_type').removeAttr('required');
        formobj.find('#divlimit_length').removeAttr('required');
        formobj.find('#divlimit_range').removeAttr('required');
        formobj.find('#divlimit_char').removeAttr('required');
    } else if (ele_type === '7') {
        hiddenele = ['textarea', 'ele_value', 'occupy_col', 'occupy_row', 'calculate'];
        formobj.find('#divele_conn').removeAttr('required');
        formobj.find('#divlimit_type').removeAttr('required');
        formobj.find('#divlimit_length').removeAttr('required');
        formobj.find('#divlimit_range').removeAttr('required');
        formobj.find('#divlimit_char').removeAttr('required');
        formobj.find('#divoccupy_col').removeAttr('required');
        formobj.find('#divoccupy_row').removeAttr('required');
    }
    formobj.find('#divcalculate').removeAttr("required");
    for (var i = 0, len = hiddenele.length; i < len; i++) {
        formobj.find('#div' + hiddenele[i]).hide().removeAttr('required');
    }
}

/**
 * 点击右键或菜单栏的弹出窗体
 * @param type
 * @param ele_id
 * @param title
 * @param ele_type
 */
function openalert(type, ele_id, title, ele_type) {
    var formobj = $('#' + type).find("form");
    clearform(type);//先将对应表单中的数据清空
    var url = '';
    if (selectedEle != null && selectedEle.outerHTML.search('</h1>') !== -1) {
        url = '/dpage/addpAddData';
    } else {
        //formobj.find('input[name=idele]').val(ele_id);
        url = '/dpage/addPageEle';
    }
    layer.open({
        title: title,
        type: 1,
        area: ['800px', '400px'],
        content: $('#' + type),
        btn: ['确定', '取消'],
        yes: function () {
            var value = formobj.serializeArray();
            if (!required(value, type, ele_type)) return;
            //如果是文字样式或css样式需要单独处理数据
            if (type === 'textcss' || type === 'cssstyle') value = clickOpenSure(value, type);
            //判断元素id是否重复
            for (var i = 0, len = value.length; i < len; i++) {
                //判断textarea是否显示，如果为显示状态需要将textarea中的值赋值到label中
                if (value[i].name === 'textarea'){
                    if($('#div'+value[i].name).css('display')!=='none')
                        for(var j = 0, len = value.length; j < len; j++){
                            if(value[j].name === 'label') value[j].value=value[i].value;
                        }
                }
                if (value[i].name === 'ele_id') {
                    /*if (value[i].value.length===0) {//判断是否没有输入id值
                        layer.alert("需要填入元素id值");
                        return false;
                    }*/
                    if (ids[value[i].value] === true) {//判断是否重复的id值
                        layer.alert("元素id重复，需重新输入");
                        return false;
                    }
                }
            }
            if (ele_type === '1' || ele_type === '2' || ele_type === '9')
                for (var i = 0, len = value.length; i < len; i++) {
                    if (value[i].name !== 'ele_type') {
                        continue;
                    }
                    value[i].value = ele_type;
                    break;
                }

            //提交保存数据，如果成功则刷新页面
            $.ajax({
                url: url,
                async: true,
                data: value,
                type: "POST",
                success: function (res) {
                    console.log(res);
                    if (res * 1 > 0) window.location.reload();
                    else layer.msg("保存失败");
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        btn2: function (index, layero) {
            ids[courrentId] = true;
        }
    });
}

/**
 * 验证必填项是否已填写
 * @param value
 * @param type
 * @param ele_type
 * @returns {boolean} true:验证通过
 */
function required(value, type, ele_type) {
    var formobj = $('#' + type).find("form");
    var res = true;
    if (type === 'addform')
        for (var i = 0, len = value.length; i < len; i++) {
            if (formobj.find('#div' + value[i].name).attr('required') === 'required') {
                if (value[i].value != null && value[i].value !== '') continue;
                for (var j = 0, le = data.dataadd.length; j < le; j++) {
                    if (data.dataadd[j].id === value[i].name) {
                        layer.msg(data.dataadd[j].text + "没有填写值");
                        break;
                    }
                }
                res = false;
                break;
            }
        }
    return res;
}

/**
 * 点击弹窗中的确认之后，如果是样式弹窗则需要对表单中的数据进行处理
 * @param value
 */
function clickOpenSure(value, type) {
    var res = [];
    var str = '';
    for (var i = 0, len = value.length; i < len; i++) {
        var item = value[i];
        if (item.name === 'idele' || item.name === 'idpage') {
            res[res.length] = {name: item.name, value: item.value};
        } else {
            if (item.value != null && item.value != '') str += item.name + ':' + item.value + ';';
        }
    }
    res[res.length] = {name: type, value: str};
    return res
}

/**
 * 清空表单元素(为什么idele元素的值没有被清除)
 * @param type 需要清空数据的表单id
 */
function clearform(type) {
    var value = $('#' + type).find("form").serializeArray();
    var res = {};
    for (var i = 0, len = value.length; i < len; i++) {
        if (value[i].name === 'idpage') continue;
        if (type === 'addtable' && value[i].name === 'ele_type') continue;
        res[value[i].name] = '';
    }
    form.val(type, res);
}

/**
 * 打开弹窗之前先回填数据（标题的样式没有回填）
 * @param type
 * @param ele_id
 */
function updDataShow(type, ele_id) {
    var ele_type = '';
    $.ajax({
        url: '/dpage/getPageEle', async: true, data: {idele: ele_id}, type: "POST",
        success: function (res) {
            ele_type = res[0].ele_type;
            var formobj = $('#' + type).find("form");
            if (type === 'addform') {
                form.val(type, res[0]);
                showAlertEle(ele_type, 'addform');
            } else
                form.val(type, strtojson(res[0][type]));
            formobj.find('input[name=idele]').val(res[0]['idele']);//当前eleid
            formobj.find('input[name=idpage]').val(res[0]['idpage']);//当前病历模板id
            formobj.find('textarea[name=textarea]').val(res[0]['label']);
            form.render(null, type);
            courrentId = res[0].ele_id;
            delete ids[courrentId]
        },
        error: function (res) {
            console.log(res)
        }
    });
    return ele_type;
}

/**
 * 初始化页面元素，几个下拉框的值
 * @param data
 * @param divid
 */
function initpage(data, divid) {
    var htmlstr = '<form style="padding:10px 10px 0px 0px;" action="javascript:return false;" class="layui-form" lay-filter="' + divid + '">';
    for (var i = 0, len = data.length; i < len; i++) {
        var item = data[i];
        htmlstr += '<div id="div' + item.id + '" class="layui-form-item' + (item.hidden ? ' hidden' : '') + '">';
        htmlstr += '<label class="layui-form-label">' + item.text + '</label>';
        if(item.type==='textarea')
            htmlstr += '<div class="layui-input-block">';
        else
            htmlstr += '<div class="layui-input-inline">';
        htmlstr += formele(item);
        htmlstr += '</div>';
        if(item.type!=='textarea')htmlstr += '<div class="layui-form-mid layui-word-aux">' + item.hint + '</div>';
        htmlstr += '</div>';
    }
    htmlstr += '</form>';
    $('#' + divid).append(htmlstr);
}

/**
 * 根据data中的数据组成页面对应的页面元素
 * @param data 页面数据对象
 * @returns {string} 组合完成的页面元素字符串
 */
function formele(data) {
    var htmlstr = '';
    var defval = data.defval;
    if (data.type === 'input') {
        htmlstr += '<input type="text" name="' + data.id /*+ '" id="' + data.id*/ + '" value="' + (defval ? defval : '') + '" lay-verify="required" class="layui-input">';
    } else if (data.type === 'select') {
        htmlstr += '<select name="' + data.id/* + '" id="' + data.id*/ + '"  lay-filter="' + data.id + '">';
        htmlstr += '<option value=""></option>';
        for (var i in data.value) {
            htmlstr += '<option value="' + i + '" ' + (defval === i ? 'selected' : '') + '>' + data.value[i] + '</option>';
        }
        htmlstr += '</select>';
    } else if (data.type === 'radio') {
        for (var i in data.value) {
            htmlstr += '<input type="radio" name="' + data.id /*+ '" id="' + data.id*/ + '" value="' + i + '" title="' + data.value[i] + '" ' + (defval === i ? 'checked' : '') + '>';
        }
    } else if (data.type === 'checkbox') {
        for (var i in data.value) {
            htmlstr += '<input type="checkbox" name="' + data.id /*+ '" id="' + data.id*/ + '" value="' + i + '" title="' + data.value[i] + '" ' + (defval === i ? 'checked' : '') + '>';
        }
    } else if (data.type === 'textarea') {
        htmlstr += '<textarea name="' + data.id /*+ '" id="' + data.id*/ + '" class="layui-textarea" value="' + (defval ? defval : '') + '"></textarea>';
    } else {

    }
    return htmlstr;
}

/**
 * 初始化数据项，病历设计中的数据项
 */
function initdata() {
    //阻止浏览器默认右键点击事件
    $("#optfile").bind("contextmenu", function () {
        return false;
    }).mousedown(function (e) {
        if (3 === e.which) {//右键为3
            $('#rightMenu').remove();
            srightMenu(e.clientX, e.clientY);
        } else if (1 === e.which) { //左键为1
            $('#rightMenu').remove();
        }
    });
    /**
     * 获取模板标题
     */
    $.ajax({
        url: '/dpage/getPageData',
        async: false,
        data: {
            idpage: getQueryString('idpage'),
            page_title: getQueryString('page_title'),
            page_type: getQueryString("page_type")
        },
        type: "POST",
        success: function (res) {
            $('#optfile').append('<h1 style="' + (res[0].textcss == null ? '' : res[0].textcss) + (res[0].cssstyle == null ? '' : res[0].cssstyle) + '" data_id="">' +
                res[0].page_title + '</h1>');//title样式不能修改么？？？

            if (preview === 'true' || preview === true) return;
            $('#optfile').children('h1').mousedown(function () {
                $(selectedEle).removeClass('selected');
                selectedEle = this;
                $(this).addClass('selected');
            });
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
            $('#optfile').find('h1').after(makePageEle(res));
            if (preview === 'true' || preview === true) return;
            //if(page_type!=4)//不用去除事件添加
            $('#optfile').find('form').children('div').mousedown(function (e) {
                $(selectedEle).removeClass('selected');
                selectedEle = this;
                $(this).addClass('selected');
            }).draggable({
                addClasses: false,
                revert: true
            }).droppable({
                drop: function (event, ui) {
                    var oldseq = $(selectedEle).attr('ele_seq');
                    var newseq = $(this).attr('ele_seq');
                    if (oldseq * 1 === newseq * 1) return;
                    $.ajax({
                        url: '/dpage/updseq',
                        async: false,
                        data: {
                            idpage: getQueryString('idpage'),
                            oldid: $(selectedEle).attr('ele_id'),
                            oldseq: oldseq,
                            newid: $(this).attr("ele_id"),
                            newseq: newseq
                        },
                        type: "POST",
                        success: function (res) {
                            window.location.reload();
                        }, error: function (res) {

                        }
                    });
                }
            });
            opttable();
        },
        error: function (res) {
            console.log(res);
        }
    });
}

/**
 * 打开右键菜单
 * @param x
 * @param y
 */
function srightMenu(x, y) {
    if (preview === 'true' || preview === true) return;
    var poition = 'left:' + x + 'px;top:' + y + 'px;';
    if (pageSize.height - y < 250) poition = 'left:' + x + 'px;bottom:' + (pageSize.height - y) + 'px;';
    var html = '<div id="rightMenu" style="position:fixed;' + poition +
        'width:150px;background-color:#fff;box-shadow: 0px 0px 10px #666;border-radius: 10px;padding: 10px;cursor:pointer">';
    //for (var i in data.rightMenu) {
    for (var i=0;i<data.rightMenu.length;i++){
        if (data.rightMenu[i].hidden) continue;//右键菜单中的隐藏项直接跳过
        if (selectedEle != null && selectedEle.outerHTML.search('</h1>') !== -1) {//点击选中元素是标题
            if (data.rightMenu[i].id === 'textcss' || data.rightMenu[i].id === 'cssstyle')
                html += '<div type_id="' + data.rightMenu[i].id + '">' + data.rightMenu[i].text + '</div>';
        } else if (selectedEle != null && selectedEle.outerHTML.search('<table') !== -1) {//点击选中的元素是一个表格
            if (page_type == 4) return;//如果也买你类型为复杂评估界面，不允许删除页面唯一table元素
            if (data.rightMenu[i].id === 'delform')
                html += '<div type_id="' + data.rightMenu[i].id + '">' + data.rightMenu[i].text + '</div>';
        } else {
            html += '<div type_id="' + data.rightMenu[i].id + '">' + data.rightMenu[i].text + '</div>';
        }
    }
    html += '</div>';
    $("body").append(html);
    $('#rightMenu').bind("contextmenu", function () {
        return false;
    }).children('div').each(function () {
        $(this).click(function () {
            $('#rightMenu').remove();
            optalert($(this).attr('type_id'));
        });
    });
}

/**
 * 将string字符串转成json对象,自符串中，将 ; 转成 ','    将 : 转成 ':'
 * @param str
 */
function strtojson(str) {
    if (str == null) return {};
    if (str.charAt(str.length - 1) === ';') str = str.substring(0, str.length - 1);
    return eval('({\'' + str.replace(/\s/g, '').replace(/;/ig, '\',\'').replace(/:/ig, '\':\'') + '\'})');
}