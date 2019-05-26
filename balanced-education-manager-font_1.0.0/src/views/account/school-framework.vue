<template>
  <div id="schoolFrame" class="tree_main fix">
  	<div class="leftPart l">
  		<el-tree :data="organizadata" @node-click="loadOrg" :props="defaultProps" node-key="id" :indent="indents" default-expand-all :expand-on-click-node="false" :render-content="renderContent">
			</el-tree>
  	</div>
  	<div class="rightPart l">
  		<div class="topPart" v-show="false">
  			<el-table :data="tableData_t" stripe row-key="id" style="width:100%;margin-top: 20px;">
	    		<el-table-column prop="name" label="机构名称" align="center" show-overflow-tooltip> </el-table-column>
	    		<el-table-column prop="createDate" :formatter="timeFormat" label="创建时间" align="center" show-overflow-tooltip> </el-table-column>
	    		<el-table-column label="操作" align="center" show-overflow-tooltip width="150px">
	    			<template scope="scope">
			    			<i title="编辑" class="el-icon-edit" @click="dialogEdits(scope.row.id)"></i>
			    			<i title="删除" class="el-icon-delete" @click="dialogDelete(scope.row.id)"></i>
			    	</template>
	    		</el-table-column>
	    	</el-table>
	    	<el-pagination @size-change="handleSizeChange_t" @current-change="handleCurrentChange_t" :current-page.sync="pageNum_t"
				      :page-sizes="[10, 20, 50]"
				      :page-size="pageSize_t"
				      layout="sizes, prev, pager, next, jumper"
				      :total="total_t" class="tc mt20"
				      >
		    </el-pagination>
		    
  		</div>
  		<div class="bottomPart">
  			<div class="addNew" @click="add_equ()"><i class="el-icon-plus"></i>新建</div>
  			<el-table :data="tableData_b" stripe row-key="id" style="width:100%;margin-top: 20px;">
  				<el-table-column label="" align="center" width="50px"> 
  					<template scope="scope">
							<el-checkbox name="equCheckbox" :true-label="scope.row.id"></el-checkbox>
						</template>
  				</el-table-column>
  				<el-table-column  label="序号" align="center" show-overflow-tooltip width="80px">
  					<template scope="scope">
							{{scope.$index+1}}
						</template>
  				</el-table-column>
	    		<el-table-column prop="name" label="设备名称" align="center" show-overflow-tooltip> </el-table-column>
	    		<el-table-column prop="ip" label="设备IP" align="center" show-overflow-tooltip> </el-table-column>
	    		<el-table-column prop="address" label="设备地址" align="center" show-overflow-tooltip width="300px"> </el-table-column>
	    		<el-table-column label="操作" align="center" show-overflow-tooltip width="150px">
    			<template scope="scope">
		    			<i title="编辑" class="el-icon-edit" @click="dialogEdits_equ(scope.row.id)"></i>
		    			<i title="删除" class="el-icon-delete" @click="deleteEqu(scope.row.id)"></i>
		    	</template>
	    		</el-table-column>
	    	</el-table>
	    	<el-pagination @size-change="handleSizeChange_b" @current-change="handleCurrentChange_b" :current-page.sync="pageNum_b"
				      :page-sizes="[10, 20, 50]"
				      :page-size="pageSize_b"
				      layout="sizes, prev, pager, next, jumper"
				      :total="total_b" class="tc mt20"
				      >
		    </el-pagination>
  		</div>
  	</div>
		
		<el-dialog :visible.sync="dialogorganiza" size="tiny" :show-close="false">
			<div class="infoTitle">新增</div>
  		<div class="editBody">
  			<el-form :inline="true" class="demo-form-inline">
  				<div class="secondTitle">架构信息</div>
  				<el-form-item label="机构名称">
			      <el-input v-model="organzia_name" name="name"></el-input>
			    </el-form-item>
  			</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="add_organiza()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="dialogorganiza = false">取消</div>								
				</div>
			</div>
		</el-dialog>
		
		<el-dialog :visible.sync="dialogequ" size="tiny" :show-close="false">
			<div class="infoTitle">新增设备</div>
  		<div class="editBody">
  			<el-form :model="orgequ" :inline="true" class="demo-form-inline">
  				<div class="secondTitle">设备信息</div>
  				<el-form-item label="设备名称">
			      <el-input v-model="orgequ.name" name="name"></el-input>
			    </el-form-item>
			    <el-form-item label="设备IP">
			      <el-input v-model="orgequ.ip" name="name"></el-input>
			    </el-form-item>
			    <el-form-item label="设备地址">
			      <el-input v-model="orgequ.address" name="name"></el-input>
			    </el-form-item>
  			</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="addEqu()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="dialogequ = false">取消</div>								
				</div>
			</div>
		</el-dialog>
		
		<el-dialog :visible.sync="dialogequUpdate" size="tiny" :show-close="false">
			<div class="infoTitle">修改设备</div>
  		<div class="editBody">
  			<el-form :model="orgequ" :inline="true" class="demo-form-inline">
  				<div class="secondTitle">设备信息</div>
  				<el-form-item label="设备名称">
			      <el-input v-model="orgequ.name" name="name"></el-input>
			    </el-form-item>
			    <el-form-item label="设备IP">
			      <el-input v-model="orgequ.ip" name="name"></el-input>
			    </el-form-item>
			    <el-form-item label="设备地址">
			      <el-input v-model="orgequ.address" name="name"></el-input>
			    </el-form-item>
  			</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="updateEqu()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="dialogequUpdate = false">取消</div>								
				</div>
			</div>
		</el-dialog>
		
		
  </div>
</template>
<script>
  export default{
    data () {
      return {
        msg:'',
        indents:30,
        organizadata: [{
        }],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        dialogorganiza:false,
        organzia_name:'',
        org_id:'',
        handle_message:'',
        
        tableData_t:[],
        pageSize_t:10,
        pageNum_t:1,
        total_t:10,
        
     		tableData_b:[],
        pageSize_b:10,
        pageNum_b:1,
        total_b:10,
        dialogequ:false,
        orgequ:{},
        org_id_equ:'',
        
        dialogequUpdate:false,
      }
    },
    mounted:function(){
    	var data = {}
    	this.postHttp(this,data,"organization/queryOrganizations",this.init_organiza);
    },
    methods: {

      renderContent(createElement, { node, data, store }) {
        var self = this;  
        return createElement('span', [  
            createElement('span', node.label),  
            createElement('span', {attrs:{  
                style:"float: right; margin-right: 20px",class:"tree_btn"  
            }},[  
                createElement('i',{attrs:{  
                    class:'el-icon-plus'
                },on:{  
                    click:function() {  
                    	self.org_id = data.id;
                    	self.dialogorganiza = true;
                    	self.organzia_name = "";
                    }  
                }},""),  
                createElement('i',{attrs:{  
                     class:'el-icon-minus'  
                },on:{  
                    click:function() {  
                    	self.org_id = data.id;
                    	self.$confirm('此操作将删除该机构下所有子机构, 是否继续?', '提示', {
							          confirmButtonText: '确定',
							          cancelButtonText: '取消',
							          type: 'warning'
							        }).then(() => {
							        	self.delete_organiza();
							        }).catch(() => {
							        	
							        });
                    }  
                }},""),  
            ]),  
        ]);  
      },
      
      init_organiza(obj,data){
      	this.organizadata = data.result.orgList;
      },
      add_organiza(){
      	var parentId = this.org_id;
      	var orgName = this.organzia_name;
      	var data = {parentId:parentId,name:orgName};
      	this.handle_message = "机构新增";
      	this.postHttp(this,data,"organization/saveOrganization",this.org_handle);
      },
      delete_organiza(){
      	var id = this.org_id;
      	var data = {id:id};
      	this.handle_message = "机构删除";
      	this.postHttp(this,data,"organization/deleteOrganization",this.org_handle);
      },
      org_handle(obj,data){
      	var code = data.code;
      	if(code=="10000"){
      		this.postHttp(this,data,"organization/queryOrganizations",this.init_organiza);
      		this.dialogorganiza = false;
      		this.notify_jr(this,this.handle_message,"操作成功","success");
      	}else{
      		this.notify_jr(this,this.handle_message,data.message,"error");
      	}
      },
      
      handleSizeChange_t(val){
      	
      },
      handleCurrentChange_t(val){
      	
      	
      },
      
      handleSizeChange_b(val){
      	var dataS = {
					pageNum:1,
					pageSize:val,
					orgId:this.orgId_s
				}
				this.postHttp(this,data,"equipment/queryEquipments",function(obj,data){
					obj.tableData_b = data.result.list;
					obj.pageNum_b = data.result.pageNum;
					obj.pageSize_b = data.result.pageSize;
					obj.total_b = data.result.total;
				})
      },
      handleCurrentChange_b(val){
      	var dataS = {
					pageNum:val,
					pageSize:this.pageSize_b,
					orgId:this.orgId_s
				}
				this.postHttp(this,data,"equipment/queryEquipments",function(obj,data){
					obj.tableData_b = data.result.list;
					obj.pageNum_b = data.result.pageNum;
					obj.pageSize_b = data.result.pageSize;
					obj.total_b = data.result.total;
				})
      },
      loadOrg(data,node,store){
//    	var parentId = data.id;
//    	var data = {parentId:parentId};
//    	this.postHttp(this,data,"organization/getOrganizations",function(obj,data){
//    		obj.tableData_t = data.result;
//    	})
				this.orgId_s = data.id;
				var dataS = {
					pageNum:this.pageNum_b,
					pageSize:this.pageSize_b,
					orgId:this.orgId_s
				}
				this.postHttp(this,dataS,"equipment/queryEquipments",function(obj,data){
					obj.tableData_b = data.result.list;
					obj.pageNum_b = data.result.pageNum;
					obj.pageSize_b = data.result.pageSize;
					obj.total_b = data.result.total;
				})
      },
      timeFormat(row, column,val){
      	var time = this.timeF(new Date(val)).format("YYYY-MM-DD HH:mm:ss");
      	return time;
      },
      add_equ(){
      	this.orgequ = {};
      	this.dialogequ = true;
      },
      addEqu(){
      	var data = this.orgequ;
      	var orgId = this.orgId_s;
      	if(orgId==""){
      		return;
      	}
      	data['orgId'] = orgId;
      	this.postHttp(this,data,"equipment/saveEquipment",function(obj,data){
      		if(data.code == "10000"){
      			obj.notify_jr(obj,'新增设备','成功',"success");
      			var dataS = {
							pageNum:obj.pageNum_b,
							pageSize:obj.pageSize_b,
							orgId:obj.orgId_s
						}
						obj.postHttp(obj,dataS,"equipment/queryEquipments",function(obj,data){
							obj.tableData_b = data.result.list;
							obj.pageNum_b = data.result.pageNum;
							obj.pageSize_b = data.result.pageSize;
							obj.total_b = data.result.total;
							obj.dialogequ = false;
						})
      		}else{
      			obj.notify_jr(obj,'新增设备','失败',"error");
      		}
      	})
      },
      dialogEdits_equ(id){
      	var data = {id:id}
      	this.postHttp(this,data,"equipment/getEquipmentById",function(obj,data){
      		obj.orgequ = data.result;
      		obj.dialogequUpdate = true;
      	})
      },
      updateEqu(){
      	delete this.orgequ['creatDate'];
      	delete this.orgequ['updateDate'];
      	this.postHttp(this,this.orgequ,"equipment/updateEquipment",function(obj,data){
      		if(data.code == "10000"){
      			obj.notify_jr(obj,'编辑设备','成功',"success");
      			var dataS = {
							pageNum:obj.pageNum_b,
							pageSize:obj.pageSize_b,
							orgId:obj.orgId_s
						}
						obj.postHttp(obj,dataS,"equipment/queryEquipments",function(obj,data){
							obj.tableData_b = data.result.list;
							obj.pageNum_b = data.result.pageNum;
							obj.pageSize_b = data.result.pageSize;
							obj.total_b = data.result.total;
							obj.dialogequUpdate = false;
						})
      		}else{
      			obj.notify_jr(obj,'编辑设备','失败',"error");
      		}
      	})
      },
      deleteEqu(id){
      	var dataS = {id:id}
      	this.postHttp(this,dataS,"equipment/deleteEquipment",function(obj,data){
      		if(data.code == "10000"){
      			obj.notify_jr(obj,'删除设备','成功',"success");
      			var dataS = {
							pageNum:obj.pageNum_b,
							pageSize:obj.pageSize_b,
							orgId:obj.orgId_s
						}
						obj.postHttp(obj,dataS,"equipment/queryEquipments",function(obj,data){
							obj.tableData_b = data.result.list;
							obj.pageNum_b = data.result.pageNum;
							obj.pageSize_b = data.result.pageSize;
							obj.total_b = data.result.total;
							obj.dialogequUpdate = false;
						})
      		}else{
      			obj.notify_jr(obj,'删除设备','失败',"error");
      		}
      	})
      }
      
    }
  }
  
  
</script>

<style>
#schoolFrame .el-tree{background:#f5f5f5;border:none;}
#schoolFrame {padding:20px;}

#schoolFrame .leftPart i{font-size:14px;padding:3px;margin-right:20px;border-radius:2px;}
#schoolFrame .leftPart .el-icon-plus{color:#EF5350;border:1px #EF5350 solid;}
#schoolFrame .el-icon-minus{color:#FFCA28;border:1px #FFCA28 solid;}
#schoolFrame .el-tree-node__expand-icon{border-left-color:#202a33;}
#schoolFrame .editBody{min-height: 110px;}
#schoolFrame .el-message-box{vertical-align: unset;}
#schoolFrame .el-message-box .el-button--primary{background:#66BB6A;border-color:#66BB6A;}
#schoolFrame .editBody .el-form-item{width:100%}
#schoolFrame .editBody .el-form-item__label{width:22%}
#schoolFrame .editBody .el-form-item__content{width:61%;}

#schoolFrame .leftPart{
	width:35%;
}
#schoolFrame .rightPart{
	width:65%;
}
#schoolFrame .rightPart .topPart{
	min-height: 400px;
}
#schoolFrame .rightPart .bottomPart{
	min-height: 400px;
}

#schoolFrame .rightPart .el-table i{
    margin: auto 4px;
    font-size: 22px;
    cursor: pointer;
}

#schoolFrame .addNew {
    padding: 20px 30px 0px 30px;
    cursor: pointer;
    width:85px;
}
#schoolFrame .addNew i {
    border: 1px #272727 solid;
    color: #272727;
    padding: 2px;
    margin-right: 6px;
    border-radius: 2px;
}
</style>