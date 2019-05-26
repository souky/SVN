<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>卫星分布</title>
    <%@ include file="/WEB-INF/views/version.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/common/css/bootstrap.min.css<%=version%>"/>
   
</head>
<style>
.all_title {
    margin: auto;
    background: #fff;
    margin-top: 20px;
    height: 40px;
    line-height: 40px;
    font-size: 20px;
}
.count_num {
    height: 60px;
    line-height: 60px;
    background: #fff;
    margin: auto;
}
.count_num .count_items {
    width: 100%;
}
.count_num .count_items .items_img {
    width: 32px;
    margin-top:10px
}
.count_num .count_items .items_img img {
    width: 100%;
    cursor: pointer;
}
.count_num .count_items .items_text div {
    margin-left: 10px;
    color: #272727; 
    font-size: 16px;
}
#allDom{
    width: 94%;
    float: left;
    padding: 0 3%;
    background: #fff;
}
#wxtable{
	width:100%
}
#wxtable>thead>tr>th{
	 text-align: center;
}
#wxtable>tbody>tr>td img {
    width: 24px;
}
#wxtable>tbody>tr>td {
    text-align: center;
}
#wxtable>tbody>tr>td>.progress{
	margin:5px 0;
}
#wxremark img {
    width: 21px;
}
</style>
<body>
   <input id="basepath" value="<%=basePath%>" type="hidden">
 
	<div class="all_title ac" id="site_name"></div>
	<div class="count_num clearfix">
		<div class="count_items fl clearfix">
			<div class="items_img fl" title="屏蔽" style="margin-left:60px">
				<img src="<%=basePath%>/static/jy/img/overview/wx.png">
			</div>
			<div class="items_text fl clearfix">
				<div class="fl">卫星个数：</div>
				<div class="fl"><font style="color:#78be66;margin-left:5px;" id="clock_normal"></font></div>
			</div>
			
		</div>
	</div>
	<div id="allDom">
		<table class="table table-hover" id="wxtable">
			<thead>
				 <tr>
					<th width="90px">卫星类型</th>
					<th width="90px">卫星编号</th>
					<th >信噪比</th>
					<th width="120px">参与计算</th>
					<th width="90px">方位角</th>
					<th width="90px">仰角</th>
				 </tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	   <P id="wxremark">*注: <img src="<%=basePath%>/static/jy/img/overview/ch.png" /> 代表北斗卫星， <img src="<%=basePath%>/static/jy/img/overview/am.png" /> 代表GPS卫星</P>
	</div>

	
</body>
<script type="text/javascript" src="static/jy/js/common/validate.js"></script>
<script>
$(function(){
	//get schoolName
	var basepath = $("#basepath").val();
	$.post(basepath+"getSchoolName",function(res){
		$("#site_name").html(res.result);
	});
	
	$.post(basepath+"satellite/querySatellites",function(res){
		var data = res;
		$("#clock_normal").html(res.result.length);
		
		var wxt;var wximg;var wxlineimg;var wxnoise;var azimuth;var elevation;var noisecloro;var changeNum;

		for(var i=0;i<data.result.length;i++){
			if(data.result[i].saType=='2')
				wximg = '<%=basePath%>/static/jy/img/overview/ch.png';
			else
				wximg = '<%=basePath%>/static/jy/img/overview/am.png';	
			if(data.result[i].isCalculation=='1')
				wxlineimg = '<%=basePath%>/static/jy/img/overview/clockpass.png';
			else
				wxlineimg = '<%=basePath%>/static/jy/img/overview/clockno.png';	
			if(data.result[i].noiseSignal==''){
				wxnoise = '0%';
				noisecloro = '#c5d845'
			}
			else{
				changeNum = parseFloat(data.result[i].noiseSignal);
				wxnoise = changeNum/60*100+'%';
				if(changeNum>30){
					noisecloro = '#91d747'
				}else if(changeNum<20){
					noisecloro = '#d8b94f'
				}else{
					noisecloro = '#c5d845'
				}
			}
			if(data.result[i].azimuth == null || data.result[i].azimuth == "NaN" || data.result[i].azimuth == ""){
				azimuth = '--';
			}else{
				azimuth = data.result[i].azimuth + "°";
			}
			if(data.result[i].elevation == null || data.result[i].elevation == "NaN" || data.result[i].elevation == ""){
				elevation = '--';
			}else{
				elevation = data.result[i].elevation + "°";
			}
			wxtr = '';
			wxtr +="<tr>\
					<td><img src='"+wximg+"' /></td>\
		      		<td>"+data.result[i].saNo+"</td>\
		      		<td>\
		      			<div class='progress'>\
				  		<div class='progress-bar progress-bar-info' role='progressbar' aria-valuenow='"+data.result[i].noiseSignal+"' aria-valuemin='0' aria-valuemax='60' style='background-color:"+noisecloro+";min-width: 2em;width:"+wxnoise+";'>"+data.result[i].noiseSignal+"\
				  		</div>\
						</div>\
					 </td>\
			         <td><img src='"+wxlineimg+"' /></td>\
			        	 <td>"+azimuth+"</td>\
			        	 <td>"+elevation+"</td>\
			        </tr>"
			$("#wxtable tbody").append(wxtr);
		}
	});
})


</script>
</html>