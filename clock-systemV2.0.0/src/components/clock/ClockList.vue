<template>
	<div id="clockList" class="main_body">
		<div class="query">
			<div class="query_items">
				<div class="text">逻辑考场:</div>
				<div class="inputs">
					<el-input v-model="querys.logicalExamRoom" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="text">物理考场:</div>
				<div class="inputs">
					<el-input v-model="querys.physicalExamRoom" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="text">IP地址:</div>
				<div class="inputs">
					<el-input v-model="querys.clockIpAddr" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="text">状态:</div>
				<div class="inputs">
					<el-select v-model="querys.clockStatus" placeholder="请选择">
						<el-option label="全部" value="-1"></el-option>
						<el-option label="在线" value="0"></el-option>
						<el-option label="离线" value="1"></el-option>
						<el-option label="时间异常" value="2"></el-option>
						<el-option label="故障" value="3"></el-option>
					</el-select>
				</div>
			</div>
			<div class="query_items"></div>
			<div class="query_items"></div>
			<div class="query_items"></div>
			<div class="query_items">
				<div class="query_button" @click="queryResult()">
					<el-button type="primary">查询</el-button>
				</div>
			</div>
		</div>

		<div class="tables">
			<el-table :data="tableData" align='center' style="width: 100%" @selection-change="getSelected">
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column align='center' type="index" label="序号" :index="indexMethod"></el-table-column>
				<el-table-column align='center' prop="clockIpAddr" label="IP地址"></el-table-column>
				<el-table-column align='center' prop="clockMac" label="MAC地址"></el-table-column>
				<el-table-column align='center' prop="logicalExamRoom" label="逻辑考场"></el-table-column>
				<el-table-column align='center' prop="physicalExamRoom" label="物理考场"></el-table-column>
				<el-table-column align='center' prop="clockStatus" :formatter="statusFormat" label="时钟状态"></el-table-column>
				<el-table-column align='center' width=140 class-name="operations" label="操作">
					<template slot-scope="scope">
						<div class="op_items">
							<el-button size="mini" @click="show(scope.row.id)">查看</el-button>
						</div>
						<!-- <div class="op_items">
							<el-button size="mini" @click="edit(scope.row.id)">编辑</el-button>
						</div> -->
						<div class="op_items">
							<el-button size="mini" type="danger" @click="deletes(scope.row.id)">删除</el-button>
						</div>
					</template>
				</el-table-column>
			</el-table>
			<div class="deleteRows">
				<el-button type="primary" size="mini" @click="deleteRows">批量删除</el-button>
			</div>

			<el-pagination @size-change="pageSizeChange" @current-change="pageChange" background :current-page.sync="page.pageNum"
			 :page-sizes="[10, 20, 30, 40]" :page-size="page.pageSize" layout="total,sizes,prev, pager, next,jumper" :total="page.total">
			</el-pagination>
		</div>

		<el-dialog :title="Dtitle" :visible.sync="showDialog">
			<el-form style="width:50%;margin:auto" :model="clockDevice" label-width="80px">
				<el-form-item label="IP地址">
					<el-input :disabled="!isEdit" v-model="clockDevice.clockIpAddr"></el-input>
				</el-form-item>
				<el-form-item label="MAC地址">
					<el-input :disabled="!isEdit" v-model="clockDevice.clockMac"></el-input>
				</el-form-item>
				<el-form-item label="逻辑考场">
					<el-input :disabled="!isEdit" v-model="clockDevice.logicalExamRoom"></el-input>
				</el-form-item>
				<el-form-item label="物理考场">
					<el-input :disabled="!isEdit" v-model="clockDevice.physicalExamRoom"></el-input>
				</el-form-item>
				<el-form-item v-if="isEdit" style="text-align: right;">
					<el-button style="width:auto;" @click="showDialog = false">取消</el-button>
					<el-button style="width:auto;" type="primary" @click="save">保存</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>

	</div>
</template>

<script>
	export default {
		data() {
			return {
				msg: 'clockList',
				querys: {
					logicalExamRoom: '',
					physicalExamRoom: '',
					clockIpAddr: '',
					clockStatus: '-1'
				},
				tableData: [],
				selectData: [],
				page: {
					pageNum: 1,
					pageSize: 10,
					total: 1
				},
				clockDevice: {
				},
				Dtitle: '',
				showDialog: false,
				isEdit:false
			}
		},
		mounted: function() {
			this.loadData();
		},
		methods: {
			//查询方法
			queryResult() {
				this.page.pageNum = 1;
				this.loadData();
			},
			indexMethod(index) {
				return index + 1;
			},
			//获取checkbox选择的条
			getSelected(val) {
				this.selectData = val;
			},
			//查看方法
			show(id) {
				this.postHttp("clockdevice/getClockDeviceById", {
					id: id
				}, res => {
					this.clockDevice = res.result;
					this.Dtitle = '查看信息';
					this.showDialog = true;
					this.isEdit = false;
				})
			},
			edit(id) {
				this.postHttp("clockdevice/getClockDeviceById", {
					id: id
				}, res => {
					this.clockDevice = res.result;
					this.Dtitle = '编辑信息';
					this.showDialog = true;
					this.isEdit = true;
				})
			},
			//保存修改
			save(){
				this.postHttp("clockdevice/updateClockDevice", this.clockDevice, res => {
					if(res.code == 10000){
						this.notify_success("修改成功!");
						this.loadData();
						this.showDialog = false;
					}else{
						this.notify_error(res.message);
					}
					
				})
			},
			deletes(id) {
				this.confim_jy('确定要删除此条信息?','提示',res =>{
					this.postHttp("clockdevice/deleteClockDevice",{id:id},res=>{
						if(res.code == 10000){
							this.notify_success("删除成功!");
							this.loadData();
						}else{
							this.notify_error(res.message)
						}
					})
				})
			},
			//批量删除
			deleteRows() {
				var ids = [];
				for (var x in this.selectData) {
					ids.push(this.selectData[x].id);
				}
				if (ids.length == 0) {
					this.notify_warning("请选择删除项");
					return;
				}
				this.confim_jy('确定要删除这几条信息?','提示',res =>{
					this.postHttp("clockdevice/deleteClockDevices",{ids:ids.join(",")},res=>{
						if(res.code == 10000){
							this.notify_success("删除成功!");
							this.loadData();
						}else{
							this.notify_error(res.message)
						}
					})
				})
				
			},
			//
			pageSizeChange(val) {
				this.page.pageSize = val;
				this.loadData();
			},
			pageChange(val) {
				this.page.pageNum = val;
				this.loadData();
			},
			//statusFormat
			statusFormat(row, column, cellValue, index) {
				var status = row.clockStatus;
				switch (status) {
					case 0:
						status = '在线';
						break;
					case 1:
						status = '离线';
						break;
					case 2:
						status = '时间异常';
						break;
					case 3:
						status = '故障';
						break;
				}
				return status;
			},
			loadData() {
				var data = this.querys;
				data['pageNum'] = this.page.pageNum;
				data['pageSize'] = this.page.pageSize;
				this.postHttp("clockdevice/queryClockDevices", data, res => {
					if (res.code == '10000') {
						this.tableData = res.result.list;
						this.page.pageNum = res.result.pageNum;
						this.page.total = res.result.total;
					}
				})
			}
		}
	}
</script>

<style>

</style>
