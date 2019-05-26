<template>
	<div id="resource" class="main_body">
		<el-tabs  >
		    <el-tab-pane label="考试列表">
		   		<div>
					<div class="exam_info_search">
						<el-row id="queryForm" :model="queryInfos" :gutter="20">
						  <el-col class="queryItems" :span="7">
						  	<div class="l">考试名称</div>
						  	<div class="r">
						  		<el-input v-model="queryInfos.examName" placeholder="考试名称"></el-input>
						  	</div>
						  </el-col>
						  <!--<el-col class="queryItems" :span="7">
						  	<div class="l">班级</div>
						  	<div class="r">
						  		<el-input v-model="queryInfos.classroom" placeholder="班级"></el-input>
						  	</div>
						  </el-col>-->
						  <el-col class="queryItems" :span="7">	
						  	<div class="l">科目</div>
						  	<div class="r">
						  		<el-input v-model="queryInfos.subject" placeholder="科目"></el-input>
						  	</div>
						  </el-col>
						  <el-col :span="3">
						  	<div class="btn_query r" @click="queryInfo">
						  		<i class="el-icon-search">查询</i>
						  	</div>
						  </el-col>
						</el-row>
						
					</div>
					
					<div class="exam_info_table">
						<el-table :data="tableData" style="width: 100%">
					      <el-table-column prop="examName" align="center" label="考试名称"></el-table-column>
					      <el-table-column prop="examStartDate" :formatter="timeFormatter" align="center" label="开始时间"></el-table-column>
					      <el-table-column prop="examEndDate" :formatter="timeFormatter" align="center"  label="结束时间"></el-table-column>
					      <!--<el-table-column prop="classroom" align="center"  label="班级"></el-table-column>-->
					      <el-table-column prop="subject" align="center"  label="科目"></el-table-column>
					      <el-table-column prop="examStatus" :formatter="statusFormatter" align="center"  label="考试状态"></el-table-column>
					      <el-table-column align="center" label="操作" width='250'>
					      	<template slot-scope="scope">
					      		<el-button type="primary" v-show="scope.row.examStatus==2" icon="el-icon-search" @click="report(scope.row.id)">查看报告</el-button>
					     	</template>
					      </el-table-column>
					    </el-table>
					    
					    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageNum"
					      :page-sizes="[10, 20, 50]"
					      :page-size="pageSize"
					      layout="sizes, prev, pager, next, jumper,total"
					      :total="total" class="tc mt20"
					      >
					    </el-pagination>
					</div>
				</div>
			</el-tab-pane>
		    <el-tab-pane label="双向细目表">
		    	<transition name="el-zoom-in-top">
					<div v-show="showTable">
						<div class="twoWay_info_search">
							<el-row id="queryForm" :model="queryInfos1" :gutter="20">
							  <el-col class="queryItems" :span="7">
							  	<div class="l">细目表名称</div>
							  	<div class="r">
							  		<el-input v-model="queryInfos1.specificationName" placeholder="双向细目表名称"></el-input>
							  	</div>
							  </el-col>
							  <el-col class="queryItems" :span="7">
							  	<div class="l">年级</div>
							  	<div class="r">
							  		<el-input v-model="queryInfos1.gradeName" placeholder="年级"></el-input>
							  	</div>
							  </el-col>
							  <el-col class="queryItems" :span="7">	
							  	<div class="l">科目</div>
							  	<div class="r">
							  		<el-input v-model="queryInfos1.subjectName" placeholder="科目"></el-input>
							  	</div>
							  </el-col>
							  <el-col :span="3">
							  	<div class="btn_query r" @click="queryInfo1">
							  		<i class="el-icon-search">查询</i>
							  	</div>
							  </el-col>
							</el-row>
						</div>
						
						<div class="twoWay_info_table">
							<el-table :data="tableData1" style="width: 100%">
						      <el-table-column prop="specificationName" align="center" label="双向细目表名称"></el-table-column>
						      <el-table-column prop="gradeCode"  align="center" label="年级"></el-table-column>
						      <el-table-column prop="subjectCode" align="center"  label="科目"></el-table-column>
						      <el-table-column prop="createDate" align="center" :formatter="timeFormatter"  label="创建时间"></el-table-column>
						      <el-table-column align="center" label="操作" width='250'>
						      	<template slot-scope="scope">
						      		<el-button type="primary" icon="el-icon-search" @click="showInfo(scope.row)">查看</el-button>
						      	</template>
						      </el-table-column>
						    </el-table>
						    
						    <el-pagination @size-change="handleSizeChange1" @current-change="handleCurrentChange1" :current-page.sync="pageNum1"
						      :page-sizes="[10, 20, 50]"
						      :page-size="pageSize1"
						      layout="sizes, prev, pager, next, jumper,total"
						      :total="total1" class="tc mt20"
						      >
						    </el-pagination>
						</div>
					</div>
				</transition>
				<transition name="el-zoom-in-bottom">
					<div v-show="showDetails" class="twoWay_details ">
						<div class="twoWay_info_table">
							<div class="tools fix">
								
								<div class="items_tools r">
									<i class="el-icon-back" @click="backList">返回列表</i>
								</div>
							</div>
							<el-table :data="detailsData1" style="width: 100%">
						      <el-table-column prop="itemNo" align="center" label="题号" width="50px"></el-table-column>
						      <el-table-column prop="itemType" :formatter="typeFormatter" align="center" label="题型"></el-table-column>
						      <el-table-column prop="itemScore" align="center"  label="满分" width="50px"></el-table-column>
						      <el-table-column prop="itemAnswer" align="center" label="答案" width="50px"></el-table-column>
						      <el-table-column align="center" label="能力值">
						      	<el-table-column prop="itemAbility[0]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" label="空间想象"></el-table-column>
						      	<el-table-column prop="itemAbility[1]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" label="抽象概括"></el-table-column>
						      	<el-table-column prop="itemAbility[2]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" label="推理论证"></el-table-column>
						      	<el-table-column prop="itemAbility[3]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" label="运算求解"></el-table-column>
						      	<el-table-column prop="itemAbility[4]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" label="数据处理"></el-table-column>
						      	<el-table-column prop="itemAbility[5]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" label="综合应用"></el-table-column>
						      </el-table-column>
						      <el-table-column prop="knowledgeName" align="center" width='250' show-overflow-tooltip label="知识点"></el-table-column>
						    </el-table>
						</div>
					</div>
				</transition>
		    </el-tab-pane>
		</el-tabs>
	</div>
</template>

<script>
import TwoWay from '../assets/data/twoway.js'
import ExamList from '../assets/data/examlist.js'
export default {
	data () {

	    return {
	      tableData:[],
		  tableData1:[],
		  detailsData1:[],
		  queryInfos1:{
		  	specificationName:'',
		  	gradeName:'',
		  	subjectName:''
		  },
		  queryInfos:{
		  	examName:'',
		  	gradeName:'',
		  	subject:''
		  },
		  pageNum:1,
	      pageSize:10,
	      total:1,
		  pageNum1:1,
	      pageSize1:10,
	      total1:1,
	      
	      showTable:true,
	      showDetails:false,
	    }
	 },
	created: function(){
		this.$emit('refreshbizlines','other');
	},
	mounted:function(){
		this.queryInfo();
		this.queryInfo1();
  },
  methods:{
  	report(id){
  		this.postHttp(this,'',"user/getLoginUser",function(obj,data){
        obj.username = data.result.userName;
        if(data.result.roleName=='老师'||data.result.roleName=='教师')
          obj.$router.push({path:'/report/schoolLevel',query:{name:id}});
        if(data.result.roleName=='学生')
          obj.$router.push({path:'/report/studentLevel',query:{name:id}});
      	});
  	},
  	queryInfo(){
  		var datas = this.ajaxData();
  		this.postHttp(this,datas,'exam/queryExams',function(obj,res){
	  		 obj.pageNum = res.result.pageNum;
	  		 obj.pageSize = res.result.pageSize;
	  		 obj.total = res.result.total;
	  		obj.tableData = res.result.list;
	  	})
  	},
  	queryInfo1(){
  		var datas = this.ajaxData1();
	  	this.postHttp(this,datas,'twowayspecification/queryTwoWaySpecifications',function(obj,res){
	  		 obj.pageNum1 = res.result.pageNum;
	  		 obj.pageSize1 = res.result.pageSize;
	  		 obj.total1 = res.result.total;

	  		obj.tableData1 = res.result.list;
	  	})
  	},
  	statusFormatter(row, column, cellValue){
		var status = row[column.property];  
	  	switch(status){
	  		case 0:
	  		return '未开始';
	  		case 1:
	  		return '正在进行';
	  		case 2:
	  		return '已结束';
	  	}
	},
	showInfo(row){
		this.showTable = false;
		this.showDetails = true;
		this.parentId = row.id;
		this.dialogSubject = row.subjectCode;
		this.postHttp(this,{parentId:row.id},'twowayspecificationdetail/queryTwoWaySpecificationDetails',function(obj,res){
			var deDate = res.result;
			for(var i = 0;i < deDate.length; i++){
				var itemAbility = deDate[i].itemAbility;
				var arrays = new Array();
				for(var j = 0;j < itemAbility.length;j++){
					arrays.push(itemAbility[j]);
				}
				deDate[i].itemAbility = arrays;
			}
	  		obj.detailsData1 = deDate;
	  	})
	},
	backList(){
		this.showTable = true;
		this.showDetails = false;
	},
	handleSizeChange1(val) {
	  	this.pageNum1 = 1;
		this.pageSize1 = val;
		this.queryInfo1();
	},
	handleCurrentChange1(val) {
	  	this.pageNum1 = val;
		this.queryInfo1();
	},
	handleSizeChange(val) {
	  	this.pageNum = 1;
		this.pageSize = val;
		this.queryInfo();
	},
	handleCurrentChange(val) {
	  	this.pageNum = val;
		this.queryInfo();
	},
	timeFormatter(row, column, cellValue){
		var date = row[column.property];  
	  	if (date == undefined || date == '') {  
	     return "";  
	  	}  
	  	return this.timeF(date).format("YYYY-MM-DD HH:mm:ss");  
	},
	typeFormatter(row, column, cellValue){
		var type = row[column.property];  
		if(type == '1'){
	  		return '主观题';
	  	}else{
	  		return '客观题';
	  	}
	  	if (type == undefined || type == '') {  
	     return "";  
	  	}
	},
	ablitFormatter(row, column, cellValue){
		var values = cellValue;
		if(values == '1'){
	  		return '√';
	  	}else{
	  		return '';
	  	}
	},
	ajaxData(){
		var data = new Object();
		data["pageSize"] = this.pageSize;
		data["pageNum"] = this.pageNum;
		data["examName"] = this.queryInfos.examName;
		data["subject"] = this.queryInfos.subject;
		return data;
	},
	ajaxData1(){
		var data = new Object();
		data["pageSize"] = this.pageSize1;
		data["pageNum"] = this.pageNum1;
		data["specificationName"] = this.queryInfos1.specificationName;
		data["gradeCode"] = this.queryInfos1.gradeName;
		data["subjectCode"] = this.queryInfos1.subjectName;
		return data;
	},
  }
}
</script>

<style>
#resource{
	padding:100px 0;
}
#queryForm .queryItems .l {
    width: 25%;
    text-align: right;
    height: 40px;
    line-height: 40px;
    color: #5a5e66;
    font-size: 14px;
}
#queryForm .btn_query {
    width: 70%;
    margin: auto;
    height: 40px;
    line-height: 40px;
    text-align: center;
    color: #fff;
    background: #409EFF;
    border-radius: 3px;
    cursor: pointer;
}
#resource .el-button--primary{
	padding: 7px
}
#resource .tools .items_tools {
    padding: 0px 10px;
    height: 30px;
    line-height: 30px;
    cursor: pointer;
    text-align: center;
    margin-right: 10px;
    font-size: 14px;
    border-radius: 4px;
    background: #409EFF;
    color: #fff;
    margin-bottom: 20px
}
#resource .twoWay_info_search,#resource .exam_info_search{
	margin-bottom: 20px
}
#resource .el-tabs__item{
	font-size: 16px
}
#resource .el-pagination__total{
	margin-left: 20px;
}
</style>