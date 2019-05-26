<template>
  <div id="textBook" class="textbook-list-container">
    <el-row type="flex" class="row-bg pt20 pl20" justify="start">
       	<el-col :span="4" class="pct25 mr20 ">
            <label class="mr20">学校  </label>
            <el-select v-model="optionSchool_val" class="pct70" placeholder="请选择学校" @change="school_chage_q()" clearable>
	            <el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
	        	</el-select>
        </el-col>
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20">年级</label>
            <el-select v-model="optionGrand_val" class="pct70" placeholder="请选择年级" clearable>
                <el-option v-for="item in optionGrand" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
        </el-col>
        <el-col :span="4" class="pct25">
            <label class="mr20">学科</label>
            <el-select v-model="optionSubject_val" class="pct70" placeholder="请选择学科" clearable>
                <el-option v-for="item in optionSubject" :key="item.dicCode" :label="item.dicName" :value="item.dicCode"></el-option>
            </el-select>
        </el-col>
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20">出版社</label>
            <el-input class="pct70" v-model="name_q" placeholder="请输入出版社"></el-input>
        </el-col>
    </el-row>

    <el-row type="flex" class="row-bg mt20 pl20" justify="start">
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20"></label>
        </el-col>
        <el-col :span="4" class="pct25 mr20">
            <label class="mr20"></label>
        </el-col>
        <el-col :span="4" class="pct25 ">
        		<label class="mr20"></label>
            <el-button type="primary" class="pct70 l" @click="addTextbook()">新增教材</el-button>
        </el-col>
        <el-col :span="4" class="pct25 mr20">
        	<label class="mr20"></label>
        	<el-button type="success" class="pct70 l" @click="query_program()">查询</el-button>
        </el-col>
    </el-row>

    <div class="main-container">
    	<el-table :data="tableData" stripe row-key="id" style="width:100%;margin-top: 20px;">
    		<el-table-column prop="subject" label="学科" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="grade" label="年级" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="press" label="出版社" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column prop="publicationDate" :formatter="timeFormat" label="出版时间" align="center" show-overflow-tooltip min-width="160px"> </el-table-column>
    		<el-table-column prop="name" label="课本名" align="center" show-overflow-tooltip> </el-table-column>
    		<el-table-column label="操作" align="center" show-overflow-tooltip min-width="150px">
    			<template scope="scope">
		    			<i title="查看详细" class="el-icon-information" @click="dialogInfos(scope.row.id)"></i>
		    			<i title="编辑" class="el-icon-edit" @click="dialogEdits(scope.row.id)"></i>
		    			<i title="删除" class="el-icon-delete" @click="delete_book(scope.row.id)"></i>
		    	</template>
    		</el-table-column>
    	</el-table>
    	
    	<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageNum"
		      :page-sizes="[10, 20, 50]"
		      :page-size="pageSize"
		      layout="sizes, prev, pager, next, jumper"
		      :total="totals" class="tc mt20"
		      >
	    </el-pagination>
    </div>
    
    <el-dialog :visible.sync="dialogaddbook" :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false" style="top:-14%">
  		<div class="infoTitle" :model="infoTitles">{{infoTitles}}</div>
  		<div class="addBody fix" >
  			<el-form  label-width="80px" :model="textbook">
  				<div class="leftPart l">
  					<el-form-item label="课本名">
					    <el-input v-model="textbook.name"></el-input>
					  </el-form-item>
					  <el-form-item label="出版社">
					    <el-input v-model="textbook.press"></el-input>
					  </el-form-item>
					  <el-form-item label="出版时间">
					    <el-date-picker v-model="textbook.publicationDate" type="datetime" placeholder="选择开课日期"></el-date-picker>
					  </el-form-item>
					  <el-form-item label="学校">
				   		<el-select v-model="optionSchool_val"  placeholder="请选择学校" style="width:100%;" @change="school_chage_q()">
	            	<el-option v-for="item in optionSchool" :key="item.id" :label="item.name" :value="item.id"></el-option>
	        		</el-select>
					  </el-form-item>
					  <el-form-item label="年级">
					    <el-select v-model="textbook.grade" placeholder="请选择年级" style="width:100%;">
                <el-option v-for="item in optionGrand" :key="item.id" :label="item.name" :value="item.name"></el-option>
            	</el-select>
					  </el-form-item>
					  <el-form-item label="学科">
					  	<el-select v-model="textbook.subject"  placeholder="请选择学科" style="width:100%;">
					   		 <el-option v-for="item in optionSubject" :key="item.dicCode" :label="item.dicName" :value="item.dicName"></el-option>
					   	</el-select>
					  </el-form-item>
					  
  				</div>
  				<div class="rightPart l tc">
  					<div style="margin-bottom: 10px;">课程图片</div>
  					<el-upload class="" 
  					:action="Url" 
  					:show-file-list="false"
  					:with-credentials="true"
  					:on-success="handleAvatarSuccess"
  					:before-upload="beforeAvatarUpload">
  						<img v-if="textbook.coverImg" :src="imageUrl" class="avatar">
  						<i v-else class="el-icon-plus avatar-uploader-icon" style="color:#bfcbd9;border-color:#bfcbd9"></i>
						</el-upload>
  				</div>
  				<div class="l el-form-item" style="width: 100%;" v-if="bookSyllabuss!=null">
  					<label class="el-form-item__label" style="width: 80px;">教材大纲</label>
  					<div class="el-form-item__content" style="margin-left: 80px;">
  						<el-tree id="textBookT"  ref="textbookTree" default-expand-all :data="bookSyllabuss" :props="defaultProps" :render-content="renderContent" node-key="id" :indent="indents">
							</el-tree>
  					</div>
  				</div>
  				<div class="l fix" v-else style="width:100%">
  					<el-form-item label="大纲名称" style="width: 40%;float:left">
	  					 <el-input v-model="levelName"></el-input>
					  </el-form-item>
					  <div class="l ">
							<el-button type="success" style="width:160px;margin-left: 30px;" class="pct70 l" @click="creatNew()">新建教材大纲</el-button>
					  </div>
  				</div>
  				
  				
				</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div v-if='isEdit' class="saveInfo tc poi auto" @click="savetextBook()">保存</div>
					<div v-else class="saveInfo tc poi auto" @click="updatetextBook()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="addTextbookClose()">取消</div>								
				</div>
			</div>
		</el-dialog>
		
		<el-dialog title="详情" :visible.sync="dialoginfobook" :close-on-press-escape="false" :close-on-click-modal="false" :show-close="true" :before-close="handleClose">
  		<!-- <div class="infoTitle">详情</div> -->
  		<div class="infoBody fix" v-model="textbook">
  			<div class="leftPart l">
  				<div class="fix">
	    			<div class="infoItems fix l">
	    				<div class="l part tr">课本名:</div>
	    				<div class="l part tl ell" :title="textbook.name">{{textbook.name}}</div>
	    			</div>
	    			<div class="infoItems fix l">
	    				<div class="l part tr">学科:</div>
	    				<div class="l part tl">{{textbook.subject}}</div>
	    			</div>
	    			<div class="infoItems fix l">
	    				<div class="l part tr">年级:</div>
	    				<div class="l part tl">{{textbook.grade}}</div>
	    			</div>
	    			<div class="infoItems fix l">
	    				<div class="l part tr">出版社:</div>
	    				<div class="l part tl">{{textbook.press}}</div>
	    			</div>
	    			<div class="infoItems fix l">
	    				<div class="l part tr">出版时间:</div>
	    				<div class="l part tl">{{textbook.publicationDate}}</div>
	    			</div>
	    		</div>
  			</div>
  			<div class="rightPart l fix">
  				
  				<div class="r rightImg">
  					<img :src="baseUrl+textbook.coverImg">
  				</div>
  				<div class="r ml30">课程图片</div>
				</div>
				<div class="l el-form-item" style="width: 100%;margin-top:40px;">
					<label class="el-form-item__label" style="width: 80px;">教材大纲</label>
					<div class="el-form-item__content" style="margin-left: 80px;">
						<el-tree id="textBookT"  ref="textbookTree" default-expand-all :data="bookSyllabuss" :props="defaultProps" node-key="id" :indent="indents">
							</el-tree>
					</div>
				</div>
			</div>
		</el-dialog>
		
		
		<el-dialog :visible.sync="text_add" size="tiny" :show-close="true">
  		<div class="infoTitle">添加大纲</div>
  		<div class="infoBody fix" >
  			<el-form  label-width="80px" :model="text_infos" :rules="rules" ref="ruleForms"  class="demo-form-inline">
  					<el-form-item label="大纲名称" prop="text_name">
					    <el-input v-model="text_infos.text_name"></el-input>
					  </el-form-item>
				</el-form>
			</div>
			<div class="editBottom fix">
				<div class="l">
					<div class="saveInfo tc poi auto" @click="savetext()">保存</div>
				</div>
				<div class="l">
					<div class="cancleInfo tc poi auto" @click="addTextClose()">取消</div>								
				</div>
			</div>
		</el-dialog>
		
    
  </div>
</template>

<script>
  export default{
    data () {
    	var validateText = (rule, value, callback) => {
        if (this.text_infos.text_name == '') {
          callback(new Error('请填入大纲名称'));
        }else{
        	callback();
        }
      };
      return {
        msg:'hello vue',
        optionSchool:null,
        optionGrand:null,
        optionSubject:null,
        optionSchool_val:'',
        optionGrand_val:'',
        optionSubject_val:'',
        name_q:'',
        isEdit:true,
        
        tableData:[],
        pageNum:1,
				pageSize:10,
				totals:5,
        
        dialogaddbook:false,
        dialoginfobook:false,
        infoTitles:'',
        textbook:{
        	id:null,
        	grade:'',
        	subject:'', 
        	publicationDate:'',
        	coverImg:'',
        },
        imageUrl:'',
        Url:'',
        baseUrl:'',
        
        bookSyllabuss:null,
     		indents:20,
     		levelName:'',
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        materialId:null,
        
        text_id:'',
        text_name:'',
        text_infos:{
        	 text_id:'',
    			 text_name:'',
        },
        text_add:false,
        rules:{
	        text_name:[
	        	{required: true, validator:validateText, trigger: 'blur' }
	        ]
        },
      }
    },
    methods:{
			addTextbook(){
				this.infoTitles = "新增教材";
				this.dialogaddbook = true;
				this.isEdit = true;
			},
			query_program(){
				 this.postHttp(this,{orgId:this.optionSchool_val,grade:this.optionGrand_val,subject:this.optionSubject_val,press:this.name_q,pageNum:this.pageNum,pageSize:this.pageSize},"material/queryMaterials",function(obj,data){
			        obj.tableData=data.result.list;
			    });
			},
			savetextBook(){
				var data = this.textbook;
				data['publicationDate'] = this.timeF(this.publicationDate).format("YYYY-MM-DD HH:mm:ss");
				data['createDate'] = this.timeF(this.createDate).format("YYYY-MM-DD HH:mm:ss");
				data['updateDate'] = this.timeF(this.updateDate).format("YYYY-MM-DD HH:mm:ss");
				this.postHttp(this,data,"material/saveMaterial",function(obj,data){
					if(data.code == '10000'){
			  			obj.notify_jr(obj,'成功','添加成功','success');
							obj.addTextbookClose();
			  			
			  			var dataS = ajaxData(obj);
							init_tabel(obj,dataS);
			  		}else{
			  			obj.notify_jr(obj,'错误','网络错误','error');
			  		}
				});
			},
			updatetextBook(){
				var data = this.textbook;
				data['publicationDate'] = this.timeF(this.publicationDate).format("YYYY-MM-DD HH:mm:ss");
				data['createDate'] = this.timeF(this.createDate).format("YYYY-MM-DD HH:mm:ss");
				data['updateDate'] = this.timeF(this.updateDate).format("YYYY-MM-DD HH:mm:ss");
				this.postHttp(this,data,"material/updateMaterial",function(obj,data){
					if(data.code == '10000'){
			  			obj.notify_jr(obj,'成功','编辑成功','success');
							obj.addTextbookClose();
			  			
			  			var dataS = ajaxData(obj);
							init_tabel(obj,dataS);
			  		}else{
			  			obj.notify_jr(obj,'错误','网络错误','error');
			  		}
				});
			},
			handleClose(done){
       			this.addTextbookClose();
       			done();
       		},
			addTextbookClose(){
				this.textbook = {
        	id:null,
        	grade:'',
        	subject:'', 
        	publicationDate:'',
        	coverImg:'',
       };
       this.bookSyllabuss = null;
       this.optionSchool_val = null;
       this.materialId = null;
       this.levelName = "";
			 this.dialogaddbook = false;
			},
			dialogInfos(id){
				this.dialoginfobook = true;
				var data = {id:id}
				this.postHttp(this,data,"material/queryMaterialById",function(obj,data){
					if(data.code == '10000'){
							obj.textbook = data.result;
							obj.textbook.publicationDate = obj.timeF(obj.textbook.publicationDate).format("YYYY-MM-DD HH:mm:ss"); 
							var data_code = {materialId:id}
			  			obj.postHttp(obj,data_code,"materialsyllabus/queryMaterialSyllabussNew",function(obj,data){
			  				obj.bookSyllabuss = data.result;
			  			});
			  		}else{
			  			obj.notify_jr(obj,'错误','网络错误','error');
			  		}
				});
				
			},
			dialogEdits(id){
				this.infoTitles = "编辑教材";
				this.dialogaddbook = true;
				this.bookSyllabuss = null;
				this.isEdit = false;
				var data = {id:id}
				this.materialId = id;
				this.postHttp(this,data,"material/queryMaterialById",function(obj,data){
					if(data.code == '10000'){
							obj.textbook = data.result;
							obj.textbook.publicationDate = obj.timeF(obj.textbook.publicationDate).format("YYYY-MM-DD HH:mm:ss"); 
							var data_code = {materialId:obj.materialId}
			  			obj.postHttp(obj,data_code,"materialsyllabus/queryMaterialSyllabussNew",function(obj,data){
			  				if(data.result.length == 0){
			  					obj.bookSyllabuss = null;
			  				}else{
			  					obj.bookSyllabuss = data.result;
			  				}
			  				
			  			});
			  			obj.imageUrl=obj.baseUrl+obj.textbook.coverImg;
			  		}else{
			  			obj.notify_jr(obj,'错误','网络错误','error');
			  		}
				});
			},
			delete_book(id){
				var data = {id:id};
				this.postHttp(this,data,"material/deleteMaterial",function(obj,data){
					if(data.code == '10000'){
							obj.notify_jr(obj,'删除','删除成功','success');
							var data = ajaxData(obj);
							init_tabel(obj,data);
			  		}else{
			  			obj.notify_jr(obj,'错误',data.message,'error');
			  		}
				});
			},
			handleSizeChange(val) {
				var data = ajaxData(this);
		  	init_tabel(this,data)
		  },
		  handleCurrentChange(val) {
		  	var pageS = this.pageSize;
		  	var dataS = {pageNum:val,pageSize:pageS};
		  },
			init_coursesyllabus(obj,data){
				
			},
			initTable(obj,data){
				this.tableData = data.result.list;
		    this.pageNum = data.result.pageNum;
		    this.pageSize = data.result.pageSize;
		    this.totals = data.result.total;
			},
			school_chage_q(){
				var id = this.optionSchool_val;
				this.optionGrand = '';
				this.optionGrand_val = null;
				if(id!=""){
					this.get_options(this,id,"optionGrand");
				}
		  },
		  object_handle(obj,data){
		  	this.optionSubject = data.result;
		  },
		  handleAvatarSuccess(res, file){
		  	this.imageUrl = URL.createObjectURL(file.raw);
		  	this.textbook.coverImg = res.result.path;
		  }, 
		  beforeAvatarUpload(file){
		  	const isJPG = file.type === 'image/jpeg';
	        const isPNG = file.type === 'image/png';
	        const isIMG = isJPG||isPNG;
	        const isLt2M = file.size / 1024 / 1024 < 2;

	        if (!isIMG) {
	          this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
	        }
	        if (!isLt2M) {
	          this.$message.error('上传头像图片大小不能超过 2MB!');
	        }
	        return isIMG && isLt2M;
		  },
		  creatNew(){
		  	if(this.materialId!=''){
      		if(this.levelName==''){
		  			this.notify_jr(this,'提示','请输入一级大纲名字','warning');
			  		return;
			  	}
	      	var data = {name:this.levelName,level:1,parentId:'',materialId:this.materialId}
			  	this.postHttp(this,data,"materialsyllabus/saveMaterialSyllabus",function(obj,datas){
			  		if(datas.code == '10000'){
			  			obj.notify_jr(obj,'成功','添加成功','success');
			  			var data_code = {materialId:obj.materialId}
			  			obj.textbook['id'] = obj.materialId;
			  			obj.postHttp(obj,data_code,"materialsyllabus/queryMaterialSyllabussNew",function(obj,data){
			  				obj.bookSyllabuss = data.result;
			  			});
			  		}else{
			  			obj.notify_jr(obj,'错误','网络错误','error');
			  		}
			  	});
      	}else{
      		this.postHttp(this,{},"getUUID",function(obj,datas){
			  		obj.materialId = datas.result;
			  		var name = obj.levelName;
				  	var level = 1;
				  	var parentId = '';
				  	if(obj.materialId==null){
				  		obj.notify_jr(obj,'提示','网络错误','warning');
				  		return;
				  	}
				  	if(name==''){
				  		obj.notify_jr(obj,'提示','请输入一级大纲名字','warning');
				  		return;
				  	}
				  	var data = {name:name,level:level,parentId:parentId,materialId:obj.materialId}
				  	obj.postHttp(obj,data,"materialsyllabus/saveMaterialSyllabus",function(obj,datas){
				  		if(datas.code == '10000'){
				  			obj.notify_jr(obj,'成功','添加成功','success');
				  			var data_code = {materialId:obj.materialId}
				  			obj.textbook['id'] = obj.materialId;
				  			obj.postHttp(obj,data_code,"materialsyllabus/queryMaterialSyllabussNew",function(obj,data){
				  				obj.bookSyllabuss = data.result;
				  			});
				  		}else{
				  			obj.notify_jr(obj,'错误','网络错误','error');
				  		}
				  	});
			  	});
		  	}
		  },
		  renderContent(createElement, { node, data, store }) {
        var self = this;  
        return createElement('span', [  
            createElement('span', node.label),  
            createElement('span', {attrs:{  
                style:"float: right; margin-right: 20px",class:"tree_btn"  
            }},[  
                createElement('i',{attrs:{  
                    class:'el-icon-plus'
                },on:{  
                    click:function() {  
                    	self.text_id = data.id;
                    	self.text_add = true;
                    	self.text_name = "";
                    	self.text_infos.text_name = ""
                    }  
                }},""),  
                createElement('i',{attrs:{  
                     class:'el-icon-minus'  
                },on:{  
                    click:function() {  
                    	self.text_id = data.id;
                    	self.$confirm('此操作将删除该大纲下所有子项,是否继续?', '提示', {
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
      savetext(){
      	this.$refs['ruleForms'].validate((valid) => {
          if (valid) {
          	var data = {parentId:this.text_id,name:this.text_infos.text_name,materialId:this.materialId};
          	this.postHttp(this,data,"materialsyllabus/saveMaterialSyllabus",function(obj,data){
          		if(data.code=="10000"){
			      		obj.text_add = false;
			      		obj.notify_jr(obj,"新增","操作成功","success");
			      		var data_code = {materialId:obj.materialId}
				  			obj.postHttp(obj,data_code,"materialsyllabus/queryMaterialSyllabussNew",function(obj,data){
				  				obj.bookSyllabuss = data.result;
				  			});
			      	}else{
			      		obj.notify_jr(obj,"新增",data.message,"error");
			      	}
          	});
          	
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      addTextClose(){
      	this.text_id = '';
	    	this.text_add = false;
	    	this.text_name = "";
      },
      delete_text_s(){
      	var data = {id:this.text_id};
      	this.postHttp(this,data,"materialsyllabus/deleteMaterialSyllabus",function(obj,data){
      		if(data.code=="10000"){
		      		obj.notify_jr(obj,"删除","操作成功","success");
		      		var data_code = {materialId:obj.materialId}
			  			obj.postHttp(obj,data_code,"materialsyllabus/queryMaterialSyllabussNew",function(obj,data){
			  				obj.bookSyllabuss = data.result;
			  			});
		      	}else{
		      		obj.notify_jr(obj,"删除",data.message,"error");
		      	}
      	});
      },
      timeFormat(row,column){
		  	var date = row[column.property];  
		  	if (date == undefined) {  
		     return "";  
		  	}  
		  	return this.timeF(date).format("YYYY-MM-DD HH:mm:ss");  
		  },
		},
		mounted:function(){
			this.get_options(this,"","optionSchool");
			var data_code ={code:'SUBJECT'};
  		this.postHttp(this,data_code,"dictionary/getDictionarysBySupCode",this.object_handle);
			var data = ajaxData(this);
			init_tabel(this,data);
			this.Url=this.getBaseUrl()+"uploadFile/upload";
			this.baseUrl=this.getBaseUrl();
		}
		
  }
  
  function init_tabel(obj,data){
			obj.postHttp(obj,data,'material/queryMaterials',obj.initTable);
	}
  
  function ajaxData(obj){
		
  	var grade = obj.optionGrand_val;
  	var subject = obj.optionSubject_val;
  	var press = obj.name_q;
  	var pageNum  = obj.pageNum;
  	var pageSize = obj.pageSize;
  	var data = {
  		grade:grade,
  		subject:subject,
  		press:press,
  		pageNum:pageNum,
  		pageSize:pageSize
  	}
  	return data;
  }
</script>

<style>
  #textBook .el-input__icon {
    width: 55px;
  }
  #textBook .addBody{background:#fff;min-height: 500px;padding:20px 40px;}
  
  #textBook .leftPart{width:40%;}
  #textBook .rightPart{width:60%;}
  #textBook .avatar-uploader-icon{border:1px solid #bfcbd9;}
  #textBook .avatar-uploader-icon:hover{border-color:#66BB6A}
  #textBook .leftPart .el-date-editor.el-input{width:100%;}
  
  #textBook .bigSteel{width: 67%;border:1px #BFCBD9 solid;height: 200px;border-radius: 3px;padding:5px 2%;}
  #textBook .infoItems{width: 100%;}
  
  #textBook .infoBody{padding-top:30px;}
  #textBook .ml30{margin-left:70px}
  #textBook .rightImg{width:122px;height: 140px;background:#D8D8D8;border: 1px solid #979797;margin-left:20px;margin-right:75px;}
  #textBook .infoItems .tr{width: 30%;}
  
  #textBook #textBookT{width:90%;}
  #textBook #textBookT i{font-size:14px;padding:3px;margin-right:20px;border-radius:2px;}
  #textBook .el-icon-plus{color:#EF5350;border:1px #EF5350 solid;}
	#textBook .el-icon-minus{color:#FFCA28;border:1px #FFCA28 solid;}
	#textBook .el-tree-node__expand-icon{border-left-color:#202a33;}
	
	#textBook .el-dialog--tiny .infoBody{min-height: 80px;padding-top:40px}
	#textBook .rightImg img{height: 100%}
	#textBook .el-dialog__header{background:#66BB6A;text-align: center; border-top-left-radius: 4px; border-top-right-radius: 4px;}
	#textBook .el-dialog__title{height: 50px; line-height: 50px; font-size: 16px; color: #fff; background: #66BB6A;font-weight:normal;}
</style>