/**
 * @description 应用程序入口文件.
 */

import App from './App'
import Vue from 'vue'

import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import VueValidator from 'vue-validator'

import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import axios from 'axios';

// vue配置
Vue.config.debug = process.env.NODE_ENV !== 'production';
Vue.config.productionTip = false;

// 附加插件
Vue.use(VueResource);
Vue.use(VueRouter);
Vue.use(VueValidator);
Vue.use(ElementUI);

//axios配置
axios.defaults.withCredentials=true;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
Vue.prototype.$axios = axios;
// 挂载
new Vue({
  el: '#app',
  router: router,
  template: '<app/>',
  components: { App }
});
var querystring = require('querystring');

const baseUrl = "http://192.168.128.176:8080/balanced-education/";

Vue.prototype.postHttp = function(obj,data,address,fn){
	obj.$axios.post(baseUrl+address,querystring.stringify(data),{withCredentials : true}).then(response => {
		console.log(response.data.code)
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
	      type:'error'
	    });
	})
}

Vue.prototype.getHttp = function(obj,data,address,fn){
	obj.$axios.get(baseUrl+address,data,{emulateJSON: true,credientials:true}).then(response => {
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
	      type:'error'
	    });
	})
}









