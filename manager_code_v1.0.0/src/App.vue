<template>
  <div id="app">
  	<b-header ></b-header>
  	<b-menu></b-menu>
  	<transition>
    	<router-view></router-view>
    </transition>
  </div>
</template>

<script>
	import BHeader from '@/components/BHeader'
	import BMenu from '@/components/BMenu'
	import Vue from 'vue'
	export default {
	  name: 'app',
   	components: {BHeader,BMenu},
    mounted:function(){
      var height = document.documentElement.clientHeight;
      document.getElementById("app").style.minHeight = height+"px";
  	},
  	methods:{
  	}
	}
</script>

<style>
body{
	overflow: hidden;
}
#app {
  font-family: '\u5FAE\u8F6F\u96C5\u9ED1',Microsoft YaHei, Helvetica, Arial, sans-serif!important;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-leave-active {
  transition: all .1s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateX(0px);
  opacity: 0;
}
</style>
