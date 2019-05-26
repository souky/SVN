<template>
<div id="Forget" class="main_body tc" >
  <div class="main" id="show_body">
      <div class="top">
        找回密码
      </div>
      <div class="midddle">
        <div class="img">
            <img src="../../static/img/passwordLogo.png" class="image">
        </div>
        <div class="tipsInformation">
            <p>找回密码</p>
            <p class="smallP">请通知您的班主任帮助您重置密码，然后班主任会把新密码告知您！</p>
        </div>
      </div>
      <div class="bottom">
              <el-button type="primary" @click="backIndex()">确定</el-button>
        </div>
    </div>

</div>
</template>

<script>
export default {

  data () {
    return {
      
    }
  },
  
  mounted:function(){
      var height = document.documentElement.clientHeight;
      var heightOfset = height-60;
      var heightPadding = (heightOfset-600)/2
      console.log(heightOfset);
      document.getElementById("Forget").style.height = heightOfset+"px";
      document.getElementById("show_body").style.marginTop = heightPadding+"px";
  },
  methods:{
    login:function(){},
    backIndex:function(){
      this.$router.push({path:'/'})
    }
    
  },
}
</script>

<style>
  html {width:100%;height:100%}
  #Forget {width:100%;background:url('../../static/img/register/registerBackground.png') no-repeat;background-size:100%;}
  #Forget .main {background-color: white;width: 1200px;height: 600px;margin: auto;}
  #Forget .main .top{text-align: left;font-size: 24px;color: #272727;padding: 38px 49px;}
  #Forget .main .midddle{width: 890px;height: 145px;margin: auto;background-color: #FDF7DC;border: 1px solid #FDDD74;margin-top: 90px}
  #Forget .main .midddle .img,#Forget .main .midddle .tipsInformation{display: inline-block;float: left;}
  #Forget .main .midddle .img{padding: 11px 20px}
  #Forget .main .midddle .tipsInformation{margin-left: 29px;}
  .tipsInformation p{font-size: 20px;color: #272727;text-align: left;margin-top: 48px;}
  .tipsInformation .smallP{font-size: 16px;margin-top: 10px;}
  #Forget .el-button{width:360px;height:60px;margin:auto;border-radius:36px;background-color:#6ED56C;border-color: #6ED56C;font-size:20px;margin-top: 147px;}
</style>