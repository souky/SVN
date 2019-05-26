<template>
  <div id="role" class="role-container">
		
		<div class="search_role fix">
			<div class="l">
				<el-input v-model="key_word"></el-input>
			</div>
			<div class="r poi" @click="query_role()">
				搜索
			</div>
		</div>
		
		<div class="addNew" @click="dialogadd()">
    	<i class="el-icon-plus" ></i>新建
    </div>
    
    <div class="main-container">
      <!--表格部分-->
      <el-table :data="tableData" stripe row-key="id" style="width:100%;margin-top: 20px;">
    		<el-table-column prop="roleName" label="角色名称" align="center"	width="250px" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="createDate" :formatter="timeFormat" label="创建时间" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column label="操作" align="center" show-overflow-tooltip width="150px">
    			<template scope="scope">
		    			<i title="编辑" class="el-icon-edit" @click="dialogEdits(scope.row.id)"></i>
		    			<i title="删除" class="el-icon-delete" @click="dialogDelete(scope.row.id)"></i>
		    	</template>
    		</el-table-column>
    	</el-table>
    	
    	<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageNum"
			      :page-sizes="[10, 20, 50]"
			      :page-size="pageSize"
			      layout="sizes, prev, pager, next, jumper"
			      :total="total" class="tc mt20"
			      >
	    </el-pagination>
    </div>
    
    
    <el-dialog :visible.sync="dialog_role" size="tiny" :show-close="false">
    	<div class="infoTitle" :model="infoTitles">{{infoTitles}}</div>
  		<div class="editBody">
  			<el-form :inline="true" :model="role" :rules="rules" ref="role" class="demo-form-inline demo-ruleForm">
  				<div class="secondTitle">角色信息</div>
  				<el-form-item label="角色名称" prop="roleName">
			      <el-input v-model="role.roleName"></el-input>
			    </el-form-item>
  			</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="saveRole()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="dialog_role = false">取消</div>								
				</div>
			</div>
    </el-dialog>
  </div>
</template>

<script>
 	
  export default{
    data () {
      return {
        msg:'hello vue',
        key_word:'',
        tableData:null,
        dialog_role:false,
        infoTitles:'新增',
        role:{},
        pageNum:1,
        pageSize:10,
        total:1,
        rules: {
          roleName: [
            { required: true, message: '请输入角色名称', trigger: 'blur' },
          ]
        }
      }
    },
    methods:{
    	dialogadd(){
    		this.infoTitles = "新增";
    		this.role = {};
    		this.dialog_role = true;
    	},
    	dialogEdits(id){
    		this.infoTitles = "编辑";
    		this.dialog_role = true;
    		var data = {id:id}
    		this.postHttp(this,data,"role/getRoleById",this.roleInit);
    	},
    	roleInit(obj,data){
    		this.role = data.result;
    		delete this.role["createDate"];
    		delete this.role["updateDate"];
    	},
    	dialogDelete(id){
    		this.$confirm('此操作将删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        	var data = {id:id};
        	this.infoTitles = "删除";
        	this.postHttp(this,data,"role/deleteRole",this.ajax_handle);
        }).catch(() => {
        	
        });
    	},
    	roleTable(obj,data){
    		this.tableData = data.result.list;
    		this.pageNum = data.result.pageNum;
    		this.pageSize = data.result.pageSize;
    		this.total = data.result.total;
    	},
    	handleSizeChange(val) {
		  	var data = {pageNum:1,pageSize:val};
		  	this.postHttp(this,data,"role/queryRoles",this.roleTable);
		  },
		  handleCurrentChange(val) {
		  	var pageS = this.pageSize;
		  	var data = {pageNum:val,pageSize:pageS};
		    this.postHttp(this,data,"role/queryRoles",this.roleTable);
		  },
		  timeFormat(row,column){
		  	var date = row[column.property];  
		  	if (date == undefined) {  
	         return "";  
	      }  
	      return this.timeF(date).format("YYYY-MM-DD HH:mm:ss");  
		  },
		  saveRole(){
		  	this.$refs['role'].validate((valid) => {
          if (valid) {
          	var data = this.role;
		  			this.postHttp(this,data,"role/editRole",this.ajax_handle);
          }
        });
		  },
		  ajax_handle(obj,data){
		  	var code = data.code;
		  	if(code=="10000"){
		  		this.notify_jr(this,this.infoTitles,'操作成功','success');
		  		var datas = {pageNum:this.pageNum,pageSize:this.pageSize};
		  		this.postHttp(this,datas,"role/queryRoles",this.roleTable);
		  		this.dialog_role = false;
		  	}else{
		  		this.notify_jr(this,this.infoTitles,data.message,'error');
		  	}
		  },
		  query_role(){
		  	var roleName = this.key_word;
		  	var data = {pageNum:1,pageSize:10,roleName:roleName};
		  	this.postHttp(this,data,"role/queryRoles",this.roleTable);
		  }
    },
    mounted:function(){
    	var data = {pageNum:this.pageNum,pageSize:this.pageSize};
    	this.postHttp(this,data,"role/queryRoles",this.roleTable);
    }

  }
  
</script>

<style>
  #role .addNew{padding:20px 30px 0px 30px;cursor: pointer;}
	#role .addNew i{border:1px #272727 solid;color:#272727;padding:2px;margin-right:6px;border-radius: 2px;}
	
	#role {padding-top:20px;}
	#role .search_role{width:310px;margin: auto;}
	#role .search_role .el-input__inner{border-color:#66BB6A;width:240px;border-top-right-radius: 0px;border-bottom-right-radius: 0px;}
	#role .search_role .r{width:70px;height:36px;line-height: 36px;border-top-right-radius: 2px;border-bottom-right-radius: 2px;text-align: center;background:#66BB6A;
	color:#fff;}
	
	#role .editBody{min-height: 100px;}
	
	#role .el-form--inline .el-form-item{margin-right:0px;width:100%;}
	#role .infoBody .el-input__inner{width:220px;}
	#role .el-form-item__label{width:25%}
	#role .el-form-item__content{width:70%;}
	#role .el-cascader.is-opened .el-input__inner{border-color: #66BB6A;width:400px}
	#role .el-cascader .el-input__inner{width: 400px;}
</style>