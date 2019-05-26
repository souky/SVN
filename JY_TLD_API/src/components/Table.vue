<template>
	<div id="table">
		<div class="common_title">
			Basics code
		</div>
		<div class="code_block">
			#jy:table %
				#jy:column  prop="example" label="例子" width="10" %
				#/jy:column%
			#/jy:table %
		</div>
		<div class="common_title">
			Table Attributes
		</div>
		<el-table :data="tableData" style="width:90%;margin:auto;">
			<el-table-column prop="parameter" label="参数" width='180px'></el-table-column>
			<el-table-column prop="explain" label="说明"></el-table-column>
			<el-table-column prop="type" label="类型" width='200px'></el-table-column>
			<el-table-column prop="chioceVal" label="可选值" width='160px'></el-table-column>
			<el-table-column prop="defaultVal" label="默认值" width='160px'></el-table-column>
		</el-table>

		<div class="common_title">
			Column Attributes
		</div>
		<el-table :data="columnData" style="width:90%;margin:auto;">
			<el-table-column prop="parameter" label="参数" width='180px'></el-table-column>
			<el-table-column prop="explain" label="说明"></el-table-column>
			<el-table-column prop="type" label="类型" width='200px'></el-table-column>
			<el-table-column prop="chioceVal" label="可选值" width='160px'></el-table-column>
			<el-table-column prop="defaultVal" label="默认值" width='160px'></el-table-column>
		</el-table>
	</div>
</template>

<script>
import TableData from '../assets/data/TableData'
export default {
	data() {
	  	return {
	  		tableData:TableData.Attributes,
				columnData:TableData.columnAttributes,
		}
	},
	mounted:function(){
		this.initCodeBlock();
	}
}
</script>

<style>

</style>
