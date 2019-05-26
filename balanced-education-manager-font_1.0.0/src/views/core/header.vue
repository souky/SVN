<template>
  <nav class="z-header">
   	<div class="header_text tc">教育均衡云管理</div>
    <div class="header-right" @click="show_items()">
        <img src="../../assets/logo/uer_logo.png" />
    </div>
    <div class="header-right-items" v-show="showItems">
    	<div class="H_R_I tc poi">消息</div>
    	<div class="H_R_I tc poi" @click="my_acc()">个人中心</div>
    	<div class="H_R_I tc poi" @click="login_out()">退出登陆</div>
    </div>
  </nav>
</template>

<script>
  export default {
    data() {
      return {
      	showItems:false,
      }
    },
    methods: {
			show_items(){
				var flag = this.showItems;
				if(flag){
					this.showItems = false;
				}else{
					this.showItems = true;
				}
			},
			my_acc(){
				this.showItems = false;
				this.$router.push({ path: '/my-account' });
			},
			login_out(){
				this.showItems = false;
				var data = {};
				this.postHttp(this,data,"logout",null);
				this.$router.push({ path: '/login' });
			}
    }
  }
</script>
<style>
	.z-header{position: relative;overflow: inherit;}
	.header_text{width:100%;height:60px;line-height: 60px;font-size: 24px;color:#fff;}
	.header-right{position: absolute;right: 50px;top:0px;width: 40px;height: 40px;line-height:40px;background: #82d886;
	margin-top: 10px;border-radius: 50px;overflow: hidden;cursor: pointer;}
	.header-user img{width: 100%;}
	.header-right-items{position: absolute;top:60px;right: 20px;width:110px;background: #fff;box-shadow: 0px 3px 10px #6b6b6b;}
	.H_R_I{height: 40px;line-height: 40px;border-bottom: 1px #e2e2e2 solid;position: relative;color:#666;}
</style>






