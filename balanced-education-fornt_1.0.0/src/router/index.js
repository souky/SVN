import Vue from 'vue'
import Router from 'vue-router'
import axios from 'axios'
// 组件
import Index from '@/components/Index'
import Login from '@/components/Login'
import Register from '@/components/Register'
import Forget from '@/components/Forget'
import allClassMore from '@/components/class/AllClassMore.vue'

import MemberCenter from '@/components/member/MemberCenter'
import Notic from '@/components/member/notic'//个人消息中心



import LiveRoom from '@/components/live/LiveRoom'
import AllClass from '@/components/class/AllClass'
import AllFile from '@/components/file/AllFile'
import Living from '@/components/live/living.vue'
import Playing from '@/components/live/playing.vue'

Vue.use(Router)

var querystring = require('querystring');

var router = new Router({
	mode:'history',
  routes: [
    {
      path: '/index',
      name: '首页',
      component: Index
    },
     {
      path: '/',
      name: '首页',
      component: Index
    },
    {
      path: '/Forget',
      name: '忘记密码',
      component: Forget
    },
    {
      path: '/login',
      name: '登录',
      component: Login
    },
    {
      path: '/register',
      name: '注册',
      component: Register
    },
    {
      path: '/memberCenter/:part',
      component: MemberCenter,
      meta:{requireAuth:true}
    },
    {
      path: '/liveRoom',
      name: '直播间',
      component: LiveRoom
    },
    {
      path: '/notic',
      name: '信息中心',
      component: Notic
    },
    {
      path: '/allClass',
      name: '所有课程',
      component: AllClass
    },
    {
      path: '/allClassMore/:part',
      component: allClassMore,
    },
    {
      path: '/allClassMore',
      name:'课程详情',
      component: allClassMore
    },
    {
      path: '/living',
      component: Living
    },
    {
      path: '/living/:part',
      component: Playing,
    },
    {
      path: '/playing',
      component: Playing
    },
    {
      path: '/playing/:part',
      component: Living,
    },
    {
      path: '/allFile',
      name: '所有教辅',
      component: AllFile
    }
  ]
})

router.beforeEach((to, from, next) => {
    if (to.matched.some(res => res.meta.requireAuth)) {// 判断是否需要登录权限
	 	axios.defaults.withCredentials = true
		axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
		var data = {};
	 	axios.post("http://192.168.1.213:8080/balanced-education/user/getLoginUesr",querystring.stringify(data),{withCredentials : true}).then(response => {
	  		if(response.data.code == "60000" || response.data.code == "50000"){
	  			next({
	  				path:'/login'
	  			})
	  		}else{
	  			next();
	  		}
	    })
    }else{
    	next();
    }
 })
export default router;