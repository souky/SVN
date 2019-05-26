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
			parameter:'defaultValue',
			explain:'原有属性',
			type:'String/number',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'textarea-size',
			explain:'框体大小',
			type:'String',
			chioceVal:'small/medium/large',
			defaultVal:'medium'
		},
		{
			parameter:'disable',
			explain:'是否禁用',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
		{
			parameter:'allow-row',
			explain:'允许的行数',
			type:'number',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'read-only',
			explain:'规定文本为只读',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
		{
			parameter:'max-length',
			explain:'最大允许字符数',
			type:'number',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'resize',
			explain:'是否可调整大小',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
		{
			parameter:'validate',
			explain:'验证规则,焦点离开后触发,参数为正则或者方法,当参数为方法时返回boolean',
			type:'regular/function',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'tips',
			explain:'验证错误时的提示,跟随validate共用',
			type:'String',
			chioceVal:'-',
			defaultVal:'参数错误'
		},
		{
			parameter:'require',
			explain:'非空验证',
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
	]
}