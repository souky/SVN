<template>
	<div id="ClockLayout" class="main_body">
		<!--query-->
		<div class="query">
			<div class="query_items">
				<div class="text">楼栋数:</div>
				<div class="inputs">
					<el-input v-model="querys.builds" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="text">楼层数:</div>
				<div class="inputs">
					<el-input v-model="querys.floors" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="text">房间数:</div>
				<div class="inputs">
					<el-input v-model="querys.rooms" placeholder="请输入内容"></el-input>
				</div>
			</div>
			<div class="query_items">
				<div class="query_button" @click="makeInit()">
					<el-button type="primary">生成</el-button>
				</div>
			</div>
		</div>
		<!--count-->
		<div class="top_ly">
			<div class="count">
				<div class="items">在线：{{counts.online}}</div>
				<div class="items">离线：{{counts.offline}}</div>
				<div class="items">故障：{{counts.unfunc}}</div>
			</div>
			<div class="btns">
				<div class="items">
					<el-button v-if="selfExam == 1" type="primary" :loading="true" size="mini">自检中</el-button>
					<el-button v-else  size="mini"  @click="selfExams()">自检</el-button>
				</div>
				<div class="items">
					<el-button v-if="power == 1" type="primary"  size="mini" @click="powerChange('0')">时钟开</el-button>
					<el-button v-else  size="mini"  @click="powerChange('1')">时钟关</el-button>
				</div>
				<div class="items">
					<el-button v-if="showSec == 1" type="primary" size="mini" @click="secChange('0')">秒钟开</el-button>
					<el-button v-else  size="mini"  @click="secChange('1')">秒钟关</el-button>
				</div>
			</div>
		</div>
		<div class="builds">
			<!--builds-->
			<el-tabs v-model="activeBuild" @tab-click="tabChange">
				<el-tab-pane v-for="e in builds" :key="e.id" :label="e.name">
					
					<div class="floors_">
						<div v-for="(f,index) in e.floors" :key="f.id" class="floor_">
							<div class="floor_name">{{floorChina[index]}}</div>
							<div v-for="r in f.rooms">
								<el-popover v-if="devices[r.deviceId] && devices[r.deviceId].unfunc == 1" placement="top" width="150" trigger="hover" >	
									<!--unfunc-->
									<div id="unfunction">
										<template v-for="(a,index) in 5">
											<div v-if="index != 2" class="pipe fl">
												<template v-if='devices[r.deviceId].pipeError' v-for="(b,indexs) in 7">
													<div v-if="devices[r.deviceId].pipeError[index][indexs] == '1'" class="pipeItem error "></div>
													<div v-else class="pipeItem "></div>
												</template>
											</div>
											<div v-else class="pipeNum fl">:</div>
										</template>
									</div>
									<div slot="reference" class="items unfunc">
										<div class="btns">
											<i class="el-icon-setting"></i>
											<i class="el-icon-delete"></i>
										</div>
										<div class="location">
											<i v-if="devices[r.deviceId] && devices[r.deviceId].address != '' " class="el-icon-location-outline"></i>
											{{devices[r.deviceId].address}}
										</div>
										<div v-if="devices[r.deviceId] && devices[r.deviceId].times != ''" class="times">
											{{devices[r.deviceId].times}}
										</div>
										<div v-else class="times">
											--:--:--
										</div>
									</div>
								</el-popover>
								<div v-if="devices[r.deviceId]==undefined || devices[r.deviceId].unfunc == 2" class="items">
									<div class="btns">
										<i class="el-icon-setting"></i>
										<i class="el-icon-delete"></i>
									</div>
									<div v-if="devices[r.deviceId] && devices[r.deviceId].address != '' " class="location">
										<i class="el-icon-location-outline"></i>
										{{devices[r.deviceId].address}}
									</div>
									<div v-else class="location"></div>
									<div v-if="devices[r.deviceId] && devices[r.deviceId].times != ''" class="times">
										{{devices[r.deviceId].times}}
									</div>
									<div v-else class="times"></div>
								</div>
							</div>
							<div class="items add">
								<i class="el-icon-circle-plus-outline"></i>
							</div>
							
						</div>
						
					</div>
				</el-tab-pane>
			</el-tabs>
		</div>
	</div>
</template>

<script>
export default {
	  data () {
	    return {
	      msg: 'ClockLayout',
	      querys:{
	      	builds:1,
	      	floors:1,
	      	rooms:1
	      },
	      counts:{
	      	online:12,
	      	offline:11,
	      	unfunc:0
	      },
	      selfExam:0,
	      power:1,
	      showSec:1,
	      activeBuild:'0',
	      floorChina:['一楼','二楼','三楼','四楼','五楼','六楼','七楼','八楼','九楼'],
	      visible:true,
	      builds:[
	      	{id:'a1',name:'楼栋1',floor:[
	      		{id:'sasd'}
	      	]},
	      	{id:'a2',name:'楼栋2'},
	      	{id:'a3',name:'楼栋3'},
	      	{id:'a4',name:'楼栋4'}
	      ],
	      devices:{},
	      
	    }
	  },
	  mounted:function(){
		var datas = this.Mock.mock({
		    'list|4': [{
		        'id': /([A-Z][0-9]){16}/,
		        'name':'@ctitle(3, 5)',
		        'floors|1-4':[{
		        		'id': /([A-Z][0-9]){16}/,
		        		'rooms|1-10':[{
		        			'id': /([A-Z][0-9]){16}/,
		        			'deviceId|+1':1,
		        		}]
		        }]
		    }]
		});
		var divice_ = new Object();
		for(var i = 1;i<4;i++){
			divice_[''+i] = this.Mock.mock({
				'id': /([A-Z][0-9]){16}/,
				'status|1':['1','2'],
				'unfunc|1':['1','2'],
				'times':'@time',
				'pipeError':[
					['1','0','0','1','0','0','1'],['1','0','0','1','0','0','1'],['1','0','0','1','0','0','1'],['1','0','0','1','0','0','1'],['1','0','0','1','0','0','1'],['1','0','0','1','0','0','1']
				],
				'address':'@ctitle(3,6)'
			})
		}
		this.devices = divice_;
		this.builds = datas.list;
		console.log(datas.list)
	  },
	  methods:{
	  	// 初始化楼层
	  	makeInit(){
	  		this.confim_jy('生成楼层会抹掉已有楼层数据,是否继续?','提示',res =>{
	  			this.validateInit();
	  			console.log(this.querys);
	  		})
	  	},
	  	//验证楼层数据
	  	validateInit(){
	  		var reg = /\d/;
	  		var builds = this.querys.builds;
	  		if(!reg.test(builds)){
	  			this.notify_error('请输入正确楼栋数');
	  			return;
	  		}
	  		var floors = this.querys.floors;
	  		if(!reg.test(floors)){
	  			this.notify_error('请输入正确楼层数');
	  			return;
	  		}
	  		var rooms = this.querys.rooms;
	  		if(!reg.test(rooms)){
	  			this.notify_error('请输入正确房间数');
	  			return;
	  		}
	  	},
	  	//自检
	  	selfExams(){
	  		this.selfExam = 1;
	  		this.notify_success('时钟自检时间较长,请耐心等待')
	  		setTimeout(res =>{
	  			this.selfExam = 0
	  		},5000)
	  	},
	  	//总开关
	  	powerChange(status){
	  		this.power = status;
	  	},
	  	//秒钟开关
	  	secChange(status){
	  		this.showSec = status;
	  	},
	  	//切换建筑物	
	  	tabChange(tab, event){
	  		 console.log(tab, event);
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
	margin:auto;
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
}
#ClockLayout .builds .floors_ .floor_ .items.unfunc{
	-webkit-animation: unfunction 1s linear infinite alternate;
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

/*css3*/
@keyframes unfunction
{
from {box-shadow: 1px 1px 10px #AAAAAA;}
to {box-shadow: 1px 1px 10px #e33b3b;}
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

</style>