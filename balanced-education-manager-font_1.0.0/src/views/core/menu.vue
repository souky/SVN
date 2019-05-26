<template>
      <el-col :span="4"  style="min-height: 100%; background-color: #333B45;">
        <el-menu :default-active="defaultActive" unique-opened style="min-height: 100%;" theme="dark" router>
          <el-menu-item index="home"><i class="fa fa-home fa-lg"></i>首页</el-menu-item>
          <el-submenu v-for="(mobj,index) in Menu" :index="''+index">
            <template slot="title"><i :class="mobj.icon"></i>{{mobj.name}}</template>
            <el-menu-item v-for="smobj in mobj.subMenu" v-text="smobj.name" :index="smobj.link"></el-menu-item>
          </el-submenu>
        </el-menu>
      </el-col>
</template>


<script>
  import Menu from '../../data/menu';

  export default {
    data () {
      return{
        Menu,
        menuData: [],
        // 默认加载的数据
        index: 0
      }
    },
    computed: {
      /**
       * @description 计算左侧menu数据
       * @returns {*}
       */
      getMenuData () {
        return this.Menu[this.index].data;
      },
      //
      defaultActive: function(){
        return this.$route.path.replace('/', '');
      }
    }



  }
</script>

<style>
  
</style>
