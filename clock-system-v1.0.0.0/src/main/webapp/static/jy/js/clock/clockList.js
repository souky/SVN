var basePath = $("#basePath").val();
$(function() {
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
	//模拟数据
	var html = '<tr><td><input id="delAll" type="checkbox" onclick="toggleSelectAll(this)"/></td><td>序号</td><td>IP地址</td><td>MAC地址</td><td>端口号</td><td>逻辑考场</td><td>物理考场</td><td>操作</td></tr>';
	$("#pageNum1").val(pageNums);
	var address = $("#keyworldClock").val();
	var ip = $("#clockIp").val();
	var port = $("#clockPort").val();
	var data = {
		pageNum : pageNums,
		pageSize : 10,
		address : address,
		ip : ip,
		remark : port
	}
	$.ajax({
				url : basePath + 'clockdevice/queryClockDevices',
				type : 'post',
				data : data,
				success : function(res) {
					var list = res.result.list;
					if (list == undefined) {
						html += "<tr><td colspan='10' class='text-danger text-center'>没有相关数据</td></tr>";
						res.result.pages = 1;
					} else {
						var result = list;
						for (var i = 0; i < result.length; i++) {
							var index = i + 1;
							html += "<tr><td>";
							html += "<input name='ids' type='checkbox' value='"
									+ result[i].id + " ' />"
							html += "</td><td>" + index + "</td>";
							html += "<td >" + result[i].ip + "</td>";
							html += "<td >" + result[i].mac + "</td>";
							html += "<td >" + result[i].port + "</td>";
							if (result[i].address == null) {
								html += "<td></td>";
							} else {
								html += "<td >" + result[i].address + "</td>";
							}
							html += "<td>" + result[i].remark + "</td>";
							html += "<td >"              
									+ "<img title='查看' onclick='goSee(this)' class='tdImg' name='"
									+ result[i].id
									+ "' src='"
									+ basePath
									+ "/static/jy/img/pcImg/searchBlack.png'>"
									+ "<img title='编辑' onclick='goEdit(this)' class='tdImg' name='"
									+ result[i].id
									+ "' src='"
									+ basePath
									+ "/static/jy/img/pcImg/edit.png'>"
									+ "<img title='删除' onclick='del(this)' class='tdImg' name='"
									+ result[i].id + "' src='" + basePath
									+ "/static/jy/img/pcImg/deleteT.png'>"
							html += "</td></tr>";

						}
					}

					var tbody = $("#deviceList");
					tbody.html(html);

					$("#pageNum").val(res.result.pageNum);
					$("#pages").val(res.result.pages);

					JRYPage.pageInit('pageCss', res.result.pageNum,
							res.result.pages, 11, changePage);
				},
				error : function(res) {

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
//弹出查看窗口
function goSee(e) {
	
	var id = e.name;
	var o = $("#bootForm_JRY");
	var data = {
		id : id
	};
	$.post("clockdevice/getClockDeviceById", data, function(data) {
		var device = data.result;

		o.find(".ip").text(device.ip);
		o.find(".mac").text(device.mac);
		o.find(".port").text(device.port);
		o.find(".address").text(device.address);
		o.find(".remark").text(device.remark);
		if(device.showSec == 1){
			o.find(".showSec").text("开启");
		}else{
			o.find(".showSec").text("关闭");
		}

		JRYPopup.Jcustom("seeDiv");
		$("#titleId").text("查看详情");
	});

}
//弹出编辑窗口
function goEdit(e) {
	
	$("#hiddenId").val(e.name);
	var id = e.name;
	var data = {
		id : id
	};
	JRYPopup.Jcustom("editDiv");$("#titleId").text("编辑详情");
	$.post("clockdevice/getClockDeviceById", data, function(data) {
		var device = data.result;

		$("#ip").val(device.ip);
		$("#mac").val(device.mac);
		$("#port").val(device.port);
		$("#address").val(device.address);
		$("#remark").val(device.remark);
		$("#showSec").val(device.showSec);
	});

}

function del(e) {
	var pageNum = $("#pageNum").val();
	var id = e.name;
	var data = {
		id : id
	};
	JRYPopup.Jconfim("确定要删除已选择的数据吗？",function(){
		$.ajax({
			url : basePath + 'clockdevice/deleteClockDevice',
			type : 'post',
			data : data,
			success : function(res) {
				loadData(pageNum);
			},
			error : function(res) {

			}
		})
	});
	
}

//检索条件验证
function validateCriteriaForm() {
	$("#editForm").find("font").remove();
	var flag = true;

	//ip地址校验
	var ipVal = $("#ip").val();
	var re = /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
	if (ipVal != "" && !re.test(ipVal)) {
		abscissaRed('ip', "IP地址格式不正确!");
		flag = false;
	} else if (ipVal == '') {
		abscissaRed('ip', "IP地址不能为空!");
		flag = false;
	}

	//端口地址校验
	var portVal = $("#port").val();
	var patternMac = /^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5])$/;
	if (portVal != "" && !patternMac.test(portVal)) {
		abscissaRed('port', "端口地址格式不正确!");
		flag = false;
	} else if (portVal == '') {
		abscissaRed('port', "端口地址不能为空!");
		flag = false;
	}

	//位置校验
	var addressVal = $("#address").val();
	if (addressVal == '') {
		abscissaRed('address', "位置不能为空!");
		flag = false;
	}

	return flag;
}
function edit() {
	if (validateCriteriaForm()) {
		var address = $("#address").val();
		var ip = $("#ip").val();
		var mac = $('#mac').val();
		var port = $('#porot').val();
		var remark = $("#remark").val(); 
		var id = $("#hiddenId").val();
		var showSec = $("#showSec").val();
		var data = {
			id:id,
			mac:mac,
			address: address,
			ip : ip,
			remark:remark,
			port : port,
			showSec:showSec
		}
		var pageNum = $("#pageNum").val();
		$.ajax({
			url : basePath + 'clockdevice/updateClockDevice',
			type : 'post',
			data : data,
			success : function(res) {
				loadData(pageNum);CancelPop()
			},
			error : function(res) {

			}
		})
		
	}

}
//取消弹出框 
function CancelPop() {
	JRYPopup.popupClose();
}
function delAll(){
	var ads = [];var pageNum = $("#pageNum").val();
	var checkoutbox =  $("#table tbody").find("input[type=checkbox]:checked");
	if(checkoutbox.length==0){
		JRYPopup.Jalert("请选择要删除的设备");
	}else{
		for(var i=0;i<checkoutbox.length;i++){
			ads.push($(checkoutbox[i]).val());
		}
		JRYPopup.Jconfim("确定要删除已选择的数据吗？",function(){
			$.ajax({
				url : basePath + 'clockdevice/deleteClockDevices',
				type : 'post',
				data : {idString:ads},
				traditional: true,
				success : function(res) {
					loadData(pageNum);CancelPop()
				},
				error : function(res) {

				}
			})
		});
		
	}
}
function queryAll(){
	var pageNum = $("#pageNum").val();
	$.ajax({
		url : basePath + 'clockdevice/queryClockDevices',
		type : 'post',
		data : {ip:$("#clockIp").val(),
				address:$("#keyworldClock").val(),
				remark:$("#clockPort").val(),
				pageNum:1,
				pageSize:10
			},
		success : function(res) {
			loadData(pageNum);
		},
		error : function(res) {

		}
	})
}