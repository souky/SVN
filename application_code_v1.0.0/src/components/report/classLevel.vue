<template>
	<div id="classLevel">
		<ul id="navInside" v-show="displayAll.studentGradeDistribution">
			<li v-for="item in liList" :class="activeList == item.id? 'active': ''" :key="item.id" @click="testtest(item.id)">{{item.name}}</li>
		</ul>
		<ul id="navInside1" v-show="displayAll.gradeDistribution">
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
		<div class="achievementSelectBox">
			<el-select v-model="changeSchool" class="myselect" @change="changeClassName" :placeholder="schoolList[0].classroomName">
				<el-option v-for="item in schoolList" :key="item.id" :label="item.classroomName" :value="item.id"></el-option>
			</el-select>
		</div>
		<div id="classTotalScore" class="louceng louceng1" v-show="displayAll.classTotalScore">
			<div class="header">
				<p>班级总成绩</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body pt20">
				<el-table class="borders" :data="schoolSelectBoxData" stripe style="width: 100%" header-row-class-name="table-header">
					<el-table-column align="center" prop="name" label=""></el-table-column>
					<el-table-column align="center" prop="classScore" label="班级"></el-table-column>
					<el-table-column align="center" prop="schoolScore" label="学校"></el-table-column>
					<el-table-column align="center" prop="regionScore" label="地区"></el-table-column>
				</el-table>
				<div class="body-foot">
					<p>{{schoolSelectBoxDataWord}}</p>
					<div class="body-foot-button">
						<el-button @click="contrastTest =true">考试对比</el-button>
					</div>
				</div>
			</div>
		</div>
		<el-dialog title="成绩对比" :visible.sync="contrastTest" width="900px" @open='classLastScoreshow()' center>
			<center v-show="diaLoading" style="margin-top:20%;"><i class="el-icon-loading"></i></center>
			<div id="classLastScore"></div>
		</el-dialog>
		<div id="gradeDistribution" v-show="displayAll.gradeDistribution" class="mt20">
			<div class="header">
				<p>分数等级分布图</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body pt20">
				<div id="gradeDistribution1"></div>
				<div class="body-foot">
					<p>{{optiongradeDistributionWord}}</p>
				</div>
				<div class="classSelectBox">
					<el-select v-model="changeSchool" @change="changeClassName1" class="myselectS" placeholder="请选择">
						<el-option
						v-for="item in schoolList1"
						:key="item.id"
						:label="item.classroomName"
						:value="item.id">
					</el-option>
				</el-select>
				<el-button type="primary" @click="gradeDistributionshow()">班级对比</el-button>
			</div>
		</div>
	</div>
	<div id="ranking" v-show="displayAll.ranking" class="mt20 louceng1">
		<div class="header">
			<p>校前？名分布图</p>
			<div class="header-title-foot"></div>
			<div class="body-title mt20">
				<p>班级：共{{rankingListS.classStuNum}}人    全校：{{rankingListS.schoolStuNum}}人</p>
			</div>
		</div>
		<div class="body pt20">
			<div id="ranking1"></div>
			<div id="ranking2">
				<div class="ranking-header">
					<p>前{{basicData.topNum}}名学生名单</p>
				</div>
				<div class="ranking-body">
					<ul>
						<li class="cl" v-for="item in rankingList"><p class="ranking-bodyName">{{item.studentName}}</p><p class="ranking-bodyNum">第{{item.ranking}}名</p></li>
					</ul>
				</div>
				<div class="ranking-foot">
					<div class="ranking-footLeft" @click="rankingTopic($event)">
						<i class="el-icon-arrow-left"></i>
						<p>前50名</p>
					</div>
					<div class="ranking-footRight" @click="rankingTopic($event)">
						<i class="el-icon-arrow-right"></i>
						<p>前100名</p>
					</div>
				</div>
			</div>
			<div class="cl" id="ranking3">
				<el-table class="borders" :data="rankingData" stripe style="width: 100%" header-row-class-name="table-header">
					<el-table-column align="center" prop="topic10" label="前10名"></el-table-column>
					<el-table-column align="center" prop="topic20" label="前20名"></el-table-column>
					<el-table-column align="center" prop="topic50" label="前50名"></el-table-column>
					<el-table-column align="center" prop="topic100" label="前100名"></el-table-column>
					<el-table-column align="center" prop="topic200" label="前200名"></el-table-column>
					<el-table-column align="center" prop="topic500" label="前500名"></el-table-column>
					<el-table-column align="center" prop="topic1000" label="前1000名"></el-table-column>
				</el-table>
			</div>
		</div>
	</div>
	<div id="ClassdisciplinesLevel" v-show="displayAll.ClassdisciplinesLevel" class="mt20 louceng1">
		<div class="header">
			<p>班级学科均衡水平</p>
			<div class="header-title-foot"></div>
		</div>
		<div class="body pt20">
			<div class="subjectsDiagnosis1-title"><p>学科均衡性</p></div>
			<div class="subjectsDiagnosis2-title"><p>班级与学校平均水平对比</p></div>
			<div id="ClassdisciplinesLevel1"></div>
			<div id="ClassdisciplinesLevel2"></div>
			<div class="cl"></div>
			<div class="subjectsDiagnosisText">
				<p>{{optionClassdisciplinesLevelWord}}</p>
				<p>{{optionClassdisciplinesLevelRightWord}}</p>
			</div>
		</div>
	</div>
	<div id="classReport" v-show="displayAll.classReport" class="mt20 mb20">
		<div class="header">
			<p>班级成绩报告单</p>
			<div class="header-title-foot"></div>
		</div>
		<div class="body pt20">
			<el-table :data="tableData3" style="width: 100%" header-row-class-name="table-header">
				<template v-for="item in headerData">
					<el-table-column align="center" v-if="item.dataIndex=='changedRanking'" :key="item.name" :label="item.name" :prop="item.dataIndex">
						<template slot-scope="scope">
							<span v-if="scope.row.isImproved == 1"><i class="el-icon-arrow-up mr5"></i>{{ scope.row.changedRanking}}</span>
							<span v-else-if="scope.row.isImproved == 0">{{ scope.row.changedRanking}}</span>
							<span v-else><i class="el-icon-arrow-down mr5"></i>{{ scope.row.changedRanking}}</span>
						</template>
					</el-table-column>
					<el-table-column align="center" v-else :key="item.name" :label="item.name" :prop="item.dataIndex"></el-table-column>
				</template>
			</el-table>
		</div>
	</div>
	<div id="studentGradeDistribution" v-show="displayAll.studentGradeDistribution" class="mt20">
		<div class="header">
			<p>学生等级分布图</p>
			<div class="header-title-foot"></div>
		</div>
		<div class="body pt20 mb10">
			<div class="body-Vtitle">
				<p>学生各等级人数分布图</p>
				<p>共{{studentTotleNum}}人，满分{{studentTotle}}分</p>
			</div>
			<div id="studentGradeDistribution1" class="l"></div>
			<div id="studentGradeDistribution2" class="r">
				<div class="studentGradeDistribution2-header">
					<p>{{studentGradeheader.name}}{{studentGradeheader.level}}学生名单{{studentGradeheader.number}}人</p>
					<p>分数区间({{studentGradeheader.startScore}}~{{studentGradeheader.endScore}}分)</p>
				</div>
				<div class="studentGradeDistribution2-body">
					<div class="studentGradeDistribution-ranking-body">
						<ul>
							<li class="cl" v-for="item in studentGradeDistributionList"><p class="ranking-bodyName">{{item.studentName}}</p><p class="ranking-bodyNum">第{{item.ranking}}名</p></li>
						</ul>
					</div>
				</div>
				<div class="ranking-foot">
					<div class="ranking-footLeft" style="display:none" @click="gradeIntervalfun($event)">
						<i class="el-icon-arrow-left"></i>
						<el-button :data-key="gradeInterval[0].key" type="text">{{gradeInterval[0].name}}</el-button>
					</div>
					<div class="ranking-footRight" @click="gradeIntervalfun($event)">
						<i class="el-icon-arrow-right"></i>
						<el-button :data-key="gradeInterval[1].key" type="text">{{gradeInterval[1].name}}</el-button>
					</div>
				</div>
			</div>
			<div class="classSelectBox">
				<el-select v-model="changeSchoolS" @change="changeClassName2" class="myselectS vclassSelectBox" placeholder="请选择">
					<el-option
					v-for="item in schoolList2"
					:key="item.id"
					:label="item.classroomName"
					:value="item.id">
				</el-option>
			</el-select>
			<el-button type="primary" @click="contrastTestStudents =true">班级对比</el-button>
		</div>
	</div>
</div>
<el-dialog title="成绩对比" :visible.sync="contrastTestStudents" width="900px" @open='classLastStudentsshow()' center>
	<center v-show="diaStudentsLoading" style="margin-top:20%;"><i class="el-icon-loading"></i></center>
	<div id="classLastStudents"></div>
</el-dialog>
<div id="classTestAnalysis" v-show="displayAll.classTestAnalysis" class="mt20 louceng">
	<div class="header">
		<p>试题分析</p>
		<div class="header-title-foot"></div>
	</div>
	<div class="body pt20">
		<el-table class="borders" :data="testAnalysisTable" stripe style="width: 100%" header-row-class-name="table-header">
			<el-table-column align="center" prop="qid" label="题号"></el-table-column>
			<el-table-column align="center" prop="topic" label="题型"></el-table-column>
			<el-table-column align="center" prop="fractionalValue" label="分值"></el-table-column>
			<el-table-column align="center" prop="divide" label="平均分"></el-table-column>
			<el-table-column align="center" label="得分率">
				<el-table-column align="center" prop="divideClass" label="班级"></el-table-column>
				<el-table-column align="center" prop="divideSchool" label="校级"></el-table-column>
				<el-table-column align="center" prop="divideAera" label="区县级"></el-table-column>
			</el-table-column>
			<el-table-column align="center" prop="trueNum" label="满分人数"></el-table-column>
			<el-table-column align="center" prop="falseNum" label="答错人数"></el-table-column>
			<el-table-column align="center" prop="difficulty" label="难度"></el-table-column>
			<el-table-column align="center" prop="differentiation" label="区分度"></el-table-column>
		</el-table>
	</div>
</div>
<div id="classOptionScoreQuestion" v-show="displayAll.classOptionScoreQuestion" class="mt20">
	<div class="header">
		<p>各小题得分率柱状图</p>
		<div class="header-title-foot"></div>
	</div>
	<div class="body pt20">
		<div id="classOptionScoreQuestion1"></div>
	</div>
</div>
<div id="classtwoDimensionalAnalysis" v-show="displayAll.classtwoDimensionalAnalysis" class="mt20">
	<div class="header">
		<p>得分率——难度二维分析</p>
		<div class="header-title-foot"></div>
	</div>
	<div class="body">
		<div id="classtwoDimensionalAnalysis1"></div>
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
			<p>{{optionClassScoreQuestionWord}}</p>
		</div>
	</div>
</div>
<div id="classknowledge" v-show="displayAll.classknowledge" class="mt20 louceng">
	<div class="header">
		<p>知识点分析</p>
		<div class="header-title-foot"></div>
	</div>
	<div class="body pt20">
		<div id="classknowledge1"></div>
		<div id="classknowledge2">
			<el-table id="knowledge-table-header" :data="falsetableData3" header-row-class-name="table-header" style="width: 100%">
				<el-table-column align="center" prop="date" label="知识点模块"></el-table-column>
				<el-table-column align="center" prop="date" label="知识点"></el-table-column>
				<el-table-column align="center" label="得分率%">
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
					<el-table-column align="center" label="得分率%">
						<el-table-column align="center" prop="divideClass" label="班级"></el-table-column>
						<el-table-column align="center" prop="divideSchool" label="校级"></el-table-column>
						<el-table-column align="center" prop="divideAera" label="区级"></el-table-column>
						<el-table-column align="center" prop="differenceOfDivide" label="差值(相对于校级)"></el-table-column>
					</el-table-column>
					<el-table-column align="center" prop="qid" label="对应题目"></el-table-column>
				</el-table>
				<div class="knowledge-table-trueTotle">
					<div class="knowledge-table-trueTotleBit"><p>总分:{{truetableData3.totle}}分</p></div>
					<div class="knowledge-table-trueTotleBit"><p>得分:{{truetableData3.score}}分</p></div>
					<div class="knowledge-table-trueTotleBit"><p>得分率:{{truetableData3.divideClass}}</p></div>
					<div class="knowledge-table-trueTotleBit"><p>得分率:{{truetableData3.divideSchool}}</p></div>
					<div class="knowledge-table-trueTotleBit"><p>得分率:{{truetableData3.divideAera}}</p></div>
				</div>
			</div>
		</div>
		<div class="abilityAnalyze-foot">
			<p>{{optionclassknowledgeWord}}</p>
		</div>
	</div>
</div>
<div id="classabilityAnalyze" v-show="displayAll.classabilityAnalyze" class="mt20">
	<div class="header">
		<p>能力分析</p>
		<div class="header-title-foot"></div>
	</div>
	<div class="body pt20">
		<div id="classabilityAnalyze1"></div>
		<div id="classabilityAnalyze2">
			<el-table class="borders" :data="scoreName" style="width: 100%">
				<el-table-column align="center" prop="ablityName" label="能力"></el-table-column>
				<el-table-column align="center" label="得分率">
					<el-table-column align="center" prop="divideClass" label="班级"></el-table-column>
					<el-table-column align="center" prop="divideSchool" label="全校"></el-table-column>
					<el-table-column align="center" prop="divideAera" label="全区县"></el-table-column>
				</el-table-column>
				<el-table-column align="center" prop="numbers" label="对应题目"></el-table-column>
			</el-table>
		</div>
		<div class="abilityAnalyze-foot">
			<p>{{optionclassabilityAnalyzeWord}}</p>
		</div>
	</div>
	<div class="leftAnchorPoint">
		
	</div>
</div>
</div>
</template>
<script>
export default{
	data(){
		return{
			basicData:{
				subject:'总分',
				class:'',
				className:'',
				anotherclass:'',
				id:'',
				topNum:50,
				rateType:'',
			},
			activeList:0,
			activeList1:0,
			liList:[{
				name:'班级总成绩',
				id:0
			},{
				name:'试题分析',
				id:1
			},{
				name:'知识点分析',
				id:2
			}],
			liList1:[{
				name:'班级总成绩',
				id:0
			},{
				name:'校前 ？名分布图',
				id:1
			},{
				name:'班级学科均衡水平',
				id:2
			}],
			diaLoading:true,
			diaStudentsLoading:true,
			contrastTestStudents:false,
			contrastTest:false,
			showselect:false,
			testAnalysisTable:[],
			studentGradeheader:{
				name:"高分",
				level:"(90%~100%)",
				endScore:0,
				startScore:0,
				number:0,
			},
			gradeInterval:[{
				key:1,
				name:"高分",
				level:"(90%~100%)",
				endScore:0,
				startScore:0,
				number:0,
				value:'HIGH_SCORE',
			},{
				key:2,
				name:"优秀",
				level:"(80%~90%)",
				endScore:0,
				startScore:0,
				number:0,
				value:'EXCELLENT',
			},{
				key:3,
				name:"良好",
				level:"(70%~80%)",
				endScore:0,
				startScore:0,
				number:0,
				value:'WELL',
			},{
				key:4,
				name:"及格",
				level:"(60%~70%)",
				endScore:0,
				startScore:0,
				number:0,
				value:'PASS',
			},{
				key:5,
				name:"不及格",
				level:"(0%~60%)",
				endScore:0,
				startScore:0,
				number:0,
				value:'FAIL',
			}],
			displayAll:{
				classTotalScore:true,
				gradeDistribution:true,
				ranking:true,
				ClassdisciplinesLevel:true,
				classReport:true,
				studentGradeDistribution:false,
				classTestAnalysis:false,
				classOptionScoreQuestion:false,
				classtwoDimensionalAnalysis:false,
				classknowledge:false,
				classabilityAnalyze:false,
			},
			scoreName:[],
			falsetableData3:[],
			truetableDatas:[],
			headerData:[{
				name : '名次',
				dataIndex:'classRanking',
			},{
				name : '考号/学号',
				dataIndex:'studentNo',
			},{
				name : '姓名',
				dataIndex:'studentName',
			},{
				name : '总分',
				dataIndex:'totalScore',
			},{
				name : '名次(校级)',
				dataIndex:'ranking',
			},{
				name : '名次进退步(校级)',
				dataIndex:'changedRanking',
			}],
			SheaderData:[{
				name : '名次',
				dataIndex:'classRanking',
			},{
				name : '考号/学号',
				dataIndex:'studentNo',
			},{
				name : '姓名',
				dataIndex:'studentName',
			},{
				name : '总分',
				dataIndex:'totalScore',
			},{
				name : '名次(校级)',
				dataIndex:'ranking',
			},{
				name : '名次进退步(校级)',
				dataIndex:'changedRanking',
			}],
			testList:[],
			tableData3:[],
			subjects:[],
			items:[{
				id:1,
				name:'总分',
				color:'#F37070',
			}],
			changeSchool:'',
			changeSchoolS:'',
			schoolList:[{
				id:'001',
				name:'1班'
			},{
				id:'002',
				name:'2班'
			}],
			schoolList1:[],
			schoolList2:[],
			schoolSelectBoxData:[],
			schoolSelectBoxDataWord:"",
			rankingList:[],
			rankingListS:{
				classStuNum:0,
				schoolStuNum:0,
			},
			studentGradeDistributionList:[],
			studentTotle:0,
			studentTotleNum:0,
			Personnel:{},
			rankingData:[{
				topic10:0,
				topic20:0,
				topic50:0,
				topic100:0,
				topic200:0,
				topic500:0,
				topic1000:0,
			}],
			classReportTable:[{}],
			optiongradeDistributionWord:"",
			optiongradeDistribution:{
				tooltip: {
					trigger: 'axis',
				},
				legend: {
					data:['班级','全校','全区县']
				},
				xAxis: [
				{
					type: 'category',
					data: ['高分率','优秀率','良好率','合格率','不及格率'],
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
					name:'百分比/%',
				},
				],
				series: [
				{
					name:'班级',
					type:'bar',
					barWidth: '30%',	
					label:{normal:{show:true,position:'top'},},
					itemStyle:{
						normal:{color:'#46C4F8'},
					},
					data:[2.0, 4.9, 7.0, 23.2, 25.6]
				},
				{
					name:'全校',
					type:'line',
					label:{normal:{show:true,position:'top'},},
					itemStyle:{
						normal:{color:'#FFD244'},
					},
					data:[2.6, 5.9, 9.0, 26.4, 28.7]
				},
				{
					name:'全区县',
					type:'line',
					label:{normal:{show:true,position:'top'},},
					itemStyle:{
						normal:{color:'#919191'},
					},
					data:[2.0, 2.2, 3.3, 4.5, 6.3]
				}
				]
			},
			optionranking:{
				color: ['#70CDF3'],
				tooltip : {
					trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    xAxis : [
			    {
			    	type : 'category',
			    	data : ['前10名', '前20名', '前50名', '前100名', '前200名', '前500名', '前1000名'],
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
			    	name:'人数',
			    	type:'bar',
			    	barWidth: '60%',
			    	data:[0, 0, 0, 0, 0, 0, 0]
			    }
			    ]
			},
			optionClassdisciplinesLevelWord:"",
			optionClassdisciplinesLevel:{
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
				    	right: '6%',
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
				optionClassdisciplinesLevelRightWord:"",
				optionClassdisciplinesLevelRight:{
					tooltip : {
						trigger: 'item',
					},
					legend: {
						x: 'center',
						data:['班级各科目-（单位：%）','校级-（单位：%）']
					},
					grid: {
						left: '3%',
						right: '3%',
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
						radius: 180,
						center: ['50%','55%'],
					}
					],
					series: [
					{
						type: 'radar',
						data: [
						{
							name: '班级各科目-（单位：%）',
							value: [23,43,54,65,23],
							itemStyle: {normal: {color: '#FFD244',areaStyle: {color: 'rgba(255,210,68,0.3)'}}},
						},
						{
							name:'校级-（单位：%）',
							value:[34,54,56,76,87],
							itemStyle: {normal: {color: '#70CDF3',areaStyle: {color: 'rgba(112,205,243,0.3)'}}},
						}
						]
					}
					]
				},
				optionstudentGradeDistribution:{
					tooltip : {
						trigger: 'item',
						formatter: "{a} <br/>{b} :{d}%"
					},
					legend: {
						data: ['高分(90%~100%)','优秀(80%~90%)','良好(70%~80%)','及格(60%~70%)','不及格(0%~60%)']
					},
					series : [
					{
						name: '数据',
						type: 'pie',
						radius : '80%',
						center: ['50%', '55%'],
						data:[
						{value:335, name:'高分(90%~100%)',itemStyle:{normal:{color:'#F37070'}}},
						{value:310, name:'优秀(80%~90%)',itemStyle:{normal:{color:'#F3DA70'}}},
						{value:234, name:'良好(70%~80%)',itemStyle:{normal:{color:'#70CDF3'}}},
						{value:135, name:'及格(60%~70%)',itemStyle:{normal:{color:'#7092F3'}}},
						{value:1548, name:'不及格(0%~60%)',itemStyle:{normal:{color:'#70F3A9'}}}
						],
					}
					]
				},
				optionClassScoreQuestionWord:"",
				optionClassScoreQuestion:{
					tooltip: {
						trigger: 'axis',
					},
					legend: {
						data:['班级','全校','全区县']
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
						name:'班级',
						type:'bar',
						label:{normal:{show:true,position:'top'},},
						itemStyle:{
							normal:{color:'#46C4F8'},
						},
						data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 43.6, 56.2, 32.6, 20.0, 6.4, 3.3,2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 35.6]
					},
					{
						name:'全校',
						type:'line',
						label:{normal:{show:true,position:'top'},},
						itemStyle:{
							normal:{color:'#FFD244'},
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
				optionTwoDimensionalAnalysisS:{
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
				optionclassknowledgeWord:"",
				optionclassknowledge:{
					tooltip: {},
					legend: {
						x: 'center',
						data:['班级','全校','全区县']
					},
					radar: [
					{
						indicator: [
						{text: '函数', max: 100},
						{text: '三角函数', max: 100},
						{text: '圆锥曲线与方程', max: 100},
						],
						radius: 180,
						center: ['50%','55%'],
					}
					],
					series: [
					{
						type: 'radar',
						data: [
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
				optionclassabilityAnalyzeWord:"",
				optionclassabilityAnalyze:{
					tooltip: {},
					legend: {
						x: 'center',
						data:['班级','全校','全区县']
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
				optionclassLastScore:{
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						data:['平均分']
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					xAxis: {
						type: 'category',
						data: ['2017期末考试','2017期中考试','2016期末考试','2016期中考试','2015期末考试']
					},
					yAxis: {
						type: 'value'
					},
					series: [
					{
						name:'平均分',
						type:'line',
						label:{normal:{show:true,position:'top'},},
						itemStyle:{
							normal:{color:'#70CDF3'},
						},
						data:[120, 132, 101, 134, 90]
					}
					]
				},
				optionclassLastStudents:{
					tooltip: {
						trigger: 'item',
						formatter: "{a} <br/>{b}: {d}%"
					},
					legend: {
						data:['高分(90%~100%)','优秀(80%~90%)','良好(70%~80%)','及格(60%~70%)','不及格(0%~60%)']
					},
					series: [
					{
						name:'数据',
						type:'pie',
						radius: [0, '45%'],
						label: {
							normal: {
								position: 'inner'
							}
						},
						labelLine: {
							normal: {
								show: false
							}
						},
						data:[
						{value:335, name:'高分(90%~100%)',itemStyle:{normal:{color:'#F37070'}}},
						{value:310, name:'优秀(80%~90%)',itemStyle:{normal:{color:'#F3DA70'}}},
						{value:234, name:'良好(70%~80%)',itemStyle:{normal:{color:'#70CDF3'}}},
						{value:135, name:'及格(60%~70%)',itemStyle:{normal:{color:'#7092F3'}}},
						{value:1048, name:'不及格(0%~60%)',itemStyle:{normal:{color:'#70F3A9'}}},
						]
					},
					{
						name:'对比班级',
						type:'pie',
						radius: ['55%', '70%'],

						data:[
						{value:335,
							label: {
								normal: {
									formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}}  {per|{d}%}  ',
									backgroundColor: '#eee',
									borderColor: '#aaa',
									borderWidth: 1,
									borderRadius: 4,
									rich: {
										a: {
											color: '#999',
											lineHeight: 22,
											align: 'center'
										},
										hr: {
											borderColor: '#aaa',
											width: '100%',
											borderWidth: 0.5,
											height: 0
										},
										per: {
											color: '#eee',
											backgroundColor: '#334455',
											padding: [2, 4],
											borderRadius: 2
										}
									}
								}
							}, name:'高分(90%~100%)',itemStyle:{normal:{color:'#F37070'}}},
							{value:310, name:'优秀(80%~90%)',itemStyle:{normal:{color:'#F3DA70'}}},
							{value:234, name:'良好(70%~80%)',itemStyle:{normal:{color:'#70CDF3'}}},
							{value:135, name:'及格(60%~70%)',itemStyle:{normal:{color:'#7092F3'}}},
							{value:1048, name:'不及格(0%~60%)',itemStyle:{normal:{color:'#70F3A9'}}},
							]
						}
						]
					}
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
				this.optionTwoDimensionalAnalysisS.tooltip.formatter=function (obj) {
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
								if(oNav[i]){
									oNav[i].className = 'active';
								}
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
				testChange(e){
					var name=this.testList[e].id;
					this.basicData.id=name;
					var totle={createDate:1512647749000,createUser:"1",id:"0",isDelete:0,orgId:"",remark:"",schoolId:"",subjectCode:1,subjectColor:"#F37070",subjectName:"总分",updateDate:1513586055000};
					this.postHttp(this,{},'exam/getExamListForTab',function(obj,res){
						if(res.code == '10000'){
							obj.testList=res.result.exams;
							obj.items=res.result[name].subject;
							obj.schoolList=res.result[name].classroom;
							obj.schoolList1=res.result[name].classroom;
							obj.schoolList2=res.result[name].classroom;
							obj.basicData.class=res.result[name].classroom[0].id;
							obj.basicData.className=res.result[name].classroom[0].classroomName;
							obj.basicData.subject="总分";
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
							obj.headerData=[];
							for(var p=0;p<obj.SheaderData.length;p++){
								obj.headerData.push(obj.SheaderData[p])
							}
							for(var q=5;q<parseInt(res.result[name].subject.length)+4;q++){
								var arr={
									"name":res.result[name].subject[q-4].subjectName,
									"dataIndex":"code"+res.result[name].subject[q-4].id,
								}
								obj.headerData.push(arr);
							}
							obj.rainbow(0,0,"总分");
						}else{
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					});

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
				changetest:function(e,ename){
					this.showselect = !this.showselect
					this.$refs.carousel.setActiveItem(ename);
				},
				selectShow:function(){
					this.showselect = !this.showselect
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
						this.displayAll.classTotalScore=true;
						this.displayAll.gradeDistribution=true;
						this.displayAll.ranking=true;
						this.displayAll.ClassdisciplinesLevel=true;
						this.displayAll.classReport=true;
						this.displayAll.studentGradeDistribution=false;
						this.displayAll.classTestAnalysis=false;
						this.displayAll.classOptionScoreQuestion=false;
						this.displayAll.classtwoDimensionalAnalysis=false;
						this.displayAll.classknowledge=false;
						this.displayAll.classabilityAnalyze=false;

						this.basicData.subject=name;
						this.getLevelDistribution();
						this.getTopOrgScoresInClass();
						this.getClassRankingReport();
						this.geReportCards();
						this.getClassScoreReport();
					}else{
						var oNav = document.getElementById("navInside").getElementsByTagName("li");
						for(var a = 0;a<oNav.length;a++){
							oNav[a].className = '';
						}
						oNav[0].className = 'active';
						this.displayAll.classTotalScore=true;
						this.displayAll.gradeDistribution=false;
						this.displayAll.ranking=false;
						this.displayAll.ClassdisciplinesLevel=false;
						this.displayAll.classReport=false;
						this.displayAll.studentGradeDistribution=true;
						this.displayAll.classTestAnalysis=true;
						this.displayAll.classOptionScoreQuestion=true;
						this.displayAll.classtwoDimensionalAnalysis=true;
						this.displayAll.classknowledge=true;
						this.displayAll.classabilityAnalyze=true;

						this.basicData.subject=name;
						this.testAnalysis();
						this.getLevelDistribution();
						this.ablityAnalysis();
						this.knowAnalysis();
						this.getClassScoreReport();
					}
				},
				changeClassName(value){
					this.basicData.class=value;
					this.rainbow(0,0,"总分");
					for(var i=0;i<this.schoolList.length;i++){
						if(value==this.schoolList[i].id){
							this.basicData.className=this.schoolList[i].classroomName;
						}
					}
				},
				changeClassName1(value){
					this.basicData.anotherclass=value;
					this.postHttp(this,{examId:this.basicData.id,tab:'CLASS_REPORT', subject:this.basicData.subject,classroomId:value},'score/getLevelDistribution',function(obj,res){
						if(res.code == '10000'){
							obj.optiongradeDistribution.legend.data=[];
							obj.optiongradeDistribution.legend.data=['班级','全校','全区县','对比班级'];
							var arrList={normal:{show:true,position:'top'},};
							if(obj.optiongradeDistribution.series.length==3){
								var list=[];
								list.push(res.result.scoreVOList[1].highRate* 1000000/10000);
								list.push(res.result.scoreVOList[1].excellentRate* 1000000/10000);
								list.push(res.result.scoreVOList[1].commissionRate* 1000000/10000);
								list.push(res.result.scoreVOList[1].passRate* 1000000/10000);
								list.push(res.result.scoreVOList[1].failureRate* 1000000/10000);
								var arr={
									"name":"对比班级",
									"type":'bar',
									"barWidth": '30%',	
									"label":arrList,
									"itemStyle":"{normal:{color:'#FF8585'},}",
									"data":list
								}
								obj.optiongradeDistribution.series.push(arr);
							}else{
								obj.optiongradeDistribution.series[3].data=[];
								obj.optiongradeDistribution.series[3].data.push(res.result.scoreVOList[1].highRate* 1000000/10000);
								obj.optiongradeDistribution.series[3].data.push(res.result.scoreVOList[1].excellentRate* 1000000/10000);
								obj.optiongradeDistribution.series[3].data.push(res.result.scoreVOList[1].commissionRate* 1000000/10000);
								obj.optiongradeDistribution.series[3].data.push(res.result.scoreVOList[1].passRate* 1000000/10000);
								obj.optiongradeDistribution.series[3].data.push(res.result.scoreVOList[1].failureRate* 1000000/10000);
							}
						}else{
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					})
				},
				changeClassName2(value){
					this.basicData.anotherclass=value;
					this.postHttp(this,{examId:this.basicData.id,tab:'CLASS_REPORT',subject:this.basicData.subject,classroomId:value,rateType:''},'score/getLevelDistribution',function(obj,res){
						if(res.code == '10000'){
							obj.optionclassLastStudents.series[0].data[0].value=obj.optionstudentGradeDistribution.series[0].data[0].value;
							obj.optionclassLastStudents.series[0].data[1].value=obj.optionstudentGradeDistribution.series[0].data[1].value;
							obj.optionclassLastStudents.series[0].data[2].value=obj.optionstudentGradeDistribution.series[0].data[2].value;
							obj.optionclassLastStudents.series[0].data[3].value=obj.optionstudentGradeDistribution.series[0].data[3].value;
							obj.optionclassLastStudents.series[0].data[4].value=obj.optionstudentGradeDistribution.series[0].data[4].value;
							if(res.result.scoreVOList){
								var num=0;
								for(var i=0;i<res.result.scoreVOList.length;i++){
									if(res.result.scoreVOList[i].subject==obj.basicData.subject){
										num=i;
									}
								}
								obj.optionclassLastStudents.series[1].name="";
								obj.optionclassLastStudents.series[1].data[0].value="";
								obj.optionclassLastStudents.series[1].data[1].value="";
								obj.optionclassLastStudents.series[1].data[2].value="";
								obj.optionclassLastStudents.series[1].data[3].value="";
								obj.optionclassLastStudents.series[1].data[4].value="";
								obj.optionclassLastStudents.series[1].name=res.result.classroomName;
								obj.optionclassLastStudents.series[1].data[0].value=res.result.scoreVOList[num].highRate;
								obj.optionclassLastStudents.series[1].data[1].value=res.result.scoreVOList[num].excellentRate;
								obj.optionclassLastStudents.series[1].data[2].value=res.result.scoreVOList[num].commissionRate;
								obj.optionclassLastStudents.series[1].data[3].value=res.result.scoreVOList[num].passRate;
								obj.optionclassLastStudents.series[1].data[4].value=res.result.scoreVOList[num].failureRate;
								//console.log(obj.optionclassLastStudents.series[1].name)
								//obj.echarts.init(document.getElementById("classLastStudents")).setOption(obj.optionclassLastStudents);
							}
						}else{
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					})
				},
				getClassScoreReport:function(){
					this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,classroomId:this.basicData.class},'score/getClassScoreReport',function(obj,res){
						if(res.code == '10000'){
							obj.schoolSelectBoxData=res.result.classReports;
							obj.schoolSelectBoxDataWord=res.result.summaryVO.classTotalScore;
						}else{
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					})
				},
				geReportCards:function(){
					this.postHttp(this,{examId:this.basicData.id,tab:'CLASS_REPORT', subject:this.basicData.subject,range:'CLASS',classroomId:this.basicData.class},'score/geReportCards',function(obj,res){
						if(res.code == '10000'){
							obj.optionClassdisciplinesLevel.xAxis[0].data=[];
							obj.optionClassdisciplinesLevel.xAxis[0].data=res.result.subjectList;
							obj.optionClassdisciplinesLevel.series[0].data=[];
							obj.optionClassdisciplinesLevel.series[0].data=res.result.standardScoreList;
							obj.optionClassdisciplinesLevel.series[0].markLine.data[0].yAxis=res.result.standardScoreList[0];
							obj.optionClassdisciplinesLevelRight.series[0].data[0].value=[];
							obj.optionClassdisciplinesLevelRight.series[0].data[1].value=[];
							obj.optionClassdisciplinesLevelRight.series[0].data[0].value=res.result.classAvgScoreList;
							obj.optionClassdisciplinesLevelRight.series[0].data[1].value=res.result.schoolAvgScoreList;
							obj.optionClassdisciplinesLevelRight.radar[0].indicator=[];
							for(var i=0;i<res.result.subjectList.length;i++){
								var arr={
									"text":res.result.subjectList[i],
									"max":100,
								}
								obj.optionClassdisciplinesLevelRight.radar[0].indicator.push(arr);
							}
							obj.optionClassdisciplinesLevelWord=res.result.summaryVO.stuSubjBalanceST;
							obj.optionClassdisciplinesLevelRightWord=res.result.summaryVO.stuSubjBalancePR;
							obj.echarts.init(document.getElementById("ClassdisciplinesLevel2")).setOption(obj.optionClassdisciplinesLevelRight);
							obj.echarts.init(document.getElementById("ClassdisciplinesLevel1")).setOption(obj.optionClassdisciplinesLevel);
						}else{
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					})
				},
				knowAnalysis:function(){
					this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,classroomId:this.basicData.class},'/knowAnalysis',function(obj,res){
						if(res.code == '10000'){
							obj.truetableDatas=res.result.listVO;
							obj.optionclassknowledge.series[0].data[0].value=[];
							obj.optionclassknowledge.series[0].data[1].value=[];
							obj.optionclassknowledge.series[0].data[2].value=[];
							obj.optionclassknowledge.radar[0].indicator=[];
							if(res.result.listVO){
								document.getElementById("classknowledge1").style.display="block";
								if(res.result.listVO.length>=3){
									for(var i=0;i<res.result.listVO.length;i++){
										var arr={
											"text": res.result.listVO[i].knowDetail[0].knowledgemodule,
											"max": 100,
										}
										obj.optionclassknowledge.radar[0].indicator.push(arr)
										obj.optionclassknowledge.series[0].data[0].value.push(res.result.listVO[i].divideClass);
										obj.optionclassknowledge.series[0].data[1].value.push(res.result.listVO[i].divideSchool);
										obj.optionclassknowledge.series[0].data[2].value.push(res.result.listVO[i].divideAera);
									}
									obj.optionclassknowledgeWord=res.result.summaryVO.knowledgeAnalysis;
									obj.echarts.init(document.getElementById("classknowledge1")).setOption(obj.optionclassknowledge);
								}else{
									document.getElementById("classknowledge1").style.display="none";
								}
							}
						}else{
							document.getElementById("classknowledge1").style.display="none";
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					})
				},
				ablityAnalysis:function(){
					this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,classroomId:this.basicData.class},'/ablityAnalysis',function(obj,res){
						if(res.code == '10000'){
							obj.scoreName=res.result.listVO;
							obj.optionclassabilityAnalyze.series[0].data[0].value=[];
							obj.optionclassabilityAnalyze.series[0].data[1].value=[];
							obj.optionclassabilityAnalyze.series[0].data[2].value=[];
							for(var i=0;i<res.result.listVO.length;i++){
								obj.optionclassabilityAnalyze.radar[0].indicator[i].text=res.result.listVO[i].ablityName;
								obj.optionclassabilityAnalyze.series[0].data[0].value.push(res.result.listVO[i].divideClass)
								obj.optionclassabilityAnalyze.series[0].data[1].value.push(res.result.listVO[i].divideSchool)
								obj.optionclassabilityAnalyze.series[0].data[2].value.push(res.result.listVO[i].divideAera)
							}
							obj.optionclassabilityAnalyzeWord=res.result.summaryVO.abilityAnalysis;
							obj.echarts.init(document.getElementById("classabilityAnalyze1")).setOption(obj.optionclassabilityAnalyze);
						}else{
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					})
				},
				getLevelDistribution:function(){
					this.postHttp(this,{examId:this.basicData.id,tab:'CLASS_REPORT', classroomId:this.basicData.class,subject:this.basicData.subject,rateType:''},'score/getLevelDistribution',function(obj,res){
						if(res.code == '10000'){
							var type=(typeof res.result);
							if(type=="string"){
								obj.optiongradeDistribution.series[0].data=[];
								obj.optiongradeDistribution.series[1].data=[];
								obj.optiongradeDistribution.series[2].data=[];
								obj.echarts.init(document.getElementById("gradeDistribution1")).setOption(obj.optiongradeDistribution);
							}else{
								if(obj.basicData.subject=="总分"){
									if(res.result.scoreVOList[0].scoreLevel=="SCHOOL"){
										obj.optiongradeDistribution.series[1].data=[];
										obj.optiongradeDistribution.series[1].data.push(res.result.scoreVOList[0].highRate * 1000000/10000);
										obj.optiongradeDistribution.series[1].data.push(res.result.scoreVOList[0].excellentRate * 1000000/10000);
										obj.optiongradeDistribution.series[1].data.push(res.result.scoreVOList[0].commissionRate * 1000000/10000);
										obj.optiongradeDistribution.series[1].data.push(res.result.scoreVOList[0].passRate * 1000000/10000);
										obj.optiongradeDistribution.series[1].data.push(res.result.scoreVOList[0].failureRate * 1000000/10000);

										obj.optiongradeDistribution.series[2].data=[];
										obj.optiongradeDistribution.series[2].data.push(res.result.scoreVOList[0].highRate * 1000000/10000);
										obj.optiongradeDistribution.series[2].data.push(res.result.scoreVOList[0].excellentRate * 1000000/10000);
										obj.optiongradeDistribution.series[2].data.push(res.result.scoreVOList[0].commissionRate * 1000000/10000);
										obj.optiongradeDistribution.series[2].data.push(res.result.scoreVOList[0].passRate * 1000000/10000);
										obj.optiongradeDistribution.series[2].data.push(res.result.scoreVOList[0].failureRate * 1000000/10000);

										obj.optiongradeDistributionWord=res.result.summaryVO.scoreLevelDistr;
									}
									if(res.result.scoreVOList[1].scoreLevel=="CLASS"){
										obj.optiongradeDistribution.series[0].data=[];
										obj.optiongradeDistribution.series[0].data.push(res.result.scoreVOList[1].highRate * 1000000/10000);
										obj.optiongradeDistribution.series[0].data.push(res.result.scoreVOList[1].excellentRate * 1000000/10000);
										obj.optiongradeDistribution.series[0].data.push(res.result.scoreVOList[1].commissionRate * 1000000/10000);
										obj.optiongradeDistribution.series[0].data.push(res.result.scoreVOList[1].passRate * 1000000/10000);
										obj.optiongradeDistribution.series[0].data.push(res.result.scoreVOList[1].failureRate * 1000000/10000);
									}
									obj.echarts.init(document.getElementById("gradeDistribution1")).setOption(obj.optiongradeDistribution);
								}else{
									if(res.result.scoreVOList){
										var num=0;
										for(var i=0;i<res.result.scoreVOList.length;i++){
											if(res.result.scoreVOList[i].subject==obj.basicData.subject){
												num=i;
											}
										}
										obj.studentTotle=res.result.scoreVOList[num].fullMarks;
										obj.studentTotleNum=res.result.stuNum;
										obj.optionstudentGradeDistribution.series[0].data[0].value=res.result.scoreVOList[num].highRate;
										obj.optionstudentGradeDistribution.series[0].data[1].value=res.result.scoreVOList[num].excellentRate;
										obj.optionstudentGradeDistribution.series[0].data[2].value=res.result.scoreVOList[num].commissionRate;
										obj.optionstudentGradeDistribution.series[0].data[3].value=res.result.scoreVOList[num].passRate;
										obj.optionstudentGradeDistribution.series[0].data[4].value=res.result.scoreVOList[num].failureRate;
										obj.echarts.init(document.getElementById("studentGradeDistribution1")).setOption(obj.optionstudentGradeDistribution);
										obj.studentGradeDistributionList=res.result.stuScoreList;
										obj.studentGradeheader.startScore=res.result.startScore;
										obj.studentGradeheader.endScore=res.result.endScore;
										obj.studentGradeheader.number=res.result.stuScoreList.length;
									}
								}
							}
						}else{
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					});
				},
				testAnalysis:function(){
					this.postHttp(this,{subject:this.basicData.subject,examId:this.basicData.id,classroomId:this.basicData.class},'/testAnalysis',function(obj,res){
						if(res.code == '10000'){
							obj.testAnalysisTable=res.result.listVO;
							obj.optionClassScoreQuestion.xAxis[0].data=[];
							obj.optionClassScoreQuestion.series[0].data=[];
							obj.optionClassScoreQuestion.series[1].data=[];
							obj.optionClassScoreQuestion.series[2].data=[];
							obj.optionTwoDimensionalAnalysisS.series[0].data=[];
							obj.optionTwoDimensionalAnalysisS.series[1].data=[];

							for(var i=0;i<res.result.listVO.length;i++){
								obj.optionClassScoreQuestion.xAxis[0].data.push(res.result.listVO[i].qid);
								obj.optionClassScoreQuestion.series[0].data.push(parseFloat(res.result.listVO[i].divideClass));
								obj.optionClassScoreQuestion.series[1].data.push(parseFloat(res.result.listVO[i].divideSchool));
								obj.optionClassScoreQuestion.series[2].data.push(parseFloat(res.result.listVO[i].divideAera));
								var number2=parseFloat(res.result.listVO[i].divideClass)-parseFloat(res.result.listVO[i].divideSchool);
								var number1=parseInt(number2*100);
								var number=number1/100;
								if(number>=0){
									var list=[];
									list.push(res.result.listVO[i].difficulty);
									list.push(number);
									list.push(res.result.listVO[i].topic);
									list.push(parseFloat(res.result.listVO[i].fractionalValue));
									list.push(parseFloat(res.result.listVO[i].qid));
									obj.optionTwoDimensionalAnalysisS.series[0].data.push(list);
								}else{
									var list1=[];
									list1.push(res.result.listVO[i].difficulty);
									list1.push(number);
									list1.push(res.result.listVO[i].topic);
									list1.push(parseFloat(res.result.listVO[i].fractionalValue));
									list1.push(parseFloat(res.result.listVO[i].qid));
									obj.optionTwoDimensionalAnalysisS.series[1].data.push(list1);
								}
							}
							obj.optionClassScoreQuestionWord=res.result.summaryVO.difficultyAnalysis;						
							obj.echarts.init(document.getElementById("classOptionScoreQuestion1")).setOption(obj.optionClassScoreQuestion);
							obj.echarts.init(document.getElementById("classtwoDimensionalAnalysis1")).setOption(obj.optionTwoDimensionalAnalysisS);
						}else{
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					});
				},
				getTopOrgScoresInClass:function(){
					this.postHttp(this,{examId:this.basicData.id,classroomId:this.basicData.class,topNum:this.basicData.topNum},'score/getTopOrgScoresInClass',function(obj,res){
						if(res.code == '10000'){
							obj.testAnalysisTable=res.result.listVO;
							obj.optionClassScoreQuestion.xAxis[0].data=[];
							obj.opti
							var type=(typeof res.result);
							if(type=="string"){
								obj.optionranking.series[0].data=[];
								obj.rankingData=[];
								obj.rankingList=[];
							}else{
								obj.rankingList=res.result.resultScoreList;
								obj.rankingListS.classStuNum=res.result.classStuNum;
								obj.rankingListS.schoolStuNum=res.result.schoolStuNum;
								obj.rankingData[0].topic10=res.result.classTopScore.classTopTenStuNum;
								obj.rankingData[0].topic20=res.result.classTopScore.classTopTwentyStuNum;
								obj.rankingData[0].topic50=res.result.classTopScore.classTopFiftyStuNum;
								obj.rankingData[0].topic100=res.result.classTopScore.classTopOneHundredStuNum;
								obj.rankingData[0].topic200=res.result.classTopScore.classTopTwoHundredStuNum;
								obj.rankingData[0].topic500=res.result.classTopScore.classTopFiveHundredStuNum;
								obj.rankingData[0].topic1000=res.result.classTopScore.classTopOneThousandStuNum;
								obj.optionranking.series[0].data=[];
								obj.optionranking.series[0].data.push(res.result.classTopScore.classTopTenStuNum);
								obj.optionranking.series[0].data.push(res.result.classTopScore.classTopTwentyStuNum);
								obj.optionranking.series[0].data.push(res.result.classTopScore.classTopFiftyStuNum);
								obj.optionranking.series[0].data.push(res.result.classTopScore.classTopOneHundredStuNum);
								obj.optionranking.series[0].data.push(res.result.classTopScore.classTopTwoHundredStuNum);
								obj.optionranking.series[0].data.push(res.result.classTopScore.classTopFiveHundredStuNum);
								obj.optionranking.series[0].data.push(res.result.classTopScore.classTopOneThousandStuNum);
							}
							obj.echarts.init(document.getElementById("ranking1")).setOption(obj.optionranking);
						}else{
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					});
				},
				getClassRankingReport:function(){
					this.postHttp(this,{examId:this.basicData.id,classroomId:this.basicData.class},'score/getClassRankingReport',function(obj,res){
						if(res.code == '10000'){
							obj.tableData3=res.result.classScoreList;
						}else{
							obj.notify_jr(obj,'错误提示',res.message,'error');
						}
					});
				},
				gradeIntervalfun:function(e){
					var num=parseInt(e.currentTarget.getElementsByTagName("button")[0].getAttribute("data-key"));
					if(e.currentTarget.className=="ranking-footRight"){
						if(num==5){
							e.currentTarget.parentNode.getElementsByClassName("ranking-footLeft")[0].getElementsByTagName("button")[0].setAttribute("data-key",num-1);
							e.currentTarget.parentNode.getElementsByClassName("ranking-footLeft")[0].getElementsByTagName("button")[0].getElementsByTagName("span")[0].innerHTML=this.gradeInterval[num-2].name;
							e.currentTarget.parentNode.getElementsByClassName("ranking-footRight")[0].style.display="none";
							this.studentGradeheader.name=this.gradeInterval[num-1].name;
							this.studentGradeheader.level=this.gradeInterval[num-1].level;
							this.basicData.rateType=this.gradeInterval[4].value;
							this.postHttp(this,{examId:this.basicData.id,tab:'CLASS_REPORT',classroomId:this.basicData.class, subject:this.basicData.subject,rateType:this.gradeInterval[4].value},'score/getLevelDistribution',function(obj,res){
								if(res.code == '10000'){
									obj.studentGradeDistributionList=res.result.stuScoreList;
									obj.studentGradeheader.startScore=res.result.startScore;
									obj.studentGradeheader.endScore=res.result.endScore;
									obj.studentGradeheader.number=res.result.stuScoreList.length;
								}else{
									obj.notify_jr(obj,'错误提示',res.message,'error');
								}
							});
						}else{
							e.currentTarget.parentNode.getElementsByClassName("ranking-footLeft")[0].style.display="block";
							e.currentTarget.parentNode.getElementsByClassName("ranking-footLeft")[0].getElementsByTagName("button")[0].setAttribute("data-key",num-1);
							e.currentTarget.parentNode.getElementsByClassName("ranking-footLeft")[0].getElementsByTagName("button")[0].getElementsByTagName("span")[0].innerHTML=this.gradeInterval[num-2].name;
							e.currentTarget.parentNode.getElementsByClassName("ranking-footRight")[0].getElementsByTagName("button")[0].setAttribute("data-key",num+1);
							e.currentTarget.parentNode.getElementsByClassName("ranking-footRight")[0].getElementsByTagName("button")[0].getElementsByTagName("span")[0].innerHTML=this.gradeInterval[num].name;
							this.studentGradeheader.name=this.gradeInterval[num-1].name;
							this.studentGradeheader.level=this.gradeInterval[num-1].level;
							this.basicData.rateType=this.gradeInterval[num-1].value;
							this.postHttp(this,{examId:this.basicData.id,tab:'CLASS_REPORT',classroomId:this.basicData.class,subject:this.basicData.subject,rateType:this.gradeInterval[num-1].value},'score/getLevelDistribution',function(obj,res){
								if(res.code == '10000'){
									obj.studentGradeDistributionList=res.result.stuScoreList;
									obj.studentGradeheader.startScore=res.result.startScore;
									obj.studentGradeheader.endScore=res.result.endScore;
									obj.studentGradeheader.number=res.result.stuScoreList.length;
								}else{
									obj.notify_jr(obj,'错误提示',res.message,'error');
								}
							});
						}
					}else{
						if(num==1){
							e.currentTarget.parentNode.getElementsByClassName("ranking-footRight")[0].getElementsByTagName("button")[0].setAttribute("data-key",num+1);
							e.currentTarget.parentNode.getElementsByClassName("ranking-footRight")[0].getElementsByTagName("button")[0].getElementsByTagName("span")[0].innerHTML=this.gradeInterval[num].name;
							e.currentTarget.parentNode.getElementsByClassName("ranking-footLeft")[0].style.display="none";
							this.studentGradeheader.name=this.gradeInterval[num-1].name;
							this.studentGradeheader.level=this.gradeInterval[num-1].level;
							this.basicData.rateType=this.gradeInterval[0].value;
							this.postHttp(this,{examId:this.basicData.id,tab:'CLASS_REPORT', classroomId:this.basicData.class,subject:this.basicData.subject,rateType:this.gradeInterval[0].value},'score/getLevelDistribution',function(obj,res){
								if(res.code == '10000'){
									obj.studentGradeDistributionList=res.result.stuScoreList;
									obj.studentGradeheader.startScore=res.result.startScore;
									obj.studentGradeheader.endScore=res.result.endScore;
									obj.studentGradeheader.number=res.result.stuScoreList.length;
								}else{
									obj.notify_jr(obj,'错误提示',res.message,'error');
								}
							});
						}else{
							e.currentTarget.parentNode.getElementsByClassName("ranking-footRight")[0].style.display="block";
							e.currentTarget.parentNode.getElementsByClassName("ranking-footLeft")[0].getElementsByTagName("button")[0].setAttribute("data-key",num-1);
							e.currentTarget.parentNode.getElementsByClassName("ranking-footLeft")[0].getElementsByTagName("button")[0].getElementsByTagName("span")[0].innerHTML=this.gradeInterval[num-2].name;
							e.currentTarget.parentNode.getElementsByClassName("ranking-footRight")[0].getElementsByTagName("button")[0].setAttribute("data-key",num+1);
							e.currentTarget.parentNode.getElementsByClassName("ranking-footRight")[0].getElementsByTagName("button")[0].getElementsByTagName("span")[0].innerHTML=this.gradeInterval[num].name;
							this.studentGradeheader.name=this.gradeInterval[num-1].name;
							this.studentGradeheader.level=this.gradeInterval[num-1].level;
							this.basicData.rateType=this.gradeInterval[num-1].value;
							this.postHttp(this,{examId:this.basicData.id,tab:'CLASS_REPORT',classroomId:this.basicData.class,subject:this.basicData.subject,rateType:this.gradeInterval[num-1].value},'score/getLevelDistribution',function(obj,res){
								if(res.code == '10000'){
									obj.studentGradeDistributionList=res.result.stuScoreList;
									obj.studentGradeheader.startScore=res.result.startScore;
									obj.studentGradeheader.endScore=res.result.endScore;
									obj.studentGradeheader.number=res.result.stuScoreList.length;
								}else{
									obj.notify_jr(obj,'错误提示',res.message,'error');
								}
							});
						}
					}
				},
				classLastScoreshow(){
					var selfs = this;
					if(this.diaLoading){
						setTimeout(function(){
							selfs.diaLoading = false;
							selfs.postHttp(selfs,{examId:selfs.basicData.id,classroomName:selfs.basicData.className,tab:'CLASS_REPORT', subject:selfs.basicData.subject},'score/compareExamScores',function(obj,res){
								if(res.code == '10000'){
									obj.optionclassLastScore.xAxis.data=[];
									obj.optionclassLastScore.xAxis.data=res.result.examNameList;
									obj.optionclassLastScore.series[0].data=[];
									obj.optionclassLastScore.series[0].data=res.result.scoreAvgList;

									obj.echarts.init(document.getElementById("classLastScore")).setOption(obj.optionclassLastScore);
								}else{
									obj.notify_jr(obj,'错误提示',res.message,'error');
								}
							})
						},1000);
					}else{
						selfs.postHttp(selfs,{examId:selfs.basicData.id,classroomName:selfs.basicData.className,tab:'CLASS_REPORT', subject:selfs.basicData.subject},'score/compareExamScores',function(obj,res){
							if(res.code == '10000'){
								obj.optionclassLastScore.xAxis.data=[];
								obj.optionclassLastScore.xAxis.data=res.result.examNameList;
								obj.optionclassLastScore.series[0].data=[];
								obj.optionclassLastScore.series[0].data=res.result.scoreAvgList;

								obj.echarts.init(document.getElementById("classLastScore")).setOption(obj.optionclassLastScore);
							}else{
								obj.notify_jr(obj,'错误提示',res.message,'error');
							}
						})
					}
				},
				gradeDistributionshow(){
					this.echarts.init(document.getElementById("gradeDistribution1")).setOption(this.optiongradeDistribution);
				},
				classLastStudentsshow(){
					var selfs = this;
					if(this.diaStudentsLoading){
						setTimeout(function(){
							selfs.diaStudentsLoading = false;

							selfs.echarts.init(document.getElementById("classLastStudents")).setOption(selfs.optionclassLastStudents);
						},1000);
					}else{
						selfs.echarts.init(document.getElementById("classLastStudents")).setOption(selfs.optionclassLastStudents);
					}
				},
				rankingTopic(e){
					if(e.currentTarget.className=="ranking-footLeft"){
						this.basicData.topNum=50;
						this.getTopOrgScoresInClass();
					}else{
						this.basicData.topNum=100;
						this.getTopOrgScoresInClass();
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
			}
		}
		</script>
		<style>
		#classLevel{
			border-top: 1px solid #f2f2f2;
		}
		#classLevel .borders{
			box-shadow: 1px 1px 14px rgba(0,0,0,.15);
		}
		#classLevel .header{
			text-align: center;
		}
		#classLevel .header p{
			font-size: 20px;
			color: #707070;
			letter-spacing: 0;
		}
		#classLevel .header-title-foot{
			width: 100px;
			height: 2px;
			background: #44A9FF;
			margin: auto;
			margin-top:5px;
		}
		#classLevel .header-banner-bit{
			width: 89px;
			height: 73px;
			float: left;
			margin-top: 15px;
		}
		#classLevel .table-header{
			background: #70CDF3;
			color: #fff;
		}
		#classLevel .header-banner .el-carousel__item{
			margin-left: 60px;
		}
		#classLevel .header-banner-click{
			width: 100%;
			height: 100%;
			text-align: center;
		}
		#classLevel .header-banner-click p{
			line-height: 73px;
		}
		#classLevel .header-banner-bit .on{
			height: 105%;
		}
		#classLevel .schoolSelectBox{
			width: 90px;
			margin: auto;
			margin-bottom: 35px;
		}
		#classLevel .table-header{
			background: #70CDF3;
			color: #fff;
		}
		#classLevel .body-foot{
			margin-top:20px;
		}
		#classLevel .body-foot p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
			line-height: 24px;
		}
		#classLevel .body-foot-button{
			width: 100px;
			height: 40px;
			margin: auto;
			margin-top: 10px;
		}
		#classLevel .body-foot-button .el-button{
			border: 1px solid #44A9FF;
		}
		#classLevel .body-foot-button span{
			color: #44A9FF !important;
		}
		#classLevel .el-input__inner{
			border: 1px solid #44A9FF;
		}
		#classLevel  #gradeDistribution1{
			width: 1080px;
			height: 460px;
			margin: auto;
			margin-top: 20px;
		}
		#classLevel .classSelectBox{
			width: 350px;
			margin: auto;
			margin-bottom: 35px;
			margin-top: 10px;
		}
		#classLevel .vclassSelectBox{
			width: 125px !important;
		}
		#classLevel .header .body-title{
			position: absolute;
			left: 50%;
			margin-left: -100px;
		} 
		#classLevel .header .body-title p{
			font-size: 16px;
			color: #707070;
			letter-spacing: 0;
		} 
		#classLevel #ranking .body{
			overflow: hidden;
		}
		#classLevel #ranking1{
			width: 750px;
			height: 460px;
			float: left;
		}
		#classLevel #ranking2{
			width: 300px;
			height: 395px;
			float: left;
			margin-top: 55px;
		}
		#classLevel .ranking-header{
			width: 100%;
			height: 40px;
			background: #70CDF3;
			text-align: center;
			line-height: 40px;
			border-radius: 3px 3px 0 0;
		}
		#classLevel .ranking-header p{
			font-size: 16px;
			color: #FFFFFF;
			letter-spacing: 0;
		}
		#classLevel .ranking-body{
			width: 100%;
			height: 285px;
			border: 1px solid #E6EBF5;
			overflow: auto;
		}
		#classLevel .ranking-body p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
		}
		#classLevel .ranking-body ul{
			padding: 20px 80px;
		}
		#classLevel .ranking-foot{
			width: 100%;
			height: 40px;
			background: #70CDF3;
			line-height: 40px;
			border-radius:0 0 3px 3px;
		}
		#classLevel .ranking-footLeft{
			float: left;
			color: #fff;
			cursor:pointer;
		}
		#classLevel .ranking-footLeft i{
			line-height: 40px;
			float: left;
		}
		#classLevel .ranking-footLeft p{
			float: left;
		}
		#classLevel .ranking-footRight{
			float: right;	
			color: #fff;
			cursor:pointer;
		}
		#classLevel .ranking-footRight i{
			line-height: 40px;
			float: right;	
		}
		#classLevel .ranking-footRight p{
			float: right;	
		}
		#classLevel .ranking-bodyName{
			float: left;
		}
		#classLevel .ranking-bodyNum{
			float: right;
		}
		#classLevel #ClassdisciplinesLevel1{
			width: 600px;
			height: 500px;
			margin-top:20px;
			float: left;
		}
		#classLevel #ClassdisciplinesLevel2{
			width: 470px;
			height: 500px;
			float: left;
			margin-top:20px;
			margin-left: 20px;
		}
		#classLevel .subjectsDiagnosisText{
			margin-top:20px;
		}
		#classLevel .subjectsDiagnosisText p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
			line-height: 24px;
		}
		#classLevel .body-Vtitle{
			text-align: center;
		}
		#classLevel .body-Vtitle p{
			font-size: 16px;
			color: #707070;
			letter-spacing: 0;
			line-height: 26px;
		}
		#classLevel #studentGradeDistribution1{
			width: 700px;
			height: 460px;
		}
		#classLevel #studentGradeDistribution2{
			width: 305px;
			height: 460px;
		}
		#classLevel .studentGradeDistribution2-header{
			width: 100%;
			height: 49px;
			background: #70CDF3;
			border-radius: 3px 3px 0 0;
			text-align: center;
			padding-top: 5px;
		}
		#classLevel .studentGradeDistribution2-header p{
			font-size: 16px;
			color: #FFFFFF;
			letter-spacing: 0;
			line-height: 24px;
		}
		#classLevel .studentGradeDistribution2-body{
			width: 99%;
			height: 285px;
			border: 1px solid #E6EBF5;
		}
		#classLevel .el-button span{
			color: #fff;
		}
		#classLevel .studentGradeDistribution-ranking-body{
			width: 100%;
			height: 285px;
			border: 1px solid #E6EBF5;
			overflow: auto;
		}
		#classLevel .studentGradeDistribution-ranking-body p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
		}
		#classLevel .studentGradeDistribution-ranking-body ul{
			padding: 20px 80px;
		}
		#classLevel .classSelectBox .el-button{
			background: #fff;
		}
		#classLevel .classSelectBox .el-button span{
			color: #44A9FF !important;
		}
		#classLevel #classTestAnalysis .el-table--border td, .el-table--border th{
			border:0px !important;
		}
		#classLevel #classTestAnalysis .el-table thead.is-group th{
			background: #70CDF3;
			color: #fff;
		}
		#classLevel #classOptionScoreQuestion1{
			width: 1080px;
			height: 460px;
			margin: auto;
			margin-top: 20px;
			margin-bottom: 20px;
		}
		#classLevel #classtwoDimensionalAnalysis1{
			width: 1080px;
			height: 660px;
			margin: auto;
			margin-bottom: 20px;
		}
		#classLevel .twoDimensionalAnalysis-foot{
			width: 870px;
			height: 30px;
			margin: auto;
			margin-top: -45px;
		}
		#classLevel .twoDimensionalAnalysis-foot .difficultyLevel{
			width: 33.33%;
			height: 100%;
			float:left;
			text-align: center;
			color: #fff;
		}
		#classLevel .twoDimensionalAnalysis-foot .easy{
			background: #FF4444;
		}
		#classLevel .twoDimensionalAnalysis-foot .midde{
			background: #FF8585;
		}
		#classLevel .twoDimensionalAnalysis-foot .difficult{
			background: #FEB0B0;
		}
		#classLevel .twoDimensionalAnalysis-foot .difficultyLevel p{
			line-height: 35px;
			font-size: 12px;
			letter-spacing: 0;
		}
		#classLevel #classtwoDimensionalAnalysis .foot-word{
			width: 100%;
			margin-top:20px;
		}
		#classLevel #classtwoDimensionalAnalysis .foot-word p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
			line-height: 24px;
		}
		#classLevel #classknowledge1{
			width: 1080px;
			height: 450px;
			margin: auto;
		}
		#classLevel .el-select .el-input .el-input__icon{
			color: #44A9FF
		}
		#classLevel .el-carousel__arrow{
			background-color: white;
			color: #44A9FF
		}
		#classLevel .el-carousel__arrow:hover{
			background-color: white
		}
		#classLevel #classknowledge2 .el-table thead.is-group th{
			background: #70CDF3;
			color: #fff;
		}
		#classLevel #knowledge-table-header .el-table__body-wrapper{
			display: none;
		}
		#classLevel #classknowledge2 .knowledge-table-true .el-table__header-wrapper{
			display: none;
		}
		#classLevel .knowledge-table-trueTotle{
			width: 100%;
			height: 50px;
			background: #FF8585;	
		}
		#classLevel .knowledge-table-trueTotle .knowledge-table-trueTotleBit{
			line-height: 50px;
			float: left;
			width: 160px;
			color: #fff;
			text-align: center;
		}
		#classLevel .knowledge-foot{
			margin-top:20px;
		}
		#classLevel .knowledge-foot p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
			line-height: 24px;
		}
		#classLevel #classabilityAnalyze1{
			width: 1080px;
			height: 520px;
			margin: auto;
			margin-top: 20px;
		}
		#classLevel #classabilityAnalyze2 .el-table--border td, .el-table--border th{
			border:0px !important;
		}
		#classLevel #classabilityAnalyze2 .el-table thead.is-group th{
			background: #70CDF3;
			color: #fff;
		}
		#classLevel .abilityAnalyze-foot{
			margin-top: 20px;
		}
		#classLevel .abilityAnalyze-foot p{
			font-size: 14px;
			color: #3D3D3D;
			letter-spacing: 0;
			line-height: 24px;
		}
		#classLastScore{
			width: 800px;
			height: 500px;
		}
		#classLastStudents{
			width: 800px;
			height: 500px;	
		}
		#classLevel .el-select .el-input .el-input__icon{
			color: #70CDF3
		}
		#classLevel .el-carousel  {
			overflow: hidden;
			position: relative;
			text-align: center;
			margin: auto;
			width: 400px;
			height: 50px;
			margin-top: 30px;
			line-height: 50px;
		}
		#classLevel #rainbow .el-carousel{
			width: 1100px;
			height: 100px;
		}
		#classLevel .el-carousel__container {
			position: relative;
			height: 50px;
			margin: auto;
		}
		#classLevel .myselect{
			border:1px solid #44a9ff;
			width: 130px;
			border-radius: 4px;
		}
		#classLevel .myselectS{
			border:1px solid #44a9ff;
			width: 130px;
			border-radius: 4px;
		}
		#classLevel .testTips{
			color:#3d3d3d;
			font-size: 14px;
			margin-top: 25px
		}
		#classLevel .schoolSelectBox{
			width: 200px;
			margin: auto;
			margin-bottom: 35px;
		}
		#classLevel .achievementSelectBox{
			width: 90px;
			margin: auto;
			margin-bottom: 35px;
		}
		#classLevel .borders{
			box-shadow: 1px 1px 14px rgba(0,0,0,.15);
		}
		#classLevel .el-table .tableBackground{
			background-color: #f5fcff;text-align: center;
		}
		#classLevel .el-table .tableCenter{
			text-align: center;
		}
		#classLevel .alltest{
			cursor: pointer;
		}
		#classLevel .formatRow{
			font-weight: normal;
			text-align:center !important;
			color: white;
			border: 0px !important;
			background-color: #70CDF3 !important;
		}
		#achievementTable .el-table--border td{
			border:0px;
		}
		#classLevel .el-input__inner{
			border: 0px;
			text-align: center;
		}
		#classLevel .el-select .el-input .el-input__icon{
			color: #70CDF3
		}
		#classLevel .el-carousel__arrow{
			background-color: white;
			color: #70CDF3
		}
		#classLevel .el-carousel__arrow:hover{
			background-color: white
		}
		#classLevel .showselect{
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
		#classLevel .showselect li{
			margin: 0;padding:0;
			border-bottom: 1px solid #f2f2f2;
			padding:10px 20px;
			cursor: pointer;
		}
		#classLevel .alltest{
			cursor: pointer;
		}
		.leftAnchorPoint{
			width: 40px;
			height: 80px;
		}
		.hand{
			cursor:pointer;
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
		</style>
