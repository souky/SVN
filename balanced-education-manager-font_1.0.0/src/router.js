import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

//定义路由组件
const index = require('./views/index.vue');

const home = require('./views/home/home.vue');

const program = require('./views/task/program.vue');
const createProgram = require('./views/task/create-program.vue');
const recording = require('./views/task/recording.vue');
const play = require('./views/task/play.vue');

const courseList = require('./views/resources/course-list.vue');
const courseVideo = require('./views/resources/course-video.vue');
const textbookList = require('./views/resources/textbook-list.vue');

const accountList = require('./views/account/account-list.vue');
const myAccount = require('./views/account/my-account.vue');
const role = require('./views/account/role.vue');
const schoolFramework = require('./views/account/school-framework.vue');

const photos = require('./views/device/photos.vue');
const service = require('./views/device/service.vue');

const login = require("./views/login.vue");

export default new Router({
mode:'history', 
 routes: [
    { path: '/', component: index,
      children: [
        {
          path: '/home',
          component: home
        },
        {
          name: '节目单',
          path: '/program',
          component: program
        },
        {
          name: '创建节目',
          path: '/create-program',
          component: createProgram
        },
        {
          name: '录制',
          path: '/recording',
          component: recording
        },
        {
          name: '点播',
          path: '/play',
          component: play
        },
        {
          name: '课程列表',
          path: '/course-list',
          component: courseList
        },
        {
          name: '课堂视频／教辅文件',
          path: '/course-video',
          component: courseVideo
        },
        {
          name: '教材列表',
          path: '/textbook-list',
          component: textbookList
        },
        {
          name: '帐户列表',
          path: '/account-list',
          component: accountList
        }, {
          name: '角色管理',
          path: '/role',
          component: role
        }, {
          name: '学校架构',
          path: '/school-framework',
          component:schoolFramework
        }, {
          name: '我的账户',
          path: '/my-account',
          component: myAccount
        }, {
          name: '摄相机配置',
          path: '/photos',
          component: photos
        }, {
          name: '服务器配置',
          path: '/service',
          component: service
        }
      ]
    },{
    	path:'/login',
    	component:login
    }
  ]
})

