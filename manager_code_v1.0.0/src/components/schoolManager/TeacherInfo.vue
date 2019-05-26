<template>
	<div id="teacherInfo" class="main_body">
		<div class="teacher_info_search">

			<el-row id="queryForm" :model="queryInfos" :gutter="20">
			  <el-col class="queryItems" :span="6">
			  	<div class="l">姓名</div>
			  	<div class="r">
			  		<el-input v-model="queryInfos.teacherName" clearable placeholder="姓名"></el-input>
			  	</div>
			  </el-col>
			  <el-col class="queryItems" :span="6">
			  	<div class="l">职责</div>
			  	<div class="r">
			  		<el-input v-model="queryInfos.teacherDuty" clearable placeholder="职责"></el-input>
			  	</div>
			  </el-col>
			  <el-col class="queryItems" :span="6">
			  		<div class="l">年级</div>
				  	<div class="r">
				  		<el-select v-model="grade" clearable @change='changeGrades' placeholder="请选择">
						    <el-option v-for="item in gradeOption" :key="item" :label="item" :value="item">
						    </el-option>
						</el-select>
				  	</div>
			  </el-col>
			  <el-col class="queryItems" :span="6">
			  		<div class="l">班级</div>
				  	<div class="r">
				  		<el-select v-model="queryInfos.classroom" clearable placeholder="请选择">
					  		<el-option v-for="e in classOptions" :label="e.classroomName" :key="e.classroomName" :value="e.classroomName" name="classId"></el-option>
						</el-select>
				  	</div>
			  </el-col>
			  <el-col class="queryItems" :span="6">
			  </el-col>
			  <el-col class="queryItems" :span="6">
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

		<div class="teacher_info_table">
			<div class="tools fix">
				<div class="items_tools l" @click="addNew">
					<i class="el-icon-circle-plus-outline">新增</i>
				</div>
				<div class="items_tools l" @click="addNews">
					<i class="el-icon-circle-plus-outline">批量新增</i>
				</div>
			</div>
			<el-table :data="tableData" style="width: 100%">
		      <el-table-column prop="teacherName" align="center" label="姓名"></el-table-column>
		      <el-table-column prop="teacherSex" :formatter='sexFormatter' align="center" label="性别"></el-table-column>
		      <el-table-column prop="teacherAge" align="center"  label="年龄"></el-table-column>
		      <el-table-column prop="classroom" align="center" show-overflow-tooltip  label="班级"></el-table-column>
		      <el-table-column prop="subject" align="center" show-overflow-tooltip  label="学科"></el-table-column>
		      <el-table-column prop="teacherMobile" align="center" show-overflow-tooltip label="电话"></el-table-column>
		      <el-table-column prop="teacherDuty" align="center"  label="职责"></el-table-column>
		      <el-table-column prop="teacherJobTitle" align="center"  label="职称"></el-table-column>
		      <el-table-column prop="userName" show-overflow-tooltip align="center"  label="帐户名"></el-table-column>
		      <el-table-column align="center" label="操作" width="300">
		      	<template slot-scope="scope">
		      		<el-button type="primary" v-show="userType == 1 || userType == 0" v-if="scope.row.userName == '' || scope.row.userName == undefined "
		      			icon="el-icon-upload" @click="allotAuth(scope.row.id)">开通账号</el-button>
		      		<el-button type="primary forbid" title="账号已开通" v-show="userType == 1 || userType == 0" v-else icon="el-icon-upload">开通账号</el-button>
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

		<el-dialog title="批量新增" :visible.sync="showAddAll" width="30%">
			<div class="typeFile fix" v-loading="loadings">
				<div class="l files">
					<form id="uploadForm">
						<input type="file" name="file" id="fileUpload" @change="fileChange"/>
					</form>
					<div id="fileUploadRead">点击这里上传文件</div>
				</div>
				<div class="l buttons">
					<el-button type="primary" @click="submitUpload">批量添加</el-button>
					<el-button type="primary" @click="downloadTemp">下载模板</el-button>
				</div>
			</div>
			<div id="fileUploadTip"></div>
		</el-dialog>

		<el-dialog :title="diaTitle" :visible.sync="dialogVisible" width="30%">
		  <div class="dialog_body">
		  	<el-form label-position="right" :rules="rules" ref="teacher" class="demo-ruleForm" label-width="80px" :model="teacher">
	  		  <el-form-item label="姓名" prop="teacherName" >
			  	<el-input v-model="teacher.teacherName" placeholder="姓名"></el-input>
			  </el-form-item>
			  <el-form-item label="性别" prop="teacherSex">
			    <el-select v-model="teacher.teacherSex" placeholder="请选择">
				    <el-option v-for="item in sexOption" :key="item.id" :label="item.value" :value="item.id">
				    </el-option>
				</el-select>
			  </el-form-item>
			  <el-form-item label="年龄"  >
			  	<el-input v-model="teacher.teacherAge" placeholder="年龄"></el-input>
			  </el-form-item>
			  <el-form-item label="手机号"  prop="teacherMobile">
			  	<el-input v-model="teacher.teacherMobile" :maxlength="maxLength" placeholder="手机号"></el-input>
			  </el-form-item>
			  <el-form-item label="职责" prop="teacherDuty" >
			  	<el-select v-model="teacher.teacherDuty" placeholder="请选择">
			  		<el-option v-for="item in dutyOption" :key="item.id" :label="item.id" :value="item.id">
				    </el-option>
				</el-select>
			  </el-form-item>
			  <el-form-item label="职称"  >
			  	<el-input v-model="teacher.teacherJobTitle" placeholder="职称"></el-input>
			  </el-form-item>
			  <el-form-item label="年级" prop="grade" >
			    <el-select v-model="teacher.grade" @change='changeGrade' placeholder="请选择">
				    <el-option v-for="item in gradeOption" :key="item" :label="item" :value="item">
				    </el-option>
				</el-select>
			  </el-form-item>
			  <el-form-item label="班级" prop="classArray" >
			  	<el-checkbox-group v-model="teacher.classArray">
			  		<el-checkbox v-for="e in classOption" :key="e.id" :label="e.classroomName" name="classArray"></el-checkbox>
			    </el-checkbox-group>
			  </el-form-item>
			  <el-form-item label="学科" prop="subjectArray" >
			  	<el-checkbox-group v-model="teacher.subjectArray">
			      <el-checkbox v-for="e in subjectOption" :key="e" :label="e" name="subjectArray"></el-checkbox>
			    </el-checkbox-group>
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
		var validatePhone = (rule, value, callback) => {
			var reg = /^1[3|4|5|8|9|7|6][0-9]\d{3,11}$/;
			if(!reg.test(value)){
	          callback(new Error('电话格式不正确'));
	        }else if(value.length != 11){
	          callback(new Error('电话格式不正确'));
	        }else{
	        	callback();
        }
    };
    return {
	  msg: 'TeacherInfo',
	  tableData:[],
	  queryInfos:{
	  	teacherName:'',
	  	teacherDuty:'',
	  	classroom:'',
	  },

	  pageNum:1,
      pageSize:10,
      total:1,

      dialogVisible:false,
      diaTitle:'新增',
      teacher:{
      	subjectArray:['语文'],
      	classArray:[]
      },
	  sexOption:[
	  	{
	  		id:1,
	  		value:'男'
	  	},
	  	{
	  		id:0,
	  		value:'女'
	  	}
	  ],
	  dutyOption:[
	  	{
	  		id:'老师'
	  	},
	  	{
	  		id:'班主任'
	  	}
	  ],

	  classOption:[],
	  classOptions:[],
	  grade:'',
	  gradeOption:[],
	  subjectOption:[],
	  classOption:[],

		showAddAll:false,
		addsAction:'',
		loadings:false,
		limit:1,

	  maxLength:11,
	  rules: {
          teacherName: [
            { required: true, message: '请输入老师名字', trigger: 'blur' }
          ],
          teacherDuty: [
            { required: true, message: '请选择老师职位', trigger: 'change' }
          ],
          grade: [
            { required: true, message: '请选择年级', trigger: 'change' }
          ],
          teacherSex: [
            { required: true, message: '请选择性别', trigger: 'change' }
          ],
          classArray: [
            { type: 'array', required: true, message: '请至少选择一个班级', trigger: 'change' }
          ],
          subjectArray: [
            { type: 'array', required: true, message: '请至少选择一个学科', trigger: 'change' }
          ],
          teacherMobile:[
          	{required: true,validator:validatePhone}
          ]
      }
    }
  },
  mounted:function(){
  	this.postHttp(this,{},'getLoingGrade',function(obj,res){
  		obj.gradeOption = res.result;
  	});

	this.postHttp(this,{},'user/getLoginUser',function(obj,res){

  		obj.userType = res.result.userType;
  	});

  	this.queryInfo();

  	this.postHttp(this,{},'school/querySchools',function(obj,res){
  		obj.subjectOption = res.result.subjectArray;
  	});
  	this.addsAction = this.getBaseUrl() + 'student/importTeacherList';
  },
  methods:{
  	queryInfo(){
  		var datas = this.ajaxData();
  		this.postHttp(this,datas,'teacher/queryTeachers',function(obj,res){
	  		obj.pageNum = res.result.pageNum;
	  		obj.pageSize = res.result.pageSize;
	  		obj.total = res.result.total;
	  		obj.tableData = res.result.list;
	  	});
  	},
		fileChange(){
			var path = document.getElementById("fileUpload").value;
			var arr = path.split("\\");
			path = arr[(arr.length - 1)];
			if(path != ''){
				document.getElementById("fileUploadRead").innerHTML = path;
			}
		},
		downloadTemp(){
			var dates = new Date();
			dates = dates.getTime();
			var url = this.getBaseUrl() + 'teacher/dowmloadTeacherListTemplate'+"?jy_pc_manager&timer="+dates;
			window.open(url)
		},
		addNews(){
			this.showAddAll = true;
			if(document.getElementById("fileUpload") != undefined){
				document.getElementById("fileUpload").value = '';
				document.getElementById("fileUploadTip").innerHTML = '';
				document.getElementById("fileUploadRead").innerHTML = '点击这里上传文件';
			}
		},
		submitUpload(){
			this.loadings = true;
			var form = document.getElementById("fileUpload").files[0];
			var formData = new FormData(document.getElementById("uploadForm"))
			let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
				withCredentials : true
      }
			var dates = new Date();
			dates = dates.getTime();
			var s = this.getBaseUrl()
      this.$axios.post(s+'teacher/importTeacherList'+"?jy_pc_manager&timer="+dates, formData, config).then(response =>  {
				this.loadings = false;
				if (response.data.code == '10000') {
					this.showAddAll = false;
					this.notify_jr(this,'添加成功','添加成功','success');
        }else{
					document.getElementById("fileUploadTip").innerHTML = response.data.message;
				}
      },response => {
				this.loadings = false;
				this.$notify({
			      title: '网络错误',
			      message: '网络错误',
			      offset: 100,
			      duration:1500,
			      type:'error'
			    });
			})
		},
  	colseDia(){
  		this.dialogVisible = false;
  		this.teacher = {};
  	},
  	saveEdit(){
  		var id = this.teacher.id;
  		delete this.teacher["createDate"];
  		delete this.teacher["updateDate"];
  		var address = 'teacher/saveTeacher';
  		var dataS = this.teacher
  		if(id){
  			address = 'teacher/updateTeacher';
  		}

  		this.$refs['teacher'].validate((valid) => {
          if (valid) {
          	this.postHttp(this,dataS,address,function(obj,res){
				if(res.code == '10000'){
					obj.dialogVisible = false;
					obj.notify_success();
					obj.queryInfo();
				}else{
					obj.notify_jr(obj,'操作错误',res.message,'error');
				}
			})
          } else {
            return false;
          }
        });

  	},
  	addNew(){
  		this.dialogVisible = true;
  		this.diaTitle = "新增";
  		this.grade = "";
  		this.teacher = {
  			subjectArray:[],
      		classArray:[]
  		};
  		this.classOption = [];
  		if(this.$refs['teacher']){
  			this.$refs['teacher'].resetFields();
  		}

  	},
	editInfo(id){
		this.dialogVisible = true;
		this.diaTitle = "编辑";
		if(this.$refs['teacher']){
  			this.$refs['teacher'].resetFields();
  		}
		this.postHttp(this,{id:id},"teacher/getTeacherById",function(obj,res){
  			if(res.code == '10000'){
  				obj.teacher = res.result;
  				var grade = res.result.grade;
  				obj.postHttp(obj,{grade:grade},'classroom/queryClassroomsByGrade',function(obj,res){
			  		obj.classOption = res.result;
			  	});
  			}else{
  				obj.notify_jr(obj,'操作错误',res.message,'error');
  			}
  		})
	},
	deleteInfo(id){
		this.$confirm('此操作将删除该教师信息,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        	this.postHttp(this,{id:id},'teacher/deleteTeacher',function(obj,res){
		  		if(res.code == "10000"){
		  			obj.notify_success();
		  			obj.queryInfo();
		  		}else{
		  			obj.notify_jr(obj,'操作错误',res.message,'error');
		  		}
		  	});
        }).catch(() => {

        });
	},
	allotAuth(id){
		var loading = this.loading('正在开通...');
		this.postHttp(this,{id:id},"user/saveTeacherUser",function(o,res){
			loading.close();
			if(res.code == "10000"){
				o.notify_success();
				o.queryInfo();
				o.$alert('开通账户成功,密码为:Aa111111', '提示', {confirmButtonText: '确定'});
			}else{
				o.notify_jr(o,'操作错误',res.message,'error');
			}
		})
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
	sexFormatter(row, column, cellValue){
		var age = row[column.property];
	  	if (age == undefined) {
	     return "";
	  	}
	  	if(age == '1'){
	  		return '男';
	  	}else{
	  		return '女';
	  	}
	},
	ajaxData(){
		var data = new Object();
		data["pageSize"] = this.pageSize;
		data["pageNum"] = this.pageNum;
		data["teacherName"] = this.queryInfos.teacherName;
		data["teacherDuty"] = this.queryInfos.teacherDuty;
		data["classroom"] = this.queryInfos.classroom;
		return data;
	},
	changeGrade(val){
		this.postHttp(this,{grade:val},'classroom/queryClassroomsByGrade',function(obj,res){
	  		obj.classOption = res.result;
	  	});
	},
	changeGrades(val){
		this.postHttp(this,{grade:val},'classroom/queryClassroomsByGrade',function(obj,res){
			obj.queryInfos["classroom"] = "";
	  		obj.classOptions = res.result;
	  	});
	}
  }
}
</script>

<style>
#teacherInfo .teacher_info_search{
	width: 90%;
	margin:20px auto;
}
#teacherInfo .teacher_info_table{
	width: 90%;
	margin:20px auto;
}
#teacherInfo .forbid{
	background:#C0C0C0!important;
	border-color: #C0C0C0!important;
	color:#828282!important;
}
</style>
