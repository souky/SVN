<template>
	<div id="alert">
		<div class="common_title">
			Alert Function
		</div>
		<div class="code_block">
			jy.Alert({
				width:400,
				height:200,
				top:200,
				title:'提示',
				content:'测试内容',
				confirm-text:'关闭',
				has-close-btn:false,
				show-mask:true,
				success:null,
				close:null
			})
		</div>
		<div class="common_title">
			Parameter Description
		</div>
		<el-table :data="alertData" style="width:90%;margin:auto;">
			<el-table-column prop="parameter" label="参数" width='180px'></el-table-column>
			<el-table-column prop="explain" label="说明"></el-table-column>
			<el-table-column prop="type" label="类型" width='200px'></el-table-column>
			<el-table-column prop="chioceVal" label="可选值" width='160px'></el-table-column>
			<el-table-column prop="defaultVal" label="默认值" width='160px'></el-table-column>
		</el-table>
	</div>
</template>

<script>
import AlertData from '../assets/data/AlertData'
export default {
	data() {
	  	return {
	  		msg:'',
	  		alertData:AlertData.Attributes
		}
	},
	mounted:function(){
		this.initCodeBlock();
	}
}
</script>

<style>
</style>