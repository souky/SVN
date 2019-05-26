<template>
	<div id="management">
		<div id="Srainbow" class="header-banner">
			<el-carousel height="100px" indicator-position="none" arrow="always" :autoplay="false">
				<el-carousel-item v-for="item in subjects" :key="item.id">
					<div class="header-banner-bit" v-for="(child,index) in item.childs">
						<div class="header-banner-click on" v-if="index == 0" :style="'background:'+child.color" @click="rainbow(index,child.name)">
							<p>{{child.name}}</p>
						</div>
						<div class="header-banner-click" v-if="index != 0" :style="'background:'+child.color" @click="rainbow(index,child.name)">
							<p>{{child.name}}</p>
						</div>
					</div>
				</el-carousel-item>
			</el-carousel>
		</div>
		<div id="knowledgeContent" class="mt20">
			<div class="header">
				<p>知识点</p>
				<div class="header-title-foot"></div>
			</div>
			<div class="body mt20">
				<el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	data(){
		return{
			data: [],
			defaultProps: {
				children: 'kpVOChildList',
				label: 'knowledgeContent',
				id:'id'
			},
			subjects:[],
			items:[],
			queryName:'',
		}
	},
	created:function(){
		this.baseData();
		
	},
	mounted:function(){
		
	},
	methods:{
		handleNodeClick(data) {
		},
		baseData(){
			this.postHttp(this,{},'subject/getSubjectByLogin',function(obj,res){
				obj.items=[];
				for(var i=0;i<res.result.length;i++){
					var arr={
						"id":res.result[i].id,
						"name":res.result[i].subjectName,
						"color":res.result[i].subjectColor,
					}
					obj.items.push(arr);
				}
				obj.queryName=res.result[0].subjectName;
				var childNum=Math.ceil(obj.items.length/11);
				var childs=[];
				for(var l=0;l<childNum;l++){
					var id=l+1;
					var e=11*(l+1);
					var s=e-11;
					childs[l] = []
					childs[l]["childs"]=obj.items.slice(s,e);
					childs[l]["id"] = id;
				}
				obj.subjects=childs;
				obj.$emit('refreshbizlines','other');
				obj.loadKonwP();
			});
		},
		loadKonwP(){
			this.postHttp(this,{subjectName:this.queryName},'knowledgepoint/queryKnowledgePointsBySubjectName',function(obj,res){
				obj.data = res.result;
			});
		},
		rainbow:function(index,num){
			for(var i=0;i<document.getElementsByClassName("header-banner-click").length;i++){
				document.getElementById("Srainbow").getElementsByClassName("header-banner-click")[i].className="header-banner-click";
			}
			document.getElementById("Srainbow").getElementsByClassName("is-active")[0].getElementsByClassName("header-banner-click")[index].className+=" on";

			var oNav = document.getElementById("Srainbow").getElementsByTagName("li");
			for(var a = 0;a<oNav.length;a++){
				oNav[a].className = '';
			}
			this.postHttp(this,{subjectName:num},'knowledgepoint/queryKnowledgePointsBySubjectName',function(obj,res){
				obj.data = res.result;
			});
		},
	},
}
</script>

<style>
#management{
	width: 1100px;
	margin: auto;
	overflow: hidden;
}
#Srainbow{
	display: table;
	margin: auto;
	margin-top: 100px;
	background-color: white
}
#Srainbow .el-carousel{
	width: 1100px;
	height: 100px;
}
.header-banner{
	margin-top:100px;
	width: 1100px;
	margin:auto;
}
.el-carousel__container {
	position: relative;
	height: 50px;
	margin: auto;
}
.header-banner-bit{
	width: 89px;
	height: 73px;
	float: left;
	margin-top: 15px;
}
.table-header{
	background: #70CDF3;
	color: #fff;
}
.header-banner .el-carousel__item{
	margin-left: 60px;
}
.header-banner-click{
	width: 100%;
	height: 100%;
	text-align: center;
}
.header-banner-click p{
	line-height: 73px;
}
.header-banner-bit .on{
	height: 105%;
}
#management .header{
	text-align: center;
}
#management .header p{
	font-size: 20px;
	color: #707070;
	letter-spacing: 0;
}
#management .header-title-foot{
	width: 100px;
	height: 2px;
	background: #44A9FF;
	margin: auto;
	margin-top:5px;
}
</style>