<template>
	<div id="login" ref="loginHight" @keyup.enter.native="onSubmit">
		<div class="login-main">
			<div class="login-main-header">
				<p>登录</p>
			</div>
			<div class="login-main-body">
				<el-form ref="form" :rules="rules" :model="form">
				  <el-form-item prop="userName">
				    <el-input placeholder="用户名" v-model="form.userName" @keyup.enter.native="onSubmit">
				    	<template slot="prepend"><img src="../../static/img/login/user.png" alt=""></template>
				    </el-input>
				  </el-form-item>
				  <el-form-item prop="psw">
				    <el-input type="password" placeholder="请输入密码" v-model="form.psw" @keyup.enter.native="onSubmit">
				    	<template slot="prepend"><img src="../../static/img/login/password.png" alt=""></template>
				    </el-input>
				  </el-form-item>
				  <el-form-item>
				    <el-button type="primary" @click="onSubmit" >登录</el-button>
				  </el-form-item>
				  <el-form-item class="login-main-foot">
				  		<p @click="forgetPsw" style="cursor:pointer">忘记密码？</p>
				  </el-form-item>
				</el-form>
			</div>
		</div>
		<div class="login-foot">
			<p>© 2017-2018 苏州金瑞阳信息科技有限公司 苏ICP备17029802号</p>
		</div>

		<el-dialog title="请先修改初始密码" :visible.sync="dialogVisible" width="30%"  :close-on-click-modal='booleanFasle' :close-on-press-escape='booleanFasle'>
		  <div class="dialog_body">
		  	<el-form label-position="right" :rules="rulesChange" ref="changUser" class="demo-ruleForm" label-width="80px" :model="changUser">
	  		  <el-form-item label="原密码" prop="psw" >
			  	<el-input type="password" v-model="changUser.psw" placeholder="原密码"></el-input>
			  </el-form-item>
			  <el-form-item label="新密码" prop="newPsw1" >
			  	<el-input type="password" v-model="changUser.newPsw1" placeholder="新密码"></el-input>
			  </el-form-item>
			  <el-form-item label="确认密码" prop="newPsw2" >
			  	<el-input type="password" v-model="changUser.newPsw2" placeholder="确认密码"></el-input>
			  </el-form-item>
			</el-form>
		  </div>
		  <span slot="footer" class="dialog-footer">
		    <el-button type="primary" @click="saveEdit">确 定</el-button>
		  </span>
		</el-dialog>
	</div>
</template>

<script>
//var crypto = require('crypto');
export default {
	data(){
		var validatePass = (rule, value, callback) => {
			var psw = this.changUser.newPsw1;
			if(psw == ''){
				callback(new Error('请输入密码'));
			}else{
				callback();
			}
	    };
	    var validatePass2 = (rule, value, callback) => {
	    	var psw = this.changUser.newPsw1;
	    	var psw2 = this.changUser.newPsw2;
	        if (psw == '') {
	          callback(new Error('请再次输入密码'));
	        } else if (psw != psw2) {
	          callback(new Error('两次输入密码不一致!'));
	        } else {
	          callback();
	        }
	    };

		return{
			form:{
				userName:"",
				psw:"",
			},
			changUser:{},
			dialogVisible:false,
			booleanFasle:false,
			rules:{
				userName:[
					 { required: true, message: '请输入用户名', trigger: 'blur' },
				],
				psw:[
					 { required: true, message: '请输入密码', trigger: 'blur' },
				]
			},
			rulesChange:{
				psw:[
					 { required: true, message: '请输入原密码', trigger: 'blur' },
				],
				newPsw1:[
					 { required: true, message: '请输入新密码', trigger: 'blur' },
					 {validator: validatePass, trigger: 'blur'}
				],
				newPsw2:[
					 { required: true, message: '请再次输入密码', trigger: 'blur' },
					 {validator: validatePass2, trigger: 'blur'}
				]
			},
		}
	},
	mounted:function(){
		document.getElementById("header").style.display = 'none';
		document.getElementById("menu").style.display = 'none';
		let h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		this.$refs.loginHight.style.height=h+"px";
	},
	methods:{
		onSubmit(){

			// var password = this.form.psw;
			// var hmac = crypto.createHmac('sha256','asd');
			// hmac.update(password);
			// var s =	hmac.digest('hex');
			// console.log(s);
			// return;

			this.postHttp(this,this.form,'login',function(obj,res){
				if(res.code == '10000'){
//					if(res.result.isChangedPsw == '0'){
//						obj.dialogVisible = true;
//					}else{
						document.getElementById("header").style.display = 'block';
						document.getElementById("menu").style.display = 'block';
						obj.$router.push({path:'/home'});
//					}
				}else{
					obj.notify_jr(obj,'错误提示',res.message,'error');
				}
			})

		},
		saveEdit(){
			this.$refs['changUser'].validate((valid) => {
	          if (valid) {
	            this.postHttp(this,this.changUser,'user/resetPsw',function(obj,res){
					if(res.code == '10000'){
						document.getElementById("header").style.display = 'block';
						document.getElementById("menu").style.display = 'block';
						obj.$router.push({path:'/home'});
					}else{
						obj.notify_jr(obj,'错误提示',res.message,'error');
					}
				})
	          } else {
	            return false;
	          }
	        });
		},
		forgetPsw(){
			this.notify_jr(this,'提示','忘记密码请联系管理员','warning');
		}
	}
}
</script>

<style>
#login{
	width: 100%;
	height: 100%;
	background-image:url(../../static/img/login/bg-d.png);
	background-repeat:no-repeat;
	background-size:100% 100%;
	-moz-background-size:100% 100%;
}
#login .login-foot{
	width: 100%;
	height: 110px;
	background: rgba(0,0,0,0.7);
	position: absolute;
	bottom: 0px;
	text-align: center;
}
#login .login-foot p{
	line-height: 110px;
	font-size: 24px;
	color: #fff;
	letter-spacing: 0;

}
#login .login-main{
	position: absolute;
	top: 27.7%;
	left:55.9%;
	width: 450px;
	height: 400px;
	background: #fff;
	border-radius: 3px;
}
#login .login-main-header{
	width: 374px;
	height: 43px;
	margin: auto;
	text-align: center;
	padding-top:40px;
	border-bottom: 1px solid #44A9FF;
}
#login .login-main-header p{
	font-size: 24px;
	color: #818181;
	letter-spacing: 0;
}
#login .login-main-body{
	width: 374px;
	height: 43px;
	margin: auto;
	margin-top:40px;
}
#login .el-button{
	padding: 15px 171px;
}
#login .el-input__inner{
	height: 50px;
}
#login .el-form-item{
	margin-bottom: 30px;
}
#login .login-main-foot .el-form-item__content{
	line-height: 0;
}
#login .login-main-body i{
	width: 20px;
	margin-left:10px;
}
#login .el-dialog__footer{
	text-align: center;
}
#login .loginDiaTitle{
	width: 100%;
	height:30px;
	line-height: 30px;
	text-align: center;
}
</style>
