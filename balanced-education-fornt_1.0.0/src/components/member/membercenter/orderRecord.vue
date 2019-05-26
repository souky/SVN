<template>
	<div id="orderrecord">
		<p class="orderrecord_title">预约记录</p>
		<hr style="width: 95%;background-color:#E5E5E5;">
		<div class="orderrecord_body">
			<el-form ref="form" :model="form" label-width="80px">
				<el-form-item class="l" label="节目状态">
				    <el-select v-model="form.region" placeholder="请选择节目状态">
				      <el-option v-for="item in Sregion" :key="item.id" :label="item.regionName" :value="item.id"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item class="l ml30" label="活动时间">
				    <el-col :span="11">
				      <el-date-picker v-model="form.date1" type="datetime" placeholder="选择开始日期时间" style="width: 100%;" @change="dateChange1"></el-date-picker>
				    </el-col>
				    <el-col class="tc" :span="2">——</el-col>
				    <el-col :span="11">
				      <el-date-picker v-model="form.date2" type="datetime" placeholder="选择结束日期时间" style="width: 100%;" @change="dateChange2"></el-date-picker>
				    </el-col>
				  </el-form-item>
			</el-form>
			<el-button class="r query_button" type="primary" @click="query">查询</el-button>
			<div class="cl"></div>
			<div class="mt15">
			<template>
			    <el-table :data="tableData" style="width: 100%">
			      <el-table-column prop="programName" align="center" label="节目名"></el-table-column>
			      <el-table-column prop="programStartDate" :formatter="timeFormat" align="center" label="直播时间"></el-table-column>
			      <el-table-column prop="courseName" align="center" show-overflow-tooltip label="课程名"></el-table-column>
			      <el-table-column prop="schoolName" align="center" label="学校"></el-table-column>
			      <el-table-column prop="teacherName" align="center" label="教师"></el-table-column>
			      <el-table-column prop="status" align="center" :formatter="statusFormat" label="节目状态"></el-table-column>
			      <el-table-column props="isCancelled" align="center" label="操作">
			      	<template scope="scope">
			            <el-button v-if="scope.row.status=='1_ONGOING'" @click.native.prevent="toView(scope.$index,tableData)" class="table-button" type="text" size="small">取消关注</el-button>
			            <el-button v-if="scope.row.status=='2_NOT_STARTED'" @click.native.prevent="toView(scope.$index,tableData)" class="table-button" type="text" size="small">取消关注</el-button>
			            <el-button v-if="scope.row.status=='3_ENDED'" @click.native.prevent="del(scope.$index,tableData)" class="table-button" type="text" size="small">删除记录</el-button>
			            <el-button v-if="scope.row.status=='4_EXCEPTIONAL'" @click.native.prevent="del(scope.$index,tableData)" class="table-button" type="text" size="small">删除记录</el-button>
			        </template>
			      </el-table-column>
			    </el-table>
		 	</template>
		 	</div>
		</div>
		<div class="orderrecord_paging tc">
			<el-pagination v-bind:current-Page="pageIndex" v-bind:page-size="pageSize" :total="total"
			   	layout="total,sizes,prev,pager,next,jumper" v-bind:page-sizes="pageSizes" :current-page="pageIndex"
			    v-on:size-change="sizeChange" v-on:current-change="pageIndexChange"></el-pagination>
		    </div>
		</div>
	</div>
</template>

<script>
import page from '../page.vue'
export default{
	data(){
		return{
			form:{
				region: '',
		        date1: '',
		        date2: '',
			},
			tableData:[/*{
				lesson:'语文',
				lessonTime:'09-03 15：00',
				lessonName:'语文第一章第一节',
				school:'苏州小学',
				teacher:'James',
				state:'进行中',
				operation:'取消关注',
			},{
				lesson:'数学',
				lessonTime:'09-03 14：00',
				lessonName:'语文第一章第一节',
				school:'苏州小学',
				teacher:'James',
				state:'进行中',
				operation:'取消关注',
			},{
				lesson:'英语',
				lessonTime:'09-03 16：00',
				lessonName:'语文第一章第一节',
				school:'苏州小学',
				teacher:'James',
				state:'进行中',
				operation:'删除记录',
			}*/],
			Sregion:[{
				id:'1_ONGOING',
				regionName:'进行中',
			},{
				id:'2_NOT_STARTED',
				regionName:'未开始',
			},{
				id:'3_ENDED',
				regionName:'已结束',
			},{
				id:'4_EXCEPTIONAL',
				regionName:'异常',
			}],
			pageIndex:1,
          	pageSize:10,
          	total:60,
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
			this.postHttp(this,{pageNum:this.pageIndex,pageSize:this.pageSize},"subscription/querySubscriptions",function(obj,data){
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
		statusFormat(row,column){
			var status = row[column.property];
			if(status == "1_ONGOING"){
				return "进行中";
			}
			if(status == "2_NOT_STARTED"){
				return "未开始";
			}
			if(status == "3_ENDED"){
				return "已结束";
			}
			if(status == "4_EXCEPTIONAL"){
				return "异常";
			}
		},
		dateChange1(val) {
		    this.form.date1=val;
		    this.form.date1=this.form.date1.substr(0, 19);
		},
		dateChange2(val){
			this.form.date2=val;
			this.form.date2=this.form.date2.substr(0, 19);
		},
		query:function(){
			alert(this.form.date1);
			this.postHttp(this,{status:this.form.region,programStartDate:this.form.date1,programEndDate:this.form.date2,pageNum:this.pageIndex,pageSize:this.pageSize},"/subscription/querySubscriptions",function(obj,data){
				obj.tableData=data.result.list;
				obj.total=data.result.total;
			})
		},
		sizeChange: function (pageSize) {   //每页显示条数
	      this.pageSize = pageSize;
	      this.fetchData();
	    },
	    pageIndexChange: function (pageIndex) {   //第几页
	      this.pageIndex = pageIndex;
	      this.fetchData();
	    },
	    toView:function(index){
	    	this.postHttp(this,{id:this.tableData[index].id,operation:"CANCEL"},"subscription/operateSubscription",function(obj,data){
			});
	    },
	    del:function(index){
	    	this.postHttp(this,{id:this.tableData[index].id,operation:"DELETE"},"subscription/operateSubscription",function(obj,data){
			});
	    },
	    fetchData:function(){
	    	this.postHttp(this,{pageNum:this.pageIndex,pageSize:this.pageSize},"subscription/querySubscriptions",function(obj,data){
	    		obj.tableData=data.result.list;
				obj.total=data.result.total;
			});
	    },
	}
}
</script>

<style>
#orderrecord .orderrecord_title{
	margin-top: 36px;
	margin-left: 40px;
	font-size: 16px;
	color: #999999;
	margin-bottom: 13px;
}
#orderrecord .orderrecord_body{
	margin: 0 auto;
	margin-top: 20px;
	width: 95%;
	overflow: hidden;
	min-height: 360px;
}
#orderrecord .orderrecord_paging{
	margin-top: 40px;
	margin-bottom: 40px;
}
#orderrecord .table-button{
	padding:0 !important;
}
#orderrecord .table-button span{
	color: #272727 !important;
}
#orderrecord .query_button{
	padding: 6px 47px !important;
	border-radius: 25px !important;
}
#orderrecord .orderrecord_body .el-button:hover{color:#fff;}
</style>