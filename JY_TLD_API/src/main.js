import Vue from 'vue'
import App from './App'
import router from './router'

import VueRouter from 'vue-router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import '../static/css/apps.css'

Vue.config.productionTip = false

/* 附加插件 */
Vue.use(VueRouter)
Vue.use(ElementUI)

Vue.prototype.initCodeBlock = function(){
	var list = document.getElementsByClassName('code_block');
	for(var i = 0;i<list.length;i++){
		var obj = list[i];
		var htmls = obj.innerHTML;
		if(htmls.indexOf("#") > -1){
			var tagName = htmls.split('#')[1].split(' ')[0];
			htmls = htmls.replace(/#/g,'<span><<span>');
			htmls = htmls.replace(/%/g,'<span>><span>');
			var reg_tagName = new RegExp(tagName , 'g');
			htmls = htmls.replace(reg_tagName,'<font>'+tagName+'</font>');
		}else{
			var tagName = htmls.split("(")[0];
			var reg_tagName = new RegExp(tagName , 'g');
			htmls = htmls.replace(reg_tagName,'<font>'+tagName+'</font>');
			
			var tagName_s = htmls.split("(")[1].split(")")[0];
			var reg_tagName_s = new RegExp(tagName_s , 'g');
			htmls = htmls.replace(reg_tagName_s,'<font style="color:#ffc107">'+tagName_s+'</font>');
		}
		obj.innerHTML = htmls;
	}
}

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})


