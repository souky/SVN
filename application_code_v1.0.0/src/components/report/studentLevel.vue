<template>
	<div id="studentlevel">
		<ul id="navInside" v-show="displayAll.anotherpersonalAchievement">
			<li v-for="item in liList" :class="activeList == item.id? 'active': ''" :key="item.id" @click="testtest(item.id)">{{item.name}}</li>
		</ul>
		<ul id="navInside1" v-show="displayAll.personalAchievement">
			<li v-for="item in liList1" :class="activeList1 == item.id? 'active': ''" :key="item.id" @click="testtest1(item.id)">{{item.name}}</li>
		</ul>
		<el-carousel :interval="5000" indicator-position="none" ref="carousel" @change="testChange($event)" arrow="always" :autoplay="false">
			<el-carousel-item v-for="item in testList" :key="item.id" :name=item.examName>
				<p class="alltest" @click="selectShow">{{item.examName}}</p>
			</el-carousel-item>
		</el-carousel>
		<el-collapse-transition>
			<ul v-if='showselect' class="showselect">
				<li v-for="item in testList" :key="item.id" @click="changetest(item.id,item.examName)">{{item.examName}}</li>
			</ul>
		</el-collapse-transition>
		<div id="rainbow" class="header-banner">
			<el-carousel height="100px" indicator-position="none" arrow="always" :autoplay="false">
				<el-carousel-item v-for="item in subjects" :key="item.id">
					<div class="header-banner-bit hand" v-for="(child,index) in item.childs">
						<div class="header-banner-click" ref="fristBit" :style="'background:'+child.subjectColor" @click="rainbow(index,child.id,child.subjectName)">
							<p>{{child.subjectName}}</p>
						</div>
					</div>
				</el-carousel-item>
			</el-carousel>
		</div>
		<div class="achievementSelectBox" v-show="displaystulList">
			<el-select v-model="changeSchool" class="myselect" @change="changestuName" :placeholder="stulList[0].studentName">
				<el-option v-for="item in stulList" :key="item.studentId" :label="item.studentName" :value="item.studentId"></el-option>
			</el-select>
		</div>
		<div id="personalAchievement" class="louceng1" v-show="displayAll.personalAchievement">
			<div class="header">
				<p>个人成绩报告单</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<el-table class="borders" :data="tableData" stripe style="width: 100%" header-row-class-name="table-header">
					<el-table-column align="center" prop="subject" label="科目"></el-table-column>
					<el-table-column align="center" prop="score" label="分数"></el-table-column>
					<el-table-column align="center" prop="scoreLevel" label="分数等级"></el-table-column>
					<el-table-column align="center" prop="schoolAvgScore" label="平均分(校级)"></el-table-column>
					<el-table-column align="center" prop="classTopScore" label="最高分(班级)"></el-table-column>
					<el-table-column align="center" prop="schoolTopScore" label="最高分(校级)"></el-table-column>
					<el-table-column align="center" prop="regionTopScore" label="最高分(地区级)"></el-table-column>
				</el-table>
			</div>
		</div>
		<div id="anotherpersonalAchievement" class="louceng" v-show="displayAll.anotherpersonalAchievement">
			<div class="header">
				<p>个人成绩报告单</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<el-table class="borders" :data="anothertableData" stripe style="width: 100%" header-row-class-name="table-header">
					<el-table-column align="center" prop="score" label="原始分数"></el-table-column>
					<el-table-column align="center" prop="schoolAvgScore" label="平均分(校级)"></el-table-column>
					<el-table-column align="center" prop="classTopScore" label="最高分(班级)"></el-table-column>
					<el-table-column align="center" prop="schoolTopScore" label="最高分(校级)"></el-table-column>
					<el-table-column align="center" prop="regionTopScore" label="最高分(地区级)"></el-table-column>
				</el-table>
			</div>
		</div>
		<div id="beyondRate" v-show="displayAll.beyondRate">
			<div class="header">
				<p>超越率</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<div class="body-icon">
					<div class="body-icon-bit">
						<div class="body-icon-color color-blue"></div>
						<p>地区</p>
					</div>
					<div class="body-icon-bit">
						<div class="body-icon-color color-green"></div>
						<p>校级</p>
					</div>
					<div class="body-icon-bit">
						<div class="body-icon-color color-red"></div>
						<p>班级</p>
					</div>
				</div>
				<div id="beyondRate1"></div>
			</div>
		</div>
		<div id="growthTrend" class="louceng louceng1" v-show="displayAll.growthTrend">
			<div class="header ">
				<p>成长趋势</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<div class="choose-area">
					<div class="choose-area-left" @click="chooseArea($event,'class')">
						<p>本班</p>
					</div>
					<div class="choose-area-midle" @click="chooseArea($event,'school')">
						<p>本校</p>
					</div>
					<div class="choose-area-right" @click="chooseArea($event,'area')">
						<p>本区县</p>
					</div>
				</div>
				<div id="growthTrend1" class="cl"></div>
			</div>
		</div>
		<div id="subjectsDiagnosis" class="louceng1" v-show="displayAll.subjectsDiagnosis">
			<div class="header">
				<p>个人学科均衡水平</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<div class="choose-area">
					<div class="choose-area-left" @click="chooseSubject($event,'class')">
						<p>本班</p>
					</div>
					<div class="choose-area-midle" @click="chooseSubject($event,'school')">
						<p>本校</p>
					</div>
					<div class="choose-area-right" @click="chooseSubject($event,'area')">
						<p>本区县</p>
					</div>
				</div>
				<div class="subjectsDiagnosis1-title"><p>学科均衡性</p></div>
				<div class="subjectsDiagnosis2-title"><p>个人与平均水平对比</p></div>
				<div id="subjectsDiagnosis1"></div>
				<div id="subjectsDiagnosis2"></div>
				<div class="cl"></div>
				<div class="subjectsDiagnosisText">
					<p>{{optionSubjectsDiagnosisWord}}</p>
					<p>{{optionSubjectsDiagnosisRightWord}}</p>
				</div>
			</div>
		</div>
		<div id="myGoal" v-show="displayAll.myGoal">
			<div class="header">
				<p>我的目标：下次考试可以增加？分</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<div class="body-title-item">
					<div class="body-title-itemTitle">
						<p>简单题</p>
						<p>中等题</p>
						<p>难题</p>
					</div>
					<div class="cl"></div>
					<div class="body-title-itemTxt">
						<p>{{classGold.easyNum}}</p>
						<p>{{classGold.secondaryNum}}</p>
						<p>{{classGold.difficultNum}}</p>
					</div>
				</div>
				<div id="myGoal1"></div>
				<div class="subjectsDiagnosisText ml20 mb20">
					<p>{{optionMyGoalWord1}}</p>
					<p>{{optionMyGoalWord2}}</p>
				</div>
			</div>
		</div>
		<div id="testAnalysis" class="louceng" v-show="displayAll.testAnalysis">
			<div class="header">
				<p>试题分析</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<div class="body-Vtitle">
					<p>得分:</p>
					<p>{{testAnalysisTableAS.number1}}分</p>
					<p>满分:</p>
					<p>{{testAnalysisTableAS.number2}}题</p>
					<p>零分:</p>
					<p>{{testAnalysisTableAS.number3}}题</p>
					<p>非满分:</p>
					<p>{{testAnalysisTableAS.number4}}题</p>
				</div>
				<div class="cl"></div>
				<div class="testAnalysis-table">
					<el-table class="borders" :data="testAnalysisTable" stripe style="width: 100%" header-row-class-name="table-header">
						<el-table-column align="center" prop="qid" label="题号"></el-table-column>
						<el-table-column align="center" prop="topic" label="题型"></el-table-column>
						<el-table-column align="center" prop="fractionalValue" label="分值"></el-table-column>
						<el-table-column align="center" prop="studentScore" label="得分">
							<template slot-scope="scope">
								<span v-if="scope.row.studentScore == 0" v-bind:class="{activeS: (scope.row.studentScore == 0)}">{{ scope.row.studentScore }}</span>
								<span v-else v-bind:class="{active: (scope.row.rightOrNot == 1)}">{{ scope.row.studentScore }}</span>
							</template>
						</el-table-column>
						<el-table-column align="center" :formatter="dateFormat" prop="divideClass" label="班级均分"></el-table-column>
						<el-table-column align="center" :formatter="dateFormat1" prop="divideSchool" label="校级均分"></el-table-column>
						<el-table-column align="center" :formatter="dateFormat2" prop="divideAera" label="区县级均分"></el-table-column>
						<el-table-column align="center" prop="knowPoint" :show-overflow-tooltip="true" label="考察知识点"></el-table-column>
						<el-table-column align="center" prop="ablity" :show-overflow-tooltip="true" label="考察能力"></el-table-column>
						<el-table-column align="center" prop="difficulty" label="难度"></el-table-column>
						<el-table-column align="center" prop="differentiation" label="区分度"></el-table-column>
					</el-table>
				</div>
			</div>
		</div>
		<div id="scoreQuestion" v-show="displayAll.optionScoreQuestion">
			<div class="header">
				<p>各小题得分率柱状图</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<div id="scoreQuestion1"></div>
			</div>
		</div>
		<div id="twoDimensionalAnalysis" v-show="displayAll.twoDimensionalAnalysis">
			<div class="header">
				<p>得分率 -- 难度二维分析</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<div class="body-icon">
					<div class="body-icon-bit">
						<div class="body-icon-color color-red" @click="twoDimensionalAnalysisChoose($event,'class')"></div>
						<p>班级</p>
					</div>
					<div class="body-icon-bit">
						<div class="body-icon-color color-push" @click="twoDimensionalAnalysisChoose($event,'school')"></div>
						<p>校级</p>
					</div>
					<div class="body-icon-bit">
						<div class="body-icon-color color-push" @click="twoDimensionalAnalysisChoose($event,'area')"></div>
						<p>全区县</p>
					</div>
				</div>
				<div id="twoDimensionalAnalysis1"></div>
				<div class="twoDimensionalAnalysis-foot">
					<div class="difficultyLevel easy">
						<p>难</p>
					</div>
					<div class="difficultyLevel midde">
						<p>中等</p>
					</div>
					<div class="difficultyLevel difficult">
						<p>容易</p>
					</div>
				</div>
				<div class="foot-word">
					<p>{{optionTwoDimensionalAnalysisWord}}</p>
				</div>
			</div>
		</div>
		<div id="knowledge" class="louceng" v-show="displayAll.knowledge">
			<div class="header">
				<p>知识点分析</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<div id="knowledge1"></div>
				<div id="knowledge-table">
					<el-table id="knowledge-table-header" :data="falsetableData3" header-row-class-name="table-header" style="width: 100%">
						<el-table-column align="center" prop="date" label="知识点模块"></el-table-column>
						<el-table-column align="center" prop="date" label="知识点"></el-table-column>
						<el-table-column align="center" label="得分率%">
							<el-table-column align="center" prop="name" label="我的"></el-table-column>
							<el-table-column align="center" prop="name" label="班级"></el-table-column>
							<el-table-column align="center" prop="name" label="校级"></el-table-column>
							<el-table-column align="center" prop="name" label="区级"></el-table-column>
							<el-table-column align="center" prop="name" label="差值(相对于校级)"></el-table-column>
						</el-table-column>
						<el-table-column align="center" prop="date" label="对应题目"></el-table-column>
					</el-table>
					<div v-for="truetableData3 in truetableDatas">
						<el-table class="knowledge-table-true" :span-method="objectSpanMethod" :data="truetableData3.knowDetail" style="width: 100%">
							<el-table-column align="center" prop="knowledgemodule" label="知识点模块"></el-table-column>
							<el-table-column align="center" :show-overflow-tooltip="true" prop="knowledge" label="知识点"></el-table-column>
							<el-table-column align="center" label="得分率(单位：%)">
								<el-table-column align="center" prop="divideStudent" label="我的"></el-table-column>
								<el-table-column align="center" prop="divideClass" label="班级"></el-table-column>
								<el-table-column align="center" prop="divideSchool" label="校级"></el-table-column>
								<el-table-column align="center" prop="divideSchool" label="区级"></el-table-column>
								<el-table-column align="center" prop="differenceOfDivide" label="差值(相对于校级)"></el-table-column>
							</el-table-column>
							<el-table-column align="center" prop="qid" label="对应题目"></el-table-column>
						</el-table>
						<div class="knowledge-table-trueTotle">
							<div class="knowledge-table-trueTotleBit"><p>总分:{{truetableData3.totle}}分</p></div>
							<div class="knowledge-table-trueTotleBit"><p>得分:{{truetableData3.score}}分</p></div>
							<div class="knowledge-table-trueTotleBit"><p>得分率:{{truetableData3.divideStudent}}</p></div>
							<div class="knowledge-table-trueTotleBit"><p>得分率:{{truetableData3.divideClass}}</p></div>
							<div class="knowledge-table-trueTotleBit"><p>得分率:{{truetableData3.divideSchool}}</p></div>
							<div class="knowledge-table-trueTotleBit"><p>得分率:{{truetableData3.divideAera}}</p></div>
						</div>
					</div>
				</div>
				<div class="knowledge-foot">
					<p>{{optionknowledgeWord}}</p>
				</div>
			</div>
		</div>
		<div id="abilityAnalyze" v-show="displayAll.abilityAnalyze">
			<div class="header">
				<p>能力分析</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body">
				<div id="abilityAnalyze1"></div>
				<div id="abilityAnalyze2">
					<el-table class="borders" :data="scoreName" style="width: 100%">
						<el-table-column align="center" prop="ablityName" label="能力"></el-table-column>
						<el-table-column align="center" label="得分率(单位：%)">
							<el-table-column align="center" prop="divideStudent" label="我的"></el-table-column>
							<el-table-column align="center" prop="divideClass" label="班级"></el-table-column>
							<el-table-column align="center" prop="divideSchool" label="全校"></el-table-column>
							<el-table-column align="center" prop="divideAera" label="全区县"></el-table-column>
						</el-table-column>
						<el-table-column align="center" prop="numbers" label="对应题目"></el-table-column>
					</el-table>
				</div>
				<div class="abilityAnalyze-foot">
					<p>{{optionabilityAnalyzeWord}}</p>
				</div>
			</div>
		</div>
	</div>
</template>
<script>
export default {

	data(){
		return{
			basicData:{
				subject:'总分',
				student:'',
				id:'',
				topNum:50,
			},
			activeList:0,
			activeList1:0,
			liList:[{
				name:'个人成绩报告单',
				id:0
			},{
				name:'成长趋势',
				id:1
			},{
				name:'试题分析',
				id:2
			},{
				name:'知识点分析',
				id:3
			}],
			liList1:[{
				name:'个人成绩报告单',
				id:0
			},{
				name:'成长趋势',
				id:1
			},{
				name:'个人学科均衡水平',
				id:2
			}],
			displaystulList:true,
			stulList:[{
				id:1,
				classroomName:'demo'
			}],
			showselect:false,
			displayAll:{
				personalAchievement:true,
				beyondRate:true,
				growthTrend:true,
				subjectsDiagnosis:true,
				myGoal:false,
				anotherpersonalAchievement:false,
				testAnalysis:false,
				optionScoreQuestion:false,
				twoDimensionalAnalysis:false,
				knowledge:false,
				abilityAnalyze:false,
			},
			scoreName:[{
				name:"空间想象能力",
				scoreClass:45.3,
				scoreSchool:53.4,
				scoreArea:34243,
				topicList:"123、231、342",
			}],
			changeSchool:'',
			falsetableData3:[],
			truetableDatas:[],
			classGold:{
				easyNum:0,
				secondaryNum:0,
				difficultNum:0,
			},
			testList:[],
			subjects:[],
			items:[{
				id:1,
				name:'总分',
				color:'#F37070',
			}],
			tableData:[],
			anothertableData:[],
			testAnalysisTableAS:{
				number1:0,
				number2:0,
				number3:0,
				number4:0,
			},
			testAnalysisTable:[],
			optionBeyondRate:{
				tooltip : {
					trigger: 'axis'
				},
				xAxis : [
				{
					type : 'category',
					data : ['地区超越率','校级超越率','班级超越率']
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
					
					splitLine: {lineStyle:{type:'dashed'}}
					
				}
				],
				series : [
				{
					barWidth: '30%',
					type:'bar',
					label:{normal:{show:true,position:'top'},},
					data:[
					{
						value:82.26,
						name:'地区超越率',
						itemStyle:{
							normal:{color:'#11abff'},
						}
					}, 
					{
						value:83.40,
						name:'校级超越率',
						itemStyle:{
							normal:{color:'#48cda6'},
						}
					},
					{
						value:92,
						name:'班级超越率',
						itemStyle:{
							normal:{color:'#fd87ab'},
						}
					},
					]
				}
				]
			},
			optionGrowthTrend:{
				tooltip: {
					trigger: 'axis'
				},
				legend: {
					data:['本次考试']
				},
				grid: {
					left: '3%',
					right: '4%',
					bottom: '3%',
					containLabel: true
				},
				xAxis: {
					type: 'category',
					data: ['2015-09-22','2015-10-22','2015-11-22','2016-01-22']
				},
				yAxis: {
					type: 'value',
					show: true,
					name:'标准分'
				},
				series: [
				{
					name:'本次考试',
					type:'line',
					stack: '标准分',
					itemStyle:{
						normal:{color:'#46C4F8 '},
					},
					data:[34.9, 44.8, 40.84, 48.3]
				},
				]
			},
			optionSubjectsDiagnosisWord:"",
			optionSubjectsDiagnosis:{
				color: ['#70CDF3'],
				tooltip : {
					trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    legend: {
				    	data:['标准分']
				    },
				    grid: {
				    	left: '3%',
				    	right: '8%',
				    	bottom: '3%',
				    	containLabel: true
				    },
				    xAxis : [
				    {
				    	type : 'category',
				    	data : ['总分', '语文', '数学', '英语', '综合'],
				    	axisTick: {
				    		alignWithLabel: true
				    	}
				    }
				    ],
				    yAxis : [
				    {
				    	type : 'value'
				    }
				    ],
				    series : [
				    {
				    	name:'标准分',
				    	type:'bar',
				    	barWidth: '40%',
				    	data:[70, 75, 65, 67, 42],
				    	markLine:{
				    		lineStyle: {
				    			normal: {
				    				type: 'solid'
				    			}
				    		},
				    		data : [
				    		{ 
				    			yAxis: 70 ,
				    			itemStyle:{
				    				normal:{color:'#FFD244'},
				    			}
				    		}
				    		]
				    		
				    	}
				    },
				    ]
				},
				optionSubjectsDiagnosisRightWord:"",
				optionSubjectsDiagnosisRight:{
					tooltip: {},
					legend: {
						x: 'center',
						data:['个人-（单位：%）','平均水平-（单位：%）']
					},
					grid: {
						left: '4%',
						right: '4%',
						bottom: '3%',
						top:'3%',
						containLabel: true
					},
					radar: [
					{
						indicator: [
						{text: '总分', max: 100},
						{text: '语文', max: 100},
						{text: '数学', max: 100},
						{text: '英语', max: 100},
						{text: '综合', max: 100}
						],
						radius: 190,
						center: ['50%','55%'],
					}
					],
					series: [
					{
						type: 'radar',
						data: [
						{
							name: '个人-（单位：%）',
							value: [23,43,54,65,23],
							itemStyle: {normal: {color: '#FFD244',areaStyle: {color: 'rgba(255,210,68,0.3)'}}},
						},
						{
							name:'平均水平-（单位：%）',
							value:[34,54,56,76,87],
							itemStyle: {normal: {color: '#70CDF3',areaStyle: {color: 'rgba(112,205,243,0.3)'}}},
						}
						]
					}
					]
				},
				optionMyGoalWord1:"",
				optionMyGoalWord2:"",
				optionMyGoal:{
					tooltip : {
						trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    legend: {
				    	data: ['得分率', '失分率']
				    },
				    grid: {
				    	left: '3%',
				    	right: '4%',
				    	bottom: '3%',
				    	containLabel: true
				    },
				    xAxis:  {
				    	type: 'category',
				    	data: ['简单题', '中等题','难题']
				    	
				    },
				    yAxis: {
				    	type: 'value',
				    	show: true,
				    	name:'百分比/%'
				    },
				    series: [
				    {
				    	barWidth: '30%',
				    	name: '失分率',
				    	type: 'bar',
				    	stack: '总量',
				    	label: {
				    		normal: {
				    			show: true,
				    		}
				    	},
				    	itemStyle:{
				    		normal:{color:'#FF8585 '},
				    	},
				    	data: [120, 132, 101]
				    },{
				    	barWidth: '30%',
				    	name: '得分率',
				    	type: 'bar',
				    	stack: '总量',
				    	label: {
				    		normal: {
				    			show: true,
				    		}
				    	},
				    	itemStyle:{
				    		normal:{color:'#46C4F8 '},
				    	},
				    	data: [320, 302, 301]
				    },
				    ]
				},
				optionScoreQuestion:{
					tooltip: {},
					legend: {
						data:['个人','班级','全校','全区县']
					},
					xAxis: [
					{
						type: 'category',
						data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','20'],
						axisPointer: {
							type: 'shadow'
						}
					}
					],
					yAxis: [
					{
						type: 'value',
						show: true,
						min: 0,
						max: 100,
						interval: 20,
						name:'得分率/%',
					},
					],
					dataZoom: [
					{
						show: true,
						start: 0,
						end: 60
					},
					{
						type: 'inside',
						start: 0,
						end: 100
					},
					],
					series: [
					{
						name:'个人',
						type:'bar',
						label:{normal:{show:true,position:'top'},},
						itemStyle:{
							normal:{color:'#46C4F8'},
						},
						data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 43.6, 56.2, 32.6, 20.0, 6.4, 3.3,2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 35.6]
					},
					{
						name:'班级',
						type:'line',
						label:{normal:{show:true,position:'top'},},
						itemStyle:{
							normal:{color:'#FFD244'},
						},
						data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 32.6, 56.2, 48.7, 18.8, 6.0, 2.3,2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 35.6]
					},
					{
						name:'全校',
						type:'line',
						label:{normal:{show:true,position:'top'},},
						itemStyle:{
							normal:{color:'#919191'},
						},
						data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 32.6, 56.2, 48.7, 18.8, 6.0, 2.3,2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 35.6]
					},
					{
						name:'全区县',
						type:'line',
						label:{normal:{show:true,position:'top'},},
						itemStyle:{
							normal:{color:'#919191'},
						},
						data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2,2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 35.6]
					}
					]
				},
				optionTwoDimensionalAnalysisWord:"",
				optionTwoDimensionalAnalysis:{
					tooltip : {
						padding: 10,
						backgroundColor: '#222',
						borderColor: '#777',
						borderWidth: 1,
						formatter:"",
					},
					xAxis : [
					{
						type : 'value',
						scale:true,
						min: 0.1,
						max: 1,
						interval: 0.1,
					}
					],
					yAxis : [
					{
						type : 'value',
						name:'得分率差距',
						show: true,
						nameLocation:'middle',
						nameGap:50,
						min: -100,
						max: 100,
						interval: 20,
						axisLabel: {  
							show: true,  
							interval: 'auto',  
							formatter: '{value} %'  
						},
					}
					],
					series : [
					{
						name:'sin',
						type:'scatter',
						symbolSize :[25,25],
						large: true,
						label:{normal:{show:true}},
						markArea: {
							silent: true,
							data: [[{
								xAxis: '0.1',
								yAxis: '0',
								itemStyle:{
									normal:{color:'rgba(112,205,243,0.5)'},
								}
							}, {
								xAxis: '0.4',
								yAxis: '100',
							}],[{
								xAxis: '0.4',
								yAxis: '0',
								itemStyle:{
									normal:{color:'rgba(145,218,249,0.5)'},
								}
							}, {
								xAxis: '0.7',
								yAxis: '100',
							}],[{
								xAxis: '0.7',
								yAxis: '0',
								itemStyle:{
									normal:{color:'rgba(187,230,248,0.5)'},
								}
							}, {
								xAxis: '1.0',
								yAxis: '100',
							}]]
						},
						itemStyle:{
							normal:{color:'#FF8585'},
						},
						data:[[0.2,30,"客观题",5,1],[0.5,30,"客观题",3,2],[0.7,30,"客观题",3,3]]
					},
					{
						name:'cos',
						type:'scatter',
						large: true,
						symbolSize :[25,25],
						label:{normal:{show:true}},
						markArea: {
							silent: true,
							data: [[{
								xAxis: '0.1',
								yAxis: '0',
								itemStyle:{
									normal:{color:'rgba(255,210,68,0.5)'},
								}
							}, {
								xAxis: '0.4',
								yAxis: '-100',
							}],[{
								xAxis: '0.4',
								yAxis: '0',
								itemStyle:{
									normal:{color:'rgba(255,226,133,0.5)'},
								}
							}, {
								xAxis: '0.7',
								yAxis: '-100',
							}],[{
								xAxis: '0.7',
								yAxis: '0',
								itemStyle:{
									normal:{color:'rgba(253,234,175,0.5)'},
								}
							}, {
								xAxis: '1.0',
								yAxis: '-100',
							}]]
						},
						itemStyle:{
							normal:{color:'#A079D9'},
						},
						data:[[0.2,-30,"客观题",5,4],[0.5,-30,"客观题",3,5],[0.7,-30,"客观题",3,6]]
					}
					]
				},
				optionknowledgeWord:"",
				optionknowledge:{
					tooltip: {},
					legend: {
						x: 'center',
						data:['我的','班级','全校','全区县']
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					radar: [
					{
						indicator: [
						{text: '函数', max: 100},
						{text: '三角函数', max: 100},
						{text: '圆锥曲线与方程', max: 100},
						],
						radius: 190,
						center: ['50%','55%'],
					}
					],
					series: [
					{	
						type: 'radar',
						data: [
						{
							name: '我的',
							value: [23,83,54,65,23],
							itemStyle: {normal: {color: '#DE20CE',areaStyle: {color: 'rgba(222,32,206,0.3)'}}},
						},
						{
							name: '班级',
							value: [23,43,54,65,23],
							itemStyle: {normal: {color: '#FFD244',areaStyle: {color: 'rgba(255,210,68,0.3)'}}},
						},
						{
							name:'全校',
							value:[34,54,56,76,87],
							itemStyle: {normal: {color: '#70CDF3',areaStyle: {color: 'rgba(112,205,243,0.3)'}}},
						},{
							name:'全区县',
							value:[34,34,56,76,87],
							itemStyle: {normal: {color: '#70F390',areaStyle: {color: 'rgba(112,243,144,0.3)'}}},
						}
						]
					}
					]
				},
				optionabilityAnalyzeWord:"",
				optionabilityAnalyze:{
					tooltip: {},
					legend: {
						x: 'center',
						data:['我的','班级','全校','全区县']
					},
					radar: [
					{
						indicator: [
						{text: '空间想象能力', max: 100},
						{text: '抽象概括能力', max: 100},
						{text: '推理论证能力', max: 100},
						{text: '运算求解能力', max: 100},
						{text: '数据处理能力', max: 100},
						{text: '综合应用能力', max: 100},
						],
						radius: 200,
						center: ['50%','52%'],
					}
					],
					series: [
					{
						type: 'radar',
						data: [
						{
							name: '我的',
							value: [23,83,54,65,23,45,65],
							itemStyle: {normal: {color: '#DE20CE',areaStyle: {color: 'rgba(222,32,206,0.3)'}}},
						},
						{
							name: '班级',
							value: [23,43,54,65,23,43,78],
							itemStyle: {normal: {color: '#FFD244',areaStyle: {color: 'rgba(255,210,68,0.3)'}}},
						},
						{
							name:'全校',
							value:[34,54,56,76,87,53,43],
							itemStyle: {normal: {color: '#70CDF3',areaStyle: {color: 'rgba(112,205,243,0.3)'}}},
						},{
							name:'全区县',
							value:[34,34,56,76,87,42,57],
							itemStyle: {normal: {color: '#70F390',areaStyle: {color: 'rgba(112,243,144,0.3)'}}},
						}
						]
					}
					]
				},

			}
		},
		created:function(){
			this.data();
			var childNum=Math.ceil(this.items.length/11);
			var childs=[];
			for(var l=0;l<childNum;l++){
				var id=l+1;
				var e=11*(l+1);
				var s=e-11;
				childs[l] = []
				childs[l]["childs"]=this.items.slice(s,e);
				childs[l]["id"] = id;
			}
			this.subjects=childs;

			this.optionTwoDimensionalAnalysis.tooltip.formatter=function (obj) {
				var value = obj.value;
				return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">'
				+ '</div>' + "题型" + '：' + value[2] + '<br>'
				+ "题号" + '：' + value[4] + '<br>'
				+ "满分值" + '：' + value[3] + '<br>'
				+ "得分率差距" + '：' + value[1]+"%" + '<br>'
			};

		},
		mounted:function(){
			this.$refs.fristBit[0].className+=" on";
			var olouceng = document.getElementsByClassName("louceng");
			var oNav = document.getElementById("navInside").getElementsByTagName("li");
			var olouceng1 = document.getElementsByClassName("louceng1");
			var oNav1 = document.getElementById("navInside1").getElementsByTagName("li");
			window.addEventListener('scroll',()=>{
				var oheight = document.documentElement.clientHeight || document.body.clientHeight;
				var otop = document.documentElement.scrollTop || document.body.scrollTop;
				if(otop>=250){
					for(var i=0;i<olouceng.length;i++){
						if(oheight+otop-olouceng[i].offsetTop>oheight/1.5){
							for(var j=0;j<oNav.length;j++){
								oNav[j].className = '';
							}
							oNav[i].className = 'active';
						}
					}
					for(var i=0;i<olouceng1.length;i++){
						if(oheight+otop-olouceng1[i].offsetTop>oheight/1.5){
							for(var j=0;j<oNav1.length;j++){
								oNav1[j].className = '';
							}
							oNav1[i].className = 'active'
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
		},
		methods:{
			data:function(){
				this.postHttp(this,{},'exam/getExamListForTab',function(obj,res){
					if(res.code == '10000'){
						obj.testList=res.result.exams;
					}else{
						obj.notify_jr(obj,'错误提示',res.message,'error');
					}
				});
			},
			dateFormat:function(row,column){
				var data=this.accDiv(parseFloat(row.divideClass),parseFloat(100));
				var data1=parseInt(this.accMul(row.fractionalValue,data)*100);
				var data2=parseFloat(data1)/100;
				return data2;
			},
			dateFormat1:function(row,column){
				var data=this.accDiv(parseFloat(row.divideSchool),parseFloat(100));
				var data1=parseInt(this.accMul(row.fractionalValue,data)*100);
				var data2=parseFloat(data1)/100;
				return data2;
			},
			dateFormat2:function(row,column){
				var data=this.accDiv(parseFloat(row.divideAera),parseFloat(100));
				var data1=parseInt(this.accMul(row.fractionalValue,data)*100);
				var data2=parseFloat(data1)/100;
				return data2;
			},
			testChange(e){
				var name=this.testList[e].id;
				this.basicData.id=name;
				var totle={createDate:1512647749000,createUser:"1",id:"0",isDelete:0,orgId:"",remark:"",schoolId:"",subjectCode:1,subjectColor:"#F37070",subjectName:"总分",updateDate:1513586055000};
				this.postHttp(this,{},'exam/getExamListForTab',function(obj,res){
					if(res.code == '10000'){
						obj.testList=res.result.exams;
						obj.items=res.result[name].subject;
						obj.items.unshift(totle);
						var childNum=Math.ceil(obj.items.length/11);
						var childs=[];
						for(var l=0;l<childNum;l++){
							var id=l+1;
							var e=11*(l+1);
							var s=e-11;
							childs[l] = []
							childs[l]["childs"]=obj.items.slice(s,e);
							childs[l]["id"] = id;
						}
						obj.subjects=childs;
						obj.getStudentOrList();

					}else{
						obj.notify_jr(obj,'错误提示',res.message,'error');
					}
				});

			}, 
			getStudentOrList:function(){
				this.postHttp(this,{examId:this.basicData.id},'score/getStudentOrList',function(obj,res){
					if(res.code == '10000'){
						if(res.result.schoolScores){
							obj.changeSchool='';
							obj.displaystulList=true;
							obj.stulList=res.result.schoolScores;
							obj.basicData.student=res.result.schoolScores[0].studentId;
							obj.rainbow(0,0,"总分");
						}else{
							obj.displaystulList=false;
							obj.basicData.student=res.result.studentId;
							obj.rainbow(0,0,"总分");
						}
					}else{
						obj.notify_jr(obj,'错误提示',res.message,'error');
					}
				})
			},
			changetest:function(e,ename){
				this.showselect = !this.showselect
				this.$refs.carousel.setActiveItem(ename);
			},
			selectShow:function(){
				this.showselect = !this.showselect
			},
			testtest:function(e){
				var olouceng = document.getElementsByClassName("louceng");
				var oNav = document.getElementById("navInside").getElementsByTagName("li");
				this.activeList = e;
				window.scrollTo(0 ,olouceng[e].offsetTop-100);
			},
			testtest1:function(e){
				var olouceng = document.getElementsByClassName("louceng1");
				var oNav = document.getElementById("navInside1").getElementsByTagName("li");
				this.activeList1 = e;
				window.scrollTo(0 ,olouceng[e].offsetTop-100);
			},
			rainbow:function(index,num,name){
				for(var i=0;i<document.getElementsByClassName("header-banner-click").length;i++){
					document.getElementById("rainbow").getElementsByClassName("header-banner-click")[i].className="header-banner-click";
				}
				document.getElementById("rainbow").getElementsByClassName("is-active")[0].getElementsByClassName("header-banner-click")[index].className+=" on";
				if(num==0){
					var oNav = document.getElementById("navInside1").getElementsByTagName("li");
					for(var a = 0;a<oNav.length;a++){
						oNav[a].className = '';
					}
					oNav[0].className = 'active';
					this.displayAll.personalAchievement=true;
					this.displayAll.beyondRate=true;
					this.displayAll.growthTrend=true;
					this.displayAll.subjectsDiagnosis=true;
					this.displayAll.myGoal=false;
					this.displayAll.anotherpersonalAchievement=false;
					this.displayAll.testAnalysis=false;
					this.displayAll.optionScoreQuestion=false;
					this.displayAll.twoDimensionalAnalysis=false;
					this.displayAll.knowledge=false;
					this.displayAll.abilityAnalyze=false;
					this.activeList = 0;this.activeList1 = 0;
					this.basicData.subject=name;
					this.geReportCards();
					this.getPercentageRank();
					this.getGrowthTrends();
				}else{
					var oNav = document.getElementById("navInside").getElementsByTagName("li");
					for(var a = 0;a<oNav.length;a++){
						oNav[a].className = '';
					}
					oNav[0].className = 'active';
					this.displayAll.personalAchievement=false;
					this.displayAll.beyondRate=true;
					this.displayAll.growthTrend=true;
					this.displayAll.subjectsDiagnosis=false;
					this.displayAll.myGoal=true;
					this.displayAll.anotherpersonalAchievement=true;
					this.displayAll.testAnalysis=true;
					this.displayAll.optionScoreQuestion=true;
					this.displayAll.twoDimensionalAnalysis=true;
					this.displayAll.knowledge=true;
					this.displayAll.abilityAnalyze=true;
					this.activeList = 0;this.activeList1 = 0;
					this.basicData.subject=name;
					this.testAnalysis();
					this.knowAnalysis();
					this.ablityAnalysis();
					this.geReportCards();
					this.getPercentageRank();
					this.getGrowthTrends();
				}
			},
			getPercentageRank:function(){
				this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student},'score/getPercentageRank',function(obj,res){
					if(res.code == '10000'){
						obj.optionBeyondRate.series[0].data[0].value=res.result.schoolPercentageRank;
						obj.optionBeyondRate.series[0].data[1].value=res.result.schoolPercentageRank;
						obj.optionBeyondRate.series[0].data[2].value=res.result.classPercentageRank;
						obj.echarts.init(document.getElementById("beyondRate1")).setOption(obj.optionBeyondRate);
					}else{
						obj.notify_jr(obj,'错误提示',res.message,'error');
					}
				})
			},
			knowAnalysis:function(){
				this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student},'/knowAnalysis',function(obj,res){
					if(res.code == '10000'){
						obj.truetableDatas=res.result.listVO;
						obj.optionknowledge.series[0].data[0].value=[];
						obj.optionknowledge.series[0].data[1].value=[];
						obj.optionknowledge.series[0].data[2].value=[];
						obj.optionknowledge.series[0].data[3].value=[];
						obj.optionknowledge.radar[0].indicator=[];
						if(res.result.listVO){
							if(res.result.listVO.length>=3){
								document.getElementById("knowledge1").style.display="block";
								for(var i=0;i<res.result.listVO.length;i++){
									var arr={
										"text":res.result.listVO[i].knowDetail[0].knowledgemodule,
										"max":100,
									}
									obj.optionknowledge.radar[0].indicator.push(arr);
									obj.optionknowledge.series[0].data[0].value.push(res.result.listVO[i].divideStudent);
									obj.optionknowledge.series[0].data[1].value.push(res.result.listVO[i].divideClass);
									obj.optionknowledge.series[0].data[2].value.push(res.result.listVO[i].divideSchool);
									obj.optionknowledge.series[0].data[3].value.push(res.result.listVO[i].divideSchool);
								}
								obj.echarts.init(document.getElementById("knowledge1")).setOption(obj.optionknowledge);
								obj.optionknowledgeWord=res.result.summaryVO.knowledgeAnalysis;
							}else{
								document.getElementById("knowledge1").style.display="none";
							}
						}
					}else{
						document.getElementById("knowledge1").style.display="none";
						obj.notify_jr(obj,'错误提示',res.message,'error');
					}
				})
			},
			changestuName(value){
				this.basicData.student=value;
				this.rainbow(0,0,"总分");
			},
			getGrowthTrends:function(){
				document.getElementById("growthTrend").getElementsByClassName("choose-area-left")[0].style.background="#46C4F8";
				document.getElementById("growthTrend").getElementsByClassName("choose-area-left")[0].style.color="#fff";
				document.getElementById("growthTrend").getElementsByClassName("choose-area-midle")[0].style.background="#fff";
				document.getElementById("growthTrend").getElementsByClassName("choose-area-midle")[0].style.color="#46C4F8";
				document.getElementById("growthTrend").getElementsByClassName("choose-area-right")[0].style.background="#fff";
				document.getElementById("growthTrend").getElementsByClassName("choose-area-right")[0].style.color="#46C4F8";
				this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student,range:'CLASS'},'score/getGrowthTrends',function(obj,res){
					if(res.code == '10000'){
						obj.optionGrowthTrend.xAxis.data=[];
						for(var i=0;i<res.result.examDates.length;i++){
							var date = new Date();  
							date.setTime(parseFloat(res.result.examDates[i]));  
							var y = date.getFullYear();      
							var m = date.getMonth() + 1;      
							m = m < 10 ? ('0' + m) : m;      
							var d = date.getDate();      
							d = d < 10 ? ('0' + d) : d;          
							var number= y + '-' + m + '-' + d;   
							obj.optionGrowthTrend.xAxis.data.push(number);
						}
						obj.optionGrowthTrend.series[0].data=[];
						obj.optionGrowthTrend.series[0].data=res.result.standardScores;
						obj.echarts.init(document.getElementById("growthTrend1")).setOption(obj.optionGrowthTrend);
					}else{
						obj.notify_jr(obj,'错误提示',res.message,'error');
					}
				})
			},
			ablityAnalysis:function(){
				this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student},'/ablityAnalysis',function(obj,res){
					if(res.code == '10000'){
						obj.scoreName=res.result.listVO;
						obj.optionabilityAnalyze.series[0].data[0].value=[];
						obj.optionabilityAnalyze.series[0].data[1].value=[];
						obj.optionabilityAnalyze.series[0].data[2].value=[];
						obj.optionabilityAnalyze.series[0].data[3].value=[];
						for(var i=0;i<res.result.listVO.length;i++){
							obj.optionabilityAnalyze.radar[0].indicator[i].text=res.result.listVO[i].ablityName;
							obj.optionabilityAnalyze.series[0].data[0].value.push(res.result.listVO[i].divideStudent);
							obj.optionabilityAnalyze.series[0].data[1].value.push(res.result.listVO[i].divideClass);
							obj.optionabilityAnalyze.series[0].data[2].value.push(res.result.listVO[i].divideSchool);
							obj.optionabilityAnalyze.series[0].data[3].value.push(res.result.listVO[i].divideAera);
							obj.optionabilityAnalyze.radar[0].indicator[i].text=res.result.listVO[i].ablityName;
						}
						obj.echarts.init(document.getElementById("abilityAnalyze1")).setOption(obj.optionabilityAnalyze);
						obj.optionabilityAnalyzeWord=res.result.summaryVO.abilityAnalysis;
					}else{
						obj.notify_jr(obj,'错误提示',res.message,'error');
					}
				})
			},
			testAnalysis:function(){
				document.getElementById("twoDimensionalAnalysis").getElementsByClassName("body-icon-color")[0].className="body-icon-color color-red";
				document.getElementById("twoDimensionalAnalysis").getElementsByClassName("body-icon-color")[1].className="body-icon-color color-push";
				document.getElementById("twoDimensionalAnalysis").getElementsByClassName("body-icon-color")[2].className="body-icon-color color-push";
				this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student},'/testAnalysis',function(obj,res){
					if(res.code == '10000'){
						obj.testAnalysisTable=res.result.listVO;
						obj.classGold=res.result.targetVO;
						obj.optionMyGoal.series[0].data=[];
						obj.optionMyGoal.series[1].data=[];
						obj.optionMyGoal.series[1].data.push(parseFloat(res.result.targetVO.divideEasy))
						obj.optionMyGoal.series[1].data.push(parseFloat(res.result.targetVO.divideSecondary))
						obj.optionMyGoal.series[1].data.push(parseFloat(res.result.targetVO.divideDifficult))
						var number1=parseInt((parseFloat(100)-parseFloat(res.result.targetVO.divideEasy))*100)
						var num1=parseFloat(number1)/100;
						var number2=parseInt((parseFloat(100)-parseFloat(res.result.targetVO.divideSecondary))*100)
						var num2=parseFloat(number2)/100;
						var number3=parseInt((parseFloat(100)-parseFloat(res.result.targetVO.divideDifficult))*100)
						var num3=parseFloat(number3)/100;
						if(res.result.targetVO.easyNum==0){
							num1=0;
						}
						if(res.result.targetVO.secondaryNum==0){
							num2=0;
						}
						if(res.result.targetVO.difficultNum==0){
							num3=0;
						}
						obj.optionMyGoal.series[0].data.push(num1)
						obj.optionMyGoal.series[0].data.push(num2)
						obj.optionMyGoal.series[0].data.push(num3)


						obj.optionScoreQuestion.xAxis[0].data=[];
						obj.optionScoreQuestion.series[0].data=[];
						obj.optionScoreQuestion.series[1].data=[];
						obj.optionScoreQuestion.series[2].data=[];
						obj.optionScoreQuestion.series[3].data=[];
						obj.optionTwoDimensionalAnalysis.series[0].data=[];
						obj.optionTwoDimensionalAnalysis.series[1].data=[];
						obj.testAnalysisTableAS.number1=0;
						obj.testAnalysisTableAS.number2=0;
						obj.testAnalysisTableAS.number3=0;
						obj.testAnalysisTableAS.number4=0;
						for(var i=0;i<res.result.listVO.length;i++){
							if(res.result.listVO[i].studentScore==undefined ||res.result.listVO[i].studentScore==""){
								res.result.listVO[i].studentScore=0
							}
							obj.testAnalysisTableAS.number1=obj.testAnalysisTableAS.number1+res.result.listVO[i].studentScore;
							if(res.result.listVO[i].rightOrNot=="1"){
								obj.testAnalysisTableAS.number2=obj.testAnalysisTableAS.number2+1;
							}
							if(res.result.listVO[i].studentScore==0){
								obj.testAnalysisTableAS.number3=obj.testAnalysisTableAS.number3+1;
							}


							obj.optionScoreQuestion.xAxis[0].data.push(res.result.listVO[i].qid);
							obj.optionScoreQuestion.series[0].data.push(parseFloat(res.result.listVO[i].divideStudent));
							obj.optionScoreQuestion.series[1].data.push(parseFloat(res.result.listVO[i].divideClass));
							obj.optionScoreQuestion.series[2].data.push(parseFloat(res.result.listVO[i].divideSchool));
							obj.optionScoreQuestion.series[3].data.push(parseFloat(res.result.listVO[i].divideAera));
							if(parseFloat(res.result.listVO[i].divideStudentToClass)>=0){
								var list=[];
								list.push(res.result.listVO[i].difficulty);
								list.push(parseFloat(res.result.listVO[i].divideStudentToClass));
								list.push(res.result.listVO[i].topic);
								list.push(parseFloat(res.result.listVO[i].fractionalValue));
								list.push(parseFloat(res.result.listVO[i].qid));
								obj.optionTwoDimensionalAnalysis.series[0].data.push(list);
							}else{
								var list1=[];
								list1.push(res.result.listVO[i].difficulty);
								list1.push(parseFloat(res.result.listVO[i].divideStudentToClass));
								list1.push(res.result.listVO[i].topic);
								list1.push(parseFloat(res.result.listVO[i].fractionalValue));
								list1.push(parseFloat(res.result.listVO[i].qid));
								obj.optionTwoDimensionalAnalysis.series[1].data.push(list1);
							}
						}
						obj.testAnalysisTableAS.number4=res.result.listVO.length-obj.testAnalysisTableAS.number2-obj.testAnalysisTableAS.number3;
						obj.echarts.init(document.getElementById("scoreQuestion1")).setOption(obj.optionScoreQuestion);
						obj.echarts.init(document.getElementById("twoDimensionalAnalysis1")).setOption(obj.optionTwoDimensionalAnalysis);
						obj.echarts.init(document.getElementById("myGoal1")).setOption(obj.optionMyGoal);
						obj.optionMyGoalWord1=res.result.summaryVO.nextTimeAddScore;
						obj.optionMyGoalWord2=res.result.summaryVO.promoteForNextTime;
						obj.optionTwoDimensionalAnalysisWord=res.result.summaryVO.difficultyAnalysis;
					}else{
						obj.notify_jr(obj,'错误提示',res.message,'error');
					}
				});
},
geReportCards:function(){
	this.tableData=[];
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-left")[0].style.background="#46C4F8";
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-left")[0].style.color="#fff";
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-midle")[0].style.background="#fff";
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-midle")[0].style.color="#46C4F8";
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-right")[0].style.background="#fff";
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-right")[0].style.color="#46C4F8";
	this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student,tab:"STUDENT_REPORT",range:'CLASS'},'score/geReportCards',function(obj,res){
		if(res.code == '10000'){
			var type=(typeof res.result);
			if(type=="string"){
				obj.tableData=[];
				obj.anothertableData=[];
			}else{
				if(obj.basicData.subject=="总分"){
					obj.tableData=res.result.scoreVOList;
					obj.optionSubjectsDiagnosis.xAxis[0].data=[];
					obj.optionSubjectsDiagnosis.xAxis[0].data=res.result.subjectList;
					obj.optionSubjectsDiagnosis.series[0].data=[];
					obj.optionSubjectsDiagnosis.series[0].data=res.result.standardScoreList;
					obj.optionSubjectsDiagnosis.series[0].markLine.data[0].yAxis=res.result.standardScoreList[0];
					obj.optionSubjectsDiagnosisRight.series[0].data[0].value=[];
					obj.optionSubjectsDiagnosisRight.series[0].data[1].value=[];
					obj.optionSubjectsDiagnosisRight.series[0].data[0].value=res.result.stuScoreList;
					obj.optionSubjectsDiagnosisRight.series[0].data[1].value=res.result.classAvgScoreList;
					obj.optionSubjectsDiagnosisRight.radar[0].indicator=[];
					for(var i=0;i<res.result.subjectList.length;i++){
						var arr={
							"text":res.result.subjectList[i],
							"max":100,
						}
						obj.optionSubjectsDiagnosisRight.radar[0].indicator.push(arr);
					}
					obj.optionSubjectsDiagnosisWord=res.result.summaryVO.stuSubjBalanceST;
					obj.optionSubjectsDiagnosisRightWord=res.result.summaryVO.stuSubjBalancePR;
					obj.echarts.init(document.getElementById("subjectsDiagnosis1")).setOption(obj.optionSubjectsDiagnosis);
					obj.echarts.init(document.getElementById("subjectsDiagnosis2")).setOption(obj.optionSubjectsDiagnosisRight);
				}else{
					if(res.result.scoreVOList){
						var num=0;
						obj.anothertableData=[];
						// for(var i=0;i<res.result.scoreVOList.length;i++){
						// 	if(res.result.scoreVOList[i].subject==obj.basicData.subject){
						// 		num=i;
						// 	}
						// }
						// var arr={
						// 	"score":res.result.scoreVOList[num].score,
						// 	"scoschoolAvgScorere":res.result.scoreVOList[num].schoolAvgScore,
						// 	"classTopScore":res.result.scoreVOList[num].classTopScore,
						// 	"schoolTopScore":res.result.scoreVOList[num].schoolTopScore,
						// 	"regionTopScore":res.result.scoreVOList[num].regionTopScore,
						// }
						obj.anothertableData=res.result.scoreVOList;
					}
				}
			}
		}else{
			obj.notify_jr(obj,'错误提示',res.message,'error');
		}
	})
},
chooseArea:function(e,num){
	document.getElementById("growthTrend").getElementsByClassName("choose-area-left")[0].style.background="#fff";
	document.getElementById("growthTrend").getElementsByClassName("choose-area-left")[0].style.color="#46C4F8";
	document.getElementById("growthTrend").getElementsByClassName("choose-area-midle")[0].style.background="#fff";
	document.getElementById("growthTrend").getElementsByClassName("choose-area-midle")[0].style.color="#46C4F8";
	document.getElementById("growthTrend").getElementsByClassName("choose-area-right")[0].style.background="#fff";
	document.getElementById("growthTrend").getElementsByClassName("choose-area-right")[0].style.color="#46C4F8";
	e.currentTarget.style.background="#46C4F8";
	e.currentTarget.style.color="#fff";
	if(num=="class"){
		this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student,range:'CLASS'},'score/getGrowthTrends',function(obj,res){
			if(res.code == '10000'){
				obj.optionGrowthTrend.xAxis.data=[];
				for(var i=0;i<res.result.examDates.length;i++){
					var date = new Date();  
					date.setTime(parseInt(res.result.examDates[i]));  
					var y = date.getFullYear();      
					var m = date.getMonth() + 1;      
					m = m < 10 ? ('0' + m) : m;      
					var d = date.getDate();      
					d = d < 10 ? ('0' + d) : d;      
					var h = date.getHours();    
					h = h < 10 ? ('0' + h) : h;    
					var minute = date.getMinutes();    
					var second = date.getSeconds();    
					minute = minute < 10 ? ('0' + minute) : minute;      
					second = second < 10 ? ('0' + second) : second;     
					var number= y + '-' + m + '-' + d;   
					obj.optionGrowthTrend.xAxis.data.push(number);
				}
				obj.optionGrowthTrend.series[0].data=[];
				obj.optionGrowthTrend.series[0].data=res.result.standardScores;
				obj.echarts.init(document.getElementById("growthTrend1")).setOption(obj.optionGrowthTrend);
			}else{
				obj.notify_jr(obj,'错误提示',res.message,'error');
			}
		})
	}
	if(num=="school"){
		this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student,range:'SCHOOL'},'score/getGrowthTrends',function(obj,res){
			if(res.code == '10000'){
				obj.optionGrowthTrend.xAxis.data=[];
				for(var i=0;i<res.result.examDates.length;i++){
					var date = new Date();  
					date.setTime(parseInt(res.result.examDates[i]));  
					var y = date.getFullYear();      
					var m = date.getMonth() + 1;      
					m = m < 10 ? ('0' + m) : m;      
					var d = date.getDate();      
					d = d < 10 ? ('0' + d) : d;      
					var h = date.getHours();    
					h = h < 10 ? ('0' + h) : h;    
					var minute = date.getMinutes();    
					var second = date.getSeconds();    
					minute = minute < 10 ? ('0' + minute) : minute;      
					second = second < 10 ? ('0' + second) : second;     
					var number= y + '-' + m + '-' + d;   
					obj.optionGrowthTrend.xAxis.data.push(number);
				}
				obj.optionGrowthTrend.series[0].data=[];
				obj.optionGrowthTrend.series[0].data=res.result.standardScores;
				obj.echarts.init(document.getElementById("growthTrend1")).setOption(obj.optionGrowthTrend);
			}else{
				obj.notify_jr(obj,'错误提示',res.message,'error');
			}
		})
	}
	if(num=="area"){
		this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student,range:'REGION'},'score/getGrowthTrends',function(obj,res){
			if(res.code == '10000'){
				obj.optionGrowthTrend.xAxis.data=[];
				for(var i=0;i<res.result.examDates.length;i++){
					var date = new Date();  
					date.setTime(parseInt(res.result.examDates[i]));  
					var y = date.getFullYear();      
					var m = date.getMonth() + 1;      
					m = m < 10 ? ('0' + m) : m;      
					var d = date.getDate();      
					d = d < 10 ? ('0' + d) : d;      
					var h = date.getHours();    
					h = h < 10 ? ('0' + h) : h;    
					var minute = date.getMinutes();    
					var second = date.getSeconds();    
					minute = minute < 10 ? ('0' + minute) : minute;      
					second = second < 10 ? ('0' + second) : second;     
					var number= y + '-' + m + '-' + d;   
					obj.optionGrowthTrend.xAxis.data.push(number);
				}
				obj.optionGrowthTrend.series[0].data=[];
				obj.optionGrowthTrend.series[0].data=res.result.standardScores;
				obj.echarts.init(document.getElementById("growthTrend1")).setOption(obj.optionGrowthTrend);
			}else{
				obj.notify_jr(obj,'错误提示',res.message,'error');
			}
		})
	}
},
chooseSubject:function(e,num){
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-left")[0].style.background="#fff";
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-left")[0].style.color="#46C4F8";
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-midle")[0].style.background="#fff";
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-midle")[0].style.color="#46C4F8";
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-right")[0].style.background="#fff";
	document.getElementById("subjectsDiagnosis").getElementsByClassName("choose-area-right")[0].style.color="#46C4F8";
	e.currentTarget.style.background="#46C4F8";
	e.currentTarget.style.color="#fff";
	if(num=="class"){
		this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student,tab:"STUDENT_REPORT",range:'CLASS'},'score/geReportCards',function(obj,res){
		if(res.code == '10000'){
			var type=(typeof res.result);
			if(type=="string"){
				obj.tableData=[];
				obj.anothertableData=[];
			}else{
				if(obj.basicData.subject=="总分"){
					obj.tableData=res.result.scoreVOList;
					obj.optionSubjectsDiagnosis.xAxis[0].data=[];
					obj.optionSubjectsDiagnosis.xAxis[0].data=res.result.subjectList;
					obj.optionSubjectsDiagnosis.series[0].data=[];
					obj.optionSubjectsDiagnosis.series[0].data=res.result.standardScoreList;
					obj.optionSubjectsDiagnosis.series[0].markLine.data[0].yAxis=res.result.standardScoreList[0];
					obj.optionSubjectsDiagnosisRight.series[0].data[0].value=[];
					obj.optionSubjectsDiagnosisRight.series[0].data[1].value=[];
					obj.optionSubjectsDiagnosisRight.series[0].data[0].value=res.result.stuScoreList;
					obj.optionSubjectsDiagnosisRight.series[0].data[1].value=res.result.classAvgScoreList;
					obj.optionSubjectsDiagnosisRight.radar[0].indicator=[];
					for(var i=0;i<res.result.subjectList.length;i++){
						var arr={
							"text":res.result.subjectList[i],
							"max":100,
						}
						obj.optionSubjectsDiagnosisRight.radar[0].indicator.push(arr);
					}
					obj.echarts.init(document.getElementById("subjectsDiagnosis1")).setOption(obj.optionSubjectsDiagnosis);
					obj.echarts.init(document.getElementById("subjectsDiagnosis2")).setOption(obj.optionSubjectsDiagnosisRight);
				}else{
					if(res.result.scoreVOList){
						var num=0;
						obj.anothertableData=[];
						// for(var i=0;i<res.result.scoreVOList.length;i++){
						// 	if(res.result.scoreVOList[i].subject==obj.basicData.subject){
						// 		num=i;
						// 	}
						// }
						// var arr={
						// 	"score":res.result.scoreVOList[num].score,
						// 	"scoschoolAvgScorere":res.result.scoreVOList[num].schoolAvgScore,
						// 	"classTopScore":res.result.scoreVOList[num].classTopScore,
						// 	"schoolTopScore":res.result.scoreVOList[num].schoolTopScore,
						// 	"regionTopScore":res.result.scoreVOList[num].regionTopScore,
						// }
						obj.anothertableData=res.result.scoreVOList;
					}
				}
			}
		}else{
			obj.notify_jr(obj,'错误提示',res.message,'error');
		}
	})
	}
	if(num=="school"){
		this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student,tab:"STUDENT_REPORT",range:'SCHOOL'},'score/geReportCards',function(obj,res){
		if(res.code == '10000'){
			var type=(typeof res.result);
			if(type=="string"){
				obj.tableData=[];
				obj.anothertableData=[];
			}else{
				if(obj.basicData.subject=="总分"){
					obj.tableData=res.result.scoreVOList;
					obj.optionSubjectsDiagnosis.xAxis[0].data=[];
					obj.optionSubjectsDiagnosis.xAxis[0].data=res.result.subjectList;
					obj.optionSubjectsDiagnosis.series[0].data=[];
					obj.optionSubjectsDiagnosis.series[0].data=res.result.standardScoreList;
					obj.optionSubjectsDiagnosis.series[0].markLine.data[0].yAxis=res.result.standardScoreList[0];
					obj.optionSubjectsDiagnosisRight.series[0].data[0].value=[];
					obj.optionSubjectsDiagnosisRight.series[0].data[1].value=[];
					obj.optionSubjectsDiagnosisRight.series[0].data[0].value=res.result.stuScoreList;
					obj.optionSubjectsDiagnosisRight.series[0].data[1].value=res.result.classAvgScoreList;
					obj.optionSubjectsDiagnosisRight.radar[0].indicator=[];
					for(var i=0;i<res.result.subjectList.length;i++){
						var arr={
							"text":res.result.subjectList[i],
							"max":100,
						}
						obj.optionSubjectsDiagnosisRight.radar[0].indicator.push(arr);
					}
					obj.echarts.init(document.getElementById("subjectsDiagnosis1")).setOption(obj.optionSubjectsDiagnosis);
					obj.echarts.init(document.getElementById("subjectsDiagnosis2")).setOption(obj.optionSubjectsDiagnosisRight);
				}else{
					if(res.result.scoreVOList){
						var num=0;
						obj.anothertableData=[];
						// for(var i=0;i<res.result.scoreVOList.length;i++){
						// 	if(res.result.scoreVOList[i].subject==obj.basicData.subject){
						// 		num=i;
						// 	}
						// }
						// var arr={
						// 	"score":res.result.scoreVOList[num].score,
						// 	"scoschoolAvgScorere":res.result.scoreVOList[num].schoolAvgScore,
						// 	"classTopScore":res.result.scoreVOList[num].classTopScore,
						// 	"schoolTopScore":res.result.scoreVOList[num].schoolTopScore,
						// 	"regionTopScore":res.result.scoreVOList[num].regionTopScore,
						// }
						obj.anothertableData=res.result.scoreVOList;
					}
				}
			}
		}else{
			obj.notify_jr(obj,'错误提示',res.message,'error');
		}
	})
	}
	if(num=="area"){
		this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student,tab:"STUDENT_REPORT",range:'REGION'},'score/geReportCards',function(obj,res){
		if(res.code == '10000'){
			var type=(typeof res.result);
			if(type=="string"){
				obj.tableData=[];
				obj.anothertableData=[];
			}else{
				if(obj.basicData.subject=="总分"){
					obj.tableData=res.result.scoreVOList;
					obj.optionSubjectsDiagnosis.xAxis[0].data=[];
					obj.optionSubjectsDiagnosis.xAxis[0].data=res.result.subjectList;
					obj.optionSubjectsDiagnosis.series[0].data=[];
					obj.optionSubjectsDiagnosis.series[0].data=res.result.standardScoreList;
					obj.optionSubjectsDiagnosis.series[0].markLine.data[0].yAxis=res.result.standardScoreList[0];
					obj.optionSubjectsDiagnosisRight.series[0].data[0].value=[];
					obj.optionSubjectsDiagnosisRight.series[0].data[1].value=[];
					obj.optionSubjectsDiagnosisRight.series[0].data[0].value=res.result.stuScoreList;
					obj.optionSubjectsDiagnosisRight.series[0].data[1].value=res.result.classAvgScoreList;
					obj.optionSubjectsDiagnosisRight.radar[0].indicator=[];
					for(var i=0;i<res.result.subjectList.length;i++){
						var arr={
							"text":res.result.subjectList[i],
							"max":100,
						}
						obj.optionSubjectsDiagnosisRight.radar[0].indicator.push(arr);
					}
					obj.echarts.init(document.getElementById("subjectsDiagnosis1")).setOption(obj.optionSubjectsDiagnosis);
					obj.echarts.init(document.getElementById("subjectsDiagnosis2")).setOption(obj.optionSubjectsDiagnosisRight);
				}else{
					if(res.result.scoreVOList){
						var num=0;
						obj.anothertableData=[];
						// for(var i=0;i<res.result.scoreVOList.length;i++){
						// 	if(res.result.scoreVOList[i].subject==obj.basicData.subject){
						// 		num=i;
						// 	}
						// }
						// var arr={
						// 	"score":res.result.scoreVOList[num].score,
						// 	"scoschoolAvgScorere":res.result.scoreVOList[num].schoolAvgScore,
						// 	"classTopScore":res.result.scoreVOList[num].classTopScore,
						// 	"schoolTopScore":res.result.scoreVOList[num].schoolTopScore,
						// 	"regionTopScore":res.result.scoreVOList[num].regionTopScore,
						// }
						obj.anothertableData=res.result.scoreVOList;
					}
				}
			}
		}else{
			obj.notify_jr(obj,'错误提示',res.message,'error');
		}
	})
	}
	this.echarts.init(document.getElementById("subjectsDiagnosis1")).setOption(this.optionSubjectsDiagnosis);
	this.echarts.init(document.getElementById("subjectsDiagnosis2")).setOption(this.optionSubjectsDiagnosisRight);
},
twoDimensionalAnalysisChoose:function(e,obj){
	for(var i=0;i<e.currentTarget.parentNode.parentNode.getElementsByClassName("body-icon-bit").length;i++){
		e.currentTarget.parentNode.parentNode.getElementsByClassName("body-icon-bit")[i].getElementsByClassName("body-icon-color")[0].className="body-icon-color color-push";
	}
	if(obj=='class'){
		e.currentTarget.className="body-icon-color color-red";
		this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student},'/testAnalysis',function(obj,res){
			if(res.code == '10000'){
				obj.optionTwoDimensionalAnalysis.series[0].data=[];
				obj.optionTwoDimensionalAnalysis.series[1].data=[];
				for(var i=0;i<res.result.listVO.length;i++){
					if(parseFloat(res.result.listVO[i].divideStudentToClass)>=0){
						var list=[];
						list.push(res.result.listVO[i].difficulty);
						list.push(parseFloat(res.result.listVO[i].divideStudentToClass));
						list.push(res.result.listVO[i].topic);
						list.push(parseFloat(res.result.listVO[i].fractionalValue));
						list.push(parseFloat(res.result.listVO[i].qid));
						obj.optionTwoDimensionalAnalysis.series[0].data.push(list);
					}else{
						var list1=[];
						list1.push(res.result.listVO[i].difficulty);
						list1.push(parseFloat(res.result.listVO[i].divideStudentToClass));
						list1.push(res.result.listVO[i].topic);
						list1.push(parseFloat(res.result.listVO[i].fractionalValue));
						list1.push(parseFloat(res.result.listVO[i].qid));
						obj.optionTwoDimensionalAnalysis.series[1].data.push(list1);
					}
				}
				obj.echarts.init(document.getElementById("twoDimensionalAnalysis1")).setOption(obj.optionTwoDimensionalAnalysis);
			}else{
				obj.notify_jr(obj,'错误提示',res.message,'error');
			}
		})
	}
	if(obj=='school'){
		e.currentTarget.className="body-icon-color color-green";
		this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student},'/testAnalysis',function(obj,res){
			if(res.code == '10000'){
				obj.optionTwoDimensionalAnalysis.series[0].data=[];
				obj.optionTwoDimensionalAnalysis.series[1].data=[];
				for(var i=0;i<res.result.listVO.length;i++){
					if(parseFloat(res.result.listVO[i].divideStudentToClass)>=0){
						var list=[];
						list.push(res.result.listVO[i].difficulty);
						list.push(parseFloat(res.result.listVO[i].divideStudentToSchool));
						list.push(res.result.listVO[i].topic);
						list.push(parseFloat(res.result.listVO[i].fractionalValue));
						list.push(parseFloat(res.result.listVO[i].qid));
						obj.optionTwoDimensionalAnalysis.series[0].data.push(list);
					}else{
						var list1=[];
						list1.push(res.result.listVO[i].difficulty);
						list1.push(parseFloat(res.result.listVO[i].divideStudentToSchool));
						list1.push(res.result.listVO[i].topic);
						list1.push(parseFloat(res.result.listVO[i].fractionalValue));
						list1.push(parseFloat(res.result.listVO[i].qid));
						obj.optionTwoDimensionalAnalysis.series[1].data.push(list1);
					}
				}
				obj.echarts.init(document.getElementById("twoDimensionalAnalysis1")).setOption(obj.optionTwoDimensionalAnalysis);
			}else{
				obj.notify_jr(obj,'错误提示',res.message,'error');
			}
		})

	}
	if(obj=='area'){
		e.currentTarget.className="body-icon-color color-blue";
		this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,studentId:this.basicData.student},'/testAnalysis',function(obj,res){
			if(res.code == '10000'){
				obj.optionTwoDimensionalAnalysis.series[0].data=[];
				obj.optionTwoDimensionalAnalysis.series[1].data=[];
				for(var i=0;i<res.result.listVO.length;i++){
					if(parseFloat(res.result.listVO[i].divideStudentToClass)>=0){
						var list=[];
						list.push(res.result.listVO[i].difficulty);
						list.push(parseFloat(res.result.listVO[i].divideStudentToArea));
						list.push(res.result.listVO[i].topic);
						list.push(parseFloat(res.result.listVO[i].fractionalValue));
						list.push(parseFloat(res.result.listVO[i].qid));
						obj.optionTwoDimensionalAnalysis.series[0].data.push(list);
					}else{
						var list1=[];
						list1.push(res.result.listVO[i].difficulty);
						list1.push(parseFloat(res.result.listVO[i].divideStudentToArea));
						list1.push(res.result.listVO[i].topic);
						list1.push(parseFloat(res.result.listVO[i].fractionalValue));
						list1.push(parseFloat(res.result.listVO[i].qid));
						obj.optionTwoDimensionalAnalysis.series[1].data.push(list1);
					}
				}
				obj.echarts.init(document.getElementById("twoDimensionalAnalysis1")).setOption(obj.optionTwoDimensionalAnalysis);
			}else{
				obj.notify_jr(obj,'错误提示',res.message,'error');
			}
		})
	}
},
objectSpanMethod({ row, column, rowIndex, columnIndex }) {
	if (columnIndex === 0) {
		if (rowIndex === 0) {
			return {
				rowspan: row.knowledgeNum,
				colspan: 1
			};
		} else {
			return {
				rowspan: 0,
				colspan: 0
			};
		}
	}
},
				accMul(arg1,arg2){   //乘法
					if(arg1==undefined){
						arg1=0;
						return
					}
					var m=0,s1=arg1.toString(),s2=arg2.toString();
					try{m+=s1.split(".")[1].length}catch(e){}
					try{m+=s2.split(".")[1].length}catch(e){}
					return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
				},
				accDiv(arg1,arg2){		//除法
					if(arg1==undefined){
						arg1=0;
						return
					}
					var t1=0,t2=0,r1,r2;
					try{t1=arg1.toString().split(".")[1].length}catch(e){}
					try{t2=arg2.toString().split(".")[1].length}catch(e){}
					
					r1=Number(arg1.toString().replace(".",""))
					r2=Number(arg2.toString().replace(".",""))
					return (r1/r2)*Math.pow(10,t2-t1);
					
				}
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
		#studentlevel{
			border-top: 1px solid #f2f2f2;
		}
		#studentlevel .borders{
			box-shadow: 1px 1px 14px rgba(0,0,0,.15);
		}
		#studentlevel #personalAchievement{
			width: 1100px;
			margin: auto;
			margin-top:20px;
		}	
		#studentlevel #anotherpersonalAchievement{
			width: 1100px;
			margin: auto;
			margin-top:20px;
		}
		#studentlevel .header{
			text-align: center;
		}
		#studentlevel .header p{
			font-size: 20px;
			color: #707070;
			letter-spacing: 0;
		}
		#studentlevel .header-title-foot{
			width: 100px;
			height: 2px;
			background: #44A9FF;
			margin: auto;
			margin-top:5px;
		}
		#studentlevel #personalAchievement .body{
			margin-top:20px;
		}
		#studentlevel #anotherpersonalAchievement .body{
			margin-top:20px;
		}
		#studentlevel .header-banner-bit{
			width: 89px;
			height: 73px;
			float: left;
			margin-top: 15px;
		}
		#studentlevel .table-header{
			background: #70CDF3;
			color: #fff;
		}
		#studentlevel .header-banner .el-carousel__item{
			margin-left: 60px;
		}
		#studentlevel .header-banner-click{
			width: 100%;
			height: 100%;
			text-align: center;
		}
		#studentlevel .header-banner-click p{
			line-height: 73px;
		}
		#studentlevel .header-banner-bit .on{
			height: 105%;
		}
		#studentlevel #beyondRate{
			width: 1100px;
			margin: auto;
			margin-top:20px;
		}
		#studentlevel #beyondRate .body{
			margin-top:20px;
		}
		#studentlevel #beyondRate .body-icon{
			width: 235px;
			margin: auto;
		}
		#studentlevel #beyondRate .body-icon-color{
			width: 20px;
			height: 20px;
			float: left;
		}
		#studentlevel #beyondRate .body-icon-bit{
			margin-left:20px;
			float: left;
		}
		#studentlevel #beyondRate .body-icon-bit p{
			margin-left: 5px;
			float: left;
		}
		#studentlevel #beyondRate .color-blue{
			background: #70CDF3;
		}
		#studentlevel #beyondRate .color-green{
			background: #49E1BC;
		}
		#studentlevel #beyondRate .color-red{
			background: #FF8585;
		}
		#beyondRate1{
			width: 1080px;
			height: 460px;
			margin: auto;
		}
		#growthTrend{
			width: 1100px;
			margin: auto;
			margin-top:50px;
		}
		#growthTrend .body{
			padding-top:20px;
		}
		#studentlevel .choose-area{
			width: 350px;
			margin: auto;
		}
		#studentlevel .choose-area-left{
			width: 115px;
			height:50px;
			border:1px solid #46C4F8;
			text-align: center;
			float: left;
			border-radius: 100px 0 0 100px;
			color: #fff;
			background: #46C4F8;
		}
		#studentlevel .choose-area-left p{
			line-height:55px;
		}
		#studentlevel .choose-area-midle{
			width: 115px;
			height:50px;
			border-top:1px solid #46C4F8;
			border-bottom:1px solid #46C4F8;
			text-align: center;
			float: left;
			color: #46C4F8;
		}
		#studentlevel .choose-area-midle p{
			line-height: 55px;
		}
		#studentlevel .choose-area-right{
			width: 115px;
			height:50px;
			border:1px solid #46C4F8;
			text-align: center;
			float: left;
			border-radius: 0 100px 100px 0;
			color: #46C4F8;
		}
		#studentlevel .choose-area-right p{
			line-height: 55px;
		}
		#growthTrend #growthTrend1{
			width: 1080px;
			height: 460px;
			margin: auto;
			margin-top: 70px;
			margin-bottom: 20px;
		}
		#subjectsDiagnosis .body{
			padding-top: 20px;
		}
		#subjectsDiagnosis1{
			width: 600px;
			height: 500px;
			margin-top:40px;
			float: left;
		}
		.subjectsDiagnosis1-title{
			width: 600px;
			margin-top: 20px;
			float: left;
			text-align: center;
		}
		.subjectsDiagnosis1-title p{
			font-size: 16px;
			color: #707070;
			letter-spacing: 0;
		}
		.subjectsDiagnosis2-title p{
			font-size: 16px;
			color: #707070;
			letter-spacing: 0;
		}
		.subjectsDiagnosis2-title{
			width: 470px;
			margin-top: 20px;
			float: left;
			text-align: center;
		}
		#studentlevel #subjectsDiagnosis2{
			width: 470px;
			height: 500px;
			float: left;
			margin-top:40px;
			margin-left: 20px;
		}
		#studentlevel .subjectsDiagnosisText{
			margin-top:20px;
		}
		#studentlevel .subjectsDiagnosisText p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
			line-height: 24px;
		}
		#studentlevel #myGoal{
			margin-top:20px;
		}
		#studentlevel #myGoal .body{
			padding-top: 20px;
		}
		#studentlevel .body-title-item{
			width: 584px;
			height: 93px;
			margin: auto;
			border:1px solid #FF8585;
		}
		#studentlevel .body-title-itemTitle{
			width: 250px;
			margin:auto;
			line-height: 60px;
		}
		#studentlevel .body-title-itemTitle p{
			float: left;
			margin-left: 30px;
		}
		#studentlevel .body-title-itemTxt{
			width: 280px;
			margin:auto;
			line-height: 20px;
		}
		#studentlevel .body-title-itemTxt p{
			float: left;
			margin-left: 60px;
			color: #FF8585;
		}
		#studentlevel #myGoal1{
			width: 1080px;
			height: 460px;
			margin: 30px;
		}
		#studentlevel #testAnalysis{
			margin-top:20px;
		}
		#studentlevel #testAnalysis .body{
			padding-top: 20px;
		}
		#studentlevel #testAnalysis .body-Vtitle{
			width: 370px;
			margin: auto;
		}
		#studentlevel #testAnalysis .body-Vtitle p{
			float: left;
			margin-left: 8px;
		}
		#studentlevel #testAnalysis .testAnalysis-table{
			margin-top:20px;
		}
		#studentlevel #testAnalysis .active{
			color: #52D871;
		}
		#studentlevel #testAnalysis .activeS{
			color: #FF8585;
		}
		#studentlevel #scoreQuestion{
			margin-top:20px;
		}
		#studentlevel #scoreQuestion .body{
			padding-top: 20px;
		}
		#studentlevel #scoreQuestion1{
			width: 1080px;
			height: 460px;
			margin: auto;
			margin-top: 20px;
			margin-bottom: 20px;
		}
		#studentlevel #twoDimensionalAnalysis{
			margin-top:20px;
		}
		#studentlevel #twoDimensionalAnalysis1{
			width: 1080px;
			height: 660px;
			margin: auto;
			margin-top: 20px;
			margin-bottom: 20px;
		}
		#studentlevel #twoDimensionalAnalysis .body{
			padding-top:20px;
		}
		#studentlevel #twoDimensionalAnalysis .body-icon{
			width: 255px;
			margin: auto;
		}
		#studentlevel #twoDimensionalAnalysis .body-icon-color{
			width: 20px;
			height: 20px;
			float: left;
		}
		#studentlevel #twoDimensionalAnalysis .body-icon-bit{
			margin-left:20px;
			float: left;
		}
		#studentlevel #twoDimensionalAnalysis .body-icon-bit p{
			margin-left: 5px;
			float: left;
		}
		#studentlevel #twoDimensionalAnalysis .color-blue{
			background: #70CDF3;
		}
		#studentlevel #twoDimensionalAnalysis .color-green{
			background: #49E1BC;
		}
		#studentlevel #twoDimensionalAnalysis .color-red{
			background: #FF8585;
		}
		#studentlevel #twoDimensionalAnalysis .color-push{
			background: #919191;
		}
		#studentlevel .twoDimensionalAnalysis-foot{
			width: 870px;
			height: 30px;
			margin: auto;
			margin-top: -45px;
		}
		#studentlevel .twoDimensionalAnalysis-foot .difficultyLevel{
			width: 33.33%;
			height: 100%;
			float:left;
			text-align: center;
			color: #fff;
		}
		#studentlevel .twoDimensionalAnalysis-foot .easy{
			background: #FF4444;
		}
		#studentlevel .twoDimensionalAnalysis-foot .midde{
			background: #FF8585;
		}
		#studentlevel .twoDimensionalAnalysis-foot .difficult{
			background: #FEB0B0;
		}
		#studentlevel .twoDimensionalAnalysis-foot .difficultyLevel p{
			line-height: 35px;
			font-size: 12px;
			letter-spacing: 0;
		}
		#studentlevel #twoDimensionalAnalysis .foot-word{
			width: 100%;
			margin-top:20px;
		}
		#studentlevel #twoDimensionalAnalysis .foot-word p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
			line-height: 24px;
		}
		#studentlevel #knowledge{
			margin-top:20px;
		}
		#studentlevel #knowledge .body{
			padding-top:20px;
		}
		#studentlevel #knowledge1{
			width: 1080px;
			height: 500px;
			margin: auto;
		}
		#studentlevel .knowledge-table{
			width: 1080px;
			margin: auto;
		}
		#studentlevel #knowledge-table .el-table thead.is-group th{
			background: #70CDF3;
			color: #fff;
		}
		#studentlevel #knowledge-table-header .el-table__body-wrapper{
			display: none;
		}
		#studentlevel #knowledge-table .knowledge-table-true .el-table__header-wrapper{
			display: none;
		}
		#studentlevel .knowledge-table-trueTotle{
			width: 100%;
			height: 50px;
			background: #FF8585;	
		}
		#studentlevel .knowledge-table-trueTotle .knowledge-table-trueTotleBit{
			line-height: 50px;
			float: left;
			width: 140px;
			color: #fff;
			text-align: center;
		}
		#studentlevel .knowledge-foot{
			margin-top:20px;
		}
		#studentlevel .knowledge-foot p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
			line-height: 24px;
		}
		#studentlevel #abilityAnalyze{
			margin-top: 20px;
		}
		#studentlevel #abilityAnalyze1{
			width: 1080px;
			height: 520px;
			margin: auto;
			margin-top: 20px;
		}
		#studentlevel #abilityAnalyze2 .el-table--border td, .el-table--border th{
			border:0px !important;
		}
		#studentlevel #abilityAnalyze2 .el-table thead.is-group th{
			background: #70CDF3;
			color: #fff;
		}
		#studentlevel .abilityAnalyze-foot{
			margin-top: 20px;
		}
		#studentlevel .abilityAnalyze-foot p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
			line-height: 24px;
		}
		#studentlevel .el-select .el-input .el-input__icon{
			color: #44A9FF
		}
		#studentlevel .el-carousel__arrow{
			background-color: white;
			color: #44A9FF
		}
		#studentlevel .el-carousel__arrow:hover{
			background-color: white
		}
		#classLevel .el-select .el-input .el-input__icon{
			color: #70CDF3
		}
		#studentlevel .el-carousel  {
			overflow: hidden;
			position: relative;
			text-align: center;
			margin: auto;
			width: 400px;
			height: 50px;
			margin-top: 30px;
			line-height: 50px;
		}
		#studentlevel #rainbow .el-carousel{
			width: 1100px;
			height: 100px;
		}
		#studentlevel .el-carousel__container {
			position: relative;
			height: 50px;
			margin: auto;
		}
		#studentlevel .myselect{
			border:1px solid #44a9ff;
			width: 90px;
			border-radius: 4px;
		}
		#studentlevel .testTips{
			color:#3d3d3d;
			font-size: 14px;
			margin-top: 25px
		}
		#studentlevel .schoolSelectBox{
			width: 200px;
			margin: auto;
			margin-bottom: 35px;
		}
		#studentlevel .achievementSelectBox{
			width: 90px;
			margin: auto;
			margin-bottom: 35px;
		}
		#studentlevel .borders{
			box-shadow: 1px 1px 14px rgba(0,0,0,.15);
		}
		#studentlevel .el-table .tableBackground{
			background-color: #f5fcff;text-align: center;
		}
		#studentlevel .el-table .tableCenter{
			text-align: center;
		}
		#studentlevel .alltest{
			cursor: pointer;
		}
		#studentlevel .formatRow{
			font-weight: normal;
			text-align:center !important;
			color: white;
			border: 0px !important;
			background-color: #70CDF3 !important;
		}
		#achievementTable .el-table--border td{
			border:0px;
		}
		#studentlevel .el-input__inner{
			border: 0px;
			text-align: center;
		}
		#studentlevel .el-select .el-input .el-input__icon{
			color: #70CDF3
		}
		#studentlevel .el-carousel__arrow{
			background-color: white;
			color: #70CDF3
		}
		#studentlevel .el-carousel__arrow:hover{
			background-color: white
		}
		#studentlevel .showselect{
			list-style: none;
			position: absolute;
			margin: 0;
			padding: 0;
			border: 1px solid #f2f2f2;
			left: 50%;
			margin-left: -150px;
			z-index: 999;
			background-color: white;
			width: 300px;
			text-align: center;
			max-height: 125px;
			overflow: auto;
		}
		#studentlevel .showselect li{
			margin: 0;padding:0;
			border-bottom: 1px solid #f2f2f2;
			padding:10px 20px;
			cursor: pointer;
		}
		#studentlevel .alltest{
			cursor: pointer;
		}
		.hand{
			cursor:pointer;
		}
		.achievementSelectBox{
			width: 90px;
			margin: auto;
			margin-bottom: 35px;
		}
		</style>