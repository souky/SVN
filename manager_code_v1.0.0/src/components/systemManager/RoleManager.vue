<template>
	<div id="roleManager" class="main_body">
		<div class="role_info_search">

			<el-row id="queryForm" :model="queryInfos" :gutter="20">
			  <el-col class="queryItems" :span="6">
			  	<div class="l">名称</div>
			  	<div class="r">
			  		<el-input v-model="queryInfos.roleName" clearable placeholder="名称"></el-input>
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

		<div class="role_info_table">
			<div class="tools fix">
				<div class="items_tools l" @click="addNew" v-if="user.userType != 0">
					<i class="el-icon-circle-plus-outline" >新增</i>
				</div>
			</div>
			<el-table :data="tableData" style="width: 100%">
		      <el-table-column prop="roleName" align="center" label="名称"></el-table-column>
		      <el-table-column prop="remark" align="center"  label="备注"></el-table-column>
		      <el-table-column align="center" label="操作" width='300'>
		      	<template slot-scope="scope" >
		      		<div v-if="scope.row.orgId != '' || user.userType == '0' ">
		      			<el-button type="primary" icon="el-icon-menu"  @click="authorizationInfo(scope.row.id)">授权</el-button>
			      		<el-button type="primary" icon="el-icon-edit" @click="editInfo(scope.row.id)">编辑</el-button>
			      		<el-button type="primary" icon="el-icon-delete" @click="deleteInfo(scope.row.id)">删除</el-button>
		      		</div>
		      		<div v-else>
		      			<el-button type="primary forbid" title="预置角色不允许修改" icon="el-icon-menu">授权</el-button>
			      		<el-button type="primary forbid" title="预置角色不允许修改" icon="el-icon-edit">编辑</el-button>
			      		<el-button type="primary forbid" title="预置角色不允许修改" icon="el-icon-delete">删除</el-button>
		      		</div>
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
		  	<el-form label-position="right" :rules="rules" ref="role" class="demo-ruleForm" label-width="80px" :model="role">
			  <el-form-item label="名称" prop="roleName">
			    <el-input v-model="role.roleName" :maxlength="20"></el-input>
			  </el-form-item>
			  <el-form-item label="备注" prop="remark">
			    <el-input type="textarea" v-model="role.remark" :maxlength="200"></el-input>
			  </el-form-item>
			</el-form>
		  </div>
		  <span slot="footer" class="dialog-footer">
		    <el-button type="primary" @click="colseDia">取 消</el-button>
		    <el-button type="primary" @click="saveEdit">确 定</el-button>
		  </span>
		</el-dialog>

		<el-dialog title="授权" :visible.sync="dialogVisibleAuth" width="50%">
			<el-tree id="textBookT" :data="authorization" ref="tree" check-strictly :default-checked-keys="authKey" @check-change="handleChange"  node-key="id" :props="defaultProps" show-checkbox></el-tree>
			<span slot="footer" class="dialog-footer">
			    <el-button type="primary" @click="closeAuth">取 消</el-button>
			    <el-button type="primary" @click="saveAuth">确 定</el-button>
			</span>
		</el-dialog>
	</div>

</template>

<script>
let id = 1000;
export default {
  data () {
		var validatename = (rule, value, callback) => {
			var reg = /^[\u4E00-\u9FA5]+$/;
			if(!reg.test(value)){
	          callback(new Error('角色名称必须为中文'));
	        }else{
	        	callback();
        }
    };
    return {
      msg: 'userManager',
      tableData:[],
      queryInfos:{
	  	roleName:''
	  },
	  pageNum:1,
      pageSize:10,
      total:1,

      dialogVisible:false,
      diaTitle:'新增',

      authorization:[],
      dialogVisibleAuth:false,
      defaultProps: {
	        children: 'children',
	        label: 'permissionName',
	        id:'id'
	  },
	  authKey:[],
	  roleIdAuth:"",
	  user:{},
      isdeep:true,

      role:{},
      rules: {
          roleName: [
            { required: true,validator:validatename, trigger: 'blur' }
          ],
      }
    }
  },
  mounted:function(){
  	this.queryInfo();
  	this.postHttp(this,{},'user/getLoginUser',function(o,res){
  		o.user = res.result;
  	})
  },
  methods:{
	queryInfo(){
  		var datas = this.ajaxData();
	  	this.postHttp(this,datas,'role/queryRoles',function(obj,res){
	  		obj.pageNum = res.result.pageNum;
	  		obj.pageSize = res.result.pageSize;
	  		obj.total = res.result.total;
	  		obj.tableData = res.result.list;
	  	})
  	},
  	colseDia(){
  		this.dialogVisible = false;
  		this.role = {};
  	},
  	handleChange(nodes,bo,childs){
  		if(bo){
  			if(nodes.children != undefined && this.isdeep){
  				for(var i = 0;i<nodes.children.length;i++){
  					if(this.authKey.indexOf(nodes.children[i].id) == -1){
  						this.authKey.push(nodes.children[i].id);
  						this.$refs.tree.setChecked(nodes.children[i].id,true,false);
  					}
  				}
  			}
  			if(this.authKey.indexOf(nodes.id) == -1){
  				this.authKey.push(nodes.id);
  				if(nodes.pid != undefined){
  					this.isdeep = false;
  					if(this.authKey.indexOf(nodes.pid) != -1){
  						this.isdeep = true;
  					}else{
  						this.authKey.push(nodes.pid);
  						this.$refs.tree.setChecked(nodes.pid,true,false);
  					}
  				}else{
  					this.isdeep = true;
  				}
  			}else{
  				this.isdeep = true;
  			}

  			this.$refs.tree.setChecked(nodes.id,true,false);
  		}else{
  			var index = this.authKey.indexOf(nodes.id);
  			if(index >= 0){
				this.authKey.splice(index,1);
			}
  			if(nodes.children != undefined && this.isdeep){
  				for(var i = 0;i<nodes.children.length;i++){
  					var indexs = this.authKey.indexOf(nodes.children[i].id);
  					if(indexs >= 0){
  						this.authKey.splice(indexs,1);
  						this.$refs.tree.setChecked(nodes.children[i].id,false,false);
  					}
  				}
  			}
  			this.$refs.tree.setChecked(nodes.id,false,false);
  		}
  	},
  	saveEdit(){
  		var id = this.role.id;
  		var address = 'role/saveRole';
  		if(id){
  			address = 'role/updateRole';
  		}
			delete this.role.createDate;
			delete this.role.updateDate;

  		this.$refs['role'].validate((valid) => {
          if (valid) {
          	this.postHttp(this,this.role,address,function(obj,res){
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
  		this.role = {};
  		this.diaTitle = "新增";
  		if(this.$refs['role']){
  			this.$refs['role'].resetFields();
  		}
  	},
	editInfo(id){
		this.diaTitle = "编辑";
		this.dialogVisible = true;
		if(this.$refs['role']){
  			this.$refs['role'].resetFields();
  		}
		this.postHttp(this,{id:id},'role/getRoleById',function(obj,res){
  			if(res.code == '10000'){
  				obj.role = res.result;
  			}else{
  				obj.notify_jr(obj,'操作错误',res.message,'error');
  			}
  		})
	},
	deleteInfo(id){
		this.$confirm('此操作将删除该角色信息,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        	this.postHttp(this,{id:id},'role/deleteRole',function(obj,res){
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
	authorizationInfo(id){
		this.dialogVisibleAuth = true;
		this.roleIdAuth = id;
		this.postHttp(this,{roleId:id},'permission/queryPermission',function(obj,res){
			var s = res.result
	  		obj.authorization = s;
	  		var arrays = new Array();
	  		for(var i = 0;i < s.length;i++){
	  			var o = s[i];
	  			if(o.isHasPermission == 1){
	  				arrays.push(o.id);
	  			}
	  			if(o.children != "" && o.children != undefined){
	  				var oo = o.children;
	  				for(var j = 0;j < oo.length;j++){
	  					if(oo[j].isHasPermission == 1){
			  				arrays.push(oo[j].id);
			  			}
	  					oo[j]['pid'] = o.id;
	  				}
	  			}
	  		}
	  		obj.authKey = arrays;
		});
	},
	saveAuth(){
		var s = this.authKey;
		var id = this.roleIdAuth;
		var Objects = new Object();
		Objects['roleId'] = id;
		Objects['permissionIds'] = s;
		this.postHttp(this,Objects,'permission/grantPermissions',function(obj,res){
			if(res.code == "10000"){
				obj.dialogVisibleAuth = false;
				obj.notify_success();
			}else{
				obj.notify_jr(obj,'操作错误',res.message,'error');
			}
		})

	},
	closeAuth(){
		this.dialogVisibleAuth = false;
		this.authKey = [];
		this.authorization = [];
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
	ajaxData(){
		var data = new Object();
		data["pageSize"] = this.pageSize;
		data["pageNum"] = this.pageNum;
		data["roleName"] = this.queryInfos.roleName;
		return data;
	}
  }
}
</script>

<style>
#roleManager .role_info_search{
	width: 90%;
	margin:20px auto;
}
#roleManager .role_info_table{
	width: 90%;
	margin:20px auto;
}
#roleManager .forbid{
	background:#C0C0C0!important;
	border-color: #C0C0C0!important;
	color:#828282!important;
}
</style>
