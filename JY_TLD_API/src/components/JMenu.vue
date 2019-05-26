<template>
	<div id="menu">
		<div >标签</div>
		<div class="title">
			<router-link to="/table">
				TABLE标签<font class="undone">(完善中)</font>
			</router-link>
		</div>

		<div class="title">
			SELECTIONS标签
		</div>
		<div class="title title_second">
			<router-link to="/input">
				INPUT标签
			</router-link>
		</div>
		<div class="title title_second">
			<router-link to="/select">
				SELECT标签
			</router-link>
		</div>

		<div class="title">
			<router-link to="/textarea">
				TEXTAREA标签
			</router-link>
		</div>

		<div class="title">
			<router-link to="/page">
				PAGE标签
			</router-link>
		</div>

		<div class="title">
			<router-link to="/tree">
				TREE标签
			</router-link>
		</div>

		<div class="title">
			<router-link to="/menu">
				MENU标签
			</router-link>
		</div>

		<div class="title">
			<router-link to="/nav">
				NAV标签
			</router-link>
		</div>

		<div class="title">
			<router-link to="/button">
				BUTTON标签
			</router-link>
		</div>
		<div >组件</div>
		<div class="title">
			通知组件
		</div>
		<div class="title title_second">
			<router-link to="/alert">
				ALERT
			</router-link>
		</div>
		<div class="title title_second">
			<router-link to="/confirm">
				CONFIRM
			</router-link>
		</div>
		<div class="title title_second">
			<router-link to="/notify">
				NOTIFY
			</router-link>
		</div>
		<div class="title">
			<router-link to="/dialog">
				DIALOG组件
			</router-link>
		</div>

	</div>
</template>

<script>
</script>

<style>
#menu a{
	text-decoration: none;
	color:#888;
}
#menu .title{
	width:320px;
	padding-left:40px;
	height:40px;
	line-height: 40px;
	color:#888;
	font-size:16px;
	cursor: default;
}
#menu .title_second{
	width:300px;
	padding-left:60px;
	font-size:14px;
}
.router-link-active{
	color:#6bbada!important;
}
#menu .undone{
	color: #ccc;
	margin-left: 10px;
}
</style>
