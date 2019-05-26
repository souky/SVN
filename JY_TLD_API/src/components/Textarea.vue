<template>
	<div id="textarea">
		<div class="common_title">
			Basics code
		</div>
		<div class="code_block">
			#jy:textarea % #/jy:textarea %
		</div>
		<div class="common_title">
			Textarea Attributes
		</div>
		<el-table :data="textareaData" style="width:90%;margin:auto;">
			<el-table-column prop="parameter" label="参数" width='180px'></el-table-column>
			<el-table-column prop="explain" label="说明"></el-table-column>
			<el-table-column prop="type" label="类型" width='200px'></el-table-column>
			<el-table-column prop="chioceVal" label="可选值" width='160px'></el-table-column>
			<el-table-column prop="defaultVal" label="默认值" width='160px'></el-table-column>
		</el-table>
	</div>
</template>

<script>
import TextareaData from '../assets/data/TextareaData'
export default {
	data() {
	  	return {
	  		textareaData:TextareaData.Attributes,
		}
	},
	mounted:function(){
		this.initCodeBlock();
	}
}
</script>

<style>
</style>