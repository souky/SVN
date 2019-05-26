<template>
	<div id="confirm">
		<div class="common_title">
			Alert Function
		</div>
		<div class="code_block">
			jy.Dialog({
				title:'提示',
				content:'contentId',
				has-close-btn:false,
				has-mask:fasle
			})
		</div>
		<div class="common_title">
			Parameter Description
		</div>
		<el-table :data="dialogData" style="width:90%;margin:auto;">
			<el-table-column prop="parameter" label="参数" width='180px'></el-table-column>
			<el-table-column prop="explain" label="说明"></el-table-column>
			<el-table-column prop="type" label="类型" width='200px'></el-table-column>
			<el-table-column prop="chioceVal" label="可选值" width='160px'></el-table-column>
			<el-table-column prop="defaultVal" label="默认值" width='160px'></el-table-column>
		</el-table>
	</div>
</template>

<script>
import DialogData from '../assets/data/DialogData'
export default {
	data() {
	  	return {
	  		msg:'',
	  		dialogData:DialogData.Attributes
		}
	},
	mounted:function(){
		this.initCodeBlock();
	}
}
</script>

<style>
</style>