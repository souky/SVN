<template>
	<div id="twoWayTable">
		<transition name="el-zoom-in-top">
		<div v-show="showTable" class="main_body">
			<div class="twoWay_info_search">
				<el-row id="queryForm" :model="queryInfos" :gutter="20">
				  <el-col class="queryItems" :span="6">
				  	<div class="l">细目表名称</div>
				  	<div class="r">
				  		<el-input v-model="queryInfos.specificationName" clearable placeholder="双向细目表名称"></el-input>
				  	</div>
				  </el-col>
				  <el-col class="queryItems" :span="6">
				  	<div class="l">年级</div>
				  	<div class="r">
				  		<el-select v-model="queryInfos.gradeCode" clearable placeholder="年级">
					      <el-option v-for="e in gradeOption" :key="e" :label="e" :value="e"></el-option>
					    </el-select>
				  	</div>
				  </el-col>
				  <el-col class="queryItems" :span="6">
				  	<div class="l">科目</div>
				  	<div class="r">
				  		<el-select v-model="queryInfos.subjectCode" clearable placeholder="请选择科目">
						    <el-option v-for="e in subjectArray" :key="e" :label="e" :value="e">
						    </el-option>
						</el-select>
				  	</div>
				  </el-col>
				  <el-col :span="6">
				  	<div class="btn_query r" @click="queryInfo">
				  		<i class="el-icon-search">查询</i>
				  	</div>
				  </el-col>
				</el-row>
			</div>

			<div class="twoWay_info_table">
				<div class="tools fix">
					<div class="items_tools l" @click="add_two_way">
						<i class="el-icon-circle-plus-outline">新增</i>
					</div>
				</div>
				<el-table :data="tableData" style="width: 100%">
			      <el-table-column prop="specificationName" align="center" label="双向细目表名称"></el-table-column>
			      <el-table-column prop="gradeCode"  align="center" label="年级"></el-table-column>
			      <el-table-column prop="subjectCode" align="center"  label="科目"></el-table-column>
			      <el-table-column prop="createDate" align="center" :formatter="timeFormatter"  label="创建时间"></el-table-column>
			      <el-table-column align="center" label="操作" width='250'>
			      	<template slot-scope="scope">
			      		<el-button type="primary" icon="el-icon-search" @click="showInfo(scope.row)">查看</el-button>
								<el-button type="primary" icon="el-icon-edit" @click="editInfo(scope.row)">编辑</el-button>
			      		<el-button type="primary" icon="el-icon-delete" @click="deleteInfo(scope.row.id)">删除</el-button>
			      	</template>
			      </el-table-column>
			    </el-table>

			    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageNum"
			      :page-sizes="[10, 20, 50]"
			      :page-size="pageSize"
			      layout="sizes, prev, pager, next, jumper, total"
			      :total="total" class="tc mt20"
			      >
			    </el-pagination>
			</div>
		</div>
		</transition>

		<transition name="el-zoom-in-bottom">
		<div v-show="showDetails" class="twoWay_details main_body">
			<div class="twoWay_info_table">
				<div class="tools fix">
					<div class="items_tools r" @click="backList">
						<i class="el-icon-back" >返回列表</i>
					</div>
				</div>
				<el-table :data="detailsData" style="width: 100%" :row-class-name="setClassName">
						<el-table-column prop="itemType" type="expand">
							<template slot-scope="props">
								<el-row style="border-bottom:1px #c7c7c7 solid;">
									<el-col :span="2">序号</el-col>
									<el-col :span="2">分步分数</el-col>
									<el-col :span="15">分步答案</el-col>
									<el-col :span="5">分步知识点</el-col>
								</el-row>
								<el-row v-for="(e,index) in props.row.stepList" :key="index" style="border-bottom:1px #e6e6e6 solid;">
									<el-col :span="2">{{(index+1)}}</el-col>
									<el-col :span="2">{{e.stepScore}}</el-col>
									<el-col :span="15" :title="e.stepAnswer" class="ell" style="text-align:left;">{{e.stepAnswer}}</el-col>
									<el-col :span="5">{{e.knowledgePointName}}</el-col>
								</el-row>
							</template>
						</el-table-column>
			      <el-table-column prop="itemNo"  align="center" label="题号" width="50px"></el-table-column>
			      <el-table-column prop="itemType" :formatter="typeFormatter" align="center" label="题型"></el-table-column>
			      <el-table-column prop="itemScore" align="center"  label="满分" width="50px"></el-table-column>
			      <el-table-column prop="itemAnswer" show-overflow-tooltip align="center" label="答案" width="50px"></el-table-column>
			      <el-table-column align="center" label="能力值">
			      	<el-table-column prop="itemAbility[0]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" :label="ablityArray[0]"></el-table-column>
			      	<el-table-column prop="itemAbility[1]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" :label="ablityArray[1]"></el-table-column>
			      	<el-table-column prop="itemAbility[2]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" :label="ablityArray[2]"></el-table-column>
			      	<el-table-column prop="itemAbility[3]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" :label="ablityArray[3]"></el-table-column>
			      	<el-table-column prop="itemAbility[4]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" :label="ablityArray[4]"></el-table-column>
			      	<el-table-column prop="itemAbility[5]" class-name="checkIcon" width='100' :formatter="ablitFormatter" align="center" :label="ablityArray[5]"></el-table-column>
			      </el-table-column>
			      <el-table-column prop="knowledgeName" align="center" width='250' show-overflow-tooltip label="知识点"></el-table-column>
			    </el-table>
			</div>
		</div>
		</transition>

		<el-dialog title="添加双向细目表" :visible.sync="dialogVisible" width="90%">
		  <el-row id="queryForm" :model="TwoWaySpecification" :gutter="20">
				  <el-col class="queryItems" :span="6" >
				  	<div class="l"><font style="color: red;margin-right: 3px;">*</font>名称</div>
				  	<div class="r">
				  		<el-input v-model="TwoWaySpecification.specificationName" placeholder="名称" :maxlength="30"></el-input>
				  	</div>
				  </el-col>
				   <el-col class="queryItems" :span="6">
				  	<div class="l"><font style="color: red;margin-right: 3px;">*</font>年级</div>
				  	<div class="r">
				  		<el-select v-model="TwoWaySpecification.gradeCode" placeholder="年级">
					      <el-option v-for="e in gradeOption" :key="e" :label="e" :value="e"></el-option>
					    </el-select>
				  	</div>
				  </el-col>
				  <el-col class="queryItems" :span="6">
				  	<div class="l"><font style="color: red;margin-right: 3px;">*</font>科目</div>
				  	<div class="r">
				  		<el-select @change="subjectChange" v-model="TwoWaySpecification.subjectCode" placeholder="请选择科目">
						    <el-option v-for="e in subjectArray" :key="e" :label="e" :value="e">
						    </el-option>
							</el-select>
				  	</div>
				  </el-col>

				  <el-col :span="6">
				  </el-col>
			</el-row>

			<el-row class="adds" v-model="addsD" :gutter="20" style="margin-top:10px">
				<el-col :span="15"></el-col>
			  <el-col :span="1">
					<el-input v-model="addsD.startNum"  placeholder="题号"></el-input>
				</el-col>
				<el-col :span="1">~</el-col>
				<el-col :span="1">
					<el-input v-model="addsD.endNum" placeholder="题号"></el-input>
				</el-col>
				<el-col :span="1">
					<el-input v-model="addsD.point" placeholder="分值"></el-input>
				</el-col>
				<el-col :span="2">
					<el-select v-model="addsD.types" placeholder="选择题型">
							<el-option label="主观题" value="1"></el-option>
							<el-option label="客观题" value="0"></el-option>
					</el-select>
				</el-col>
				<el-col :span="3">
					<div class="btn_query " @click="addsItems">
						<i class="el-icon-plus">批量新增</i>
					</div>
				</el-col>
			</el-row>

			<el-row :gutter="10">
				<el-col :span="1">题号</el-col>
				<el-col :span="2">题型</el-col>
				<el-col :span="1">满分</el-col>
				<el-col :span="7">答案</el-col>
				<el-col :span="4">知识点</el-col>
				<el-col :span="5">能力值</el-col>
				<el-col :span="4">操作</el-col>
			</el-row>

			<el-row v-model="two_way_D" :gutter="10" >
				<div v-for="e in two_way_D" :key="e.id" class="fix" style="min-height: 80px;border-bottom:1px #c7c7c7 solid;">
					<el-col :span="1" style="margin-top:25px">
						<el-input v-model="e.itemNo" placeholder="题号"></el-input>
					</el-col>
					<el-col :span="2" style="margin-top:25px">
						<el-select v-model="e.itemType" >
					      <el-option label="主观题" value="1"></el-option>
					      <el-option label="客观题" value="0"></el-option>
					    </el-select>
					</el-col>
					<el-col :span="1" style="margin-top:25px">
						<el-input v-model="e.itemScore" placeholder="满分" ></el-input>
					</el-col>
					<el-col :span="7" style="margin-top:25px" v-if="e.itemType == 0">
						<el-input v-model="e.itemAnswer" ></el-input>
					</el-col>
					<el-col :span="4" style="margin-top:25px" v-if="e.itemType == 0">
					    <el-cascader v-model="e.knowledgeId" placeholder="选择知识点" :options="knowOption" :props="defaultProps" :show-all-levels="false"></el-cascader>
					</el-col>

					<div style="width: 45.83333%;float: left;" v-if="e.itemType == 1">
						<div v-for="es in e.stepList" :key="es.id">
							<el-col :span="3" style="margin-top:25px" v-if="e.itemType == 1">
								<el-input v-model="es.stepScore" placeholder="分步分"></el-input>
							</el-col>
							<el-col :span="8" style="margin-top:25px" v-if="e.itemType == 1">
								<el-input v-model="es.stepAnswer" placeholder="分步答案" ></el-input>
							</el-col>
							<el-col :span="7" style="margin-top:25px" v-if="e.itemType == 1">
								 <el-cascader v-model="es.knowledgePointId" placeholder="选择知识点" :options="knowOption" :props="defaultProps" :show-all-levels="false"></el-cascader>
							</el-col>
							<el-col :span="6" style="margin-top:25px" v-if="e.itemType == 1">
								<el-button type="primary" size="small" round @click="addItems(e)">添加分步</el-button>
								<el-button type="danger" size="small" round style="backgound:red" @click.prevent="deleteItems(es,e)">删除</el-button>
							</el-col>
						</div>
					</div>

					<el-col :span="5">
							<el-checkbox-group v-model="e.itemAbility">
								<el-row :gutter="10">
									<el-col :span="8">
										<el-checkbox :label="ablityArray[0]" ></el-checkbox>
									</el-col>
									<el-col :span="8">
										<el-checkbox :label="ablityArray[1]" ></el-checkbox>
									</el-col>
									<el-col :span="8">
										<el-checkbox :label="ablityArray[2]" ></el-checkbox>
									</el-col>
								</el-row>
								<el-row :gutter="10">
									<el-col :span="8">
										<el-checkbox :label="ablityArray[3]" ></el-checkbox>
									</el-col>
									<el-col :span="8">
										<el-checkbox :label="ablityArray[4]" ></el-checkbox>
									</el-col>
									<el-col :span="8">
										<el-checkbox :label="ablityArray[5]" ></el-checkbox>
									</el-col>
								</el-row>
							</el-checkbox-group>
					</el-col>
					<el-col :span="4" style="margin-top:25px">
						 <el-button type="primary" size="small" round @click="addDetile()">添加</el-button>
						 <el-button type="danger" size="small" round @click.prevent="removeDomain(e)">删除</el-button>
					</el-col>
				</div>

			</el-row>

		  	<span slot="footer" class="dialog-footer">
			  <el-button type="primary" @click="dialogVisible = false">取 消</el-button>
			  <el-button type="primary" @click="saveEdit">确 定</el-button>
			</span>
		</el-dialog>


	</div>
</template>

<script>
export default {
  data () {

    return {
		  msg: 'twoWayTable',
		  tableData:[],
		  detailsData:[],
		  queryInfos:{
		  	specificationName:'',
		  	gradeCode:'',
		  	subjectCode:''
		  },
		  pageNum:1,
	    pageSize:10,
	    total:1,

	    subjectArray:[],
	    gradeOption:[],

	    showTable:true,
	    showDetails:false,
	    parentId:'',
	    dialogVisible:false,
	    dialogSubject:'',
			innerVisible:false,
			changeItems:null,
			ablityArray:['空间想象','抽象概括','推理论证','运算求解','数据处理','综合应用'],

	    dialogEdit:false,
	    two_way_D_single:{
	    	itemNo:'',
	  		itemType:'0',
	  		itemScore:'',
	  		itemAnswer:'',
	  		itemAbility:[]
	    },

	    TwoWaySpecification:{
		  },
		  two_way_D:[
		  	{
		  		itemNo:'',
		  		itemType:'0',
		  		itemScore:'',
		  		itemAnswer:'',
		  		itemAbility:[],
					stepList:[
						{
							stepScore:'',
							stepAnswer:'',
							knowledgePointId:[],
						}
					],
		  	},
		  ],

	  	rules: {
	        specificationName: [
	          { required: true, message: '请输入角色名称', trigger: 'blur' }
	        ],
	   	},

	   	knowOption:[],
	   	defaultProps: {
	      	children: 'kpVOChildList',
	      	label: 'knowledgeContent',
	      	value:'id'
		 	},

			addsD:{}
	  }
  },
  mounted:function(){
  	this.queryInfo();
  	this.postHttp(this,{},"school/querySchools",function(obj,res){
  		obj.subjectArray = res.result.subjectArray;
  	});

  	this.postHttp(this,{},'getLoingGrade',function(obj,res){
  		obj.gradeOption = res.result;
  	});
  },
  methods:{
  	queryInfo(){
  		var datas = this.ajaxData();
	  	this.postHttp(this,datas,'twowayspecification/queryTwoWaySpecifications',function(obj,res){
	  		obj.pageNum = res.result.pageNum;
	  		obj.pageSize = res.result.pageSize;
	  		obj.total = res.result.total;
	  		obj.tableData = res.result.list;
	  	})
  	},
  	saveEdit(){
  		var id = this.TwoWaySpecification.id;
  		var address = 'twowayspecification/saveTwoWaySpecification';
  		if(id){
				delete this.TwoWaySpecification['createDate'];
				delete this.TwoWaySpecification['updateDate'];
  			address = 'twowayspecification/updateTwoWaySpecification';
  		}
  		var dataDetails = this.formatDate();
			this.TwoWaySpecification['twDetails'] = JSON.stringify(dataDetails);
  		if(!this.TwoWaySpecification.specificationName){
  			this.notify_jr(this,'操作错误','请输入细目表名称','warning');
  			return;
  		}
  		if(!this.TwoWaySpecification.gradeCode){
  			this.notify_jr(this,'操作错误','请选择年级','warning');
  			return;
  		}
  		if(!this.TwoWaySpecification.subjectCode){
  			this.notify_jr(this,'操作错误','请选择科目','warning');
  			return;
  		}
  		var loading = this.loading('正在处理...');
      	this.postHttp(this,this.TwoWaySpecification,address,function(obj,res){
			if(res.code == '10000'){
				loading.close();
				obj.dialogVisible = false;
				obj.notify_success();
				obj.queryInfo();
			}else{
				loading.close();
				obj.notify_jr(obj,'操作错误',res.message,'error');
			}
        });
  	},
	editInfo(row){
		var subjectName = row.subjectCode;
		var gradeCode = row.gradeCode;
		this.postHttp(this,{subjectName:subjectName,gradeCode:gradeCode},'knowledgepoint/queryKnowledgePointsBySubjectName',function(obj,res){
	  		obj.knowOption = res.result;
  	});
		this.postHttp(this,{id:row.id},'twowayspecification/queryTwoWaySpecificationById',function(obj,res){
			var ablityArray = obj.ablityArray;
			var list = res.result.listDetail;
			for(var i = 0;i < list.length; i++){
				var itemAbility = list[i].itemAbility;
				var arrays = new Array();
				for(var j = 0;j < itemAbility.length;j++){
					if(itemAbility[j] == '1'){
						arrays.push(ablityArray[j]);
					}
				}
				list[i].itemAbility = arrays;
				if(list[i].itemType == 0){
					list[i].knowledgeId = list[i].knowledgeId.split(",");
				}else{
					var lists = list[i].stepList;
					for(var x = 0;x < lists.length;x++){
						lists[x].knowledgePointId = lists[x].knowledgePointId.split(",");
					}
				}
				list[i].itemType += '';
			}
			obj.TwoWaySpecification = res.result.twowayspecification;
			obj.two_way_D = list;
			obj.dialogVisible = true;
		})
	},
	deleteInfo(id){
		this.$confirm('此操作将删除该双向细目表和已经关联的考试计划,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        	this.postHttp(this,{id:id},'twowayspecification/deleteTwoWaySpecification',function(obj,res){
		  		if(res.code == "10000"){
		  			obj.notify_success();
					obj.queryInfo();
		  		}else{
		  			obj.notify_jr(obj,'操作错误',res.message,'error');
		  		}
		  	});
        }).catch(() => {

        });
	},
	deleteInfoDetail(id){
		this.$confirm('此操作将删除该细则,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        	this.postHttp(this,{id:id},'twowayspecificationdetail/deleteTwoWaySpecificationDetail',function(obj,res){
		  		if(res.code == "10000"){
		  			obj.notify_success();
					var row = {id:obj.parentId,subjectCode:obj.dialogSubject};
					obj.showInfo(row);
		  		}else{
		  			obj.notify_jr(obj,'操作错误',res.message,'error');
		  		}
		  	});
        }).catch(() => {

        });
	},
	addDetials(){
		this.dialogEdit = true;
		this.postHttp(this,{subjectName:this.dialogSubject},'knowledgepoint/queryKnowledgePointsBySubjectName',function(obj,res){
	  		obj.knowOption = res.result;
	  	});
	},
	addItems(e){
		e.stepList.push({
			stepScore:'',
			stepAnswer:'',
			knowledgePointId:[],
		})
	},
	deleteItems(es,e){
		var index = e.stepList.indexOf(es)
		if (index !== -1 && e.stepList.length != 1) {
			e.stepList.splice(index, 1)
		}
	},
	saveItemsAn(){
		var s = this.itemsL;
		var html = '';
		for(var i = 0;i<s.length;i++){
			html += s[i].num + ':' + s[i].answer+';';
			s[i].knowledge = s[i].knowledge[(s[i].knowledge.length - 1)];
		}
		this.changeItems.itemAnswer = s;
		this.changeItems.itemAnswers = html;
		this.innerVisible = false;
		this.itemsL = [{
			num:'',
			score:'',
			answer:'',
			knowledge:[]
		}]
	},
	saveDetials(){
		this.two_way_D_single["parentId"] = this.parentId;
		var data = this.formatDateSingle();
		var loading = this.loading('正在处理...');
		this.postHttp(this,data,"twowayspecificationdetail/saveTwoWaySpecificationDetail",function(obj,res){
			if(res.code == '10000'){
				loading.close();
				obj.dialogEdit = false;
				obj.notify_success();
				var row = {id:obj.parentId,subjectCode:obj.dialogSubject};
				obj.showInfo(row);
			}else{
				loading.close();
				obj.notify_jr(obj,'操作错误',res.message,'error');
			}
        });
	},
	showInfo(row){
		this.showTable = false;
		this.showDetails = true;
		this.parentId = row.id;
		this.dialogSubject = row.subjectCode;
		this.postHttp(this,{subjectName:row.subjectCode},'subjectablity/querySubjectAblitys',function(obj,res){
	  		obj.ablityArray = res.result;
  	});
		this.postHttp(this,{parentId:row.id},'twowayspecificationdetail/queryTwoWaySpecificationDetails',function(obj,res){
			var deDate = res.result;
			for(var i = 0;i < deDate.length; i++){
				var itemAbility = deDate[i].itemAbility;
				var arrays = new Array();
				for(var j = 0;j < itemAbility.length;j++){
					arrays.push(itemAbility[j]);
				}
				deDate[i].itemAbility = arrays;
			}
	  		obj.detailsData = deDate;
	  	})
	},
	backList(){
		this.showTable = true;
		this.showDetails = false;
	},
	add_two_way(){
		this.dialogVisible = true;
		this.TwoWaySpecification = {};
		this.addsD = {};
		this.two_way_D = [{
			itemNo:'',
	  		itemType:'0',
	  		itemScore:'',
	  		itemAnswer:'',
	  		itemAbility:[],
				stepList:[
					{
						stepScore:'',
						stepAnswer:'',
						knowledgePointId:[],
					}
				],
		}];
	},
	subjectChange(){
		var subjectName = this.TwoWaySpecification.subjectCode;
		var gradeCode = this.TwoWaySpecification.gradeCode;
		this.postHttp(this,{subjectName:subjectName,gradeCode:gradeCode},'knowledgepoint/queryKnowledgePointsBySubjectName',function(obj,res){
	  		obj.knowOption = res.result;
  	});
		this.postHttp(this,{subjectName:subjectName},'subjectablity/querySubjectAblitys',function(obj,res){
	  		obj.ablityArray = res.result;
  	});
	},
	addDetile(){
		this.two_way_D.push({
			itemNo:'',
  		itemType:'0',
  		itemScore:'',
  		itemAnswer:'',
  		itemAbility:[],
			stepList:[
				{
					stepScore:'',
					stepAnswer:'',
					knowledgePointId:[],
				}
			],
		});
	},
	removeDomain(item) {
	    var index = this.two_way_D.indexOf(item)
	    if (index !== -1 && this.two_way_D.length != 1) {
	      this.two_way_D.splice(index, 1)
	    }
	},
	handleSizeChange(val) {
	  	this.pageNum = 1;
			this.pageSize = val;
			this.queryInfo();
	},
	handleCurrentChange(val) {
	  	this.pageNum = val;
			this.queryInfo();
	},
	timeFormatter(row, column, cellValue){
		var date = row[column.property];
	  	if (date == undefined || date == '') {
	     return "";
	  	}
	  	return this.timeF(date).format("YYYY-MM-DD HH:mm:ss");
	},
	typeFormatter(row, column, cellValue){
		var type = row[column.property];
		if(type == '1'){
	  		return '主观题';
	  	}else{
	  		return '客观题';
	  	}
	  	if (type == undefined || type == '') {
	     return "";
	  	}
	},
	ablitFormatter(row, column, cellValue){
		var values = cellValue;
		if(values == '1'){
	  		return '√';
	  	}else{
	  		return '';
	  	}
	},
	setClassName({row, index}){
      return row.itemType == 0 ? 'expand' : '';
  },
	addsItems(){
		var startNum = parseInt(this.addsD.startNum);
		var endNum = parseInt(this.addsD.endNum);
		var types = this.addsD.types;
		var point = this.addsD.point;

		if(isNaN(startNum) || isNaN(endNum) || (startNum <= 0 || endNum <=0)){
			this.notify_jr(this,'操作错误','题号格式错误','error');
			return;
		}
		if(startNum >= endNum){
			this.notify_jr(this,'操作错误','起始题号必须小于结束题号','error');
			return;
		}
		for(startNum;startNum <= endNum;startNum++){
			var o = {
				itemNo:startNum,
	  		itemType:types,
	  		itemScore:point,
	  		itemAnswer:'',
	  		itemAbility:[],
				stepList:[
					{
						stepScore:'',
						stepAnswer:'',
						knowledgePointId:[],
					}
				],
			}
			this.two_way_D.push(o);
		}
		this.addsD = {};
	},
	ajaxData(){
		var data = new Object();
		data["pageSize"] = this.pageSize;
		data["pageNum"] = this.pageNum;
		data["specificationName"] = this.queryInfos.specificationName;
		data["gradeCode"] = this.queryInfos.gradeCode;
		data["subjectCode"] = this.queryInfos.subjectCode;
		return data;
	},
	formatDate(){
		var data = JSON.parse(JSON.stringify(this.two_way_D));
		var dataArray = new Array();
		var ablityArrayS = this.ablityArray;
		for(var i = 0;i < data.length;i++){
			var itemAbility = data[i].itemAbility.toString();
			var itemAbilityString = "";
			if(itemAbility.indexOf(ablityArrayS[0]) >= 0){
				itemAbilityString +='1';
			}else{
				itemAbilityString +='0';
			}
			if(itemAbility.indexOf(ablityArrayS[1]) >= 0){
				itemAbilityString +='1';
			}else{
				itemAbilityString +='0';
			}
			if(itemAbility.indexOf(ablityArrayS[2]) >= 0){
				itemAbilityString +='1';
			}else{
				itemAbilityString +='0';
			}
			if(itemAbility.indexOf(ablityArrayS[3]) >= 0){
				itemAbilityString +='1';
			}else{
				itemAbilityString +='0';
			}
			if(itemAbility.indexOf(ablityArrayS[4]) >= 0){
				itemAbilityString +='1';
			}else{
				itemAbilityString +='0';
			}
			if(itemAbility.indexOf(ablityArrayS[5]) >= 0){
				itemAbilityString +='1';
			}else{
				itemAbilityString +='0';
			}
			data[i].itemAbility = itemAbilityString;
			if(data[i].itemType == '1'){
				data[i].knowledgeId = '';
				var lists = data[i].stepList;
				for(var j = 0;j < lists.length;j++){
					var strings = '';
					var listk = lists[j].knowledgePointId;
					if(listk != undefined){
						for(var x = 0;x < listk.length;x++){
							if(x == 0){
								strings += listk[x];
							}else{
								strings += "," + listk[x];
							}
						}
					}
					data[i].stepList[j].knowledgePointId = strings;
				}
			}else{
				delete data[i]['stepList'];
				var listK = data[i].knowledgeId;
				var strings = '';
				if(listK != undefined){
					for(var j = 0;j < listK.length;j++){
						if(j == 0){
							strings += listK[j];
						}else{
							strings += "," + listK[j];
						}
					}
				}
				data[i].knowledgeId = strings;
			}
			dataArray[i] = data[i];
		}
		return dataArray;
	}
  }
}
</script>

<style>
#twoWayTable .twoWay_info_search{
	width: 90%;
	margin:20px auto;
}
#twoWayTable .twoWay_info_table{
	width: 90%;
	margin:20px auto;
}
#twoWayTable .el-table__row .checkIcon{
	font-size: 22px;
    color: #67C23A;
    font-weight: 800;
}
#twoWayTable .two_way_subject{
    width: 600px;
    margin: 5px auto;
	height:50px;
	line-height: 50px;
	color:#666;
	border-bottom:1px #e2e2e2 solid;
}
#twoWayTable .el-collapse-item__header{padding-left:20px;}
#twoWayTable .el-checkbox-group{padding:0px 20px;}
#twoWayTable .el-checkbox.is-bordered.is-checked{border-color:#FFD100}
#twoWayTable .el-col{
	height: 40px;
    line-height: 40px;
    text-align: center;
    margin-bottom:2px;
}
#twoWayTable .el-col-1 .el-input__inner{padding:0px;text-align: center;}
#twoWayTable .expand .el-table__expand-column .cell {
    display: none;
}
#twoWayTable .el-checkbox:first-child{
	margin-right: 0px;
}
</style>
