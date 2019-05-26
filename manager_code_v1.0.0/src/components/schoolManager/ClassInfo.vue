<template>
	<div id="classInfo" class="main_body">
		<div class="class_info_search">

			<el-row id="queryForm" :model="queryInfos" :gutter="20">
			  <el-col class="queryItems" :span="6">
			  	<div class="l">班级</div>
			  	<div class="r">
			  		<el-input v-model="queryInfos.classroomName" placeholder="班级" clearable></el-input>
			  	</div>
			  </el-col>
			  <el-col class="queryItems" :span="6">
			  	<div class="l">班主任名称</div>
			  	<div class="r">
			  		<el-input v-model="queryInfos.classTeather" placeholder="班主任名称" clearable></el-input>
			  	</div>
			  </el-col>
			  <el-col class="queryItems" :span="6">
			  </el-col>
			  <el-col :span="6">
			  	<div class="btn_query r" @click="queryInfo">
			  		<i class="el-icon-search">查询</i>
			  	</div>
			  </el-col>
			</el-row>

		</div>

		<div class="class_info_table">
			<div class="tools fix">
				<div class="items_tools l" @click="addNew">
					<i class="el-icon-circle-plus-outline">新增</i>
				</div>
			</div>
			<el-table :data="tableData" style="width: 100%">
		      <el-table-column prop="classroomName" align="center" label="班级"></el-table-column>
		      <el-table-column prop="teacherName" align="center" show-overflow-tooltip label="班主任"></el-table-column>
		      <el-table-column prop="studentCount" align="center" label="班级人数"></el-table-column>
		      <el-table-column prop="position" align="center" show-overflow-tooltip label="地理位置"></el-table-column>
		      <el-table-column prop="remark" align="center" show-overflow-tooltip label="备注"></el-table-column>
		      <el-table-column align="center" width="200" label="操作">
		      	<template slot-scope="scope">
		      		<el-button type="primary" icon="el-icon-edit" @click="editInfo(scope.row.id)">编辑</el-button>
		      		<el-button type="primary" icon="el-icon-delete" @click="deleteInfo(scope.row.id)">删除</el-button>
		      	</template>
		      </el-table-column>
		    </el-table>

		    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageNum"
		      :page-sizes="[10, 20, 50]"
		      :page-size="pageSize"
		      layout="sizes, prev, pager, next, jumper, total"
		      :total="total" class="tc mt20"
		      >
		    </el-pagination>
		</div>


		<el-dialog :title="diaTitle" :visible.sync="dialogVisible" width="30%">
		  <div class="dialog_body">
		  	<el-form label-position="right" :rules="rules" ref="classroom" class="demo-ruleForm" label-width="80px" :model="classroom">
			  <el-form-item label="年级" v-show="showInfo" prop="gradeCodes">
			    <el-select v-model="classroom.gradeCodes" placeholder="请选择">
				    <el-option v-for="item in gradeOption" :key="item" :label="item" :value="item">
				    </el-option>
				</el-select>
			  </el-form-item>
			  <el-form-item label="班级"  v-show="showInfo" prop="classroomNames" style="position: relative;">
			  	<el-input v-model.number="classroom.classroomNames" placeholder="班级"></el-input>
			  	<div style="height:40px;line-height: 40px;width:20px;text-align: center;position: absolute;right: 10px;top: 0px;">班</div>
			  </el-form-item>
			  <el-form-item label="班主任">
			    <el-select v-model="classroom.classTeacherId" placeholder="请选择">
				    <el-option v-for="item in teacherOption" :key="item.id" :label="item.teacherName" :value="item.id">
				    </el-option>
				</el-select>
			  </el-form-item>
			  <el-form-item label="地理位置" >
			  	<el-input v-model="classroom.position" :maxlength="200" placeholder="地理位置"></el-input>
			  </el-form-item>
			  <el-form-item label="备注" >
			  	<el-input v-model="classroom.remark" :maxlength="200" placeholder="备注"></el-input>
			  </el-form-item>
			</el-form>
		  </div>
		  <span slot="footer" class="dialog-footer">
		    <el-button type="primary" @click="colseDia">取 消</el-button>
		    <el-button type="primary" @click="saveEdit">确 定</el-button>
		  </span>
		</el-dialog>
	</div>

</template>

<script>
export default {
  data () {
	var validateClass = (rule, value, callback) => {
	      var reg = new RegExp("^[1-9][0-9]*$");
	      if(!reg.test(value)){
	      	callback(new Error('班级必须为大于零的正整数'));
	      }else{
	      	callback();
	      }
	};
    return {
	  msg: 'classInfo',
	  tableData:[],
	  queryInfos:{
	  	classroomName:'',
	  	teacherName:''
	  },

	  pageNum:1,
      pageSize:10,
      total:1,

      dialogVisible:false,
      diaTitle:'新增',
      showInfo:true,
      classroom:{},
      gradeOption:[],
      teacherOption:[],

	   rules: {
	      gradeCodes: [
	        { required: true, message: '请选择年级', trigger: 'change' }
	      ],
	      classroomNames: [
	        { required: true, validator: validateClass, trigger: 'blur'},
	      ],
      }
    }
  },
  mounted:function(){
  	this.postHttp(this,{},'getLoingGrade',function(obj,res){
  		obj.gradeOption = res.result;
  	});

  	this.postHttp(this,{pageNum:1,pageSize:400},'teacher/queryTeachers',function(obj,res){
  		obj.teacherOption = res.result.list;
  	});

  	this.queryInfo();
  },
  methods:{
  	queryInfo(){
  		var datas = this.ajaxData();
	  	this.postHttp(this,datas,'classroom/queryClassrooms',function(obj,res){
	  		obj.pageNum = res.result.pageNum;
	  		obj.pageSize = res.result.pageSize;
	  		obj.total = res.result.total;
	  		obj.tableData = res.result.list;
	  	})
  	},
  	colseDia(){
  		this.dialogVisible = false;
  		this.classroom = {};
  	},
  	saveEdit(){

	  		var id = this.classroom.id;
	  		var address = 'classroom/saveClassroom';
	  		delete this.classroom["createDate"];
	  		delete this.classroom["updateDate"];
	  		var dataS = this.classroom;
	  		if(id){
	  			var loading = this.loading('正在处理...');
	  			address = 'classroom/updateClassroom';
	  			this.postHttp(this,dataS,address,function(obj,res){
		  			if(res.code == '10000'){
		  				loading.close();
		  				obj.dialogVisible = false;
		  				obj.notify_success();
		  				obj.queryInfo();
		  			}else{
		  				loading.close();
		  				obj.notify_jr(obj,'操作错误',res.message,'error');
		  			}
		  		})
	  		}else{
	  			this.$refs['classroom'].validate((valid) => {
          			if (valid) {
          				var loading = this.loading('正在处理...');
			  			var classNo = this.classroom.classroomNames;
				  		var grade = this.classroom.gradeCodes;
				  		dataS['classroomName'] = grade + "(" + classNo + ")班";
				  		this.postHttp(this,dataS,address,function(obj,res){
				  			if(res.code == '10000'){
				  				loading.close();
				  				obj.dialogVisible = false;
				  				obj.notify_success();
				  				obj.queryInfo();
				  			}else{
				  				loading.close();
				  				obj.notify_jr(obj,'操作错误',res.message,'error');
				  			}
				  		})
				  	} else {
			            return false;
			        }
				});
		  	}



  	},
  	addNew(){
  		this.dialogVisible = true;
  		this.showInfo = true;
  		this.diaTitle = "新增";
  		this.classroom = {};
  		if(this.$refs['classroom']){
  			this.$refs['classroom'].resetFields();
  		}
  	},
	editInfo(id){

		this.showInfo = false;
		this.dialogVisible = true;
		this.diaTitle = "编辑";
		this.postHttp(this,{id:id},"classroom/getClassroomById",function(obj,res){
  			if(res.code == '10000'){
  				obj.classroom = res.result;
  				obj.$refs['classroom'].resetFields();
  			}else{
  				obj.notify_jr(obj,'操作错误',res.message,'error');
  			}
  		})
	},
	deleteInfo(id){
		this.$confirm('此操作将删除该班级和班级下学生,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        	var loading = this.loading('正在处理...');
        	this.postHttp(this,{id:id},'classroom/deleteClassroom',function(obj,res){
		  		if(res.code == "10000"){
		  			loading.close();
		  			obj.notify_success();
		  			obj.queryInfo();
		  		}else{
		  			loading.close();
		  			obj.notify_jr(obj,'操作错误',res.message,'error');
		  		}
		  	});
        }).catch(() => {

        });
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
	ajaxData(){
		var data = new Object();
		data["pageSize"] = this.pageSize;
		data["pageNum"] = this.pageNum;
		data["classroomName"] = this.queryInfos.classroomName;
		data["classTeather"] = this.queryInfos.classTeather;
		return data;
	}
  }
}
</script>

<style>
#classInfo .class_info_search{
	width: 90%;
	margin:20px auto;
}
#classInfo .class_info_table{
	width: 90%;
	margin:20px auto;
}
</style>
