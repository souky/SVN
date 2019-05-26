<template>
  <div id="mycount" class="my-account-container pt30">
    <div class="">
      <el-row type="flex" class="row-bg" justify="space-around">
          <div class="account-first l">
            <el-row class="title">
              <el-col :span="24" class="tc f16">帐户信息</el-col>
              <div class="editmy tc" @click="open_update()">编辑</div>
            </el-row>

            <el-row type="flex" class="row-bg" :model="user" justify="space-around">
              <div class="admin_set">
              <ul>
                <li>
                  <span class="w160 tr">帐户名：</span><span>{{user.userName}}</span>
                </li>
                <li>
                  <span>帐户角色：</span><span>{{user.role}}</span>
                </li>
                <li>
                  <span>真实姓名：</span><span>{{user.name}}</span>
                </li>
                <li>
                  <span>学校：</span><span>{{user.organization.name}}</span>
                </li>
                <li>
                  <span>职位：</span><span>{{user.position}}</span>
                </li>
                <li>
                  <span>联系电话：</span><span>{{user.phone}}</span>
                </li>
                <li>
                  <span>联系邮箱：</span><span>{{user.email}}</span>
                </li>
                <li>
                  <span>个人简历：</span><span>{{user.remark}}</span>
                </li>
              </ul>
            </div>

              <div class="admin_set">
              <ul>
                <li>
                  <span class="tc" :model="user" style="width: 76%;text-align: center;">个人照片</span>
                    <img :src="user.img" class="avatar">
                </li>
              </ul>
            </div>
            </el-row>
          </div>

          <div class="account-first l">
            <el-row class="title">
              <el-col class="tc f16">帐户安全</el-col>
            </el-row>
          	<div class="tc change_pass" @click="open_pass()">修改密码</div>
          </div>
      </el-row>
    </div>
    
    <el-dialog :visible.sync="dialogedit" :show-close="false" >
  		<div class="infoTitle">账户信息</div>
  		<div class="addBody fix" >
  			<el-form  label-width="80px" :model="user">
  				<div class="leftPart l">
  					<el-form-item label="账户名">
					    <el-input v-model="user.userName" readonly></el-input>
					  </el-form-item>
					  <el-form-item label="账户角色">
					    <el-input v-model="user.role" readonly></el-input>
					  </el-form-item>
					  <el-form-item label="真实姓名">
					    <el-input v-model="user.name"></el-input>
					  </el-form-item>
					  <el-form-item label="学校">
					  	<el-select v-model="user.orgId" style="width:100%;" placeholder="请选择学校">
	                <el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
	            </el-select>
					  </el-form-item>
					  <el-form-item label="职位">
					    <el-input v-model="user.position"></el-input>
					  </el-form-item>
					  <el-form-item label="联系电话">
					    <el-input v-model="user.phone"></el-input>
					  </el-form-item>
					  <el-form-item label="联系邮箱">
					    <el-input v-model="user.email"></el-input>
					  </el-form-item>
  				</div>
  				<div class="rightPart l tc">
  					<div style="margin-bottom: 10px;">用户头像</div>
						<el-upload class="" 
  					:action="Url" 
  					:show-file-list="false"
  					:with-credentials="true"
  					:on-success="handleAvatarSuccess"
  					:before-upload="beforeAvatarUpload">
  						<img v-if="user.img" :src="user.img" class="avatar">
  						<i v-else class="el-icon-plus avatar-uploader-icon" style="color:#bfcbd9;border-color:#bfcbd9"></i>
						</el-upload>
  				</div>
  				<div class="l" style="width:90%;">
  					<el-form-item label="课程简介">
					    <el-input type="textarea" :rows="3" resize="none"></el-input>
					  </el-form-item>
  				</div>
				</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="update_user()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="close_update()">取消</div>								
				</div>
			</div>
		</el-dialog>
		
		<el-dialog :visible.sync="dialogpass" size="tiny" :show-close="false">
    	<div class="infoTitle" :model="infoTitles">{{infoTitles}}</div>
  		<div class="editBody">
  			<el-form :inline="true" :rules="rules" ref="psw_update_s" class="demo-form-inline demo-ruleForm">
  				<div class="secondTitle">密码信息</div>
  				<el-form-item label="原密码" prop="psws">
			      <el-input type="password" v-model="psw"></el-input>
			    </el-form-item>
			    <el-form-item label="新密码" prop="newPsw1s">
			      <el-input type="password" v-model="newPsw1"></el-input>
			    </el-form-item>
			    <el-form-item label="确认密码" prop="newPsw2s">
			      <el-input type="password" v-model="newPsw2"></el-input>
			    </el-form-item>
  			</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="update_psw()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="close_psw()">取消</div>								
				</div>
			</div>
    </el-dialog>
    
    
  </div>
</template>

<script>
  export default{
    data () {
    	var validatePass = (rule, value, callback) => {
        if (this.psw == '') {
          callback(new Error('请输入密码'));
        }else if(this.psw.length < 6){
        	callback(new Error('密码长度必须大于6位'));
        } else {
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (this.newPsw1 == '') {
          callback(new Error('请输入新密码'));
        }else if(this.newPsw1.length < 6){
        	callback(new Error('密码长度必须大于6位'));
        } else {
          callback();
        }
      };
      var validatePass3 = (rule, value, callback) => {
        if (this.newPsw2 == '') {
          callback(new Error('请再次输入密码'));
        } else if (this.newPsw2 != this.newPsw1) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        msg:'hello vue',
        dialogedit:false,
        dialogpass:false,
        user:{
        	organization:{name:''},
        	name:'',
        	realname:'',
        	
        },
        optionSchool:null,
        infoTitles:'',
        psw:'',
        newPsw1:'',
        newPsw2:'',
        flagLogin:false,
        rules: {
          psws: [
            { validator: validatePass, trigger: 'blur' }
          ],
          newPsw1s: [
            { validator: validatePass2, trigger: 'blur' }
          ],
          newPsw2s: [
            { validator: validatePass3, trigger: 'blur' }
          ],
        },
        
        Url:''
      }
    },
    methods:{
    	initUserData(obj,data){
    		this.user = data.result.user;
    		var baseUrl = this.getBaseUrl();
    		this.user.img = baseUrl + this.user.img
    		delete this.user["createDate"];
    		delete this.user["updateDate"];
    	},
    	handleAvatarSuccess(res, file){
		  	var baseUrl = this.getBaseUrl();
    		this.user.img = baseUrl + res.result.path
		  }, 
		  beforeAvatarUpload(file){
		  	const isJPG = file.type === 'image/jpeg';
	        const isPNG = file.type === 'image/png';
	        const isIMG = isJPG||isPNG;
	        const isLt2M = file.size / 1024 / 1024 < 2;

	        if (!isIMG) {
	          this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
	        }
	        if (!isLt2M) {
	          this.$message.error('上传头像图片大小不能超过 2MB!');
	        }
	        return isIMG && isLt2M;
		  },
    	open_update(){
    		this.get_options(this,"","optionSchool");
    		this.dialogedit = true;
    		this.infoTitles = '修改信息';
    	},
    	close_update(){
    		initUser(this,this.initUserData);
    		this.dialogedit = false;
    	},
    	update_user(){
				delete this.user["organization"];
				var baseUrl = this.getBaseUrl();
				this.user.img = this.user.img.replace(baseUrl,"");
    		this.postHttp(this,this.user,"user/updateUser",this.ajax_handle);
    	},
    	open_pass(){
    		this.dialogpass = true;
    		this.psw = '';
    		this.newPsw1 = '';
    		this.newPsw2 = '';
    	},
    	close_psw(){
    		this.dialogpass = false;
    		this.psw = '';
    		this.newPsw1 = '';
    		this.newPsw2 = '';
    	},
    	update_psw(){
    		this.$refs['psw_update_s'].validate((valid) => {
          if (valid) {
          	this.infoTitles = '修改密码';
		    		var data = {psw:this.psw,newPsw1:this.newPsw1,newPsw2:this.newPsw2};
		    		this.postHttp(this,data,"user/resetPsw",this.ajax_handle);
		    		this.flagLogin = true;
          }else{
          	return;
          }
        });
    		
    	},
    	ajax_handle(obj,data){
    		if(data.code=="10000"){
    			this.notify_jr(this,this.infoTitles,'操作成功','success');
    			initUser(this,this.initUserData);
    			this.dialogedit = false;
    			if(this.flagLogin){
    				this.postHttp(this,data,"logout",null);
						this.$router.push({ path: '/login' });
    			}
    		}else{
    			this.notify_jr(this,this.infoTitles,data.message,'error');
    			this.flagLogin = false;
    		}
    	},
    	
    },
    mounted:function(){
    	initUser(this,this.initUserData);
    	this.Url=this.getBaseUrl()+"uploadFile/upload";
    }
    
  }
  function initUser(obj,fn){
  	obj.postHttp(obj,{},"user/getLoginUser",fn);
  }
</script>

<style lang="scss">
  #mycount .account-first {
    width: 560px;
    padding-bottom: 30px;
    background: white;
    .title {
      height: 55px;
      line-height: 55px;
      border-bottom: 1px solid #66BB6A;
    }
    .admin_set{
      width: 45%;
      /*background-color: #F9FAFC;*/
      margin: 20px auto 0;
      ul > li{
        padding: 10px;
        span{
          width: 100%;
          color: #666;
          display: inline-block ;
        }
        span:first-child {
          width: 40%;
          text-align: right;
        }
        span:last-child {
          width: 60%;
          text-align: left;
        }
      }
    }
  }
  
  #mycount .change_pass{color: #66BB6A;border:1px #66BB6A solid;width:200px;margin:auto;height:40px;line-height: 40px;margin-top:20px;border-radius: 3px;cursor: pointer;}
  #mycount .editmy{width:80px;height:55px;line-height: 55px;cursor: pointer;position: absolute;right:0px;top:0px;z-index: 99;color: #66BB6A;}
  
  #mycount .addBody{background:#fff;min-height: 500px;padding:20px 40px;}
  
  #mycount .leftPart{width:40%;}
  #mycount .rightPart{width:60%;}
  #mycount .avatar-uploader-icon{border:1px solid #bfcbd9;}
  #mycount .avatar-uploader-icon:hover{border-color:#66BB6A}
  #mycount .leftPart .el-date-editor.el-input{width:100%;}
  
  #mycount .bigSteel{width: 96%;border:1px #BFCBD9 solid;height: 200px;border-radius: 3px;padding:5px 2%;}
  #mycount .el-textarea__inner:focus{border-color:#66BB6A}
  
  #mycount .el-dialog--tiny .el-form--inline .el-form-item{margin-right:0px;width:100%;}
  #mycount .el-dialog--tiny  .el-form-item__label{width:25%}
	#mycount .el-dialog--tiny  .el-form-item__content{width:70%;}
	#mycount .el-dialog--tiny .editBody{min-height: 100px;}
</style>