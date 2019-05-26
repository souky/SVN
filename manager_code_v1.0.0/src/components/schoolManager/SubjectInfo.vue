<template>
	<div id="subjectInfo" class="main_body">
		<div class="subject_info_search">
			<el-row id="queryForm" :model="queryInfos" :gutter="20">
			  <el-col class="queryItems" :span="6">
			  	<div class="l">科目名称</div>
			  	<div class="r">
			  		<el-input v-model="queryInfos.subjectName" clearable placeholder="科目名称"></el-input>
			  	</div>
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

		<div class="subject_info_table">
			<el-table :data="tableData" style="width: 100%">
		      <el-table-column prop="subjectCode" align="center" label="科目编码"></el-table-column>
		      <el-table-column prop="subjectName" align="center" label="科目名称"></el-table-column>
		      <el-table-column  align="center" label="背景颜色">
		      	<template slot-scope="scope">
		      		<div class="colrs" :style="{background:scope.row.subjectColor}">{{scope.row.subjectColor}}</div>
		      	</template>
		      </el-table-column>
		      <el-table-column align="center" label="操作">
		      	<template slot-scope="scope">
		      		<el-button type="primary" icon="el-icon-edit" @click="ablityInfo(scope.row)">相关能力值</el-button>
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

				<el-dialog :title="diaTitle" :visible.sync="dialogVisible" width="30%">
				  <div class="dialog_body">
				  	<el-form label-position="right" ref="formName" :model="ablity" :rules="rules" class="demo-ruleForm" label-width="80px">
						  <el-form-item label="能力值1" prop="name0">
						  	<el-input v-model="ablity.list[0]" placeholder="填写能力值"></el-input>
						  </el-form-item>

							<el-form-item label="能力值2" prop="name1">
							 	<el-input v-model="ablity.list[1]" placeholder="填写能力值"></el-input>
						 	</el-form-item>

						 	<el-form-item label="能力值3" prop="name2">
							 	<el-input v-model="ablity.list[2]" placeholder="填写能力值"></el-input>
						 	</el-form-item>

						 	<el-form-item label="能力值4" prop="name3">
							 	<el-input v-model="ablity.list[3]" placeholder="填写能力值"></el-input>
						 	</el-form-item>

						 	<el-form-item label="能力值5" prop="name4">
							 	<el-input v-model="ablity.list[4]" placeholder="填写能力值"></el-input>
						 	</el-form-item>

						 	<el-form-item label="能力值6" prop="name5">
							 	<el-input v-model="ablity.list[5]" placeholder="填写能力值"></el-input>
						 	</el-form-item>
					</el-form>
				  </div>
				  <span slot="footer" class="dialog-footer">
				    <el-button type="primary" @click="colseDia">取 消</el-button>
				    <el-button type="primary" @click="saveEdit">确 定</el-button>
				  </span>
				</el-dialog>
		</div>
	</div>

</template>

<script>
export default {
  data () {
		var validateName0 = (rule, value, callback) => {value = this.ablity.list[0];if (value == undefined || value.length == 0) {callback(new Error('请输入能力值'));}else if(value.length > 7){callback(new Error('必须小于7个字符'));}else {callback();}};
		var validateName1 = (rule, value, callback) => {value = this.ablity.list[1];if (value == undefined || value.length == 0) {callback(new Error('请输入能力值'));}else if(value.length > 7){callback(new Error('必须小于7个字符'));}else {callback();}};
		var validateName2 = (rule, value, callback) => {value = this.ablity.list[2];if (value == undefined || value.length == 0) {callback(new Error('请输入能力值'));}else if(value.length > 7){callback(new Error('必须小于7个字符'));}else {callback();}};
		var validateName3 = (rule, value, callback) => {value = this.ablity.list[3];if (value == undefined || value.length == 0) {callback(new Error('请输入能力值'));}else if(value.length > 7){callback(new Error('必须小于7个字符'));}else {callback();}};
		var validateName4 = (rule, value, callback) => {value = this.ablity.list[4];if (value == undefined || value.length == 0) {callback(new Error('请输入能力值'));}else if(value.length > 7){callback(new Error('必须小于7个字符'));}else {callback();}};
		var validateName5 = (rule, value, callback) => {value = this.ablity.list[5];if (value == undefined || value.length == 0) {callback(new Error('请输入能力值'));}else if(value.length > 7){callback(new Error('必须小于7个字符'));}else {callback();}};
    return {
      msg: 'subjectInfo',
      tableData:[],
		  queryInfos:{
		  	subjectName:''
		  },
		  pageNum:1,
	    pageSize:10,
	    total:1,

			diaTitle:'编辑能力值',
			dialogVisible:false,
			ablity:{
				list:[],
				subject:'',
			},

			rules:{
				name0: [{ required: true,  validator: validateName0, trigger: 'blur' }],
				name1: [{ required: true,  validator: validateName1, trigger: 'blur' }],
				name2: [{ required: true,  validator: validateName2, trigger: 'blur' }],
				name3: [{ required: true,  validator: validateName3, trigger: 'blur' }],
				name4: [{ required: true,  validator: validateName4, trigger: 'blur' }],
				name5: [{ required: true,  validator: validateName5, trigger: 'blur' }],
			}
    }
  },
  mounted:function(){
  	this.queryInfo();
  },
  methods:{
	queryInfo(){
  		var datas = this.ajaxData();
	  	this.postHttp(this,datas,'subject/querySubjects',function(obj,res){
	  		obj.pageNum = res.result.pageNum;
	  		obj.pageSize = res.result.pageSize;
	  		obj.total = res.result.total;
	  		obj.tableData = res.result.list;
	  	})
  	},
	ablityInfo(row){
		var datas = {subjectName:row.subjectName}
		this.ablity.subject = row.subjectName;
		this.ablity.list = [];
		this.postHttp(this,datas,'subjectablity/querySubjectAblitys',function(obj,res){
			obj.dialogVisible = true;
			obj.ablity.list = res.result;
			if(obj.$refs['formName']){
				obj.$refs['formName'].clearValidate();
			}
		});
	},
	saveEdit(){
		var data = this.ablity;
		this.$refs['formName'].validate((valid) => {
      if (valid) {
				this.postHttp(this,data,'subjectablity/saveSubjectAblity',function(obj,res){
					if(res.code == '10000'){
						obj.notify_success();
						obj.dialogVisible = false;
					}else{
						obj.notify_jr(obj,'操作错误',res.message,'error');
					}
				})
      } else {
        return false;
      }
    });
	},
	colseDia(){
		this.dialogVisible = false;
		this.ablityArray = [];
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
		data["subjectName"] = this.queryInfos.subjectName;
		return data;
	},
  }
}
</script>

<style>
#subjectInfo .subject_info_search{
	width: 90%;
	margin:20px auto;
}
#subjectInfo .subject_info_table{
	width: 90%;
	margin:20px auto;
}
#subjectInfo .colrs{
	width:100px;
	height:30px;
	line-height: 30px;
	color:#fff;
	margin:auto;
	border-radius: 10px;
}
</style>
