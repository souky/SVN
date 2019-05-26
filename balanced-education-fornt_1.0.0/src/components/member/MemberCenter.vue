<template>
<div id="member_center" class="main_body">
<div class="member_center_style fix">
	
	<div class="member_center_content ">
		<personal-data v-if="showPage == 'personaldata'"></personal-data>
		<account-security  v-if="showPage == 'accountsecurity'"></account-security>
		<classes-record  v-if="showPage == 'classesrecord'"></classes-record>
		<study-record  v-if="showPage == 'studyrecord'"></study-record>
		<order-record  v-if="showPage == 'orderrecord'"></order-record>
		
		<div class="member_center_leftNav tc">
			<div class="member_center_leftNav_header">
				<div class="member_center_leftNav_header_logo">
					<img :src="use.img" height="100px" width="100px">
				</div>
				<p class="member_center_leftNav_header_padding">{{use.name}}</p>
				<p>{{use.sex}} {{use.class}}</p>
				<p>{{use.organization.name}}</p>
			</div>
	
			<div class="member_center_leftNav_button member_center_leftNav_button_top member_center_leftNav_button_padding" @click="personaldataS('personaldata')">
				<div v-show="showPage == 'personaldata'" class="member_center_leftNav_isactive"><i class="iconstyle el-icon-arrow-right"></i></div><p>个人资料</p>
			</div>
			<div class="member_center_leftNav_button member_center_leftNav_button_bottom" @click="personaldataS('accountsecurity')">
				<div v-show="showPage == 'accountsecurity'" class="member_center_leftNav_isactive"><i class="iconstyle el-icon-arrow-right"></i></div><p>账号安全</p>
			</div>
	
			<div class="member_center_leftNav_button member_center_leftNav_button_top member_center_leftNav_button_padding" @click="personaldataS('classesrecord')">
				<div v-show="showPage == 'classesrecord'" class="member_center_leftNav_isactive"><i class="iconstyle el-icon-arrow-right"></i></div><p>我的开课记录</p>
			</div>
			<div class="member_center_leftNav_button member_center_leftNav_button_top" @click="personaldataS('studyrecord')">
				<div v-show="showPage == 'studyrecord'" class="member_center_leftNav_isactive"><i class="iconstyle el-icon-arrow-right"></i></div><p>我的学习记录</p>
			</div>
			<div class="member_center_leftNav_button member_center_leftNav_button_bottom" @click="personaldataS('orderrecord')">
				<div v-show="showPage == 'orderrecord'" class="member_center_leftNav_isactive"><i class="iconstyle el-icon-arrow-right"></i></div><p>我的预约记录</p>
			</div>
		</div>
	</div>
</div>
</div>
</template>

<script>
import PersonalData from '@/components/member/membercenter/personaldata.vue'
import AccountSecurity from '@/components/member/membercenter/accountSecurity.vue'
import ClassesRecord from '@/components/member/membercenter/classesRecord.vue'
import StudyRecord from '@/components/member/membercenter/studyRecord.vue'
import OrderRecord from '@/components/member/membercenter/orderRecord.vue'
export default {
  data () {
    return {
      use:{
      	name:'James',
      	sex:'女',
      	class:'五年级',
      	school:'苏州二中',
      	organization:{name:''}
      },
      showPage:'personaldata',
      datOabj:'',
    }
  },
  components: {PersonalData,AccountSecurity,ClassesRecord,StudyRecord,OrderRecord},
  created:function(){
  	this.getdata();
  	var s = this.$route.params.part;
	this.personaldataS(''+s);
	var baseUU = this.getBaseUrl();
	
	this.postHttp(this,{},"user/getLoginUser",function(obj,data){
		if(data.code == '60000' || data.code == '50000'){
			obj.$router.push('/login')
		}
		data.result.user.img = baseUU + data.result.user.img;
		obj.use=data.result.user;
		obj.use.class = data.result.grade.name;
		if(data.result.user.sex=="M"){
			obj.use.sex="男";
		}
		if(data.result.user.sex=="W"){
			obj.use.sex="女";
		}
	});
  },
  watch: {
    '$route' (to, from) {
  	 var s = this.$route.params.part;
  	 this.postHttp(this,{},"user/getLoginUser",function(obj,data){
		if(data.code == '60000' || data.code == '50000'){
			obj.$router.push('/login')
		}
		obj.personaldataS(''+s);
	});
     
    }
  },
  methods:{
  	getdata:function(){

  	},
  	personaldataS:function(val){
  		if(val=='accountsecurity'){
  			this.showPage = 'accountsecurity';
  		}
  		if(val=='personaldata'){
  			this.showPage = 'personaldata';
  		}
  		if(val=='classesrecord'){
  			this.showPage = 'classesrecord';
  		}
  		if(val=='studyrecord'){
  			this.showPage = 'studyrecord';
  		}
  		if(val=='orderrecord'){
  			this.showPage = 'orderrecord';
  		}
  	}
  }
}
</script>

<style>
#member_center .member_center_leftNav{
	width: 200px;
	position: absolute;
	top:0px;
	left:-220px;
}
#member_center .member_center_content{
	margin:auto;
	margin-bottom: 20px;
	width: 1100px;
	background: #fff;
	border: 1px solid #E5E5E5;
	border-radius: 3px;
	min-height:565px;
	position: relative;
}
#member_center .member_center_style{
	width: 1200px;
	margin:0 auto;
	margin-top: 20px;
}
#member_center .member_center_leftNav_header{
	width: 100%;
	height: 220px;
	background: #fff;
	border: 1px solid #E5E5E5;
	border-radius: 3px; 
}
#member_center .member_center_leftNav_button{
	width: 100%;
	height: 60px;
	background: #fff;
	position: relative;
}
#member_center .member_center_leftNav_button_top{
	border-top:1px solid #E5E5E5;
	border-left:1px solid #E5E5E5;
	border-right: 1px solid #E5E5E5;
	border-radius: 3px 3px 0 0;
}
#member_center .member_center_leftNav_button_bottom{
	border-radius: 0 0 3px 3px;
	border: 1px solid #E5E5E5;
}
#member_center .member_center_leftNav_button_padding{
	margin-top: 19px;
}
#member_center .member_center_leftNav_header_logo{
	width: 100px;
	height: 100px;
	margin:0 auto;
	margin-top: 41px;
	border-radius: 50%;
	overflow: hidden;
}
#member_center .member_center_leftNav_header p{
	color: #999999;
	font-size: 14px;
}
#member_center .member_center_leftNav_header_padding{
	margin-top: 10px;
	color: #272727 !important;
	font-size: 16px !important;
}
#member_center .member_center_leftNav_button p{
	font-size: 16px;
	margin-top: 19px;
}
.member_center_leftNav_isactive{
	width: 20px;
	height: 20px;
	background: #6ED56C;
	position: absolute;
	top:21px;
	left:16px;
	border-radius: 50%;
}
.iconstyle{
	color:#fff;
	font-size: 12px;
	margin-left: 3px;
}

</style>