<template>
	<div id="page">
		<div class="common_title">
			Basics code
		</div>
		<div class="code_block">
			#jy:page % #/jy:page%
		</div>
		<div class="common_title">
			Page Attributes
		</div>
		<el-table :data="pageData" style="width:90%;margin:auto;">
			<el-table-column prop="parameter" label="参数" width='180px'></el-table-column>
			<el-table-column prop="explain" label="说明"></el-table-column>
			<el-table-column prop="type" label="类型" width='200px'></el-table-column>
			<el-table-column prop="chioceVal" label="可选值" width='160px'></el-table-column>
			<el-table-column prop="defaultVal" label="默认值" width='160px'></el-table-column>
		</el-table>
		
		<div class="common_title">
			page-options Attributes
		</div>
		<el-table :data="optionData" style="width:90%;margin:auto;">
			<el-table-column prop="parameter" label="参数" width='180px'></el-table-column>
			<el-table-column prop="explain" label="说明"></el-table-column>
			<el-table-column prop="type" label="类型" width='200px'></el-table-column>
			<el-table-column prop="chioceVal" label="可选值" width='160px'></el-table-column>
			<el-table-column prop="defaultVal" label="默认值" width='160px'></el-table-column>
		</el-table>
	</div>
</template>

<script>
import PageData from '../assets/data/PageData'
export default {
	data() {
	  	return {
	  		pageData:PageData.Attributes,
	  		optionData:PageData.OptionAttributes
		}
	},
	mounted:function(){
		this.initCodeBlock();
	}
}
</script>

<style>
</style>