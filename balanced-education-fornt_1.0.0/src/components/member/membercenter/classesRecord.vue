<template>
<div id="classesrecord">
	<p class="classesrecord_title l">排序：</p>
	<p class="l ml10 mt36" @click="classesrecordOrder('1')">名称</p>
	<p class="l ml10 mt36" @click="classesrecordOrder('2')">开课日期</p>
	<p class="l ml10 mt36" @click="classesrecordOrder('3')">关注人数</p>
	<p class="l ml10 mt36" @click="classesrecordOrder('4')">播放量</p>
	<hr style="width: 95%;background-color:#E5E5E5;">
	<div class="classesrecord_table">
		<template>
		    <el-table :data="tableData" style="width: 100%">
		      <el-table-column prop="name" label="课程"></el-table-column>
		      <el-table-column prop="startDate" :formatter="timeFormat" label="开课时间"></el-table-column>
		      <el-table-column prop="courseDuration" label="课时"></el-table-column>
		      <el-table-column prop="concernedNum" label="关注人数"></el-table-column>
		      <el-table-column prop="playedNum" label="播放量"></el-table-column>
		    </el-table>
		 </template>
	</div>
	<div class="classesrecord_paging tc">
		<el-pagination v-bind:current-Page="pageIndex" v-bind:page-size="pageSize" :total="total"
	   	layout="total,sizes,prev,pager,next,jumper" v-bind:page-sizes="pageSizes" :current-page="pageIndex"
	    v-on:size-change="sizeChange" v-on:current-change="pageIndexChange"></el-pagination>
	</div>
</div>
</template>

<script>
export default {
     data() {
        return {
          tableData: [/*{
            lesson:'语文',
            lessonStart:'2017-09-02 14:30',
            lessonTime:'1小时',
            followersNumber:'223',
            playNumber:'1000',
          },{
            lesson:'数学',
            lessonStart:'2017-09-01 14:30',
            lessonTime:'1小时',
            followersNumber:'223',
            playNumber:'1000',
          },{
            lesson:'英语',
            lessonStart:'2017-09-02 18:30',
            lessonTime:'1小时',
            followersNumber:'223',
            playNumber:'1000',
          }*/],
          pageIndex:1,
          pageSize:1,
          total:60,
          pageSizes:[1,10,20,50,100],
        }
      },
      created:function(){
      	this.getdata();
      },
      methods: {
      	getdata:function(){
      		this.$emit('newfind');
      		this.postHttp(this,{tab:"",pageNum:1,pageSize:1},"course/findMyCourseOfTeacher",function(obj,data){
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
      	sizeChange: function (pageSize) {   //每页显示条数
	    	this.pageSize=pageSize;
	    	this.fetchData();
	    },
	   pageIndexChange: function (pageIndex) {   //第几页
	      	this.pageIndex=pageIndex;
	    	this.fetchData();
	    },
	    fetchData:function(){
	    	this.postHttp(this,{tab:"name",pageNum:this.pageIndex,pageSize:this.pageSize},"course/findMyCourseOfTeacher",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
			});
	    },
	    classesrecordOrder:function(e){
	    	if(e==1){
	    		this.postHttp(this,{tab:"name",pageNum:1,pageSize:1},"course/findMyCourseOfTeacher",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
				});
	    	}
	    	if(e==2){
	    		this.postHttp(this,{tab:"start_date",pageNum:1,pageSize:1},"course/findMyCourseOfTeacher",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
				});
	    	}
	    	if(e==3){
	    		this.postHttp(this,{tab:"concerned_num",pageNum:1,pageSize:1},"course/findMyCourseOfTeacher",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
				});
	    	}
	    	if(e==4){
	    		this.postHttp(this,{tab:"played_num",pageNum:1,pageSize:1},"course/findMyCourseOfTeacher",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
				});
	    	}
	    }
    },
    }
</script>

<style>

#classesrecord .classesrecord_title{
	margin-top: 36px;
	margin-left: 40px;
	font-size: 16px;
	color: #999999;
}
#classesrecord .mt36{
	margin-top: 36px;
	font-size: 16px;
}
#classesrecord .classesrecord_table{
	margin: 0 auto;
	margin-top: 20px;
	width: 95%;
	min-height: 360px;
}
#classesrecord .classesrecord_paging{
	margin-top: 40px;
	margin-bottom: 40px;
}
</style>