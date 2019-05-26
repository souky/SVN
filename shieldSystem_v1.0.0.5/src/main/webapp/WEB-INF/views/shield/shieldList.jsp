<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Equipment Page</title>
    <%@ include file="/WEB-INF/views/version.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/jy/css/common/common.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/jy/css/equipment/equipment.css<%=version%>"/>

    <style type="text/css">
        .Jry_table .table_body th{
            font-size: 16px;
            color: black;
        }
    </style>
</head>
<body>

<input type="hidden" id="basePath" value="<%=basePath%>"/>
<form id="searchCriteriaForm">
    <div class="Jry_query">
        <div class="query_part">
            <div class="query_items">
                <label>IP地址</label>
                <div class="inputDivOne">
                    <input name="ip" id="ipSearch" type="text"/>
                </div>
            </div>

            <div class="query_items">
                <label>MAC地址</label>
                <div class="inputDivOne">
                    <input name="mac" id="macSearch" type="text"/>
                </div>
            </div>

            <div class="query_items">
                <label>状态</label>
                <div class="inputDivOne">
                    <select name="status" id="statusSelect">
                        <option value=-1>全部</option>
                        <option value=1>在线</option>
                        <option value=2>离线</option>
                        <option value=3>未安装</option>
                    </select>
                </div>
            </div>

            <div class="query_items">
                <div class="query_btn" onclick="loadData(1)">
                    查询
                </div>
            </div>


        </div>
    </div>
</form>

<form id="tbForm">
    <div class="Jry_table" id="testTable">
        <div class="buttons">
        	<div class="btns addnew_btn"  onclick="openAdd()">
	            <div class="imgButton">
	            	<img src="<%=basePath%>/static/jy/img/pcImg/add.png">
	            </div>
            	<div class="textButton">新增</div>
            </div>
        
            <div class="btns delet_btn" onclick="delAll()">
                <div class="imgButton">
                    <img src="<%=basePath%>/static/jy/img/pcImg/delete.png">
                </div>
                <div class="textButton">批量删除</div>
            </div>
            
            <div class="btns delet_btn" style="float:right!important" onclick="refreshDevice()">
                <div class="textButton">刷新设备状态</div>
            </div>
        </div>
        <div class="table_body">
            <input type="hidden" id="isUpperOrg" value="${isUpperOrg}">
            <table id="table">
                <tbody id="deviceList">

                </tbody>
            </table>
        </div>
    </div>
</form>

<div class="navi">
    <form id="naviForm">
        <div class="Jry_page" id="pageCss">
            <input type="hidden" class="pageNow" id="pageNum"/>
            <input type="hidden" class="pageTotal" id="pages"/>
            <input type="hidden" name="pageNum" id="pageNum1"/>
            <input type="hidden" name="pageSize" value="10"/>
            <div class="page_body">
                <div class="prev_page" onclick="proPage()">上一页</div>
                <div class="maps_page"></div>
                <div class="next_page" onclick="nextPage()">下一页</div>
                <div class="jump_page">
                    跳转到<input type="text" maxlength="3">页
                </div>
                <div class="jump_button">GO</div>
            </div>
        </div>
    </form>
</div>

<div class="hide" id="editDiv" style="width:350px">
    <form id="editForm_JRY">
        <div class="Jcustom_title" id="bootTitle_JRY">编辑设备</div>
        <input id="id_JRY" type="hidden" name="id"/>
        <div class="Jcustom_content">
        	<div class="Jcustom_content_items">
				<label>设备IP</label>
				<input type="text" id="ShieldIp_JRY" disabled>
				<div class="error_text"></div>
			</div>
			<div class="Jcustom_content_items">
				<label>子网掩码</label>
				<input type="text" id="ShieldMask_JRY"  disabled>
				<div class="error_text"></div>
			</div>
            <div class="Jcustom_content_items">
				<label>设备网关</label>
				<input type="text" id="ShieldGateway_JRY" disabled>
				<div class="error_text"></div>
			</div>
			<div class="Jcustom_content_items">
				<label>MAC地址</label>
				<input type="text" id="ShieldMac_JRY" disabled>
				<div class="error_text"></div>
			</div>
			<div class="Jcustom_content_items">
				<label>逻辑考场</label>
				<input type="text" id="address_JRY" name="address_JRY" placeholder="请输入物理考场">
				<div class="error_text"></div>
			</div>
			<div class="Jcustom_content_items">
				<label>物理考场</label>
				<input type="text" id="remark_JRY" name="remark_JRY" placeholder="请输入逻辑考场">
				<div class="error_text"></div>
			</div>
			<div class="Jcustom_content_btns">
				<div id="add_btn_JRY" class="okBtn btns" onclick="edit()">确定</div>
				<div class="cancelBtn btns" onclick="CancelPop()">取消</div>
			</div>
        </div>
        

    </form>
</div>

</body>

<script type="text/javascript" src="static/jy/js/common/validate.js"></script>
<script type="text/javascript" src="static/jy/js/shield/shieldList.js<%=version%>"></script>
<script type="text/javascript">
    
</script>
</html>