<template>
  <div id="app">
			<div class="jy_header tc">金瑞阳内部标签库API</div>
			<div class="jy_main fix">
				<div class="leftPart l">
					<j-menu></j-menu>
				</div>
				<div class="rightPart l">
					<router-view></router-view>
				</div>
			</div>
  </div>
</template>

<script>
import JMenu from '@/components/JMenu'
export default {
  name: 'app',
  mounted:function(){
	  var height = document.documentElement.clientHeight;
	  document.getElementById("app").style.minHeight = height+"px";
	},
	components: {JMenu},
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
#app .jy_header{
	font-size:30px;
	color:#6bbada;	
}
#app .jy_main{
	width:1600px;
	margin:40px auto 0 auto;
	padding-bottom: 40px;
}
#app .jy_main .leftPart{
	width:360px;
	padding:10px 10px;
	background: #fff;
	min-height: 200px;
	margin-right:20px;
	box-shadow: 1px 1px 10px #e8e8e8;
}
#app .jy_main .rightPart{
	width:1180px;
	padding:10px 10px;
	min-height: 200px;
	background: #fff;
}
.code_block{
	width:90%;
	margin:auto;
	color:#7b7b7b;
	font-family: Menlo,Monaco,Consolas,Courier,monospace;
}
pre{
	font-family: Menlo,Monaco,Consolas,Courier,monospace;
}
.code_block font{
	color:#f40;
}
.common_title{
	width:97%;
	padding-left:3%;
	height:60px;
	line-height: 60px;
	font-size:18px;
	color: #777;
}
</style>
