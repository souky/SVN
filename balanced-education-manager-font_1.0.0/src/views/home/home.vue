
<template>
  <div id="home" class="home-container main-container">
    <!-- 标题 -->
    <p class="f16 lh40">节目单</p>
    <!-- 表格 -->
    <div class="lh40 f14 showall">显示全部</div>
		<el-table
    	:data="tableData"
	    stripe
	    style="width:100%"
	    >
	    <el-table-column
	      prop="schoolName"
	      label="学校"
	      align="center"
	      >
	    </el-table-column>
	    <el-table-column
	      prop="sourceName"
	      label="录制源"
	      align="center"
	      show-overflow-tooltip>
	    </el-table-column>
	    <el-table-column
	      prop="name"
	      label="节目名"
	      align="center"
	      width=""
	      show-overflow-tooltip
	      >
	    </el-table-column>
	    <el-table-column
	      prop="type"
	      label="类型"
	      align="center"
	      >
	    </el-table-column>
	    <el-table-column
	      prop="startDate"
	      label="开始时间"
	      align="center">
	    </el-table-column>
	    <el-table-column
	      prop="endDate"
	      label="结束时间"
	      align="center">
	    </el-table-column>
  	</el-table>
  	
    <!-- 标题 -->
    <p class="f16 lh40">统计图</p>
    <div class="echarts">
      <z-charts></z-charts>
    </div>

    <!-- 标题 -->
    <p class="f16 lh40">课程资源统计</p>
    <!-- 表格 -->
		<el-tabs v-model="activeName">
	    <el-tab-pane label="按年级" name="first">
	    	<div class="fix" id="table_st_grade">
	    		
	    	</div>
	    </el-tab-pane>
	    <el-tab-pane label="按学科" name="second">
	    	<div class="fix" id="table_st_subject">
	    		
	    	</div>
	    	
	    </el-tab-pane>
	  </el-tabs>
  </div>
  
</template>
<script>
  import ZCharts from './components/charts-first';
	var moment = require('moment');
  export default{
    data () {
      return {
        msg:'hello vue',
        tableData:[],
        tableData_grand:[],
        activeName: 'first'
      }
    },
    components: { ZCharts},
    mounted:function(){
    	var data = {pageNum:'1',pageSize:'10'}
    	this.postHttp(this,data,"program/findPrograms",findProgram);
    	this.postHttp(this,data,"statistics/queryCourseResourceByGrade",function(obj,data){
    		var list = data.result;
    		var html = '<div class="items_grade l"><div class="item_one tc">资源类型</div><div class="item_two tc">课程</div><div class="item_three tc">课堂视频</div><div class="item_four tc">教辅文件</div></div>';
    		if(list){
    			var size = list.length +1;
    			var widthP = (100/size) + "%";
    			for(var i = 0;i<list.length;i++){
    				var o = list[i];
    				html += '<div class="items_grade l">';
    				html += '<div class="item_one tc">'+o.resourseType+'</div>';
    				html += '<div class="item_two tc">'+o.courseNum+'</div>';
    				html += '<div class="item_three tc">'+o.videoNum+'</div>';
    				html += '<div class="item_four tc">'+o.teachingFileNum+'</div>';
    				html += '</div>';
    			}
    			document.getElementById("table_st_grade").innerHTML = html;
    			var doc_list = document.getElementsByClassName("items_grade");
    			for(var s = 0;s<doc_list.length;s++){
    				doc_list[s].style.width = widthP
    			}
    		}else{
    			document.getElementById("table_st_grade").innerHTML = html;
    		}
    		
    	})
    	this.postHttp(this,data,"statistics/queryCourseResourceBySubject",function(obj,data){
    		var list = data.result;
    		var html = '<div class="items_subject l"><div class="item_one tc">资源类型</div><div class="item_two tc">课程</div><div class="item_three tc">课堂视频</div><div class="item_four tc">教辅文件</div></div>';
    		if(list){
    			var size = list.length +1;
    			var widthP = (100/size) + "%";
    			for(var i = 0;i<list.length;i++){
    				var o = list[i];
    				html += '<div class="items_subject l">';
    				html += '<div class="item_one tc">'+o.resourseType+'</div>';
    				html += '<div class="item_two tc">'+o.courseNum+'</div>';
    				html += '<div class="item_three tc">'+o.videoNum+'</div>';
    				html += '<div class="item_four tc">'+o.teachingFileNum+'</div>';
    				html += '</div>';
    			}
    			document.getElementById("table_st_subject").innerHTML = html;
    			var doc_list = document.getElementsByClassName("items_subject");
    			for(var s = 0;s<doc_list.length;s++){
    				doc_list[s].style.width = widthP
    			}
    		}else{
    			document.getElementById("table_st_subject").innerHTML = html;
    		}
    	})
    }
      
  }
  function findProgram(obj,data){
  	var lists = data.result.list;
		for(var i = 0 ;i<lists.length;i++){
			var startDates = moment(lists[i].startDate).format("YYYY-MM-DD HH:mm:ss");
			var endDates = moment(lists[i].endDate).format("YYYY-MM-DD HH:mm:ss");
			lists[i].startDate = startDates;
			lists[i].endDate = endDates;
		}
      obj.tableData = lists;
  }
  
  
</script>


<style>
  #home .echarts {
    width: 100%;
    height: 145px;
    background: #fff;
  }
  #home .showall{
  	background:#fff;
  	padding-right:20px;
  	text-align: right;
  	cursor: pointer;
  }
	#home .cell{
		display: block;
	}
	
	#home{background: #fafafa;}
	
	#home .el-tabs__header{margin:0px;}
	#home .el-tabs__nav{width:100%;background: #fff;}
	#home .el-tabs__active-bar{background:#66BB6A;width:100px;}
	#home .el-tabs__item{color:#272727;text-align: center;height: 50px;line-height: 50px;width:100px;}
	#home .el-tabs__item.is-active{color: #66BB6A;}
	
	#home .tc{
		height: 40px;line-height: 40px;font-size:14px;color:#272727;
	}
	#home .item_two{background: #fff;}
	#home .item_four{background: #fff;}
</style>