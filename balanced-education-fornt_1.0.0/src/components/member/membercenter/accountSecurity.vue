<template>
<div id="accountSecurity">
	<p class="accountSecurity_title">{{title}}</p>
	<hr style="width:95%;background-color:#E5E5E5;">
	<div class="accountSecurity_control">
		<div class="accountSecurity_control_password" v-show="accountSecurityControlStyle" @click="accountSecurityControlButton">
			<img src="../../../../static/img/passwordLogo.png">
			<p>修改密码</p>
		</div>
		<div class="accountSecurity_control_password_control" v-show="changeControlStyle">
			<el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="120px" class="demo-ruleForm">
				  <el-form-item label="输入当前密码" prop="nowpass">
				    <el-input v-model.number="ruleForm2.nowpass" style="width:200px;"></el-input>
				  </el-form-item>
				  <el-form-item label="输入新密码" prop="pass">
				    <el-input type="password" v-model="ruleForm2.pass" style="width:200px;" auto-complete="off"></el-input>
				  </el-form-item>
				  <el-form-item label="再次输入新密码" prop="checkPass">
				    <el-input type="password" v-model="ruleForm2.checkPass" style="width:200px;" auto-complete="off"></el-input>
				  </el-form-item>
				  <el-form-item>
				  	<el-button class="l surebuttonStyle" type="primary" @click="submitForm('ruleForm2')">提交</el-button>
    				<el-button class="r new-el-button" @click="resetForm('ruleForm2')">取消</el-button>
				  </el-form-item>
			</el-form>
		</div>
	</div>
</div>
</template>

<script>
export default {
    data() {
    	var checkAge = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入当前密码'));
        } else {
          callback();
        }
      };
    	var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm2.checkPass !== '') {
            this.$refs.ruleForm2.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm2.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
       	title:'账户安全',
       	accountSecurityControlStyle:true,
       	changeControlStyle:false,
       	ruleForm2: {
          pass: '',
          checkPass: '',
          nowpass: ''
        },
        rules2: {
         pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ],
          nowpass:[
          	{ validator: checkAge, trigger: 'blur' }
          ]
        }
      }
    },
    created:function(){
      this.getdata();
    },
    methods: {
      getdata:function(){
        this.$emit('newfind');
      },
    	accountSecurityControlButton:function(){
    		this.title="密码修改";
    		this.accountSecurityControlStyle=!this.accountSecurityControlStyle;
    		this.changeControlStyle=!this.changeControlStyle;
    	},
    	submitForm:function(formName){
    		this.$refs[formName].validate((valid) => {
	          if (valid) {
	            alert('submit!');
              this.postHttp(this,{psw:this.ruleForm2.nowpass,newPsw1:this.ruleForm2.pass,newPsw2:this.ruleForm2.checkPass},"study/user/resetPsw",function(obj,data){
      
              });
	            this.resetForm(formName);
	          } else {
	            this.$message.error('密码错误！');
	            return false;
	          }
	        });
    	},
    	resetForm(formName) {
	        this.$refs[formName].resetFields();
	        this.title="账户安全";
	    	this.accountSecurityControlStyle=!this.accountSecurityControlStyle;
    		this.changeControlStyle=!this.changeControlStyle;
      }
    }
  }
</script>

<style>
#accountSecurity .accountSecurity_title{
	margin-top: 36px;
	margin-left: 40px;
}
#accountSecurity .accountSecurity_control{
	margin-top: 39px;
	min-height:460px
}
#accountSecurity .accountSecurity_control_password{
	margin: 0 auto;
	width: 890px;
	height: 145px;
	background-color: #FDF7DC;
	border: 1px solid #FDDD74;
}
#accountSecurity .accountSecurity_control_password img{
	margin-top: 15px;
	margin-left: 25px;
}
#accountSecurity .accountSecurity_control_password p{
	margin-top: -75px;
	margin-left: 189px;
	color:#272727;
	font-size: 20px;
}
#accountSecurity .accountSecurity_control_password_control{
	margin: 0 auto;
	width: 340px;
	height: 300px;
}
#accountSecurity .surebuttonStyle{
	margin-left: -120px;
}
#accountSecurity .new-el-button:hover{color:#6ED56C;}
</style>