<template>
	<div id="input">
		<div class="common_title">
			Basics code
		</div>
		<div class="code_block">
			#jy:input type="text" /%
		</div>
		<div class="common_title">
			Input Attributes
		</div>
		<el-table :data="inputData" style="width:90%;margin:auto;">
			<el-table-column prop="parameter" label="参数" width='180px'></el-table-column>
			<el-table-column prop="explain" label="说明"></el-table-column>
			<el-table-column prop="type" label="类型" width='200px'></el-table-column>
			<el-table-column prop="chioceVal" label="可选值" width='160px'></el-table-column>
			<el-table-column prop="defaultVal" label="默认值" width='160px'></el-table-column>
		</el-table>
		<div class="common_title">
			Input Funtion
		</div>
		<div class="common_title">
			Input Class
		</div>
	</div>
</template>

<script>
import InputData from '../assets/data/InputData'
export default {
	data() {
	  	return {
	  		inputData:InputData.Attributes
		}
	},
	mounted:function(){
		this.initCodeBlock();
	}
}
</script>

<style>

</style>