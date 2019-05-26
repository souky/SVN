$(function () {
	initSwitch();
    loadData();
    $(document).bind("click",function(e){
        var _con = $('#showRecordList');   // 设置目标区域

            if(!_con.is(e.target) && _con.has(e.target).length === 0){ // Mark 1
                //$('#divTop').slideUp('slow');   //滑动消失
                $('#showRecordList').hide(500);
                //淡出消失
            }
    });
    
});
var testData = [];
var building = '';
var floor = '';
var classNo = '';
var buildingNameList = [];
var classroomid = '';
var statusAll = '';

function loadData() {
    $.ajax({
        async: false,
        url: "classroominfo/queryClassroomInfos",
        data: '',
        type: "POST",
        success: function (data) {
            if (data.code == '10000') {
                if (data.result == '') {
                    $(".Jry_query").show();

                } else {
                    $(".Jry_query").hide();

//        		   new jy_creatList().creats({
//       				building:data.result[0].buildingNo,
//       				floor:data.result[0].floorNo,
//       				classNo:data.result[0].classroomNo,
//       				dataList:data.result[0].devices
//       			});
                    building = data.result[0].buildingNo;
                    floor = data.result[0].floorNo;
                    classNo = data.result[0].classroomNo;
                    buildingNameList = data.result[0].buildingNameList;
                    classroomid = data.result[0].id;
                    loadshield(building, floor, classNo, buildingNameList);
                }
            }
        }

    });
}

function loadshield(a, b, c, d) {
    $("#allDom").html('');
    $.ajax({
        async: false,
        url: "device/queryDevicesByListOrder",
        data: '',
        type: "POST",
        success: function (data) {
            if (data.code == '10000') {
                statusAll = data.result.totalSwitchStatus;
                if (statusAll == 2) {
                    $('#topOff').removeClass('active');
                    $('#topOff').html("OFF");
                    $('#topOff').attr("data-power",1);
                }else{
                	$('#topOff').addClass('active');
                    $('#topOff').html("ON");
                    $('#topOff').attr("data-power",2);
                }
                new jy_creatList().creats({
                    building: a,
                    floor: b,
                    classNo: c,
                    dataList: data.result.devices,
                    basePath: $("#basepath").val(),
                    buildingNameList: d,
                    powerOff:statusAll
                });

                $('#onlineCount').html(data.result.onlineShiled);
                $('#leavesCount').html(data.result.abnormalShiled);
                if(data.result.orgName=='苏州大学'){
                	$('#site_name').html('');
                }else{
                	$('#site_name').html(data.result.orgName);
                }
                $('#uninstalls').html(a * b * c - data.result.onlineShiled - data.result.abnormalShiled);
            }
        }

    });
}


function changeSound(e) {
    var id = $(e).attr('name');
    if ($(e).hasClass('current')) {
        var poweroff = 1;
        $.ajax({
            type: "POST",
            url: "device/updateShieldPoweroff",
            data: {id: id, poweroff: poweroff},
            beforeSend: function(){      
            	JRYPopup.Jloading("数据处理中..");
            },
            success: function (data) {
                if (data.code == '10000') {
                	JRYPopup.popupClose();
                    $(e).toggleClass('current');
                    $(e).find(".isValid-scroll").html("ON");
                    var grads = $(e).parent().next().find('.gradRed');
                    for(var i=0;i<grads.length;i++){
                        $(grads[i]).removeClass('gradRed').addClass('grad');
                    }
                } else {
                	JRYPopup.popupClose();
                    JRYPopup.Jalert(data.message)
                }
            },
            error: function () {
            	JRYPopup.popupClose();
                JRYPopup.Jalert("修改失败！");
            }
        })

    } else {
        var poweroff = 2;
        $.ajax({
            type: "POST",
            url: "device/updateShieldPoweroff",
            data: {id: id, poweroff: poweroff},
            beforeSend: function(){      
            	JRYPopup.Jloading("数据处理中..");
            },
            success: function (data) {
                if (data.code == '10000') {
                	JRYPopup.popupClose();
                    $(e).toggleClass('current');
                    $(e).find(".isValid-scroll").html("OFF");
                    var grads = $(e).parent().next().find('.grad');
                    for(var i=0;i<grads.length;i++){
                        $(grads[i]).removeClass('grad').addClass('gradRed');
                    }
                    if($('.current').length==$('.isValid').length){
                    	$('#topOff').removeClass('active');
                        $('#topOff').html("OFF");
                        $('#topOff').attr("data-power",1);
                    }
                } else {
                	JRYPopup.popupClose();
                    JRYPopup.Jalert(data.message)
                }
            },
            error: function () {
            	JRYPopup.popupClose();
                JRYPopup.Jalert("修改失败！");
            }
        })

    }

}

// initSwitch
function initSwitch(){
	$.ajax({
        async: false,
        url: "device/querySwitch",
        data: '',
        type: "POST",
        success: function (data) {
        	if(data.code == "10000"){
        		var list = data.result;
        		if(list[0] == '1'){
        			funcON("#onl_")
        		}else{
        			funcOFF("#onl_");
        		}
        		
        		if(list[1] == '1'){
        			funcON("#tel_")
        		}else{
        			funcOFF("#tel_");
        		}
        		
        		if(list[2] == '1'){
        			funcON("#pro_")
        		}else{
        			funcOFF("#pro_");
        		}
        	}
        }
    });
}

function editshield(e) {
    var id = $(e).attr('name');
    $("#hiddenId").val(id);
    $.ajax({
        async: false,
        url: "device/queryDeviceById",
        data: {"id": id},
        type: "POST",
        success: function (data) {
            if (data.result != null) {
                if (data.result.controlled == 1) {
                    $("#autoRadio_JRY").attr("checked", "checked");
                    disableModule();
                } else {
                    enableModule();
                    $("#autoRadio_JRY").attr("checked", false);
                    $("#manualRedio_JRY").attr("checked", "checked");
                }

                if (data.result.operationType == 2) {
                    $("#1g_JRY").attr("checked", "checked");
                    $("#phone_JRY").attr("checked", "checked");
                } else if (data.result.operationType == 3) {
                    $("#1g_JRY").attr("checked", "checked");
                    $("#phone_JRY").removeAttr("checked");
                } else if (data.result.operationType == 4) {
                    $("#phone_JRY").attr("checked", "checked");
                    $("#1g_JRY").removeAttr("checked");
                } else if (data.result.operationType == 5) {
                    $("#phone_JRY").removeAttr("checked");
                    $("#1g_JRY").removeAttr("checked");
                }
            }
        }

    });
    JRYPopup.Jcustom("bootDiv");
    $("#bootTitle").text("编辑设备信息");
}

function disableModule() {
    $(".moduleInput").prop("checked", false);//从手动转为自动时,取消全选
    $(".moduleInput").prop("disabled", true);
}

function enableModule() {
    $(".moduleInput").prop("disabled", false);
}

//取消弹出框
function CancelPop() {
    JRYPopup.popupClose();
}

function startAll() {
    $.ajax({
        type: "POST",
        url: "classroominfo/saveClassroomInfo",
        data: {
            buildingNo: $("#buildingSelect").val(),
            floorNo: $("#floorSelect").val(),
            classroomNo: $("#classSelect").val()
        },
        success: function (data) {

        },
        error: function () {

        }
    });
    building = $("#buildingSelect").val();
    floor = $("#floorSelect").val();
    classNo = $("#classSelect").val();

    document.getElementById("allDom").innerHTML = '';
    new jy_creatList().creats({
        building: $("#buildingSelect").val(),
        floor: $("#floorSelect").val(),
        classNo: $("#classSelect").val(),
        dataList: testData,
        basePath: $("#basepath").val()
    });
}

//必填信息验证
function validateEditForm() {
    $("#editFormShield").find("font").remove();
    var flag = true;
    var inputs = $(".required");
    var regu = "^[ ]+$";
    var respecs = new RegExp(regu);
    inputs.each(function () {

        if (this.value == ''|| respecs.test(this.value)) {
            abscissaRed(this.id, "&nbsp必填");
            flag = false;

        }
    });

    //ip地址校验
    // var ipVal = $("#ip").val();
    // var re = /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
    // if (ipVal != "" && !re.test(ipVal)) {
    //     //$("#ip").after("<p class='validateMsg' style='color:red'> ip地址格式不正确，请修改</p>");
    //     abscissaRed('ip', "&nbspip地址格式不正确，请修改");
    //     flag = false;
    // }

    return flag;
}

//弹出编辑窗口
function goEdit(e, ids) {
    JRYPopup.Jcustom("editDiv");
    //clearEditForm();
    $("#titleId").text("编辑");
    $("#editBtn").attr("onclick", "edit()");
    var id = $(e).attr('name');
    $("#listOrder").val(ids);
    $.ajax({
        type: "POST",
        url: "device/queryMACs",
        data: "",
        success: function (data) {
            var options = '';
            for (var a = 0; a < data.result.length; a++) {
                options += '<option value="' + data.result[a] + '">' + data.result[a] + '</option>'
            }
            $("#ip").append(options);
        },
        error: function () {

        }
    });

    if(id == '' || id == null){
        $("#ip").attr("disabled", false);
    } else {
        $.get("device/queryDeviceById.do?id=" + id, function (data) {
            var device = data.result;
            $("#ip").attr("disabled", false);
            $("#id").val(device.id);
            $("#ip").append('<option value="' + device.mac + '">' + device.mac + '</option>');
            $("#ip").val(device.mac);
            if(device.address){
                $("#address").val(device.address);
            }
        });
    }


}

//修改
function edit() {
    if (validateEditForm()) {
        var datas = {
            mac: $("#ip").val(),
            address: $("#address").val(),
            listOrder: $("#listOrder").val()
        }
        $.ajax({
            type: "POST",
            url: "device/updateShieldListOrder",
            data: datas,
            success: function (data) {
                if (data.code == '10000') {
                    CancelPop();
                    loadshield(building, floor, classNo, buildingNameList);
                    JRYPopup.popupClose();
                } else {
                    JRYPopup.Jalert(data.message)
                }
            },
            error: function () {
                JRYPopup.Jalert("修改失败！");
            }
        })
    }

}

function delectshield(e, num) {
    var ids = $(e).attr('name');
    JRYPopup.Jconfim('确定要删除已选择的数据吗？', function () {
        $.ajax({
            type: "POST",
            url: "device/clearListOrder",
            data: {id: ids},
            success: function (data) {
                if (data.code == '10000') {
                    $(e).parents(".classClass").css("background-color", "#fff");
                    $(e).parents(".classClass").find(".swicthinput").text("--");
                    loadshield(building, floor, classNo, buildingNameList);
                    JRYPopup.popupClose();
                } else {
                    JRYPopup.Jalert(data.message)
                }
            },
            error: function () {
                JRYPopup.Jalert("修改失败！");
            }
        })
    });

}

function doBoot() {
    $.ajax({
        type: "POST",
        url: "device/bootDevice",
        data: $("#bootForm").serialize(),
        success: function (data) {
            if (data.code == '10000') {
                CancelPop();
                loadData(1);
            } else {
                JRYPopup.Jalert(data.message);
            }
        },
        error: function () {
            JRYPopup.Jalert("网络错误");
        }
    })

}

function hideDom(e) {
	if($(e).attr("data-show") == 1){
		$(e).attr("data-show",0);
		$(".bkint").addClass("hideDom");
	}else{
		$(e).attr("data-show",1);
		$(".bkint").removeClass("hideDom");
	}
}

function editBuliding(e) {
    if ($(e).children("input").length == 0) {
        var valus = $(e).text();
        $(e).html("<input onkeypress='saveBulid(event,this)' style='width:100px' type='text' value='" + valus + "' />")
    } else {

    }
}

function saveBulid(event, e) {
    var classroomnames = [];
    if (event.keyCode == "13") {
        for (var i = 0; i < $('.topClass').length; i++) {
            var topthis = $('.topClass')[i];
            var topthisdiv = $(topthis).children()[0];
            if ($(topthisdiv).children('input').length == 0) {
                classroomnames.push($(topthisdiv).text());
            } else {
                var inputsthis = $(topthisdiv).children('input')[0];
                classroomnames.push($(inputsthis).val());
            }
        }

        $.ajax({
            type: "POST",
            url: "classroominfo/updateClassroomInfo",
            data: {id: classroomid, buildingNamesList: classroomnames},
            traditional: true,
            success: function (data) {
                if (data.code == '10000') {
                    $(e).parent().text($(e).val());
                } else {

                }
            },
            error: function () {
                JRYPopup.Jalert("网络错误");
            }
        });
    }
}


function changeitems_switch(e) {
	JRYPopup.Jloading("数据处理中..");
    var poweroff = $(e).attr("data-power");
    $.ajax({
        type: "POST",
        url: "device/updateAllOnlineShield",
        data: {poweroff: poweroff},
        success: function (data) {
            if (data.code == '10000') {
            	JRYPopup.popupClose();
            	if(poweroff == 2){
            		$(e).attr("data-power",1);
            		$(e).removeClass("active")
            		$(e).html("OFF")
            		funcOFFAll();
            	}else{
            		$(e).attr("data-power",2)
            		$(e).addClass("active")
            		$(e).html("ON")
            		funcONAll();
            	}
                loadshield(building, floor, classNo, buildingNameList);

            } else {
            	JRYPopup.popupClose();
                JRYPopup.Jalert(data.message)
            }
        },
        error: function () {
        	JRYPopup.popupClose();
            JRYPopup.Jalert("网络错误");
        }
    })
}

//三个分体开关
function funcBtn(obj){
	JRYPopup.Jloading("数据处理中..");
	var type = $(obj).attr("data-type");
	var status = $(obj).attr("data-status");
	$.ajax({
        type: "POST",
        url: "device/swicth",
        data: {type: type,status:status},
        success: function (data) {
            if (data.code == '10000') {
            	if(status == 1){
            		funcON(obj)
            	}else{
            		funcOFF(obj)
            	}
            	JRYPopup.popupClose();
                loadshield(building, floor, classNo, buildingNameList);
            } else {
            	JRYPopup.popupClose();
                JRYPopup.Jalert(data.message)
            }
        },
        error: function () {
        	JRYPopup.popupClose();
        	JRYPopup.Jalert("网络错误");
        }
    })
}

// 分体开关开
function funcONAll(){
	funcON("#onl_")
	funcON("#tel_")
	funcON("#pro_")
}
function funcOFFAll(){
	funcOFF("#onl_")
	funcOFF("#tel_")
	funcOFF("#pro_")
}
function funcON(obj){
	$(obj).addClass("active");
	$(obj).html("ON");
	$(obj).attr("data-status",2);
}
function funcOFF(obj){
	$(obj).removeClass("active");
	$(obj).html("OFF");
	$(obj).attr("data-status",1);
}

function changeLigth(e, ids) {
    var modules = e;
    var lightids = $(ids).parent().parent().attr('name');
    var status = 2;
    if($(ids).hasClass("gradRed")){
    	status = 1;
    }
    $.ajax({
        type: "POST",
        url: "device/updateSelfShieldStatus",
        data: {id: lightids, module: modules,status:status},
        success: function (data) {
            if (data.code == '10000') {
                $(ids).toggleClass('grad');
                $(ids).toggleClass('gradRed');

            	console.log($(ids).parent().find('.grad').length);
                if($(ids).parent().find('.grad').length==0){
                	$(ids).parent().prev().find('.isValid').click();
                }
            } else {
                JRYPopup.Jalert(data.message)
            }
        },
        error: function () {
            JRYPopup.Jalert("网络错误");
        }
    });
}

var domheight = 0;

function shieldshow(e) {
    if ($(e).hasClass("transitionviews-down")) {
        $(e).parents('.domClass').find('.buildDOm').slideToggle();
        $(e).removeClass("transitionviews-down").addClass("transitionviews-up");

    } else if ($(e).hasClass("transitionviews-up")) {
        $(e).parents('.domClass').find('.buildDOm').slideToggle();
        $(e).removeClass("transitionviews-up").addClass("transitionviews-down");
    }
}

function showRecordList(event) {
    event.stopPropagation();
    //按钮的toggle,如果div是可见的,点击按钮切换为隐藏的;如果是隐藏的,切换为可见的。
    $('#showRecordList').toggle(500);
}
	
	
	