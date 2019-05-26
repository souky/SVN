<template>
	<div id="index" class="main_body">
	
	</div>
</template>

<script>
export default {
	  data () {
	    return {
	      msg: 'index',
	    }
	  },
	  mounted:function(){
			this.postHttp("getLoginUesr",{},res=>{
				if(res.code == "60000" || res.code == "50000" || res.code == "30000"){
					this.$router.push('login');	
				}
			});
	  },
	  methods:{
	  }
}
</script>

<style>

</style>