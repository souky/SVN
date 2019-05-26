<template>
  <div id="app">
  	<b-header></b-header>
  	<b-menu></b-menu>
  	<transition name="slide-fade">
    		<router-view></router-view>
    </transition>
  </div>
</template>

<script>
	import BHeader from '@/components/BHeader'
	import BMenu from '@/components/Menu'
	import Vue from 'vue'
	export default {
	 	name: 'app',
	   	components: {BHeader,BMenu},
	    mounted:function(){
	      var height = document.documentElement.clientHeight;
	      document.getElementById("app").style.minHeight = height+"px";
	  	}
	}
</script>

<style>
#app {
  font-family: '\u5FAE\u8F6F\u96C5\u9ED1', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  overflow: hidden;
}
.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-leave-active {
  transition: all .1s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to{
  transform: translateX(0px);
  opacity: 0;
}
</style>
