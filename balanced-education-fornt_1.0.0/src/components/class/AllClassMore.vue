<template>
	<div id="allClass">
		<div class="allClass_body">
			<p class="allClass_body_title">{{book.name}}</p>
			<hr style="width:93%;background-color:#E5E5E5;margin-top:20px;">
			<div class="allClass_body_content">
				<div class="allClass_body_content_img l">
					<img src="book.coverImg" width="100%" height="100%">
				</div>
				<div class="allClass_body_content_word l">
					<p class="l allClass_body_content_word_color">年级：</p><p class="l">{{book.gradeName}}</p>
					<div class="cl"></div>
					<p class="l allClass_body_content_word_color">学科：</p><p class="l">{{book.subjectName}}</p>
					<div class="cl"></div>
					<p class="l allClass_body_content_word_color">教材：</p><p class="l">{{book.name}}</p>
					<div class="cl"></div>
					<p class="l allClass_body_content_word_color">授课学校：</p><p class="l">{{book.schoolName}}</p>
					<div class="cl"></div>
					<p class="l allClass_body_content_word_color">授课老师：</p><p class="l">{{book.teacherName}}</p>
					<div class="cl"></div>
					<p class="l allClass_body_content_word_color">开课日期：</p><p class="l">{{timeF(book.startDate).format("YYYY/MM/DD hh:mm:ss")}}</p>
					<div class="cl"></div>
					<p class="l allClass_body_content_word_color">学习时长：</p><p class="l">{{book.courseDuration}}</p>
					<div class="cl"></div>
					<p class="l allClass_body_content_word_color">播放量：</p><p class="l">{{book.playedNum}}</p>
					<div class="cl"></div>
					<p class="l allClass_body_content_word_color">关注：</p><p class="l">{{book.concernedNum}}</p>
					<div class="cl"></div>
					<div class="allClass_body_content_button">
						<el-button class="attention_button_public" @click="attentionButton(book.id)" type="primary">进入学习</el-button>
						<el-button v-if="book.buttonName==false" @click="attention(book.id)" class="attention_button_public attention_button">报名</el-button>
						<el-button v-if="book.buttonName==true" class="attention_button_public attention_button_sure" type="primary">已报名</el-button>
					</div>
				</div>
				<div class="cl"></div>
			</div>
			<div class="allClass_body_tabs">
				<template>
				  <el-tabs v-model="activeName" @tab-click="handleClick">
				    <el-tab-pane label="课程内容" name="first">
				    	<h3>课程简介</h3>
				    	<p class="mt10">{{tabs1.remark}}</p>
				    	<h3 class="mt10">课程大纲</h3>
				    	<!-- <p class="mt10 allClass_body_tabs_download">点击下载课程视频进入学习</p> -->
				    	<div class="mt10 allClass_body_tabs_first_content">
				    		<div class="mt10 allClass_body_tabs_first_middle cl" v-for="proper in propers">
				    			<p class="l mt10">{{proper.name}}</p>
				    			<p class="r mt10">{{proper.duration}}</p>
				    			<div class="cl"></div>
				    			<div class="mt10 allClass_body_tabs_first_middle_body cl" v-for="anotherchild in proper.teachingFiles">
									<p class="l allClass_body_tabs_first_middle_bodyVipS mt10">{{anotherchild.name}}</p>
				    				<p class="r mt10">{{anotherchild.duration}}</p>
				    				<div class="cl"></div>
				    			</div>
				    			<div class="cl"></div>
				    			<div v-for="child in proper.childCourseSyllabus">
				    			<div class="mt10 allClass_body_tabs_first_middle_body cl">
				    				<p class="l allClass_body_tabs_first_middle_bodyVip mt10">{{child.name}}</p>
				    				<p class="r mt10">{{child.duration}}</p>
				    				<div class="cl"></div>
				    				<div class="mt10 allClass_body_tabs_first_middle_foot cl" v-for="grandchildren in child.teachingFiles">
				    					<p class="l allClass_body_tabs_first_middle_bodyVVip mt10">{{grandchildren.name}}</p>
				    					<p class="r mt10">{{grandchildren.duration}}</p>
				    					<div class="cl"></div>
				    				</div>
				    			</div>
				    			<div class="cl"></div>
								</div>
				    		</div>
				    	</div>
				    </el-tab-pane>
				    <el-tab-pane label="授课老师" name="second">
				    	<h3>教师简介</h3>
				    	<div class="allClass_body_tabs_Vimg mt10 l">
				    		<img :src="teacherImg" width="125px" height="155px">
				    	</div>
				    	<p class="l allClass_body_tabs_word ml10 mt10">{{lessons}}</p>
				    	<div class="cl"></div>
				    	<h3 class="mt10">所授课程</h3>
				    	<p class="mt10 allClass_body_tabs_download">点击下载课程视频进入学习</p>
				    	<div class="allClass_body_tabs_second_table">
				    		<div class="allClass_body_tabs_second_table_bar l" v-for="tab in tabs">
				    			<div class="allClass_body_tabs_second_table_bar_img">
				    				<img src="tab.coverImg" width="100%" height="100%">
				    			</div>
				    			<p>{{tab.name}}</p>
				    			<p class="l">开课日期：</p><p class="l">{{timeF(tab.startDate).format("YYYY/MM/DD hh:mm:ss")}}</p>
				    			<p class="mr10 r">{{tab.playedNum}}</p><p class="r">播放次数：</p>
				    			<div class="cl"></div>
				    			<p class="l">学习时长：</p><p class="l">{{tab.courseDuration}}</p>
				    		</div>
				    	</div>
				    	<div class="cl"></div>
				    	<div class="allClass_body_tabs_second_pagging tc">
				    		<el-pagination v-bind:current-Page="pageIndexN" v-bind:page-size="pageSizeN" :total="totalN"
								layout="total,sizes,prev,pager,next,jumper" v-bind:page-sizes="pageSizesN" :current-page="pageIndexN"
								v-on:size-change="sizeChangeN" v-on:current-change="pageIndexChangeN"></el-pagination>
				    	</div>
				    </el-tab-pane>
				    <el-tab-pane label="教辅下载" name="third">
				    	<h3>教辅文件</h3>
				    	<p class="mt10 allClass_body_tabs_download">点击下载课程视频进入学习</p>
				    	<div class="mt10 allClass_body_tabs_first_content">
				    		<div class="mt10 allClass_body_tabs_first_middle cl" v-for="proper in propers">
				    			<p class="l mt10">{{proper.name}}</p>
				    			<p class="r mt10">{{proper.duration}}</p>
				    			<div class="cl"></div>
				    			<div class="mt10 allClass_body_tabs_first_middle_body cl" v-for="anotherchild in proper.teachingFiles">
									<a :href="anotherchild.path"><p class="l allClass_body_tabs_first_middle_bodyVipS mt10">{{anotherchild.name}}</p></a>
				    				<p class="r mt10">{{anotherchild.duration}}</p>
				    				<div class="cl"></div>
				    			</div>
				    			<div class="cl"></div>
				    			<div v-for="child in proper.childCourseSyllabus">
				    			<div class="mt10 allClass_body_tabs_first_middle_body cl">
				    				<p class="l allClass_body_tabs_first_middle_bodyVip mt10">{{child.name}}</p>
				    				<p class="r mt10">{{child.duration}}</p>
				    				<div class="cl"></div>
				    				<div class="mt10 allClass_body_tabs_first_middle_foot cl" v-for="grandchildren in child.teachingFiles">
				    					<a :href="grandchildren.path"><p class="l allClass_body_tabs_first_middle_bodyVVip mt10">{{grandchildren.name}}</p></a>
				    					<p class="r mt10">{{grandchildren.duration}}</p>
				    					<div class="cl"></div>
				    				</div>
				    			</div>
				    			<div class="cl"></div>
				    			</div>
				    		</div>
				    	</div>
				    </el-tab-pane>
				    <el-tab-pane label="教学交流" name="fourth">
				    	<div class="allClass_body_tabs_fourth">
				    		<p>全部评论（{{tabs4.total}}）</p>
				    		<div class="allClass_body_tabs_fourth_bar" v-for="comment in comments">
				    			<div class="allClass_body_tabs_fourth_bar_img l">
				    				<img :src="baseUrl+comment.userImg" width="60px" height="60px" />
				    			</div>
				    			<div class="allClass_body_tabs_fourth_bar_word l">
				    				<p class="l">{{comment.createUser}}</p><p class="l ml10">{{timeF(comment.createDate).format("YYYY-MM-DD HH:mm:ss")}}</p><p class="l ml10">{{comment.what}}</p>
				    				<div class="cl"></div>
				    				<p class="allClass_body_tabs_fourth_bar_word_content mt10">{{comment.comment}}</p>
				    				<hr class="mt10" style="width:100%;background-color:#E5E5E5;">
				    			</div>
				    		</div>
				    		<div class="allClass_body_tabs_fourth_pagging tc">
				    			<el-pagination v-bind:current-Page="pageIndex" v-bind:page-size="pageSize" :total="total"
								   	layout="total,sizes,prev,pager,next,jumper" v-bind:page-sizes="pageSizes" :current-page="pageIndex"
								    v-on:size-change="sizeChange" v-on:current-change="pageIndexChange"></el-pagination>
				    		</div>
				    		<div class="allClass_body_tabs_fourth_input">
				    			<div class="allClass_body_tabs_fourth_input_img">
				    				<img :src="user.img" width="100px" height="100px" />
				    			</div>
				    			<p class="allClass_body_tabs_fourth_input_img_name l">{{user.name}}</p>
				    			<el-input class="allClass_body_tabs_fourth_input_textarea l" type="textarea" :rows="6" placeholder="不超过300字" v-model="textarea"></el-input>
				    			<el-button class="r allClass_body_tabs_fourth_input_button" @click="saveComment" type="primary">发表评论</el-button>
				    		</div>
				    	</div>
				    </el-tab-pane>
				  </el-tabs>
				</template>
			</div>
		</div>
	</div>
</template>

<script>
import page from '../member/page.vue'
export default {
  data () {
    return {
    	book:{},
      	tabs1:{
      		remark:""
      	},
      	pageIndex:1,
        pageSize:10,
        total:60,
        pageSizes:[1,10,20,50,100],
        pageIndexN:1,
        pageSizeN:10,
        totalN:90,
        pageSizesN:[1,10,20,50,100],
        lessons:'这门课程讲述的很详细',
        teacherImg:'',
      	tabs:[],
      	propers:[],
      	activeName: 'first',
      	textarea:'',
        comments:[],
        tabs4:{
        	total:''
        },
        user:{},
        baseUrl:"",
    }
  },
  components:{page},
   created:function(){
  	this.getdata();
  },
  	methods: {
  		getdata:function(){
  			var s = this.$route.params.part;
  			this.postHttp(this,{courseId:s},"course/study/queryCourseContent",function(obj,data){
  				obj.book=data.result.course;
  				obj.book.buttonName=data.result.concernFlag;
  				obj.book.login=data.result.loginFlag;
  				obj.propers=data.result.resultSyllabus;
  				obj.tabs1.remark=data.result.course.remark;
  			});
  			var baseUU = this.getBaseUrl();
  			this.baseUrl = baseUU;
  			this.postHttp(this,{},"user/getLoginUser",function(obj,data){
  				data.result.user.img = baseUU + data.result.user.img;
	          	obj.user=data.result.user;
	        });
  			this.postHttp(this,{courseId:s,pageNum:this.pageIndexN,pageSize:this.pageSizeN},"course/study/queryCourseTeacher",function(obj,data){
  				obj.tabs=data.result.courseList.list;
  				obj.totalN=data.result.courseList.total;
  				obj.teacherImg=data.result.teacher.img;
  				obj.lessons=data.result.teacher.remark;
  			});
  			this.postHttp(this,{courseId:s,pageNum:this.pageIndex,pageSize:this.pageSize},"comment/study/queryComments",function(obj,data){
  				obj.comments=data.result.list;
				obj.total=data.result.total;
				obj.tabs4.total=data.result.total;
  			})
  		},
  	   handleClick(tab, event) {
      },
      saveComment:function(){
      	var s = this.$route.params.part;
      	this.postHttp(this,{courseId:s,comment:this.textarea},"comment/saveComment",function(obj,data){
      		
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
	  	var s = this.$route.params.part;
	    this.postHttp(this,{courseId:s,pageNum:this.pageIndex,pageSize:this.pageSize},"comment/study/queryComments",function(obj,data){
			obj.comments=data.result.list;
			obj.total=data.result.total;
			});
	    },
	  sizeChangeN: function (pageSizeN) {   //每页显示条数
	      this.pageSizeN = pageSizeN;
	      this.fetchDataN();
	  },
	  pageIndexChangeN: function (pageIndexN) {   //第几页
	      this.pageIndexN = pageIndexN;
	      this.fetchDataN();
	  },
	  fetchDataN:function(){
	  	var s = this.$route.params.part;
	    this.postHttp(this,{courseId:s,pageNum:this.pageIndexN,pageSize:this.pageSizeN},"course/study/queryCourseTeacher",function(obj,data){
			obj.tabs=data.result.courseList.list;
			obj.totalN=data.result.courseList.total;
			});
	    },
	 attentionButton:function(ids){
	 	 this.$router.push({path:'/playing/'+ids});
	 },
	 attention:function(ids){
	 	var s = this.$route.params.part;
	 	if(this.book.login==false){
	 		this.notify_login();
	 		return false;
	 	}
	 	this.postHttp(this,{courseId:s,},"studiedrecord/saveStudiedRecord",function(obj,data){
	 		obj.getdata();
		});
	 },
	 unfollow:function(ids){
	 	var s = this.$route.params.part;
	 	this.postHttp(this,{id:ids,operation:"operation"},"subscription/operateSubscription",function(obj,data){
		});
		this.postHttp(this,{courseId:s},"course/study/queryCourseContent",function(obj,data){
  			obj.book=data.result.course;
  			obj.propers=data.result.resultSyllabus;
  			obj.tabs1.remark=data.result.course.remark;
  		});
	 }
    }
}
</script>

<style>
#allClass{
	overflow: hidden;
	background-color: #F9F9F9;
}
#allClass .allClass_body{
	border: 1px solid #E5E5E5;
	width: 1200px;
	margin: 0 auto;
	margin-top: 90px;
	background-color: #fff;
	margin-bottom: 50px;
}
#allClass .allClass_body_title{
	font-size: 24px;
	color: #272727;
	margin-top:40px;
	margin-left:40px;
}
#allClass .allClass_body_content{
	width: 93%;
	margin: 0 auto;
	margin-top:20px;
}
#allClass .allClass_body_content_img{
	width: 290px;
	height: 372px;
}
#allClass .allClass_body_content_word{
	width: 750px;
	margin-left: 40px;
}
#allClass .allClass_body_content_word p{
	margin-top:10px;
	margin-left:5px;
	font-size: 16px;
	line-height: 19px;
}
#allClass .allClass_body_content_word_color{
	color: #999999;
}
#allClass .allClass_body_content_button{
	width: 800px;
	margin-top:50px;
}
#allClass .attention_button_public{
	width: 125px;
	height: 35px;
}
#allClass .attention_button_sure{
	width: 125px;
	height: 35px;
	background-color: #999999 !important;
	border-color: #999999 !important;
}
#allClass .allClass_body_tabs{
	width: 93%;
	margin: 0 auto;
	margin-top:40px;
	margin-bottom: 30px;
}
#allClass .allClass_body_tabs h3{
	font-size: 16px;
}
#allClass .allClass_body_tabs p{
	font-size: 14px;
	color: #666666;
}
#allClass .allClass_body_tabs_download{
	color: #6ED56C !important;
}
#allClass .allClass_body_tabs_Vimg{
	width: 125px;
	height: 155px;
}
#allClass .allClass_body_tabs_word{
	width: 950px;
	height: 150px;
	overflow:hidden;
	word-wrap:break-word;
}
#allClass .allClass_body_tabs_second_table{
	width: 100%;
}
#allClass .allClass_body_tabs_second_table_bar{
	width: 360px;
	height: 270px;
	margin-left: 10px;
	margin-top: 10px;
	background-color: #F5F5F5;
}
#allClass .allClass_body_tabs_second_table_bar_img{
	width: 100%;
	height: 170px;
}
#allClass .allClass_body_tabs_second_table_bar p{
	font-size: 14px;
	color: #666666;
	line-height: 24px;
	margin-left: 10px;
	margin-top: 5px;
}
#allClass .allClass_body_tabs_second_pagging{
	width: 100%;
	margin-top: 20px;
}
#allClass .allClass_body_tabs_fourth{
	width: 100%;
	margin-top:10px;
}
#allClass .allClass_body_tabs_fourth_bar{
	margin: 0 auto;
	width: 90%;
	height: 100px;
	margin-top: 20px;
}
#allClass .allClass_body_tabs_fourth_bar_img{
	width: 60px;
	height: 60px;
	border-radius: 50%;
	overflow: hidden;
}
#allClass .allClass_body_tabs_fourth_bar_word{
	width: 92%;
	margin-left: 20px;
}
#allClass .allClass_body_tabs_fourth_bar_word p{
	line-height: 19px;
	min-height: 30px;
}
#allClass .allClass_body_tabs_fourth_bar_word_content{
	width: 100%;
	word-wrap:break-word;
}
#allClass .allClass_body_tabs_fourth_pagging{
	width: 100%;
	margin: 0 auto;
	margin-top: 20px;
}
#allClass .allClass_body_tabs_fourth_input{
	width: 100%;
	margin-top: 40px;
}
#allClass .allClass_body_tabs_fourth_input_img{
	width: 100px;
	height: 100px;
	border-radius: 50%;
	margin-left: 30px;
	overflow: hidden;
}
#allClass .allClass_body_tabs_fourth_input_img_name{
	margin-left: 55px;
	margin-top: 10px;
}
#allClass .allClass_body_tabs_fourth_input_textarea{
	width: 920px;
	margin-left: 50px;
	margin-top: -100px;
}
#allClass .allClass_body_tabs_fourth_input_button{
	margin-top:20px;
	margin-right: 50px;
	width: 125px;
	height: 35px;
}
#allClass .allClass_body_tabs_first_content{
	width: 100%;
	overflow: hidden;
}
#allClass .allClass_body_tabs_first_middle{
	border-bottom:1px dashed #000;
	width: 100%;
	height: 23px;
}
#allClass .allClass_body_tabs_first_middle p{
	height: 18px;
	background-color: #fff;
}
#allClass .allClass_body_tabs_first_middle_body{
	border-bottom:1px dashed #000;
	width: 100%;
	height: 23px;
}
#allClass .allClass_body_tabs_first_middle_foot{
	border-bottom:1px dashed #000;
	width: 100%;
	height: 23px;
}
#allClass .allClass_body_tabs_first_middle_bodyVip{
	padding-left: 20px;
}
#allClass .allClass_body_tabs_first_middle_bodyVipS{
	padding-left: 20px;
}
#allClass .allClass_body_tabs_first_middle_bodyVipS:hover{
	color: #6ED56C;
}
#allClass .allClass_body_tabs_first_middle_bodyVVip{
	padding-left: 40px;
}
#allClass .allClass_body_tabs_first_middle_bodyVVip:hover{
	color: #6ED56C;
}
#allClass .el-button{
    border-radius: 25px !important;
}
#allClass .attention_button:hover{border-color:#999999 !important;color: #999999 !important;}
#allClass .attention_button:active{color:#999999;border-color:#999999;}
#allClass .attention_button:focus{color:#999999;border-color:#999999;}
</style>
