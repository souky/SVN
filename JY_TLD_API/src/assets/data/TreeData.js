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
			parameter:'tree-data',
			explain:'树结构数据',
			type:'Array',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'row-click',
			explain:'每一行的点击方法',
			type:'Function(id,text,children)',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'row-code',
			explain:'行内追加代码',
			type:'Function',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'jy-onlyOne',
			explain:'是否只展开一行',
			type:'Boolean',
			chioceVal:'true/false',
			defaultVal:'true'
		},
		
//		{
//			parameter:'',
//			explain:'',
//			type:'',
//			chioceVal:'',
//			defaultVal:''
//		},
	]
}