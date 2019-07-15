/**
 * 初始化表格内容，右键菜单等内容
 */
function opttable() {
    //给每一个单元格添加对应的事件
    $('#optfile').find('form').find('td').mousedown(function (e) {//鼠标按键按下
        $("#rightMenu").remove();
        if (3 === e.which) {//右键为3
            if (tabledata.downElement == null) {
                layer.msg("请先左键选择元素");
                return false;
            }
            tdRightMenu(e);
        } else if (1 === e.which) { //左键为1
            tabledata.merge = true;//回复默认值需要放在鼠标点击的地方
            tabledata.break = true;//回复默认值需要放在鼠标点击的地方
            //清空之前选中的元素
            if (tabledata.downElement != null && tabledata.upElement != null) {
                var start = tabledata.downElement;
                var end = tabledata.upElement;
                var s = {
                    row: start.row > end.row ? end.row : start.row,
                    col: start.col > end.col ? end.col : start.col
                };
                var e = {
                    row: start.row < end.row ? end.row : start.row,
                    col: start.col < end.col ? end.col : start.col
                };
                for (var i = s.row; i <= e.row; i++)
                    for (var j = s.col; j <= e.col; j++)
                        tabledata.tableobj.find('td[tdrow=' + i + '][tdcol=' + j + ']').removeClass("opttd");
            }
            //选中元素操作
            tabledata.tableobj = $(this).parent().parent();//保存当前操作表格对象的tbody元素
            tabledata.downElement = option(this);//记录鼠标按下的位置
            tabledata.opt = true;
            if (!$(this).hasClass("opttd"))
                $(this).addClass('opttd');
            //增加选中表格元素
            $(selectedEle).removeClass('selected');
            selectedEle = $(this).parent().parent().parent().parent()[0];
            $(selectedEle).addClass('selected');
        }
        return false;
    }).mouseover(function () {
        if (tabledata.opt) {
            var opt = option(this);
            if ($(this).hasClass("opttd")) {//有些选中项不能去掉
                if (tabledata.previousElement.row !== opt.row) setRemoveTd().col();
                if (tabledata.previousElement.col !== opt.col) setRemoveTd().row();
            } else {
                setSelectTd().col(opt);
                setSelectTd().row(opt);
            }
        }
        tabledata.previousElement = option(this);
    }).mouseup(function (e) {
        if (1 === e.which) {
            tabledata.upElement = option(this);//记录鼠标松开的位置
            tabledata.previousElement = null;//鼠标松开时需要将上一个表格对象清空
            tabledata.opt = false;
            //判断是否可合并或拆分
            if (tabledata.downElement.col === tabledata.upElement.col && tabledata.downElement.row === tabledata.upElement.row) {//只选择了一个单元格
                tabledata.merge = false;
                if (tabledata.downElement.rowspan !== 1 || tabledata.downElement.colspan !== 1)//非合并单元格
                    tabledata.break = true;
                else//合并单元格
                    tabledata.break = false;
            } else//选了多个单元格
                tabledata.break = false;
        }
    }).children('span').each(function () {
        var THIS = this;
        $(this).children("span").children('i').each(function (index, ele) {
            $(this).click(function () {
                if (index === 0) {
                    var courrentId = $(THIS).children(":first").attr("name").split('[')[0];
                    $.ajax({
                        url: '/dpage/delTableTdEle',//对同一个表的不同字段修改，可以直接就用一个接口
                        async: true,
                        data: {
                            idpage_table_ele: $(THIS).attr("ele_id"),

                            formid: tabledata.downElement.formid.replace(eval('/,' + courrentId + '/ig'), '')
                                .replace(eval('/' + courrentId + ',/ig'), '').replace(eval('/' + courrentId + '/ig'), ''),
                            page_table_id: tabledata.downElement.idtable,
                            rowspan: tabledata.downElement.rowspan,
                            colspan: tabledata.downElement.colspan,
                            tdrow: tabledata.downElement.row,
                            tdcol: tabledata.downElement.col,
                            idele: $(selectedEle).attr('ele_id')
                        },
                        type: "POST",
                        success: function (res) {
                            console.log(res);
                            if (res * 1 > 0) {
                                window.location.reload();
                            } else layer.msg("保存失败");
                        },
                        error: function (res) {
                            console.log(res)
                        }
                    })
                } else {
                    //修改
                }
            });
        });
    }).mouseover(function (e) {
        $(this).addClass("tdEleSel").children("span").css('visibility', 'visible');
    }).mouseout(function () {
        $(this).removeClass("tdEleSel").children("span").css('visibility', 'hidden');
    });
}

/**
 * 表格td中点击右键或菜单栏的弹出窗体
 * @param type 弹窗类型
 * @param func 点击弹窗中确定按钮的执行函数，包含一个参数，用户填写的form表单元素及值的数组对象
 */
function tableopenalert(type, func) {
    var formobj = $('#' + type).find("form");
    clearform(type);//先将对应表单中的数据清空
    var tableTitle = '';
    for (var i = 0, len = tabledata.rightMenu.length; i < len; i++) {
        if (tabledata.rightMenu[i].id !== type) continue;
        tableTitle = '单元格' + tabledata.rightMenu[i].text;
        break;
    }
    layer.open({
        title: tableTitle,
        type: 1,
        area: ['800px', '430px'],
        content: $('#' + type),
        btn: ['确定', '取消'],
        yes: function () {
            var value = formobj.serializeArray();
            if (func) {
                func(formobj.serializeArray());
                return true;
            }
            if (type === 'addform')
                if (!required(value, type, ele_type)) return;
            //如果是文字样式或css样式需要单独处理数据
            if (type === 'textcss' || type === 'cssstyle')//文本样式和css样式
                value = clickOpenSure(value, type);
            else {//添加的内容

            }
            //idele showcontent textcss cssstyle calculate
            value[value.length] = {name: 'idtable', value: tabledata.downElement.idtable};
            //提交保存数据，如果成功则刷新页面
            $.ajax({
                url: '/dpage/updPageTable',//对同一个表的不同字段修改，可以直接就用一个接口
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
 * 设鼠标移动时的选中项
 * @param opt
 */
function setSelectTd() {
    return {
        row: function (opt) {
            var i = opt.row > tabledata.downElement.row ? tabledata.downElement.row : opt.row;
            var len = opt.row > tabledata.downElement.row ? opt.row : tabledata.downElement.row;
            for (; i <= len; i++) {
                tabledata.tableobj.find('td[tdrow=' + i + '][tdcol=' + opt.col + ']').addClass("opttd");
            }
            if (opt.colspan !== 1 || opt.rowspan !== 1) tabledata.merge = false;
        },
        col: function (opt) {
            var j = opt.col > tabledata.downElement.col ? tabledata.downElement.col : opt.col;
            var le = opt.col > tabledata.downElement.col ? opt.col : tabledata.downElement.col;
            for (; j <= le; j++) {
                tabledata.tableobj.find('td[tdrow=' + opt.row + '][tdcol=' + j + ']').addClass("opttd");
            }
            if (opt.colspan !== 1 || opt.rowspan !== 1) tabledata.merge = false;
        }
    };
}

/**
 * 设鼠标移动时移除选中项
 * @param opt
 */
function setRemoveTd() {
    return {
        row: function () {
            var i = tabledata.previousElement.row > tabledata.downElement.row ? tabledata.downElement.row : tabledata.previousElement.row;
            var len = tabledata.previousElement.row > tabledata.downElement.row ? tabledata.previousElement.row : tabledata.downElement.row;
            for (; i <= len; i++) {
                tabledata.tableobj.find('td[tdrow=' + i + '][tdcol=' + tabledata.previousElement.col + ']').removeClass("opttd");
            }
        },
        col: function () {
            var j = tabledata.previousElement.col > tabledata.downElement.col ? tabledata.downElement.col : tabledata.previousElement.col;
            var le = tabledata.previousElement.col > tabledata.downElement.col ? tabledata.previousElement.col : tabledata.downElement.col;
            for (; j <= le; j++) {
                tabledata.tableobj.find('td[tdrow=' + tabledata.previousElement.row + '][tdcol=' + j + ']').removeClass("opttd");
            }
        }
    };
}

/**
 * 位置记录
 * @param obj
 */
function option(obj) {
    var formid = $(obj).attr("formid");
    if (!formid) {
        formid = '';
        $(obj).children().each(function (index, ele) {
            formid += (index === 0 ? '' : ',') + $(ele).children().first().attr('name').split('[')[0];
        });
    }
    return {
        row: $(obj).attr("tdrow") * 1,
        col: $(obj).attr("tdcol") * 1,
        idtable: $(obj).attr("idtable"),
        colspan: $(obj).attr("colspan") * 1,
        rowspan: $(obj).attr("rowspan") * 1,
        formid: formid,
        text: $(obj).contents().filter(function () {
            return this.nodeType === 3;
        }).text()
    };
}

/**
 * 右键菜单操作
 * @param e
 */
function tdRightMenu(e) {
    var poition = 'left:' + e.clientX + 'px;top:' + e.clientY + 'px;';
    if (pageSize.height - e.clientY < 250) poition = 'left:' + e.clientX + 'px;bottom:' + (pageSize.height - e.clientY) + 'px;';
    var html = '<div id="rightMenu" style="position:fixed;' + poition +
        'width:150px;background-color:#fff;box-shadow: 0px 0px 10px #666;border-radius: 10px;padding: 10px;cursor:pointer">';
    for (var i = 0, len = tabledata.rightMenu.length; i < len; i++) {
        if(tabledata.rightMenu[i].id==='merge'||tabledata.rightMenu[i].id==='split')
            console.log(tabledata.rightMenu[i].id);
        if(tabledata.rightMenu[i].id==='merge'&&!tabledata.merge)
            continue;
        if(tabledata.rightMenu[i].id==='split'&&!tabledata.break)
            continue;
        html += '<div type_id="' + tabledata.rightMenu[i].id + '">' + tabledata.rightMenu[i].text + '</div>';
    }
    html += '</div>';
    $("body").append(html);
    $('#rightMenu').bind("contextmenu", function () {
        return false;
    }).children('div').each(function () {
        $(this).click(function () {
            $('#rightMenu').remove();
            for (var i in tabledata.rightMenu) {
                if (tabledata.rightMenu[i].id !== $(this).attr('type_id')) continue;
                tabledata.rightMenu[i].func();
                break;
            }
        });
    });
}

/**
 * 打开单元格样式弹窗是回填数据
 */
function updTableStyle(type) {
    var formobj = $('#' + type).find("form");
    $.ajax({
        url: '/dpage/getTableCol',
        async: true,
        data: {idtable: tabledata.downElement.idtable},
        type: "POST",
        success: function (res) {
            form.val(type, strtojson(res[0][type]));
            formobj.find('input[name=idtable]').val(res[0]['idtable']);
            formobj.find('input[name=idele]').val(res[0]['idele']);
        },
        error: function (res) {
            console.log(res)
        }
    });

}