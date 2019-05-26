<template>
	<div id="schoolInfo" class="main_body">

		<div class="info_body">
			<el-form label-position="left" label-width="100px" :rules="rules" ref="schoolInfo" class="demo-ruleForm" :model="schoolInfo">
			  <el-form-item label="学校名称" prop="schoolName" :maxlength="50">
			    <el-input v-model="schoolInfo.schoolName"></el-input>
			  </el-form-item>
			  <el-form-item label="学校学段" prop="periodArray">
			    <el-checkbox-group v-model="schoolInfo.periodArray" >
			      <el-checkbox label="小学" name="periodArrays"></el-checkbox>
			      <el-checkbox label="初中" name="periodArrays"></el-checkbox>
			      <el-checkbox label="高中" name="periodArrays"></el-checkbox>
			    </el-checkbox-group>
			  </el-form-item>
			  <el-form-item label="学校地址">
			    <el-input v-model="schoolInfo.schoolAddr" :maxlength="50"></el-input>
			  </el-form-item>
			  <el-form-item label="学校电话" prop="schoolMobile">
			    <el-input v-model="schoolInfo.schoolMobile" :maxlength="maxLength"></el-input>
			  </el-form-item>
			  <el-form-item label="学校唯一码">
			    <el-input v-model="schoolInfo.schoolOnlyCode" readonly disabled></el-input>
			  </el-form-item>
			  <el-form-item label="学校联系人">
			    <el-input v-model="schoolInfo.schoolContact" :maxlength="10"></el-input>
			  </el-form-item>
			  <el-form-item label="联系人电话"  prop="schoolContactMobile">
			    <el-input v-model="schoolInfo.schoolContactMobile" :maxlength="maxLength"></el-input>
			  </el-form-item>
			  <el-form-item label="学校学科" prop="subjectArray">
			    <el-checkbox-group v-model="schoolInfo.subjectArray">
			      <el-checkbox v-for="e in subjectList" :label="e.subjectName" :key="e.subjectName" :value="e.subjectName" name="subjectArrays"></el-checkbox>
			    </el-checkbox-group>
			  </el-form-item>
			</el-form>

			<div class="ok_btn" v-show="showEdit" @click="updateSchool">
				确认修改
			</div>
		</div>

	</div>

</template>

<script>
export default {
  data () {
		var validatePhone = (rule, value, callback) => {
			var reg = /^1[3|4|5|8|9|7|6][0-9]\d{3,11}$/;
			var regTell = /^((0\d{2,4})-)(\d{7,8})(-(\d{3,}))?$/;
			if(reg.test(value) || regTell.test(value)){
	      	callback();
      }else{
      		callback(new Error('电话格式不正确'));
      }
    };
    return {
      msg: 'schoolInfo',
      schoolInfo:{},
      maxLength:15,
      subjectList:[],
      showEdit:false,
      rules: {
          schoolName: [
            { required: true, message: '请输入考试名称', trigger: 'blur' }
          ],
          periodArray: [
            { type: 'array', required: true, message: '请至少选择一个学段', trigger: 'change' }
          ],
          subjectArray: [
            { type: 'array', required: true, message: '请至少选择一个学科', trigger: 'change' }
          ],
          schoolMobile:[
          	{required: true, validator: validatePhone, trigger: 'blur'}
          ],
          schoolContactMobile:[
          	{required: true, validator: validatePhone, trigger: 'blur'}
          ],
      }
    }
  },
  mounted:function(){
  	this.postHttp(this,{},"school/querySchools",function(obj,res){
  		if(res.result.periodArray == "" || res.result.periodArray == undefined){
  			res.result['periodArray'] = new Array();
  		}
  		if(res.result.subjectArray == "" || res.result.subjectArray == undefined){
  			res.result['subjectArray'] = new Array();
  		}
  		obj.schoolInfo = res.result;
  		obj.$refs['schoolInfo'].resetFields();
  	})

  	this.postHttp(this,{pageNum:1,pageSize:0},'subject/querySubjects',function(obj,res){
  		obj.subjectList = res.result.list;
  	});

  	this.postHttp(this,{},'user/getLoginUser',function(obj,res){
  		if(res.result.roleId == '2'){
  			obj.showEdit = true;
  		}
  	});


  },
  methods:{
	updateSchool(){
		this.$refs['schoolInfo'].validate((valid) => {
          if (valid) {
          	delete this.schoolInfo["createDate"];
			delete this.schoolInfo["updateDate"];
			this.postHttp(this,this.schoolInfo,"school/updateSchool",function(obj,res){
		  		if(res.code == '10000'){
	  				obj.notify_success();
	  			}else{
	  				obj.notify_jr(obj,'操作错误',res.message,'error');
	  			}
		  	})
          } else {
            return false;
          }
        });

	}
  }
}
</script>

<style>
#schoolInfo{

}
#schoolInfo .info_body{
	width: 30%;
	margin:80px auto;
}
</style>
