<template>
	<div id="live">
		<div class="live_header">
			<el-form ref="form" :model="form" label-width="60px">
				<el-form-item class="l ml30 mt20 live_select" label="学校">
				    <el-select v-model="form.schoolid"  @change="newGrades()" clearable>
				      <el-option v-for="item in schools" :key="item.id" :label="item.name" :value="item.id"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item class="l ml30 mt20 live_select" label="年级" clearable>
				    <el-select v-model="form.gradesid">
				      <el-option v-for="item in grades" :key="item.id" :label="item.name" :value="item.id"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item class="l ml30 mt20 live_select" label="教师" clearable>
				    <el-select v-model="form.teachersid">
				      <el-option v-for="item in teachers" :key="item.id" :label="item.name" :value="item.id"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item class="l ml30 mt20 live_select" label="学科" clearable>
				    <el-select v-model="form.subjectsid">
				      <el-option v-for="item in subjects" :key="item.id" :label="item.dicName" :value="item.dicCode"></el-option>
				    </el-select>
				</el-form-item>
				<div class="cl"></div>
				<el-form-item class="l ml30 live_select" label="节目名">
				    <el-input v-model="form.name" placeholder="请输入文字"></el-input>
				</el-form-item>
				<el-form-item class="l ml30" label="日期的" placeholder="请选择节目状态">
				    <el-col class="live_select_date">
				      <el-date-picker v-model="form.date1" type="datetime" placeholder="选择开始日期时间" style="width: 100%;"></el-date-picker>
				    </el-col>
				    <el-col class="tc live_select_date_pagging">-</el-col>
				    <el-col class="live_select_date">
				      <el-date-picker v-model="form.date2" type="datetime" placeholder="选择结束日期时间" style="width: 100%;"></el-date-picker>
				    </el-col>
				</el-form-item>
			</el-form>
			<el-button class="l ml93 query_button" type="primary" @click="query">查询</el-button>
			<div class="cl"></div>
		</div>
		<div class="live_body">
			<p class="live_body_title">直播列表</p>
			<div class="fix">
				<div class="live_body_content l" v-for="tab in tabs">
					<div class="live_body_content_bar l">
						<div class="l live_body_content_bar_img">
							<img :src="tab.courseImg" width="206px" height="274px">
						</div>
						<div class="l ml10">
							<p class="mt15 live_body_content_bar_p">{{tab.name}}</p>
							<div class="mt10 live_body_content_bar_vp">
								<p class="l">课程名：</p><p class="l">{{tab.courseName}}</p> <p class="l ml20">学校：</p><p class="l">{{tab.schoolName}}</p>
							</div>
							<div class="cl"></div>
							<div class="mt10 live_body_content_bar_vp">
								<p class="l">大纲进度：</p><p class="l">{{tab.syllabusName}}</p>
							</div>
							<div class="cl"></div>
							<div class="mt20 live_body_content_bar_vp">
								<p class="l">{{timeF(tab.startDate).format("YYYY/MM/DD hh:mm:ss")}}</p><p class="l ml20 live_body_content_bar_color">{{tab.state}}</p>
							</div>
							<div class="cl"></div>
							<div class="mt80 live_body_content_bar_vp">
								<p class="l">教师：</p><p class="l">{{tab.teacherName}}</p><p class="l ml20">播放:&nbsp总{{tab.playedNum}}次</p>
								<button v-if="tab.status=='1_ONGOING'" @click="tableButton(tab.id)" class="l mt-5 live_new_button"><span>观看直播</span></button>
								<button v-if="tab.status=='3_ENDED'"  @click="tableButton(tab.id)" class="l mt-5 live_new_button"><span>回看直播</span></button>
								<button v-if="tab.status=='PROGRAM_NOT_SUBSCRIBED'" class="l mt-5 live_new_button live_new_button_red"><span>预约</span></button>
								<button v-if="tab.status=='2_NOT_STARTED'" @click="tableAttion(tab.id)" class="l mt-5 live_new_button live_new_button_red"><span>预约</span></button>
								<button v-if="tab.status=='PROGRAM_SUBSCRIBED'" @click="tableAttion(tab.id)" class="l mt-5 live_new_button live_new_button_blue"><span>已预约</span></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
		<div class="cl live_foot tc">
			<el-pagination class="mt20" v-bind:current-Page="pageIndex" v-bind:page-size="pageSize" :total="total"
                    layout="total,sizes,prev,pager,next,jumper" v-bind:page-sizes="pageSizes" :current-page="pageIndex"
                    v-on:size-change="sizeChange" v-on:current-change="pageIndexChange"></el-pagination>
		</div>
	</div>
</template>

<script>
import page from '../member/page.vue'
export default {
  data () {
    return {
      form:{
      		name:'',
			gradesid:'',
			schoolid:'',
			teachersid:'',
			subjectsid:'',
		    date1: '',
		    date2: '',
		},
	  tabs:[],
		schools:[],
		grades:[],
		teachers:[],
		subjects:[],
		pageIndex:1,
        pageSize:10,
        total:0,
        pageSizes:[1,10,20,50,100],
    }
  },
  components:{page},
  created:function(){
  	this.newdata();
  },
  methods:{
  	newdata:function(){
  		this.postHttp(this,{type:"LIVE",pageNum:1,pageSize:10},"/program/study/queryStudyPrograms",function(obj,data){
     	obj.tabs=data.result.list;
     	obj.total=data.result.total;
    });
    this.postHttp(this,{parentId:"1293c5e6d1244c248b1635c9a98be564"},"/organization/study/getOrganizations",function(obj,data){
     	obj.schools=data.result;
    });
    this.postHttp(this,{role:"教师",pageNum:1,pageSize:30,},"study/user/queryUsers",function(obj,data){
     	obj.teachers=data.result.list;
    });
    this.postHttp(this,{code:"SUBJECT"},"dictionary/study/getDictionarysBySupCode",function(obj,data){
     	obj.subjects=data.result;
    });
  	},
  	newGrades:function(){
  	this.postHttp(this,{parentId:this.form.schoolid},"organization/getOrganizations",function(obj,data){
     	obj.grades=data.result;
    });
  	},
	query:function(){
		this.postHttp(this,{pageNum:1,pageSize:10,schoolId:this.form.schoolid,gradeId:this.form.gradesid,teacherId:this.form.teachersid,subject:this.form.subjectsid,name:this.form.name,queryStartDate:this.form.date1,queryEndDate:this.form.date2,type:"LIVE",},"/program/study/queryStudyPrograms",function(obj,data){
     	obj.tabs=data.result.list;
     	obj.total=data.result.total;
    });
	},
	sizeChange: function (pageSize) {   //每页显示条数
        this.pageSize = pageSize;
        this.fetchData();
    },
    pageIndexChange: function (pageIndex) {   //第几页
        this.pageIndex = pageIndex;
        this.fetchData();
    },
    fetchData:function(){
      this.postHttp(this,{pageNum:this.pageIndex,pageSize:this.pageSize,type:"LIVE",},"program/study/queryStudyPrograms",function(obj,data){
     	obj.tabs=data.result.list;
     	obj.total=data.result.total;
      });
      },
	tableButton:function(ids){
		this.$router.push({path:'/living/'+ids});
	},
	tableAttion:function(ids){
		this.postHttp(this,{programId:ids},"subscription/saveSubscription",function(obj,data){
			obj.newdata();
		})
	}
  },
  
}
</script>

<style>
#live{
	overflow: hidden;
}
#live .live_header{
	width: 1200px;
	height: 141px;
	margin:0 auto;
	margin-top:90px;
	border-radius: 3px;
	background:#fff;
}
#live .live_select .el-form-item__content{
	width: 200px;
}
#live .ml35{
	margin-left: 35px;
}
#live .live_select_date{
	width: 200px;
}
#live .live_select_date_pagging{
	width: 90px;
}
#live .el-button{
	padding: 6px 47px !important;
    border-radius: 25px !important;
}
#live .ml93{
	margin-left: 93px;
}
#live .mt10{
	margin-top: 10px;
}
#live .live_body{
	width: 1200px;
	margin: 0 auto;
	margin-top: 40px;
}
#live .live_body_title{
	font-size: 24px;
	color: #272727;
	padding-left:10px;
	height:40px;
	line-height: 40px;
	border-bottom: 2px #6ED56C solid;
}
#live .live_body_content{
	margin-top: 20px;
}
#live .live_body_content_bar{
	width: 590px;
	height: 274px;
	background-color: #ececec;
	margin:0 5px;
	box-shadow: 1px 1px 10px #c3c3c3;
}
#live .live_body_content_bar_img{
	height: 100%;
	width: 206px;
}
#live .live_body_content_bar_p{
	color:#272727;
	font-size: 16px;
}
#live .live_body_content_bar_vp p{
	color: #666;
	font-size: 14px;
}
#live .live_body_content_bar_color{
	color: #6ED56C !important;
}
#live .mt80{
	margin-top: 120px;
}
#live .mt-5{
	margin-top:-18px !important;
	margin-left: 43px !important;
}
#live .live_new_button{
	display: inline-block;
    line-height: 1;
    white-space: nowrap;
    cursor: pointer;
    background: #6ED56C;
    border: 1px solid #6ED56C;
    color: #fff;
    -webkit-appearance: none;
    text-align: center;
    box-sizing: border-box;
    outline: none;
    margin: 0;
    -moz-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;
    font-size: 14px;
    width: 125px;
    height: 35px;
    border-radius: 25px;

}
#live .live_new_button_red{
	 background: #F99BB7;
    border: 1px solid #F99BB7;
}
#live .live_new_button_blue{
	 background: #6BC6FB;
    border: 1px solid #6BC6FB;
}
#live .live_foot{
	overflow: hidden;
	margin: 0 auto;
	margin-bottom: 80px;
}
#live .query_button{
	width: 200px;
	height: 40px;
}
#live .live_body_content_bar_vp p{
	line-height: 17px;
}
</style>