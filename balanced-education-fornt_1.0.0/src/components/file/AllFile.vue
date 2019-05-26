<template>
	<div id="allFile" class="main_body">
		<div class="fileSelect">
			<div class="selectItem">
				<div class="itemList">
          <span class="itemLabel">学校:</span>
          <div class="itemDetail">
            <span :class="active3 == -1? 'actives': ''" @click="chooseSchool(-1)">全部</span>
            <span v-for="item in School" :key="item.id" :class="active3 == item.id? 'actives': ''" @click="chooseSchool(item.id)">{{item.name}}</span>
          </div>
        </div>
				<div class="itemList">
					<span class="itemLabel">学科:</span>
          <div  class="itemDetail">
          <span :class="active2 == -1? 'actives': ''" @click="chooseSubject(-1)">全部</span>
					<span v-for="item in Subject" :key="item.id" :class="active2 == item.dicCode? 'actives': ''" @click="chooseSubject(item.dicCode)">{{item.dicName}}</span>
          </div>
				</div>
				<div class="itemList">
          <span class="itemLabel">年级:</span>
            <div class="itemDetail">
              <span :class="active == -1? 'actives': ''" @click="chooseGrade(-1)">全部</span>
              <span v-for="item in Grade" :key="item.id"  :class="active == item.id? 'actives': ''" @click="chooseGrade(item.id)">{{item.name}}</span>
            </div>
        </div>
			</div>
			<el-row>
			  <el-col :span="8">
			  	<div class="labelItem">
				<label>老师</label>
				<el-select v-model="itemList.select1" clearable @change="chooseTeacher" @clear="clearTeacher" filterable placeholder="请选择">
				    <el-option
				      v-for="item in select.options1"
				      :key="item.id"
				      :label="item.name"
				      :value="item.id">
				    </el-option>
				 </el-select>
			 </div>
			  </el-col>
			  <el-col :span="8">
			  <div class="labelItem">
				<label>课程</label>
				<el-select v-model="itemList.select2" clearable @change="chooseCourse" @clear="clearCourse" filterable placeholder="请选择">
				    <el-option
				      v-for="item in select.options2"
				      :key="item.id"
				      :label="item.name"
				      :value="item.id">
				    </el-option>
				 </el-select>
			 </div></el-col>
			  <el-col :span="8">
			  <div class="labelItem">
				<label>教辅类型</label>
				<el-select v-model="itemList.select3" clearable @change="chooseFilesType" @clear="clearFilesType" filterable placeholder="请选择">
				    <el-option
				      v-for="item in select.options3"
				      :key="item.id"
				      :label="item.dicName"
				      :value="item.dicCode">
				    </el-option>
				 </el-select>
			 </div></el-col>
			</el-row>
		</div>
		<div class="fileMain">
			<div class="filetitle">
				<span class="itemLabel">排序:</span>
				<span @click="updataTime('updata')" :class="active4 == 'updata'? 'actives': ''">更新时间</span>
				<span @click="downLoad('download')" :class="active4 == 'download'? 'actives': ''">下载量</span>
        <span class="totle">共{{total}}份文件</span>
			</div>
			<div v-if="total != 0" class="fileList">
				<el-row :gutter="20">
				  <el-col :span="12" v-for="item in curriculum" :key="item.id">
				  	<div class="file-content">
				  		<div class="file-img inline__box">
				  			<img v-bind:src="item.imgsrc"/>
				  		<!-- 	<img v-if="item.suffix=='png'" src="../../../static/img/defualt/img.png" />
				  			<img v-if="item.suffix=='fla'" src="../../../static/img/defualt/swf.png" />
				  			<img v-if="item.suffix=='mp4'" src="../../../static/img/defualt/voice.png" />
				  			<img v-if="item.suffix=='avi'" src="../../../static/img/defualt/video.png" />
				  			<img v-if="item.suffix=='doc'||item.suffix=='ppt'" src="../../../static/img/defualt/doc.png" />
               <img v-if="item.suffix=='exe'" src="../../../static/img/defualt/exe.png" /> -->
				  		</div><div class="file-detail inline__box">
							<div class="file-title">
								【<span v-if="item.category == 'COURSEWARE'">课件</span><span v-if="item.category == 'REFERENCE_MATERIALS'">参考资料</span><span v-if="item.category == 'HOMEWORK'">作业</span><span v-if="item.category == 'TEST_QUESTIONS'">试题</span><span v-if="item.category == 'OTHER'">其他</span>】 {{item.name}}
							</div>
							<div class="file-config">
								课程：{{item.courseName}}
							</div>
							<div class="file-config">
								大纲：{{item.courseSyllabusName}}
							</div>
							<div class="file-config">
								<span>
									老师：{{item.teacherName}}
								</span>
								<span>
									文件类型：{{item.suffix}}
								</span>
								<span>
									下载量：{{item.downloads}}
								</span>
							</div>
							<div class="file-config">
								更新日期：{{timeF(item.updateDate).format("YYYY/MM/DD")}}
								<button @click="downloadC(item.id)">下载</button>
							</div>
				  		</div>
				  	</div>
				  </el-col>
        </el-row>
				<div class="cl  tc">
           <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="1"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
            </el-pagination>
				</div>
			</div>
      <div v-else class="fileList">
        <el-row :gutter="20">
          <el-col :span="4" :offset="10">
            <img src="../../../static/img/defualt/noRecord.png" />
          </el-col>
        </el-row>
      </div>
		</div>
	</div>
</template>

<script>
export default {
  data () {
    return {
    	/** 筛选条件的选中状态设置 */
    	active: -1,
    	active2:-1,
    	active3:-1,
    	active4:'updata',
    	/** 筛选条件的选中状态设置 */
    	/** 年级选择 */
      	Grade:{},
      	/** 年纪选择结束 */
      	/** 学科选择 */
      	Subject:{},
      	/** 学科选择结束 */
      	/** 学校选择 */
      	School:{},
      	/** 学校选择结束 */
      	/** 老师课程教辅 */
      	select:{
      	options1:{},
      	options2:{},
      	options3:{}
        
        },
        itemList:{
          grade:'',
          subject:'',
          school:'',
          select1:'',
          select2:'',
          select3:''
        },
        /** 老师课程结束 */
		/** 课程内容开始 */
		 curriculum:{},
     suffix:{},
         /** 课程内容结束 */
       pageIndex:1,
	     pageSize:10,
       tab:'UPDATE_DATE',
	     total:1
  	}
  },
  mounted:function(){
     this.itemInit();
     this.filesInit();
    
  },
  methods:{
		  chooseGrade(val) {
	      this.active = val;
        if(val == -1)
          this.itemList.grade = '';
        else
          this.itemList.grade = val;
        this.filesInit();
        this.itemInit();
        this.itemList.select1 = '';
        this.itemList.select2 = '';
      },
      chooseSubject(val){
		    this.active2 = val;
        if(val == -1)
          this.itemList.subject = '';
        else
          this.itemList.subject = val;
        this.filesInit();
        this.itemInit();
        this.itemList.select1 = '';
        this.itemList.select2 = '';
	    },
	    chooseSchool(val){
	      this.active3 = val;
        if(val == -1)
          this.itemList.school = '';
        else
          this.itemList.school = val;
        this.filesInit();
        this.itemInit();
        this.itemList.select1 = '';
        this.itemList.select2 = '';
	    },
      chooseTeacher(val){
        this.itemList.select1 = val;
        var teacherId ={teacherId:val}
        this.postHttp(this,teacherId,"teachingfile/study/initParamList",function(obj,data){
          obj.select.options2 = data.result.courseList;
          obj.itemList.select2 = ''
        });
        this.filesInit();
      },
      clearTeacher(){
        this.itemList.select1 = '';
        var teacherId ={teacherId:''}
        this.postHttp(this,teacherId,"teachingfile/study/initParamList",function(obj,data){
          obj.select.options2 = data.result.courseList;
          obj.itemList.select2 = ''
        });
        this.filesInit();
      },
      chooseCourse(val){
        this.itemList.select2 = val;
        this.filesInit();
      },
      clearCourse(){
        this.itemList.select2 = '';
        this.filesInit();
      },
      chooseFilesType(val){
        this.itemList.select3 = val;
        this.filesInit();
      },
      clearFilesType(){
        this.itemList.select3 = '';
        this.filesInit();
      },
	    updataTime(strs){
	    	this.active4 = strs;
	      this.pageIndex = 1;
        this.tab = 'UPDATE_DATE';
        this.filesInit();
	    },
	    SubjectType(strs){
	    	this.active4 = strs;
	    },
	    downLoad(strs){
	    	this.active4 = strs;
        this.pageIndex = 1;
        this.tab = 'DOWNLOADS';
        this.filesInit();
	    },
	    downloadC(val){
        var ids = {id:val};
	    	this.postHttp(this,ids,"teachingfile/study/downloadFile",function(obj,data){
          if(data.code=='40000'){
            alert(data.message);
          }else{
            console.log('下载成功')
          }
        });
	    },
	    handleSizeChange:function(val){
        this.pageSize = val;
        this.pageIndex = 1;
        this.filesInit();
      },
      handleCurrentChange:function(val){
        this.pageIndex = val;
        this.filesInit();
      },
      filesInit:function(){
        var needData = {gradeId:this.itemList.grade,
                        subject:this.itemList.subject,
                        schoolId:this.itemList.school,
                        teacherId:this.itemList.select1,
                        courseId:this.itemList.select2,
                        category:this.itemList.select3,
                        pageNum:this.pageIndex,
                        pageSize:this.pageSize,
                        tab:this.tab}
        this.postHttp(this,needData,"teachingfile/study/queryTeachingFilesByType",function(obj,data){
          obj.curriculum = data.result.list;
          obj.total = data.result.total;
          if(data.result.total >0){
            for(var i = 0;i<obj.curriculum.length;i++){
             obj.curriculum[i].imgsrc = "../../../static/img/defualt/"+obj.curriculum[i].suffix+".png";
            }
          }
        });
      },
      itemInit:function(){
        var needList = {gradeId:this.itemList.grade,subject:this.itemList.subject,schoolId:this.itemList.school}
        this.postHttp(this,needList,"teachingfile/study/initParamList",function(obj,data){
         obj.Grade = data.result.gradeList;
         obj.Subject = data.result.subjectList;
         obj.School = data.result.schoolList;
         //选项清空
         obj.select.options1 = data.result.teacherList;
         obj.select.options2 = data.result.courseList;
         obj.select.options3 = data.result.teachingFileList;
        });
      
      }
	}
}
</script>

<style>

#allFile {padding-bottom: 40px}
#allFile .fileSelect{margin-top:30px;border: 1px solid #E5E5E5;border-radius: 3px;background: #fff;padding:0 40px;}
#allFile .selectItem{}
#allFile .file-img{width: 200px;height: 200px}
#allFile .itemList{padding:16px 0 0 0;border-bottom:1px dashed #e5e5e5;}
#allFile .el-row{margin:16px 0;}
#allFile .fileSelect .actives,#allFile .fileMain .actives{color:#6ED56C;}
#allFile .itemDetail{display:inline-block;width:1050px}
#allFile .itemLabel{color:#999;cursor: default;vertical-align: top}
#allFile .itemList span{margin-right: 20px;display: inline-block;margin-bottom: 16px;cursor: pointer;font-size: 16px}
#allFile .totle{float: right;font-size: 16px;color: #999}
#allFile .labelItem label,.filetitle .itemLabel{color: #999;margin-right: 20px}
#allFile .fileMain .filetitle{padding: 40px 0 10px 0;border-bottom: 2px #6ED56C solid}
#allFile .filetitle span{margin-right: 20px;display: inline-block;cursor: pointer;font-size: 16px}
#allFile .fileList .el-col{margin-bottom: 20px}
#allFile .fileList .file-content{height: 200px;background-color: #f5f5f5}
#allFile .file-detail{padding:16px 10px 4px 18px;height: 180px;vertical-align: top}
#allFile .file-title{margin-bottom: 15px;font-size: 16px;color: #272727}
#allFile .file-config{color: #999;font-size: 14px;margin-bottom:15px; }
#allFile .file-config button{border:1px solid #6ED56C;border-radius: 25px;height: 35px;width: 125px;vertical-align: bottom;color: #fff;background-color: #6ed56c;font-size: 16px;outline: none;margin-left: 85px}
#allFile .file-config span{margin-right: 18px}
</style>