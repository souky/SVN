<template>
	<div id="loginMains" class="loginMain fix app-container">
		<div class="l loginImg">
			<img src="../../dist/static/img/loginLeft.png" />
		</div>
		<div class="l loginLine" ></div>
		<div class="l loginInput">
			<div class="title tc">
				登录
			</div>
			<div class="loginBody">
				<el-input size="large" placeholder="请输入用户名" v-model="loginName"></el-input>
				<el-input size="large" type="password" placeholder="请输入密码" v-model="passWord"></el-input>
				
				<div class="loginBootm fix">
					<el-checkbox class="l" v-model="checked">记住我</el-checkbox>
					<div class="r">忘记密码?</div>
				</div>
				
				<div class="loginButton" @click="login_in()">
					登录
				</div>
			</div>
		</div>
	</div>
</template>

<script>
  export default{
    data () {
      return {
        msg:'hello vue',
        loginName:'',
        passWord:'',
        checked:false
      }
    },
    mounted:function(){
    	var height = window.screen.availHeight;
    	var heightOfset = (height - 500)/2;
    	console.log(height+'--'+heightOfset)
    	document.getElementById("loginMains").style.marginTop = heightOfset+"px";
    },
    methods:{
    	login_in(){
    		var userName = this.loginName;
    		var psw = this.passWord;
    		var data = {userName:userName,psw:psw};
    		//console.log(data)
    		this.postHttp(this,data,'login',login_press);
    	},
    	
    }
  }
  
  function login_press(obj,data){
  	var code = data.code;
  	if(code != "10000") {
  		obj.$notify({
	      title: '错误',
	      message: data.message,
	      offset: 100,
	      type:'error'
	    });
  	}else{
  		obj.$router.push({ path: '/home' });
  	}
  	
  }
</script>

<style>
body{
	background: rgba(0,0,0,0);
}
html{background: url(../../dist/static/img/login_background.jpg) no-repeat;}
.loginMain{width: 1000px;height: 400px;margin:100px auto;background: #fff;border-radius: 4px;}
.loginImg{width: 498px;}
.loginImg img{width: 498px;height: 400px;}
.loginLine{width: 1px;height: 380px;margin-top:10px;border-right: 1px #E8E8E8 dashed;}
.loginInput{width: 430px;height: 400px;padding:10px 35px;}
.loginInput .title{padding:10px 0;border-bottom:1px solid #66BB6A;font-size: 28px;color:#66BB6A;}
.loginBody{width:410px;margin-left:10px}
.loginBootm{margin-top:30px;}
.loginBootm .r{cursor:pointer;color:#5B5B5B;}
.loginButton{width:100%;height: 60px;line-height:60px;background: #66BB6A;color:#fff;border-radius: 4px;cursor: pointer;text-align: center;font-size: 20px;margin-top:40px;}

.el-input--large .el-input__inner{height: 50px;line-height: 50px;margin-top: 30px;}
.el-checkbox__label{color:#5B5B5B}
</style>