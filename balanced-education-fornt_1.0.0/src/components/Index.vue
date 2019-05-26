<template>
	<div id="index" class="main_body">
		<!--直播-->
		<div class="livePrograss">
			<div class="common_title">
				直播节目
				<router-link to="/liveRoom">
					<div class="title_more">查看更多</div>
				</router-link>
			</div>
			<el-table :data="tableData" :show-header="false" :row-style="tableStyle" @row-click="goToLive" style="width: 100%;margin-top:20px">
				<el-table-column prop="name" align="center" show-overflow-tooltip ></el-table-column>
				<el-table-column prop="sourceName" align="center" show-overflow-tooltip ></el-table-column>
				<el-table-column prop="startDate" :formatter="timeFormat" align="center" ></el-table-column>
				<el-table-column align="center">
					<template scope="scope">
                        <span v-if="scope.row.status == '1'" style="color:#6ED56C">直播中</span>
                        <span v-else style="color:#272727">未开始</span>
               		</template>
				</el-table-column>
			</el-table>
		</div>
		
		<!--推荐课程-->
		<div v-if="showRecommend" class="recommend">
			<div class="common_title">
				推荐课程
				<router-link to="/allClass">
					<div class="title_more">查看更多</div>
				</router-link>
			</div>
			<div class="recommend_mian fix">
				
				<div v-for="e in recommendList" @click="goClass(e.id)" class="recommend_items l">
					<div class="items_img">
						<img v-if="e.coverImg!=''" :src="e.coverImg" width="100%" />
						<img v-else src="../../static/img/temp/fm1.png" width="100%" />
					</div>
					<div class="items_name ell" :title="e.name">
						{{e.name}}
					</div>
					<div class="items_source fix">
						<div class="source_name l">
							{{e.schoolName}}
						</div>
						<div class="source_count r">
							{{e.playedNum}}次播放
						</div>
					</div>
				</div>
				
			</div>
		</div>
		
		<!--学习记录-->
		<div v-if="showRecord" class="record">
			<div class="common_title">
				学习记录
				<router-link to="/memberCenter/studyrecord">
					<div class="title_more">查看更多</div>
				</router-link>
			</div>
			<div class="record_main fix">
				<div v-for="e in recordList" @click="goClass(e.id)" class="record_items l fix">
					<div class="items_left l">
						<img :src="e.src" width="100%"/>
					</div>
					<div class="items_right l">
						<div class="items_name">
							{{e.courseName}}
						</div>
						<div class="items_school">
							学校: {{e.schoolName}}
						</div>
						<div class="items_teacher">
							教师: {{e.teacherName}}
						</div>
						<div class="items_times">
							课时: {{e.courseDuration}}
						</div>
						<div class="items_date">
							开课时间: {{e.startDate}}
						</div>
						<div class="items_count">
							总播放: {{e.playedNum}}次
						</div>
						<div class="cross">
							最近学习: {{e.lastStudiedFileName}}
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="last_update">
			<div class="common_title">
				最近更新
				<router-link to="/allFile">
					<div class="title_more">查看更多</div>
				</router-link>
				
				<div v-if="isVideo" @click="changeVideo($event)" class="tab_update active">课堂视频</div>
				<div v-else @click="changeVideo($event)" class="tab_update">课堂视频</div>
				
				<div v-if="isVideo == false" @click="changeVideo($event)" class="tab_update orther active">教辅文件</div>
				<div v-else @click="changeVideo($event)" class="tab_update orther">教辅文件</div>
			</div>
			
			<div class="last_update_main fix">
				<a target="_blank" v-for="e in lastUpdateList" :href="e.path">
					<div class="last_update_items l fix">
						<div class="items_left l">
							<img v-if="e.isVedio" src="../../static/img/defualt/video.png" width="100%" />
							<img v-else :src="e.suffix" width="100%" />
						</div>
						<div class="items_right l">
							<div class="items_name">{{e.name}}</div>
							<div class="items_cross">课程: {{e.courseName}}</div>
							
							<div class="fix">
								<div class="items_school l">学校: {{e.schoolName}}</div>
								<div class="items_updateTime l">更新日期: {{e.updateDate}}</div>
							</div>
						</div>
					</div>
				</a>
			</div>
			
		</div>
		
	</div>
</template>

<script>
import IndexData from '../assets/data/indexData.js'
export default {
  data () {
    return {
      msg: '首页',
      IndexData,
      
      tableData:[],
      tableStyle:{
      	height:'60px',
      	lineHeight:'60px'
      },
      
      showRecommend:true,
      recommendList:[],
      
      showRecord:true,
      recordList:[],
      
      isVideo:true,
      lastUpdateList:[],
    }
  },
  mounted:function(){
  	
  	//假数据  接口对接后删除
  	//this.tableData = this.IndexData.tableData;
  	//this.recommendList = this.IndexData.recommendList;
  	//this.recordList = this.IndexData.recordList;
  	//this.lastUpdateList = this.IndexData.lastUpdateList;
  	
  	//live function
  	this.postHttp(this,{pageNum:1,pageSize:5,isStatusSort:true},'program/study/findPrograms',function(obj,data){
  		obj.tableData = data.result.list;
  	})
  	
  	//recommend function
  	this.postHttp(this,{pageNum:1,pageSize:10},'course/study/findRecommendCourses',function(obj,data){
  		obj.recommendList = data.result.list;
  	})
  	
  	//record function
  	this.postHttp(this,{pageNum:1,pageSize:10,tab:'ALL'},'studiedrecord/getStudiedRecList',function(obj,data){
  		if(data.code=="60000" || data.code=="50000"){
  			obj.recordList = []
  			obj.showRecord = false;
  			return;
  		}
  		var dataS = data.result.list;
  		//data deal
  		for(var i = 0;i < dataS.length;i++){
  			var objs = dataS[i];
  			var timesize = (parseFloat(objs.courseDuration) / (1000 * 60 * 60) );
  			objs.courseDuration = timesize.toFixed(0)+'分';
  			var time = obj.timeF(objs.startDate).format("YYYY-MM-DD HH:mm:ss");
  			objs.startDate = time;
  			dataS[i] = objs;
  		}
  		obj.recordList = dataS;
  		
  	});
  	
  	initUpdata(this)
  },
  methods:{
  	changeVideo(e){
  		var obj = e.currentTarget;
  		var html = obj.innerHTML;
  		if(html == '课堂视频'){
			this.isVideo = true;
			initUpdata(this)
  		}else{
  			this.isVideo = false;
  			initUpdata(this)
  		}
  	},
	timeFormat(row,column){
		var date = row[column.property];  
		if (date == undefined) {  
		 return "";  
		}  
		return this.timeF(date).format("YYYY-MM-DD HH:mm:ss");  
	},
	goToLive(row,event,column){
		var ids = row.id;
		this.$router.push({path:'/living/'+ids});
	},
	goClass(ids){
		//console.log(ids)
		this.$router.push({path:'/allClassMore/'+ids});
	}
  }
}

function initUpdata(objS){
	//updata function
  	objS.postHttp(objS,{pageNum:1,pageSize:10,isVedio:objS.isVideo},'teachingfile/study/findTeachingFiles',function(obj,data){
  		var dataS = data.result.list;
  		var baseUrl = obj.getBaseUrl();
  		//data deal
  		for(var i = 0;i < dataS.length;i++){
  			var objs = dataS[i];
  			objs.suffix = "../../static/img/defualt/"+objs.suffix+".png";
  			var time = obj.timeF(objs.updateDate).format("MM-DD");
  			objs.updateDate = time;
  			objs.path = baseUrl + objs.path;
  			dataS[i] = objs;
  		}
  		obj.lastUpdateList = dataS;
  	})
}
</script>

<style>
#index{overflow-x: hidden;}
#index img{
	display: block;
}
#index .common_title{
	height:40px;
	line-height: 40px;
	border-bottom:2px #6ED56C solid;
	font-size:24px;
	color:#272727;
	padding-left:2px;
	width:100%;
	margin-top: 30px;
	position: relative;
}
#index .common_title .tab_update{
	position: absolute;
	left:160px;
	top:0px;
	height:40px;
	line-height: 40px;
	font-size:16px;
	color:#272727;
	cursor: pointer;
}
#index .common_title .orther{
	left:250px;
}
#index .common_title .tab_update.active{
	color:#6ED56C;
}
#index .common_title .title_more{
	position: absolute;
	width: 100px;
	text-align: center;
	cursor: pointer;
	color:#73D368;
	font-size: 16px;
	top:10px;
	right: 10px;
	height: 20px;
	line-height: 20px;
}
#index .recommend_mian{
	width:1250px;
	margin-left:-25px;
	padding-bottom: 30px;
}
#index .recommend_mian .recommend_items{
	width:200px;
	margin: 30px 16px 0 31px;
	background: #ececec;
	cursor: pointer;
}
#index .recommend_mian .recommend_items:hover{
	box-shadow: 1px 1px 10px #67676780;
}
#index .recommend_mian .recommend_items .items_img{
	width:200px;
	height:200px;
}
#index .recommend_mian .recommend_items .items_name{
	width:190px;
	height:30px;
	line-height: 30px;
	padding:0px 10px;
	font-size: 16px;
	color: #272727;
}
#index .recommend_mian .recommend_items .items_source{
	width:180px;
	height:30px;
	line-height: 30px;
	padding:0px 10px;
	font-size: 14px;
	color: #999999;
}

#index .record_main{
	width:1220px;
	margin-left:-10px;
	padding: 10px 0 20px 0;
}
#index .record_main .record_items{
	margin:20px 10px 0px 10px;
	background:#ececec;
	width:590px;
	position: relative;
}
#index .record_main .record_items:hover{
	box-shadow: 1px 1px 10px #67676780;
}
#index .record_main .record_items .items_left{
	width:206px;
	height:274px;
}
#index .record_main .record_items .items_right{
	padding: 16px 20px;
	font-size: 14px;
	color: #999999;
	height:30px;
	line-height: 30px;
	
}
#index .record_main .record_items .items_right .items_name{
	font-size: 16px;
	color: #272727;
}
#index .record_main .record_items .items_right .cross{
	position: absolute;
	bottom:10px;
	left:226px;
	font-size: 14px;
	color: #6ED56C;
}
#index .last_update_main{
	width:1220px;
	margin-left:-10px;
	padding: 10px 0 20px 0;
}
#index .last_update_main .last_update_items{
	margin:20px 10px 0px 10px;
	background:#ececec;
	width:590px;
	position: relative;
	height:200px;
}
#index .last_update_main .last_update_items .items_left{
	width:200px;
	height:200px;
}
#index .last_update_main .last_update_items .items_right{
	padding: 16px 20px;
	font-size: 14px;
	color: #999999;
	line-height: 30px;
	height: 168px;
	width: 350px;
}
#index .last_update_main .last_update_items .items_right .items_name{
	font-size: 16px;
	color: #272727;
}
#index .last_update_main .last_update_items .items_right .fix{
	margin-top:80px;
	width:100%;
}
#index .last_update_main .last_update_items .items_right .items_school{
	width:50%;
}
#index .last_update_main .last_update_items .items_right .items_updateTime{
	width:50%;
}
</style>