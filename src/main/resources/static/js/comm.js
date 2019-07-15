//存储已经存在的元素id属性值
var ids = {};
var idEditor = window.location.href.indexOf("/ppage/todesign") != -1 ? true : false;
var calculate = {};//保存计算公式
/**
 * 获取url中的参数值
 * @param name
 * @returns {*}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

/**
 * 组合成页面元素
 * @param data
 * @returns {string}
 */
function makePageEle(data) {
    var html = '<form class="layui-form" action="">';
    for (var i = 0, len = data.length; i < len; i++) {
        var ele = data[i];
        if (ele.ele_id != null && ele.ele_id !== undefined && ele.ele_id !== '') ids[ele.ele_id] = true;
        html += func[ele.ele_type](ele);
    }
    html += '<form>';
    html += '<div style="clear: both;"></div>';
    return html;
}

//给Array.prototype添加remove方法
Array.prototype.remove = function (from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest);
};
//不同页面元素进行不同的组合
var func = {
    1: function (ele) {//1：分类标题
        var html = '<div style="clear:both;font-size:20px;' + (ele.textcss == null ? '' : ele.textcss) + (ele.cssstyle == null ? '' : ele.cssstyle) +
            '" ele_id="' + ele.idele + '" ele_seq="' + ele.show_seq + '">';
        html += ele.label
        html += '</div>';
        return html;
    },
    2: function (ele) {//2：label
        var html = '<div style="' + (ele.textcss == null ? '' : ele.textcss) + (ele.cssstyle == null ? '' : ele.cssstyle) +
            '" ele_id="' + ele.idele + '" ele_seq="' + ele.show_seq + '">';
        html += ele.label
        html += '</div>';
        return html;
    },
    3: function (ele) {//3：输入框
        var html = '<div style="float:left;' + (ele.textcss == null ? '' : ele.textcss) + (ele.cssstyle == null ? '' : ele.cssstyle) +
            '" ele_id="' + ele.idele + '" ele_seq="' + ele.show_seq + '">';
        html += '<div class="layui-form-item">';
        html += '<label class="layui-form-label">' + ele.label + '</label>';
        html += '<div class="layui-input-inline">';
        if (ele.calculate) {
            html += '<input type="text" id="' + ele.ele_id + '" name="' + ele.ele_id + '" placeholder="" class="layui-input" calculate="true">';
            calculate[ele.ele_id] = ele.calculate;
        } else
            html += '<input type="text" id="' + ele.ele_id + '" name="' + ele.ele_id + '" placeholder="" class="layui-input">';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        return html;
    },
    4: function (ele) {//4：下拉框
        var html = '<div style="float:left;' + (ele.textcss == null ? '' : ele.textcss) + (ele.cssstyle == null ? '' : ele.cssstyle) +
            '" ele_id="' + ele.idele + '" ele_seq="' + ele.show_seq + '">';
        html += '<div class="layui-form-item">';
        html += '<label class="layui-form-label">' + ele.label + '</label>';
        html += '<div class="layui-input-block">';
        html += '<select name="' + ele.ele_id + '">';
        html += '<option value=""></option>';
        var elechild = eval('(' + ele.ele_value + ')');
        var ele_conn = ele.ele_conn;
        if (ele_conn == null || ele_conn === '')
            for (var i in elechild) {
                html += '<option value="' + i + '">' + elechild[i] + '</option>';
            }
        else {
            if (typeof (ele_conn) === 'string') ele_conn = eval('(' + ele_conn + ')')
            for (var i in ele_conn) {
                for (var j in elechild) {
                    html += '<option value="' + ele_conn[i][j] + '">' + ele_conn[i][elechild[j]] + '</option>';
                }
            }
        }
        html += '</select>';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        return html;
    },
    5: function (ele) {//5：单选框
        var html = '<div style="' + (ele.textcss == null ? '' : ele.textcss) + (ele.cssstyle == null ? '' : ele.cssstyle) +
            '" ele_id="' + ele.idele + '" ele_seq="' + ele.show_seq + '">';
        html += '<div class="layui-form-item">';
        html += '<label class="layui-form-label">' + ele.label + '</label>';
        html += '<div class="layui-input-block">';
        var elechild = eval('(' + ele.ele_value + ')');
        var ele_conn = ele.ele_conn;
        if (ele_conn == null || ele_conn === '')
            for (var i in elechild) {
                html += '<input type="radio" name="' + ele.ele_id + '" value="' + i + '" title="' + elechild[i] + '">';
            }
        else {
            if (typeof (ele_conn) === 'string') ele_conn = eval('(' + ele_conn + ')')
            for (var i in ele_conn) {
                for (var j in elechild) {
                    html += '<input type="radio" name="' + ele.ele_id + '" value="' + ele_conn[i][j] + '" title="' + ele_conn[i][elechild[j]] + '">';
                }
            }
        }
        html += '</div>';
        html += '</div>';
        html += '</div>';
        return html;
    },
    6: function (ele) {//6：多选框
        var html = '<div style="' + (ele.textcss == null ? '' : ele.textcss) + (ele.cssstyle == null ? '' : ele.cssstyle) +
            '" ele_id="' + ele.idele + '" ele_seq="' + ele.show_seq + '">';
        html += '<div class="layui-form-item">';
        html += '<label class="layui-form-label">' + ele.label + '</label>';
        html += '<div class="layui-input-block">';
        var elechild = eval('(' + ele.ele_value + ')');
        var ele_conn = ele.ele_conn;
        if (ele_conn == null || ele_conn === '')
            for (var i in elechild) {
                html += '<input type="checkbox" name="' + ele.ele_id + '[' + i + ']" value="' + i + '" title="' + elechild[i] + '">';
            }
        else {
            if (typeof (ele_conn) === 'string') ele_conn = eval('(' + ele_conn + ')')
            for (var i in ele_conn) {
                for (var j in elechild) {
                    html += '<input type="checkbox" name="' + ele.ele_id + '[' + ele_conn[i][j] + ']" value="' + ele_conn[i][j] + '" title="' + ele_conn[i][elechild[j]] + '">';
                }
            }
        }
        html += '</div>';
        html += '</div>';
        html += '</div>';
        return html;
    },
    7: function (ele) {//7：文本域
        var html = '<div style="' + (ele.textcss == null ? '' : ele.textcss) + (ele.cssstyle == null ? '' : ele.cssstyle) +
            '" ele_id="' + ele.idele + '" ele_seq="' + ele.show_seq + '">';
        html += '<div class="layui-form-item layui-form-text">';
        html += '<label class="layui-form-label">' + ele.label + '</label>';
        html += '<div class="layui-input-block">';
        html += '<textarea name="' + ele.ele_id + '" placeholder="" class="layui-textarea"></textarea>';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        return html;
    },
    8: function (ele) {//8：文件上传
        return '<div style="' + (ele.textcss == null ? '' : ele.textcss) + (ele.cssstyle == null ? '' : ele.cssstyle) +
            '" ele_id="' + ele.idele + '" ele_seq="' + ele.show_seq + '">' + ele.label + '</div>';
    },
    9: function (ele) {//9：说明性文字
        return '<div style="color: #999;' + (ele.textcss == null ? '' : ele.textcss) + (ele.cssstyle == null ? '' : ele.cssstyle) +
            '" ele_id="' + ele.idele + '" ele_seq="' + ele.show_seq + '">' + ele.label + '</div>';
    },
    10: function (ele) {
        var row = 0;
        var html = '<div style="clear:both;" ele_id="' + ele.idele + '" ele_seq="' + ele.show_seq + '">';
        html += '<table class="layui-table" lay-size="sm" border="' + ele.textcss + '" summary="' + ele.cssstyle + '"';
        html += '<caption>' + (ele.label == null || ele.label === 'null' ? '&nbsp;' : ele.label) + '<?caption><tbody>' +
            '<tr' + ' condition="' + (ele.page_tables[0].condition || ele.page_tables[0].condition === 'null' ? ele.page_tables[0].condition : '') + '">';
        for (var i = 0, len = ele.page_tables.length; i < len; i++) {
            var col = ele.page_tables[i];
            if (i !== 0 && col.tdrow !== row) {
                html += '</tr><tr' + ' condition="' + (col.condition || col.condition === 'null' ? col.condition : '') + '">';
                row = col.tdrow;
            }
            //console.log('<tr' + ' condition="' +(col.condition||col.condition==='null'?col.condition:'') +'">');
            if (col.ele_id !== '' && col.ele_id !== undefined && col.ele_id != null) ids[col.ele_id] = true;
            html += '<td colspan="' + col.colspan + '" rowspan="' + col.rowspan + '" formid="' + (col.formid || col.formid === 'null' ? col.formid : '') +
                '" tdrow="' + col.tdrow + '" tdcol="' + col.tdcol + '" idtable="' + col.idtable + '" getvalue="' + (col.getvalue || col.getvalue === 'null' ? col.getvalue : '') +
                '" style="' + (col.tdshow === 'off' ? "display:none;" : "") + (col.textcss == null ? '' : col.textcss) + (col.cssstyle == null ? '' : col.cssstyle) + '">';
            //表格内的输入元素
            var tdEle = col.tableEle, e = '';
            for (var j = 0, le = tdEle.length; j < le; j++) {
                ids[tdEle[j].ele_id] = true;
                e += funcTable[tdEle[j].ele_type](tdEle[j]);
            }
            if (e === '') html += col.showcontent.trim() + '</td>';
            else if (col.showcontent.trim() === '&nbsp;') html += e + '</td>';
            else html += col.showcontent.trim() + e + '</td>';
        }
        html += '</tr></tbody></table></div>';
        return html;
    }
};
//表格中的表单元素
var funcTable = {
    3: function (ele) {//3：输入框
        var html = '<span ele_id="' + ele.idpage_table_ele + '">';
        if (ele.calculate) {
            html += '<input type="text" id="' + ele.ele_id + '" name="' + ele.ele_id + '" placeholder="" class="layui-input" calculate="true">';
            calculate[ele.ele_id] = ele.calculate;
        } else
            html += '<input type="text" id="' + ele.ele_id + '" name="' + ele.ele_id + '" placeholder="" class="layui-input">';
        if (idEditor) html += '<span style="visibility:hidden;"><i class="layui-icon">&#x1006;</i><i class="layui-icon">&#xe642;</i></span>';
        html += '</span>';
        return html;
    },
    4: function (ele) {//4：下拉框
        var html = '<span ele_id="' + ele.idpage_table_ele + '">';
        html += '<select name="' + ele.ele_id + '">';
        html += '<option value="">请选择</option>';
        var elechild = eval('(' + ele.ele_value + ')');
        var ele_conn = ele.ele_conn;
        if (ele_conn == null || ele_conn === '')
            for (var i in elechild) {
                html += '<option value="' + i + '">' + elechild[i] + '</option>';
            }
        else {
            if (typeof (ele_conn) === 'string') ele_conn = eval('(' + ele_conn + ')')
            for (var i in ele_conn) {
                for (var j in elechild) {
                    html += '<option value="' + ele_conn[i][j] + '">' + ele_conn[i][elechild[j]] + '</option>';
                }
            }
        }
        html += '</select>';
        if (idEditor) html += '<span style="visibility:hidden;"><i class="layui-icon">&#x1006;</i><i class="layui-icon">&#xe642;</i></span>';
        html += '</span>';
        return html;
    },
    5: function (ele) {//5：单选框
        var html = '<span ele_id="' + ele.idpage_table_ele + '">';
        var elechild = eval('(' + ele.ele_value + ')');
        var ele_conn = ele.ele_conn;
        if (ele_conn == null || ele_conn === '')
            for (var i in elechild) {
                //html += '<input type="radio" name="' + ele.ele_id + '" value="' + i + '">' + elechild[i];
                html += '<input type="radio" name="' + ele.ele_id + '" value="' + i + '" title="' + elechild[i] + '">';
            }
        else {
            if (typeof (ele_conn) === 'string') ele_conn = eval('(' + ele_conn + ')')
            for (var i in ele_conn) {
                for (var j in elechild) {
                    //html += '<input type="radio" name="' + ele.ele_id + '" value="' + ele_conn[i][j] + '">' + ele_conn[i][elechild[j]];
                    html += '<input type="radio" name="' + ele.ele_id + '" value="' + ele_conn[i][j] + '" title="' + ele_conn[i][elechild[j]] + '">';
                }
            }
        }
        if (idEditor) html += '<span style="visibility:hidden;"><i class="layui-icon">&#x1006;</i><i class="layui-icon">&#xe642;</i></span>';
        html += '</span>';
        return html;
    },
    6: function (ele) {//6：多选框
        var html = '<span ele_id="' + ele.idpage_table_ele + '">';
        var elechild = eval('(' + ele.ele_value + ')');
        var ele_conn = ele.ele_conn;
        if (ele_conn == null || ele_conn === '')
            for (var i in elechild) {
                //html += '<input type="checkbox" name="' + ele.ele_id + '" value="' + i + '">' + elechild[i];
                html += '<input type="checkbox" name="' + ele.ele_id + '[' + i + ']" value="' + i + '" title="' + elechild[i] + '" lay-skin="primary">';
            }
        else {
            if (typeof (ele_conn) === 'string') ele_conn = eval('(' + ele_conn + ')')
            for (var i in ele_conn) {
                for (var j in elechild) {
                    //html += '<input type="checkbox" name="' + ele.ele_id + '" value="' + ele_conn[i][j] + '">' + ele_conn[i][elechild[j]];
                    html += '<input type="checkbox" name="' + ele.ele_id + '[' + ele_conn[i][j] + ']" value="' + ele_conn[i][j] + '" title="' + ele_conn[i][elechild[j]] + '" lay-skin="primary">';
                }
            }
        }
        if (idEditor) html += '<span style="visibility:hidden;"><i class="layui-icon">&#x1006;</i><i class="layui-icon">&#xe642;</i></span>';
        html += '</span>';
        return html;
    },
    7: function (ele) {//文本域
        var html = '<span ele_id="' + ele.idpage_table_ele + '">';
        html += '<textarea class="layui-textarea" rows="2" name="' + ele.ele_id + '" placeholder=""></textarea>';
        if (idEditor) html += '<span style="visibility:hidden;"><i class="layui-icon">&#x1006;</i><i class="layui-icon">&#xe642;</i></span>';
        html += '</span>';
        return html;
    },
    null: function () {
        return '';
    }
}