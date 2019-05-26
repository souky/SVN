$(function () {
	$(".Jry_query").show();$('.count_num').hide();$('.all_title').hide();
	loadData();
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
        url: "clockdevice/queryClockDevicesByOrderList",
        data: '',
        type: "POST",
        success: function (data) {
            if (data.code == '10000') {
//                statusAll = data.result.totalSwitchStatus;
//                if (statusAll == 2) {
//                    $('#totalSwitchStatus').addClass('currentTwo');
//                    $('#totalSwitchStatus').find(".isValid-scroll").html("off");
//                }
                new jy_creatList().creats({
                    building: a,
                    floor: b,
                    classNo: c,
                    dataList: data.result,
                    basePath: $("#basepath").val(),
                    buildingNameList: d,
                    powerOff:statusAll
                });

//                $('#clock_normal').html(data.result.onlineShiled);
//                $('#clock_offline').html(data.result.abnormalShiled);
//                $('#site_name').html(data.result.orgName)
            }
        }

    });
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
        	building = $("#buildingSelect").val();
            floor = $("#floorSelect").val();
            classNo = $("#classSelect").val();
            
            document.getElementById("allDom").innerHTML = '';
            new jy_creatList().creats({
                building: $("#buildingSelect").val(),
                floor: $("#floorSelect").val(),
                classNo: $("#classSelect").val(),
                dataList: [],
                basePath: $("#basepath").val()
            });
        },
        error: function () {

        }
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
        if (this.value == ''||respecs.test(this.value)) {
            abscissaRed(this.id, "&nbsp必填");
            flag = false;

        }
    });

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
        url: "clockdevice/getIdleClockDevices",
        data: "",
        success: function (data) {
            var options = '';
            for (var a = 0; a < data.result.length; a++) {
                options += '<option value="' + data.result[a].id + '">' + data.result[a].ip + '</option>'
            }
            $("#ip").append(options);
        },
        error: function () {

        }
    });

    if(id == '' || id == null){
        $("#ip").attr("disabled", false);
    } else {
    	var data = {id:id};
    	$.post("clockdevice/getClockDeviceById", data, function(data) {
    		var device = data.result;

    		$("#ip").attr("disabled", false);
            $("#id").val(device.id);
            $("#ip").html('<option value="' + device.id + '">' + device.mac + '</option>');
            $("#address").val(device.address);
    	});
    }


}

//修改
function edit() {
    if (validateEditForm()) {
        var datas = {
            id: $("#ip").val(),
            address: $("#address").val(),
            listOrder: $("#listOrder").val()
        }
        $.ajax({
            type: "POST",
            url: "clockdevice/updateClockDevice",
            data: datas,
            success: function (data) {
                if (data.code == '10000') {
                    CancelPop();
                    loadshield(building, floor, classNo, buildingNameList);
                    
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
            url: "clockdevice/bootDevice",
            data: {id: ids},
            success: function (data) {
                if (data.code == '10000') {
                    
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
    $(".bkint").addClass("hideDom");
    $(e).hide();
    $("#showdom").show();
}

function showDom(e) {
    $(".bkint").removeClass("hideDom");
    $(e).hide();
    $("#hidedom").show();
}

function editBuliding(e) {
    if ($(e).children("input").length == 0) {
        console.log($(e));
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

function shieldshow(e){
	if($(e).hasClass("transitionviews-down")){
		$(e).parents('.domClass').find('.buildDOm').slideToggle();
		$(e).removeClass("transitionviews-down").addClass("transitionviews-up");
		
	}else if($(e).hasClass("transitionviews-up")){
		$(e).parents('.domClass').find('.buildDOm').slideToggle();
		$(e).removeClass("transitionviews-up").addClass("transitionviews-down");
	}
}
	
	