<template>
	<div id="orgManager" class="main_body">
		<div class="org_body fix">
			<div class="left_part l">
				<div class="title">机构树</div>
				<el-input placeholder="请输入机构名称" clearable v-model="filterText"></el-input>
				<el-tree :data="data" :props="defaultProps" ref="orgTree" accordion @node-click="handleNodeClick" :filter-node-method="filterNode"></el-tree>
			</div>
			<div class="right_part r">
				<div class="title">机构详细</div>
				<div class="tools fix">
					<div class="items_tools r" @click="addNew">
						<i class="el-icon-circle-plus-outline" >新增</i>
					</div>
				</div>
				<el-table :data="tableData" style="width: 100%">
			      <el-table-column prop="orgName" align="center" label="名称"></el-table-column>
			      <el-table-column prop="managerCount" align="center" label="管理员数量"></el-table-column>
			      <el-table-column align="center" label="操作" width='400'>
			      	<template slot-scope="scope">
			      		<el-button type="primary" icon="el-icon-plus" @click="addManager(scope.row.id)">添加管理员</el-button>
			      		<el-button type="primary" icon="el-icon-edit" @click="editInfo(scope.row.id)">编辑</el-button>
			      		<el-button type="primary" icon="el-icon-delete" @click="deleteInfo(scope.row.id)">删除</el-button>
			      	</template>
			      </el-table-column>
			    </el-table>
			</div>
		</div>

		<el-dialog :title="diaTitle" :visible.sync="dialogVisible" width="30%">
		  <div class="dialog_body">
		  	<el-form label-position="right" label-width="80px" :rules="rules" ref="org" class="demo-ruleForm" :model="org">
			  <el-form-item label="名称" prop="orgName">
			    <el-input v-model="org.orgName" :maxlength="64"></el-input>
			  </el-form-item>
			  <el-form-item label="省" v-show="showInfo" >
			  	<el-select v-model="p_name" @change="changeP" placeholder="请选择">
			    	<el-option v-for="item in pOption" :key="item.id" :label="item.regionName" :value="item.id"></el-option>
			  	</el-select>
			  </el-form-item>
			  <el-form-item label="市" v-show="showInfo">
			   <el-select v-model="c_name" @change="changeC" placeholder="请选择">
			    	<el-option v-for="item in cOption" :key="item.id" :label="item.regionName" :value="item.id"></el-option>
			  	</el-select>
			  </el-form-item>
			  <el-form-item label="区" prop="regionCode"  v-show="showInfo">
			  	<el-select v-model="org.regionCode" placeholder="请选择">
			    	<el-option v-for="item in aOption" :key="item.id" :label="item.regionName" :value="item.id"></el-option>
			  	</el-select>
			  </el-form-item>
			</el-form>
		  </div>
		  <span slot="footer" class="dialog-footer">
		    <el-button type="primary" @click="colseDia">取 消</el-button>
		    <el-button type="primary" @click="saveEdit">确 定</el-button>
		  </span>
		</el-dialog>

		<el-dialog title="添加管理员" :visible.sync="showManager" width="30%">
		  <div class="dialog_body">
		  	<el-form label-position="right" label-width="80px" :rules="rulesU" ref="user" class="demo-ruleForm" :model="user">
			  <el-form-item label="名称" prop="name">
			    <el-input v-model="user.name" :maxlength="20" ></el-input>
			  </el-form-item>
			  <el-form-item label="用户名" prop="userName">
			    <el-input v-model="user.userName" :maxlength="20"></el-input>
			  </el-form-item>
			  <el-form-item label="备注"  >
			    <el-input v-model="user.remark" :maxlength="200"></el-input>
			  </el-form-item>
			</el-form>
		  </div>
		  <span slot="footer" class="dialog-footer">
		    <el-button type="primary" @click="colseDiaU">取 消</el-button>
		    <el-button type="primary" @click="saveEditU">确 定</el-button>
		  </span>
		</el-dialog>

	</div>

</template>

<script>
export default {
  data () {
    return {
        msg: 'orgManager',
        data:[],
        defaultProps: {
	        children: 'children',
	        label: 'label'
	    },
        tableData:[],

        diaTitle:'新增',
        dialogVisible:false,
      	org:{},
      	showInfo:true,
      	pOption:[],
      	cOption:[],
      	aOption:[],
      	p_name:'',
      	c_name:'',

      	filterText:'',
      	managerOrgId:'',
      	showManager:false,
      	user:{
      	},

      	rules: {
          orgName: [
            { required: true, message: '请输入组织名称', trigger: 'blur' }
          ],
          regionCode: [
            { required: true, message: '请选择市', trigger: 'change' }
          ],
       },
      	rulesU:{
      		name: [
            { required: true, message: '请输入名字', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],

      	}
    }
  },
  watch: {
      filterText(val) {
        this.$refs.orgTree.filter(val);
      }
  },
  mounted:function(){

  	this.postHttp(this,{},'organization/loadOrganizations',function(obj,res){
  		obj.data = res.result;
  	});

  	this.postHttp(this,{regionPId:'000000'},'region/queryRegionsByParentId',function(obj,res){
  		obj.pOption = res.result;
  	});
  },
  methods:{
  	queryInfo(id){
  		this.postHttp(this,{regionCode:id},'organization/queryOrg',function(obj,res){
	  		obj.tableData = res.result;
	  	})
  	},
  	filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
    },
	handleNodeClick(data){
		if(data.children == null || data.children == undefined){
			this.queryInfo(data.id);
		}
	},
	colseDia(){
  		this.dialogVisible = false;
  		this.org = {};
  	},
  	saveEdit(){
  		var id = this.org.id;
  		delete this.org["createDate"];
  		delete this.org["updateDate"];
  		var address = 'organization/saveOrganization';
  		if(id){

  			address = 'organization/updateOrganization';
  			this.org.regionCode = "1";
  			this.$refs['org'].validate((valid) => {
	          if (valid) {
	          	var loading = this.loading('正在处理');
	          	delete this.org.regionCode;
	          	this.postHttp(this,this.org,address,function(obj,res){
		  			if(res.code == '10000'){
		  				obj.dialogVisible = false;
		  				obj.notify_success();
		  				obj.tableData = null;
		  				loading.close();
		  				obj.postHttp(obj,{},'organization/loadOrganizations',function(obj,res){
					  		obj.data = res.result;
					  	});
		  			}else{
		  				loading.close();
		  				obj.notify_jr(obj,'操作错误',res.message,'error');
		  			}
		  		})
	          } else {
	            return false;
	          }
	        });
  		}else{
  			this.$refs['org'].validate((valid) => {
	          if (valid) {
	          	var loading = this.loading('正在处理');
	          	this.postHttp(this,this.org,address,function(obj,res){
		  			if(res.code == '10000'){
		  				obj.dialogVisible = false;
		  				obj.notify_success();
		  				obj.tableData = null;
		  				loading.close();
		  				obj.postHttp(obj,{},'organization/loadOrganizations',function(obj,res){
					  		obj.data = res.result;
					  	});
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
  		this.org = {};
  		this.p_name = "";
  		this.c_name = "";

  		if(this.$refs['org']){
  			this.$refs['org'].resetFields();
  		}
	},
	editInfo(id){
		this.showInfo = false;
		this.dialogVisible = true;
		this.diaTitle = "编辑";
		if(this.$refs['org']){
  			this.$refs['org'].resetFields();
  		}
		this.postHttp(this,{id:id},"organization/getOrganizationById",function(obj,res){
			if(res.code == '10000'){
				obj.org = res.result;
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
        	this.postHttp(this,{id:id},'organization/deleteOrganization',function(obj,res){
		  		if(res.code == "10000"){
		  			obj.notify_success();
		  			obj.tableData = null;
	  				obj.postHttp(obj,{},'organization/loadOrganizations',function(obj,res){
				  		obj.data = res.result;
				  	});
		  		}else{
		  			obj.notify_jr(obj,'操作错误',res.message,'error');
		  		}
		  	});
        }).catch(() => {

        });
	},
	addManager(orgId){
		this.managerOrgId = orgId;
		this.showManager = true;
		if(this.$refs['user']){
  			this.$refs['user'].resetFields();
  		}
	},
	saveEditU(){
		this.$refs['user'].validate((valid) => {
          if (valid) {
          	this.user['orgId'] = this.managerOrgId;
			this.postHttp(this,this.user,'user/saveManagerUser',function(obj,res){
				if(res.code == '10000'){
					obj.showManager = false;
					obj.$alert('新建用户成功,初始密码为:Aa111111', '提示', {confirmButtonText: '确定'});
				}else{
					obj.notify_jr(obj,'操作错误',res.message,'error');
				}
			})
		  } else {
            return false;
          }
		});
	},
	colseDiaU(){
		this.showManager = false;
		this.user = {};
	},
	changeP(val){
		this.postHttp(this,{regionPId:val},'region/queryRegionsByParentId',function(obj,res){
	  		obj.cOption = res.result;
	  	});
	},
	changeC(val){
		this.postHttp(this,{regionPId:val},'region/queryRegionsByParentId',function(obj,res){
	  		obj.aOption = res.result;
	  	});
	}
  }
}
</script>

<style>
#orgManager .org_body{
	width:90%;
	margin:20px auto;
}
#orgManager .left_part{
	width: 30%;
}
#orgManager .left_part .title{
	width: 100%;
	text-align: center;
	cursor: default;
	margin-bottom: 10px;
}
#orgManager .right_part{
	width: 65%;
}
#orgManager .right_part .title{
	width: 100%;
	text-align: center;
	cursor: default;
	margin-bottom:10px
}
</style>
