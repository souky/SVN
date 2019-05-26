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
			parameter:'type',
			explain:'input类型',
			type:'String',
			chioceVal:'text,password',
			defaultVal:'text'
		},
		{
			parameter:'label',
			explain:'input前label',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'placeholder',
			explain:'占位字符',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'disable',
			explain:'是否可编辑',
			type:'boolean',
			chioceVal:'true/false',
			defaultVal:'true'
		},
		{
			parameter:'show-password',
			explain:'在type为password时可用,是否显示密码明文按钮',
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
