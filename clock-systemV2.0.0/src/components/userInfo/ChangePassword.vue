<template>
	<div id="ChangPsw" class="main_body">
		<div class="change">
			<el-form :model="passWord" status-icon :rules="passRule" ref="passWordRef" label-width="100px" class="demo-ruleForm">
				<el-form-item label="原密码" prop="oldPassword">
					<el-input type="password" v-model="passWord.oldpsw" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="新密码" prop="newPassword">
					<el-input type="password" v-model="passWord.newpsw" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="confimPassword">
					<el-input type="password" v-model="passWord.newpsw_" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label=" ">
					<div class="btn">
						<el-button type="primary" @click="changePass('passWordRef')">保存</el-button>
					</div>
				</el-form-item>	
			</el-form>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			//密码验证
			var oldPassword = (rule, value, callback) => {
				if(value == '') {
					callback(new Error('请输入原密码'));
				} else {
					callback();
				}
			};
			var newPassword = (rule, value, callback) => {
				if(value == '') {
					callback(new Error('请输入新密码'));
				} else {
					callback();
				}
			};
			var confimPassword = (rule, value, callback) => {
				var new_pass = this.passWord.newpsw;
				var old_pass = this.passWord.oldpsw;
				value = this.passWord.newpsw_;
				if(value == '') {
					callback(new Error('请再次输入新密码'));
				} else if(new_pass != value) {
					callback(new Error('两次密码输入不一致'));
				} else if(old_pass == new_pass) {
					callback(new Error('新密码不能与原密码一致'));
				} else {
					callback();
				}
			};
			return {
				msg: 'ChangPsw',
				passWord: {
					oldpsw: '',
					newpsw: '',
					newpsw_: ''
				},
				passRule: {
					oldPassword: [{
						validator: oldPassword,
						trigger: 'blur'
					}],
					newPassword: [{
						validator: newPassword,
						trigger: 'blur'
					}],
					confimPassword: [{
						validator: confimPassword,
						trigger: 'blur'
					}]
				}
			}
		},
		mounted: function() {},
		methods: {
			//更改密码
			changePass(formName){
				this.$refs[formName].validate((valid) => {
		          if (valid) {
		            this.postHttp("clockuser/changePassword",this.passWord,res=>{
									if(res.code == 10000){
										this.notify_success("修改密码成功");
										this.passWord = {};
										this.$refs[formName].clearValidate();
									}else{
										this.notify_error(res.message);
									}
								})
		          } else {
		            console.log('验证失败!!');
		            return false;
		          }
		        });
			}
		}
	}
</script>

<style scoped="scoped">
.change{
	width:400px;
	margin:40px auto;
}
.btn{
	width:150px;
	margin:10px auto;
}
</style>