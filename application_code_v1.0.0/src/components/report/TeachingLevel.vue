<template>
	<div id="teachLevel" class="mainbody">
		<ul id="navInside" >
			<li v-for="item in liList" :class="activeList == item.id? 'active': ''" :key="item.id" @click="testtest(item.id)">{{item.name}}</li>
		</ul>
		  <el-carousel :interval="5000" ref="carousel" @change="testChange($event)" indicator-position="none" arrow="always" :autoplay="false">
		    <el-carousel-item v-for="item in testList" :key="item.id" :name="item.examName" >
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
			     	<div class="header-banner-bit" v-for="(child,index) in item.childs">
			     		<div :class="index == indes? 'header-banner-click on': 'header-banner-click'"  :style="'background:'+child.subjectColor" @click="rainbow(index,child.id,child.subjectName)">
			     			<p>{{child.subjectName}}</p>
			     		</div>
			     	</div>
			    </el-carousel-item>
			</el-carousel>
		</div>

		  <div class="header louceng">
			<p>科目报告单</p>
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
		      	<el-table-column width="80" prop="schoolMinScore" :formatter='setParse' label="最低分">
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
		    <p style="text-align:center;margin-top:20px"><el-button type="primary" @click="compareTest" plain>考试对比</el-button></p>
		  </div>
			<!-- 考试对比图 -->
			<el-dialog
			  title="考试对比图"
			  :visible.sync="dialogVisible"
			  @open="alertas" @close="dialogClose">
			  	<center v-show="diaLoading" style="margin-top:20%;"><i class="el-icon-loading"></i></center>
			  	<div id="compareTestChart">
			  	</div>
		  	  <span slot="footer" class="dialog-footer">
			    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
			  </span>
			</el-dialog>
			<!-- 考试对比图 -->
		  <div class="header">
			<p>学生等级分布图</p>
			<div class="header-title-foot"></div>
		  </div>
		  <div id="ranked">
		  		<div id="rankedchart"></div>
		  		<!-- 产品板暂不考虑<div class="schoolSelectBox">
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
		   <div class="header louceng">
			<p>各班级科目报告单</p>
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
		      <el-table-column prop="classAvgScore" label="平均分(班级)">
		      </el-table-column>
		      <el-table-column prop="schoolAvgScore" label="平均分(校级)">
		      </el-table-column>
		      <el-table-column width="60" prop="classTopScore" label="最高分">
		      </el-table-column>
		      <el-table-column width="60" prop="classMinScore" label="最低分">
		      </el-table-column>
		      <el-table-column prop="classSubjectStandardDeviation" label="标准差">
		      </el-table-column>
		      <el-table-column prop="classSubjectDiffCoefficient" label="分化程度(前三名提醒)">
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
		    <p class="testTips">{{eachClassSubjScore}}</p>
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
		  		<!-- <p class="testTips">本次考试中，前10名最多的班级是8班和7班；前20名最多的班级的是8班、10班和11班；前50名最多的班级是10班、12班和14班。</p> -->
		  </div>
		  <div class="header louceng">
			<p>试卷分析</p>
			<div class="header-title-foot"></div>
		  </div>
		  <div id="achievement">
		  	<p style="text-align:center;font-size:14px">本次试卷整体难度为：0.7，整体区分度为0.8</p>
			<p style="font-size:14px;margin-bottom:30px">注释：<br>
D值为0.4以上表明区分度优秀； D值为0.3~0.39表明区分度合格；D值为0.2~0.29表明区分度尚可，需要改进；D值在0.19以下表明区分度劣，必须淘汰或改进以提高区分度。</p>
		  	<div id="achievementTable">
			  	<el-table :data="examination" style="width: 100%" class="borders" header-cell-class-name="formatRow" :row-class-name="rowsClassName">
    					<el-table-column  prop="qid" label="题号">
    					</el-table-column>
    					<el-table-column prop="topic" label="题型">
    					</el-table-column>
    					<el-table-column  prop="fractionalValue" label="分值">
    					</el-table-column>
    					<el-table-column prop="divide" label="平均分">
    					</el-table-column>
    					<el-table-column label="得分率">
      						<el-table-column prop="divideSchool" label="校级"></el-table-column>
      						<el-table-column  prop="divideAera" label="地区级"></el-table-column>
    					</el-table-column>
    					<el-table-column prop="trueNum" label="满分人数"></el-table-column>
    					<el-table-column prop="falseNum" label="答错人数"></el-table-column>
    					<el-table-column prop="difficulty" label="难度"></el-table-column>
    					<el-table-column prop="differentiation" label="区分度"></el-table-column>
  					</el-table>
		  	</div>
		  	<!-- 产品版暂时不加<div id="twoDimensionalAnalysis">
		  				<div class="header">
		  					<p>得分率 -- 难度二维分析</p>
		  					<div class="header-title-foot"></div>
		  				</div>
		  				<div class="body">
		  					<div class="body-icon">
		  						<div class="body-icon-bit">
		  							<div class="body-icon-color color-blue" @click="twoDimensionalAnalysisChoose('class')"></div>
		  							<p>班级</p>
		  						</div>
		  						<div class="body-icon-bit">
		  							<div class="body-icon-color color-lightGrey" @click="twoDimensionalAnalysisChoose('school')"></div>
		  							<p>校级</p>
		  						</div>
		  						<div class="body-icon-bit">
		  							<div class="body-icon-color color-darkGrey" @click="twoDimensionalAnalysisChoose('area')"></div>
		  							<p>全区县</p>
		  						</div>
		  					</div>
		  					<div id="twoDimensionalAnalysis1"></div>
		  					<div class="twoDimensionalAnalysis-foot">
		  						<div class="difficultyLevel easy">
		  							<p>容易</p>
		  						</div>
		  						<div class="difficultyLevel midde">
		  							<p>中等</p>
		  						</div>
		  						<div class="difficultyLevel difficult">
		  							<p>难</p>
		  						</div>
		  					</div>
		  					<div class="foot-word">
		  						<p>在本次考试中，得分率低于学校平均水平的题目分别是第4题、第8题、第12题、第16题和第20题，其中中等难度的题目为第4题和第12题，这些题目需要注意；其中简单难度的题目为第8题和第20题，需要特别注意。</p>
		  					</div>
		  				</div>
		  	</div> -->
		  </div>
	</div>	
</template>

<script>
import IndexData from '../../assets/data/teachingLevel.js'
export default {
	data(){
		return {
			testid:'',
			indes:0,
			activeList:0,
			liList:[{
				name:'科目报告单',
				id:0
			},{
				name:'各班级科目报告单',
				id:1
			},{
				name:'各班级等级分布对比',
				id:2
			},{
				name:'试卷分析',
				id:3
			}],
			subname:'',
			autoplay:false,
			alse:'二中',
			IndexData,
			testList:[],
			showselect:false,
			schoolTest:'',
			changeSchool:'',
			eachClassSubjScore:'',
			classesLevelDistri:'',
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
			classNumble:'',classS:'',classH:'',missdistance:'',classL:[],classLs:'',
			tableData3:[],option4:{},dialogVisible:false,option5:{},option6:{},
			subjects:[],subjAvgComparation:'',
			items:[],classroom:[],
			optionTwoDimensionalAnalysis:{},
			examination:[],
			diaLoading:true
		}
	},
	created:function(){
		
	},
    methods: {
    	//初始化信息
    	initAll:function(){
    		var needData = {tab:'TEACHING_REPORT'};
    		this.postHttp(this,needData,"exam/getExamListForTab",function(obj,data){
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
	        	return e*1000000/10000;
	        }else{
	        	return e;
	        }
    	},
    	testChange(e){
    		var needData = {tab:'TEACHING_REPORT',examId:this.testList[e].id};
    		this.testid = this.testList[e].id;
    		var ids = this.testList[e].id;
    		var es = e;
	    	this.postHttp(this,needData,"exam/getExamListForTab",function(obj,data){
	    		obj.classroom = data.result.exams[es].classroom.split(",");
	    		obj.items = [];
	    		obj.subjects = [];
	    		obj.indes = 0;
	    		obj.items = data.result[ids].subject;
	    		obj.subname = data.result[ids].subject[0].subjectName;
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
					obj.postHttp(obj,{tab:'TEACHING_REPORT',examId:obj.testid,subject:obj.subname},"score/geReportCards",function(objs,data){
							objs.tableData1 = [];objs.tableData2 = [];var avgList =[];var avgXAxis = [];
							if(data.result == "该考试尚未制定双向细目表"||data.code=="20000"){
								objs.tableData1 = [];obj.tableData2 = [];
								objs.option2.xAxis[0].data = objs.classroom;
				           		objs.option2.series[0].data = [];
				           		for(var a of obj.classroom){
					           		objs.option2.series[0].data.push(0);
					           		objs.option4.series[0].data.push(0);
					           }
					           obj.subjAvgComparation ='';
					           obj.eachClassSubjScore = '';
					           obj.option2.series[0].markLine.data[0].yAxis = 0;
					           obj.echarts.init(document.getElementById("averageChart")).setOption(obj.option2);
							}else{
									obj.subjAvgComparation = data.result.summaryVO.subjAvgComparation;
									obj.eachClassSubjScore = data.result.summaryVO.eachClassSubjScore;
									avgList  =[];avgXAxis = [];
									objs.tableData1.push(data.result.scoreVO);
									objs.tableData2=data.result.classScoreVOList;
							        objs.setmans = data.result.studentNum;
							        for(var a of data.result.classScoreVOList){
							        	avgList.push(a.classSubjectAvgScore);
							        	avgXAxis.push(a.classroomName);
							        }
							        obj.option2.series[0].markLine.data[0].yAxis = data.result.schoolAvgSubjectScore;
							        obj.option2.series[0].data = avgList;
							        obj.option2.series[0].data[0] = {value:avgList[0],itemStyle:{normal:{color:"#FF8585"}}}
									obj.option2.xAxis[0].data = avgXAxis;
							        objs.echarts.init(document.getElementById("averageChart")).setOption(objs.option2);
				    		}
			        });
					obj.postHttp(obj,{tab:'TEACHING_REPORT',examId:obj.testid,subject:obj.subname},"score/getLevelDistribution",function(objs,data){
			    	   		
			    	   		for(var a of data.result){
			    	   			if(a.subject==objs.subname){
			    	   				var data1 = [objs.initPrate(a.highRate),
			    	   					objs.initPrate(a.excellentRate),
			    	   					objs.initPrate(a.commissionRate),
			    	   					objs.initPrate(a.passRate),
			    	   					objs.initPrate(a.failureRate)];
			    	   					break
			    	   			}else{
			    	   				var data1 = [0,0,0,0,0]
			    	   			}
			    	   		}
							
			    	   	
			    	   objs.option1.series[0].data = data1;
			           objs.echarts.init(document.getElementById("rankedchart")).setOption(objs.option1);
			        });
			        obj.postHttp(obj,{subject:obj.subname,examId:obj.testid},"/testAnalysis",function(objs,data){
			           objs.examination = [];
			           objs.examination = data.result.listVO;
			        });
					obj.postHttp(obj,{tab:'TEACHING_REPORT',examId:obj.testid,subject:obj.subname},"score/getEachClassTopScores",function(objs,data){
		    		var datax = []; var data10 = []; var data20 = [];
		    		var data50 = []; var data100 = []; var data200 = [];
		    		var data500 = []; var data1000 = [];
		    		if(data.result=="没有最近一次考试的相关数据"){
		    			datax = [];data10 = [];data20 = [];data50 = [];
		    			data100 = [];data200 = [];data500 = [];data1000 = [];
						datax = objs.classroom;
						for(var a of objs.classroom){
			           		data10.push(0);
			           		data20.push(0);
			           		data50.push(0);
			           		data100.push(0);
			           		data200.push(0);
			           		data500.push(0);
			           		data1000.push(0);
			           }
		    		}else{
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
		    	   objs.option3.xAxis[0].data = datax;
		           objs.option3.series[0].data = data10;obj.option3.series[1].data = data20;
		           objs.option3.series[2].data = data50;obj.option3.series[3].data = data100;
		           objs.option3.series[4].data = data200;obj.option3.series[5].data = data500;
		           objs.option3.series[6].data = data1000;
		           objs.echarts.init(document.getElementById("topComparedChart")).setOption(objs.option3);
		        });
				obj.postHttp(obj,{tab:'TEACHING_REPORT',examId:obj.testid,subject:obj.subname},"score/getEachSubjectLevelDistribution",function(objs,data){
					var xAxisD = [];var highRates;var excellentRates;
					var commissionRates;var passRates;var failureRates;
					var xDatas = [];
		    	   	if(data.result=="没有最近一次考试的相关数据"){
		    	   		objs.option6.xAxis.data =[];
		    	   		objs.option6.xAxis.data=objs.classroom;
		    	   		objs.option6.xAxis.data.unshift("全校");
		    	   		for(var a of objs.classroom){
		    	   			xAxisD.push(0)
		    	   		}
		    	   		objs.classesLevelDistri = '';
		    	   		objs.option6.series[0].data = xAxisD;
		    	   		objs.option6.series[1].data = xAxisD;
		    	   		objs.option6.series[2].data = xAxisD;
		    	   		objs.option6.series[3].data = xAxisD;
		    	   		objs.option6.series[4].data = xAxisD;
		    	   		console.log(objs.option6);
						objs.echarts.init(document.getElementById("contrastiveChart")).setOption(objs.option6);
		    	   	}else{

						highRates=[];excellentRates=[];
		    	   		commissionRates=[];passRates=[];
		    	   		failureRates=[];objs.option6.xAxis.data =[];
		    	   		xDatas = [];
		    	   		for(var a of data.result.classScoreVOList){
		    	   			highRates.push(objs.initPrate(a.highRate));
		    	   			excellentRates.push(objs.initPrate(a.excellentRate));
		    	   			commissionRates.push(objs.initPrate(a.commissionRate));
		    	   			passRates.push(objs.initPrate(a.passRate));
		    	   			failureRates.push(objs.initPrate(a.failureRate));
		    	   			xDatas.push(a.classroomName);
						}
						objs.classesLevelDistri = data.result.summaryVO.classesLevelDistri;
						objs.option6.xAxis.data=xDatas;
						objs.option6.series[0].data = highRates;
		    	   		objs.option6.series[1].data = excellentRates;
		    	   		objs.option6.series[2].data = commissionRates;
		    	   		objs.option6.series[3].data = passRates;
		    	   		objs.option6.series[4].data = failureRates;
						objs.echarts.init(document.getElementById("contrastiveChart")).setOption(objs.option6);
		    	   	}
		        });
	        });
	        
	        
    	}, 
    	subReport:function(ename){
    		var needData = {tab:'TEACHING_REPORT'};
    		var names = ename;

    	},
    	selectShow:function(){
    		this.showselect = !this.showselect
    	},
    	changetest:function(e,ename){
    		this.showselect = !this.showselect;
    		this.$refs.carousel.setActiveItem(ename);
    	},
    	rowsClassName:function({row, rowIndex}){
	       if(rowIndex%2===1)
    			return 'tableBackground'
      		else 
      			return 'tableCenter'
    	},
    	compareTest:function(){
			this.dialogVisible = true;
			//console.log(document.getElementById("compareTestChart"));
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
    	alertas:function(){
    		var selfs = this;

    		if(this.diaLoading){
    			setTimeout(function(){
	    			selfs.diaLoading = false;
					selfs.postHttp(selfs,{tab:'TEACHING_REPORT',examId:selfs.testid},"score/compareExamScores",function(objs,data){
					    			objs.option5.series[0].data = [];
									objs.option5.series[0].data=data.result.scoreAvgList;
									objs.option5.xAxis[0].data = [];
									objs.option5.xAxis[0].data=data.result.examNameList;
									objs.echarts.init(document.getElementById("compareTestChart")).setOption(objs.option5);
					});
		    		
	    		},1000);
    		}
    		
    	},
    	dialogClose:function(){
  

    		//document.getElementById("compareTestChart").innerHTML = '';
    	},
    	rainbow:function(index,num,name){
			this.indes = index;
			var needDataR = {tab:'TEACHING_REPORT',examId:this.testid,subject:name};
			this.postHttp(this,needDataR,"score/geReportCards",function(objs,data){
					objs.tableData1 = [];objs.tableData2 = [];
					var avgList =[];var avgXAxis = [];
					if(data.result == "该考试尚未制定双向细目表"||data.code=="20000"){
						objs.tableData1 = [];objs.tableData2 = [];
						objs.option2.xAxis[0].data = objs.classroom;
		           		objs.option2.series[0].data = [];
		           		objs.option2.series[0].markLine.data[0].yAxis = 0;
		           		for(var a of obj.classroom){
			           		objs.option2.series[0].data.push(0);
			           		objs.option4.series[0].data.push(0);
			           }
			           obj.echarts.init(document.getElementById("averageChart")).setOption(obj.option2);
					}else{
						objs.tableData1.push(data.result.scoreVO);
						objs.setmans = data.result.studentNum;
					   	objs.tableData2 = data.result.classScoreVOList;
					   	for(var a of data.result.classScoreVOList){
							avgList.push(a.classSubjectAvgScore);
							avgXAxis.push(a.classroomName);
						}
						objs.option2.series[0].data = avgList;
						objs.option2.series[0].data[0] = {value:avgList[0],itemStyle:{normal:{color:"#FF8585"}}}
						objs.option2.xAxis[0].data = avgXAxis;
						objs.option2.series[0].markLine.data[0].yAxis = data.result.schoolAvgSubjectScore;
						objs.echarts.init(document.getElementById("averageChart")).setOption(objs.option2);
				}
			});
			this.postHttp(this,{tab:'TEACHING_REPORT',examId:this.testid,subject:name},"score/getLevelDistribution",function(objs,data){
			    	   		for(var a of data.result){
			    	   			if(a.subject==name){
			    	   				var data1 = [objs.initPrate(a.highRate),
			    	   					objs.initPrate(a.excellentRate),
			    	   					objs.initPrate(a.commissionRate),
			    	   					objs.initPrate(a.passRate),
			    	   					objs.initPrate(a.failureRate)];
			    	   					break;
			    	   			}else{
			    	   				var data1 = [0,0,0,0,0]
			    	   			}
			    	   		}
					   objs.option1.series[0].data = data1;
			           objs.echarts.init(document.getElementById("rankedchart")).setOption(objs.option1);
			        });
			this.postHttp(this,{subject:name,examId:this.testid},"/testAnalysis",function(objs,data){
					   objs.examination = [];
			           objs.examination = data.result.listVO;
			        });
			this.postHttp(this,{tab:'TEACHING_REPORT',examId:this.testid,subject:name},"score/getEachClassTopScores",function(objs,data){
	    		var datax = []; var data10 = []; var data20 = [];
	    		var data50 = []; var data100 = []; var data200 = [];
	    		var data500 = []; var data1000 = [];
	    		if(data.result=="没有最近一次考试的相关数据"){
	    			datax = [];data10 = [];data20 = [];data50 = [];
	    			data100 = [];data200 = [];data500 = [];data1000 = [];
					datax = objs.classroom;
					for(var a of objs.classroom){
		           		data10.push(0);
		           		data20.push(0);
		           		data50.push(0);
		           		data100.push(0);
		           		data200.push(0);
		           		data500.push(0);
		           		data1000.push(0);
		           }
	    		}else{
					for(var value of data.result){
		           		datax.push(value.classroomName);data10.push(value.classTopTenStuNum);
		           		data20.push(value.classTopTwentyStuNum);data50.push(value.classTopFiftyStuNum);
		           		data100.push(value.classTopOneHundredStuNum);
		           		data200.push(value.classTopTwoHundredStuNum);
		           		data500.push(value.classTopFiveHundredStuNum);
		           		data1000.push(value.classTopFortyStuNum);
	           		}
	    		}
	    		
			   objs.option3.xAxis[0].data = datax;
	           objs.option3.series[0].data = data10;objs.option3.series[1].data = data20;
	           objs.option3.series[2].data = data50;objs.option3.series[3].data = data100;
	           objs.option3.series[4].data = data200;objs.option3.series[5].data = data500;
	           objs.option3.series[6].data = data1000;
	           objs.echarts.init(document.getElementById("topComparedChart")).setOption(objs.option3);
	        });
	        this.postHttp(this,{tab:'TEACHING_REPORT',examId:this.testid,subject:name},"score/getEachSubjectLevelDistribution",function(objs,data){
					var xAxisD = [];var highRates;var excellentRates;
					var commissionRates;var passRates;var failureRates;
					var xDaras = [];
		    	   	if(data.result=="没有最近一次考试的相关数据"){
		    	   		objs.option6.xAxis.data =[];
		    	   		objs.option6.xAxis.data=objs.classroom;
		    	   		objs.option6.xAxis.data.unshift("全校");
		    	   		for(var a of objs.classroom){
		    	   			xAxisD.push(0)
		    	   		}
		    	   		objs.option6.series[0].data = xAxisD;
		    	   		objs.option6.series[1].data = xAxisD;
		    	   		objs.option6.series[2].data = xAxisD;
		    	   		objs.option6.series[3].data = xAxisD;
		    	   		objs.option6.series[4].data = xAxisD;
						objs.echarts.init(document.getElementById("contrastiveChart")).setOption(objs.option6);
		    	   	}else{
						highRates=[];excellentRates=[];
		    	   		commissionRates=[];passRates=[];
		    	   		failureRates=[];objs.option6.xAxis.data =[];xDaras = [];
		    	   		for(var a of data.result.classScoreVOList){
		    	   			highRates.push(objs.initPrate(a.highRate));
		    	   			excellentRates.push(objs.initPrate(a.excellentRate));
		    	   			commissionRates.push(objs.initPrate(a.commissionRate));
		    	   			passRates.push(objs.initPrate(a.passRate));
		    	   			failureRates.push(objs.initPrate(a.failureRate));
		    	   			xDaras.push(a.classroomName);
						}
						objs.option6.xAxis.data = xDaras;
						objs.option6.series[0].data = highRates;
		    	   		objs.option6.series[1].data = excellentRates;
		    	   		objs.option6.series[2].data = commissionRates;
		    	   		objs.option6.series[3].data = passRates;
		    	   		objs.option6.series[4].data = failureRates;
						objs.echarts.init(document.getElementById("contrastiveChart")).setOption(objs.option6);
		    	   	}
		        });
		},
    	compareSchool:function(){
    	},
    	
		testtest:function(e){
    		var olouceng = document.getElementsByClassName("louceng");
    		var oNav = document.getElementById("navInside").getElementsByTagName("li");
    		this.activeList = e;
			window.scrollTo(0 ,olouceng[e].offsetTop);
    	}
    },
    mounted:function(){

    	//接js模拟数据
    	this.tableData1 = this.IndexData.tableData1;this.countP = this.IndexData.countP;
    	this.totalCount = this.IndexData.totalCount;this.hightCount = this.IndexData.hightCount;
    	this.ranking = this.IndexData.ranking;this.goodsuject = this.eachWork(this.IndexData.goodsuject,'、');
    	this.hightavarge = this.eachWork(this.IndexData.hightavarge,'分、');this.allNumber = this.eachWork(this.IndexData.allNumber,'、');
    	this.lowSuject = this.eachWork(this.IndexData.lowSuject,'、');this.lowavarge = this.eachWork(this.IndexData.lowavarge,'分、');
    	this.allRanking = this.eachWork(this.IndexData.allRanking,'、');
    	this.option1 = this.IndexData.option1;
    	this.tableData2 = this.IndexData.tableData2;
    	this.option2 = this.IndexData.option2;
    	this.classNumble = this.IndexData.classNumble;
    	
    	this.classH = this.IndexData.classH;
    	this.missdistance = this.IndexData.missdistance;
    	this.classL = this.eachWork(this.IndexData.classL,'班、');
    	this.classLs = this.IndexData.classLs;
    	this.option3 = this.IndexData.option3;
    	this.tableData3 = this.IndexData.tableData3;
    	this.option4 = this.IndexData.option4;
    	this.option5 = this.IndexData.option5;
    	this.option6 = this.IndexData.option6;
    	this.examination = this.IndexData.examination;
    	//等级分布图
    	var olouceng = document.getElementsByClassName("louceng");
    	var oNav = document.getElementById("navInside").getElementsByTagName("li");
    	window.addEventListener('scroll',()=>{
    		var oheight = document.documentElement.clientHeight || document.body.clientHeight;
    		var otop = document.documentElement.scrollTop || document.body.scrollTop;
    		if(otop>=300){
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
    	this.initAll();
    }
}
</script>
czxc
<style>
.mainbody{
	width: 1100px;
	margin: auto;
	position: relative;
	border-top: 1px solid #f2f2f2
}
#teachLevel .el-carousel  {
    overflow: hidden;
    position: relative;
    text-align: center;
    margin: auto;
    width: 400px;
    height: 50px;
    margin-top: 30px;
    line-height: 50px;
}
#teachLevel #averageCompare{
	position: relative;
}
#teachLevel #averageCompare .allaverag{
	position: absolute;
	font-size: 13px;
	left: 620px;
	top: 3px
}
#teachLevel #rainbow .el-carousel{
    width: 1100px;
    height: 100px;
}
#teachLevel .el-carousel__container {
    position: relative;
    height: 50px;
    margin: auto;
}
#teachLevel .myselect{
	border:1px solid #44a9ff;
	width: 90px;
	border-radius: 4px;
}
#teachLevel .testTips{
	color:#3d3d3d;
	font-size: 14px;
	margin-top: 25px
}
#teachLevel .schoolSelectBox{
	width: 200px;
	margin: auto;
	margin-bottom: 35px;
}
#teachLevel .achievementSelectBox{
	width: 90px;
	margin: auto;
	margin-bottom: 35px;
}
#teachLevel .borders{
	box-shadow: 1px 1px 14px rgba(0,0,0,.15);
}
#teachLevel .el-table .tableBackground{
	background-color: #f5fcff;text-align: center;
}
#teachLevel .el-table .tableCenter{
	text-align: center;
}
#teachLevel .alltest{
	cursor: pointer;
}
#teachLevel .formatRow{
	font-weight: normal;
	text-align:center !important;
	color: white;
	border: 0px !important;
    background-color: #70CDF3 !important;
}
#achievementTable .el-table--border td{
	border:0px;
}
#teachLevel .el-input__inner{
	border: 0px;
	text-align: center;
}
#teachLevel .el-select .el-input .el-input__icon{
	color: #70CDF3
}
#teachLevel .el-carousel__arrow{
	background-color: white;
	color: #70CDF3
}
#teachLevel .el-carousel__arrow:hover{
	background-color: white
}
#teachLevel .showselect{
	list-style: none;position: absolute;margin: 0;padding:0;
	border: 1px solid #f2f2f2;
	left: 400px;
	z-index: 999;
    background-color: white;
    width: 300px;
    text-align: center;
}
#teachLevel .showselect li{
	margin: 0;padding:0;
	border-bottom: 1px solid #f2f2f2;
	padding:10px 20px;
	cursor: pointer;
}
#teachLevel .header p{
	font-size: 20px;
	color: #707070;
	text-align: center;
	letter-spacing: 0;
	margin-top: 35px
}
#teachLevel .header-title-foot{
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
#compareTestChart{
	width: 100%;
	height: 330px
}
#teachLevel .header-banner-bit{
	width: 89px;
	height: 73px;
	float: left;
	margin-top: 15px;
}
#teachLevel .table-header{
	background: #70CDF3;
	color: #fff;
}
#teachLevel .header-banner .el-carousel__item{
	margin-left: 60px;
}
#teachLevel .header-banner-click{
	width: 100%;
	height: 100%;
	text-align: center;
}
#teachLevel .header-banner-click p{
	line-height: 73px;
}
#teachLevel .header-banner-bit .on{
	height: 105%;
}

#twoDimensionalAnalysis{
	margin-top:20px;
}
#twoDimensionalAnalysis1{
	width: 1080px;
	height: 660px;
	margin: auto;
	margin-top: 20px;
	margin-bottom: 20px;
}
#twoDimensionalAnalysis .body{
	padding-top:20px;
}
#teachLevel #twoDimensionalAnalysis .body-icon{
	width: 255px;
	margin: auto;
}
#teachLevel #twoDimensionalAnalysis .body-icon-color{
	width: 20px;
	height: 20px;
	float: left;
}
#teachLevel #twoDimensionalAnalysis .body-icon-bit{
	margin-left:20px;
	float: left;
}
#teachLevel #twoDimensionalAnalysis .body-icon-bit p{
	margin-left: 5px;
	float: left;
}
#teachLevel #twoDimensionalAnalysis .color-blue{
	background: #70CDF3;
}
#teachLevel #twoDimensionalAnalysis .color-lightGrey{
	background: #919191;
}
#teachLevel #twoDimensionalAnalysis .color-darkGrey{
	background: #919191;
}
#teachLevel .twoDimensionalAnalysis-foot{
	width: 870px;
    height: 30px;
    margin: auto;
    margin-top: -45px;
}
#teachLevel .twoDimensionalAnalysis-foot .difficultyLevel{
	width: 33.33%;
	height: 100%;
	float:left;
	text-align: center;
	color: #fff;
}
#teachLevel .twoDimensionalAnalysis-foot .easy{
	background: #FF4444;
}
#teachLevel .twoDimensionalAnalysis-foot .midde{
	background: #FF8585;
}
#teachLevel .twoDimensionalAnalysis-foot .difficult{
	background: #FEB0B0;
}
#teachLevel .twoDimensionalAnalysis-foot .difficultyLevel p{
	line-height: 35px;
	font-size: 12px;
	letter-spacing: 0;
}
#teachLevel #twoDimensionalAnalysis .foot-word{
	width: 100%;
	margin-top:20px;
}
#teachLevel #twoDimensionalAnalysis .foot-word p{
	font-size: 14px;
	color: #3D3D3D;
	letter-spacing: 0;
	line-height: 24px;
}
#teachLevel .el-table .cell{
	padding:5px;
}
</style>