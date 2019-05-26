/**
 * @description 主页数据
 */

export default {
	Attributes:[
		{
			parameter:'id',
			explain:'原有属性',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'class',
			explain:'原有属性',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'name',
			explain:'原有属性',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'pageOptions',
			explain:'分页所需要的数据,例如: var pageOptions=[{total:34}];',
			type:'Object',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'show-total',
			explain:'是否显示总条数',
			type:'Boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
		{
			parameter:'show-pages',
			explain:'是否显示总页数',
			type:'Boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
		{
			parameter:'NewsizeOptions',
			explain:'每页数据条数,需要自定义时需创建变量并赋值,例如：var NewsizeOptions=[5,10,20,30];',
			type:'Array',
			chioceVal:'-',
			defaultVal:'[10,20,30]'
		},
		{
			parameter:'NewpagingTotal',
			explain:'最大显示页码个数,需要自定义时需创建变量并赋值,例如：var NewpagingTotal= 6;',
			type:'number',
			chioceVal:'-',
			defaultVal:'5'
		},
//		{
//			parameter:'',
//			explain:'',
//			type:'',
//			chioceVal:'',
//			defaultVal:''
//		},
	],
	
	OptionAttributes:[
		{
			parameter:'output',
			explain:'返回当前页码和大小',
			type:'function(pageNum,pageSize)',
			chioceVal:'-',
			defaultVal:''
		},
	]
}