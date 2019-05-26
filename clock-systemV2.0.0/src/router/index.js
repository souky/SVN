import Vue from 'vue'
import Router from 'vue-router'
import axios from 'axios'
// 组件
import Index from '@/components/Index'
import Login from '@/components/Login'
//网络时钟
import ClockList from '@/components/clock/ClockList'
import ClockLayout from '@/components/clock/ClockLayout'
//卫星
import SateList from '@/components/satellite/SateList'

//用户信息
import ChangePassword from '@/components/userInfo/ChangePassword'

//系统信息
import ShowLog from '@/components/system/ShowLog'
//系统设置
import SystemSetting from '@/components/system/SystemSetting'


Vue.use(Router)

var querystring = require('querystring');

var router = new Router({
	mode: 'history',
	routes: [{
			path: '/',
			name: '首页',
			component: Index,
			meta: {
				requireAuth: true
			}
		},
		{
			path: '/login',
			name: '登录',
			component: Login
		},
		{
			path: '/clockList',
			name: '时钟列表',
			component: ClockList,
			meta: {
				requireAuth: true
			}
		},
		{
			path: '/clockLayout',
			name: '时钟布局',
			component: ClockLayout,
			meta: {
				requireAuth: true
			}
		},
		{
			path: '/sateList',
			name: '卫星列表',
			component: SateList,
			meta: {
				requireAuth: true
			}
		},
		{
			path: '/changePsw',
			name: '修改密码',
			component: ChangePassword,
			meta: {
				requireAuth: true
			}
		},
		{
			path: '/showLog',
			name: '查看日志',
			component: ShowLog,
			meta: {
				requireAuth: true
			}
		},
		{
			path: '/systemSetting',
			name: '系统参数',
			component: SystemSetting,
			meta: {
				requireAuth: true
			}
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
