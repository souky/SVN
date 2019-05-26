<template>
	<div id="studyrecord">
		<div class="studyrecord_header fix">
			<div class="studyrecord_header_title active l" @click="choiseType('ALL',$event)"><p class="all">全部</p></div>
			<div class="studyrecord_header_title l" @click="choiseType('UNDERWAY',$event)"><p class="studing">正在学</p></div>
			<div class="studyrecord_header_title l" @click="choiseType('NOT_LEARNED',$event)"><p class="studed">未学</p></div>
			<div class="studyrecord_header_title l" @click="choiseType('FINISHED',$event)"><p class="over">已学完</p></div>
			<div class="line-cross l"></div>
		</div>
		<div class="studyrecord_body">
		 <template>
		    <el-table :data="tableData" style="width: 100%">
		      <el-table-column prop="courseName" align="center" label="课程"></el-table-column>
		      <el-table-column prop="schoolName" align="center" label="学校"></el-table-column>
		      <el-table-column prop="teacherName" align="center" label="老师"></el-table-column>
		      <el-table-column prop="progress" :formatter="progressFormat" align="center" label="进度"></el-table-column>
		      <el-table-column prop="lastStudiedFileName" align="center" label="最近学习"></el-table-column>
		      <el-table-column prop="startDate" :formatter="timeFormat" align="center" label="开课日期"></el-table-column>
		      <el-table-column prop="courseDuration" align="center" label="课时"></el-table-column>
		    </el-table>
		 </template>
		</div>
		<div class="studyrecord_paging tc">
			<el-pagination v-bind:current-Page="pageIndex" v-bind:page-size="pageSize" :total="total"
			   	layout="total,sizes,prev,pager,next,jumper" v-bind:page-sizes="pageSizes" :current-page="pageIndex"
			    v-on:size-change="sizeChange" v-on:current-change="pageIndexChange"></el-pagination>
		</div>
	</div>
</template>
<script>
import page from '../page.vue'
export default{
	data(){
		return{
			tableData:[/*{
				lesson:'语文',
				school:'苏州小学',
				teacher:'James',
				progress:'正在学',
				lastsuty:'小学语文',
				start:'2017-09-03',
				lessontime:'2小时',
			},{
				lesson:'数学',
				school:'苏州小学',
				teacher:'James',
				progress:'正在学',
				lastsuty:'小学语文',
				start:'2017-09-03',
				lessontime:'2小时',
			},{
				lesson:'英语',
				school:'苏州小学',
				teacher:'Tom',
				progress:'正在学',
				lastsuty:'小学语文',
				start:'2017-09-03',
				lessontime:'2小时',
			}*/],
			pageIndex:1,
          	pageSize:1,
          	total:60,
          	types:"ALL",
          	pageSizes:[1,10,20,50,100],
		}
	},
	created:function(){
      this.getdata();
    },
	components:{page},
	methods:{
		getdata:function(){
			this.$emit('newfind');
			this.postHttp(this,{tab:"ALL",pageNum:1,pageSize:1},"studiedrecord/getStudiedRecList",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
			});
		},
		timeFormat(row,column){
		  	var date = row[column.property]; 
		  	if (date == undefined) {  
		     return "";  
		  	}  
		  	return this.timeF(date).format("YYYY-MM-DD HH:mm:ss");  
		},
		progressFormat:function(row,column){
			var progress = row[column.property];
			return progress+"%";
		},
		choiseType:function(type,event){
			var obj = event.currentTarget;
			var reg = new RegExp('(\\s|^)active(\\s|$)'); 
			var list = document.getElementsByClassName("studyrecord_header_title");
			for(var i = 0;i<list.length;i++){
		  		var e = list[i];
		  		e.className = e.className.replace(reg,' ');
		  	}
		  	obj.className += ' active';
		  	if(type=='ALL'){
		  		this.postHttp(this,{tab:"ALL",pageNum:this.pageIndex,pageSize:this.pageSize},"studiedrecord/getStudiedRecList",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
				});
		  	}
		  	if(type=='UNDERWAY'){
		  		this.postHttp(this,{tab:"UNDERWAY",pageNum:this.pageIndex,pageSize:this.pageSize},"studiedrecord/getStudiedRecList",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
				});
		  	}
		  	if(type=='NOT_LEARNED'){
		  		this.postHttp(this,{tab:"NOT_LEARNED",pageNum:this.pageIndex,pageSize:this.pageSize},"studiedrecord/getStudiedRecList",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
				});
		  	}
		  	if(type=='FINISHED'){
		  		this.postHttp(this,{tab:"FINISHED",pageNum:this.pageIndex,pageSize:this.pageSize},"studiedrecord/getStudiedRecList",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
				});
		  	}
		  	this.pageIndex=1;
		  	this.types=type;
		},
		sizeChange: function (pageSize) {   //每页显示条数
	      this.pageSize = pageSize;
	      this.fetchData();
	    },
	    pageIndexChange: function (pageIndex) {   //第几页
	      this.pageIndex = pageIndex;
	      this.fetchData();
	    },
	    fetchData:function(){
	    	this.postHttp(this,{tab:this.types,pageNum:this.pageIndex,pageSize:this.pageSize},"studiedrecord/getStudiedRecList",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
				});
	    },
	},
	
}

</script>
<style>
#studyrecord{
	overflow: hidden;
}
#studyrecord .studyrecord_header{
	width: 95%;
	margin:34px auto 20px auto;
}
#studyrecord .ml42{
	margin-left: 42px;
}
#studyrecord .all:hover{
	color:#6ED56C;
}
#studyrecord .studing:hover{
	color:#6ED56C;
}
#studyrecord .studed:hover{
	color:#6ED56C;
}
#studyrecord .over:hover{
	color:#6ED56C; 
}
#studyrecord .studyrecord_header_title{
	width: 10%;
	height:30px;
	line-height: 30px;
	border-bottom: 2px #e5e5e5 solid;
	text-align:center;
	cursor:pointer;
}
#studyrecord .studyrecord_header{
	position: relative;
}
#studyrecord .studyrecord_header .active{
	height: 30px;
	color:#6ED56C; 
	border-bottom:2px solid #6ED56C;
}
#studyrecord .studyrecord_header .line-cross{
	width:60%;
	height:30px;
	border-bottom:2px solid #e5e5e5;
	z-index:0;
}
#studyrecord .studyrecord_body{
	margin: 0 auto;
	width: 95%;
	min-height: 360px;
}
#studyrecord .studyrecord_paging{
	margin-top: 40px;
	margin-bottom: 40px;
}
</style>