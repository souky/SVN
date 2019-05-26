<template>
	<div id="userManager" class="main_body">
		<div class="user_info_search">

			<el-row id="queryForm" :model="queryInfos" :gutter="20">
			  <el-col class="queryItems" :span="6">
			  	<div class="l">姓名</div>
			  	<div class="r">
			  		<el-input v-model="queryInfos.name" clearable placeholder="姓名"></el-input>
			  	</div>
			  </el-col>
			  <el-col class="queryItems" :span="6">
			  	<div class="l">用户名</div>
			  	<div class="r">
			  		<el-input v-model="queryInfos.userName" clearable placeholder="用户名"></el-input>
			  	</div>
			  </el-col>
			  <el-col class="queryItems" :span="6">
			  	<div class="l">角色</div>
			  	<div class="r">
			  		<el-select v-model="queryInfos.roleId" clearable placeholder="请选择">
					    <el-option v-for="item in roleOptions" :key="item.id" :label="item.roleName" :value="item.id"></el-option>
					  </el-select>
			  	</div>
			  </el-col>
			  <el-col :span="6">
			  	<div class="btn_query r" @click="queryInfo">
			  		<i class="el-icon-search">查询</i>
			  	</div>
			  </el-col>
			</el-row>

		</div>

		<div class="user_info_table">
			<div class="tools fix">
				<div class="items_tools l" @click="addNew" v-if="userT.userType != 0">
					<i class="el-icon-circle-plus-outline">新增</i>
				</div>
			</div>
			<el-table :data="tableData" style="width: 100%">
		      <el-table-column prop="name" align="center" label="姓名"></el-table-column>
		      <el-table-column prop="userName" align="center"  label="用户名"></el-table-column>
		      <el-table-column prop="roleName" align="center"  label="角色"></el-table-column>
		      <el-table-column align="center" label="操作" width='300'>
		      	<template slot-scope="scope">
		      		<el-button type="primary" icon="el-icon-edit" @click="editInfo(scope.row.id)">编辑</el-button>
		      		<el-button type="primary" icon="el-icon-delete" @click="deleteInfo(scope.row.id)">删除</el-button>
		      		<el-button type="primary" icon="el-icon-edit" @click="initPsw(scope.row.id)">重置密码</el-button>
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
		  	<el-form label-position="right" :rules="rules" ref="user" class="demo-ruleForm" label-width="80px" :model="user">
			  <el-form-item label="名称" prop="name">
			    <el-input v-model="user.name" :maxlength="nameMax"></el-input>
			  </el-form-item>
			  <el-form-item label="用户名" prop="userName">
			    <el-input v-model="user.userName" :maxlength="userNameMax" :disabled="editDisabled"></el-input>
			  </el-form-item>
			  <el-form-item label="用户类型"  prop="userType">
			  	<el-select v-model="user.userType" placeholder="请选择">
				    <el-option v-for="item in userTypeO" :key="item.id" :label="item.value" :value="item.id"></el-option>
				</el-select>
			  </el-form-item>
			  <el-form-item label="用户角色"  prop="roleId">
			    <el-select v-model="user.roleId" name="selectRoleName" placeholder="请选择">
				    <el-option  v-for="item in roleOptions" :key="item.id" :label="item.roleName" :value="item.id"></el-option>
				</el-select>
			  </el-form-item>
			  <el-form-item label="备注" prop="remark">
			    <el-input type="textarea" v-model="user.remark" :maxlength="200"></el-input>
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
    return {
      msg: 'userManager',
      tableData:[],
      queryInfos:{
	  	name:'',
	  	userName:'',
	  	roleId:''
	  },
	  pageNum:1,
      pageSize:10,
      total:1,
      roleOptions:[],
      editDisabled:false,
      nameMax:10,
      userNameMax:64,

      dialogVisible:false,
      diaTitle:'新增',
      user:{},
			userT:{},
      showPsw:true,

      userTypeO:[
      	{
      		id:2,
      		value:'自定义用户'
      	},
      	{
      		id:3,
      		value:'区域用户'
      	},
      	{
      		id:4,
      		value:'校级用户'
      	},
      	{
      		id:5,
      		value:'年级用户'
      	},
      	{
      		id:6,
      		value:'个人用户'
      	}
      ],

       rules: {
          name: [
            { required: true, message: '请输入名字', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          userType: [
            { required: true, message: '请选择用户类型', trigger: 'change' }
          ],
          roleId: [
            { required: true, message: '请选择用户角色', trigger: 'change' }
          ],
      }
    }
  },
  mounted:function(){
  	this.queryInfo();
  	this.postHttp(this,{pageNum:1,pageSize:0},'role/queryRoles',function(obj,res){
			var list = res.result.list;
			for(var i in list){
				if(list[i].roleName == "管理员"){
					list.splice(i,1);
					break;
				}
			}
  		obj.roleOptions = res.result.list;
  	})
  	//权限处理
  	this.postHttp(this,{pageNum:1,pageSize:0},'role/queryRoles',function(obj,res){
  		obj.roleOptions = res.result.list;
  	});
		this.postHttp(this,{},'user/getLoginUser',function(o,res){
  		o.userT = res.result;
  	})
  },
  methods:{
	queryInfo(){
  		var datas = this.ajaxData();
  		this.postHttp(this,datas,'user/queryUsers',function(obj,res){
	  		obj.pageNum = res.result.pageNum;
	  		obj.pageSize = res.result.pageSize;
	  		obj.total = res.result.total;
	  		obj.tableData = res.result.list;
	  	})
  	},
  	colseDia(){
  		this.dialogVisible = false;
  		this.user = {};
  	},
  	saveEdit(){
  		var id = this.user.id;
  		var address = 'user/saveUser';
  		this.user["roleName"] = document.getElementsByName("selectRoleName")[0].value;
  		this.user["phone"] = this.user.userName;
  		if(id){
  			address = 'user/updateUser';
  			delete this.user.createDate;
  			delete this.user.updateDate;
  			this.$refs['user'].validate((valid) => {
	          if (valid) {
	          	this.postHttp(this,this.user,address,function(obj,res){
					if(res.code == '10000'){
						obj.dialogVisible = false;
						obj.notify_success();

					}else{
						obj.notify_jr(obj,'操作错误',res.message,'error');
					}
				})
	          } else {
	            return false;
	          }
	        });
  		}else{
  			this.$refs['user'].validate((valid) => {
	          if (valid) {
	          	this.postHttp(this,this.user,address,function(obj,res){
								if(res.code == '10000'){
									obj.dialogVisible = false;
									obj.queryInfo();
									obj.$alert('新增成功,初始密码为:Aa111111', '提示', {confirmButtonText: '确定'});
								}else{
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
  		this.showPsw = true;
  		this.user = {};
  		this.editDisabled=false;
  		this.diaTitle = "新增";
  		if(this.$refs['user']){
  			this.$refs['user'].resetFields();
  		}
  	},
	editInfo(id){
		this.diaTitle = "编辑";
		if(this.$refs['user']){
  			this.$refs['user'].resetFields();
  		}
  		this.editDisabled=true;
		this.postHttp(this,{id:id},'user/getUserById',function(obj,res){
  			if(res.code == '10000'){
  				obj.user = res.result;
  				obj.showPsw = false;
  				obj.dialogVisible = true;
  			}else{
  				obj.notify_jr(obj,'操作错误',res.message,'error');
  			}
  		})
	},
	deleteInfo(id){
		this.$confirm('此操作将删除该用户信息,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        	this.postHttp(this,{id:id},'user/deleteUser',function(obj,res){
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
	initPsw(id){
		this.postHttp(this,{id:id},'user/initPsw',function(obj,res){
	  		if(res.code == "10000"){
	  			obj.$alert('重置密码成功,密码为:Aa111111', '提示', {confirmButtonText: '确定'});
	  		}else{
	  			obj.notify_jr(obj,'操作错误',res.message,'error');
	  		}
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
		data["name"] = this.queryInfos.name;
		data["userName"] = this.queryInfos.userName;
		data["roleId"] = this.queryInfos.roleId;
		return data;
	}
  }
}
</script>

<style>
#userManager .user_info_search{
	width: 90%;
	margin:20px auto;
}
#userManager .user_info_table{
	width: 90%;
	margin:20px auto;
}
</style>
