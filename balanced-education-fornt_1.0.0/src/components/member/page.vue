<template>
	<div id="paging">
		<el-pagination v-bind:current-Page="pageIndex" v-bind:page-size="pageSize" :total="totalNumber"
	   	layout="total,sizes,prev,pager,next,jumper" v-bind:page-sizes="pageSizes" :current-page="pageIndex"
	    v-on:size-change="sizeChange" v-on:current-change="pageIndexChange"></el-pagination>
    </div>
</template>
<script>
export default {
     data() {
        return {
          // pageIndex:1,
          // pageSize:1,
          //total:40,
          pageSizes:[1,10,20,50,100],
        }
      },
      props:['totalNumber','pageIndex','pageSize'],
      created:function(){
      },
      methods: {
      sizeChange: function (pageSize,pageIndex) {   //每页显示条数
	      this.pageSize = pageSize;
	      this.$emit('newNOdeEvents',this.pageSize,this.pageIndex);
	    },
	   pageIndexChange: function (pageIndex,pageSize) {   //第几页
	      this.pageIndex = pageIndex;
	      this.$emit('newNOdeEventss',this.pageIndex,this.pageSize);
	    },
    },
}
</script>