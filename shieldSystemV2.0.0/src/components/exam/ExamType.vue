<template>
	<div id="examType" class="main_body">
		
		
		<div class="tables">
			<el-table :data="tableData" align='center' style="width: 100%" @selection-change="getSelected">
				<el-table-column type="selection" width="55"></el-table-column>
			    <el-table-column align='center' type="index" label="序号" :index="indexMethod"></el-table-column>
			    <el-table-column align='center' prop="examTypeNo" label="考试类型编号"></el-table-column>
					<el-table-column align='center' prop="examTypeName" label="考试类型名称"></el-table-column>
			    <el-table-column align='center' width="140" class-name="operations" label="操作">
			    		<template slot-scope="scope">
								<div class="op_items">
									<el-button size="mini" @click="edit(scope.row.id)">编辑</el-button>
								</div>
			    			<div class="op_items">
			    				<el-button size="mini" type="danger" @click="deletes(scope.row.id)">删除</el-button>
			    			</div>
			      </template>
			    </el-table-column>
			</el-table>
			<div class="deleteRows sec">
				<el-button type="primary" size="mini" @click="deleteRows">批量删除</el-button>
			</div>
			<div class="add">
				<el-button type="primary" size="mini" @click="addType">添加类型</el-button>
			</div>
			
			<el-pagination @size-change="pageSizeChange" @current-change="pageChange" background :current-page.sync="pageNum"
		      :page-sizes="[10, 20, 30, 40]"
		      :page-size="pageSize"
		      layout="total,sizes,prev, pager, next,jumper" :total="total">
		    </el-pagination>
		</div>
		
		<!-- 弹窗 -->
		<el-dialog :title="title" :visible.sync="editDialog" width="30%">
			<el-form style="width:80%;margin:auto" :model="examType" label-width="80px">
				<el-form-item label="类型编号">
					<el-input v-model="examType.examTypeNo"></el-input>
				</el-form-item>
				<el-form-item label="类型名称">
					<el-input v-model="examType.examTypeName"></el-input>
				</el-form-item>
				<el-form-item style="text-align: right;">
					<el-button style="width:auto;" @click="editDialog = false">取消</el-button>
					<el-button style="width:auto;" type="primary" @click="save">保存</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
		
	</div>
</template>

<script>
export default {
	  data () {
	    return {
	      msg: 'clockList',
	      
	      tableData:[],
	      selectData:[],
				editDialog:false,
				title:'',
				examType:{},
				pageNum:1,
				pageSize:10,
				total:1
	    }
	  },
	  mounted:function(){
			this.loadData();
	  },
	  methods:{
	  	
	  	indexMethod(index){
	  		return index + 1;
	  	},
	  	// 获取checkbox选择的条
	  	getSelected(val) {
	        this.selectData = val;
	    },
	    // 数据操作
	    edit(id){
	    		this.postHttp("shieldexamtype/getShieldExamTypeById",{id:id},res=>{
						if(res.code == '10000'){
							this.editDialog = true;
							this.examType = res.result;
							this.title = "编辑类型";
						}
					});
	    },
			save(){
				var id = this.examType.id;
				var no = this.examType.examTypeNo;
				var name = this.examType.examTypeName;
				if(!no || no == ''){
					this.notify_warning("编号不能为空");
					return;
				}
				if(!name || name == ''){
					this.notify_warning("名称不能为空");
					return;
				}
				var url = '';
				if(id == '' || !id){
					// 新增
					url = 'shieldexamtype/saveShieldExamType';
				}else{
					// 编辑
					url = 'shieldexamtype/updateShieldExamType';
				}
				
				this.postHttp(url,this.examType,res=>{
					if(res.code == '10000'){
						this.notify_success("操作成功");
						this.examType = {};
						this.loadData();
						this.editDialog = false;
					}else{
						this.notify_error(res.message);
					}
				})
			},	
	    deletes(id){
	    		this.confim_jy('确定要删除此条信息?','提示',res =>{
	    			this.postHttp("shieldexamtype/deleteShieldExamType",{id:id},res=>{
	    				if(res.code == 10000){
	    					this.notify_success("删除成功!");
	    					this.loadData();
	    				}else{
	    					this.notify_error(res.message)
	    				}
	    			})
	    		})
	    },
	    // 批量删除
	    deleteRows(){
	    		var ids = [];
	    		for(var x in this.selectData){
	    			ids.push(this.selectData[x].id);
	    		}
	    		if(ids.length == 0){
	    			this.notify_warning("请选择删除项");
	    			return;
	    		}
	    		this.confim_jy('确定要删除这几条信息?','提示',res =>{
	    			this.postHttp("shieldexamtype/deleteShieldExamTypes",{ids:ids.join(",")},res=>{
	    				if(res.code == 10000){
	    					this.notify_success("删除成功!");
	    					this.loadData();
	    				}else{
	    					this.notify_error(res.message)
	    				}
	    			})
	    		})
	    },
			// 新增类型
			addType(){
				this.editDialog = true;
				this.examType = {};
				this.title = "新增类型";
			},
			// 加载数据
			loadData(){
				var data = {pageNum:this.pageNum,pageSize:this.pageSize};
				this.postHttp("shieldexamtype/queryShieldExamTypes",data,res => {
					this.tableData = res.result.list;
					this.pageNum = res.result.pageNum;
					this.total = res.result.total;
				});
			},
	    // 分页
	    pageSizeChange(val) {
	      this.pageSize = val;
				this.loadData();
	    },
	    pageChange(val) {
	      this.pageNum = val;
	      this.loadData();
	    }
	  }
}
</script>

<style>
#examType .tables{
	margin-top:60px;
}
</style>
