<template>
	<div id="examPlan" class="main_body">
		<div class="query">
			<div class="query_items">
				<div class="text">开始时间:</div>
				<div class="inputs">
					<el-date-picker v-model="querys.startTime" value-format="timestamp" type="datetime" placeholder="选择日期时间"></el-date-picker>
				</div>
			</div>
			<div class="query_items">
				<div class="text">结束时间:</div>
				<div class="inputs">
					<el-date-picker v-model="querys.endTime" value-format="timestamp" type="datetime" placeholder="选择日期时间"></el-date-picker>
				</div>
			</div>
			<div class="query_items">
				<div class="text">考试名称:</div>
				<div class="inputs">
					<el-input v-model="querys.examName" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="text">考试编号:</div>
				<div class="inputs">
					<el-input v-model="querys.examNo" placeholder="请输入内容"></el-input>
				</div>
			</div>
			
			<div class="query_items"></div>
			<div class="query_items"></div>
			<div class="query_items"></div>
			<div class="query_items">
				<div class="query_button" @click="queryResult()">
					<el-button type="primary">查询</el-button>
				</div>
			</div>
		</div>
		
		<div class="tables">
			<el-table :data="tableData" align='center' style="width: 100%" @selection-change="getSelected">
				<el-table-column type="selection" width="55"></el-table-column>
			    <el-table-column align='center' width="55" type="index" label="序号" :index="indexMethod"></el-table-column>
			    <el-table-column align='center' width="100" prop="examNo" label="考试编号"></el-table-column>
					<el-table-column align='center' prop="examName" label="考试名称"></el-table-column>
					<el-table-column align='center' width="220" prop="startTime" :formatter="timeFormat" label="开始时间"></el-table-column>
					<el-table-column align='center' width="220" prop="endTime" :formatter="timeFormat" label="结束时间"></el-table-column>
					<el-table-column align='center' width="100" prop="status" :formatter="statusFormat" label="状态"></el-table-column>
			    <el-table-column align='center' width='210' class-name="operations" label="操作">
			    		<template slot-scope="scope">
			    			<div class="op_items">
			    				<el-button size="mini" @click="show(scope.row.id)">查看</el-button>
			    			</div>
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
				<el-button type="primary" size="mini" @click="addExam">添加计划</el-button>
			</div>
			
			<el-pagination @size-change="pageSizeChange" @current-change="pageChange" background :current-page.sync="page.pageNum"
		      :page-sizes="[10, 20, 30, 40]"
		      :page-size="page.pageSize"
		      layout="total,sizes,prev, pager, next,jumper" :total="page.total">
			</el-pagination>
		</div>
		
		<el-dialog :title="Dtitle" width="40%" :visible.sync="showDialog">
			<el-form style="width:70%;margin:auto" :model="examPlan" label-width="80px">
				<el-form-item label="考试编号">
					<el-input :disabled="!isEdit" v-model="examPlan.examNo"></el-input>
				</el-form-item>
				<el-form-item label="考试名称">
					<el-input :disabled="!isEdit" v-model="examPlan.examName"></el-input>
				</el-form-item>
				<el-form-item label="开始时间">
					<el-date-picker :disabled="!isEdit" v-model="examPlan.startTime" value-format="timestamp" type="datetime" placeholder="选择日期时间"></el-date-picker>
				</el-form-item>
				<el-form-item label="结束时间">
					<el-date-picker :disabled="!isEdit" v-model="examPlan.endTime" value-format="timestamp" type="datetime" placeholder="选择日期时间"></el-date-picker>
				</el-form-item>
				<el-form-item label="计划状态">
					<el-select :disabled="!isEdit" v-model="examPlan.examStatus" placeholder="请选择">
						<el-option label="启用" :value="0"></el-option>
						<el-option label="禁用" :value="1"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-if="isEdit" style="text-align: right;">
					<el-button style="width:auto;" @click="showDialog = false">取消</el-button>
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
	      querys:{
	      	examNo:'',
	      	examName:'',
	      	startTime:null,
	      	endTime:null
	      },
	      tableData:[],
	      selectData:[],
	      page:{
	      	pageNum:1,
	      	pageSize:10,
	      	total:1
	      },
				
				examPlan:{},
				Dtitle: '',
				showDialog: false,
				isEdit:false
	    }
	  },
	  mounted:function(){
			this.loadData();
	  },
	  methods:{
	  	//查询方法
	  	queryResult(){
	  		this.page.pageNum = 1;
				console.log(this.querys)
				this.loadData();
	  	},
	  	indexMethod(index){
	  		return index + 1;
	  	},
	  	//获取checkbox选择的条
	  	getSelected(val) {
	        this.selectData = val;
	    },
	    //查看方法
	    show(id){
	    		this.postHttp("shieldexamplan/getShieldExamPlanById",{id:id},res=>{
						if(res.code == "10000"){
							this.examPlan = res.result;
							this.showDialog = true;
							this.isEdit = false;
						}
					});
	    },
	    edit(id){
	    		this.postHttp("shieldexamplan/getShieldExamPlanById",{id:id},res=>{
	    			if(res.code == "10000"){
	    				this.examPlan = res.result;
	    				this.showDialog = true;
	    				this.isEdit = true;
	    			}
	    		});
	    },
	    deletes(id){
				this.confim_jy('确定要删除此条信息?','提示',res =>{
					this.postHttp("shieldexamplan/deleteShieldExamPlan",{id:id},res=>{
						if(res.code == "10000"){
							this.notify_success("删除成功");
							this.showDialog = false;
							this.loadData();
						}else{
							this.notify_error(res.message);
						}
					});
				})
	    },
			save(){
				var id = this.examPlan.id;
				var no = this.examPlan.examNo;
				var name = this.examPlan.examName;
				var startdate = this.examPlan.startTime;
				var enddate = this.examPlan.endTime;
				if(!no || no == ''){
					this.notify_warning("编号不能为空");
					return;
				}
				if(!name || name == ''){
					this.notify_warning("名称不能为空");
					return;
				}
				if(!startdate || startdate == null){
					this.notify_warning("请选择开始时间");
					return;
				}
				if(!enddate || enddate == null){
					this.notify_warning("请选择结束时间");
					return;
				}
				if(startdate > enddate ){
					this.notify_warning("开始时间不能大于结束时间");
					return;
				}
				var url = '';
				if(id == '' || !id){
					// 新增
					url = 'shieldexamplan/saveShieldExamPlan';
				}else{
					// 编辑
					url = 'shieldexamplan/updateShieldExamPlan';
				}
				
				this.postHttp(url,this.examPlan,res=>{
					if(res.code == '10000'){
						this.notify_success("操作成功");
						this.examPlan = {};
						this.loadData();
						this.showDialog = false;
					}else{
						this.notify_error(res.message);
					}
				})
			},
	    //批量删除
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
	    			this.postHttp("shieldexamplan/deleteShieldExamPlans",{ids:ids.join(",")},res=>{
	    				if(res.code == 10000){
	    					this.notify_success("删除成功!");
	    					this.loadData();
	    				}else{
	    					this.notify_error(res.message)
	    				}
	    			})
	    		})
	    },
	    //添加计划
			addExam(){
				this.examPlan = {};
				this.showDialog = true;
				this.isEdit = true;
			},
			loadData(){
				var data = this.querys;
				data['pageNum'] = this.page.pageNum;
				data['pageSize'] = this.page.pageSize;
				this.postHttp("shieldexamplan/queryShieldExamPlans", data, res => {
					if (res.code == '10000') {
						this.tableData = res.result.list;
						this.page.pageNum = res.result.pageNum;
						this.page.total = res.result.total;
					}
				})
			},
	    pageSizeChange(val) {
	    	this.page.pageSize = val;
	    	this.loadData();
	    },
	    pageChange(val) {
	    	this.page.pageNum = val;
	    	this.loadData();
	    },
			statusFormat(row, column, cellValue, index) {
				var status = row.examStatus;
				switch (status) {
					case 0:
						status = '启用';
						break;
					case 1:
						status = '禁用';
						break;
				}
				return status;
			},
			timeFormat(row, column, cellValue, index){
				if (cellValue == undefined || cellValue == '') {
				return "";
				}
				return this.timeF(cellValue).format("YYYY-MM-DD HH:mm:ss");
			}
	  }
}
</script>

<style>
#examPlan .el-date-editor{width: 100%;}
</style>
