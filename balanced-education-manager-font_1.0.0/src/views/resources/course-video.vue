<template>
  <div id="courseVideo" class="course-video-container">
  	
  	<ul class="tab-bor">
	    <li name="programPoi" class="poi active" @click="choiseType('video',$event)" >课堂视频</li>
	    <li name="programPoi" class="poi" @click="choiseType('supplementary',$event)">教辅文件</li>
	</ul>
    <el-row type="flex" class="row-bg pt20 pl20" justify="start">
        <el-col :span="4" class="pct25 mr20 ">
            <label class="mr20">学校  </label>
            <el-select v-model="optionSchool_val" class="pct70" placeholder="请选择学校" @change="change_school()" clearable>
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
                <el-option v-for="item in optionSubject" :key="item.dicName" :label="item.dicName" :value="item.dicName"></el-option>
            </el-select>
        </el-col>
    </el-row>

    <el-row type="flex" class="row-bg mt20 pl20" justify="start">
        <!-- 课程名 -->
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20">视频名</label>
            <el-input class="pct70" placeholder="请输入视频名" v-model="name_q"></el-input>
        </el-col>
        <!-- 开始 -->
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20">开始</label>
            <el-date-picker v-model="startTime" type="datetime" class="pct70" placeholder="选择开始日期"></el-date-picker>
        </el-col>
        <!-- 结束 -->
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20">——</label>
            <el-date-picker v-model="endTime" type="datetime" placeholder="选择结束时间"></el-date-picker>
        </el-col>
        <!-- 查询 -->
        <el-col :span="4" class="pct25">
        		<label class="mr20"></label>
            <el-button type="success" class="pct70 l" @click="queryVideo()">查询</el-button>
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
            <el-button type="primary" class="pct70 l" @click="upload()" :model="textChioce">{{textChioce}}</el-button>
        </el-col>
    </el-row>

    <div class="main-container">
      <!--表格部分-->
    	<el-table :data="tableData" stripe row-key="id" style="width:100%;margin-top: 20px;">
    		<el-table-column prop="schoolName" label="学校" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="gradeName" label="年级" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="subject" label="学科" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="teacherName" label="教师" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="name" label="资源名" align="center" show-overflow-tooltip width="200px"> </el-table-column>
    		<el-table-column prop="createDate" label="创建时间" :formatter=timeFormat align="center" show-overflow-tooltip min-width="160px"> </el-table-column>
    		<el-table-column prop="timeout" label="时长" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="ispublic" :formatter=isPublic label="是否公开" align="center" show-overflow-tooltip></el-table-column>
    		<el-table-column label="操作" align="center" show-overflow-tooltip min-width="150px">
    			<template scope="scope">
		    			<i title="编辑" class="el-icon-edit" @click="dialogEdits(scope.row.id)"></i>
		    			<i title="删除" class="el-icon-delete" @click="deletevideo(scope.row.id)"></i>
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
    
    <el-dialog :visible.sync="dialogUpload" :show-close="false" :close-on-click-modal="false" :close-on-press-escape="false">
  		<div class="infoTitle" :model="textChioce">{{textChioce}}</div>
  		<div class="editBody fix">
  			<el-form :inline="true" :model="course" class="demo-form-inline">
    			<div class="secondTitle">文件信息</div>
    			<el-form-item label="文件名">
    				
            <el-upload class="upload-demo" drag :action="urls"  ref="upload"  :auto-upload="true" :with-credentials="true" :before-upload="beforeupload" :on-success="upload_success">
            	<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
							
            </el-upload>
			      <!--<el-input v-model="course.name" icon="search"></el-input>-->
			    </el-form-item><br />
			    <el-form-item label="是否公开" style="width: 45%;padding-left: 4%;">
			    	<el-radio-group  style="margin-left:30px;" v-model="course.isPublic">
				      <el-radio :label="1" name="isCourse">是</el-radio>
				      <el-radio :label="0" name="isCourse">否</el-radio>
				    </el-radio-group>
			    </el-form-item><br  />
			  
			    <el-form-item label="资源类型">
			        <el-input class="pct70" placeholder="自动获取" readonly v-model="course.category"></el-input>
			    </el-form-item>
			    <el-form-item label="资源简介" class="moreinfo">
				    <el-input type="textarea" :rows="4" resize="none" v-model="course.remark"></el-input>
				  </el-form-item>
			    <div class="secondTitle">当前文件信息</div>
			    <el-form-item label="学科" >
			        <el-select v-model="course.subject" @change="change_subject()">
				      <el-option v-for="item in optionSubject" :key="item.dicCode" :label="item.dicName" :value="item.dicCode"></el-option>
				    </el-select>
			    </el-form-item>
			    <el-form-item label="" >
			    </el-form-item>
			    <el-form-item label="大纲" >
			       <el-cascader expand-trigger="click" :options="optionCascader" v-model="course.courseSyllabusIdArray"></el-cascader>
			    </el-form-item>
    		</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="savevideo()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="dialogUpload = false">取消</div>								
				</div>
			</div>
		</el-dialog>
		
		
		<el-dialog :visible.sync="dialogEdit" :show-close="false" :close-on-click-modal="false" :close-on-press-escape="false">
  		<div class="infoTitle">修改资源</div>
  		<div class="editBody fix">
  			<el-form :inline="true" :model="course" class="demo-form-inline">
  				<div class="secondTitle">文件信息</div>
  				
  				<el-form-item label="是否公开" style="width: 45%;padding-left: 4%;">
			    	<el-radio-group  style="margin-left:30px;" v-model="course.isPublic">
				      <el-radio :label="1" name="isCourse">是</el-radio>
				      <el-radio :label="0" name="isCourse">否</el-radio>
				    </el-radio-group>
			    </el-form-item>
			    
			    <el-form-item label="资源简介" class="moreinfo">
				    <el-input type="textarea" :rows="4" resize="none" v-model="course.remark"></el-input>
				  </el-form-item>
				  <div class="secondTitle">当前资源信息</div>
			    <el-form-item label="学科" >
			        <el-select v-model="course.subject" @change="change_subject()">
				      <el-option v-for="item in optionSubject" :key="item.dicCode" :label="item.dicName" :value="item.dicCode"></el-option>
				    </el-select>
			    </el-form-item> 
			    <el-form-item label="" >
			    </el-form-item>
			    <el-form-item label="大纲" >
			    	<el-cascader expand-trigger="click" :options="optionCascader" v-model="course.courseSyllabusIdArray"></el-cascader>
			    </el-form-item>
				  
  			</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="editvideo()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="dialogEdit = false">取消</div>								
				</div>
			</div>
		</el-dialog>
  </div>
</template>

<script>
  export default{
    data () {
      return {
      	urls:"",
        msg:'hello vue',
        optionSchool:null,
        optionGrand:null,
        optionSubject:null,
        optionTeacher:null,
        optionTeacher_val:'',
        optionSchool_val:'',
        optionGrand_val:'',
        optionSubject_val:'',
      	name_q:'',
      	type:1,
      	isVedio:1,
       
        startTime:'',
        endTime:'',
        optionss: [{
        }],
        selectedOptions:[],
        
        textChioce:'上传课堂视频',
        
        dialogUpload:false,
        dialogEdit:false,
        supplementary:false,
        
        pageNum:1,
        pageSize:10,
        total:0,
        
        tableData:[],
		    course:{},
		    optionCascader:[],
      }
    },
		methods:{
			upload(){
				this.dialogUpload = true;
				this.course={
		    	isPublic:1,
		    	schoolId:'',
		    	subject:'',
		    	gradeId:'',
		    	teacherId:'',
		    }
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
				if(type=="supplementary"){
					this.textChioce = "上传教辅文件";
					this.supplementary = true;
					this.type = 2;
					this.isVedio = 0;
				}else{
					this.textChioce = "上传课堂视频";
					this.supplementary = false;
					this.type = 1;
					this.isVedio = 1;
				}
				initTable(this);
			},
			isPublic(row, column){
				if(row.isPublic=="1"){
					return "是";
				}else{
					return "否";
				}
			},
			timeFormat(row, column){
				var date = row[column.property];  
		  	if (date == undefined) {  
		     return "";  
		  	}  
		  	return this.timeF(date).format("YYYY-MM-DD HH:mm:ss");  
			},
			dialogEdits(id){
				this.dialogEdit = true;
				var data = {id:id};
				this.postHttp(this,data,"teachingfile/getTeachingFileById",function(obj,data){
					obj.course = data.result;
				});
			},
			beforeupload(file){
				var upFileName = file.name;
        var index1=upFileName.lastIndexOf(".");
        var index2=upFileName.length;
        var suffix=upFileName.substring(index1+1,index2);//后缀名
        if(this.supplementary){
        	if(suffix == "docx" || suffix == "doc" || suffix == "xlsx" || suffix == "xls" || suffix == "ppt" || suffix == "pdf"){
					}else{
						 this.notify_jr(this,"提示","上传文件格式错误","warning")
						  return false;
					}	
        }else{
        	if(suffix == "mp4"){
					}else{
						 this.notify_jr(this,"提示","上传视频格式必须为MP4","warning")
						  return false;
					}	
        }
				
				var objs =document.getElementsByClassName("el-upload-list__item")[0];
				if(objs!=undefined){
					objs.remove();
				}
				
				return true;
			},
			upload_success(response, file, fileList){
				var code = response.code;
				if(code=="60000" || code=="50000"){
					this.$router.push({ path: '/login' });
				}else if(code=="10000"){
					this.notify_jr(this,"提示","上传文件成功","success");
					this.course["path"] = response.result.path;
					if(response.result.isVedio=='1'){
						this.course["duration"] = response.result.duration;
					}
					this.course["isVedio"] = response.result.isVedio;
					this.course["name"] = response.result.fileName
					var codeS = response.result.fileName.split(".")[1].toLowerCase();
					if(codeS == "mp4"){
						this.course["category"] = 'MP4';
						return;
					}
					if(codeS == "docx" || codeS == "doc" ){
						this.course["category"] = 'DOC';
						return;
					}
					if(codeS == "xlsx" || codeS == "xls" ){
						this.course["category"] = 'EXCEL';
						return;
					}
					if(codeS == "ppt"){
						this.course["category"] = 'PPT';
						return;
					}
					if(codeS == "pdf" ){
						this.course["category"] = 'PDF';
						return;
					}
					this.course["category"] = 'OTHER';
				}else{
					this.notify_jr(this,"提示","失败"+response.message,"error");
				}
			},
			change_school(){
		  	var id = this.optionSchool_val;
		  	this.optionGrand = null;
		  	this.optionGrand_val = "";
		  	this.get_options(this,id,"optionGrand");
			},
		  user_handle(obj,data){
		  	this.optionTeacher = data.result.list;
		  },
		  queryVideo(){
		  	this.pageNum = 1;
		  	this.pageSize = 10;
		  	initTable(this);
		  },
		  handleSizeChange(val) {
		  	this.pageSize = val;
		  	initTable(this);
		  },
		  handleCurrentChange(val) {
		  	this.pageNum = val;
		  	initTable(this);
		  },
		  change_subject(){
		  	var subject = this.course.subject;
		  	var data = {subject:subject};
		  	this.postHttp(this,data,"coursesyllabus/queryCSCascader",function(obj,data){
		  		obj.optionCascader = data.result;
		  	})
		  },
		  savevideo(){
		  	if(this.course.path=="" || this.course.path==undefined){
		  		this.notify_jr(this,"提示","请上传文件","warning");
		  		return;
		  	}
		  	var arrays = this.course.courseSyllabusIdArray;
		  	if(arrays==undefined){
		  		this.notify_jr(this,"提示","请选择绑定课程大纲","warning");
		  		return;
		  	}
		  	this.course.courseId = arrays[0];
		  	this.course.courseSyllabusId = arrays[(arrays.length-1)];
		  	
		  	this.course.type = this.type;
		  	this.course.isVedio = this.isVedio;
		  	
		  	var data = this.course;
		  	this.postHttp(this,data,"teachingfile/saveTeachingFile",function(obj,data){
		  		if(data.code == '10000'){
		  			obj.notify_jr(obj,"新增","操作成功","success");
		  			initTable(obj);
		  			obj.dialogUpload = false;
		  		}else{
		  			obj.notify_jr(obj,"新增","失败:"+data.message,"success");
		  		}
		  	})
		  },
		  editvideo(){
		  	var arrays = this.course.courseSyllabusIdArray;
		  	if(arrays==undefined){
		  		this.notify_jr(this,"提示","请选择绑定课程大纲","warning");
		  		return;
		  	}
		  	this.course.courseId = arrays[0];
		  	this.course.courseSyllabusId = arrays[(arrays.length-1)];
		  	
		  	var data = {
		  		id:this.course.id,
		  		name:this.course.name,
		  		isPublic:this.course.isPublic,
		  		courseId:this.course.courseId,
		  		courseSyllabusId:this.course.courseSyllabusId,
		  		remark:this.course.remark
		  	}
		  	this.postHttp(this,data,"teachingfile/updateTeachingFile",function(obj,data){
		  		if(data.code == '10000'){
		  			obj.notify_jr(obj,"修改","操作成功","success");
		  			initTable(obj);
		  			obj.dialogEdit = false;
		  		}else{
		  			obj.notify_jr(obj,"修改","失败:"+data.message,"success");
		  		}
		  	})
		  },
		  deletevideo(id){
		  	this.$confirm('此操作将删除该资源,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        	var data = {id:id};
			  	delete data["createDate"];
			  	this.postHttp(this,data,"teachingfile/deleteTeachingFile",function(obj,data){
			  		if(data.code == '10000'){
			  			obj.notify_jr(obj,"删除","操作成功","success");
			  			initTable(obj);
			  			obj.dialogEdit = false;
			  		}else{
			  			obj.notify_jr(obj,"删除","失败:"+data.message,"success");
			  		}
			  	})
        }).catch(() => {
        	
        });
		  	
		  }
		},
		
		mounted:function(){
    	this.get_options(this,"","optionSchool");
    	
			var data_user = {role:'教师',pageNum:1,pageSize:30};
	  	this.postHttp(this,data_user,"user/queryUsers",this.user_handle);
	  	
			var data_code ={code:'SUBJECT'};
  		this.postHttp(this,data_code,"dictionary/getDictionarysBySupCode",function(obj,data){
  			obj.optionSubject = data.result;
  		});
  		
  		initTable(this);
  		
  		var baseUrl = this.getBaseUrl();
			this.urls = baseUrl+'uploadFile/upload';
		}
  }
  
  function initTable(obj){
  	var data = ajaxData(obj);
		obj.postHttp(obj,data,"teachingfile/queryTeachingFiles",function(obj,data){
			obj.tableData = data.result.list;
			obj.pageSize = data.result.pageSize;
			obj.pageNum = data.result.pageNum;
			obj.total = data.result.total;
		})
  }
  
  function ajaxData(obj){
  	var schoolId = obj.optionSchool_val;
  	var gradeId = obj.optionGrand_val;
  	var teacherId = obj.optionTeacher_val;
  	var subject = obj.optionSubject_val;
  	var pageSizeStr = obj.pageSize;
  	var pageNumStr = obj.pageNum;
  	var name = obj.name_q;
  	var type = obj.type;
  	var isVedio = obj.isVedio;
  	var data = {
  		schoolId:schoolId,
  		gradeId:gradeId,
  		teacherId:teacherId,
  		subject:subject,
  		pageSizeStr:pageSizeStr,
  		pageNumStr:pageNumStr,
  		name:name,
  		type:type,
  		isVedio:isVedio
  	}
  	
  	return data;
  	
  }
</script>

<style>
  #courseVideo .el-input__icon {
    width: 55px;
  }
  #courseVideo .pct18{height:1px;}
  #courseVideo .main-container i{margin: auto 4px;font-size:22px;cursor:pointer;}
  
  #courseVideo .tab-bor .active{font-weight: normal;}
  #courseVideo .moreinfo{width:100%!important;}
  #courseVideo .moreinfo label{width:17%!important;}
  #courseVideo .editBody{background: #fff;min-height: 500px;padding:10px 40px;}
  #courseVideo .el-form--inline .el-form-item{margin-right:0px;width:49%;}
  #courseVideo .el-form-item__label{width:35%}
	#courseVideo .el-form-item__content{width:61%;}
	#courseVideo .el-cascader.is-opened .el-input__inner{border-color: #66BB6A;width:400px}
	#courseVideo .el-cascader .el-input__inner{width: 400px;}
	#courseVideo .el-upload-dragger{height:40px;border:solid 1px #bfcbd9}
</style>