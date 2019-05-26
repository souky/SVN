<template>

<div id="LoginS" class="Login-container main_body">
	<div class="loginInput">
		<div class="title tc">
			登录
		</div>
		<div class="loginBody">
			<el-form :model="ruleForm" :rules="rules" ref="ruleForm" >
				<el-form-item prop="loginName">
					<el-input size="large" placeholder="请输入用户名" v-model="ruleForm.loginName"></el-input>
				</el-form-item>
				<el-form-item prop="passWord">
					<el-input size="large" type="password" placeholder="请输入密码" auto-complete="off" v-model="ruleForm.passWord"></el-input>
				</el-form-item>
			<div class="loginBootm fix">
				<el-checkbox class="l" v-model="ruleForm.checked">记住我</el-checkbox>
				<div class="r">忘记密码?</div>
			</div>
			</el-form>	
			<div class="loginButton" @click="submitForm('ruleForm')">
				登录
			</div>
		</div>
	</div>
</div>
</template>
<script>
 export default{
    data () {
     var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码!'));
        } else {
          if (this.ruleForm.passWord !== '') {
            
          }
          callback();
        }
      };
      return {
      	ruleForm:{
      		loginName:'',
	        passWord:'',
	        checked:false
      	},
      	rules:{
      		loginName:[
      			{required:true, message: '请输入用户名',trigger:'blur'}
      		],
      		passWord: [
            	{ validator: validatePass,trigger: 'blur' }
          	]
      	},
      }
    },
    mounted:function(){
	  	var height = document.documentElement.clientHeight - 60;
	  	document.getElementById("LoginS").style.height = height+'px';
    },
    methods:{
    	submitForm(formName) {
	        this.$refs[formName].validate((valid) => {
			if (valid) {
				this.loading = true;
	             var userName = this.ruleForm.loginName;
		    	 var psw = this.ruleForm.passWord;
		    	 var data = {userName:userName,psw:psw};
		    	 //登陆请求
		    	  this.postHttp(this,data,'login',function(obj,data){
	    			var code = data.code;
				  	if(code != "10000") {
				  		obj.loading = false;
				  		obj.$notify({
					      title: '错误',
					      message: data.message,
					      offset: 100,
					      type:'error'
					    });
				  	}else{
						obj.$router.push("/index")
				  	}	
	    		});
		    
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
#LoginS{
	width:100%;
    background:url('../../static/img/register/registerBackground.png') no-repeat;
	background-size:100%;
	position:relative;
}
#LoginS .loginInput{width: 430px;height: 400px;padding:10px 35px;position:absolute;top:30%;right:150px;background: #fff;}
#LoginS .loginInput .title{padding:10px 0;border-bottom:1px solid #66BB6A;font-size: 28px;color:#66BB6A;}
#LoginS .loginBody{width:410px;margin-left:10px}
#LoginS .loginBootm{margin-top:30px;}
#LoginS .loginBootm .r{cursor:pointer;color:#5B5B5B;}
#LoginS .loginButton{width:100%;height: 60px;line-height:60px;background: #66BB6A;color:#fff;border-radius: 4px;cursor: pointer;text-align: center;font-size: 20px;margin-top:40px;}

#LoginS .el-input--large .el-input__inner{height: 50px;line-height: 50px;margin-top: 30px;}
#LoginS .el-checkbox__label{color:#5B5B5B}
</style>