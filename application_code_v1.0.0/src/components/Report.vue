<template>
<div class="main_body-header">
	<div class="main_body">
		 <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" router>
		  <el-menu-item v-if="roleName=='老师'||roleName=='教师'" index="/report/schoolLevel">校级报告</el-menu-item>
		  <el-menu-item v-if="roleName=='老师'||roleName=='教师'" index="/report/teachingLevel">教研报告</el-menu-item>
		  <el-menu-item v-if="roleName=='老师'||roleName=='教师'" index="/report/classLevel">班级报告</el-menu-item>
		  <el-menu-item v-if="roleName=='老师'||roleName=='学生'||roleName=='教师'" index="/report/studentLevel">学生报告</el-menu-item>
		</el-menu>
		<router-view></router-view>
	</div>
	<div class="main_foot mt30">
		<p>© 2017-2018 苏州金瑞阳信息科技有限公司 苏ICP备17029802号</p>
	</div>
</div>
</template>

<script>
export default {
	data(){
		return {
	      activeIndex:'',
	      roleName:''
	    }
	},
	created: function(){
		this.$emit('refreshbizlines','other');
		this.activeIndex=window.location.pathname;
		if(window.location.pathname=="/report"){

			this.activeIndex="/report/schoolLevel";
		}
	},
	mounted:function(){
		this.getLoginUser()
	},
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      },
	  getLoginUser(){
	      this.postHttp(this,'',"user/getLoginUser",function(obj,data){
	        obj.roleName = data.result.roleName;
	      });
	  }
    }
}
</script>

<style>
.el-menu{
	display: table;
	margin: auto;
	margin-top: 33px;
	background-color: white
}
.el-menu--horizontal{
	border-bottom: 0px
}
.el-menu--horizontal .el-menu-item{
	background-color: white;
	border-bottom: 0px;
	padding: 0 25px;
    border-radius: 16px 16px 0 0;
}
.el-menu--horizontal>.el-menu-item.is-active{
	color: white;
    background-color: #44A9FF;
    border-radius: 16px 16px 0 0;
}
.el-menu--horizontal .el-menu-item:hover{
	background-color: white;
	border-bottom:0px;
}
.el-menu--horizontal .el-menu-item.is-active:hover{
	background-color: #44A9FF;
	border-bottom:0px;
}
.main_body-header .main_foot{
	width: auto;
	height: 110px;
	background: #F1F1F1;
	text-align: center;
	line-height: 110px;
}
.main_body-header .main_foot p{
	font-size: 24px;
	color: #3D3D3D;
	letter-spacing: 0;
}
</style>