<template>
	<div v-if="isShow=='home'" id="header" class="fix box_s">
		<div class="logoImg">
      <router-link to="/home">
       <img src="../../static/img/header/logo.png" />
       <span class="titleJRY hometitles">金阳测评</span>
     </router-link>
     <div class="rightPart r">
      <div class="l">
       <span class="userName">{{username}}</span>  <span @click="loginOut">退出</span>
     </div>
   </div>
 </div>
</div>
<div v-else-if="isShow=='other'" id="header" class="fix box_s box_other">
  <div class="logoImg">
    <router-link to="/home">
      <img src="../../static/img/header/logo.png" />
      <span class="titleJRY reporthomes">金阳测评</span>
    </router-link>
    <div class="leftPart l">
      <div class="l">
        <router-link :to="report">
          <div class="leftItems l">成绩报告</div>
        </router-link>

        <router-link to="/resource">
          <div class="leftItems l">考试管理</div>
        </router-link>

        <router-link to="/management">
          <div class="leftItems l">资源中心</div>
        </router-link>
      </div>
    </div>
    <div class="rightPart r">
      <div class="l">
        <span class="userName">{{username}}</span>  <span @click="loginOut">退出</span>
      </div>
    </div>
  </div>
</div>
<div v-else="isShow=='login'">
</div>
</template>

<script>

export default {
  data () {
    return {
      isShow:'',
      msg: '顶部导航栏',
      username:'用户13974289150',
      report:''
    }
  },
  mounted:function(){
  	//判断cookie登陆信息初始化
  	this.getLoginUser();
  },
  watch:{
    '$route':'getLoginUser'
  },
  methods:{
  	handleParentClick(e){
  		this.isShow=e;
  	},
  	//退出登陆操作
  	loginOut(){
  		//页面跳转
      this.postHttp(this,'',"/logout",function(obj,data){
        obj.$router.push({path:'/login'});
      });

    },
    getLoginUser(){
      if(window.location.pathname=="/login"){
       
     }else{
        this.postHttp(this,'',"user/getLoginUser",function(obj,data){
        obj.username = data.result.userName;
        if(data.result.roleName=='老师'||data.result.roleName=='教师')
          obj.report = '/report'
        if(data.result.roleName=='学生')
          obj.report = '/report/studentLevel'
      });
     }
   }
 }
}
</script>

<style>
#header .titleJRY{
  font-size: 24px;
}
#header .hometitles{
  color: #000
}
#header .reporthomes{
  color: #fff
}
#header .leftItems{
  padding: 0 20px;
  color: white;
}
#header .leftPart .router-link-active .leftItems{
  background-color: rgba(0,0,0,.24);
}
</style>