import Vue from 'vue'
import Router from 'vue-router'
import axios from 'axios'

// 组件
import Index from '@/components/Index'
import Login from '@/components/Login'

//屏蔽终端
import ShieldList from '@/components/shield/ShieldList'
import ShieldLayout from '@/components/shield/ShieldLayout'

//考试计划
import ExamType from '@/components/exam/ExamType'
import ExamPlan from '@/components/exam/ExamPlan'

//用户信息
import ChangePassword from '@/components/userInfo/ChangePassword'

//系统信息
import ShowLog from '@/components/system/ShowLog'


Vue.use(Router)

var querystring = require('querystring');

var router = new Router({
	mode:'history',
  routes: [
     {
      path: '/',
      name: '首页',
      component: Index
    },
    {
      path: '/login',
      name: '登录',
      component: Login
    },
		{
			path: '/examType',
			name: '考试类型',
			component: ExamType
		},
		{
			path: '/examPlan',
			name: '考试计划',
			component: ExamPlan
		},
    {
    	path: '/shieldList',
      name: '屏蔽终端列表',
      component: ShieldList
    },
    {
    	path: '/shieldLayout',
      name: '屏蔽终端布局',
      component: ShieldLayout
    },
    {
    	path: '/changePsw',
      name: '修改密码',
      component: ChangePassword
    },
    {
    	path: '/showLog',
      name: '查看日志',
      component: ShowLog
    }
    
  ]
})

router.beforeEach((to, from, next) => {
	if (to.matched.some(res => res.meta.requireAuth)) { // 判断是否需要登录权限
		axios.defaults.withCredentials = true
		axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
		var data = {};
		axios.post(Vue.prototype.getBaseUrl()+"getLoginUesr", querystring.stringify(data), {
			withCredentials: true
		}).then(response => {
			if (response.data.code == "60000" || response.data.code == "50000" || response.data.code == "30000") {
				next({
					path: '/login'
				})
			} else {
				next();
			}
		})
	} else {
		next();
	}
})
export default router;