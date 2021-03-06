<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
		<meta charset="UTF-8">
		<title>表单管理</title>
		<%@ include file="/WEB-INF/spf/header.jsp"%>
	</head>
<body>
		<div class="margin50">
			<table class="table table-bordered  table-condensed" name="requireAttr">
				   <tbody>
						<tr>
							<td class="width-15 active">
								<label class="pull-right"><span class="red">*</span>表名:</label>
							</td>
							<td class="width-35">
								<input id="name" name="name" class="form-control" type="text" value="" maxlength="200" aria-required="true">
							</td>
							<td class="width-15 active">
								<label class="pull-right"><span class="red">*</span>说明:</label>
							</td>
							<td class="width-35">
								<input id="comments" name="comments" class="form-control" type="text" value="" maxlength="200" aria-required="true">
							</td>
						</tr>
						<tr>
							<td class="width-15 active">
								<label class="pull-right">表类型</label>
							</td>
							<td class="width-35">
								<select id="tableType" name="tableType" class="form-control m-b">
									<option value="0">单表</option>
									<option value="1">主表</option>
									<option value="2">附表</option>
									<option value="3">树结构表</option>
								</select>
								<span class="help-inline">如果是附表，请指定主表表名和当前表的外键</span>
							</td>
							<td class="width-15 active">
								<label class="pull-right"><span class="red">*</span>类名:</label>
							</td>
							<td class="width-35">
								<input id="className" name="className" class="form-control" type="text" value="" maxlength="200" aria-required="true">
							</td>						
						</tr>
						<tr>
							<td class="width-15 active">
								<label class="pull-right">主表表名:</label>
							</td>
							<td class="width-35">
								<select id="parentTable" name="parentTable" class="form-control">
									<option value="">无</option>
									<option value="4rtdfgfg">4rtdfgfg  :  ffgfg</option>
									<option value="act_hi_detail">act_hi_detail  :  act_hi_detail</option>
								</select>
							</td>
							<td class="width-15 active">
								<label class="pull-right">当前表外键：</label>
							</td>
							<td class="width-35">
								<input id="parentTableFk" name="parentTableFk" class="form-control" type="text" value="" maxlength="200">
							</td>
						</tr>					
					</tbody>
			</table>
			<div>
				<button class="btn btn-default btn-sm"><span class="glyphicon glyphicon-plus"></span> 增加</button>
				<button class="btn btn-default btn-sm"><span class="glyphicon glyphicon-minus"></span> 删除</button>
			</div>
			<div class="tabs-container">
				<ul class="nav nav-tabs">
                    <li class="active">
                        <a data-toggle="tab" href="#tab-1" aria-expanded="true"> 数据库属性</a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#tab-2" aria-expanded="false">页面属性</a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#tab-4" aria-expanded="false">页面校验</a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#tab-3" aria-expanded="false">grid选择框（自定义java对象）</a>
                    </li>                     
                </ul>
                <div class="tab-content">
                	<div id="tab-1" class="tab-pane active">
                        <div class="panel-body">
                            <table id="contentTable1" class="table table-striped table-bordered table-hover">
                                <thead>
									<tr>
										<th class="width40">序号</th>
										<th>操作</th>
										<th title="数据库字段名">列名</th>
										<th title="默认读取数据库字段备注">说明</th>
										<th title="数据库中设置的字段类型及长度">物理类型</th>
										<th title="是否是数据库主键">主键</th>
									</tr>
								</thead>
								<tbody>							
								<!-- id -->
								<tr>
									<td class="bold">
										0
									</td>
									<td>
										<span class="checkboxImg"></span>
									</td>
									<td name="id">
										<input type="text" class="form-control" value="id">
									</td>
									<td name="主键">
										<input type="text" class="form-control" value="主键" maxlength="200">
									</td>
									<td>
										<select class="form-control">
											<option value="">无</option>
											<option value="varchar(64)">varchar(64)</option>
											<option value="integer">integer</option>
										</select>
									</td>
									<td>
										<span class="checkboxImg"></span>	
									</td>
								</tr>
							</tbody>
						</table>
                            </div>
                        </div>
                	<div id="tab-2" class="tab-pane">
                            <div class="panel-body">
                                 <table id="contentTable2" class="table table-striped table-bordered table-hover table-condensed">
                              <thead>
							<tr>
								<th title="数据库字段名" class="width-15">列名</th>
								<th title="默认读取数据库字段备注">说明</th>
								<th title="实体对象的属性字段类型" class="width-15">Java类型</th>
								<th title="实体对象的属性字段（对象名.属性名|属性名2|属性名3，例如：用户user.id|name|loginName，属性名2和属性名3为Join时关联查询的字段）">Java属性名称</th>
								<th title="选中后该字段被加入到查询列表里">表单</th>
								<th title="选中后该字段被加入到查询列表里">列表</th>
								<th title="选中后该字段被加入到查询条件里">查询</th>
								<th title="该字段为查询字段时的查询匹配放松" class="width-15">查询匹配方式</th>
								<th title="字段在表单中显示的类型" class="width-15">显示表单类型</th>
								<th title="显示表单类型设置为“下拉框、复选框、点选框”时，需设置字典的类型">字典类型</th>
							</tr>
							</thead>
							<tbody>													
								<!-- id -->
								<tr>
									<td>
										<input type="text" class="form-control" readonly="readonly" value="id">
									</td>
									<td>
										<input type="text" class="form-control" value="主键" maxlength="200" readonly="readonly">
									</td>
									<td>
										<select class="form-control" aria-required="true">											
												<option value="String" selected="" title="">String</option>											
												<option value="Long" title="">Long</option>											
												<option value="Integer" title="">Integer</option>											
												<option value="Double" title="">Double</option>											
												<option value="java.util.Date" title="">Date</option>										
												<option value="com.jeeplus.modules.sys.entity.User" title="">User</option>										
												<option value="com.jeeplus.modules.sys.entity.Office" title="">Office</option>										
												<option value="com.jeeplus.modules.sys.entity.Area" title="">Area</option>										
												<option value="This" title="生成当前对象">ThisObj</option>										
												<option value="Custom" class="newadd">自定义输入</option>
										</select>
									</td>
									<td>
										<input type="text"value="id" maxlength="200" class="form-control" aria-required="true">
									</td>
									<td>
										<span class="checkboxImg"></span>
									</td>
									<td>
										<span class="checkboxImg"></span>
									</td>
									<td>
										<span class="checkboxImg"></span>
									</td>
									<td>
										<select class="form-control" aria-required="true">											
												<option value="=" title="">=</option>										
												<option value="!=" title="">!=</option>											
												<option value=">" title="">&gt;</option>											
												<option value=">=" title="">&gt;=</option>											
												<option value="<" title="">&lt;</option>											
												<option value="<=" title="">&lt;=</option>											
												<option value="between" title="">Between</option>										
												<option value="like" title="">Like</option>											
												<option value="left_like" title="">Left Like</option>											
												<option value="right_like" title="">Right Like</option>										
										</select>
									</td>
									<td>
										<select class="form-control" aria-required="true" aria-invalid="false">										
												<option value="input" title="">单行文本</option>											
												<option value="textarea" title="">多行文本</option>											
												<option value="umeditor" title="">富文本编辑器</option>											
												<option value="select" title="">下拉选项</option>											
												<option value="radiobox" title="">单选按钮</option>											
												<option value="checkbox" title="">复选框</option>											
												<option value="dateselect" title="">日期选择</option>											
												<option value="userselect" title="">人员选择</option>											
												<option value="officeselect" title="">部门选择</option>											
												<option value="areaselect" title="">区域选择</option>											
												<option value="treeselect" title="">树选择控件</option>											
												<option value="fileselect" title="">文件上传选择</option>											
												<option value="gridselect" title="">grid选择框(自定义java对象)</option>										
										</select>
									</td>
									<td>
										<input type="text" maxlength="200" class="form-control">
									</td>
									
								</tr>
							</tbody>
						</table>
                            </div>
                        </div>
                	<div id="tab-3" class="tab-pane">
                            <div class="panel-body">
                                 <table id="contentTable3" class="table table-striped table-bordered table-hover table-condensed">
                              <thead>
							<tr>
								<th title="数据库字段名" class="width-15">列名</th>
								<th title="默认读取数据库字段备注">说明</th>
								<th title="实体对象的属性字段类型" class="width-15">table表名</th>
								<th title="实体对象的属性字段说明（label1|label2|label3，用户名|登录名|角色）">JAVA属性说明</th>
								<th title="选中后该字段被加入到查询列表里">JAVA属性名称</th>
								<th title="选中后该字段被加入到查询列表里">检索标签</th>
								<th title="选中后该字段被加入到查询条件里">检索key</th>
							</tr>
							</thead>
							<tbody>												
								<!-- id -->
								<tr>
									<td>
										<input type="text" class="form-control" readonly="readonly" name="page-columnList[0].name" value="id">
									</td>
									<td>
										<input type="text" class="form-control" name="page-columnList[0].comments" value="主键" maxlength="200" readonly="readonly">
									</td>
									<td>
										<input type="text" name="columnList[0].tableName" value="" maxlength="200" class="form-control">
									</td>
									<td>
										<input type="text" name="columnList[0].fieldLabels" value="" maxlength="200" class="form-control">
									</td>
									<td>
										<input type="text" name="columnList[0].fieldKeys" value="" maxlength="200" class="form-control">
									</td>
									<td>
										<input type="text" name="columnList[0].searchLabel" value="" maxlength="200" class="form-control">
									</td>
									<td>
										<input type="text" name="columnList[0].searchKey" value="" maxlength="200" class="form-control">
									</td>																		
								</tr>
							</tbody>
						</table>
                    </div>
                </div>
            		<div id="tab-4" class="tab-pane">
                    <div class="panel-body">
                        <table id="contentTable4" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                           <thead>
							<tr>
								<th title="数据库字段名" class="width-15">列名</th>
								<th title="默认读取数据库字段备注">说明</th>
								<th title="字段是否可为空值，不可为空字段自动进行空值验证">可空</th>
								<th title="校验类型">校验类型<i class="icon-question-sign"></i></th>
								<th title="最小长度">最小长度</th>
								<th title="最大长度">最大长度</th>
								<th title="最小值">最小值</th>
								<th title="最大值">最大值</th>
							</tr>
							</thead>
							<tbody><!-- id -->
								<tr>
									<td>
										<input type="text" class="form-control" readonly="readonly" name="page-columnList[0].name" value="id">
									</td>
									<td>
										<input type="text" class="form-control" name="page-columnList[0].comments" value="主键" maxlength="200" readonly="readonly">
									</td>
									<td>
										<span class="checkboxImg"></span>
									</td>
									<td>
										<select name="columnList[0].validateType" class="form-control">											
												<option value="" title=""></option>										
												<option value="string" title="">字符串</option>											
												<option value="email" title="">电子邮件</option>											
												<option value="url" title="">网址</option>											
												<option value="date" title="">日期</option>											
												<option value="dateISO" title="">日期(ISO)</option>											
												<option value="number" title="">数字</option>											
												<option value="digits" title="">整数</option>											
												<option value="creditcard" title="">信用卡号</option>											
										</select>
									</td>
									<td>
										<input type="text" name="columnList[0].minLength" value="" maxlength="200" class="form-control">
									</td>
									<td>
										<input type="text" name="columnList[0].maxLength" value="" maxlength="200" class="form-control">
									</td>
									<td>
										<input type="text" name="columnList[0].minValue" value="" maxlength="200" class="form-control">
									</td>
									<td>
										<input type="text" name="columnList[0].maxValue" value="" maxlength="200" class="form-control">
									</td>									
								</tr>
							</tbody>
						</table>
                    </div>
                </div>
            </div>
	    </div>
	</div>		

</body>
</html>