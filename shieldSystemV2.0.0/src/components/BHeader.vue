<template>
	<div id="header" class="fix box_s">
		<div class="logo">
			<img src="static/img/header/logo_white.png"/>
		</div>
		作弊防控系统
		<div class="login_out" @click="loginOut">
			<img src="static/img/header/login_Out.png" /> 退出
		</div>
	</div>
	
</template>

<script>
export default {
  data () {
    return {
      msg: '顶部导航栏',
    }
  },
  mounted:function(){
  },
  methods:{
		loginOut(){
			this.postHttp("loginOut",{},res=>{
				if(res.code == '10000'){
					this.$router.push('/login');
				}
			});
		}
  }
}
</script>

<style>

</style>