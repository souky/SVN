<template>
	<div id="select">
		<div class="common_title">
			Basics code
		</div>
		<div class="code_block">
			#jy:nav%

			#/jy:nav%
		</div>
		<div class="common_title">
			Select Attributes
		</div>
		<el-table :data="selectData" style="width:90%;margin:auto;">
			<el-table-column prop="parameter" label="参数" width='180px'></el-table-column>
			<el-table-column prop="explain" label="说明"></el-table-column>
			<el-table-column prop="type" label="类型" width='200px'></el-table-column>
			<el-table-column prop="chioceVal" label="可选值" width='160px'></el-table-column>
			<el-table-column prop="defaultVal" label="默认值" width='160px'></el-table-column>
		</el-table>
		<div class="common_title">
			Functions
		</div>
		<el-table :data="optionsData" style="width:90%;margin:auto;">
			<el-table-column prop="parameter" label="参数" width='180px'></el-table-column>
			<el-table-column prop="explain" label="说明"></el-table-column>
			<el-table-column prop="type" label="类型" width='200px'></el-table-column>
			<el-table-column prop="chioceVal" label="可选值" width='160px'></el-table-column>
			<el-table-column prop="defaultVal" label="默认值" width='160px'></el-table-column>
		</el-table>
	</div>
</template>

<script>
import NavData from '../assets/data/NavData'
export default {
	data() {
	  	return {
	  		selectData:NavData.Attributes,
	  		optionsData:NavData.optionsData
		}
	},
	mounted:function(){
		this.initCodeBlock();
	}
}
</script>

<style>
</style>
