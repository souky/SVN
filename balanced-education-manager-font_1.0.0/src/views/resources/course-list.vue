<template>
  <div id="courseList" class="course-list-container">
    <el-row type="flex" class="row-bg pt20 pl20" justify="start">
        <el-col :span="4" class="pct25 mr20 ">
            <label class="mr20">学校  </label>
            <el-select v-model="optionSchool_val" 
            			class="pct70" 
            			placeholder="请选择学校" 
            			clearable
            			@clear="clearSchool" 
            			@change="school_chage_q()">
	            <el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
	        	</el-select>
        </el-col>
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20">年级</label>
            <el-select v-model="optionGrand_val" 
            		   class="pct70" 
            		   placeholder="请选择年级"
            		   clearable
            		   @clear="clearGrand" 
            		   >
                <el-option v-for="item in optionGrand" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
        </el-col>
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20">教师</label>
            <el-select v-model="optionTeacher_val" 
            		   class="pct70" 
            		   placeholder="请选择教师"
            		   clearable
            		   @clear="clearTeacher" 
            		   >
                <el-option v-for="item in optionTeacher" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
        </el-col>
        <el-col :span="4" class="pct25">
            <label class="mr20">学科</label>
            <el-select v-model="optionSubject_val" 
            		   class="pct70" 
            		   placeholder="请选择学科"
            		   clearable
            		   >
                <el-option v-for="item in optionSubject" :key="item.dicCode" :label="item.dicName" :value="item.dicCode"></el-option>
            </el-select>
        </el-col>
    </el-row>
 
    <el-row type="flex" class="row-bg mt20 pl20" justify="start">
        <!-- 课程名 -->
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20">课程</label>
            <el-input class="pct70" placeholder="请输入课程名"></el-input>
        </el-col>
        <!-- 开始 -->
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20">开始</label>
            <el-date-picker
            		v-model="queryStartDate"
                    type="datetime"
                    class="pct70"
                    placeholder="选择开始日期">
            </el-date-picker>
        </el-col>
        <!-- 结束 -->
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20">——</label>
            <el-date-picker
            		v-model="queryEndDate"
                    type="datetime"
                    placeholder="选择结束时间">
            </el-date-picker>
        </el-col>
        <!-- 查询 -->
        <el-col :span="4" class="pct25">
        		<label class="mr20"></label>
            <el-button type="success" class="pct70 l" @click="sleectAll">查询</el-button>
        </el-col>
    </el-row>
    
    <el-row type="flex" class="row-bg mt20 pl20" justify="start">
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20"></label>
        </el-col>
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20"></label>
        </el-col>
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20"></label>
        </el-col>
        <el-col :span="4" class="pct25">
        		<label class="mr20"></label>
            <el-button type="primary" class="pct70 l" @click="dialogAdds()">新增课程</el-button>
        </el-col>
    </el-row>


    <div class="main-container">
    	<!--表格部分-->
    	<el-table :data="tableData" stripe row-key="id" style="width:100%;margin-top: 20px;">
    		<el-table-column prop="schoolName" label="学校" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="gradeName" label="年级" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="subject" label="学科" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="teacherName" label="教师" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="name" label="课程名" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="startDate" :formatter="timeFormat" label="开课日期" align="center" show-overflow-tooltip min-width="160px"> </el-table-column>
    		<el-table-column label="操作" align="center" show-overflow-tooltip min-width="150px">
    			<template scope="scope">
		    			<i title="查看详细" class="el-icon-information" @click="dialogInfos(scope.row.id)"></i>
		    			<i title="编辑" class="el-icon-edit" @click="dialogEdits(scope.row.id)"></i>
		    			<i title="删除" class="el-icon-delete" @click="delete_course(scope.row.id)"></i>
		    	</template>
    		</el-table-column>
    	</el-table>
    	
    	<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageNum"
	      :page-sizes="[10, 20, 50]"
	      :page-size="pageSize"
	      layout="sizes, prev, pager, next, jumper"
	      :total="total" class="tc mt20"
	      >
	    </el-pagination>
    </div>
    
    <el-dialog :visible.sync="dialogAdd" :close-on-click-modal="false" id="addCrouse" :show-close="false" style="top:-14%">
  		<div class="infoTitle" :model="infoTitles">{{infoTitles}}</div>
  		<div class="addBody fix" >
  			<el-form :label-position="labelPosition" label-width="80px" :model="course">
  				<div class="leftPart l">
  					<el-form-item label="名称">
					    <el-input v-model="course.name"></el-input>
					  </el-form-item>
					  <el-form-item label="开课日期">
					    <el-date-picker v-model="course.startDate" type="datetime" placeholder="选择开课日期"></el-date-picker>
					  </el-form-item>
					  <el-form-item label="学校">
					  	<el-select v-model="course.schoolId" id="schoolName_in" placeholder="请选择学校" @change="change_school()">
					   		 <el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
					   	</el-select>
					  </el-form-item>
					  <el-form-item label="年级">
					   <el-select v-model="course.gradeId" id="gradeName_in" placeholder="请选择年级">
					   		<el-option v-for="item in optionGrand" :key="item.id" :label="item.name" :value="item.id"></el-option>
					   	</el-select>
					  </el-form-item>
					  <el-form-item label="学科">
					  	<el-select v-model="course.subject"  placeholder="请选择学科" @change="change_subject()">
					   		<el-option v-for="item in optionSubject" :key="item.dicName" :label="item.dicName" :value="item.dicName"></el-option>
					   	</el-select>
					  </el-form-item>
					  <el-form-item label="教师">
					  	<el-select v-model="course.teacherId" id="teacherName_in" placeholder="请选择教师">
					   		<el-option v-for="item in optionTeacher" :key="item.id" :label="item.name" :value="item.id"></el-option>
					   	</el-select>
					  </el-form-item>
					  <el-form-item label="教材">
					  	<el-select v-model="course.materialId"  placeholder="请选择教材">
					   		<el-option v-for="item in optionMaterial" :key="item.id" :label="item.name" :value="item.id"></el-option>
					   	</el-select>
					  </el-form-item>
  				</div>
  				<div class="rightPart l tc">
  					<div style="margin-bottom: 10px;">课程图片</div>
  					<el-upload class="" 
  					:action="Url"
  					:show-file-list="false"
  					:with-credentials="true"
  					:on-success="handleAvatarSuccess"
  					:before-upload="beforeAvatarUpload" >
  						<img v-if="course.coverImg" :src="imageUrl" class="avatar">
  						<i v-else class="el-icon-plus avatar-uploader-icon" style="color:#bfcbd9;"></i>
						</el-upload>
  				</div>
  				
  				<div class="l" style="width:90%;">
  					<el-form-item label="课程简介">
					    <el-input type="textarea" :rows="3" resize="none" v-model="course.remark"></el-input>
					  </el-form-item>
  				</div>
  				
  				<div class="l el-form-item" style="width: 100%;" v-if="courseSyllabuss!=null">
  					<label class="el-form-item__label" style="width: 80px;">课程大纲</label>
  					<div class="el-form-item__content" style="margin-left: 80px;">
  						<el-tree id="courses"  ref="courseTree" default-expand-all :data="courseSyllabuss" :props="defaultProps" :render-content="renderContent" node-key="id" :indent="indents">
						</el-tree>
  					</div>
  				</div>
  				<div class="l fix" v-else style="width:100%">
  					<el-form-item label="大纲名称" style="width: 40%;float:left">
	  					 <el-input v-model="levelName"></el-input>
					  </el-form-item>
					  <div class="l ">
							<el-button type="success" style="width:160px;margin-left: 30px;" class="pct70 l" @click="creatNew()">新建教材大纲</el-button>
					  </div>
					  <div class="l ">
							<el-button type="success" style="width:160px;margin-left: 30px;" class="pct70 l" @click="sysnCourse()">同步教材大纲</el-button>
					  </div>
  				</div>
  				
				</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="savecourse()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="dialogAdd = false">取消</div>								
				</div>
			</div>
		</el-dialog>
		
		<el-dialog :visible.sync="dialogInfo" :show-close="true" >
  		<div class="infoTitle">详情</div>
  		<div class="infoBody fix" v-model="course">
  			<div class="leftPart l">
  				<div class="fix">
	    			<div class="infoItems fix l">
	    				<div class="l part tr">课程名:</div>
	    				<div class="l part tl ell" :title="course.name">{{course.name}}</div>
	    			</div>
	    			<div class="infoItems fix l">
	    				<div class="l part tr">开始日期:</div>
	    				<div class="l part tl">{{timeF(course.startDate).format("YYYY-MM-DD HH:mm")}}</div>
	    			</div>
	    			<div class="infoItems fix l">
	    				<div class="l part tr">学校:</div>
	    				<div class="l part tl">{{course.schoolName}}</div>
	    			</div>
	    			<div class="infoItems fix l">
	    				<div class="l part tr">年级:</div>
	    				<div class="l part tl">{{course.gradeName}}</div>
	    			</div>
	    			<div class="infoItems fix l">
	    				<div class="l part tr">学科:</div>
	    				<div class="l part tl">{{course.subject}}</div>
	    			</div>
	    			<div class="infoItems fix l">
	    				<div class="l part tr">教师:</div>
	    				<div class="l part tl">{{course.teacherName}}</div>
	    			</div>
	    			<div class="infoItems fix l">
	    				<div class="l part tr">教材:</div>
	    				<div class="l part tl">{{course.name}}</div>
	    			</div>
	    		</div>
  			</div>
  			<div class="rightPart l fix">
  				<div class="l ml30">课程图片</div>
  				<div class="l rightImg">
  					<img :src="course.coverImg">
  				</div>
				</div>
				<div class="l el-form-item" style="width: 90%;margin-top:10px">
					<label class="el-form-item__label" style="width: 90px;padding-top:6px;color:#272727;">课程简介</label>
					<div class="el-form-item__content" style="margin-left: 90px;">
						<div class="bigSteel" style="height: 90px;line-height: 30px;">{{course.remark}}</div>
					</div>
				</div>
				<div class="l el-form-item" style="width: 90%;">
					<label class="el-form-item__label" style="width: 90px;padding-top:6px;color:#272727;">课程大纲</label>
					<div class="el-form-item__content" style="margin-left: 90px;">
						<el-tree id="courses"  default-expand-all :data="courseSyllabuss" :props="defaultProps" node-key="id" :indent="indents">
						</el-tree>
					</div>
				</div>
			</div>
		</el-dialog>
		
		<el-dialog :visible.sync="text_add" size="tiny" :show-close="true" >
  		<div class="infoTitle">添加大纲</div>
  		<div class="infoBody fix" >
  			<el-form  label-width="80px" :model="text_infos" :rules="rules" ref="ruleForms"  class="demo-form-inline">
  					<el-form-item label="大纲名称" prop="text_name">
					    <el-input v-model="text_infos.text_name"></el-input>
					  </el-form-item>
				</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="savetext()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="addTextClose()">取消</div>								
				</div>
			</div>
		</el-dialog>
    
  </div>
</template>

<script>
  export default{
    data () {
    	var validateText = (rule, value, callback) => {
        if (this.text_infos.text_name == '') {
          callback(new Error('请填入大纲名称'));
        }else{
        	callback();
        }
      };
      return {
        msg:'hello vue',
        optionSchool:null,
        optionGrand:null,
        optionTeacher:null,
        optionSubject:null,
        optionMaterial:null,
        optionSchool_val:'',
        optionGrand_val:'',
        optionTeacher_val:'',
        optionSubject_val:'',
        queryStartDate:'',
        queryEndDate:'',
        dialogAdd:false,
        dialogInfo:false,
        course:{},
        pageNum:1,
        pageSize:10,
        total:0,
        tableData:[],
        imageUrl:'',
        Url:'',
        baseUrl:'',
        labelPosition: 'right',
        infoTitles:'',
        
        indents:20,
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        courseSyllabuss:null,
        levelName:'',
        
        text_id:'',
        text_name:'',
        text_infos:{
        	 text_id:'',
    			 text_name:'',
        },
        text_add:false,
        courseId:'',
        rules:{
	        text_name:[
	        	{required: true, validator:validateText, trigger: 'blur' }
	        ]
        },
      }
    },
    methods: {
    	dialogInfos(id){
    		var data = {id:id}
    		this.dialogInfo = true;
    		this.postHttp(this,data,"course/queryCourseById",function(obj,data){
		  		obj.course = data.result;
		  	})
		  },
		  dialogEdits(id){
		  	var data = {id:id}
		  	this.dialogAdd = true;
		  	this.infoTitles = "编辑";
		  	this.postHttp(this,data,"course/queryCourseById",function(obj,data){
		  		obj.course = data.result;
		  		obj.courseId = obj.course.id;
		  		var datas = {courseId:obj.course.id}
	  			obj.postHttp(obj,datas,"coursesyllabus/queryCourseSyllabuss",function(obj,data){
	  				if(data.result.length==0){
	  					obj.courseSyllabuss = null;
	  				}else{
	  					obj.courseSyllabuss = data.result;
	  				}
	  			});
	  			obj.imageUrl=obj.baseUrl+obj.course.coverImg;
		  	})
		  },
		  dialogAdds(){
		  	this.course = {
	        	schoolId:'',
	        	gradeId:'',
	        	subject:'',
	        	teacherId:'',
	        	name:'',
	        	startDate:'',
	        	coverImg:'',
	        	materialId:'',
        	}
		  	this.courseSyllabuss = null;
		  	this.dialogAdd = true;
		  	this.infoTitles = "新增";
		  },
		  savecourse(){
		  	if(this.infoTitles=="新增"){
		  		var data = this.course
			  	data.startDate = this.timeF(data.startDate).format("YYYY-MM-DD HH:mm:ss");
			  	data.schoolName = document.getElementById("schoolName_in").getElementsByTagName("input")[0].value;
			  	data.gradeName = document.getElementById("gradeName_in").getElementsByTagName("input")[0].value;
			  	data.teacherName = document.getElementById("teacherName_in").getElementsByTagName("input")[0].value;
			  	delete data['createDate'];
			  	delete data['updateDate'];
			  	this.postHttp(this,data,"course/saveCourse",function(obj,data){
			  		if(data.code == '10000'){
			  			obj.pageNum = 1;
			  			obj.pageSzie = 10;
			  			ajax_data(obj);
			  			obj.dialogAdd = false;
			  			obj.notify_jr(obj,"新增课程","成功","success");
			  		}else{
			  			obj.notify_jr(obj,"新增课程","失败:"+data.message,"error");
			  		}
			  	})
		  	}else{
		  		var data = this.course;
		  		data.startDate = this.timeF(data.startDate).format("YYYY-MM-DD HH:mm:ss");
			  	data.schoolName = document.getElementById("schoolName_in").getElementsByTagName("input")[0].value;
			  	data.gradeName = document.getElementById("gradeName_in").getElementsByTagName("input")[0].value;
			  	data.teacherName = document.getElementById("teacherName_in").getElementsByTagName("input")[0].value;
			  	delete data['createDate'];
			  	delete data['updateDate'];
			  	this.postHttp(this,data,"course/updateCourse",function(obj,data){
			  		if(data.code == '10000'){
			  			obj.pageNum = 1;
			  			obj.pageSzie = 10;
			  			ajax_data(obj);
			  			obj.dialogAdd = false;
			  			obj.notify_jr(obj,"修改课程","成功","success");
			  		}else{
			  			obj.notify_jr(obj,"修改课程","失败:"+data.message,"error");
			  		}
			  	})
		  	}
		  	
		  },
		  initTabelDate(obj,data){
		  	this.tableData = data.result.list;
		    this.pageNum = data.result.pageNum;
		    this.pageSize = data.result.pageSize;
		    this.total = data.result.total;
		  },
    	handleSizeChange(val) {
		  	this.pageNum = 1;
  			this.pageSzie = val;
  			ajax_data(this);
		  },
		  handleCurrentChange(val) {
		  	this.pageNum = val;
  			ajax_data(this);
		  },
	  handleAvatarSuccess(res, file) {

        this.imageUrl = URL.createObjectURL(file.raw);
        console.log(this.course.imageUrl);
        this.course.imageUrl = res.result.path;
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isPNG = file.type === 'image/png';
        const isIMG = isJPG||isPNG;
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isIMG) {
          this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isIMG && isLt2M;
      },
      school_chage_q(){
				var id = this.optionSchool_val;
				this.optionGrand = '';
				this.optionGrand_val = null;
				if(id!=""){
					this.get_options(this,id,"optionGrand");
				}
	  	},
	  	clearSchool(){
	  		this.optionSchool_val = '';
	  		this.optionGrand = '';
			this.optionGrand_val = null;
	  	},
	  	clearGrand(){
	  		this.optionGrand_val = '';
	  	},
	  	clearTeacher(){
	  		this.optionTeacher_val = '';
	  	},
	  	user_handle(obj,data){
		  	this.optionTeacher = data.result.list;
		  },
		  object_handle(obj,data){
		  	this.optionSubject = data.result;
		  },
		  timeFormat(row,column){
		  	var date = row[column.property];  
		  	if (date == undefined) {  
		     return "";
		  	}  
		  	return this.timeF(date).format("YYYY-MM-DD HH:mm:ss");  
		  },
		  change_school(){
		  	var id = this.course.schoolId;
		  	this.optionGrand = null;
		  	this.get_options(this,id,"optionGrand");
		  },
		  change_subject(){
		  	var subject = this.course.subject
		  	var data = {subject:subject,pageNum:1,pageSize:50};
		  	this.postHttp(this,data,"material/queryMaterials",function(obj,data){
		  		obj.optionMaterial = data.result.list;
		  	})
		  },
		  sysnCourse(){
		  	var materialId = this.course.materialId;
		  	if(materialId==''){
		  		this.notify_jr(this,"提示","请先选择教材","warning");
		  		return;
		  	}
		  	if(this.courseId==""){
		  		var data = {materialId:materialId};
		  	}else{
		  		var data = {courseId:this.courseId,materialId:materialId}
		  	}
		  	this.postHttp(this,data,"materialsyllabus/copySyllabus",function(obj,data){
		  		if(data.code == '10000'){
		  			obj.notify_jr(obj,"同步教材大纲","成功","success");
		  			var courseId = data.result;
		  			var datas = {courseId:courseId}
		  			obj.courseId = courseId
		  			obj.postHttp(obj,datas,"coursesyllabus/queryCourseSyllabuss",function(obj,data){
		  				if(data.result.length==0){
		  					obj.courseSyllabuss = null;
		  				}else{
		  					obj.courseSyllabuss = data.result;
		  				}
		  			})
		  		}else{
		  			obj.notify_jr(obj,"同步教材大纲","失败:"+data.message,"error");
		  		}
		  	})
		  },
		renderContent(createElement, { node, data, store }) {
        var self = this;  
        return createElement('span', [  
            createElement('span', node.label),  
            createElement('span', {attrs:{  
                style:"float: right; margin-right: 20px",class:"tree_btn"  
            }},[  
                createElement('i',{attrs:{  
                    class:'el-icon-plus'
                },on:{  
                    click:function() {  
                    	self.text_id = data.id;
                    	self.text_add = true;
                    	self.text_name = "";
                    	self.text_infos.text_name = ""
                    }  
                }},""),  
                createElement('i',{attrs:{  
                     class:'el-icon-minus'  
                },on:{  
                    click:function() {  
                    	self.text_id = data.id;
                    	self.$confirm('此操作将删除该大纲下所有子项,是否继续?', '提示', {
							          confirmButtonText: '确定',
							          cancelButtonText: '取消',
							          type: 'warning'
							        }).then(() => {
							        	self.delete_text_s();
							        }).catch(() => {
							        	
							        });
                    }  
                }},""),  
            ]),  
        ]);  
      },
   		savetext(){
      	this.$refs['ruleForms'].validate((valid) => {
          if (valid) {
          	var data = {parentId:this.text_id,name:this.text_infos.text_name,courseId:this.courseId};
          	this.postHttp(this,data,"coursesyllabus/saveCourseSyllabus",function(obj,data){
          		if(data.code=="10000"){
			      		obj.text_add = false;
			      		obj.notify_jr(obj,"新增","操作成功","success");
			      		var datas = {courseId:obj.courseId}
				  			obj.postHttp(obj,datas,"coursesyllabus/queryCourseSyllabuss",function(obj,data){
				  				if(data.result.length==0){
				  					obj.courseSyllabuss = null;
				  				}else{
				  					obj.courseSyllabuss = data.result;
				  				}
				  			})
			      	}else{
			      		obj.notify_jr(obj,"新增",data.message,"error");
			      	}
          	});
          	
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      addTextClose(){
      	this.text_id = '';
	    	this.text_add = false;
	    	this.text_name = "";
      },
   		delete_text_s(){
      	var data = {id:this.text_id};
      	this.postHttp(this,data,"coursesyllabus/deleteCourseSyllabus",function(obj,data){
      		if(data.code=="10000"){
		      		obj.notify_jr(obj,"删除","操作成功","success");
		      		var data_code = {courseId:obj.courseId}
			  			obj.postHttp(obj,data_code,"coursesyllabus/queryCourseSyllabuss",function(obj,data){
			  				if(data.result.length==0){
			  					obj.courseSyllabuss = null;
			  				}else{
			  					obj.courseSyllabuss = data.result;
			  				}
			  			});
		      	}else{
		      		obj.notify_jr(obj,"删除",data.message,"error");
		      	}
      	});
      },
      creatNew(){
      	if(this.courseId!=''){
      		if(this.levelName==''){
		  			this.notify_jr(this,'提示','请输入一级大纲名字','warning');
			  		return;
			  	}
	      	var data = {name:this.levelName,level:1,parentId:'',courseId:this.courseId}
			  	this.postHttp(this,data,"coursesyllabus/saveCourseSyllabus",function(obj,datas){
			  		if(datas.code == '10000'){
			  			obj.notify_jr(obj,'成功','添加成功','success');
			  			var data_code = {courseId:obj.courseId}
			  			obj.course['id'] = obj.courseId;
			  			obj.postHttp(obj,data_code,"coursesyllabus/queryCourseSyllabuss",function(obj,data){
			  				if(data.result.length==0){
			  					obj.courseSyllabuss = null;
			  				}else{
			  					obj.courseSyllabuss = data.result;
			  				}
			  			});
			  		}else{
			  			obj.notify_jr(obj,'错误','网络错误','error');
			  		}
			  	});
      	}else{
      		this.postHttp(this,{},"getUUID",function(obj,datas){
			  		obj.courseId = datas.result;
			  		var name = obj.levelName;
				  	var level = 1;
				  	var parentId = '';
				  	if(obj.courseId==null){
				  		obj.notify_jr(obj,'提示','网络错误','warning');
				  		return;
				  	}
				  	if(name==''){
				  		obj.notify_jr(obj,'提示','请输入一级大纲名字','warning');
				  		return;
				  	}
				  	var data = {name:name,level:level,parentId:parentId,courseId:obj.courseId}
				  	obj.postHttp(obj,data,"coursesyllabus/saveCourseSyllabus",function(obj,datas){
				  		if(datas.code == '10000'){
				  			obj.notify_jr(obj,'成功','添加成功','success');
				  			var data_code = {courseId:obj.courseId}
				  			obj.course['id'] = obj.courseId;
				  			obj.postHttp(obj,data_code,"coursesyllabus/queryCourseSyllabuss",function(obj,data){
				  				if(data.result.length==0){
				  					obj.courseSyllabuss = null;
				  				}else{
				  					obj.courseSyllabuss = data.result;
				  				}
				  			});
				  		}else{
				  			obj.notify_jr(obj,'错误','网络错误','error');
				  		}
				  	});
			  	});
      	}
		  },
		  delete_course(id){
		  	this.$confirm('此操作将删除该课程,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        	var data = {id:id};
			  	this.postHttp(this,data,"course/deleteCourse",function(obj,data){
			  		if(data.code == '10000'){
			  			obj.notify_jr(obj,"删除","操作成功","success");
			  			ajax_data(obj);
			  		}else{
			  			obj.notify_jr(obj,"删除","失败:"+data.message,"success");
			  		}
			  	})
        }).catch(() => {
        	
        });
		  },
		sleectAll(){
			ajax_data(this);
		}
    },
    mounted:function(){
    	ajax_data(this);
    	this.get_options(this,"","optionSchool");
			var data_user = {role:'教师',pageNum:1,pageSize:30};
	  	this.postHttp(this,data_user,"user/queryUsers",this.user_handle);
	  	var data_code ={code:'SUBJECT'};
	  	this.postHttp(this,data_code,"dictionary/getDictionarysBySupCode",this.object_handle);
	  	this.Url=this.getBaseUrl()+"uploadFile/upload";
		this.baseUrl=this.getBaseUrl();
    }

  }
  
  
  
  function ajax_data(obj){
  	var pageNum = obj.pageNum;
  	var pageSize = obj.pageSize;
  	var schoolId = obj.optionSchool_val;
  	var gradeId = obj.optionGrand_val;
  	var teacherId = obj.optionTeacher_val;
  	var subject = obj.optionSubject_val;
  	var name = obj.name_q;
  	var fromDate = obj.timeF(obj.queryStartDate).format("YYYY-MM-DD HH:mm:ss");
  	var toDate = obj.timeF(obj.queryEndDate).format("YYYY-MM-DD HH:mm:ss");
  	if(fromDate == 'Invalid date'){
  		fromDate = null;
  	}
  	if(toDate == 'Invalid date'){
  		toDate = null;
  	}
  	var data = {
  		schoolId:schoolId,
  		gradeId:gradeId,
  		teacherId:teacherId,
  		subject:subject,
  		pageSize:pageSize,
  		pageNum:pageNum,
  		name:name,
  		fromDate:fromDate,
  		toDate:toDate
  	}
  	
  	obj.postHttp(obj,data,"course/queryCourses",function(obj,data){
  		obj.tableData = data.result.list;
	    obj.pageNum = data.result.pageNum;
	    obj.pageSize = data.result.pageSize;
	    obj.total = data.result.total;
  	})
  	
  }
</script>

<style>
  #courseList .el-input__icon {
    width: 55px;
  }
  #courseList .pct18{height:1px;}
  
  #courseList .main-container i{margin: auto 4px;font-size:22px;cursor:pointer;}
  
  #courseList .addBody{background:#fff;min-height: 500px;padding:20px 40px;}
  
  #courseList .leftPart{width:40%;}
  #courseList .rightPart{width:60%;}
  #courseList .avatar-uploader-icon{border:1px solid #bfcbd9;}
  #courseList .avatar-uploader-icon:hover{border-color:#66BB6A}
  #courseList .leftPart .el-date-editor.el-input{width:100%;}
  
  #courseList .bigSteel{width: 96%;border:1px #BFCBD9 solid;height: 200px;border-radius: 3px;padding:5px 2%;}
  #courseList .infoItems{width: 100%;}
  
  #courseList .ml30{margin-left:70px}
  #courseList .rightImg{width:122px;height: 140px;background:#D8D8D8;border: 1px solid #979797;margin-left:20px;}
  #courseList .infoItems .tr{width: 30%;}
  #addCrouse .el-select{width:100%}
  
  #courseList #courses i{font-size:14px;padding:3px;margin-right:20px;border-radius:2px;}
  #courseList .el-icon-plus{color:#EF5350;border:1px #EF5350 solid;}
	#courseList .el-icon-minus{color:#FFCA28;border:1px #FFCA28 solid;}
	#courseList .el-tree-node__expand-icon{border-left-color:#202a33;}
	
	#courseList .el-dialog--tiny .infoBody{min-height: 80px;padding-top:40px}
</style>