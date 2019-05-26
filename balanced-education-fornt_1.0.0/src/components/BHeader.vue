<template>
	<div id="header" class="fix box_s">
		<div class="leftPart l fix">
			<router-link to="/index">
				<div class="leftItems l">首页</div>
			</router-link>
			
			<router-link to="/allClass">
				<div class="leftItems l">所有课程</div>
			</router-link>
			
			<router-link to="/allFile">
				<div class="leftItems l">所有教辅</div>
			</router-link>
			
			<router-link to="/liveRoom">
				<div class="leftItems l">直播间</div>
			</router-link>
			
			<router-link to="/memberCenter/personaldata">
				<div class="leftItems l">个人中心</div>
			</router-link>
			
		</div>
		<div class="rightPart r">
			<div class="l search">
				<el-input placeholder="搜索内容" icon="search" v-model="searchKey" :on-icon-click="handleIconClick"></el-input>
			</div>
			<!-- 未登录 -->
			<div v-if="isshow" class="inline__box">
				<div class="l rightItems" @click="dialogFormVisible = true">登录</div>
				<router-link to="/register">
				<div class="l rightItems">注册</div>
				</router-link>
			</div>
			<div v-else class="inline__box">
				<el-dropdown trigger="click" >
					<div class="l rightItems" @click="test">
						<el-badge v-if="unReadMsgNum == 0" :is-dot= "false" class="item">
						  <img src="../../static/img/header/message.png" />
						</el-badge>
						<el-badge v-else :is-dot= "true" class="item">
						  <img src="../../static/img/header/message.png" />
						</el-badge>
					</div>
					 <el-dropdown-menu slot="dropdown">
					    <el-dropdown-item disabled> 
					    	系统消息
					    </el-dropdown-item>
					    <el-dropdown-item divided v-for="item in notic.list" :key="item.id">
					    	<div @click="">
						    	<div class="notic_left">
						    		<el-badge v-if="item.isRead == 0" :is-dot="true" class="item">
									  <img src="../../static/img/header/shi.png" />
									</el-badge>
									<el-badge v-else :is-dot="false" class="item">
									  <img src="../../static/img/header/shi.png" />
									</el-badge>
						    	</div>
						    	<div class="notic_right inline__box">
							    	<p class="notic_title">直播提醒 <span>{{timeF(item.updateDate).format("MM-DD HH:mm")}}</span></p>
							    	<P class="notic_detail">{{item.content}}</P>
						    	</div>
					    	</div>
					    </el-dropdown-item>
					    <el-dropdown-item divided>
					    	<router-link to="notic">
								<div class="allNotic">
									查看消息列表>>
								</div>
							</router-link>
					    </el-dropdown-item>
					 </el-dropdown-menu>
				</el-dropdown>
				<el-dropdown trigger="click">
					<div class="l personRight el-dropdown-link">
						<img v-bind:src="person.img" style="width:30px;height:30px;border-radius:15px;vertical-align:middle" />
						<span>{{person.name}}</span>
					</div>
					 <el-dropdown-menu slot="dropdown" size="middles">
					    <el-dropdown-item>

							<router-link to="/memberCenter/personaldata">

								<div class="menuStyle">
									个人中心
								</div>
							</router-link>
					    </el-dropdown-item>
					    <el-dropdown-item>

							<router-link to="/memberCenter/accountsecurity">

								<div class="menuStyle">
									账户信息
								</div>
							</router-link>
					    </el-dropdown-item>
					    <el-dropdown-item>
					    	<div class="menuStyle" @click="loginOut">
									退出
							</div>
					    </el-dropdown-item>
					  </el-dropdown-menu>
				</el-dropdown>
			</div>
			<!-- 已登路 -->
		</div>
		<div class="logoImg">
			<img src="../../static/img/header/logo.png" />
		</div>
		<div id="Login">
				<el-dialog title="请先登陆" :lock-scroll="true" :visible.sync="dialogFormVisible" :close-on-click-modal = "dialogClose" @close = "resetForm('ruleForm')">
				    <div class="loginBody">
				    	<el-form :model="ruleForm" :rules="rules" ref="ruleForm" >
				    		<el-form-item prop="loginName">
								<el-input size="normal" placeholder="请输入用户名" v-model="ruleForm.loginName"></el-input>
							</el-form-item>
							<el-form-item prop="passWord">
								<el-input size="normal" type="password" placeholder="请输入密码" auto-complete="off" v-model="ruleForm.passWord"></el-input>
							</el-form-item>
							<div class="loginBootm fix">
								<el-checkbox class="l" v-model="ruleForm.checked">记住我</el-checkbox>
								<div class="r"><span class="forget" @click="forget()">忘记密码?</span> | <span class="register" @click="register()">立即注册</span></div>
							</div>
							<el-form-item>
								<el-button class="loginButton" :loading="loading"  @click="submitForm('ruleForm')">登陆</el-button>
							</el-form-item>
						</el-form>
					</div>
				</el-dialog>
			</div>
	</div>
	
</template>

<script>
export default {
  data () {
  	var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码!'));
        } else {
          if (this.ruleForm.passWord !== '') {
            
          }
          callback();
        }
    }

    return {
      msg: '顶部导航栏',
      searchKey:'',
      isshow:true,//登陆注册显示开关
      isnotic:true,//notic开关
      notic:{},//消息红点
      unReadMsgNum:0,
      person:{
      	
	  },
	  pageNum: 1,
	  pageSize:4,
      dialogFormVisible: false,
      dialogClose:false,
      loading:false,
      ruleForm:{
      		loginName:'',
	        passWord:'',
	        checked:true
      	},
      	rules:{
      		loginName:[
      			{required:true, message: '请输入用户名',trigger:'blur'}
      		],
      		passWord: [
            	{validator: validatePass,trigger: 'blur'}
          	]
          }
    }
  },
  mounted:function(){
  	//判断cookie登陆信息初始化
  	if(sessionStorage.getItem("jyname")!= null){
  		this.isshow = false;
  		var mypageNum = this.pageNum;
  		var mypageSize = this.pageSize;
  		var pageData = {pageNum:mypageNum,pageSize:mypageSize};
  		var baseUU = this.getBaseUrl();

	  	this.postHttp(this,{},"user/getLoginUser",function(obj,data){

			obj.person = data.result.user;
			obj.person.img = baseUU + data.result.user.img;

		});
	  	this.postHttp(this,pageData,"message/queryMessagesByUserId",function(obj,data){
	  		obj.notic = data.result;
	  		obj.unReadMsgNum = data.result.navigatePages;
	  	});
	  	
  	}else{
  		this.isshow = true;
  		if(sessionStorage.getItem("remenber")!= null){
			this.ruleForm.checked = sessionStorage.getItem("remenber");
  			console.log(this.ruleForm.checked);
  			this.ruleForm.loginName = sessionStorage.getItem("Name");
  			this.ruleForm.passWord = sessionStorage.getItem("password");
  		}else{
  			this.ruleForm.checked = false
  		}
  	}
  	
  },
  methods:{
  	handleIconClick(ev){
  		console.log(ev);
  	},
  	test(){
  		var mypageNum = this.pageNum;
  		var mypageSize = this.pageSize;
  		var pageData = {pageNum:mypageNum,pageSize:mypageSize};
  		this.postHttp(this,pageData,"message/queryMessagesByUserId",function(obj,data){
	  		obj.notic = data.result;
	  	});
  	},
  	//退出登陆操作
  	loginOut(){
  		//请求操作
  		 this.postHttp(this,'','logout',function(obj,data){
  		 	// delCookie('jyname');
  		 	sessionStorage.removeItem("jyname")
  		 	obj.$router.push({path:'/'});
  		 	obj.$router.go(0)
  		 });
  		//页面跳转
  	},
  	submitForm(formName) {
	    this.$refs[formName].validate((valid) => {
		if (valid) {
			this.loading = true;
	        var userName = this.ruleForm.loginName;
		    var psw = this.ruleForm.passWord;
		    var data = {userName:userName,psw:psw};
		    //登陆请求
		    this.postHttp(this,data,'login',login_press);
		}else {
	            console.log('error submit!!');
	            return false;
	        }
	    });
    },
	resetForm(formName) {
		this.$refs[formName].resetFields();
		if(this.loading==true)
		{
			this.loading = false;
		}
  	},
    forget(){
    		this.dialogFormVisible = false;
    		this.$router.push({path:'/Forget'})
	},
    register(){
    		this.dialogFormVisible = false;
    		this.$router.push({path:'/register'})
    },
    changePoint(){
    	this.unReadMsgNum = 0;
    }

  }
}
function login_press(obj,data){
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
  		// setCookie("jyname",obj.isshow,1000*60);
  		sessionStorage.setItem("jyname",obj.isshow);
  		if(obj.ruleForm.checked == true){
  			console.log(1);
  			sessionStorage.setItem("remenber",obj.ruleForm.checked);
  			sessionStorage.setItem("Name",obj.ruleForm.loginName);
  			sessionStorage.setItem("password",obj.ruleForm.passWord);
  		}else{
  			sessionStorage.setItem("remenber",obj.ruleForm.checked);
  			sessionStorage.setItem("Name",'');
  			sessionStorage.setItem("password",'');
  		}
  		obj.loading = false;
		obj.dialogFormVisible = false;
		obj.$router.go(0)
  	}
  	
  }
</script>

<style>
#Login .el-dialog{width: 530px;height: 330px;}
#Login .el-form-item__error{top:38px;}
#Login .el-dialog__headerbtn .el-dialog__close:hover{color:#66BB6A;}
#Login .el-form-item{margin-bottom: 0}
#Login .el-dialog__header {padding: 20px 40px;text-align: left;background-color: #f4f4f4;border-radius: 3px;line-height: 1}
#Login .el-dialog__title {line-height: 1;font-weight:normal;font-size: 20px;color: #666;}
#Login .el-dialog__body{padding:40px 86px 48px 84px;border-radius: 3px}
#Login .loginBootm{margin-top:0px;line-height: 1}
#Login .loginBootm .r{cursor:pointer;color:#5B5B5B;}
#Login .register{color: #66BB6A;font-size: 14px;}
#Login .loginButton{padding:0;border:1px solid #66bb6a;width:100%;height: 40px;background: #66BB6A;color:#fff;border-radius:27px;cursor: pointer;text-align: center;font-size: 16px;margin-top:14px;}
#Login .el-input--normal .el-input__inner{height: 40px;line-height: 40px;width: 360px;border-radius: 3px;margin-bottom: 19px;color: #272727}
#Login .el-checkbox__label{color:#5B5B5B}
#Login .el-checkbox__input.is-checked .el-checkbox__inner {background: #6ED56C;border-color: #6ED56C;}
.inline__box{display: inline-block;}
.inline__box .rightItems img{vertical-align: middle;}
.inline__box .el-badge__content.is-fixed.is-dot{top:22px;right:8px;}
.inline__box .el-dropdown-menu__item.is-disabled{color: #272727;}
#header .rightPart .personRight {height:60px;text-align: center;cursor: pointer;font-size:16px;color:#666;margin-right: 30px;}
#header .rightPart .personRight span{font-size:14px;}
.menuStyle{text-decoration: none;color: #6ED56C;font-size: 16px;text-align: center;}
.el-dropdown-menu__item a{text-decoration: none}
.el-dropdown-menu--middles{width: 130px}
.el-dropdown-menu__item--divided{margin-top: 3px}
.el-dropdown-menu__item--divided:before{height:3px;}
.notic_left{width: 48px;height: 48px;padding:0 10px;display: inline-block;}
.notic_left img{width: 48px;height:48px;}
.notic_left .item{vertical-align: inherit;}
.notic_title{font-size: 14px;color: #272727;}
.notic_title span{margin-left: 20px;color: #2A2A2A}
.notic_detail{color: #7D7C55;font-size:13px;max-width: 300px;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;line-height: 20px}
.allNotic{color:#272727;font-size: 16px;line-height: 40px;text-align: center;}
</style>