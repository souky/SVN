$(function(){
	//初始化工具栏提示信息
	$("[data-toggle='tooltip']").tooltip();
	
	//单选框选择和取消
	$('table tbody tr td span.checkboxImg').on('click',function(){
		if($(this).hasClass('checked')){
			$(this).removeClass('checked');
		}else{
			$(this).addClass('checked');
		}
	});
	//全选和取消
	$('table thead tr th span.checkboxImg').on('click',function(){
		if($(this).hasClass('checked')){
			$(this).removeClass('checked');
			$(this).parents('table').find('span.checkboxImg').removeClass('checked');
		}else{
			$(this).addClass('checked');
			$(this).parents('table').find('span.checkboxImg').addClass('checked');
		}
	});
	//添加
	$('#addRecord').on('click', function(){
		  layer.open({
		  type: 2,
		  title: '新增表单',
		  maxmin: true,
		  shadeClose: true, 
		  shade: false,
		  area : ['1000px' , '520px'],
		  content: 'addRecord.html',
		  btn: ['确认', '取消']
		  });
		});
});

