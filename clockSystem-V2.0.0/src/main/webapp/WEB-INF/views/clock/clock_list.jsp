<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Equipment Page</title>
    <%@ include file="/WEB-INF/views/version.jsp"%>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/jy/css/clock/clockList.css<%=version%>"/>
</head>

<body>

    <input type="hidden" id="basePath" value="<%=basePath%>"/>
    <div class="rightSide">
    	<form id="searchClockForm">
		<div class="Jry_query">
			<div class="query_part">
				<div class="query_items">
					<label>逻辑考场   :</label>
					<div class="inputDivOne">
						 <input name="keyworldClock" id="keyworldClock" type="text" />
					</div>
				</div>
				
				<div class="query_items">
					<label>物理考场   :</label>
					<div class="inputDivOne">
						<input name="clockPort" id="clockPort" type="text" />
					</div>
				</div>
				
				<div class="query_items">
					<label>IP地址   :</label>
					<div class="inputDivOne">
						<input name="clockIp" id="clockIp" type="text" />
					</div>
				</div>
				<div class="query_items">
					<div class="query_btns">
						<div class="query_btn" onclick="queryAll()">
							查询
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	    <form id="tbForm">
	        <div class="Jry_table" id="testTable">
	            
	            <div class="table_body">
	                <input type="hidden" id="isUpperOrg" value="${isUpperOrg}">
	                <table id="table">
	                    <tbody id="deviceList">
	                   
	                    </tbody>
	                </table>
	            </div>
	        </div>
	    </form>
		<div class="debuttons">
	        <div class="btns delet_btn" onclick="delAll()">
	           <div class="textButton">批量删除</div>
	         </div>
	    </div>
	    <div class="navi">
	        <form id="naviForm">
	            <div class="Jry_page" id="pageCss">
	                <input type="hidden" class="pageNow"  id="pageNum" />
	                <input type="hidden" class="pageTotal" id="pages" />
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
	</div>
    <div class="hide" id="seeDiv" style="width:350px">
        <form id="bootForm_JRY">
            <div class="Jcustom_title" id="titleId_JRY"></div>
            <div class="Jcustom_content">
                <br/>
                <div class="Jcustom_content_items">
					<label>IP:</label>
					<span class="ip"></span>
				</div>
				<div class="Jcustom_content_items">
					<label>MAC:</label>
					<span class="mac"></span>
				</div>
				<div class="Jcustom_content_items">
					<label>端口:</label>
					<span class="port"></span>
				</div>
				<div class="Jcustom_content_items">
					<label>秒钟显示:</label>
					<span class="showSec"></span>
				</div>
				<div class="Jcustom_content_items">
					<label>逻辑考场:</label>
					<span class="address"></span>
				</div>
				<div class="Jcustom_content_items">
					<label>物理考场:</label>
					<span class="remark"></span>
				</div>
                <br/>
                <div class="Jcustom_content_btns">
                    <div class="btns" style="background-color:#E40D0D;margin:auto" onclick="CancelPop()">确定</div>
                </div>
            </div>

        </form>
    </div>
	    <div class="hide" id="editDiv" style="width:350px">
        <form id="editForm_JRY">
            <div class="Jcustom_title" id="titleId_JRY"></div>
            <input id="hiddenId" type="hidden" name="id"/>
            <div class="Jcustom_content">
                <br/>
                <div class="Jcustom_content_items">
					<label>IP:</label>
					<input type="text" name="ip_JRY" id="ip_JRY" placeholder="请输入设备IP" maxlength="32" class="required_JRY edit" />
					<div class="error_text"></div>
				</div>
				<div class="Jcustom_content_items">
					<label>MAC:</label>
					<input type="text" name="mac_JRY" id="mac_JRY" placeholder="请输入设备MAC" maxlength="32" disabled class="required_JRY edit" />
					<div class="error_text"></div>
				</div>
				<div class="Jcustom_content_items">
					<label>端口:</label>
					<input type="text" name="port_JRY" id="port_JRY" placeholder="请输入设备端口" maxlength="4" disabled class="required_JRY edit" />
					<div class="error_text"></div>
				</div>
				<div class="Jcustom_content_items">
					<label>秒钟显示:</label>
					<select  name="showSec_JRY" id="showSec_JRY" class="required_JRY edit">
						<option value="1">开启</option>
						<option value="0">关闭</option>
					</select>
					<div class="error_text"></div>
				</div>
				<div class="Jcustom_content_items">
					<label>逻辑考场:</label>
					<input type="text" name="address_JRY" id="address_JRY" placeholder="请输入逻辑考场，最多输入20个字符" maxlength="20" class="required_JRY edit" />
					<div class="error_text"></div>
				</div>
				<div class="Jcustom_content_items">
					<label>物理考场:</label>
					<textarea type="text" name="remark_JRY" id="remark_JRY" placeholder="请输入物理考场，最多输入50个字符" maxlength="50" class=" edit" ></textarea>
					
				</div>
                <br/>
                <div class="Jcustom_content_btns">
                   
                    <div class="okBtn btns" id="editBtn_JRY" onclick="edit()" >确定</div>
					<div class="cancelBtn btns" onclick="CancelPop()">取消</div>
                </div>
            </div>

        </form>
    </div>
</body>
<script type="text/javascript" src="static/jy/js/common/validate.js<%=version%>"></script>
<script type="text/javascript" src="<%=basePath%>static/jy/js/clock/clockList.js<%=version%>"></script>
</html>