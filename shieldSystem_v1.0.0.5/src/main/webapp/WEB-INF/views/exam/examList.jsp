<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>考试列表</title>
<%@ include file="/WEB-INF/views/version.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/jy/css/common/common.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/common/css/bootstrap2.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/common/css/bootstrap-datetimepicker.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/jy/css/exam/examList.css<%=version%>"/>
</head>

<style>
	[class*=" datetimepicker-dropdown"]:after{
		border-bottom: 0;
	}
	[class*=" datetimepicker-dropdown"]:before{
		border-bottom: 0;
	}
</style>

<body>
<div id="exemList">
	<div class="showInfo ">
		<div class="buttonS hide clearfix">
			<div class="fr buttonNew ac CP glyphicon glyphicon-download-alt" onclick="exportExams()">导出</div>
			<div class="fr buttonNew ac CP" onclick="newExam()">新增考试</div>
		</div>
        <form id="searchCriteriaForm">
            <div class="Jry_query">
                <div class="query_part">
                    <div class="query_items">
                        <label>编号</label>
                        <div class="inputDivOne">
                            <input name="examNo" id="examNoSearch" type="text"/>
                        </div>
                    </div>

                    <div class="query_items">
                        <label>考试名称</label>
                        <div class="inputDivOne">
                            <input name="examName" id="examNameSearch" type="text"/>
                        </div>
                    </div>

                    <div class="query_items">
                        <label>开始时间</label>
                        <div class="inputDivOne">
                            <input type="text" id="startTimeSearch" name="startTime" placeholder="开始时间" readonly>
                        </div>
                    </div>

                    <div class="query_items">
                        <label>结束时间</label>
                        <div class="inputDivOne">
                            <input type="text" id="endTimeSearch" name="endTime" placeholder="结束时间" readonly>
                        </div>
                    </div>

                    <div class="query_items">
                    </div>
                    <div class="query_items">
                    </div>
                    <div class="query_items">
                    </div>

                    <div class="query_items">
                        <div class="query_btn" onclick="initTable(1, 10)">
                            查询
                        </div>
                    </div>
                </div>
            </div>
        </form>
		<div id="showExamList">
			
		</div>
	</div>
	
	<div id="newExam" class="hide">
		<div class="Jcustom_title">新增考试</div>
		<div class="Jcustom_content">
			<form class="saveAndEdit_JRY">
			<div class="Jcustom_content_items ">
				<label>考试编号</label>
				<input type="hidden" id="examId_JRY" readonly name="examId_JRY" >
				<input type="text" id="examNo_JRY" disabled="disabled" readonly name="examNo_JRY" placeholder="请输入考试编号">
				<div class="error_text"></div>
			</div>
			<div class="Jcustom_content_items">
				<label>考试计划名称</label>
				<input type="text" id="examName_JRY" name="examName_JRY" placeholder="请输入考试计划名称">
				<div class="error_text"></div>
			</div>
			<!-- <div class="Jcustom_content_items">
				<label>考试计划年份</label>
				<select id="year_JRY" name="year_JRY">
			      <option value="2017" selected>2017</option>
			    </select>
				<div class="error_text"></div>
			</div> -->
			
			<div class="Jcustom_content_items ">
				<label>开始时间</label>
				 <input type="text" id="examStartTime_JRY" name="examStartTime_JRY" placeholder="开始时间" readonly>
				<div class="error_text"></div>
			</div>
			<div class="Jcustom_content_items ">
				<label>结束时间</label>
				<input type="text" id="examEndTime_JRY" name="examEndTime_JRY" placeholder="结束时间" readonly>
				<div class="error_text"></div>
			</div>
			<div class="Jcustom_content_items ">
				<label>考试类型</label>
				<select id="examTypeId_JRY" name="examTypeId">
			      <option value="" selected>请选择考试类型</option>
			    </select>
				<div class="error_text"></div>
			</div>
			<div class="Jcustom_content_items ">
				<label>状态</label>
				<select id="status_JRY" name="status" readonly="readonly" disabled="disabled">
				  <option value="1">启用</option>
				  <option value="2" selected>禁用</option>
				</select>
				<div class="error_text"></div>
			</div>
			<div id="exSub_JRY" class="exSub hide clearfix" data-show="0" onclick="showSub(this)">
				<div class="titleText fl">考试场次管理</div>
				<div class="titleImg fr">
					<img src="<%=basePath%>static/jy/img/pcImg/add.png">
				</div>
			</div>
			<div class="exSubTable_JRY">
				<div class="inputs clearfix">
					<div class="items fl">
						<input type="hidden" id="examSubId_JRY" name="examSubId_JRY" />
						<input type="text" maxlength="3" id="no_JRY" name="no_JRY" placeholder="场次序号"/>
					</div>
					<div class="items fl">
						<input type="text" id="name_JRY" onkeyup="this.value=this.value.replace(/\s+/g,'')" name="name_JRY" placeholder="场次名称"/>
					</div>
					<div class="items fl">
						<input type="text" id="subStartTime_JRY" name="subStartTime_JRY" placeholder="开始时间" readonly/>
					</div>
					<div class="items fl">
						<input type="text" id="subEndTime_JRY" name="subEndTime_JRY" placeholder="结束时间" readonly/>
					</div>
					<div class="items fl" onclick="saveExsub(this)">
						添加
					</div>
				</div>
				<table>
				</table>
			</div>
                <div class="Jcustom_content_items exsubInfo" >
                    <label style="text-align: left">考试场次信息</label>
                </div>
                <div class="exSubTableRead_JRY">
                    <table>
                    </table>
                </div>
			</form>
			
			<div class="Jcustom_content_btns btnsExsub">
				<div class="okBtn btns" onclick="examSave()">确定</div>
				<div class="cancelBtn btns" onclick="examCancel()">取消</div>
			</div>

            <div class="Jcustom_content_btns btnsRead" style="text-align: center">
                <div class="cancelBtn btns" style="display: inline-block;float: inherit;" onclick="examCancel()">取消</div>
            </div>
		</div>
	</div>
	
</div>


</body>
<script type="text/javascript" src="<%=basePath%>/static/common/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/common/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>/static/common/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>static/jy/js/exam/examList.js<%=version%>" charset="UTF-8"></script>


<script>
 
</script>

</html>