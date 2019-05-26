<template>
<div id="personaldata">
	<p class="personaldata_title">个人资料</p>
	<hr style="width:95%;background-color:#E5E5E5;">
	<div class="personaldata_padding" v-show="isAlter">
		<el-form label-position="left" :model="form" label-width="80px">
			<el-form-item label="头像">
			    <div class="personaldata_logo">
			    	<el-upload
					  class="avatar-uploader"
					  :action="Url"
					  :with-credentials="true"
					  :show-file-list="false"
					  :on-success="handleAvatarSuccess"
					  :before-upload="beforeAvatarUpload">
					  <img v-if="imageUrl" :src="imageUrl" class="avatar">
					  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
			    </div>
			 </el-form-item>
			 <el-form-item label="真实姓名">
			    <p>{{form.name}}</p>
			 </el-form-item>
			 <el-form-item label="学校">
			    <p>{{form.organization.name}}</p>
			 </el-form-item>
			 <el-form-item label="年级">
			    <el-select v-model="form.orgId">
			    	<el-option v-for="item in Sclass" :key="item.id" :label="item.name" :value="item.id"></el-option>
			    </el-select>
			 </el-form-item>
			 <el-form-item label="性别">
			    <template>
				  <el-radio class="radio" v-model="form.sex" label="M">男</el-radio>
				  <el-radio class="radio" v-model="form.sex" label="W">女</el-radio>
				</template>
			 </el-form-item>
			 <el-form-item label="班主任">
			    <el-select v-model="form.teacherId">
			    	<el-option v-for="item in Steacher" :key="item.id" :label="item.name" :value="item.id"></el-option>
			    </el-select>
			 </el-form-item>
			 <el-form-item label="学号">
			    <el-input v-model="form.studentid" style="width:217px;" placeholder="请输入学号"></el-input>
			 </el-form-item>
			 <el-form-item label="联系手机">
			    <el-input v-model="form.phone" style="width:217px;" placeholder="请输入手机号"></el-input>
			 </el-form-item>
			 <el-form-item label="邮箱">
			    <el-input v-model="form.email" style="width:217px;" placeholder="请输入邮箱"></el-input>
			 </el-form-item>
			 <el-form-item label="个人简介">
			    <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 5}" v-model="form.remark" style="width:770px;" placeholder="请输入个人信息"></el-input>
			 </el-form-item>
			 <el-form-item>
			    <el-button type="primary" @click="onSubmit">立即创建</el-button>
			 </el-form-item>
		</el-form>
	</div>
	<div class="personaldata_padding" v-show="isView">
		<el-form label-position="left" :model="form" label-width="80px">
			<el-form-item label="头像">
			    <div class="personaldata_logo">
			    	<img :src="form.img" width="150px" height="150px">
			    </div>
			 </el-form-item>
			 <el-form-item label="真实姓名">
			    <p>{{form.name}}</p>
			 </el-form-item>
			 <el-form-item label="学校">
			    <p>{{form.organization.name}}</p>
			 </el-form-item>
			 <el-form-item label="年级">
			    <p>{{form.gradeName}}</p>
			 </el-form-item>
			 <el-form-item label="性别">
			    <p v-if="form.sex=='W'">女</p>
			    <p v-else>男</p>
			 </el-form-item>
			 <el-form-item label="班主任">
			    <p>{{form.teacher}}</p>
			 </el-form-item>
			 <el-form-item label="学号">
			    <p>{{form.studentid}}</p>
			 </el-form-item>
			 <el-form-item label="联系手机">
			    <p>{{form.phone}}</p>
			 </el-form-item>
			 <el-form-item label="邮箱">
			    <p>{{form.email}}</p>
			 </el-form-item>
			 <el-form-item label="个人简介">
			 	<div style="width:800px">
			    <p style="word-break:break-all">{{form.remark}}</p>
			    </div>
			 </el-form-item>
			 <el-form-item>
			    <el-button type="primary" @click="onAlter">编辑</el-button>
			 </el-form-item>
		</el-form>
	</div>
</div>
</template>
<script>
export default {
    data() {
      return {
       form:{
       	organization:{name:''}
       },
       Sclass:[],
       Steacher:[],
       imageUrl:'../../../static/img/toux1.png',
       isView:true,
       isAlter:false,
       Url:'',
      }
    },
    created:function(){
    	this.getdata();
    	this.Url=this.getBaseUrl()+"uploadFile/upload";

    },
    methods: {
      getdata(){
      	var userid="";
      	var baseUU = this.getBaseUrl();
      	this.postHttp(this,{},"user/getLoginUser",function(obj,data){
      		data.result.user.img = baseUU + data.result.user.img;
			obj.form=data.result.user;
			obj.imageUrl=data.result.user.img;
			obj.form.teacher=data.result.teacher.name;
			obj.form.gradeName=data.result.grade.name;
			obj.Sclass=data.result.grades;
			obj.Steacher=data.result.teachers;
			if(data.result.sex==""||data.result.sex==null){
				data.result.sex='M';
			}
			
		});
      },
      onSubmit() {
       this.postHttp(this,{img:this.form.img,gradeId:this.form.class,sex:this.form.sex,teacherId:this.form.teacher,phone:this.form.phone,email:this.form.email,remark:this.form.remark,StudyNum:this.form.studentid,Id:this.form.id,},"/study/user/updateUser",function(obj,data){
			obj.getdata();
		});
       this.isView=true;
       this.isAlter=false;
      },
      onAlter:function(){
      	this.isView=false;
      	this.isAlter=true;
      },
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
        this.form.img=res.result.path;
      },
      beforeAvatarUpload(file) {
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
      }
    }
  }
</script>

<style>
#personaldata .personaldata_title{
	margin-top: 36px;
	margin-left: 40px;
}
#personaldata .personaldata_padding{
	margin-top: 20px;
	margin-left: 40px;
}
#personaldata .personaldata_logo{
	width: 150px;
	height: 150px;
	border-radius: 50%;
    overflow: hidden;
}
#personaldata .el-form-item{
	margin-bottom:19px !important;
}
#personaldata .el-button{
	padding: 6px 47px !important;
	border-radius: 25px !important;
}
#personaldata .avatar{
	width: 150px;
	height: 150px;
}
</style>