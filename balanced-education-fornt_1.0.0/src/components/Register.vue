<template>
<div id="register" class="main_body tc" >
	<div class="page2" v-show="showpage2">
		<div class="main">
			<div class="top">
				注册成功
			</div>
			<div class="middle1">
				<div class="img">
			      <img src="../../static/img/register/ico1.png" class="image">
		        </div>
			</div>
			<div class="middle2">
				祝贺您注册成功！<br  />
				你的账号是{{userForm.userName}}，您可前往账户中进一步修改资料或修改密码
			</div>
			<div class="bottom">
				<el-button type="primary" @click="backfirstpage('val')">完成返回首页</el-button>
			</div>
		</div>
	</div>
	
	
	<div class="page1" v-show="showpage1">
		<!-- <div class="img">
			 <img src="../../static/img/register/logo.png" class="image">
		</div>-->
		<div class="content">
		     <div class="title">
			 	注  册
			 	<div class="dengru">我已注册，现在就去<a class="pointer" herf="" @click="login()">登入</a></div>
			</div>
           <el-form  :model="userForm" :rules="rules" ref="userForm" label-width="100px" class="demo-userForm">
								<el-form-item class="fleft psition" label="账户名" prop="userName" >
								    <el-input v-model="userForm.userName" auto-complete="off" placeholder="请输入账户名"></el-input>
								</el-form-item> 
						        <div class="fright">6-18个字符，可用字母，数字，下划线，请以字母开头</div>
                                <el-form-item class="fleft psition clear"  label="姓名" prop="name"  >
								    <el-input v-model="userForm.name" auto-complete="off" placeholder="请输入真实姓名"></el-input>
								</el-form-item>
								<div class="fright">请输入真实的姓名，用于审核身份</div>
								
								<el-form-item label="学校" prop="schools"  class="clear">
									
								    <el-select  v-model="userForm.orgId" placeholder="请选择学校" @change="change_school()">
								        <el-option  v-for="item in schools" :key="item.id" :label="item.name" v-model="item.id">
								        </el-option>
								    </el-select>
                                </el-form-item>
								
								
                                <el-form-item class="fleft psition clear"  label="班主任" prop="teachers" >
								    <el-select  v-model="userForm.teacherId" placeholder="请选择老师">
								        <el-option  v-for="item in teachers" :key="item.id" :label="item.name" v-model="item.id">
								        </el-option>
								    </el-select>
								</el-form-item>
								
								
                                <div class="fright">请输入真实的姓名，用于审核身份</div>
                                <el-form-item class="fleft psition clear" label="设置密码" prop="psw"  >
								    <el-input type="password" v-model="userForm.psw" auto-complete="off" placeholder="请输入密码"></el-input>
								</el-form-item>
                                <div class="fright">密码长度6-16位，且必须数字，字母，字符任意两种以上组合</div>
                                <el-form-item label="确认密码" prop="checkpsw"  class="clear">
								    <el-input type="password" v-model="userForm.checkpsw" auto-complete="off" placeholder="请再次输入密码"></el-input>
								</el-form-item>
								<!--<el-form-item>
									<el-button class="button" type="primary" @click="submitForm('userForm')">立即注册</el-button>
                             </el-form-item>-->
						<div class="button">		     
								     
								       <el-button type="primary" @click="submitForm('userForm')">立即注册</el-button>
								  
						</div> 
			</el-form>
		    
	    </div>
	</div>    
</div>
</template>

<script>
export default {
  data () {
  	var validatename = (rule, value, callback) => {
  		var pattern = /^[\w\u4e00-\u9fa5]{2,10}$/g
  		if(!value){
  			callback(new Error('请输入姓名'));
  		}else if(!pattern.test(value)){
  			callback(new Error('请输入3-10个字母/汉字'))
  		}else{
  			callback()
  		}
  	};
  	var validateaccount = (rule, value, callback) => {
  		if(value==''){
  			callback(new Error('请输入账户名'));
  		}else if(value.length < 6 || value.length > 18 ){
  			callback(new Error('账户名6-18字符'));	
  		}else{
  			callback();
  		}	
  	};
  	var validatepsw = (rule, value, callback) => {
  		 if (value == '') {
          callback(new Error('请输入密码'));
        }else if (value.length < 6 || value.length > 18 ){
          callback(new Error('密码6-18字符'));
        }
        else {
          if (this.userForm.checkpsw !== '') {
            this.$refs.userForm.validateField('checkpsw');
          }
          callback();
        }	
  	};
  	var validatecheckpsw = (rule, value, callback) => {
  		if(value==''){
  			callback(new Error('请再次输入密码'));
  		}else if(value != this.userForm.psw){
  			callback(new Error('两次输入密码不一致'));	
  		}else{
  			callback();
  		}	
  	};
    return {
      value:'',	
      schools:[],
      teachers:[], 
      showpage2:false,	
      showpage1:true,	
      userForm:{
      	userName:'',
      	name:'',
      	orgId:'',
      	teacherId:'',
      	psw:'',
      	checkpsw:'',
      },
      rules:{
      	userName:[
      	{validator: validateaccount, trigger:'blur'}
      	],
      	name:[
      	{validator: validatename, trigger:'blur'}
      	],
      	psw:[
      	{validator: validatepsw, trigger:'blur'}
      	],
      	checkpsw:[
      	{validator: validatecheckpsw, trigger:'blur'}
      	],
      }
    }
  },
  methods:{
  	//注册提交
  	submitForm(userForm){
  	     //验证
  		 //验证整个userForm,并上传到后台
  		 this.$refs[userForm].validate((valid) => {
           if (valid) {
           	 var data = this.userForm ;
           	 this.postHttp(this,data,'study/user/register',function(obj,data){
           	 	if(data.code == '10000'){
           	 		obj.showpage1 = false;
           	 		obj.showpage2 = true;
           	 	}else{
           	 		obj.notify_jr(obj,'错误',data.message,'error');
           	 	}
           	 })
             
          } else {
            console.log('error submit!!');
            return false;
          }
        });	
  	},
  	
  	login(){
  		this.$router.push({path:'/login'});
  	},
  	backfirstpage(){
  		 this.$router.push({path:'/'});
  		 this.showpage1 = true;
  		 this.showpage2 = false;
  	},
  	change_school(){
  		var orgId = this.userForm.orgId;
  		var data = {
  			role:'教师',
  			orgId:orgId
  		}
  		this.postHttp(this,data,"study/user/queryUsersByOrgId",function(obj,data){
	  		obj.teachers = data.result;
	  	})
  	},
  	
  },
  mounted:function(){
  	var height = document.documentElement.clientHeight - 60;
  	document.getElementById("register").style.height = height+'px';
  	
  	this.postHttp(this,{type:'SCHOOL'},"organization/study/getOrganizationsByType",function(obj,data){
  		obj.schools = data.result;
  	})
  }
}
</script>

<style>
	#register  a:hover   {color:white;}
	#register .pointer{cursor: pointer;}
	#register .el-select{width:330px;}
	html {
		width:100%;
	    height:100%
	}
	#register {
		width:100%;
	    background:url('../../static/img/register/registerBackground.png') no-repeat;
		background-size:100%;
	}
	#register .content{
		margin: 50px;
		padding:50px 100px;
		border: 1px solid whitesmoke;
		background-color:white ;
		box-shadow: 1px 1px 3px #636363;
		border-radius: 5px;	
	}
	#register .form{margin: auto;}
	#register .el-button{
		background-color: #6ED56C;
		width:200px;
		height:50px;
	}
	#register .title{
		height:50px;
		padding-bottom:10px;
		position: relative;
		line-height: 50px;
		font-size:36px;
		color:#272727;
		border-bottom:1px #6ED56C solid;
		margin-bottom: 20px;
	}
	#register .dengru{
		position: absolute;
		top:30px;
		right:0px;
		height:20px;
		line-height: 20px;
		font-size:16px;
	}
	#register .dengru a{
		color:#6ED56C;
	}
	#register .psition{
		position:relative;
	}
	#register .fleft{float:left;}
	
	#register .fright{
		float:left;
		text-align: left;
		line-height: 36px;
		font-size:14px;
		color: #7D7C55;
		margin-left:10px ;
	}
	#register .clear{clear:both;}
	#register .el-form-item__content{width:330px;}
	#register .form{width:872px;}
	#register .el-button{width:360px;height:60px;margin:auto;border-radius:36px;}\
	#register .image{width:184px;height:184px;}
	#register .img{margin-top:0px;}
	#register .page1{width:1200px;margin: auto;}
	#register .page2{width:100%;height:100%}
	#register .main{background-color: white;width:1200px;height:600px;margin: auto;margin-top: 5%;}
	#register .top{height:36px;color: #272727;font-size:36px;
	               margin: auto;padding-top:38px ; margin-bottom: 39.8px;}
	#register .middle2{width:491px;height:60px;font-size: 16px; line-height:30px;margin: auto;
	                font-family: .PingFangSC-Regular; margin-bottom:36px }
	#register .school{margin-left:0px ;}               
  
</style>