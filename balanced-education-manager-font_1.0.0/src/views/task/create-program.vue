<template>
  <div id="createProgram" class="create-program-container">
    <!--查询-->
    <el-row type="flex"  class="row-bg pt20 pl20" justify="start">
	    <!-- 节目类型 -->
	    <el-col :span="4" class="pct25 mr20">
	      <label class="mr20">节目类型</label>
	      <el-select v-model="progarm_type" class="pct70" placeholder="请选择节目类型" clearable>
	         <el-option v-for="item in type_option" :key="item.value" :label="item.label" :value="item.value">
	        </el-option>
	      </el-select>
	    </el-col>
	    <el-col :span="4" class="pct25 mr20">
	        <label class="mr20">学校  </label>
	        <el-select v-model="optionSchool_val" class="pct70" placeholder="请选择学校" @change="school_chage_q()" clearable>
		            <el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
		        </el-select>
	    </el-col>
	    <el-col :span="4" class="pct25 mr20">
	    		<label class="mr20">年级</label>
	        <el-select v-model="optionGrand_val" class="pct70" placeholder="请选择年级" @change="grand_chage_q()" clearable>
	            <el-option v-for="item in optionGrand" :key="item.id" :label="item.name" :value="item.id"></el-option>
	        </el-select>
	    </el-col>
	    <el-col :span="4" class="pct25 mr20" >
	   		<label class="mr20">班级</label>
	        <el-select v-model="optionClass_val" class="pct70" placeholder="请选择班级" clearable>
	            <el-option v-for="item in optionClass" :key="item.id" :label="item.name" :value="item.id"></el-option>
	        </el-select>
	    </el-col>
	    
  	</el-row>
  	
  	<el-row type="flex"  class="row-bg pt20 pl20" justify="start">
	    <!-- 节目类型 -->
	    <el-col :span="4" class="pct25 mr20">
	    </el-col>
	    <el-col :span="4" class="pct25 mr20">
	    </el-col>
	    <el-col :span="4" class="pct25 mr20" >
	    </el-col>
	    <el-col :span="4" class="pct25 mr20">
	    	<label class="mr20"></label>
        <el-button type="success" class="pct70 l" @click="query_program()">查询</el-button>
	    </el-col>
  	</el-row>

    <div class="main-container">
      <!--表格部分-->
      <div class="table-striped">
      	 <div class="remind">双击空白部分创建新节目</div>
        <table class="ktable">
          <thead>
          <tr>
            <th>
            	<el-date-picker v-model="queryStartDate" @change="time_change()" :clearable="false"  type="datetime" placeholder="选择开始日期"></el-date-picker>
            </th>
	        	<th>
        				<div class="dataW">星期一</div>
            		<div id="week_0" class="dataW">2017/04/11</div>
            </th>
            <th>
            		<div class="dataW">星期二</div>
            		<div id="week_1" class="dataW">2017/04/12</div>
            </th>
            <th>
            		<div class="dataW">星期三</div>
            		<div id="week_2" class="dataW">2017/04/13</div>
            </th>
            <th>
            		<div class="dataW">星期四</div>
            		<div id="week_3" class="dataW">2017/04/14</div>
            </th>
            <th>
            		<div class="dataW">星期五</div>
            		<div id="week_4" class="dataW">2017/04/15</div>
            </th>
            <th>
            		<div class="dataW">星期六</div>
            		<div id="week_5" class="dataW">2017/04/16</div>
            </th>
            <th>
            		<div class="dataW">星期日</div>
            		<div id="week_6" class="dataW">2017/04/17</div>
            </th>
          </tr>
          </thead>
          <tbody>
          <tr class="tc" v-for="e in time_point">
            <td class="dateTime">{{e.time}}</td>
            <td class="poi itemsTd week_0"></td>
            <td class="poi itemsTd week_1"></td>
            <td class="poi itemsTd week_2"></td>
            <td class="poi itemsTd week_3"></td>
            <td class="poi itemsTd week_4"></td>
            <td class="poi itemsTd week_5"></td>
            <td class="poi itemsTd week_6"></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
		
		<el-dialog :visible.sync="dialog_program" :show-close="false" :close-on-click-modal="false" :close-on-press-escape="false">
    	<div class="infoTitle">新建节目单</div>
  		<div class="editBody">
	    	<el-form :inline="true" :model="program" class="demo-form-inline">
	    		
	    		<div class="secondTitle">节目信息</div>
			    <el-form-item label="节目名">
			      <el-input v-model="program.name" style="width:220px" name="name"></el-input>
			    </el-form-item>
			    <el-form-item label="节目类型">
		    		<el-select v-model="program.type" placeholder="请选择节目类型">
		         	<el-option v-for="item in type_option" :key="item.value" :label="item.label" :value="item.value">
			        </el-option>
	      		</el-select>
			    </el-form-item>
			    <el-form-item label="是否属于课程" style="width: 45%;padding-left: 4%;" v-if="program.type != 'VOD'">
			    	<el-radio-group  style="margin-left:30px;" v-model="program.isCourse">
				      <el-radio :label="1" name="isCourse">是</el-radio>
				      <el-radio :label="0" name="isCourse">否</el-radio>
				    </el-radio-group>
			    </el-form-item>
			    <div class="secondTitle" v-if="program.isCourse == 1 && program.type != 'VOD'">课程信息</div>
			    <el-form-item label="教师" v-if="program.isCourse == 1 && program.type != 'VOD'">
			        <el-select id="teacherName_in" v-model="program.teacherId" placeholder="请选择教师">
					    <el-option v-for="item in optionTeacher" :key="item.id" :label="item.name" :value="item.id"></el-option>
					</el-select>
			    </el-form-item>
			    <el-form-item label="学科" v-if="program.isCourse == 1 && program.type != 'VOD'">
				    <el-select id="subjectName_in" v-model="program.subject" placeholder="请选择学科" @change="subject_change()">
					    <el-option v-for="item in optionSubject" :key="item.dicCode" :label="item.dicName" :value="item.dicCode"></el-option>
					</el-select>
			    </el-form-item>
			    <el-form-item label="课程大纲" v-if="program.isCourse == 1 && program.type != 'VOD'">
			    	<el-cascader expand-trigger="click" :options="optionCascader" v-model="program.programScArray"></el-cascader>
			    </el-form-item>
			    
			    <div class="secondTitle" v-if="program.type != 'VOD'">录制信息</div>
			    <el-form-item label="学校" v-if="program.type != 'VOD'">
				    <el-select id="schoolName_in" v-model="program.schoolId" placeholder="请选择学校" @change="school_chage()">
					    <el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
					</el-select>
			    </el-form-item>
			    <el-form-item label="年级" v-if="program.isCourse == 1 && program.type != 'VOD'">
				    <el-select id="gradeName_in" v-model="program.gradeId" placeholder="请选择年级" @change="grand_chage()">
					    <el-option v-for="item in optionGrand" :key="item.id" :label="item.name" :value="item.id"></el-option>
					</el-select>
			    </el-form-item>
			    <el-form-item label="班级" v-if="program.isCourse == 1 && program.type != 'VOD'">
				    <el-select id="className_in" v-model="program.sourceId" placeholder="请选择班级">
					    <el-option v-for="item in optionClass" :key="item.id" :label="item.name" :value="item.id"></el-option>
					</el-select>
			    </el-form-item>
			    <el-form-item label="录制源" v-if="program.type != 'VOD'">
			      <el-input id="sourceName_in" style="width:220px" v-model="program.sourceName" readonly></el-input>
			    </el-form-item>
			    <el-form-item label="开始时间" v-if="program.type != 'VOD'">
				    <el-date-picker v-model="program.startDate" type="datetime">
				    </el-date-picker>
			    </el-form-item>
			    <el-form-item label="结束时间" v-if="program.type != 'VOD'">
				    <el-date-picker v-model="program.endDate" type="datetime">
				    </el-date-picker>
			    </el-form-item>
			    
			    <div class="secondTitle" v-if="program.type == 'VOD'">视频信息</div>
			    <el-form-item label="学校" v-if="program.type == 'VOD'">
				    <el-select id="schoolName_in" v-model="program.schoolId" placeholder="请选择学校" @change="school_chage()">
					    <el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
					</el-select>
			    </el-form-item>
			    <el-form-item label="年级" v-if="program.type == 'VOD'">
				    <el-select id="gradeName_in" v-model="program.gradeId" placeholder="请选择年级" @change="grand_chage()">
					    <el-option v-for="item in optionGrand" :key="item.id" :label="item.name" :value="item.id"></el-option>
					</el-select>
			    </el-form-item>
			    <el-form-item label="教师" v-if="program.type == 'VOD'">
			        <el-select id="teacherName_in" v-model="program.teacherId" placeholder="请选择教师">
					    <el-option v-for="item in optionTeacher" :key="item.id" :label="item.name" :value="item.id"></el-option>
					</el-select>
			    </el-form-item>
			    <el-form-item label="学科" v-if="program.type == 'VOD'">
				    <el-select id="subjectName_in" v-model="program.subject" placeholder="请选择学科" @change="subject_change()">
					    <el-option v-for="item in optionSubject" :key="item.dicCode" :label="item.dicName" :value="item.dicCode"></el-option>
					</el-select>
			    </el-form-item>
			    <el-form-item label="视频" v-if="program.type == 'VOD'">
				    <el-select v-model="program.videoPath" placeholder="请选择视频">
						    <el-option v-for="item in optionVideo" :key="item.path" :label="item.name" :value="item.path"></el-option>
						</el-select>
			    </el-form-item>
			    
			    <div class="secondTitle" v-if="program.type == 'LIVE' || program.type == 'VOD'">播放信息</div>
			    <el-form-item label="开始时间" v-if="program.type == 'VOD'">
				    <el-date-picker v-model="program.startDate" type="datetime">
				    </el-date-picker>
			    </el-form-item>
			    <el-form-item label="结束时间" v-if="program.type == 'VOD'">
				    <el-date-picker v-model="program.endDate" type="datetime">
				    </el-date-picker>
			    </el-form-item>
			    <el-tree v-if="program.type == 'LIVE' || program.type == 'VOD'" ref="playTree" default-expand-all :data="organizadata" :props="defaultProps" node-key="id" show-checkbox :indent="indents">
					</el-tree>
			    
			</el-form>
		</div>
		<div class="editBottom fix">
			<div class="l">
				<div class="saveInfo tc poi auto" @click="saveprogarm()">保存</div>
			</div>
			<div class="l">
				<div class="cancleInfo tc poi auto" @click="close_program()">取消</div>								
			</div>
		</div>
    </el-dialog>


  </div>
</template>



<script>
	import jQuery from 'jquery';
	
  export default{
    data () {
      return {
        msg:'hello vue',
	      type_option:[{
	      	value: 'LIVE',
          label: '直播'
	      },{
	      	value: 'RECORD',
          label: '录制'
	      },{
	      	value: 'VOD',
          label: '点播'
	      }],
	      progarm_type:'',
	      optionSchool:null,
        optionGrand:null,
     		optionClass:null,
     		optionTeacher:null,
        optionSubject:null,
        optionSchool_val:'',
        optionGrand_val:'',
        optionClass_val:'',
        
        indents:20,
        organizadata: [],
        expand:false,
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        
	      tableData:[],
	      queryStartDate:'',
	      dialog_program:false,
	      
	      program:{
	      	schoolId:'',
	      	gradeId:'',
	      	sourceId:'',
	      	sourceName:'',
	      	teacherId:'',
	      	subject:'',
	      	isCourse:1,
	      	startDate:null,
	      	endDate:null,
	      	name:'',
	      	type:'',
	      	schoolName:'',
	      	gradeName:'',
	      	videoPath:'',
	      	programScArray:[]
	      },
	      time_point:[{
	      	time:'9:00'
	      },{
	      	time:'10:00'
	      },{
	      	time:'11:00'
	      },{
	      	time:'12:00'
	      },{
	      	time:'13:00'
	      },{
	      	time:'14:00'
	      },{
	      	time:'15:00'
	      },{
	      	time:'16:00'
	      },{
	      	time:'17:00'
	      },{
	      	time:'18:00'
	      }],
	      
	      optionVideo:null,
	      optionCascader:[],
      }
    },
    methods:{
    	time_change(){
    		date_init(this.queryStartDate,this); 
    	},
    	school_chage_q(){
				var id = this.optionSchool_val;
				this.optionGrand = '';
				this.optionGrand_val = null;
				if(id!=""){
					this.get_options(this,id,"optionGrand");
				}
		  },
		  grand_chage_q(){
		  	var id = this.optionGrand_val;
				this.optionClass = '';
				this.optionClass_val = null;
				if(id!=""){
					this.get_options(this,id,"optionClass");
				}
		  },
		  school_chage(){
		  	var id = this.program.schoolId;
				this.optionGrand = '';
	  		this.program.gradeId = '';
				if(id!=""){
					this.get_options(this,id,"optionGrand");
				}
		  },
		  grand_chage(){
		  	var id = this.program.gradeId;
				this.optionClass = '';
	  		this.program.sourceId = '';
				if(id!=""){
					this.get_options(this,id,"optionClass");
				}
		  },
		  onFocus_name(){
		  	if(document.getElementById("schoolName_in")==null || document.getElementById("gradeName_in")==null || document.getElementById("className_in")==null){
		  		return;
		  	}
		  	var s1 = document.getElementById("schoolName_in").getElementsByTagName("input")[0].value;
		  	var s2 = document.getElementById("gradeName_in").getElementsByTagName("input")[0].value;
		  	var s3 = document.getElementById("className_in").getElementsByTagName("input")[0].value;
		  	this.program.sourceName = s1+s2+s3;
		  	this.program.schoolName = s1;
		  	this.program.gradeName = s2;
		  },
			query_program(){
				var data = ajax_data(this);
				if(data==null){
					return;
				}
  			this.postHttp(this,data,"program/findProgramsByClass",this.data_handle);	
			},
		  data_handle(obj,data){
		  	this.tableData = data.result.list;
		  	items_init(this);
		  },
		  saveprogarm(){
					if(this.program.programScArray){
						var length = (this.program.programScArray.length -1) == -1?0:(this.program.programScArray.length -1);
						this.program['syllabusId'] = this.program.programScArray[length];
					}
					this.program.startDate = this.timeF(this.program.startDate).format("YYYY-MM-DD HH:mm:ss")=='Invalid date'?'':this.timeF(this.program.startDate).format("YYYY-MM-DD HH:mm:ss")
					this.program.endDate = this.timeF(this.program.endDate).format("YYYY-MM-DD HH:mm:ss")=='Invalid date'?'':this.timeF(this.program.endDate).format("YYYY-MM-DD HH:mm:ss")
					this.onFocus_name();
					if(document.getElementById("schoolName_in")!=null){
						this.program['schoolName'] = document.getElementById("schoolName_in").getElementsByTagName("input")[0].value;
					}
					if(document.getElementById("gradeName_in")!=null){
						this.program['gradeName'] = document.getElementById("gradeName_in").getElementsByTagName("input")[0].value;
					}
					if(document.getElementById("className_in")!=null){
						this.program['className'] = document.getElementById("className_in").getElementsByTagName("input")[0].value;
					}
					if(document.getElementById("subjectName_in")!=null){
						this.program['subjectName'] = document.getElementById("subjectName_in").getElementsByTagName("input")[0].value;
					}
					if(document.getElementById("teacherName_in")!=null){
						this.program['teacherName'] = document.getElementById("teacherName_in").getElementsByTagName("input")[0].value;
					}
					var data = this.program;
					if(this.$refs.playTree!=undefined){
						data.programShowIds = this.$refs.playTree.getCheckedKeys();
					}
					this.postHttp(this,data,"program/saveProgram",this.save_handle);
		  },
		  save_handle(obj,data){
		  	if(data.code=="10000"){
			  		this.notify_jr(this,'新建节目单','操作成功','success');
			  		this.dialog_program = false;
			  		this.optionSchool_val = this.program.schoolId;
						this.optionGrand_val = this.program.gradeId;
						this.optionClass_val = this.program.sourceId;
						this.progarm_type = this.program.type;
			  		this.query_program()
			  		
			  	}else{
			  		this.notify_jr(this,'新建节目单',data.message,'error');
			  	}
		  },
		  user_handle(obj,data){
		  	this.optionTeacher = data.result.list;
		  },
		  object_handle(obj,data){
		  	this.optionSubject = data.result;
		  },
		  init_organiza(obj,data){
      	this.organizadata = data.result.orgList;
      },
		  close_program(){
		  	this.progarm = {
		  		schoolId:'',
	      	gradeId:'',
	      	sourceId:'',
	      	sourceName:'',
	      	teacherId:'',
	      	subject:'',
	      	isCourse:1,
	      	startDate:null,
	      	endDate:null,
	      	name:'',
	      	type:'',
	      	videoPath:'',
	      	schoolName:'',
	      	gradeName:'',
	      	programScArray:[]
		  	}
		  	this.dialog_program = false;
		  	this.$refs.playTree.setCheckedKeys([]);
		  },
		  subject_change(){
		  	var subject = this.program.subject;
		  	var data = {subject:subject};
		  	this.postHttp(this,data,"coursesyllabus/queryCSCascader",function(obj,data){
		  		obj.optionCascader = data.result;
		  	})
		  },
    },
    mounted:function(){
    	var dateNow = new Date();
    	date_init(dateNow,this); 
    	this.get_options(this,"","optionSchool");
    	var data_user = {role:'教师',pageNum:1,pageSize:30};
	  	this.postHttp(this,data_user,"user/queryUsers",this.user_handle);
	  	var data_code ={code:'SUBJECT'};
	  	this.postHttp(this,data_code,"dictionary/getDictionarysBySupCode",this.object_handle);
	  	
	  	var data = {}
    	this.postHttp(this,data,"organization/queryOrganizations",this.init_organiza);
    	
    	this.postHttp(this,{},"teachingfile/queryTeachingFilesVideo",function(obj,data){
    		obj.optionVideo = data.result;
    	})
    }
  }
  
  function date_init(datenow,obj){
  	var weeknow = datenow.getUTCDay();
  	if(weeknow!=0){
  		datenow.setDate(datenow.getDate()-weeknow);
  	}
  	for(var i = 0;i<7;i++){
			datenow.setDate(datenow.getDate()+1);
			var html = ''+obj.timeF(datenow).format("YYYY/MM/DD");
			jQuery("#week_"+i).html(html);
  	}
  	
  	jQuery(".itemsTd").dblclick(function(){
  		obj.dialog_program = true;
  		var classs = jQuery(this).attr("class").split(" ");
  		var week = classs[classs.length-1];
  		var date_day = jQuery("#"+week).html().split("/");
  		var date_time = parseInt(jQuery(this).parent().find(".dateTime").html().split(":")[0]);
  		var date_now = new Date();
  		date_now.setFullYear(parseInt(date_day[0]));
  		date_now.setMonth(parseInt(date_day[1])-1);
  		date_now.setDate(parseInt(date_day[2]));
  		date_now.setHours(parseInt(date_time));
  		date_now.setMinutes(0);date_now.setSeconds(0);
  		obj.program.startDate = date_now.getTime();
  		obj.program.endDate = date_now.getTime()+7200000;
  		document.getElementById("sourceName_in").getElementsByTagName("input")[0].onfocus = obj.onFocus_name;
  	})
  }
  
  function items_init(obj){
  	jQuery(".itemsTd").each(function(){
  		var pro_list = obj.tableData;
  		if(pro_list==null || pro_list.length==0 ){
  			return;
  		}
  		var classs = jQuery(this).attr("class").split(" ");
  		var week = classs[classs.length-1];
  		var date_day = jQuery("#"+week).html();
  		var date_time = parseInt(jQuery(this).parent().find(".dateTime").html().split(":")[0]);
  		var html = '';
	  	for(var i = 0;i<pro_list.length;i++){
				var e = pro_list[i];
				var date_i = new Date(e.startDate);
				var date_day_d = obj.timeF(date_i).format("YYYY/MM/DD");
				var date_time_d = date_i.getHours();
				if(date_day_d == date_day && date_time_d >= date_time && date_time_d < (date_time+1)){
					html += e.subject+" ";
					jQuery(this).unbind();
					jQuery(this).addClass("clearClick");
				}
			}
	  
	  	if(html!=''){
	  		jQuery(this).html(html);
	  	}
  	})
  	
  }
  
  function ajax_data(obj){
		var schoolId = obj.optionSchool_val;
		var gradeId = obj.optionGrand_val;
		var remark = obj.optionClass_val;
		var type = obj.progarm_type;
		if(type==""){
			obj.notify_jr(obj,"提示","请选择节目类型","warning");
			return null;
		}
		if(schoolId==""){
			obj.notify_jr(obj,"提示","请选择学校","warning");
			return null;
		}
		if(gradeId==""){
			obj.notify_jr(obj,"提示","请选择年级","warning");
			return null;
		}
		var pageNum = 1;
		var pageSize = 70;
		
		var data = {
			schoolId:schoolId,
			gradeId:gradeId,
			remark:remark,
			type:type,
			pageNum:pageNum,
			pageSize:pageSize
		}
		return data;
  }
</script>

<style>
	#createProgram .table-striped{margin-top:40px;}
	#createProgram .table-striped th,td{width:12.5%}
	#createProgram .table-striped tr{height:60px;line-height:60px;}
	#createProgram .table-striped .remind{padding-left:20px;height:30px;line-height: 30px;color:#666;}
	#createProgram .table-striped .dataW{line-height: 20px;height: 20px;}
	#createProgram .table-striped .itemsTd:hover{background:#66BB6A;color:#fff}
	#createProgram .table-striped .clearClick{background:#96dc99;color:#fff;cursor: default;}
	#createProgram .table-striped .el-icon-time:before {content: "\E611";}
	#createProgram .table-striped .el-date-editor input{display: none;}
	#createProgram .table-striped .el-date-editor .el-input__icon{position: relative;color:#66BB6A;}
	
	#createProgram .el-form--inline .el-form-item{margin-right:0px;width:49%;}
	#createProgram .infoBody .el-input__inner{width:220px;}
	#createProgram .el-form-item__label{width:35%}
	#createProgram .el-form-item__content{width:61%;}
	#createProgram .el-cascader.is-opened .el-input__inner{border-color: #66BB6A;width:400px}
	#createProgram .el-cascader .el-input__inner{width: 400px;}
	
	#createProgram .editBody .el-date-editor.el-input{width:220px;}
	#createProgram .editBody .el-select .el-input__inner{width:220px;}
	
</style>