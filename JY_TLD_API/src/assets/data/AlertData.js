/**
 * @description 主页数据
 */

export default {
	Attributes:[
		{
			parameter:'width',
			explain:'弹框宽度',
			type:'Number',
			chioceVal:'-',
			defaultVal:'500'
		},
		{
			parameter:'height',
			explain:'弹框高度',
			type:'Number',
			chioceVal:'-',
			defaultVal:'300'
		},
		{
			parameter:'top',
			explain:'弹框距离上层高度',
			type:'Number',
			chioceVal:'-',
			defaultVal:'200'
		},
		{
			parameter:'title',
			explain:'标题',
			type:'String',
			chioceVal:'-',
			defaultVal:'提示'
		},
		{
			parameter:'content',
			explain:'提示内容',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'confirm-text',
			explain:'按钮显示文字',
			type:'String',
			chioceVal:'-',
			defaultVal:'确定'
		},
		{
			parameter:'has-close-btn',
			explain:'是否有关闭按钮',
			type:'Boolean',
			chioceVal:'true/false',
			defaultVal:'true'
		},
		{
			parameter:'show-mask',
			explain:'是否显示遮罩层',
			type:'Boolean',
			chioceVal:'true/false',
			defaultVal:'false'
		},
		{
			parameter:'success',
			explain:'确认回调',
			type:'Function',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'close',
			explain:'关闭回调',
			type:'Function',
			chioceVal:'-',
			defaultVal:'-'
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