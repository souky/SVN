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
			parameter:'value',
			explain:'原有属性',
			type:'String/number',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'label',
			explain:'select前label',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'options',
			explain:'select装载数据,此参数可以为方法,需返回标准options数组',
			type:'array/function',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'changes',
			explain:'select变化时调用的方法',
			type:'function',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'multiselect',
			explain:'是否可以多选',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
		{
			parameter:'show-cheack-box',
			explain:'是否显示多选框',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
		{
			parameter:'disable',
			explain:'是否禁用',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
		
//		{
//			parameter:'',
//			explain:'',
//			type:'',
//			chioceVal:'',
//			defaultVal:''
//		},
	],
	optionsData:[
		{
			parameter:'name',
			explain:'显示的字段',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'val',
			explain:'对应的值',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'disable',
			explain:'是否禁用此选项',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
	]
}