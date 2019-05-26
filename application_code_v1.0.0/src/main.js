import Vue from 'vue'
import App from './App'
import router from './router'

import VueRouter from 'vue-router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '../static/css/main.css'
import '../static/css/apps.css'
import axios from 'axios'

// 以下代码是嘉宁加得有锅找他 我不背  搞ie
import promise from 'es6-promise';
promise.polyfill();


/* vue配置 */
Vue.config.debug = process.env.NODE_ENV !== 'production'
Vue.config.productionTip = false

/* 附加插件 */
Vue.use(VueRouter)
Vue.use(ElementUI)


/* axios配置 */
axios.defaults.withCredentials = true
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
Vue.prototype.$axios = axios

/* 时间转化 */
Vue.prototype.timeF = require('moment');
Vue.prototype.echarts = require('echarts');

var querystring = require('querystring');

var baseUrl = "http://192.168.1.233:8080/edu-system/"
/* 
 * 封装ajax
 * obj : 全局this
 * data : ajax传入后台data数据
 * address : ajax接口地址
 * fn : 成功返回方法  带参数  obj,data  obj : this data : response
 * */
Vue.prototype.postHttp = function(obj,data,address,fn){
	var dates = new Date();
	dates = dates.getTime();
	obj.$axios.post(baseUrl+address+"?jy_pc_viewer&timer="+dates,querystring.stringify(data),{withCredentials : true}).then(response => {
  		if(response.data.code == "60000" || response.data.code == "50000" || response.data.code=="11111"){
  			obj.$router.push({ path: '/login' });
  		}else{
  			fn(obj,response.data);
  		}
    },response => {
    	obj.loading = false;
		obj.$notify({
	      title: '网络错误',
	      message: '网络错误',
	      offset: 100,
	      duration:1500,
	      type:'error'
	    });
	})
}

/* 
 * 封装提示
 * obj : 全局this
 * title : 标题
 * message : 提示内容
 * type : 提示类型  success warning error
 * */
Vue.prototype.notify_jr = function(obj,title,message,type){
	obj.$notify({
      title: title,
      message: message,
      offset: 100,
      duration:1500,
      type:type
    });
}

/* 
 * 封装未登录提示
 * */
Vue.prototype.notify_login = function(){
	this.$notify({
      title: '提示',
      message:'请先登录',
      offset: 100,
      duration:1500,
      type:'warning'
    });
}

Vue.prototype.getBaseUrl = function(){
	return baseUrl;
}


/* 
 * 视频时间转化 毫秒转为时分
 * msec : 毫秒值
 * */
Vue.prototype.formatMsec = function(msec){
	var regNum = /^[0-9]*$/;
	var s = '';
	if(msec && regNum.test(msec)){
		var second = parseInt(parseFloat(msec) / 1000);
		var minute = Math.ceil(second / 60);
		if(minute > 60){
			var hours = Math.floor(minute / 60);
			var minute_s = minute % 60;
			s = hours + '小时' + minute_s + '分钟'
		}else{
			s = minute + '分钟';
		}
	}
	return s;
}
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App}
})
