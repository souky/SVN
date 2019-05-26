import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import Table from '@/components/Table'
import Input from '@/components/Input'
import Select from '@/components/Select'
import Textarea from '@/components/Textarea'
import Button from '@/components/Button'
import Page from '@/components/Page'
import Tree from '@/components/Tree'
import Menu from '@/components/Menu'
import Nav from '@/components/Nav'

import Alert from '@/components/Alert'
import Confirm from '@/components/Confirm'
import Notify from '@/components/Notify'
import Dialog from '@/components/Dialog'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Index,
    },
    {
      path: '/table',
      component: Table
    },
    {
      path: '/input',
      component: Input
    },
    {
      path: '/select',
      component: Select
    },
    {
      path: '/textarea',
      component: Textarea
    },
    {
      path: '/button',
      component:Button
    },{
      path:'/nav',
      component:Nav
    },
    {
    	path:'/page',
    	component:Page
    },
    {
    	path:'/alert',
    	component:Alert
    },
    {
    	path:'/confirm',
    	component:Confirm
    },
    {
    	path:'/notify',
    	component:Notify
    },
    {
    	path:'/tree',
    	component:Tree
    },
    {
    	path:'/dialog',
    	component:Dialog
    },
    {
      path:'/menu',
      component:Menu
    }
    
  ]
})
