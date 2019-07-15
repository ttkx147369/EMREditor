$(function () {
    $.ajax({
        url: '/drecord/index',
        async: true,
        data: {},
        type: "POST",
        success: function (res) {
            var str = '';
            for (var i = 0, len = res.length; i < len; i++) {
                str += '<tr>';
                str += '<td>' + res[i].idpage + '</td><td>' + res[i].idrecord + '</td>';
                str += '<td>' + res[i].record_title + '</td><td>' + res[i].record_create + '</td>';
                str += '<td>' + res[i].record_update + '</td><td>' +
                    (res[i].record_state === '-1' ? '新建未填写' : res[i].record_state === '0' ? '填写只保存未提交' : '提交') + '</td>';
                str += '<td>' + res[i].recordcol + '</td><td>' + res[i].recordcol1 + '</td>';
                str += '<td>' + res[i].recordcol2 + '</td><td>' + res[i].recordcol3 + '</td>';
                str += '<td>' + res[i].recordcol4 + '</td><td>' + res[i].recordcol5 + '</td>';
                str += '<td>';
                str += '<button class="layui-btn layui-btn-xs layui-btn-warm" idpage="' + res[i].idpage + '" idrecord="' + res[i].idrecord + '">查看</button>';
                str += '<button' + (res[i].record_state !== '1' ? '' : ' style="display: none;"') + ' class="layui-btn layui-btn-xs" idpage="' + res[i].idpage + '" idrecord="' + res[i].idrecord + '">修改</button>';
                str += '<button class="layui-btn layui-btn-xs layui-btn-danger" idpage="' + res[i].idpage + '" idrecord="' + res[i].idrecord + '">删除</button>';
                str += '</tr>';
            }
            $('#datalist').append(str);
            $('#datalist').find('tr').each(function () {
                $(this).find('button').each(function (index, element) {
                    var idpage = $(this).attr('idpage');
                    var idrecord = $(this).attr("idrecord");
                    if (index === 0)
                        $(this).click(function () {
                            window.location.href = '/record/record?' + 'idpage=' + idpage + '&idrecord=' + idrecord;
                        });
                    else if (index === 1)
                        $(this).click(function () {
                            window.location.href = '/record/record?' + 'idpage=' + idpage + '&idrecord=' + idrecord + "&editor=true";
                        });
                    else {
                        $(this).click(function () {
                            $.ajax({
                                url: '/drecord/delrecord?' + 'idpage=' + idpage + '&idrecord=' + idrecord,
                                async: false,
                                data: {},
                                type: "POST",
                                success: function (res) {
                                    if (res > 0) window.location.reload();
                                },
                                error: function (res) {
                                    console.log(res);
                                }
                            });
                        });
                    }
                })
            });
        },
        error: function (res) {
            console.log(res);
        }
    });
});