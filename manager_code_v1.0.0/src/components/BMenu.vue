<template>
	<div id="menu">
		<el-menu :default-active="defaultActive()" background-color="#282828" text-color="#ffffff" active-text-color="#FFD100"  router unique-opened @open="handleOpen" class="el-menu-vertical-demo">
			<el-menu-item v-for="(e,index) in menuData" :key="e.id" v-if="e.path != null && e.path != ''"  :index="e.path">
				<i :class="e.icon"></i>{{e.menuName}}
			</el-menu-item>
			<el-submenu v-for="(e,index) in menuData" :key="e.id" v-if="e.path == '' || e.path == null" :index="''+index">
				<template slot="title">
	        		<i :class="e.icon"></i>{{e.menuName}}
	        	</template>
	        	<el-menu-item v-for="ee in e.children" :index="ee.path" :key="ee.id">
	        		<i :class="ee.icon"></i>{{ee.menuName}}
	        	</el-menu-item>
			</el-submenu>
    	</el-menu>
	</div>
</template>

<script>
export default {
  data () {

    return {
      msg: '顶部导航栏',
      menuData:[],
    }
  },
  mounted:function(){
  	this.postHttp(this,{},"getMenu",function(o,res){
  		o.menuData = res.result;
  	})
  },
  watch: {
    '$route': 'fetchData'
  },
  methods:{
  	defaultActive(){
	    return this.$route.path.replace('/', '');
	},
	handleOpen(index,indexPath){
	},
	fetchData(){
		var path = this.$route.path.replace('/', '');
	    if(path == "home"){
	    	this.menuData = [];
	    	this.postHttp(this,{},"getMenu",function(o,res){
		  		o.menuData = res.result;
		  	})
	    }
	}
  }
}
</script>

<style>
#menu{
	position: fixed;
	left:0px;
	top:80px;
	width:350px;
	height: 100%;
	background:#282828;
}
</style>