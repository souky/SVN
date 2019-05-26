<template>
	<div id="LoginS">
		<div class="login_content ">
			<div class="login_left">
				<img src="../../static/img/loginLeft.png" />
			</div>
			<div class="login_right">

				<div class="loginInput">
					<div class="title tc">
						登录
					</div>
					<div class="loginBody">
						<el-form :model="ruleForm" :rules="rules" ref="ruleForm">
							<el-form-item prop="loginName">
								<el-input size="large" placeholder="请输入用户名" v-model="ruleForm.loginName"></el-input>
							</el-form-item>
							<el-form-item prop="passWord">
								<el-input size="large" type="password" placeholder="请输入密码" auto-complete="off" v-model="ruleForm.passWord"></el-input>
							</el-form-item>
						</el-form>
						<div class="loginButton" @click="submitForm('ruleForm')">
							登录
						</div>
					</div>
				</div>

			</div>

		</div>
	</div>
</template>
<script>
	export default {
		data() {
			var validatePass = (rule, value, callback) => {
				if(value === '') {
					callback(new Error('请输入密码!'));
				} else {
					if(this.ruleForm.passWord !== '') {

					}
					callback();
				}
			};
			return {
				ruleForm: {
					loginName: '',
					passWord: '',
					checked: false
				},
				rules: {
					loginName: [{
						required: true,
						message: '请输入用户名',
						trigger: 'blur'
					}],
					passWord: [{
						validator: validatePass,
						trigger: 'blur'
					}]
				},
			}
		},
		mounted: function() {

		},
		methods: {
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if(valid) {
						this.loading = true;
						var userName = this.ruleForm.loginName;
						var psw = this.ruleForm.passWord;
						var data = {
							loginName: userName,
							password: psw
						};
						//登陆请求
						this.postHttp("loginIn",data,res =>{
							if(res.code == '10000'){
								this.$router.push('/');
							}else{
								this.notify_warning(res.message);
							}
						})
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
		}
	}
</script>
<style>
	#LoginS {
		width: 100%;
		background-size: 100%;
		background: url('../../static/img/login_background.jpg');
		position: absolute;
		height: 100%;
		z-index: 99999;
		left: 0px;
		top: 0px;
	}
	#LoginS .login_content{
		width:100%;
		height: 100%;
		display: flex;
		display: -webkit-flex;
		justify-content:center; 
		align-items:center;
	}
	#LoginS .login_content .login_left{
		width:500px;
		height:400px;
	}
	#LoginS .login_content .login_right{
		width:500px;
	}
	#LoginS .login_content .login_left img{
		width:500px;
		height:400px;
	}
	#LoginS .loginInput {
		width: 430px;
		height: 400px;
		padding: 0px 35px;
		background: #fff;
	}
	
	#LoginS .loginInput .title {
		padding: 10px 0;
		border-bottom: 1px solid #00bcd4;
		font-size: 28px;
		color: #00bcd4;
	}
	
	#LoginS .loginBody {
		width: 410px;
		margin-left: 10px
	}
	
	#LoginS .loginBootm {
		margin-top: 30px;
	}
	
	#LoginS .loginBootm .r {
		cursor: pointer;
		color: #00bcd4;
	}
	
	#LoginS .loginButton {
		width: 100%;
		height: 60px;
		line-height: 60px;
		background: #00bcd4;
		color: #fff;
		border-radius: 4px;
		cursor: pointer;
		text-align: center;
		font-size: 20px;
		margin-top: 40px;
	}
	
	#LoginS .el-input--large .el-input__inner {
		height: 50px;
		line-height: 50px;
		margin-top: 30px;
	}
	
	#LoginS .el-checkbox__label {
		color: #5B5B5B
	}
</style>