<template>
  <div id="app">
  	<b-header ref="selectHeader"></b-header>
  	<transition name="slide-fade">
    	<router-view v-on:noticFn = 'dealwith'></router-view>
    </transition>
  </div>
</template>

<script>
	import BHeader from '@/components/BHeader'
	import Vue from 'vue'
	export default {
	  name: 'app',
   	components: {BHeader},
    mounted:function(){
      var height = document.documentElement.clientHeight;
      document.getElementById("app").style.minHeight = height+"px";
  	},
  	methods:{
  		dealwith(){
				this.$refs.selectHeader.changePoint();
  		}
  	}
	}
</script>

<style>
#app {
  font-family: '\u5FAE\u8F6F\u96C5\u9ED1', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  background-color: #F9F9F9
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
