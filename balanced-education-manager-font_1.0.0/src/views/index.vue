<template>
  <div class="app-container">
    <!-- 导航栏 -->
    <z-header></z-header>
    <div class="content-container">
      <el-row style="height: 100%;">
        <!-- 菜单 -->
        <z-menu></z-menu>
        <!-- 主体 -->
        <el-col :span="20" class="work-container">
          <div class="router-container">
            <keep-alive>
              <router-view></router-view>
            </keep-alive>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import ZHeader from './core/header'
  import ZMenu from './core/menu'
  import ZHome from './home/home'
	import Vue from 'vue'
	import axios from 'axios';
	
	axios.defaults.withCredentials=true;
	axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
	Vue.prototype.$axios = axios;

	const baseUrl = "http://192.168.1.213:8080/balanced-education/";
	
  export default {
    name: 'app',
    data () {
      return {
      }
    },
    components: {ZHeader,ZMenu,ZHome},
    mounted:function(){
    	document.getElementsByClassName("el-menu-item")[0].click();
    }
    
  }
  
  var querystring = require('querystring');
	
	Vue.prototype.timeF = require("moment");
	
	Vue.prototype.postHttp = function(obj,data,address,fn){
		obj.$axios.post(baseUrl+address,querystring.stringify(data),{withCredentials : true}).then(response => {
	  		if(response.data.code == "60000" || response.data.code == "50000"){
	  			obj.$router.push({ path: '/login' });
	  		}else{
	  			fn(obj,response.data);
	  		}
	    },response => {
			obj.$notify({
		      title: '网络错误',
		      message: '网络错误',
		      offset: 100,
		      duration:1500,
		      type:'error'
		    });
		})
	}
	
	Vue.prototype.notify_jr = function(obj,title,message,type){
		obj.$notify({
	      title: title,
	      message: message,
	      offset: 100,
	      duration:1500,
	      type:type
	    });
	}
	
	Vue.prototype.getBaseUrl = function(){
		return baseUrl;
	}
	
	Vue.prototype.get_options = function(obj,id,options){
		if(id==""){
			obj.$axios.post(baseUrl+'organization/queryOrg',querystring.stringify({}),{withCredentials : true}).then(response => {
		  		if(response.data.code == "60000" || response.data.code == "50000"){
		  			obj.$router.push({ path: '/login' });
		  		}else{
		  			var id = response.data.result.id;
		  			var data = {parentId:id};
		  			
		  			obj.$axios.post(baseUrl+'organization/getOrganizations',querystring.stringify(data),{withCredentials : true}).then(response => {
					  		if(response.data.code == "60000" || response.data.code == "50000"){
					  			obj.$router.push({ path: '/login' });
					  		}else{
					  			setOption(obj,response.data.result,options);
					  		}
					    },response => {
							obj.$notify({
						      title: '网络错误',
						      message: '网络错误',
						      offset: 100,
						      duration:1500,
						      type:'error'
						    });
						})
		  			
		  		}
		    },response => {
				obj.$notify({
			      title: '网络错误',
			      message: '网络错误',
			      offset: 100,
			      duration:1500,
			      type:'error'
			    });
			})
			
		}else{
			var data = {parentId:id};
			var returndata;
			obj.$axios.post(baseUrl+'organization/getOrganizations',querystring.stringify(data),{withCredentials : true}).then(response => {
		  		if(response.data.code == "60000" || response.data.code == "50000"){
		  			obj.$router.push({ path: '/login' });
		  		}else{
		  			setOption(obj,response.data.result,options);
		  		}
		    },response => {
				obj.$notify({
			      title: '网络错误',
			      message: '网络错误',
			      offset: 100,
			      duration:1500,
			      type:'error'
			    });
			})
			
		}
		
	}
	
	function setOption(obj,data,options){
		if(options=="optionSchool"){
			obj.optionSchool = data;
		}else if(options=="optionGrand"){
			obj.optionGrand = data;
		}else if(options=="optionClass"){
			obj.optionClass = data;
		}
		
	}
</script>
<style>
	html{background:#fff;}
</style>




