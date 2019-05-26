<template>
    <div id="program" class="program-container">
        <!-- Tab 部分 -->
        <ul class="tab-bor">
        	<li name="programPoi" class="poi active" @click="choiseType('',$event)">全部</li>
            <li name="programPoi" class="poi" @click="choiseType('LIVE',$event)">直播</li>
            <li name="programPoi" class="poi" @click="choiseType('RECORD',$event)">录制</li>
            <li name="programPoi" class="poi" @click="choiseType('VOD',$event)">点播</li>
        </ul>

        <el-row type="flex" class="row-bg mt20 pl20" justify="start">
            <el-col :span="4" class="pct25 mr20 ">
                <label class="mr20">学校  </label>
                <el-select v-model="optionSchool_val" class="pct70" placeholder="请选择学校" @change="school_chage_q()" clearable>
		            <el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
		        </el-select>
            </el-col>
            <el-col :span="4" class="pct25 mr20">
                <label class="mr20">年级</label>
                <el-select v-model="optionGrand_val" class="pct70" placeholder="请选择年级" clearable>
	                <el-option v-for="item in optionGrand" :key="item.id" :label="item.name" :value="item.id"></el-option>
	            </el-select>
            </el-col>
            <el-col :span="4" class="pct25 mr20">
                <label class="mr20">教师</label>
                <el-select v-model="optionTeacher_val" class="pct70" placeholder="请选择教师" clearable>
	                <el-option v-for="item in optionTeacher" :key="item.id" :label="item.name" :value="item.id"></el-option>
	            </el-select>
            </el-col>
            <el-col :span="4" class="pct25">
                <label class="mr20">学科</label>
                <el-select v-model="optionSubject_val" class="pct70" placeholder="请选择学科" clearable>
	                <el-option v-for="item in optionSubject" :key="item.dicCode" :label="item.dicName" :value="item.dicCode"></el-option>
	            </el-select>
            </el-col>
        </el-row>

        <el-row type="flex" class="row-bg mt20 pl20" justify="start">
            <!-- 节目名 -->
            <el-col :span="4" class="pct25 mr20">
                <label class="mr20">节目</label>
                <el-input class="pct70" v-model="name_q" placeholder="请输入节目名"></el-input>
            </el-col>
            <!-- 开始 -->
            <el-col :span="4" class="pct25 mr20">
	            <label class="mr20">开始</label>
	            <el-date-picker v-model="queryStartDate" type="datetime" class="pct70" placeholder="选择开始日期"></el-date-picker>
	        </el-col>
	        <!-- 结束 -->
	        <el-col :span="4" class="pct25 mr20">
	            <label class="mr20">——</label>
	            <el-date-picker v-model="queryEndDate" type="datetime" placeholder="选择结束时间"></el-date-picker>
	        </el-col>
            <!-- 查询 -->
            <el-col :span="4" class="pct25">
            	<label class="mr20"></label>
                <el-button type="success" class="pct70 l" @click="query_program()">查询</el-button>
            </el-col>
        </el-row>

        <div class="main-container">
            <!--表格部分-->
            <el-table :data="tableData" stripe row-key="id" style="width:100%">
			    <el-table-column prop="schoolName" label="学校" align="center" show-overflow-tooltip></el-table-column>
			    <el-table-column prop="gradeName" label="年级" width="120px" align="center" show-overflow-tooltip></el-table-column>
			    <el-table-column prop="sourceName" label="录制源" align="center" show-overflow-tooltip></el-table-column>
			    <el-table-column prop="subjectName" label="学科" width="100px" align="center" show-overflow-tooltip></el-table-column>
			    <el-table-column prop="teacherName" label="教师" width="100px" align="center" show-overflow-tooltip></el-table-column>
			    <el-table-column prop="name" label="节目名" align="center" show-overflow-tooltip></el-table-column>
			    <el-table-column prop="startDate" :formatter="timeFormat" width="190px" label="开始时间" align="center" show-overflow-tooltip></el-table-column>
			    <el-table-column prop="endDate" :formatter="timeFormat" width="190px" label="结束时间" align="center" show-overflow-tooltip></el-table-column>
			    <el-table-column prop="status" label="状态" width="100px" align="center" show-overflow-tooltip></el-table-column>
			    <el-table-column label="操作" align="center" width="200px">
			    	<template scope="scope">
			    			<i title="查看详细" class="el-icon-information" @click="dialogInfos(scope.row.id)"></i>
			    			<i title="预览视频" class="el-icon-search" @click="dialogShows(scope.row.id)"></i>
			    			<i title="编辑" class="el-icon-edit" @click="dialogEdits(scope.row.id)"></i>
			    			<i title="删除" class="el-icon-delete" @click="dialogDelete(scope.row.id)"></i>
			    	</template>
			    </el-table-column>
		  	</el-table>
	  		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageNum"
		      :page-sizes="[10, 20, 50]"
		      :page-size="pageSize"
		      layout="sizes, prev, pager, next, jumper"
		      :total="totals" class="tc mt20"
		      >
		    </el-pagination>
        </div>
        
        <el-dialog :visible.sync="dialogInfo"style="top:-14%" title="详情" :close-on-press-escape="false" :close-on-click-modal="false" :show-close="true" :before-close="handleClose">
        	<!-- <div class="infoTitle">详情</div> -->
        	<div class="infoBody" v-model="program">
        		<div class="secondTitle">节目信息</div>
        		<div class="fix">
        			<div class="infoItems fix l">
        				<div class="l part tr">节目名:</div>
        				<div class="l part tl ell" :title="program.name">{{program.name}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">状态:</div>
        				<div class="l part tl">{{program.status}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">开始时间:</div>
        				<div class="l part tl">{{program.startDate}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">结束时间:</div>
        				<div class="l part tl">{{program.endDate}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">是否属于课程:</div>
        				<div class="l part tl" v-if="program.isCourse==1">是</div>
        				<div class="l part tl" v-else>否</div>
        			</div>
        		</div>
        		<div class="secondTitle" v-if="program.isCourse==1">课程信息</div>
        		<div class="fix" v-if="program.isCourse==1">
        			<div class="infoItems fix l">
        				<div class="l part tr">学校:</div>
        				<div class="l part tl">{{program.schoolName}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">年级:</div>
        				<div class="l part tl">{{program.gradeName}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">教师:</div>
        				<div class="l part tl">{{program.teacherName}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">学科:</div>
        				<div class="l part tl">{{program.subjectName}}</div>
        			</div>
        		</div>
        		<div class="secondTitle">录制信息</div>
        		<div class="fix">
        			<div class="infoItems fix l">
        				<div class="l part tr">录制源:</div>
        				<div class="l part tl">{{program.sourceName}}</div>
        			</div>
        		</div>
        		<div class="secondTitle" >播放信息</div>
				<el-tree ref="playTreeInfo" default-expand-all :data="organizadata" :props="defaultProps_info" show-checkbox node-key="id" :indent="indents">
				</el-tree>
        		<div class="secondTitle">视频参数</div>
        		<div class="fix">
        			<div class="infoItems fix l">
        				<div class="l part tr">编码类型:</div>
        				<div class="l part tl">{{program.encoding}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">分辨率:</div>
        				<div class="l part tl">{{program.resolution}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">码率:</div>
        				<div class="l part tl">{{program.codeRate}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">码流控制:</div>
        				<div class="l part tl">{{program.rateControl}}</div>
        			</div>
        			<div class="infoItems fix l">
        				<div class="l part tr">帧率:</div>
        				<div class="l part tl">{{program.frameRate}}</div>
        			</div>
        		</div>
        		<div class="secondTitle" v-show="false">错误详情</div>
        	</div>
    	</el-dialog>
    	
    	<el-dialog :visible.sync="dialogShow" :before-close="show_close" id="playVideo" :show-close="fasle">
    	</el-dialog>
    	
    	<el-dialog :visible.sync="dialogEdit" :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false" style="top:-14%">
    		<el-tabs v-model="activeName">
			    <el-tab-pane label="节目详情" name="first">
			    	<div class="editBody">
				    	<el-form :inline="true" :model="program" class="demo-form-inline">
				    		
				    		<div class="secondTitle">节目信息</div>
						    <el-form-item label="节目名">
						      <el-input v-model="program.name" style="width:220px" name="name"></el-input>
						    </el-form-item>
						    <el-form-item label="是否属于课程" style="width: 45%;padding-left: 4%;" v-if="program.type=='LIVE' || program.type=='RECORD'">
						    	<el-radio-group  style="margin-left:30px;" v-model="program.isCourse">
							      <el-radio :label="1" name="isCourse">是</el-radio>
							      <el-radio :label="0" name="isCourse">否</el-radio>
							    </el-radio-group>
						    </el-form-item>
						    <div class="secondTitle" v-if="program.isCourse == 1 && program.type!='VOD'">课程信息</div>
						    <el-form-item label="教师" v-if="program.isCourse == 1 && program.type!='VOD'">
						        <el-select id="teacherName_in" v-model="program.teacherId" placeholder="请选择教师">
								    <el-option v-for="item in optionTeacher" :key="item.id" :label="item.name" :value="item.id"></el-option>
								</el-select>
						    </el-form-item>
						    <el-form-item label="学科" v-if="program.isCourse == 1 && program.type!='VOD'">
							    <el-select id="subjectName_in" v-model="program.subject" placeholder="请选择学科" @change="subject_change()">
								    <el-option v-for="item in optionSubject" :key="item.dicCode" :label="item.dicName" :value="item.dicCode"></el-option>
								</el-select>
						    </el-form-item>
						    <el-form-item label="课程大纲" v-if="program.isCourse == 1 && program.type!='VOD'">
						    	<el-cascader expand-trigger="click" :options="optionCascader" v-model="program.programScArray"></el-cascader>
						    </el-form-item>
						    
						    <div class="secondTitle" v-if="program.type=='LIVE' || program.type=='RECORD'">录制信息</div>
						    <el-form-item label="学校" v-if="program.type=='LIVE' || program.type=='RECORD'">
							    <el-select id="schoolName_in" v-model="program.schoolId" placeholder="请选择学校" @change="school_chage()">
								    <el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
								</el-select>
						    </el-form-item>
						    <el-form-item label="年级" v-if="program.type=='LIVE' || program.type=='RECORD'">
							    <el-select id="gradeName_in" v-model="program.gradeId" placeholder="请选择年级" @change="grand_chage()">
								    <el-option v-for="item in optionGrand" :key="item.id" :label="item.name" :value="item.id"></el-option>
								</el-select>
						    </el-form-item>
						    <el-form-item label="班级" v-if="program.type=='LIVE' || program.type=='RECORD'">
							    <el-select id="className_in"  v-model="program.sourceId" placeholder="请选择年级" >
								    <el-option v-for="item in optionClass" :key="item.id" :label="item.name" :value="item.id"></el-option>
								</el-select>
						    </el-form-item>
						    <el-form-item label="录制源" v-if="program.type=='LIVE' || program.type=='RECORD'">
						      <el-input style="width:220px" id="sourceName_in" v-model="program.sourceName" readonly></el-input>
						    </el-form-item>
						    <el-form-item label="开始时间" v-if="program.type=='LIVE' || program.type=='RECORD'">
							    <el-date-picker v-model="program.startDate" type="datetime">
							    </el-date-picker>
						    </el-form-item>
						    <el-form-item label="结束时间" v-if="program.type=='LIVE' || program.type=='RECORD'">
							    <el-date-picker v-model="program.endDate" type="datetime">
							    </el-date-picker>
						    </el-form-item>
						    
						    <div class="secondTitle" v-if="program.type=='VOD'">视频信息</div>
						    <el-form-item label="学校" v-if="program.type=='VOD'">
							    <el-select id="schoolName_in" v-model="program.schoolId" placeholder="请选择学校" @change="school_chage()">
								    <el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
								</el-select>
						    </el-form-item>
						    <el-form-item label="年级" v-if="program.type=='VOD'">
							    <el-select id="gradeName_in" v-model="program.gradeId" placeholder="请选择年级" @change="grand_chage()">
								    <el-option v-for="item in optionGrand" :key="item.id" :label="item.name" :value="item.id"></el-option>
								</el-select>
						    </el-form-item>
						    <el-form-item label="班级" v-if="program.type=='VOD'">
							    <el-select id="className_in"  v-model="program.sourceId" placeholder="请选择年级" >
								    <el-option v-for="item in optionClass" :key="item.id" :label="item.name" :value="item.id"></el-option>
								</el-select>
						    </el-form-item>
						    <el-form-item label="学科" v-if="program.type=='VOD'">
							    <el-select id="subjectName_in" v-model="program.subject" placeholder="请选择学科">
								    <el-option v-for="item in optionSubject" :key="item.dicCode" :label="item.dicName" :value="item.dicCode"></el-option>
								</el-select>
						    </el-form-item>
						    <el-form-item label="视频" v-if="program.type=='VOD'">
							    <el-select v-model="program.videoPath" placeholder="请选择视频">
								    <el-option v-for="item in optionVideo" :key="item.path" :label="item.name" :value="item.path"></el-option>
								</el-select>
						    </el-form-item>
						    
						    <div class="secondTitle" v-if="program.type=='LIVE' || program.type=='VOD'">播放信息</div>
						    <el-form-item label="开始时间" v-if="program.type=='VOD'">
							    <el-date-picker v-model="program.startDate" type="datetime">
							    </el-date-picker>
						    </el-form-item>
						    <el-form-item label="结束时间" v-if="program.type=='VOD'">
							    <el-date-picker v-model="program.endDate" type="datetime">
							    </el-date-picker>
						    </el-form-item>
						    <el-tree ref="playTree" v-if="program.type=='LIVE' || program.type=='VOD'" default-expand-all :data="organizadata" :props="defaultProps" node-key="id" show-checkbox :indent="indents">
							</el-tree>
						    
						</el-form>
					</div>
					<div class="editBottom fix">
						<div class="l">
							<div class="saveInfo tc poi auto" @click="saveprogarm()">保存</div>
						</div>
						<div class="l">
							<div class="cancleInfo tc poi auto" @click="dialogEdit = false">取消</div>								
						</div>
					</div>
			    </el-tab-pane>
			    
			    <el-tab-pane label="高级设置" name="second">
			    	<div class="editBody" v-model="program">
			    		<el-form :inline="true" :model="program" class="demo-form-inline">
			    			<div class="secondTitle">视频参数</div>
			    			<el-form-item label="编码类型">
							    <el-input v-model="program.encoding" readonly></el-input>
						    </el-form-item>
						    <el-form-item label="分辨率">
							    <el-input v-model="program.resolution" readonly></el-input>
						    </el-form-item>
						    <el-form-item label="码率">
						    	<el-input v-model="program.codeRate" readonly></el-input>
						    </el-form-item>
						    <el-form-item label="码流控制">
						    	<el-input v-model="program.rateControl" readonly></el-input>
						    </el-form-item>
						    <el-form-item label="帧率">
						    	<el-input v-model="program.frameRate" readonly></el-input>
						    </el-form-item>
			    		</el-form>
			    	</div>
			    	<div class="editBottom fix">
						<div class="l">
							<div class="saveInfo tc poi auto" @click="saveprogarm()">保存</div>
						</div>
						<div class="l">
							<div class="cancleInfo tc poi auto" @click="dialogEdit = false">取消</div>								
						</div>
					</div>
			    </el-tab-pane>
		  	</el-tabs>
    	</el-dialog>
    	
    	
    </div>
</template>

<script>
  export default{
    data () {
      return {
        queryStartDate:'',
        queryEndDate:'',
     	optionSchool:null,
        optionGrand:null,
        optionClass:null,
        optionSchool_val:'',
        optionGrand_val:'',
        optionTeacher:null,
        optionSubject:null,
        optionTeacher_val:'',
        optionSubject_val:'',
        name_q:'',
        
        tableData:[],
        dialogInfo:false,
        dialogEdit:false,
        dialogShow:false,
        flagF_s:true,
        flagF_g:true,
        flagF_c:true,
        activeName:'first',
    	pageNum:1,
		pageSize:10,
		totals:5,
		types:'',
        program:{},
        optionCascader:[],
        
        indents:20,
        organizadata: [{
        }],
        expand:false,
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        defaultProps_info: {
          children: 'children',
          label: 'label',
          disable:true
        },
        fasle:false,
        
        videoSrc:'http://he.yinyuetai.com/uploads/videos/common/C033015644E6D35D99022E014A4761A1.flv?sc\u003d6cbd6cfc31def573\u0026br\u003d3138\u0026vid\u003d2650626\u0026aid\u003d167\u0026area\u003dHT\u0026vst\u003d2',
        
        optionVideo:null,
        
      }
    },
	methods: {
		handleClose(done){
       		this.clearData();
       		done();
       	},
       	clearData(){
       		this.program={};
       	},
      dialogInfos(id){
      	this.dialogInfo = true;
      	var data = {id:id};
      	this.postHttp(this,data,"program/getProgram",this.info_handle);
      	this.postHttp(this,data,"organization/queryOrganizations",this.init_organiza);
	  },
	  dialogEdits(id){
    	this.dialogEdit = true;
    	var data = {id:id};
      	this.postHttp(this,data,"program/getProgram",this.info_handle);
	  },
	  init_organiza(obj,data){
      	this.organizadata = data.result.orgList;
      },
	  user_handle(obj,data){
	  	this.optionTeacher = data.result.list;
	  },
	  object_handle(obj,data){
	  	this.optionSubject = data.result;
	  },
	  info_handle(obj,data){
	  	if(this.dialogInfo){
	  		var lists = data.result;
			var startDates = this.timeF(lists.startDate).format("YYYY-MM-DD HH:mm:ss");
			var endDates = this.timeF(lists.endDate).format("YYYY-MM-DD HH:mm:ss");
			lists.startDate = startDates;
			lists.endDate = endDates;
			this.program = lists;
			var s = data.result.programShowIds;
			this.$refs.playTreeInfo.setCheckedKeys(s);
	  	}else{
	  		this.flagF_s = true;
	  		this.flagF_g = true;
	  		this.flagF_c = true;
	  		var par = data.result.schoolId;
			this.get_options(this,par,"optionGrand");
			var pars = data.result.gradeId;
			this.get_options(this,pars,"optionClass");
			var subject = data.result.subject;
		  	var datas = {subject:subject};
		  	this.postHttp(this,datas,"coursesyllabus/queryCSCascader",function(obj,data){
		  		obj.optionCascader = data.result;
		  	})
			this.program = data.result;
			var s = data.result.programShowIds;
			if(this.$refs.playTree){
				this.$refs.playTree.setCheckedKeys(s);
			}
			if(document.getElementById("sourceName_in")){
				document.getElementById("sourceName_in").getElementsByTagName("input")[0].onfocus = this.onFocus_name;
			}
	  	}
	  	
	  },
	  changeCourse(){
		var iscourse = this.program.isCourse
	  },
	  saveprogarm(){
		delete this.program['createDate']
		delete this.program['updateDate']
		delete this.program['programShowList']
		if(this.program.programScArray){
			var length = this.program.programScArray.length -1;
			this.program['syllabusId'] = this.program.programScArray[length];
		}
		this.program.startDate = this.timeF(this.program.startDate).format("YYYY-MM-DD HH:mm:ss")=='Invalid date'?'':this.timeF(this.program.startDate).format("YYYY-MM-DD HH:mm:ss")
		this.program.endDate = this.timeF(this.program.endDate).format("YYYY-MM-DD HH:mm:ss")=='Invalid date'?'':this.timeF(this.program.endDate).format("YYYY-MM-DD HH:mm:ss")
		this.onFocus_name();
		if(document.getElementById("schoolName_in")!=null){
			this.program['schoolName'] = document.getElementById("schoolName_in").getElementsByTagName("input")[0].value;
			this.program['gradeName'] = document.getElementById("gradeName_in").getElementsByTagName("input")[0].value;
			this.program['className'] = document.getElementById("className_in").getElementsByTagName("input")[0].value;
			this.program['subjectName'] = document.getElementById("subjectName_in").getElementsByTagName("input")[0].value;
			if(document.getElementById("teacherName_in")!=null){
				this.program['teacherName'] = document.getElementById("teacherName_in").getElementsByTagName("input")[0].value;
			}
		}
		var data = this.program;
		if(this.$refs.playTree!=undefined){
			data.programShowIds = this.$refs.playTree.getCheckedKeys();
		}
		this.postHttp(this,data,"program/saveProgram",this.save_handle);
	  },
	  save_handle(obj,data){
	  	if(data.code=="10000"){
	  		this.notify_jr(this,'编辑节目单','操作成功','success');
	  		var dataS = ajax_data(this);
	  		this.postHttp(this,dataS,'program/findPrograms',this.programInit);
	  		this.dialogEdit = false;
	  	}else{
	  		this.notify_jr(this,'编辑节目单',data.message,'error');
	  	}
	  },
	  handleSizeChange(val) {
		this.pageSize = val;
		this.pageNum = 1;
	  	var dataS = ajax_data(this);
	  	this.postHttp(this,dataS,'program/findPrograms',this.programInit);
	  },
	  handleCurrentChange(val) {
	  	this.pageNum = val;
	  	var dataS = ajax_data(this);
	    this.postHttp(this,dataS,'program/findPrograms',this.programInit);
	  },
	  choiseType(type,event){
	  	var obje = event.currentTarget;
	  	var reg = new RegExp('(\\s|^)active(\\s|$)');  
	  	var list = document.getElementsByName("programPoi");
	  	for(var i = 0;i<list.length;i++){
	  		var e = list[i];
	  		e.className = e.className.replace(reg,' ');
	  	}
	  	obje.className += ' active';
  		var dataS = {pageNum:1,pageSize:10,type:type};
  		this.types = type;
	  	this.postHttp(this,dataS,'program/findPrograms',this.programInit);
  	  },
  	  programInit(obj,data){
	    this.tableData = data.result.list;
	    for(var i=0;i<this.tableData.length;i++){
	    	if(this.tableData[i].status == '2_NOT_STARTED'){
	    		this.tableData[i].status = '未开始'
	    	}else if(this.tableData[i].status == '1_ONGOING'){
	    		this.tableData[i].status = '进行中'
	    	}else if(this.this.tableData[i].status == '3_ENDED'){
	    		this.tableData[i].status = '已结束'
	    	}else{
	    		this.tableData[i].status = '异常'
	    	}
	    }
	    this.pageNum = data.result.pageNum;
	    this.pageSize = data.result.pageSize;
	    this.totals = data.result.total;
	  },
	  timeFormat(row,column){
	  	var date = row[column.property];  
	  	if (date == undefined) {  
	     return "";  
	  	}  
	  	return this.timeF(date).format("YYYY-MM-DD HH:mm:ss");  
	  },
	  school_chage_q(){
		var id = this.optionSchool_val;
		this.optionGrand = '';
		this.optionGrand_val = null;
		if(id!=""){
			this.get_options(this,id,"optionGrand");
		}
	  },
	  school_chage(){
	  	var id = this.program.schoolId;
	  	if(!this.flagF_s){
			this.optionGrand = '';
	  		this.program.gradeId = '';
		}else{
			this.flagF_s = false;
		}
		if(id!=""){
			this.get_options(this,id,"optionGrand");
		}
	  },
	  grand_chage(){
	  	var id = this.program.gradeId;
	  	if(!this.flagF_g){
			this.optionClass = '';
	  		this.program.sourceId = '';
		}else{
			this.flagF_g = false;
		}
		if(id!=""){
			this.get_options(this,id,"optionClass");
		}
	  },
	  onFocus_name(){
	  	if(document.getElementById("schoolName_in")!=null){
	  		var s1 = document.getElementById("schoolName_in").getElementsByTagName("input")[0].value;
		  	var s2 = document.getElementById("gradeName_in").getElementsByTagName("input")[0].value;
		  	var s3 = document.getElementById("className_in").getElementsByTagName("input")[0].value;
		  	this.program.sourceName = s1+s2+s3;
	  	}
	  },
	  query_program(){
	  	this.pageNum = 1;
	  	this.pageSize = 10;
	  	this.types = '';
	  	var dataS = ajax_data(this);
		this.postHttp(this,dataS,'program/findPrograms',this.programInit);
	  },
	  dialogShows(id){
	  	var src = this.videoSrc;
	  	var html = '<div class="el-dialog el-dialog--small" style="top: 15%;"><div class="el-dialog__header"><span class="el-dialog__title"></span></div><div class="el-dialog__body">'
	  	html += '<div class="infoTitle">视频预览</div><div class="infoBody"><video width="100%" height="500" controls autoplay> <source src="'+src+'" type="video/mp4">您的浏览器不支持 video 标签。</video></div>'
	  	html +='</div></div>'
	  	var videos = document.getElementById("playVideo");
	  	videos.innerHTML = html;
	  	this.dialogShow = true;
	  },
	  show_close(done){
	  	var videos = document.getElementById("playVideo");
	  	videos.innerHTML = "";
	  	this.dialogShow = false;
	  },
	  subject_change(){
	  	var subject = this.program.subject;
	  	var data = {subject:subject};
	  	this.postHttp(this,data,"coursesyllabus/queryCSCascader",function(obj,data){
	  		obj.optionCascader = data.result;
	  	})
	  },
	  dialogDelete(id){
	  	this.$confirm('此操作将删除该课程,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        	var data = {id:id};
		  	delete data["createDate"];
		  	delete data["updataDate"];
		  	this.postHttp(this,data,"program/deleteProgram",function(obj,data){
		  		if(data.code=="10000"){
		  			obj.notify_jr(obj,"删除","操作成功","success");
		  			var dataS = ajax_data(obj);
					obj.postHttp(obj,dataS,'program/findPrograms',obj.programInit);
		  		}else{
		  			obj.notify_jr(obj,"删除","失败:"+data.message,"success");
		  		}
		  		
		  	})
        }).catch(() => {
        });
	  }
    },
	mounted:function(){
    	var dataS = ajax_data(this);
		this.postHttp(this,dataS,'program/findPrograms',this.programInit);
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
  
  function listToArray(list) {
  	var arr = new Array();
  	for(var i = 0;i<list.length;i++){
  		arr[i] = list[i].id;
  	}
  	return arr;
  }
  
  function ajax_data(obj){
	var schoolId = obj.optionSchool_val;
	var gradeId = obj.optionGrand_val;
	var teacherId = obj.optionTeacher_val;
	var subject = obj.optionSubject_val;
	var name = obj.name_q;
	//var queryStartDate = new Date(obj.queryStartDate).getTime();
	var queryStartDate = obj.timeF(obj.queryStartDate).format("YYYY-MM-DD HH:mm:ss")=='Invalid date'?'':obj.timeF(obj.queryStartDate).format("YYYY-MM-DD HH:mm:ss")
	var queryEndDate = obj.timeF(obj.queryEndDate).format("YYYY-MM-DD HH:mm:ss")=='Invalid date'?'':obj.timeF(obj.queryEndDate).format("YYYY-MM-DD HH:mm:ss")
	var type = obj.types;
	var pageNum = obj.pageNum;
	var pageSize = obj.pageSize;
	
	var data = {
		schoolId:schoolId,
		gradeId:gradeId,
		teacherId:teacherId,
		subject:subject,
		name:name,
		type:type,
		queryStartDate:queryStartDate,
		queryEndDate:queryEndDate,
		pageNum:pageNum,
		pageSize:pageSize
	}
	return data;
  }
  
</script>

<style>
    #program .main-container{
    	margin-top:20px;
    }
    #program .tab-bor .active{font-weight: normal;}
    #program .main-container i{margin: auto 4px;font-size:22px;cursor:pointer;}
    
    
    #program .el-tabs__header{margin:0px;}
    #program .el-tabs__nav{width:100%;}
    #program .el-tabs__active-bar{height:0px;}
    #program .el-tabs__item{width:50%;background: #E4E4E4;color:#272727;text-align: center;height: 50px;line-height: 50px;}
    #program .el-tabs__item.is-active{background:#66BB6A;color: #FFFFFF;}
    
    #program .el-form--inline .el-form-item{margin-right:0px;width:49%;}
    #program .infoBody .el-input__inner{width:220px;}
    #program .el-form-item__label{width:35%}
    #program .el-form-item__content{width:61%;}
    #program .el-cascader.is-opened .el-input__inner{border-color: #66BB6A;width:400px}
    #program .el-cascader .el-input__inner{width: 400px;}
    
    #program .editBody .el-date-editor.el-input{width:220px;}
    #program .editBody .el-select .el-input__inner{width:220px;}
    
    #program .el-tabs__nav-wrap{border-top-left-radius: 4px;border-top-right-radius: 4px;}
    #program .el-dialog__header{background:#66BB6A;text-align: center; border-top-left-radius: 4px; border-top-right-radius: 4px;}
	#program .el-dialog__title{height: 50px; line-height: 50px; font-size: 16px; color: #fff; background: #66BB6A;font-weight:normal;}
</style>