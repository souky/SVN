<template>
	<div id="showLog" class="main_body">
		<div class="query">
			<div class="query_items">
				<div class="text">用户名称:</div>
				<div class="inputs">
					<el-input v-model="querys.operationUserName" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="text">操作事件:</div>
				<div class="inputs">
					<el-input v-model="querys.operationInfo" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="text">操作类型:</div>
				<div class="inputs">
					<el-select v-model="querys.operationType" placeholder="请选择">
						<el-option label="全部" value="0"></el-option>
						<el-option label="新增" value="1"></el-option>
						<el-option label="修改" value="2"></el-option>
						<el-option label="删除" value="3"></el-option>
					</el-select>
				</div>
			</div>
			<div class="query_items">
				<div class="query_button" @click="queryResult()">
					<el-button type="primary">查询</el-button>
				</div>
			</div>
		</div>
		
		<div class="tables">
			<el-table :data="tableData" align='center' style="width: 100%" >
			    <el-table-column align='center' type="index" label="序号" :index="indexMethod"></el-table-column>
					<el-table-column align='center' width="150" prop="operationUserName" label="用户名称"></el-table-column>
					<el-table-column align='center' width="250" prop="operationMoudle" label="操作模块"></el-table-column>
			    <el-table-column align='center' prop="operationInfo" label="操作信息"></el-table-column>
					<el-table-column align='center' width="150" prop="operationType" :formatter="statusFormat" label="操作类型"></el-table-column>
			    <el-table-column align='center' width="200" prop="operationTime" :formatter="timeFormat" label="操作时间"></el-table-column>
			</el-table>
			<el-pagination @size-change="pageSizeChange" @current-change="pageChange" background :current-page.sync="page.pageNum"
		      :page-sizes="[10, 20, 30, 40]"
		      :page-size="page.pageSize"
		      layout="total,sizes,prev, pager, next,jumper" :total="page.total">
		    </el-pagination>
		</div>
	</div>
</template>

<script>
export default {
	  data () {
	    return {
	      msg: 'showLog',
	      querys:{
	      	operationUserName:'',
	      	operationInfo:'',
					operationType:'0'
	      },
	      tableData:[],
	      page:{
	      	pageNum:1,
	      	pageSize:10,
	      	total:1
	      }
	    }
	  },
	  mounted:function(){
			this.loadData();
	  },
	  methods:{
	  	//查询方法
	  	queryResult(){
	  		this.loadData();
	  	},
	  	indexMethod(index){
	  		return index + 1;
	  	},
	  	 // 分页
	    pageSizeChange(val) {
	      this.page.pageSize = val;
	      this.loadData();
	    },
	    pageChange(val) {
	      this.page.pageNum = val;
	      this.loadData();
	    },
			loadData(){
				var data = this.querys;
				data['pageNum'] = this.page.pageNum;
				data['pageSize'] = this.page.pageSize;
				this.postHttp("clocklog/queryClockLogs",data,res=>{
					if(res.code == 10000){
						this.tableData = res.result.list;
						this.page.pageNum = res.result.pageNum;
						this.page.total = res.result.total;
					}
				})
			},
			statusFormat(row, column, cellValue, index) {
				var status = row.operationType;
				switch (status) {
					case 1:
						status = '新增';
						break;
					case 2:
						status = '修改';
						break;
					case 3:
						status = '删除';
						break;
				}
				return status;
			},
			timeFormat(row, column, cellValue, index){
				var date = row.operationTime;
				if (date == undefined || date == '') {
				 return "";
				}
				return this.timeF(date).format("YYYY-MM-DD HH:mm:ss");
			}
	  }
}
</script>

<style>

</style>