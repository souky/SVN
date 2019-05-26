<template>
  <div id="app">
		 <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'app',
  mounted:function(){
	  var height = document.documentElement.clientHeight -40;
	  document.getElementById("app").style.minHeight = height+"px";
	},

}
</script>

<style>
#app {
  font-family: '\u5FAE\u8F6F\u96C5\u9ED1', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  padding-top: 40px;
  background: #f3f3f3;
}

</style>
