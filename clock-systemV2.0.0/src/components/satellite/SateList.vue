<template>
	<div id="satellite" class="main_body">
		<div class="tables">
			<el-table :data="sateList" align='center' style="width: 100%" >
				<el-table-column align='center' type="index" label="序号" :index="indexMethod"></el-table-column>
				<el-table-column align='center' prop="satNo" width=100 label="卫星编号"></el-table-column>
				<el-table-column align='center' prop="satType" :formatter="statusFormat" width=100 label="卫星类型"></el-table-column>
				<el-table-column align='center' prop="satSnr" label="卫星信噪比">
					<template slot-scope="scope">
						<el-progress  :text-inside="true" :stroke-width="16" :percentage="scope.row.satSnr" color="rgb(255, 138, 138)"></el-progress>
					</template>
				</el-table-column>
				<el-table-column align='center' prop="satElevation" width=100 label="卫星仰角"></el-table-column>
				<el-table-column align='center' prop="satAzimuth" width=100 label="卫星方位角"></el-table-column>
			</el-table>
		</div>
		
	</div>
</template>

<script>
	export default {
		data() {
			return {
				msg:'',
				sateList:[]
			};
		},
		mounted:function(){
// 			this.postHttp("",{},res=>{
// 				this.sateList = res.result;
// 			});

			this.sateList = this.Mock.mock({
				'list|21': [{
					'id': /([A-Z][0-9]){16}/,
					'satNo':/\d{1,2}/,
					'satType':/[0-3]{1}/,
					'satElevation':/\d{1,3}/,
					'satAzimuth':/\d{1,3}/,
					'satSnr':/\d{2}/,
				}]
			}).list;
		},
		methods:{
			indexMethod(index) {
				return index + 1;
			},
			statusFormat(row, column, cellValue, index) {
				var status = row.satType;
				switch (status) {
					case '0':
						status = 'BD';
						break;
					case '1':
						status = 'GP';
						break;
					case '2':
						status = 'GALILEO';
						break;
					case '3':
						status = 'GLONASS';
						break;
				}
				return status;
			},
		}
		
	}
</script>

<style>
#satellite{
	padding:60px 0px;
	box-sizing: border-box;
}
</style>
