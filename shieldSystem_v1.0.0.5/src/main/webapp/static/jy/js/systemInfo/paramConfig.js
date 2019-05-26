$(function(){
	 $(".choices .right_btn").click(function(){
		 $(".choices .right_btn").removeClass("active");
		 $(this).addClass("active");
	 });
	 
	 //获取当前是否平台已连接侦测服务器
	 getConnectStatus();
 });

//连接侦测
function connectServer(status){
    JRYPopup.Jloading("正在执行操作");
	$.ajax({
        url: "paramConfig/connectServer",
        type: 'post',
        data: {status:status},
        dataType: 'json', 
        success: function (res) {
            if (res.code == '10000') {
                JRYPopup.popupClose();
                if(status == 1){
                    $('#closeConnect').hide();
                    $('#startConnect').show();
                }else{
                    $('#startConnect').hide();
                    $('#closeConnect').show();
                }
            } else {
                JRYPopup.Jalert("网络错误");
            }
        }, 
        error: function (res) {
            JRYPopup.Jalert("网络错误");
        }
    });
}

function getConnectStatus(){
	$.ajax({
        url: "paramConfig/getConnectStatus",
        type: 'post',
        dataType: 'json', 
        success: function (res) {
            if (res.code == '10000') {
            	var result = res.result;
            	if(result){
            		$('#closeConnect').hide();
            		$('#startConnect').show();
                    $('#withDetection').attr("onclick",null);
                    $('#noDetection').attr("style","cursor:default");
            	}else{
            		$('#startConnect').hide();
            		$('#closeConnect').show();
                    $('#noDetection').attr("onclick",null);
            	}
            } else {
                JRYPopup.Jalert("网络错误");
            }
        }, 
        error: function (res) {
            JRYPopup.Jalert("网络错误");
        }
    });
}

//考前--清理数据
function clearDataBeforeExam(){
    JRYPopup.Jconfim('确定要清除所有数据吗？',clearData,"");

}

function clearData(){
	$.ajax({
        url: "paramConfig/clearDataBeforeExam",
        type: 'post',
        dataType: 'json', 
        success: function (res) {
            if (res.code == '10000') {
            	JRYPopup.Jalert(res.result);
                //JRYPopup.popupClose();
            } else {
                JRYPopup.Jalert("操作失败");
            }
        }, 
        error: function (res) {
            JRYPopup.Jalert("网络错误");
        }
    });
}