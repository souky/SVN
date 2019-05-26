<template>
	<div id="systemSetting" class="main_body">
		<div class="tabs_">
			<el-tabs type="border-card">
				<el-tab-pane label="时钟设置">
					<el-form style="width:400px" label-width="150px">
						<el-form-item label="时钟分布刷新频率">
							<el-select v-model="refresh" @change="changeRefresh" placeholder="请选择">
								<el-option value="10000" label="10秒"></el-option>
								<el-option value="30000" label="30秒"></el-option>
								<el-option value="60000" label="60秒"></el-option>
								<el-option value="120000" label="120秒"></el-option>
							</el-select>
						</el-form-item>
						
						<el-form-item label="时钟绑定模式">
							<el-select v-model="models" @change="changeModels" placeholder="请选择">
								<el-option value="1" label="IP"></el-option>
								<el-option value="2" label="MAC"></el-option>
							</el-select>
						</el-form-item>
						
						<el-form-item label="地址显示模式">
							<el-select v-model="addressmodel" @change="changeAddModels" placeholder="请选择">
								<el-option value="1" label="物理考场"></el-option>
								<el-option value="2" label="逻辑考场"></el-option>
							</el-select>
						</el-form-item>
					</el-form>
					
					<div class="items">
						<el-button v-if="selfExam == 1" type="primary" :loading="true" >自检中</el-button>
						<el-button v-else  @click="selfExams()">自检</el-button>
					</div>
				</el-tab-pane>
				
				<el-tab-pane label="卫星设置">
					<el-form style="width:400px" label-width="150px">
						<el-form-item label="卫星串口选择">
							<el-select v-model="satellite" @change="changeSatellite" placeholder="请选择">
								<el-option value="COM3"></el-option>
								<el-option value="ttyS1"></el-option>
								<el-option value="ttyS2"></el-option>
								<el-option value="ttyS3"></el-option>
							</el-select>
						</el-form-item>
					</el-form>
				</el-tab-pane>
			</el-tabs>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				msg:'',
				refresh:'',
				models:'',
				satellite:'',
				selfExam:0,
				addressmodel:''
			};
		},
		mounted:function() {
			// 获取状态
			this.postHttp("clockconfig/queryClockConfigs",{},res=>{
				var map = res.result;
				this.refresh = map.refresh;
				this.models = map.models;
				this.satellite = map.satellite;
				this.selfExam = map.selfExam;
				this.addressmodel = map.addressmodel;
			});
		},
		methods:{
			//自检
			selfExams(){
				var data = {sysKey:'selfExam',sysVal:1}
				this.postHttp("clockconfig/updateClockConfig",data,res=>{
					if(res.code == '10000'){
						this.notify_success('时钟自检时间较长,请耐心等待');
						this.selfExam = 1;
					}else{
						this.notify_error("自检失败");
					}
				})
			},
			// 更改刷新频率
			changeRefresh(val){
				this.updateValue('refresh',val);
			},
			changeModels(val){
				this.updateValue('models',val);
			},	
			changeSatellite(val){
				this.updateValue('satellite',val);
			},
			changeAddModels(val){
				this.updateValue('addressmodel',val);
			},
			
			//更新值总方法
			updateValue(key,val){
				var data = {sysKey:key,sysVal:val}
				this.postHttp("clockconfig/updateClockConfig",data,res=>{
					if(res.code == '10000'){
						this.notify_success("修改成功");
					}else{
						this.notify_error("修改失败");
					}
				})
			},
		}
	}
</script>

<style>
#systemSetting .tabs_{
	margin:20px auto;
	width:98%;
}
#systemSetting .items{
	width:400px;
}
</style>
