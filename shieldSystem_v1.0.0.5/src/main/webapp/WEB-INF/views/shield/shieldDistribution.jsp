<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="/WEB-INF/views/version.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/jy/css/shield/shield.css<%=version%>"/>
     <link rel="stylesheet" type="text/css" href="<%=basePath%>static/jy/css/autoExamLayout/shieldExam.css<%=version%>"/>
</head>
<body>
   <input id="basepath" value="<%=basePath%>" type="hidden">
   <div class="Jry_query" style="display:none">
			<div class="query_part">
				<div class="query_items">
					<label>教室楼栋数</label>
					<div class="inputDivOne">
						<select id="buildingSelect">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
	                    </select>
					</div>
				</div>
			
				<div class="query_items">
					<label>每栋楼层数</label>
					<div class="inputDivOne">
						<select id="floorSelect">
	                        <option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
	                    </select>
					</div>
				</div>
				<div class="query_items">
					<label>每层教室数</label>
					<div class="inputDivOne">
						<select id="classSelect">
	                        <option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
	                    </select>
					</div>
				</div>
				<div class="query_items">
					<div class="query_btns">
						<div class="query_btn" onclick="startAll()">
							生成
						</div>
					</div>
				</div>
			</div>
	</div>
	<div id="counts" class="clearfix">
		<div class="fl left_part clearfix">
			<div class="items fl">
				在线 :<font id="onlineCount" style="color:#23cafd;">0</font>
			</div>
			<div class="items fl">
				离线 :<font id="leavesCount" style="color:red;">0</font>
			</div>
			<div class="items fl">
				未绑定 :<font id="uninstalls" >0</font>
			</div>
		</div>
		
		<div class="fr" style="width:40px;margin-right:3%;">
			<img id="showClassRoom" onclick="hideDom(this)" data-show="1" title="显示/隐藏未绑定教室" src="<%=basePath%>/static/jy/img/pcImg/kj2x.png" width="100%"/>
		</div>
		<div class="fr btnOff" >
			<div class="items fl">
				总开关 :<div id="topOff" class="fr ac CP btn_off_on" data-power="2" onclick="changeitems_switch(this)">OFF</div>
			</div>
		</div>
	</div>
	<div id="counts" class="clearfix">
		<div class="fr" style="width:40px;margin-right:3%;height:40px;"></div>
		<div class="fr btnOff">
			<div class="items fl">
				上网屏蔽开关 :
				<div id="onl_" class="fr ac CP btn_off_on" data-type="3"
				 data-status="1" onclick="funcBtn(this)">OFF</div>
			</div>
		</div>
		<div class="fr btnOff" >
			<div class="items fl">
				电话屏蔽开关 :
				<div id="tel_" class="fr ac CP btn_off_on" data-type="2"
				data-status="1" onclick="funcBtn(this)">OFF</div>
			</div>
		</div>
		<div class="fr btnOff" >
			<div class="items fl">
				专业作弊开关 :
				<div id="pro_" class="fr ac CP btn_off_on" data-type="1"
				data-status="1" onclick="funcBtn(this)">OFF</div>
			</div>
		</div>
	</div>
	<div class="all_title ac" id="site_name" style="display:none"></div>
	<div id="allDom">
	</div>
	<div class="hide " id="editDiv"> 
    	<form id="editFormShield_JRY"> 
	     	<div class="Jcustom_title" id="titleId_JRY"></div>
			<div class="Jcustom_content">
				<input type="hidden" name="id" id="id_JRY"/>
				<input type="hidden" name="listOrder" id="listOrder_JRY"/>

				<div class="Jcustom_content_items">
					<label>设备MAC:</label>
<!-- 					<input type="text" name="ip_JRY" id="ip_JRY" placeholder="请输入设备IP" maxlength="32" class="required_JRY edit" readonly />
 -->					<select class="required_JRY edit" name="ip_JRY" id="ip_JRY">
							<option value='' selected>请选择设备MAC</option>
						</select>
					<div class="error_text"></div>
				</div>
				<div class="Jcustom_content_items">
					<label>位置:</label>
					<input type="text" name="address" id="address_JRY" placeholder="请输入位置信息,最多输入6个字符" maxlength="6" class="required_JRY edit"/>
					<div class="error_text"></div>
				</div>
		
				<div class="Jcustom_content_btns">
					<div class="okBtn btns" id="editBtn_JRY" >确定</div>
					<div class="cancelBtn btns" onclick="CancelPop()">取消</div>
				</div>
			</div>
		</form>
	</div>
	
</body>
<script type="text/javascript" src="static/jy/js/common/validate.js"></script>
<script type="text/javascript" src="<%=basePath%>static/jy/js/shield/shield.js<%=version%>" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>static/jy/js/shield/shieldmain.js<%=version%>" charset="UTF-8"></script>
<script>

</script>
</html>