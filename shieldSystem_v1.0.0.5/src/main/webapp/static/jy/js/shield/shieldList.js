var basePath = $("#basePath").val();
$(function () {
    loadData(1);
});

//加载table list数据
function loadData(pageNums) {
    if (isNaN(pageNums)) {
        JRYPopup.Jalert("请输入正确的页码");
        return;
    }
    if (pageNums < 1) {
        JRYPopup.Jalert("页码应从1开始");
        return;
    }
    var isUpperOrg = $("#isUpperOrg").val();
    $("#pageNum1").val(pageNums);
    var url = "device/queryShieldDevices";
    var ip = $("#ipSearch").val();
    var mac = $("#macSearch").val();
    var status = $("#statusSelect").val();
    $.ajax({
        cache: true,
        type: "POST",
        url: url,
        dataType: "json",
        data: {pageNum: pageNums,pageSize:10, type: 2, ip: ip, mac: mac, status: status},
        success: function (data) {

            var html = "";
            //isUpperOrg==true 为上级,显示数据的组织机构
            isUpperOrg = false;
            if (isUpperOrg) {
                html += "<tr><th><input id='delAll' type='checkbox' onclick='toggleSelectAll(this)'/></th>"
                    + "<th>序号</th><th>组织机构</th><th>类型</th><th>IP地址</th><th>MAC地址</th><th>逻辑考场</th><th>物理考场</th>"
                    + "<th>状态</th><th>开启模块</th><th>操作</th></tr>";
            } else {
                html += "<tr><th><input id='delAll' type='checkbox' onclick='toggleSelectAll(this)'/></th>"
                    + "<th>序号</th><th>类型</th><th>IP地址</th><th>MAC地址</th><th>逻辑考场</th><th>物理考场</th>"
                    + "<th>状态</th><th>开启模块</th><th>操作</th></tr>";
            }


            if (data.result.total == 0) {
                html += "<tr><td colspan='10' class='text-danger text-center'>没有相关数据</td></tr>";
            } else {
                var result = data.result.list;
                for (var i = 0; i < result.length; i++) {
                    var index = i + 1;
                    var type = "未知";
                    var status = "未知";
                    var controlled = "未知";
                    var operationType = "未知";

                    if (result[i].type == 1) {
                        type = "侦测";
                    } else if (result[i].type == 2) {
                        type = "屏蔽";
                    }

                    if (result[i].status == 1) {
                        status = "在线";
                    }
                    if (result[i].status == 2) {
                        status = "离线";
                    }
                    if (result[i].address == undefined) {
                        status = "未安装";
                    }

                    if (result[i].controlled == 1) {
                        controlled = "自动";
                    } else if (result[i].controlled == 2) {
                        controlled = "手动";
                    }

                    var opType = result[i].operationType_
                    if (opType != null) {
                        if (opType == "0") {
                            operationType = "未开启"
                        } else if (opType == "6") {
                            operationType = "全部开启"
                        } else {
                            operationType = opType.replace("1", "专业作弊").replace("2", "电话").replace("3", "上网")
                        }
                    }

                    html += "<tr><td>";
                    if (result[i].type != 1) {//这侧设备不允许删除
                        html += "<input name='ids' type='checkbox' value='" + result[i].id + " ' />"
                    }
                    html += "</td><td>" + index + "</td>";
                    if (isUpperOrg) {
                        html += "<td>" + result[i].organization.name + "</td>";
                    }
                    html += "<td >" + type + "</td>"
                        + "<td >" + result[i].ip + "</td>"
                        + "<td >" + (result[i].mac==undefined?"--":result[i].mac) + "</td>";
                    if (result[i].address == null) {
                        html += "<td></td>";
                    } else {
                        html += "<td >" + result[i].address + "</td>";
                    }

                    html += "<td >" + (result[i].remark == undefined?"":result[i].remark) + "</td>"
                        + "<td >" + status + "</td>";

                    if (result[i].type == 1) {
                        html += "<td >" + '---' + "</td>";
                    } else if (result[i].type == 2) {
                        html += "<td >" + operationType + "</td>";
                    }

                    
                    html += "<td >"
                   	 	+"<img title='编辑' onclick='goEdit(this)' class='tdImg' name='"
                        +result[i].id+"' src='"+basePath+"/static/jy/img/pcImg/edit.png'>"
                        + "<img title='删除' onclick='del(this)' class='tdImg' name='"
                        + result[i].id + "' src='" + basePath + "/static/jy/img/pcImg/deleteT.png'>"

                    html += "</td></tr>";

                }
            }
            var tbody = $("#deviceList");
            tbody.html(html);

            $("#pageNum").val(data.result.pageNum);
            $("#pages").val(data.result.pages);

            var pageTotal = $("#pageCss").find(".pageTotal").val();
            JRYPage.pageInit('pageCss', pageNums, pageTotal, 11, changePage);
        },
        error: function (msg) {
            JRYPopup.Jalert("数据加载失败!");
        }
    })

}


//上一页
function proPage() {
    var pageNow = $("#pageCss").find(".pageNow").val();
    var pageNums = parseInt(pageNow) - 1;
    if (pageNums < 1) {
        return;
    } else {
        loadData(pageNums);
    }
}

//下一页
function nextPage() {
    var pageNow = $("#pageCss").find(".pageNow").val();
    var pageTotal = $("#pageCss").find(".pageTotal").val();
    var pageNums = parseInt(pageNow) + 1;
    if (pageNums > pageTotal) {
        return;
    } else {
        loadData(pageNums);
    }
}

//分页
function changePage(obj, pages) {
    if (pages == "") {
        JRYPopup.Jalert("请输入页码！");
    } else {
        var pageNums = 1;
        if (pages) {
            pageNums = pages;
        } else {
            pageNums = $(obj).text();
        }
        var pageTotal = $("#pageCss").find(".pageTotal").val();
        if (pageNums != '...') {
            pageNums = parseInt(pageNums);
            pageTotal = parseInt(pageTotal);
            if (pageNums > pageTotal) {
                JRYPopup.Jalert("输入的页码超出最大范围");
                return;
            }
            loadData(pageNums);
        }
    }
}

//全选和全不选
function toggleSelectAll(e) {
    if (e.checked) {
        $("#table tbody").find("input[type='checkbox']").prop("checked", true);
    } else {
        $("#table tbody").find("input[type='checkbox']").prop("checked", false);
    }
}

//批量删除
function delAll() {
    if ($("input[name='ids']:checked").length <= 0) {
        JRYPopup.Jalert("请选择删除项");
        return;
    }
    JRYPopup.Jconfim('确定要删除已选择的数据吗？', ajaxDel, "");

}

function ajaxDel() {
    $.ajax({
        type: "POST",
        url: "device/deleteDevices.do",
        data: $('#tbForm').serialize(),
        success: function (data) {
            if (data.code == '10000') {
                loadData(1);
                $("#delAll").prop('checked', false);
                JRYPopup.popupClose();
                JRYPopup.Jalert("删除成功！")
            } else {
                JRYPopup.Jalert(data.message)
            }
        },
        error: function () {
            JRYPopup.Jalert("删除失败！");
        }
    })
}

//弹出编辑窗口
function goEdit(e) {
    JRYPopup.Jcustom("editDiv");
    //clearEditForm();
    var id = e.name;
    $.get("device/queryDeviceById.do?id=" + id, function (data) {
        var device = data.result;
        $("#id").val(id);
        $("#ShieldIp").val(device.ip);
		$("#ShieldMask").val(device.mask);
		$("#ShieldGateway").val(device.gateway);
		$("#ShieldMac").val(device.mac);
		$("#controlled").val(device.controlled);
        $("#remark").val(device.remark);
        $("#address").val(device.address);
    });
}
//弹出新增设备窗口
function openAdd(){
	JRYPopup.Jcustom("editDiv");
	$("#bootTitle").html("新增设备");
	$("#ShieldIp").removeAttr("disabled");
	$("#ShieldMask").removeAttr("disabled");
	$("#ShieldGateway").removeAttr("disabled");
	$("#ShieldMac").removeAttr("disabled");
	
	$("#ShieldIp").attr("name","ip");
	$("#ShieldMask").attr("name","mask");
	$("#ShieldGateway").attr("name","gateway");
	$("#ShieldMac").attr("name","mac");
	
	$("#add_btn").attr("onclick","doAdd()");
	
}

function doAdd(){
	console.log($('#editForm'))
	$('#editForm').find(".error_text").html("");
	//验证地址
	var reg = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
	var ip = $("#ShieldIp").val();
	if(!reg.test(ip)){
		$("#ShieldIp").parent().find(".error_text").html("ip格式错误");
		return;
	}
	
	var mask = $("#ShieldMask").val();
	if(!reg.test(mask)){
		$("#ShieldMask").parent().find(".error_text").html("掩码格式错误");
		return;
	}
	
	var gateway = $("#ShieldGateway").val();
	if(!reg.test(gateway)){
		$("#ShieldGateway").parent().find(".error_text").html("网关格式错误");
		return;
	}
	$.ajax({
		url: "device/saveDevice",
        type: "POST",
        data:$('#editForm').serialize(),
        success: function (res) {
            if (res.code == '10000') {
            	CancelPop();
                loadData(1);
                JRYPopup.Jalert("新增成功！")
            } else {
                JRYPopup.Jalert(res.message)
            }
        },
        error: function () {
            JRYPopup.Jalert("网络错误");
        }
    })
}

//修改
function edit() {
    $.ajax({
        type: "POST",
        url: "device/updateDevice",
        data: $('#editForm').serialize(),
        success: function (data) {
            if (data.code == '10000') {
                CancelPop();
                loadData(1);
                JRYPopup.Jalert("修改成功！")
            } else {
                JRYPopup.Jalert(data.message)
            }
        },
        error: function () {
            JRYPopup.Jalert("网络错误");
        }
    })
}


function CancelPop(){
	JRYPopup.popupClose();
}

// 刷新设备
function refreshDevice(){
	JRYPopup.Jloading('正在刷新...');
	$.ajax({
        type: "POST",
        url: "device/refreshDevice",
        data: '',
        success: function (data) {
        	CancelPop();
            if (data.code == '10000') {
                loadData(1);
                JRYPopup.Jalert("刷新成功");
            } else {
                JRYPopup.Jalert(data.message)
            }
        },
        error: function () {
        	CancelPop();
            JRYPopup.Jalert("网络错误");
        }
    })
}


//删除设备
function del(e) {
    var id = e.name;
    JRYPopup.Jconfim('确定要删除已选择的数据吗？', deleteByConfirm, id);
}

function deleteByConfirm(id) {
    $.post("device/deleteDevice.do?id=" + id, function (data) {
        loadData(1);
        JRYPopup.Jalert("删除成功！")
    });
}