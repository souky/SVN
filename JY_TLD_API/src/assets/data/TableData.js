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
			parameter:'show-header',
			explain:'是否显示表头',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'true'
		},
    {
			parameter:'row-class-name',
			explain:'列的class',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
    {
			parameter:'show-selection',
			explain:'是否显示选择框',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
		// {
		// 	parameter:'',
		// 	explain:'',
		// 	type:'',
		// 	chioceVal:'',
		// 	defaultVal:''
		// },
	],
  columnAttributes:[
    {
			parameter:'prop',
			explain:'列属性名',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
    {
			parameter:'label',
			explain:'列中文名',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
    {
			parameter:'width',
			explain:'列宽度(百分比)',
			type:'number',
			chioceVal:'1~100',
			defaultVal:'-'
		},
    {
			parameter:'formatter',
			explain:'格式化此列',
			type:'fn',
			chioceVal:'-',
			defaultVal:'-'
		},
    {
			parameter:'is-expand',
			explain:'是否拓展',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
    {
			parameter:'align',
			explain:'对齐方式',
			type:'String',
			chioceVal:'left/center/right',
			defaultVal:'left'
		},
    {
			parameter:'show-tips',
			explain:'内容过长被隐藏时显示tips',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'fasle'
		},
    {
			parameter:'class-name',
			explain:'列class名',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
    // {
		// 	parameter:'',
		// 	explain:'',
		// 	type:'',
		// 	chioceVal:'',
		// 	defaultVal:''
		// },
    // {
		// 	parameter:'',
		// 	explain:'',
		// 	type:'',
		// 	chioceVal:'',
		// 	defaultVal:''
		// },
  ]
}
