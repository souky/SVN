/**
 * @description 主页数据
 */

export default {
	Attributes:[
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
			parameter:'autoClose',
			explain:'是否自动关闭',
			type:'Boolean',
			chioceVal:'true/false',
			defaultVal:'true'
		},
		{
			parameter:'type',
			explain:'提示类型',
			type:'String',
			chioceVal:'success/error/warning/info',
			defaultVal:'success'
		},
		{
			parameter:'position',
			explain:'提示框方位',
			type:'String',
			chioceVal:'left-top/right-top/left-bottom/right-bottom',
			defaultVal:'right-top'
		},
		{
			parameter:'closeTime',
			explain:'自动隐藏时间,此值为0时不自动隐藏,单位ms',
			type:'number',
			chioceVal:'-',
			defaultVal:'5000'
		},
		{
			parameter:'width',
			explain:'提示框宽度,单位PX',
			type:'number',
			chioceVal:'-',
			defaultVal:'200'
		},
		{
			parameter:'height',
			explain:'提示框高度,单位PX',
			type:'number',
			chioceVal:'-',
			defaultVal:'100'
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
