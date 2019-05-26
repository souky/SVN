<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Data Table Manager</title>
    <%@ include file="/WEB-INF/spf/header.jsp" %>
    <script type="text/javascript">
        $(function () {
            //下拉框赋值
            $("input[name='javaType']").parent("td").find("ul li a").on('click', function () {
                var aVal = $(this).text();
                $(this).parents('div').prev('input').val(aVal);
                $(this).parents('div').parents('td').children('input').val(aVal)
            });

            $("input[name='columnName']").on('blur', function () {
                var columnName = $(this).val();
                var javaPropretyInput = $(this).parent("td").parent("tr").find("input[name='javaProprety']");
                columnName = columnName.toLowerCase();
                var columnNames = columnName.split("_");
                if (1 >= columnNames.length) {
                    javaPropretyInput.val(columnName);
                } else {
                    var javaProprety = columnNames[0];
                    for (var i = 1; i < columnNames.length; i++) {
                        javaProprety += (columnNames[i].substring(0, 1).toUpperCase() + columnNames[i].substring(1));
                    }
                    javaPropretyInput.val(javaProprety);
                }
            });

            $("input[name='columnType']").parent("td").find("ul li a").on('click', function () {
            	var columnType = $(this).text();
                $(this).parents('div').prev('input').val(columnType);
                $(this).parents('div').parents('td').children('input').val(columnType);
                var javaType = $(this).parents("tr").find("input[name='javaType']");
                var javaTypeTd = javaType.parent("td");
                var inputText = javaTypeTd.find(".form-control");
            	if (columnType.indexOf('varchar') > -1) {
            		javaType.val("String");
            		inputText.val("String");
            	} else if (columnType.indexOf('int') > -1) {
            		javaType.val("int");
            		inputText.val("int");
            	} else if (columnType.indexOf('decimal') > -1){
            		javaType.val("BigDecimal");
            		inputText.val("BigDecimal");
            	} else if (columnType.indexOf('datetime') > -1) {
            		javaType.val("Date");
            		inputText.val("Date");
            	} else {
            		javaType.val("");
            		inputText.val("");
            	}
            	
            });

        });

        //添加一行记录
        function addTr(obj) {
            //添加元素之后绑定时间无效，需要为新添加的元素再次绑定
            $("table tbody").children("tr").eq(0).clone(true).insertAfter($("table tbody tr:eq(" + ($("table tbody").children("tr").length - 1) + ")"));
        }

        //删除一行记录
        function removeTr(obj) {

            var trNum = $('.table tbody tr').length;
            if (1 != trNum) {
                $(obj).parents('tr').remove();
            }
        }
    </script>
</head>
<body>
<form action="<%=basePath%>/code/createCode.do"
      class="container form-horizontal" method="post">
    <section>
        <div class="titleInfo">
            <span> 基本信息 </span>
        </div>
        <div class="form-group ">
            <label for="name" class="col-sm-2 control-label">表名</label>
            <div class="col-sm-5">
                <input type="text" id="tableName" name="tableName" value=""
                       class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label for="kind" class="col-sm-2 control-label">包名</label>
            <div class="col-sm-5">
                <input type="text" id="packageName" value="" name="packageName"
                       class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label for="kind" class="col-sm-2 control-label">对象</label>
            <div class="col-sm-5">
                <input type="text" id="objectName" value="" name="objectName"
                       class="form-control"/>
            </div>
        </div>
        <div class="form-group ">
            <label for="describe" class="col-sm-2 control-label">说明</label>
            <div class="col-sm-5">
                <input type="text" name="remark" id="remark" value=""
                       class="form-control"/>
            </div>
        </div>
    </section>
    <section>
        <div class="titleInfo">
            <span> 字段列表 </span>
        </div>
        <table class="table" model="genFiledsEntities">
            <thead>
            <tr>
                <th class="colTh">列名</th>
                <th class="colTh">说明</th>
                <th class="colTh">物理类型</th>
                <th class="colTh">默认值</th>
                <th class="colTh">java类型</th>
                <th class="colTh">java属性名称</th>
                <!-- 					         <th>主键</th> -->
                <!-- 					         <th>可空</th> -->
                <!-- 					         <th>插入</th> -->
                <!-- 					         <th>编辑</th> -->
                <!-- 					         <th>列表</th> -->
                <!-- 					         <th>查询</th> -->
                <!-- 					         <th class="colTh">查询方式</th> -->
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><input type="text" class="form-control" value=""
                           name="columnName"></td>
                <td><input type="text" class="form-control" value=""
                           name="remark"></td>
                <td>
                	<input type="hidden" name="columnType">
                    <div class="input-group">
                        <input type="text" class="form-control">
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-default dropdown-toggle"
                                    data-toggle="dropdown">
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu pull-right">
                                <li><a href="#">varchar(64)</a></li>
                                <li><a href="#">int(10)</a></li>
                                <li><a href="#">bigint(10)</a></li>
                                <li><a href="#">tinyint(1)</a></li>
                                <li><a href="#">decimal(5,2)</a></li>
                                <li><a href="#">datetime</a></li>
                            </ul>
                        </div>
                    </div>
                </td>
                <td><input type="text" class="form-control" id="defaultType" name="columnDefault"></td>
                <td>
                	<input type="hidden" name="javaType">
                    <div class="input-group">
                        <input type="text" class="form-control">
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-default dropdown-toggle"
                                    data-toggle="dropdown">
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu pull-right">
                                <li><a href="#">String</a></li>
                                <li><a href="#">Date</a></li>
                                <li><a href="#">int</a></li>
                                <li><a href="#">BigDecimal</a></li>
                            </ul>
                        </div>
                    </div>
                </td>
                <td><input type="text" class="form-control" value="" name="javaProprety"></td>
                <!-- 								<td> -->
                <!-- 									<input type="checkbox" name="primaryKey" value="1"> -->
                <!-- 								</td> -->
                <!-- 								<td> -->
                <!-- 									<input type="checkbox" name="required" value="1"> -->
                <!-- 								</td> -->
                <!-- 								<td> -->
                <!-- 									<input type="checkbox" name="insert" value="1"> -->
                <!-- 								</td> -->
                <!-- 								<td> -->
                <!-- 									<input type="checkbox" name="edit" value="1"> -->
                <!-- 								</td> -->
                <!-- 								<td> -->
                <!-- 									<input type="checkbox" name="list" value="1"> -->
                <!-- 								</td> -->
                <!-- 								<td> -->
                <!-- 									<input type="checkbox" name="query" value="1"> -->
                <!-- 								</td> -->
                <!-- 								<td> -->
                <!-- 								 	<input type="hidden" name="queryType"> -->
                <!-- 									<div class="input-group"> -->
                <!-- 						               <input type="text" class="form-control"> -->
                <!-- 						               <div class="input-group-btn"> -->
                <!-- 						                  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"> -->
                <!-- 						                     <span class="caret"></span> -->
                <!-- 						                  </button> -->
                <!-- 						                  <ul class="dropdown-menu pull-right"> -->
                <!-- 						                     <li><a href="#">=</a></li> -->
                <!-- 						                     <li><a href="#">like</a></li> -->
                <!-- 						                     <li><a href="#">between</a></li> -->
                <!-- 						                  </ul> -->
                <!-- 						               </div> -->
                <!-- 						            </div> -->
                <!-- 								</td> -->
                <td>
                    <button type="button" class="btn btn-default" id="add"
                            onclick="addTr(this)">添加
                    </button>
                    <button type="button" class="btn btn-default" id="delete"
                            onclick="removeTr(this)">删除
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </section>
    <button type="button" class="btn btn-default" id="save"
            onclick="saveGen()">保存
    </button>
    <button type="button" class="btn btn-default" id="goback">返回</button>
</form>
</body>
<script type="text/javascript">
    function saveGen() {

        var data = [];
        var tbody = $("table tbody");
        var objName = $("table").attr("model");
        var trs = tbody.children("tr");
        for (var i = 0; i < trs.length; i++) {
            var tr = trs.eq(i);
            var tds = tr.children("td");
            var genFiledsEntity = {};
            for (var j = 0; j < tds.length; j++) {
                var td = tds.eq(j);
                var name = td.children('input').eq(0).attr('name');
                if (typeof(name) != "undefined") {
                    if ("checkbox" == td.children('input').attr("type").toLowerCase()) {
                        if ($("[name='" + name + "']")[0].checked) {
                            eval("genFiledsEntity." + name + "='1'");
                        } else {
                            eval("genFiledsEntity." + name + "='0'");
                        }
                    } else {
                        eval("genFiledsEntity." + name + "='" + td.children('input').eq(0).val() + "'");
                    }
                }
            }
            data.push(genFiledsEntity);
        }
        var tableName = $("#tableName").val();
        var objName = $("#objectName").val();
        var remark = $("#remark").val();
        var packageName = $("#packageName").val();
        
        if ('' ==$.trim(tableName)) {
        	alert('表名称无效');
        	return;
        }
        
        if ('' == $.trim(packageName)) {
        	alert('包名无效');
        	return;
        }
        
        if ('' == $.trim(objName)) {
        	alert('对象名称无效');
        	return;
        }
        
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "<%=basePath%>/code/createCode.do",
            data: JSON.stringify({
                tableName: tableName,
                objectName: objName,
                packageName: packageName,
                remark:remark,
                genFiledsEntities: data
            }),
            success: function (result) {
                location.href = '<%=basePath%>user/ftl/code/' + packageName + '.zip';
            }
        });

    }
</script>
</html>