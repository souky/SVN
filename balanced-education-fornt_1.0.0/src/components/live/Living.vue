<template>
	<div id="living">
		<div class="living_play">
      <div class="living_main">
		<div class="video_playS">
			<video-player  class="vjs-custom-skin"
                 ref="videoPlayer"
                 :options="playerOptions"
                 :playsinline="true"
                 customEventName="customstatechangedeventname">
  			</video-player>
		</div>
         <div class="living_voidTitle">
           <p class="voidtitle">{{profile.courseName}}</p>
           <p class="progress">{{profile.newprogram}}</p>
           <p class="playNumber">共播放{{profile.playNum}}次</p>
         </div>
      </div>
      <div class="living_list">
          <div class="mt10 allClass_body_tabs_first_content">
                <div class="mt10 allClass_body_tabs_first_middle cl" v-for="proper in propers">
                  <p class="l mt10">{{proper.name}}</p>
                  <div class="cl"></div>
                  <div class="mt10 allClass_body_tabs_first_middle_body cl" v-for="anotherchild in proper.teachingFiles">
                  <p  @click="playButton(anotherchild.id)" class="l allClass_body_tabs_first_middle_bodyVipS mt10">{{anotherchild.name}}</p>
                    <div class="cl"></div>
                  </div>
                  <div class="cl"></div>
                  <div v-for="child in proper.childCourseSyllabus">
                  <div class="mt10 allClass_body_tabs_first_middle_body cl">
                    <p class="l allClass_body_tabs_first_middle_bodyVip mt10">{{child.name}}</p>
                    <div class="cl"></div>
                    <div class="mt10 allClass_body_tabs_first_middle_foot cl" v-for="grandchildren in child.teachingFiles">
                      <p  @click="playButton(grandchildren.id)" class="l allClass_body_tabs_first_middle_bodyVVip mt10">{{grandchildren.name}}</p>
                      <div class="cl"></div>
                    </div>
                  </div>
                  <div class="cl"></div>
                </div>
                </div>
              </div>
      </div>
		</div>
		<div class="living_banner">
			 <el-carousel indicator-position="outside" arrow="always" :autoplay="false">
			    <el-carousel-item v-for="item in users" :key="item.id">
			       <div class="banner_bar l" v-for="child in item.childs">
			         <!-- <img v-if="child.type=='rar'" src="../../../static/img/defualt/rar.png" />
			         <img v-if="child.type=='png'" src="../../../static/img/defualt/img.png" />
			         <img v-if="child.type=='fla'" src="../../../static/img/defualt/swf.png" />
			         <img v-if="child.type=='mp3'" src="../../../static/img/defualt/voice.png" />
			         <img v-if="child.type=='avi'" src="../../../static/img/defualt/video.png" />
			         <img v-if="child.type=='word'" src="../../../static/img/defualt/doc.png" />
			         <img v-if="child.type=='exe'" src="../../../static/img/defualt/exe.png" /> -->
               <img v-if="child.suffix==undefined" src="../../../static/img/defualt/noRecord.png" height="100%" width="100%">
               <img v-else :src="child.img">
			         <p class="tc banner_bar_word">{{child.name}}</p>
			       </div>
			    </el-carousel-item>
			  </el-carousel>
		</div>
		<div class="living_table_foot">
			<div class="allClass_body_tabs_fourth">
				    		<p>全部评论（{{total}}）</p>
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
				    				<img :src="userU.img" width="100px" height="100px" />
				    			</div>
				    			<p class="allClass_body_tabs_fourth_input_img_name l">{{userU.name}}</p>
				    			<el-input class="allClass_body_tabs_fourth_input_textarea l" type="textarea" :rows="6" placeholder="不超过300字" v-model="textarea"></el-input>
				    			<el-button class="r allClass_body_tabs_fourth_input_button" @click="saveComment" type="primary">发表评论</el-button>
				    		</div>
				    	</div>
              <div class="cl"></div>
		</div>
	</div>
</template>

<script>
export default {
  data () {
    return {
    	  textarea:'',
        users:[],
    	  items:[],
        comments:[],
        textarea:'',
        propers:[],
        pageIndex:1,
        pageSize:10,
        total:0,
        pageSizes:[1,10,20,50,100],
        lastTeachingFilesId:"",
        profile:{
          newprogram:"",
       },
        userU:{},
        baseUrl:"",
        
        playerOptions: {
          // videojs options
          muted: true,
          language: 'zh',
          aspectRatio:'900:550',
          notSupportedMessage:'视频资源错误',
          sources: [{
            type: "video/mp4",
            src: "../../../../static/video/mov_bbb.mp4"
          }],
        }
     }
  },
  created:function(){
    var s = this.$route.params.part;
    this.postHttp(this,{courseId:s,pageNum:"1",pageSize:"20"},"teachingfile/study/queryTeachingFilesByType",function(obj,data){
      obj.items=data.result.list;
    for(var i=0;i<obj.items.length;i++){
      obj.items[i].img="../../../static/img/defualt/"+obj.items[i].suffix+".png";
    }
    var childNum=Math.ceil(obj.items.length/5);
    var childs=[];
    for(var l=0;l<childNum;l++){
      var id=l+1;
      var e=5*(l+1);
      var s=e-5;
      childs[l] = []
      childs[l]["childs"]=obj.items.slice(s,e);
      childs[l]["id"] = id;
    }
    obj.users=childs;
    });
    this.postHttp(this,{courseId:s,pageNum:this.pageIndex,pageSize:this.pageSize},"comment/study/queryComments",function(obj,data){
      obj.comments=data.result.list;
      obj.total=data.result.total;
    });
    var baseUU = this.getBaseUrl();
    this.baseUrl = baseUU;
    this.postHttp(this,{},"user/getLoginUser",function(obj,data){
        data.result.user.img = baseUU + data.result.user.img;
        obj.userU=data.result.user;
    });
    this.postHttp(this,{courseId:s},"course/study/queryCourseContent",function(obj,data){
          obj.propers=data.result.resultSyllabus;

          obj.postHttp(obj,{id:data.result.lastTeachingFilesId},"teachingfile/getTeachingFileById",function(obj1,data1){
          obj1.profile=data1.result;
          obj1.profile.newprogram=data1.result.courseSyllabusNameArray[0];
          for(var i=1;i<data1.result.courseSyllabusNameArray.length;i++){
            obj1.profile.newprogram+=data1.result.courseSyllabusNameArray[i];
          }
        });
    });
  },
  methods:{
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
    saveComment:function(){
      var s = this.$route.params.part;
        this.postHttp(this,{courseId:s,comment:this.textarea},"comment/saveComment",function(obj,data){
        });
        this.textarea="";
        this.fetchData();
      },
      playButton:function(ids){
        alert(ids)
      }
  	}
}
</script>
<style>
#living{
	padding-top:60px;
	background-color: #F9F9F9;
}	

#living .video_playS{
	width: 900px;
	height:550px;
	background: rgba(0,0,0,0.5);
}

#living .living_play{
	width: 1200px;
	height: 600px;
	margin: 0 auto;
	margin-top: 30px;
}
#living .living_banner{
	margin: 0 auto;
	margin-top: 40px;
	width: 1250px;
	height: 250px;
}
  #living .el-carousel__indicators--outside{
    display: none;
  }
  #living .el-carousel__arrow{
    border: none;
    padding: 0;
    width: 30px;
    height: 55px;
    border-radius:0 !important;
    background-color: #6ED56C;
    color: #fff;
    position: absolute;
    top: 50%;
    z-index: 10;
    -ms-transform: translateY(-50%);
    transform: translateY(-50%);
    text-align: center;
    font-size: 12px;
  }
  #living .el-carousel__arrow--left{
    left: 0;
  }
  #living .el-carousel__arrow--right{
    right: 0;
  }
  #living .el-carousel__item{
    width: 90%;
    left: 5%;
    height: 300px;
  }
  #living .banner_bar{
    width: 200px;
    height: 200px;
    margin-top: 20px;
    margin-left: 23px;
  }
  #living .banner_bar img{
    height: 200px;
  }
  #living .banner_bar_word{
    margin-top: 20px;
    line-height: 17px;
    font-size: 14px;
    color: #272727;
  }
  #living .living_table_foot{
  	width: 1200px;
  	margin: 0 auto;
  	margin-top: 50px;
  }
#living .allClass_body_tabs_fourth{
	width: 100%;
	margin-top:10px;
}
#living .allClass_body_tabs_fourth_bar{
	margin: 0 auto;
	width: 90%;
	height: 100px;
	margin-top: 20px;
}
#living .allClass_body_tabs_fourth_bar_img{
	width: 60px;
	height: 60px;
	border-radius: 50%;
	overflow: hidden;
}
#living .allClass_body_tabs_fourth_bar_word{
	width: 92%;
	margin-left: 20px;
}
#living .allClass_body_tabs_fourth_bar_word p{
	line-height: 19px;
}
#living .allClass_body_tabs_fourth_bar_word_content{
	width: 100%;
	word-wrap:break-word;
}
#living .allClass_body_tabs_fourth_pagging{
	width: 100%;
	margin: 0 auto;
	margin-top: 20px;
}
#living .allClass_body_tabs_fourth_input{
	width: 100%;
	margin-top: 40px;
}
#living .allClass_body_tabs_fourth_input_img{
	width: 100px;
	height: 100px;
	border-radius: 50%;
	margin-left: 30px;
	overflow: hidden;
}
#living .allClass_body_tabs_fourth_input_img_name{
	margin-left: 55px;
	margin-top: 10px;
}
#living .allClass_body_tabs_fourth_input_textarea{
	width: 920px;
	margin-left: 50px;
	margin-top: -100px;
}
#living .allClass_body_tabs_fourth_input_button{
	margin-top:20px;
	margin-right: 50px; 
  margin-bottom: 50px;
	width: 125px;
	height: 35px;
}
#living .living_list{
  width: 300px;
  height:600px;
  background: #282828;
  float: right;
  overflow: auto;
}
#living .living_main{
  width: 900px;
  height: 550px;
  float: left;
}
#living .living_voidTitle{
  width: 900px;
  height: 50px;
  background: #282828;
}
#living .allClass_body_tabs_first_content{
  width: 100%;
  overflow: auto;
}
#living .allClass_body_tabs_first_middle{
  width: 100%;
  height: 23px;
}
#living .allClass_body_tabs_first_middle p{
  height: 20px;
  color:#fff;
  overflow: hidden;
  text-overflow: ellipsis;
  -o-text-overflow: ellipsis;
  white-space: nowrap;
  width: 250px;
}
#living .allClass_body_tabs_first_middle_body{
  width: 100%;
  height: 23px;
}
#living .allClass_body_tabs_first_middle_foot{
  width: 100%;
  height: 23px;
}
#living .allClass_body_tabs_first_middle_bodyVip{
  padding-left: 20px;
}
#living .allClass_body_tabs_first_middle_bodyVipS{
  padding-left: 20px;
}
#living .allClass_body_tabs_first_middle_bodyVipS:hover{
  color: #6ED56C;
}
#living .allClass_body_tabs_first_middle_bodyVVip{
  padding-left: 40px;
}
#living .allClass_body_tabs_first_middle_bodyVVip:hover{
  color: #6ED56C;
}
#living .living_voidTitle p{
  color: #fff;
  float: left;
  font-size: 14px;
}
#living .living_voidTitle .voidtitle{
  font-size: 24px;
  margin-top: 8px;
}
#living .living_voidTitle .progress{
  margin-top: 17px;
  margin-left: 10px;
  width:500px;
  overflow: hidden;
  text-overflow: ellipsis;
  -o-text-overflow: ellipsis;
  white-space: nowrap;
}
#living .living_voidTitle .playNumber{
  margin-top: 17px;
  float: right;
  margin-right: 10px;
}
#living .el-button{
  border-radius: 32px;
}
</style>