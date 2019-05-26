<template>
	<div id="home">
		<div class="center_box">
			<div class="home_body">
				<img class="backgroundImg" src="../../static/img/header/bg-s.png" />
				<div class="personal">
					<img src="../../static/img/header/n3.png">
					<div class="personalDetail">
						<p>姓名：{{name}}</p>
						<p v-show='xueji'>学籍号：{{code}}</p>
						<p v-show='jiaoshi'>教师职称：{{code}}</p>
					</div>
				</div>
				<div id="average">
				</div>
			</div>
		</div>
		<div class="home_main_body">
			<el-row :gutter="20">
				<el-col :span="8">
					<div class="gotoReport homeBox" @click="gotoReport">
						<img src="../../static/img/header/cj.png" />
						<div class="bottomBtn">成绩报告</div>
					</div>
				</el-col>
				<el-col :span="8">
					<div class="gotoResource homeBox" @click="gotoManagement">
						<img src="../../static/img/header/zy.png" />
						<div class="bottomBtn">资源中心</div>
					</div>
				</el-col>
				<el-col :span="8">
					<div class="gotoManagement homeBox" @click="gotoResource">
						<img src="../../static/img/header/ks.png" />
						<div class="bottomBtn">考试管理</div>
					</div>
				</el-col>
			</el-row>
		</div>
	</div>
</template>

<script>
export default {
	data () {
		return {
			msg: '顶部导航栏',
			report:'',
			xueji:true,
			username:'用户13974289150',
			name:'王丫丫',
			jiaoshi:false,
			code:'201706070003',
			option1:{
				color: ['#D5D5D5'],
				textStyle:{
					color:'#E8E8E8'
				},
				tooltip : {
					trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'       // 默认为直线，可选为：'line' | 'shadow'
		            
		        },
		        formatter:'{b}:\n{c}%'
		    },
		    grid: {
		    	left: '3%',
		    	right: '4%',
		    	bottom: '3%',
		    	containLabel: true
		    },
		    xAxis : [
		    {
		    	type:'category',
		    	data : ['高分率 90%~100%', '优秀率 80%~90%', '良好率 70%~80%', '合格率 60%~70%', '不及格 率0%~ 60%'],
		    	axisTick: {
		    		alignWithLabel: true
		    	},
		    	axisLine:{
		    		lineStyle:{color:'#E8E8E8'},
		    	},
		    	axisLabel:{
		    		formatter:""
		    	},
		    	color:'red'
		    }
		    ],
		    yAxis : [
		    {
		    	type: 'value', 
		    	max:'100',
		    	axisLabel: {  
		    		show: true,  
		    		interval: 'auto',  
		    		formatter: '{value} %'  
		    	},
		    	axisLine:{
		    		lineStyle:{color:'#E8E8E8'}
		    	},
		    	splitLine: {lineStyle:{type:'dashed'}}

		    }
		    ],
		    series : [
		    {
		    	name:'占比',
		    	type:'bar',
		    	barWidth: '60%',
		    	data:[62,78,  
		    	{value : 43,
		    		itemStyle:{normal:{color:"#FFD700"}}
		    	},50, 31
		    	]
		    }
		    ]
		}
	}
},
created: function(){
	this.option1.xAxis[0].axisLabel.formatter=function(val){ 
        var strs = val.split(''); //字符串数组  
        var str = ''  
        for (var i = 0, s; s = strs[i++];) { //遍历字符串数组  
            str += s;  
            if (!(i % 4)) str += '\n';  
        }  
        return str  
        }
       this.$emit('refreshbizlines','home');
      },
    mounted:function(){
  	//判断cookie登陆信息初始化
  	this.getLoginUser();
  },
  methods:{
  	initPrate(e){
  		if(e<1||e==1){
  			return e*1000000/10000 ;
  		}else{
  			return e;
  		}

  	},
  	gotoReport:function(){
  		//页面跳转
  		this.$router.push({path:this.report});
  	},
  	gotoResource:function(){
  		this.$router.push({path:'/resource'});
  	},
  	gotoManagement:function(){
  		this.$router.push({path:'/management'});
  	},
  	getLoginUser:function(){
  		var datas = [];
  		this.postHttp(this,'',"user/getLoginUser",function(obj,data){
  			obj.name = data.result.name;obj.username = data.result.userName;

  			if(data.result.roleName=="超级管理员"){

  			}else{
  				if(data.result.roleName=='老师'||data.result.roleName=='教师'){
  					obj.xueji = false;
  					obj.jiaoshi = true;
  					obj.report = '/report/schoolLevel'
  				}
  				if(data.result.roleName=='学生'){
  					obj.xueji = true;
  					obj.jiaoshi = false;
  					obj.report = '/report/studentLevel'
  				}
  				datas = [];
  				obj.postHttp(obj,'',"score/getHomePageChart",function(objs,data){
  					if(data.code!='10000'){
  						objs.code = '-';
  					}
  					if(data.result=="没有该教师所在班级的最近一场考试数据"){
  						objs.name = data.result.name
  						objs.code = data.result.label
  						objs.option1.series[0].data =[0,0,0,0,0];
  						objs.echarts.init(document.getElementById("average")).setOption(obj.option1);
  					}else if(data.code=='10000'){
  						objs.name = data.result.name
  						objs.code = data.result.label
  						datas = [obj.initPrate(data.result.rateMap.highRate),
  						obj.initPrate(data.result.rateMap.excellentRate),
  						obj.initPrate(data.result.rateMap.commissionRate),
  						obj.initPrate(data.result.rateMap.passRate),
  						obj.initPrate(data.result.rateMap.failureRate)];
  						if(data.result.studentLevel=='高分'){
  							datas[0] = {value : obj.initPrate(data.result.rateMap.highRate),itemStyle:{normal:{color:"#FFD700"}}}
  						}
  						if(data.result.studentLevel=='优秀'){
  							datas[1] = {value : obj.initPrate(data.result.rateMap.excellentRate),itemStyle:{normal:{color:"#FFD700"}}}
  						}
  						if(data.result.studentLevel=='良好'){
  							datas[2] = {value : obj.initPrate(data.result.rateMap.commissionRate),itemStyle:{normal:{color:"#FFD700"}}}
  						}
  						if(data.result.studentLevel=='合格'){
  							datas[3] = {value : obj.initPrate(data.result.rateMap.passRate),itemStyle:{normal:{color:"#FFD700"}}}
  						}
  						if(data.result.studentLevel=='不及格'){
  							datas[4] = {value : obj.initPrate(data.result.rateMap.failureRate),itemStyle:{normal:{color:"#FFD700"}}}
  						}
  						objs.option1.series[0].data =datas;
  						objs.echarts.init(document.getElementById("average")).setOption(obj.option1);
  					}

  				});
  			}

  		});

  	}
  }
}


</script>

<style>
#home {
	
}
#home #average{
	width: 350px;
	height: 200px;
	position: absolute;
	top: 150px;
	left: 70px
}
#home .center_box{
	padding-top: 60px;
	background-color: #44A9FF
}
#home .home_body{
	width: 1100px;
	margin: auto;
	position: relative;
}
#home .home_body .backgroundImg{
	width: 1100px;
}
#home .home_main_body{
	width: 1100px;
	margin: auto;
	margin-top: 20px
}
#home .personal{
	width: 500px;
	position: absolute;
	top: 70px;
	left: 80px
}
#home .personal img,#home .personal .personalDetail{
	display: block;
	float: left;
}
#home .personal .personalDetail{
	margin-left: 10px;
	margin-top: 10px
}
#home .personal .personalDetail p {
	margin-top: 6px;
	color: white
}
#home .homeBox{
	border-radius: 3px;
	cursor: pointer;
}
#home .homeBox:hover{box-shadow: 1px 1px 14px rgba(0,0,0,.15);}
#home .homeBox img{
	margin: 52px
}
#home .gotoReport{
	border: 1px solid #44A9FF;
}
#home .gotoReport .bottomBtn{
	width: 100%;
	height: 70px;
	border-radius: 0px 0px 3px 3px;
	background-color: #44A9FF;
	color: white;
	text-align: center;
	line-height: 70px;
}
#home .gotoResource{
	border: 1px solid #93C454;
}
#home .gotoResource .bottomBtn{
	width: 100%;
	height: 70px;
	border-radius: 0px 0px 3px 3px;
	background-color: #93C454;
	color: white;
	text-align: center;
	line-height: 70px;
}
#home .gotoManagement{
	border: 1px solid #F3DA53;
}
#home .gotoManagement .bottomBtn{
	width: 100%;
	height: 70px;
	border-radius: 0px 0px 3px 3px;
	background-color: #F3DA53;
	color: white;
	text-align: center;
	line-height: 70px;
}
</style>