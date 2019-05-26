<template>
	<div id="showLog" class="main_body">
		<div class="query">
			<div class="query_items">
				<div class="text">用户名称:</div>
				<div class="inputs">
					<el-input v-model="querys.userName" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="text">用户IP:</div>
				<div class="inputs">
					<el-input v-model="querys.ip" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="text">操作事件:</div>
				<div class="inputs">
					<el-input v-model="querys.events" placeholder="请输入内容"></el-input>
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
			    <el-table-column align='center' prop="ip" label="用户IP"></el-table-column>
			    <el-table-column align='center' prop="userName" label="用户名称"></el-table-column>
			    <el-table-column align='center' prop="events" label="操作事件"></el-table-column>
			    <el-table-column align='center' prop="opDate" label="操作时间"></el-table-column>
			</el-table>
			<el-pagination @size-change="pageSizeChange" @current-change="pageChange" background :current-page.sync="page.pageNum"
		      :page-sizes="[10, 20, 30, 40]"
		      :page-size="page.pageSize"
		      layout="sizes,prev, pager, next,jumper" :total="page.total">
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
	      	userName:'',
	      	events:'',
	      	ip:''
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
	  	// mock 数据
	  	var datas = this.Mock.mock({
		    'list|10': [{
		        'id': /([A-Z][0-9]){16}/,
		        'ip':'@ip',
		        'userName':'@last',
		        'events':'@ctitle(5)',
		        'opDate':'@datetime("yyyy-MM-dd HH:mm:ss")',
		    }]
		});
		var pages = this.Mock.mock({
			'pageNum':1,
			'pageSize':10,
			'total|1-100':1
		})
		this.tableData = datas.list;
		this.page = pages;
	  },
	  methods:{
	  	//查询方法
	  	queryResult(){
	  		console.log(this.querys);
	  	},
	  	indexMethod(index){
	  		return index + 1;
	  	},
	  	 // 分页
	    pageSizeChange(val) {
	      console.log(`每页 ${val} 条`);
	    },
	    pageChange(val) {
	      console.log(`当前页: ${val}`);
	    }
	  }
}
</script>

<style>

</style>