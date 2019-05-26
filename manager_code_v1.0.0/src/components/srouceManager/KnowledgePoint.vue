<template>
	<div id="knowledgePoint" class="main_body">
		<div class="knowledge_body">
			<div class="title">选择年级</div>
			<div class="select_subject fix">
				<div v-for="(e,index) in gradeArray" v-if="index == 0" class="itemsG l active" @click="changeCode($event)">{{e}}</div>
				<div v-for="(e,index) in gradeArray" v-if="index != 0" class="itemsG l" @click="changeCode($event)">{{e}}</div>
			</div>
			<div class="title">选择科目</div>
			<div class="select_subject fix">
				<div v-for="(e,index) in subjectArray" v-if="index == 0" class="items l active" @click="changeSubject($event)">{{e}}</div>
				<div v-for="(e,index) in subjectArray" v-if="index != 0" class="items l" @click="changeSubject($event)">{{e}}</div>
			</div>
			<div class="title">知识点</div>
			<div class="fix addBtnFa">
				<div class="addBtn" @click="addKnowPoint">新增知识点</div>
			</div>
			<div class="konwledge_detiles fix">
				<el-tree id="textBookT" :data="data" :render-content="renderContent" node-key="id" :props="defaultProps"  @node-click="handleNodeClick"></el-tree>
			</div>

		</div>

		<el-dialog :title="diaTitle" :visible.sync="dialogVisible" width="30%">
		  <div class="dialog_body">
		  	<el-form label-position="right" label-width="80px" :rules="rules" ref="konw" class="demo-ruleForm" :model="konw" >
			  <el-form-item label="名称" prop="knowledgeContent">
			    <el-input v-model="konw.knowledgeContent" :maxlength="20" :minlength="1"></el-input>
			  </el-form-item>
			</el-form>
		  </div>
		  <span slot="footer" class="dialog-footer">
		    <el-button type="primary" @click="colseDia">取 消</el-button>
		    <el-button type="primary" @click="saveEdit">确 定</el-button>
		  </span>
		</el-dialog>
	</div>

</template>

<script>
export default {
  data () {

    return {
      msg: 'knowledgePoint',
      subjectArray:[],
      gradeArray:[],
      defaultProps: {
	        children: 'kpVOChildList',
	        label: 'knowledgeContent',
	        id:'id'
	  },
	  data: [],

	  dialogVisible:false,
	  konw:{
	  	parentId:'',
	  	knowledgeContent:''
	  },
	  rules: {
          knowledgeContent: [
            { required: true, message: '请输入知识点名称', trigger: 'blur' }
          ],
     },
     queryName:'',
     queryCode:'',
     text_id:'',

     diaTitle:'新增'
    }
  },
  mounted:function(){
  	this.postHttp(this,{},"school/querySchools",function(obj,res){
  		obj.subjectArray = res.result.subjectArray;
  		obj.queryName = obj.subjectArray[0];
  		obj.postHttp(obj,{},"getLoingGrade",function(obj,res){
  			obj.gradeArray = res.result;
  			obj.queryCode = obj.gradeArray[0];
  			obj.loadKonwP();
  		});
  	})

  },
  methods:{
	changeSubject(event){
		var list = document.getElementsByClassName("items");
		for(var i = 0;i < list.length;i++){
			list[i].className = 'items l';
		}
		event.currentTarget.className = 'items l active';
		this.queryName = event.currentTarget.innerHTML;
		this.loadKonwP();
	},
	changeCode(event){
		var list = document.getElementsByClassName("itemsG");
		for(var i = 0;i < list.length;i++){
			list[i].className = 'l itemsG';
		}
		event.currentTarget.className = 'itemsG l active';
		this.queryCode = event.currentTarget.innerHTML;
		this.loadKonwP();
	},
	loadKonwP(){
		this.postHttp(this,{subjectName:this.queryName,gradeCode:this.queryCode},'knowledgepoint/queryKnowledgePointsBySubjectName',function(obj,res){
	  		obj.data = res.result;
	  	});
	},
	colseDia(){
  		this.dialogVisible = false;
  		this.konw = {};
  	},
  	addKnowPoint(){
  		this.dialogVisible = true;
  		this.diaTitle = "新增";
  		this.konw = {};
  		this.konw["parentId"] = "";
  		this.konw["subjectId"] = this.queryName;
  		this.konw["gradeCode"] = this.queryCode;
  		if(this.$refs['konw']){
  			this.$refs['konw'].resetFields();
  		}
  	},
  	saveEdit(){
  		var address = 'knowledgepoint/saveKnowledgePoint';
		this.$refs['konw'].validate((valid) => {
          if (valid) {
          	this.postHttp(this,this.konw,address,function(obj,res){
	  			if(res.code == '10000'){
	  				obj.dialogVisible = false;
	  				obj.notify_success();
	  				obj.loadKonwP();
	  			}else{
	  				obj.notify_jr(obj,'操作错误',res.message,'error');
	  			}
	  		})
          } else {
            return false;
          }
        });

  	},
	handleNodeClick(data){
		console.log(data)
	},
	delete_text_s(){
		var id = this.text_id;
		this.postHttp(this,{id:id},'knowledgepoint/deleteKnowledgePoint',function(obj,res){
	  		if(res.code == "10000"){
	  			obj.notify_success();
	  			obj.loadKonwP();
	  		}else{
	  			obj.notify_jr(obj,'操作错误',res.message,'error');
	  		}
	  	});
	},
	renderContent(createElement, { node, data, store }) {
	    var self = this;
	    return createElement('div',{attrs:{
	            style:"width:100%;"
	        }}, [
	        createElement('span', node.label),
	        createElement('span', {attrs:{
	            style:"float: right; margin-right: 20px",class:"tree_btn"
	        }},[
	            createElement('i',{attrs:{
	                class:'el-icon-plus'
	            },on:{
	                click:function() {
	                	self.konw.parentId = data.id;
	                	self.konw.subjectId = self.queryName;
	                	self.konw.gradeCode = self.queryCode;
	                	self.dialogVisible = true;
	                	self.$refs['konw'].resetFields();
	                	self.konw.knowledgeContent = "";
	                }
	            }},""),
	            createElement('i',{attrs:{
	                 class:'el-icon-minus'
	            },on:{
	                click:function() {
	                	self.text_id = data.id;
	                	self.$confirm('此操作将删除该知识点下所有子项,是否继续?', '提示', {
							          confirmButtonText: '确定',
							          cancelButtonText: '取消',
							          type: 'warning'
							        }).then(() => {
							        	self.delete_text_s();
							        }).catch(() => {

							        });
	                }
	            }},""),
	        ]),
	    ]);
	  },
  }
}
</script>

<style>
#knowledgePoint .knowledge_body{
	width: 90%;
	margin:20px auto;
}
#knowledgePoint .knowledge_body .select_subject{
	margin-bottom: 30px;
}
#knowledgePoint .knowledge_body  .title{
	width:20%;
	border-bottom:1px #a7a7a7 solid;
	height:40px;
	line-height: 40px;
}
#knowledgePoint .knowledge_body .select_subject .items{
	width:18%;
	height: 40px;
	line-height: 40px;
	color:#7b7b7b;
	border:1px solid #e4e4e4;
	border-radius: 3px;
	text-align: center;
	cursor: pointer;
	font-size:18px;
	margin-right:1%;
	margin-top:20px;
}
#knowledgePoint .knowledge_body .select_subject .items.active{
	border-color: #FFD100;
	background: #FFD100;
	color:#2b2b2b;
}
#knowledgePoint .knowledge_body .select_subject .itemsG{
	width:18%;
	height: 40px;
	line-height: 40px;
	color:#7b7b7b;
	border:1px solid #e4e4e4;
	border-radius: 3px;
	text-align: center;
	cursor: pointer;
	font-size:18px;
	margin-right:1%;
	margin-top:20px;
}
#knowledgePoint .knowledge_body .select_subject .itemsG.active{
	border-color: #FFD100;
	background: #FFD100;
	color:#2b2b2b;
}
#knowledgePoint .konwledge_detiles{
	padding-top: 20px;
}
#knowledgePoint .konwledge_detiles .detiles_left{
	width: 400px;
}
#knowledgePoint .konwledge_detiles .detiles_right{
	width: calc(100% - 420);
}
#knowledgePoint .addBtnFa{
	width:95%;
}
#knowledgePoint .addBtn{
	width:150px;
	float:right;
	text-align: center;
	height:30px;
	line-height: 30px;
	color:#2b2b2b;
	background: #FFD100;
	border-radius: 4px;
	cursor: pointer;
}

#knowledgePoint #textBookT{width:95%;}
#knowledgePoint #textBookT i{font-size:14px;padding:3px;margin-right:20px;border-radius:2px;}
#knowledgePoint .el-icon-plus{color:#EF5350;border:1px #EF5350 solid;}
#knowledgePoint .el-icon-minus{color:#FFCA28;border:1px #FFCA28 solid;}
#knowledgePoint .el-tree-node__expand-icon{border-left-color:#202a33;}
</style>
