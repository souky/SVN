<template>
	<div id="menu">
		<el-menu class="el-menu-vertical-demo" background-color="#162743" text-color="#fff" active-text-color="#00bcd4" unique-opened>
			<el-submenu v-for="(e,index) in menuData" :key="e.id"  :index="e.id">
				<template slot="title">
					<img :src=" 'static/img/menu/' + e.menuImg + '.png' " style="width:20px;height:20px"/>
					<span>{{e.menuNameZh}}</span>
				</template>
				<el-menu-item-group v-for="(ee,index) in e.listNextLevel" :key="ee.id">
					<router-link v-if="ee.menuUrl != '' && ee.menuUrl != null" :to="ee.menuUrl">
						<el-menu-item :index="ee.id"><i class="el-icon-arrow-right menuIcon"></i>{{ee.menuNameZh}}</el-menu-item>
					</router-link>
				</el-menu-item-group>
			</el-submenu>
		</el-menu>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				msg: 'menu',
				menuData:[
					{
						id:'0',
						menuNameZh:'考试计划',
						menuUrl:'',
						menuImg:'exam',
						listNextLevel:[
							{
								menuNameZh:'考试类型',
								id:'1',
								menuUrl:'/examType',
								menuImg:''
							},
							{
								menuNameZh:'考试计划',
								id:'2',
								menuUrl:'/examPlan',
								menuImg:''
							}
						]
					},
					{
						id:'1',
						menuNameZh:'屏蔽终端',
						menuUrl:'',
						menuImg:'shield',
						listNextLevel:[
							{
								menuNameZh:'屏蔽终端列表',
								id:'1',
								menuUrl:'/shieldList',
								menuImg:''
							},
							{
								menuNameZh:'屏蔽终端分布',
								id:'2',
								menuUrl:'/shieldLayout',
								menuImg:''
							}
						]
					},
					{
						menuNameZh:'个人设置',
						id:'3',
						menuUrl:'',
						menuImg:'info',
						listNextLevel:[
							{
								menuNameZh:'密码管理',
								id:'4',
								menuUrl:'/changePsw',
								menuImg:''
							}
						]
					},
					{
						menuNameZh:'系统设置',
						menuUrl:'',
						menuImg:'system',
						id:'5',
						listNextLevel:[
							{
								menuNameZh:'查看日志',
								id:'6',
								menuUrl:'/showLog',
								menuImg:''
							}
						]
					}
				]
			}
		},
		mounted: function() {
			this.postHttp("shieldmenu/queryShieldMenus",{},res=>{
				this.menuData = res.result;
			})
		},
		methods: {}
	}
</script>

<style>

</style>