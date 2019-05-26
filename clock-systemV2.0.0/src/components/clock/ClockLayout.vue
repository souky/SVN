<template>
	<div id="ClockLayout" class="main_body">
		<!--count-->
		<div class="top_ly">
			<div class="count">
				<div class="items">在线：{{counts['0']}}</div>
				<div class="items">离线：{{counts['1']}}</div>
				<div class="items">异常：{{counts['2']}}</div>
				<div class="items">故障：{{counts['3']}}</div>
			</div>
			<div class="btns">
				<div class="items">
					<el-button v-if="power == 0" type="primary"  size="mini" @click="powerChange('1')">时钟开</el-button>
					<el-button v-else  size="mini"  @click="powerChange('0')">时钟关</el-button>
				</div>
				<div class="items">
					<el-button v-if="showSec == 0" type="primary" size="mini" @click="secChange('1')">秒钟开</el-button>
					<el-button v-else  size="mini"  @click="secChange('0')">秒钟关</el-button>
				</div>
			</div>
		</div>
		<div class="builds">
			<!--builds-->
			<el-tabs v-model="activeBuild" @tab-click="tabChange" addable @tab-add="addBuildSync = true">
				<el-tab-pane v-for="(e,index_b) in builds" :key="e.id" :label="buildsNameArray[index_b]">
					<div class="floors_">
						<div v-for="(f,index) in e.floors" :key="f.id" class="floor_">
							<div class="floor_name">{{floorChina[index]}}</div>
							<div v-for="r in f">
								<el-popover :disabled="r.clockStatus == 3?false:true" placement="top" width="150" trigger="hover" >	
									<!--unfunc-->
									<div id="unfunction">
										<template v-for="(a,index) in 5">
											<div v-if="index != 2" class="pipe fl">
												<template v-if='r.selfArray' v-for="(b,indexs) in 7">
													<div v-if="r.selfArray[index][indexs] == '1'" class="pipeItem error "></div>
													<div v-else class="pipeItem "></div>
												</template>
											</div>
											<div v-else class="pipeNum fl">:</div>
										</template>
									</div>
									<div slot="reference" :class="changeClass(r.clockStatus)">
										<div class="btns">
											<i class="el-icon-setting" @click="findClock(r.id)"></i>
											<i class="el-icon-delete" @click="unbinding(r.id)"></i>
										</div>
										<template v-if="addressmodel == '1'">
											<div v-if="r.physicalExamRoom && r.physicalExamRoom != '' " class="location">
												<i class="el-icon-location-outline"></i>
												{{r.physicalExamRoom}}
											</div>
											<div v-else class="location">未绑定</div>
										</template>
										<template v-else>
											<div v-if="r.logicalExamRoom && r.logicalExamRoom != '' " class="location">
												<i class="el-icon-location-outline"></i>
												{{r.logicalExamRoom}}
											</div>
											<div v-else class="location">未绑定</div>
										</template>
										<div v-if="r.clockId && r.clockId != ''" class="times">
											{{r.clockTime}}
										</div>
										<div v-else class="times"></div>
									</div>
								</el-popover>
							</div>
							<div class="items add" @click="creatNewRoom((index_b+1),(index+1),(e.floors[index].length+1))">
								<i class="el-icon-circle-plus-outline"></i>
							</div>
							
						</div>
						<div class="floor_">
							<div class="floor_add">
								<i class="el-icon-circle-plus-outline" title="添加楼层" @click="addFloor((index_b+1),(e.floors.length+1))"></i>
							</div>
						</div>
					</div>
				</el-tab-pane>
			</el-tabs>
		</div>
		
		<el-dialog width="30%" title="编辑信息" :visible.sync="showEdit">
			<el-form style="width:80%;margin:auto" :model="clockPosition" label-width="80px">
				<el-form-item label="绑定时钟">
					<el-select v-model="clockPosition.clockId" placeholder="请选择">
						<el-option v-if="models == '1'" v-for="item in unBinding" :key="item.id" :label="item.clockIpAddr" :value="item.id"></el-option>
						<el-option v-if="models == '2'" v-for="item in unBinding" :key="item.id" :label="item.clockMac" :value="item.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="逻辑考场">
					<el-input  v-model="clockPosition.logicalExamRoom"></el-input>
				</el-form-item>
				<el-form-item label="物理考场">
					<el-input  v-model="clockPosition.physicalExamRoom"></el-input>
				</el-form-item>
				<el-form-item style="text-align: right;">
					<el-button style="width:auto;" @click="showEdit = false">取消</el-button>
					<el-button style="width:auto;" type="primary" @click="binding">保存</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
		
		<el-dialog width="30%" title="新楼栋信息" :visible.sync="addBuildSync">
			<el-form style="width:80%;margin:auto" label-width="80px">
				<el-form-item label="楼栋名称">
					<el-input  v-model="buildNames_"></el-input>
				</el-form-item>
				<el-form-item label="楼层数">
					<el-input  v-model="floorCount"></el-input>
				</el-form-item>
				<el-form-item label="房间数">
					<el-input  v-model="roomCount"></el-input>
				</el-form-item>
				<el-form-item style="text-align: right;">
					<el-button style="width:auto;" @click="addBuildSync = false">取消</el-button>
					<el-button style="width:auto;" type="primary" @click="addBuildFnc">保存</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
		
		<el-dialog custom-class="init" width="60%" title="初始化楼栋信息" :visible.sync="showInit" :show-close='false' :close-on-click-modal='false' :close-on-press-escape="false">
			<div class="buildsName">
				<el-input v-model="buildingName" placeholder="填写楼栋名称"></el-input>
				<el-button style="width:auto;" type="primary" @click="addBuilds">添加楼栋</el-button>
			</div>
			<el-tabs type="border-card" v-if='buildsNames.length > 0' closable @tab-remove="removeTab">
				<el-tab-pane v-for="e in buildsNames" :key="e.name" :name='e.name' :label="e.name">
					<div class="flexs">
						<label>楼层数:</label>
						<el-input v-model="e.floors"></el-input>
					</div>
					<div class="flexs">
						<label>房间数:</label>
						<el-input v-model="e.rooms"></el-input>
					</div>
				</el-tab-pane>
			</el-tabs>
			<el-button style="width:150px;float:right;margin-top:10px;" type="primary" @click="makeInit">生成数据</el-button>
		</el-dialog>
	</div>
</template>

<script>
export default {
	  data () {
	    return {
	      msg: 'ClockLayout',
	      counts:{},
	      selfExam:0,
	      power:1,
	      showSec:1,
	      activeBuild:'0',
				buildsNameArray:[],
	      floorChina:['一层','二层','三层','四层','五层','六层','七层','八层','九层','十层','十一层','十二层','十三层','十四层'],
	      visible:true,
	      builds:[],
	      timeout:0,
	      
				clockPosition:{},
				showEdit:false,
				unBinding:[],
				models:'1',
				addressmodel:'',
				showInit:false,
				addBuildSync:false,
				
				buildingName:'',
				buildsNames:[],
				
				buildNames_:'',
				floorCount:0,
				roomCount:0
	    }
	  },
	  mounted:function(){
			this.loadData();
			
			this.propsUpdate();
			
	  },
		beforeDestroy:function(){
			clearInterval(this.timeout);
		},
	  methods:{
	  	// 初始化楼层
	  	makeInit(){
				if(this.validateInit()){
					this.postHttp("clockdeviceposition/makePosition",{str:JSON.stringify(this.buildsNames)},res=>{
						if(res.code == 10000){
							this.notify_success("生成成功");
						}else{
							this.notify_error(res.message);
						}
					})
				}
	  	},
	  	//验证楼层数据
	  	validateInit(){
	  		var reg = /\d/;
				var list = this.buildsNames;
				var flag = false;
				if(list.length == 0){
					this.notify_error('请添加新楼栋');
					return false;
				}
				for(var e in list){
					if(!reg.test(list[e].floors) || list[e].floors == 0){
						flag = true;
						break;
					}
					if(!reg.test(list[e].rooms) || list[e].rooms == 0){
						flag = true;
						break;
					}
				}
				if(flag){
					this.notify_error('楼层/房间数据格式错误');
					return false;
				}
				return true;
	  	},
			//添加楼栋
			addBuilds(){
				var name = this.buildingName;
				if(name == ''){
					this.notify_error('请输入正确的名称');
					return;
				}
				var obj = new Object();
				obj['name'] = name;
				obj['floors'] = 0;
				obj['rooms'] = 0;
				this.buildsNames.push(obj);
				this.buildingName = '';
			},
			// 删除楼栋
			removeTab(targetName){
				for(var x in this.buildsNames){
					if(this.buildsNames[x].name == targetName){
						this.buildsNames.splice(x,1);
					}
				}
			},
			// 添加新楼栋
			addBuildFnc(){
				var builds = this.builds.length + 1;
				var buildsName = this.buildNames_;
				var floors = this.floorCount;
				var rooms = this.roomCount;
				if(buildsName == ''){
					this.notify_error('请输入正确的名称');
					return;
				}
				var reg = /\d/;
				if(!reg.test(floors) || floors == 0){
					this.notify_error('请输入正确的楼层数');
					return;
				}
				if(!reg.test(rooms) || rooms == 0){
					this.notify_error('请输入正确的房间数');
					return;
				}
				this.confim_jy('添加楼栋后不可删除,是否添加?','提示',res =>{
					this.postHttp("clockdeviceposition/creatNewBuilds",{names:buildsName,builds:builds,floors:floors,rooms:rooms},res=>{
						if(res.code == 10000){
							this.notify_success("添加成功");
							this.addBuildSync = false;
							this.loadData();
							this.propsUpdate();
						}else{
							this.notify_error(res.message);
						}
					})
				})
				
			},
			// 添加楼层
			addFloor(builds,floors){
				this.confim_jy('添加楼层后不可删除,是否添加?','提示',res =>{
					this.postHttp("clockdeviceposition/creatNewFloors",{builds:builds,floors:floors},res=>{
						if(res.code == 10000){
							this.notify_success("添加成功");
							this.loadData();
						}else{
							this.notify_error(res.message);
						}
					})
				})
				
			},	
	  	//总开关
	  	powerChange(status){
				var data = {sysKey:'power',sysVal:status}
				this.postHttp("clockconfig/updateClockConfig",data,res=>{
					if(res.code == '10000'){
						this.power = status;
						this.loadData();
					}
				})
	  	},
	  	//秒钟开关
	  	secChange(status){
				var data = {sysKey:'showSec',sysVal:status}
				this.postHttp("clockconfig/updateClockConfig",data,res=>{
					if(res.code == '10000'){
						this.showSec = status;
						this.loadData();
					}
				})
	  	},
			//css
			changeClass(i){
				var sl = ['normal','leave','unNormal','unfunc'];
				return sl[i] + ' items';
			},
	  	//切换建筑物	
	  	tabChange(tab, event){
	  	},
			// 查询绑定时钟
			findClock(id){
				this.findunClock();
				this.postHttp("clockdeviceposition/getClockDevicePositionById",{id:id},res=>{
					if(res.code == 10000){
						this.clockPosition = res.result;
						this.showEdit = true;
					}else{
						this.notify_error(res.message);
					}
				})
			},
			//查询未绑定时钟
			findunClock(){
				this.postHttp("clockdevice/getUnbindingClockDevice",{},res=>{
					if(res.code == 10000){
						this.unBinding = res.result;
					}
				})
			},
			// 绑定时钟
			binding(){
				this.postHttp("clockdeviceposition/updateClockDevicePosition",this.clockPosition,res=>{
					if(res.code == 10000){
						this.notify_success("绑定成功");
						this.loadData();
						this.showEdit = false;
						this.clockPosition = {};
					}else{
						this.notify_error(res.message);
					}
				})
			},
			//解绑时钟
			unbinding(id){
				this.confim_jy('确认解绑此设备吗?','提示',res =>{
					this.postHttp("clockdeviceposition/unBinding",{id:id},res=>{
						if(res.code == 10000){
							this.notify_success("解绑成功");
							this.loadData();
						}else{
							this.notify_error(res.message);
						}
					})
				})
				
			},
			loadData(){
				// 设备
				this.postHttp("clockdeviceposition/queryClockDevicePositions",{},res=>{
					this.builds = res.result;
				});
				//统计
				this.postHttp("clockdevice/getCounts",{},res=>{
					var rs = res.result;
					if(rs['0'] == undefined){
						rs['0'] = 0;
					}
					if(rs['1'] == undefined){
						rs['1'] = 0;
					}
					if(rs['2'] == undefined){
						rs['2'] = 0;
					}
					if(rs['3'] == undefined){
						rs['3'] = 0;
					}
					this.counts = rs;
				});
			},
			propsUpdate(){
				var times = 30000;
				this.postHttp("clockconfig/queryClockConfigs",{},res=>{
					if(res.code == 10000){
						var map = res.result;
						
						if(this.timeout == 0){
							// 定时器刷新
							if(map.refresh){
								times = parseInt(map.refresh);
							}
							this.timeout = setInterval(res=>{
								this.loadData();
							},times);
						}
						
						// 获取绑定模式
						this.models = map.models;
						this.addressmodel = map.addressmodel;
						
						//获取楼层名称
						if(map.buildingsname){
							this.buildsNameArray = eval(map.buildingsname);
						}else{
							this.showInit = true;
						}
						
						this.power = map.power;
						this.showSec = map.showSec;
					}
				});
			},
			creatNewRoom(builds,floors,rooms){
				this.confim_jy('新增房间后不可删除,是否继续?','提示',res =>{
					var data = {builds:builds,floors:floors,rooms:rooms};
					this.postHttp("clockdeviceposition/creatNewPosition",data,res=>{
						if(res.code == 10000){
							this.loadData();
						}else{
							this.notify_error(res.message)
						}
					})
				})
				
			}
	  }
}
</script>

<style>
#ClockLayout .top_ly{
	width:97%;
	height:40px;
	line-height: 40px;
	display: flex;
	margin:20px auto;
}
#ClockLayout .top_ly .count{
	display: flex;
	width:70%;
}
#ClockLayout .top_ly .count .items{
	color:#666;
	margin:0px 10px;
	font-size: 14px;
}
#ClockLayout .top_ly .btns{
	display: flex;
	width:30%;
	justify-content:flex-end;
}
#ClockLayout .top_ly .btns .items{
	margin:0px 10px;
}
#ClockLayout .top_ly .el-button:hover{
	color: #e33b3b!important;
    border-color: #f7c4c4!important;
    background-color: #fcebeb!important;
}
#ClockLayout .top_ly .btns  .el-button:focus{
	background: #fff;
    border-color: #dcdfe6;
    color: #606266;
}
#ClockLayout .top_ly .btns .el-button--primary:focus{
	color: #fff!important;
    background-color: #e33b3b!important;
    border-color: #e33b3b!important;
}
#ClockLayout .top_ly .btns .el-button--primary:hover{
	color: #fff!important;
    background-color: #e33b3b!important;
    border-color: #e33b3b!important;
}
#ClockLayout .builds{
	width: 97%;
	margin: auto;
}
#ClockLayout .builds .floors_{
	width: 100%;
	box-sizing: border-box;
}
#ClockLayout .builds .floors_ .floor_{
	width: 100%;
	padding:20px 15px;
	display: flex;
	flex-wrap:wrap;
	position:relative;
	border-bottom: 1px #bbbbbb solid;
}
#ClockLayout .builds .floors_ .floor_:last-child{
	border:none;
}
#ClockLayout .builds .floors_ .floor_ .floor_name{
	position: absolute;
	width: 10px;
	line-height: 13px;
	top:5px;
	left:5px;
	color: #999999;
	font-size: 12px;
	text-align: center;
}
#ClockLayout .builds .floors_ .floor_ .items{
	width: 170px;
	height:150px;
	margin:10px 10px;
	box-shadow: 1px 1px 10px #AAAAAA;
	position: relative;
}
#ClockLayout .builds .floors_ .floor_ .items.unfunc{
	-webkit-animation: unfunction 1s linear infinite alternate;
}
#ClockLayout .builds .floors_ .floor_ .items:before{
	position: absolute;
	font-size:14px;
	left:10px;
	top:5px;
	color:#9e9e9e;
}
#ClockLayout .builds .floors_ .floor_ .items.unNormal:before{
	content: '时间异常';
}
#ClockLayout .builds .floors_ .floor_ .items.leave:before{
	content: '离线';
}
#ClockLayout .builds .floors_ .floor_ .items.unfunc:before{
	content: '故障';
}
#ClockLayout .builds .floors_ .floor_ .items.unNormal .times{
	color:#ff8d8d;
}

#ClockLayout .builds .floors_ .floor_ .items.unNormal{
	-webkit-animation: unNormal 1s linear infinite alternate;
}

#ClockLayout .builds .floors_ .floor_ .items .btns{
	height: 30px;
	text-align: right;
	padding:0px 5px;
}
#ClockLayout .builds .floors_ .floor_ .items .btns i{
	font-size: 20px;
	margin:5px 5px;
	cursor: pointer;
}
#ClockLayout .builds .floors_ .floor_ .items .btns .el-icon-setting{
	color:#76C9FF;
}
#ClockLayout .builds .floors_ .floor_ .items .btns .el-icon-delete{
	color:#FF5C5C;
}
#ClockLayout .builds .floors_ .floor_ .items .location{
	width:100%;
	padding:5px 0px;
	font-size: 15px;
	height:40px;
	line-height: 40px;
	text-align: center;
	color:#565656;
}
#ClockLayout .builds .floors_ .floor_ .items .times{
	width:100%;
	text-align: center;
	height:60px;
	line-height: 60px;
	font-size: 40px;
}
#ClockLayout .builds .floors_ .floor_ .items.add{
	line-height: 150px;
	font-size:60px;
	color:#c7c7c7;
	text-align: center;
	cursor:pointer;
}
#ClockLayout .builds .floors_ .floor_ .items.leave{
	 -webkit-filter: grayscale(100%);
    -moz-filter: grayscale(100%);
    -ms-filter: grayscale(100%);
    -o-filter: grayscale(100%);
    filter: grayscale(100%);
    filter: gray;
}
#ClockLayout .builds .floors_ .floor_ .floor_add{
	width:100%;
	height:170px;
	line-height: 170px;
	font-size:60px;
	color:#c7c7c7;
	text-align: center;
	cursor: pointer;
}

/*css3*/
@keyframes unfunction
{
from {box-shadow: 1px 1px 10px #AAAAAA;}
to {box-shadow: 1px 1px 10px #e33b3b;}
}
@keyframes unNormal
{
from {box-shadow: 1px 1px 10px #AAAAAA;}
to {box-shadow: 1px 1px 10px #ff9800;}
}
#unfunction{
    width:120px;
    margin:auto;
    height:40px;
    border-radius:0px;
    background:#fff;
    border-radius:4px;
}
#unfunction .fl{
    width:27px;
    height:40px;
    float: left;
}
#unfunction .pipeNum{
    line-height: 36px;
    font-size: 21px;
    color:#585858;
    width:12px;
    text-align:center;
}
#unfunction .pipe{
    position:relative;
}
#unfunction .pipe .pipeItem{
    
    position:absolute;
    background:#585858;
}
#unfunction .pipe .pipeItem.error{
    background:#e33b3b;
}
#unfunction .pipe .pipeItem:nth-child(1){
    width:13px;
    height:4px;
    top:5px;
    left:7px;
}
#unfunction .pipe .pipeItem:nth-child(2){
    right: 2px;
    width: 4px;
    height: 14.5px;
    top: 5px;
}
#unfunction .pipe .pipeItem:nth-child(3){
    right: 2px;
    width: 4px;
    height: 14.5px;
    top: 20.5px;
}
#unfunction .pipe .pipeItem:nth-child(4){
    width:13px;
    height:4px;
    bottom:5px;
    left:7px;
}
#unfunction .pipe .pipeItem:nth-child(5){
    left: 2px;
    width: 4px;
    height: 14.5px;
    top: 20.5px;
}
#unfunction .pipe .pipeItem:nth-child(6){
    left: 2px;
    width: 4px;
    height: 14.5px;
    top: 5px;
}
#unfunction .pipe .pipeItem:nth-child(7){
    width:13px;
    height:4px;
    top:18px;
    left:7px;
}
/* unfunction css end*/
#ClockLayout .init{
	padding: 0px 0px 30px 0px;
}
#ClockLayout .init .buildsName{
	width:350px;
	display: flex;
	margin-bottom:30px;
}
#ClockLayout .init .buildsName button{
	margin-left: 10px;
}
#ClockLayout .init .flexs{
	display: flex;
	margin:10px auto;
}
#ClockLayout .init .flexs label{
	width:60px;
	height: 40px;
	line-height: 40px;
}
#ClockLayout .init .flexs input{
	width:260px;
}
</style>