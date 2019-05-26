<template>
	<div id="schoolLevel" class="mainbody">
		<ul id="navInside" >
			<li v-for="item in liList" :class="activeList == item.id? 'active': ''" :key="item.id" @click="testtest(item.id)">{{item.name}}</li>
		</ul>
		<el-carousel :interval="5000" indicator-position="none" ref="carousel" @change="testChange($event)" arrow="always" :autoplay="false">
			<el-carousel-item v-for="item in testList" :key="item.id" :name="item.examName">
				<p class="alltest" @click="selectShow">{{item.examName}}</p>
			</el-carousel-item>
		</el-carousel>
		<el-collapse-transition>
			<ul v-if='showselect' class="showselect">
				<li v-for="item in testList" :key="item.id" @click="changetest(item.id,item.examName)">{{item.examName}}</li>
			</ul>
		</el-collapse-transition>
		<div class="header louceng">
			<p>学校整体成绩报告单</p>
			<div class="header-title-foot"></div>
		</div>
		<div id="overallReport">
			<el-table :data="tableData1" class="borders" style="width: 100%" header-cell-class-name="formatRow" :row-class-name="rowsClassName" >
				<el-table-column width="50" prop="subject" label="学科">
				</el-table-column>
				<el-table-column width="80" :formatter="setMan" label="统计人数">
				</el-table-column>
				<el-table-column width="50" prop="fullMarks" label="满分">
				</el-table-column>
				<el-table-column prop="schoolAvgScore" :formatter='setParse' label="平均分 (学校)">
				</el-table-column>
				<el-table-column :formatter="setRow" label="平均分 (地区)">
				</el-table-column>
				<el-table-column width="80" :formatter="setRow" label="离均差">
				</el-table-column>
				<el-table-column :formatter="setRow" label=" 排名 (地区)">
				</el-table-column>
				<el-table-column width="80" prop="schoolTopScore" :formatter='setParse' label="最高分">
				</el-table-column>
				<el-table-column prop="highRate" :formatter='setParse' label=" 高分率 [90%-100%]">
				</el-table-column>
				<el-table-column prop="excellentRate" :formatter='setParse' label=" 优秀率 [80%-90%)">
				</el-table-column>
				<el-table-column prop="commissionRate" :formatter='setParse' label=" 良好率 [70%-80%)">
				</el-table-column>
				<el-table-column prop="passRate" :formatter='setParse' label=" 合格率 [60%-70%)">
				</el-table-column>
				<el-table-column prop="failureRate" :formatter='setParse' label=" 不及格率 (60%以下)">
				</el-table-column>
			</el-table>
			<!--  <p class="testTips">本次考试中，我校参与统计人数{{countP}}人，总分平均分{{totalCount}}分。各学科中{{goodsuject}}平均分表现较好；{{lowSuject}}科目表现较弱。</p> -->
		</div>

		<div class="header">
			<p>学生等级分布图</p>
			<div class="header-title-foot"></div>
		</div>
		<div id="ranked">
			<div id="rankedchart"></div>
		  		<!-- 产品版暂时不要<div class="schoolSelectBox">
					  <el-select v-model="changeSchool" class="myselect" placeholder="请选择">
					    <el-option
					      v-for="item in schoolList"
					      :key="item.id"
					      :label="item.name"
					      :value="item.id">
					    </el-option>
					  </el-select>
					<el-button type="primary" @click="compareSchool" plain>学校对比</el-button>

		  		</div> -->
		  </div>
		   <div class="header louceng" >
			<p>各班成绩报告单</p>
			<div class="header-title-foot"></div>
		  </div>
		  <div id="reportClass">
		  		<el-table :data="tableData2" class="borders" style="width: 100%" header-cell-class-name="formatRow" :row-class-name="rowsClassName" >
		      <el-table-column width="50" prop="schoolRanking" label="排名">
		      </el-table-column>
		      <el-table-column  prop="classroomName" label="班级">
		      </el-table-column>
		      <el-table-column prop="classTeacherName" label="班主任">
		      </el-table-column>
		      <el-table-column prop="classAvgScore" label="总分平均分">
		      </el-table-column>
		      <el-table-column width="60" prop="classTopScore" label="最高分">
		      </el-table-column>
		      <el-table-column width="60" prop="classMinScore" label="最低分">
		      </el-table-column>
		      <el-table-column prop="classTotalStandardDeviation" label="标准差">
		      </el-table-column>
		      <el-table-column prop="classTotalDiffCoefficient" label="分化程度">
		      </el-table-column>
		      <el-table-column prop="highRate" :formatter='setParse' label=" 高分率 [90%-100%]">
		      </el-table-column>
		      <el-table-column prop="excellentRate" :formatter='setParse' label=" 优秀率 [80%-90%)">
		      </el-table-column>
		      <el-table-column prop="commissionRate" :formatter='setParse' label=" 良好率 [70%-80%)">
		      </el-table-column>
		      <el-table-column prop="passRate" :formatter='setParse' label=" 合格率 [60%-70%)">
		      </el-table-column>
		      <el-table-column prop="failureRate" :formatter='setParse' label=" 不及格率 (60%以下)">
		      </el-table-column>
		    </el-table>
		  </div>

		  <div class="header">
			<p>各班平均分对比</p>
			<div class="header-title-foot"></div>
		  </div>
		  <div id="averageCompare">
		  		<div id="averageChart"></div>
		  		<p class="testTips">{{subjAvgComparation}}</p>
		  		<div class="allaverag"><img src="/static/img/header/Group.png" /> 本校平均分</div>
		  </div>

		  <div class="header louceng">
			<p>各班级等级分布对比</p>
			<div class="header-title-foot"></div>
		  </div>
		  <div id="contrastive">
		  	<div id="contrastiveChart">
		  	
		  	</div>
		  <p class="testTips">{{classesLevelDistri}}</p>
		  </div>


			<div class="header">
				<p>各班前？名对比图</p>
				<div class="header-title-foot"></div>
			</div>
			<div id="topCompared">
				<div id="topComparedChart">

				</div>
				<!-- <p class="testTips">我校及格率较高的前三名为{{classS}}。其中{{classH}}班的及格率达到{{classH}}%。{{classH}}班的优秀率为{{classH}}%，其他班级优秀率为零。{{classL}}班的不及格率较高。</p> -->
			</div>
			<div class="header louceng">
				<p>科目成绩报告单</p>
				<div class="header-title-foot"></div>
			</div>
			<div id="achievement">
				<div class="achievementSelectBox">
					<el-select v-model="changeSchool" @change="changeReprot($event)" class="myselect" placeholder="请选择">
					    <el-option
					      v-for="item in classList"
					      :key="item"
					      :label="item"
					      :value="item">
					    </el-option>
					</el-select>
		  	</div>
		  	<div id="achievementTable">
			  	<el-table :data="tableData3" class="borders" style="width: 100%" header-cell-class-name="formatRow" :row-class-name="rowsClassName" >
			      <el-table-column width="50" prop="schoolRanking" label="排名">
			      </el-table-column>
			      <el-table-column prop="classroomName" label="班级">
			      </el-table-column>
			      <el-table-column width="100" prop="classTeacherName" label="教师">
			      </el-table-column>
			      <el-table-column prop="classSubjectAvgScore" width="140" label="班级科目平均分">
			      </el-table-column>
			      <el-table-column prop="classAvgScore" width="140" label="班级总分平均分">
			      </el-table-column>
			      <el-table-column prop="classSubjectStandardScore" width="140" label="班级科目标准分">
			      </el-table-column>
			      <el-table-column prop="classTotalStandardScore" width="140" label="班级总分标准分">
			      </el-table-column>
			      <el-table-column prop="contribution" label="科目贡献率(班级课目标准分/班级总分标准分*100)">
			      </el-table-column>
			    </el-table>
			    
		  	</div>
		  	<div class="header">
				<p>贡献率折线图</p>
				<div class="header-title-foot"></div>
			</div>
			<div id="achievementChart"></div>
			<p class="testTips">{{mycontribution}}</p>
		</div>
	</div>	
</template>

<script>
import IndexData from '../../assets/data/schoolLevel.js'
export default {
	data(){
		return {
			ides:'',
			activeList:0,
			liList:[{
				name:'学校整体成绩报告单',
				id:0
			},{
				name:'各班成绩报告单',
				id:1
			},{
				name:'各班级等级分布对比',
				id:2
			},{
				name:'科目成绩报告单',
				id:3
			}],
			classList:[],
			classroom:[],
			setmans:0,
			autoplay:false,
			alse:'二中',
			IndexData,
			testList:[],
			showselect:false,
			schoolTest:'',
			changeSchool:'',
			schoolList:[{
				id:'001',
				name:'一中'
			},{
				id:'002',
				name:'二中'
			}],
			tableData1:[],
			countP:'',totalCount:'',hightCount:'',ranking:'',
			goodsuject:[],hightavarge:[],allNumber:[],lowSuject:[],lowavarge:[],
			allRanking:[],option1:{},tableData2:[],option2:{},option3:{},
			classNumble:'',classS:[],classH:'',missdistance:'',classL:[],classLs:'',
			tableData3:[],option4:{},option5:{},subjAvgComparation:'',mycontribution:'',
			classesLevelDistri:''
		}
	},
    methods: {
    	initAll:function(){
    		
    		var needData = {tab:'SCHOOL_REPORT'};
	    	this.postHttp(this,'',"exam/getExamListForTab",function(obj,data){
	    	   obj.testList=[];obj.changeSchool='';
	           for(var value of data.result.exams){
	           		obj.testList.push(value);
	           		
	           		
	           }
	          
	        });
	    	
	    
    	},
    	//格式化
    	setRow(){
    		return '-'
    	}, 
    	//格式化统计人数
    	setMan(){
    		return this.setmans
    	},
    	setParse(row, column){
    		var e = row[column.property];
    		if(e<1){
    			return e*1000000/10000 + '%';
    		}else{
    			return e;
    		}

    	},
    	initPrate(e){
    		if(e<1){
    			return e*1000000/10000 ;
    		}else{
    			return e;
    		}

    	},
    	testChange(e){
    		var needData = {tab:'SCHOOL_REPORT',examId:this.testList[e].id};
    		this.ides = this.testList[e].id;
    		this.postHttp(this,'',"exam/getExamListForTab",function(obj,data){
    			    obj.classList = [];
	           		obj.classroom = data.result.exams[e].classroom.split(",");
	           		obj.classList = data.result.exams[e].subject.split(",");
	           		obj.changeSchool = obj.classList[0];
	        });
    		this.postHttp(this,needData,"score/getLevelDistribution",function(obj,data){
    			if(data.result == undefined){
    				var data1 = [0,0,0,0,0]
    			}else{
    				var data1 = [obj.initPrate(data.result.highRate),
    				obj.initPrate(data.result.excellentRate),
    				obj.initPrate(data.result.commissionRate),
    				obj.initPrate(data.result.passRate),
    				obj.initPrate(data.result.failureRate)];
    			}
    			obj.option1.series[0].data = data1;
	           obj.echarts.init(document.getElementById("rankedchart")).setOption(obj.option1);
	        });
	        this.postHttp(this,needData,"score/getEachClassTopScores",function(obj,data){
	    		var datax ; var data10 ; var data20 ;
	    		var data50 ; var data100 ; var data200 ;
	    		var data500; var data1000;
	    		if(data.result == "没有最近一次考试的相关数据"){
	    			datax = [];data10 = [];data20 = [];data50 = [];
	    			data100 = [];data200 = [];data500 = [];data1000 = [];
	    			
					datax = obj.classroom;
					for(var a of obj.classroom){
		           		data10.push(0);
		           		data20.push(0);
		           		data50.push(0);
		           		data100.push(0);
		           		data200.push(0);
		           		data500.push(0);
		           		data1000.push(0);
		           }
	    		}else{
	    			datax = [];data10 = [];data20 = [];data50 = [];
	    			data100 = [];data200 = [];data500 = [];data1000 = [];
		    		for(var value of data.result){
		           		datax.push(value.classroomName);
		           		data10.push(value.classTopTenStuNum);
		           		data20.push(value.classTopTwentyStuNum);
		           		data50.push(value.classTopFiftyStuNum);
		           		data100.push(value.classTopOneHundredStuNum);
		           		data200.push(value.classTopTwoHundredStuNum);
		           		data500.push(value.classTopFiveHundredStuNum);
		           		data1000.push(value.classTopOneThousandStuNum);
		           	}
		           	
	       		}
	       		obj.option3.xAxis[0].data = datax;
		           obj.option3.series[0].data = data10;obj.option3.series[1].data = data20;
		           obj.option3.series[2].data = data50;obj.option3.series[3].data = data100;
		           obj.option3.series[4].data = data200;obj.option3.series[5].data = data500;
		           obj.option3.series[6].data = data1000;
		           obj.echarts.init(document.getElementById("topComparedChart")).setOption(obj.option3);
	        });
	    	this.postHttp(this,{tab:'SCHOOL_REPORT',examId:this.testList[e].id,subject:this.changeSchool},"score/geReportCards",function(obj,data){
	    		if(data.result =="该考试尚未制定双向细目表"||data.code=='20000'){
					obj.tableData1 = [];
		           obj.tableData2 = [];
		           obj.option2.xAxis[0].data = obj.classroom;
		           obj.option2.series[0].data = [];
		           obj.option4.series[0].data = [];
		           for(var a of obj.classroom){
		           		obj.option2.series[0].data.push(0);
		           		obj.option4.series[0].data.push(0);
		           }
		           obj.mycontribution = '';
		           obj.subjAvgComparation ='';
		           obj.option2.series[0].markLine.data[0].yAxis = 0;
		           obj.echarts.init(document.getElementById("averageChart")).setOption(obj.option2);
		           obj.tableData3 = [];
		           obj.option4.xAxis[0].data = obj.classroom;
		           obj.echarts.init(document.getElementById("achievementChart")).setOption(obj.option4);
				}else{
	    		   obj.tableData1 = data.result.scoreVOList;
		           obj.tableData2 = data.result.classScoreVOList;
		           obj.option2.series[0].data = data.result.avgList;
		           obj.option2.series[0].data[0] = {value:data.result.avgList[0],itemStyle:{normal:{color:"#FF8585"}}}
				   obj.option2.xAxis[0].data = data.result.classList;
				   obj.option2.series[0].markLine.data[0].yAxis = data.result.schoolAvgTotalScore;
		           obj.echarts.init(document.getElementById("averageChart")).setOption(obj.option2);
		           obj.setmans = data.result.studentNum;
		           obj.tableData3 = data.result.classSubScoreList;
		           var comtribution = [];
		           for(var a of data.result.classSubScoreList){
		           		comtribution.push(a.contribution);
		           }
		           obj.option4.series[0].data = comtribution;
		           obj.option4.xAxis[0].data = data.result.classList;
		           obj.echarts.init(document.getElementById("achievementChart")).setOption(obj.option4);
		           obj.subjAvgComparation = data.result.summaryVO.subjAvgComparation;
		           obj.mycontribution = data.result.summaryVO.contribution;
	    		}
	        });
			this.postHttp(this,needData,"score/getEachClassLevelDistribution",function(obj,data){
				var xAxisD = [];var highRates;var excellentRates;
				var commissionRates;var passRates;var failureRates;
				var xdatas = [];
	    	   	if(data.result=="没有最近一次考试的相关数据"){
	    	   		obj.option5.xAxis.data =[];
	    	   		obj.option5.xAxis.data=obj.classroom;
	    	   		obj.option5.xAxis.data.unshift("全校");
	    	   		for(var a of obj.classroom){
	    	   			xAxisD.push(0)
	    	   		}
	    	   		obj.option5.series[0].data = xAxisD;
	    	   		obj.option5.series[1].data = xAxisD;
	    	   		obj.option5.series[2].data = xAxisD;
	    	   		obj.option5.series[3].data = xAxisD;
	    	   		obj.option5.series[4].data = xAxisD;
					obj.echarts.init(document.getElementById("contrastiveChart")).setOption(obj.option5);
					obj.classesLevelDistri = '';
	    	   	}else{
	    	   		highRates=[];excellentRates=[];
	    	   		commissionRates=[];passRates=[];
	    	   		failureRates=[];obj.option5.xAxis.data =[];
	    	   		xdatas=[];
	    	   		for(var a of data.result.scoreVOList){
	    	   			highRates.push(obj.initPrate(a.highRate));
	    	   			excellentRates.push(obj.initPrate(a.excellentRate));
	    	   			commissionRates.push(obj.initPrate(a.commissionRate));
	    	   			passRates.push(obj.initPrate(a.passRate));
	    	   			failureRates.push(obj.initPrate(a.failureRate));
	    	   			xdatas.push(a.classroomName)
					}
					obj.option5.xAxis.data=xdatas;
					obj.option5.series[0].data = highRates;
	    	   		obj.option5.series[1].data = excellentRates;
	    	   		obj.option5.series[2].data = commissionRates;
	    	   		obj.option5.series[3].data = passRates;
	    	   		obj.option5.series[4].data = failureRates;
					obj.echarts.init(document.getElementById("contrastiveChart")).setOption(obj.option5);
					obj.classesLevelDistri = data.result.summaryVO.classesLevelDistri;
	    	   	}
	        });
    	},    
    	selectShow:function(){
    		this.showselect = !this.showselect
    	},
    	changetest:function(e,ename){
    		this.showselect = !this.showselect;
    		this.$refs.carousel.setActiveItem(ename);
    	},
    	tedt:function(){


    	},
    	rowsClassName:function({row, rowIndex}){
    		if(rowIndex%2===1)
    			return 'tableBackground'
    		else 
    			return 'tableCenter'
    	},
    	eachWork:function(e,a){
    		var eachWorks='';
    		if(e.length>0){
    			for(var i=0;i<e.length;i++){
    				if(i==e.length-1)
    					eachWorks +=e[i];
    				else
    					eachWorks +=e[i]+a;
    			}
    		}
    		return eachWorks;
    	},
    	
    	testtest:function(e){
    		var olouceng = document.getElementsByClassName("louceng");
    		var oNav = document.getElementById("navInside").getElementsByTagName("li");
    		this.activeList = e;
    		window.scrollTo(0 ,olouceng[e].offsetTop);
    	},
    	changeReprot(e){
			this.postHttp(this,{tab:'SCHOOL_REPORT',examId:this.ides,subject:e},"score/geReportCards",function(obj,data){
				if(data.result =="该考试尚未制定双向细目表"||data.code=='20000'){
					obj.tableData3 = [];
					obj.option4.series[0].data = [];
					obj.option4.xAxis[0].data = obj.classroom;
					obj.echarts.init(document.getElementById("achievementChart")).setOption(obj.option4);
					obj.mycontribution = '';
				}else{
					obj.tableData3 = [];obj.option4.series[0].data = [];
					obj.option4.xAxis[0].data = [];
				   obj.tableData3 = data.result.classSubScoreList;
		           var comtribution = [];
		           for(var a of data.result.classSubScoreList){
		           		comtribution.push(a.contribution);
		           }
		           obj.option4.series[0].data = comtribution;
		           obj.option4.xAxis[0].data = data.result.classList;
		           obj.echarts.init(document.getElementById("achievementChart")).setOption(obj.option4);
		           obj.mycontribution = data.result.summaryVO.contribution;
				}
	           
	        });
    	}
    },
    mounted:function(){
    	var olouceng = document.getElementsByClassName("louceng");
    	var oNav = document.getElementById("navInside").getElementsByTagName("li");
    	window.addEventListener('scroll',()=>{
    		var oheight = document.documentElement.clientHeight || document.body.clientHeight;
    		var otop = document.documentElement.scrollTop || document.body.scrollTop;
    		if(otop>=250){
    			for(var i=0;i<olouceng.length;i++){
    				if(oheight+otop-olouceng[i].offsetTop>oheight/1.1){
    					for(var j=0;j<oNav.length;j++){
    						oNav[j].className = '';
    					}
    					oNav[i].className = 'active'
    				}

    			}
    		}else{
    		}
    	});
    	window.addEventListener('click',(e)=>{
    		if(e.target.className!='alltest'){
    			this.showselect =false
    		}
    	});
		//接js模拟数据
    	this.tableData1 = this.IndexData.tableData1;this.countP = this.IndexData.countP;
    	this.totalCount = this.IndexData.totalCount;this.hightCount = this.IndexData.hightCount;
    	this.ranking = this.IndexData.ranking;
    	this.option1 = this.IndexData.option1;
    	this.option2 = this.IndexData.option2;
    	this.classNumble = this.IndexData.classNumble;
    	this.classH = this.IndexData.classH;
    	this.missdistance = this.IndexData.missdistance;
    	this.classLs = this.IndexData.classLs;
    	this.option3 = this.IndexData.option3;
    	this.tableData3 = this.IndexData.tableData3;
    	this.option4 = this.IndexData.option4;
    	this.option5 = this.IndexData.option5;
    	//等级分布图
    	this.initAll();
    },
    updated:function(){
    	if(this.$route.query.name!=undefined){
    		for(var a of this.testList){
    			if(a.id==this.$route.query.name){
    				this.$refs.carousel.setActiveItem(a.examName);
    			}
    		}
    	}
    }
}
</script>
<style>
.mainbody{
	width: 1100px;
	margin: auto;
	position: relative;
	border-top: 1px solid #f2f2f2
}
#schoolLevel .el-carousel {
	overflow: hidden;
	position: relative;
	height: 50px;
	text-align: center;
	width: 400px;
	margin: auto;
	margin-top: 30px;
	line-height: 50px;
}
#schoolLevel .el-carousel__container {
	position: relative;
	height: 50px;
	margin: auto;
}
#schoolLevel .myselect{
	border:1px solid #44a9ff;
	width: 90px;
	border-radius: 4px;
}
#schoolLevel .testTips{
	color:#3d3d3d;
	font-size: 14px;
	margin-top: 25px
}
#schoolLevel .schoolSelectBox{
	width: 200px;
	margin: auto;
	margin-bottom: 35px;
}
#schoolLevel .achievementSelectBox{
	width: 90px;
	margin: auto;
	margin-bottom: 35px;
}
#schoolLevel #averageCompare{
	position: relative;
}
#schoolLevel #averageCompare .allaverag{
	position: absolute;
	font-size: 13px;
	left: 620px;
	top: 3px
}
#schoolLevel .borders{
	box-shadow: 1px 1px 14px rgba(0,0,0,.15);
}
#schoolLevel .el-table .tableBackground{
	background-color: #f5fcff;text-align: center;
}
#schoolLevel .el-table .tableCenter{
	text-align: center;
}
#schoolLevel .alltest{
	cursor: pointer;
}
#schoolLevel .formatRow{
	font-weight: normal;
	text-align:center !important;
	color: white;
	background-color: #70CDF3
}
#schoolLevel .el-input__inner{
	border: 0px;
	text-align: center;
}
#schoolLevel .el-select .el-input .el-input__icon{
	color: #70CDF3
}
#schoolLevel .el-carousel__arrow{
	background-color: white;
	color: #70CDF3
}
#schoolLevel .el-carousel__arrow:hover{
	background-color: white
}
#schoolLevel .showselect{
	list-style: none;position: absolute;margin: 0;padding:0;
	border: 1px solid #f2f2f2;
	left: 400px;
	z-index: 999;
	background-color: white;
	width: 300px;
	text-align: center;
	max-height: 125px;
	overflow: auto;
}
#schoolLevel .showselect li{
	margin: 0;padding:0;
	border-bottom: 1px solid #f2f2f2;
	padding:10px 20px;
	cursor: pointer;
}
#schoolLevel .header p{
	font-size: 20px;
	color: #707070;
	text-align: center;
	letter-spacing: 0;
	margin-top: 35px
}
#schoolLevel .header-title-foot{
	width: 100px;
	height: 2px;
	background: #44A9FF;
	margin: auto;
	margin-top: 5px;
	margin-bottom: 35px
}
#rankedchart{
	width: 100%;
	height: 530px
}
#averageChart{
	width: 100%;
	height: 530px
}
#contrastiveChart{
	width: 100%;
	height: 530px
}
#topComparedChart{
	width: 100%;
	height: 530px
}
#achievementChart{
	width: 100%;
	height: 530px
}
#schoolLevel .el-table .cell{
	padding:5px;
}

</style>