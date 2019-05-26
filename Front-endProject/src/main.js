import Vue from 'vue'
import App from './App'
import router from './router'

import VueRouter from 'vue-router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '../static/css/apps.css'
import axios from 'axios'


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


var querystring = require('querystring');

var baseUrl = "http://192.168.1.89:8080/happyTree/"

/*
 * 封装ajax
 * obj : 全局this
 * data : ajax传入后台data数据
 * address : ajax接口地址
 * fn : 成功返回方法  带参数  obj,data  obj : this data : response
 * */
Vue.prototype.postHttp = function(address,data,fn){
	this.$axios.post(baseUrl+address,querystring.stringify(data),{withCredentials : true}).then(response => {
  		if(response.data.code == "60000" || response.data.code == "50000" || response.data.code=="11111"){
  			//this.$router.push({ path: '/login' });
        fn(response.data)
  		}else{
  			fn(response.data);
  		}
    },response => {
    	//this.loading = false;
  		this.$notify({
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
Vue.prototype.notify_jr = function(title,message,type){
	this.$notify({
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

/*
 * 获取基础地址
 * */
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
  components: { App }
})
