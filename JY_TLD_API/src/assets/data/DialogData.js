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
			defaultVal:'-'
		},
		{
			parameter:'content',
			explain:'内容容器id',
			type:'String',
			chioceVal:'-',
			defaultVal:'-'
		},
		{
			parameter:'has-close-btn',
			explain:'是否有关闭按钮',
			type:'Boolean',
			chioceVal:'true/false',
			defaultVal:'true'
		},
		{
			parameter:'has-mask',
			explain:'是否有遮罩层',
			type:'Boolean',
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