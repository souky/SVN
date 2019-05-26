import Vue from 'vue'
import Router from 'vue-router'


import Home from '@/components/Home'
import Login from '@/components/Login'

import ExamList from '@/components/examManager/ExamList'
import TwoWayTable from '@/components/examManager/TwoWayTable'

import SchoolInfo from '@/components/schoolManager/SchoolInfo'
import ClassInfo from '@/components/schoolManager/ClassInfo'
import SubjectInfo from '@/components/schoolManager/SubjectInfo'
import TeacherInfo from '@/components/schoolManager/TeacherInfo'
import StudentInfo from '@/components/schoolManager/StudentInfo'

import OrgManager from '@/components/systemManager/OrgManager'
import RoleManager from '@/components/systemManager/RoleManager'
import UserManager from '@/components/systemManager/UserManager'

import KnowledgePoint from '@/components/srouceManager/KnowledgePoint'

Vue.use(Router)

export default new Router({
  mode:'history',
  routes: [
    {
    	path: '/',
    	redirect: {
            name: 'login'
        }
    },
    {
    	path: '/home',
        name: 'home',
        component: Home
    },
    {
    	path: '/login',
        name: 'login',
        component: Login
    },
    {
	    path: '/examList',
	    name: 'examList',
	    component: ExamList
	},
	{
	    path: '/twoWayTable',
	    name: 'twoWayTable',
	    component: TwoWayTable
	},
	{
	    path: '/schoolInfo',
	    name: 'schoolInfo',
	    component: SchoolInfo
	},
	{
	    path: '/classInfo',
	    name: 'classInfo',
	    component: ClassInfo
	},
	{
	    path: '/subjectInfo',
	    name: 'subjectInfo',
	    component: SubjectInfo
	},
	{
	    path: '/teacherInfo',
	    name: 'teacherInfo',
	    component: TeacherInfo
	},
	{
	    path: '/studentInfo',
	    name: 'studentInfo',
	    component:StudentInfo
	},
	{
	    path: '/roleManager',
	    name: 'roleManager',
	    component: RoleManager
	},
	{
	    path: '/userManager',
	    name: 'userManager',
	    component: UserManager
	},
	{
	    path: '/orgManager',
	    name: 'orgManager',
	    component: OrgManager
	},
	{
	    path: '/knowledgePoint',
	    name: 'knowledgePoint',
	    component: KnowledgePoint
	},
  ]
})
