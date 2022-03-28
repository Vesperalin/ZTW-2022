<template>
	<div class="main-component">
		<main-header />
		<div class="wrapper"><router-view /></div>
	</div>
</template>

<script>
import MainHeader from '@/components/MainHeader.vue';
export default {
	name: 'app',
	components: {
		MainHeader,
	},
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Lato:wght@400;700&display=swap');
* {
	margin: 0;
	padding: 0;
	border: 0;
	font-family: inherit;
	box-sizing: border-box;
}
.main-component {
	font-family: 'Lato', sans-serif;
	background-color: #f7f3ee;
	min-height: 100vh;
	width: 100%;
}

.wrapper {
	padding-top: 200px;
	display: flex;
	justify-content: center;
}

.error-message {
	color: #d33c40;
}

[class*='-message'] {
	font-weight: 500;
}
.success-message {
	color: #32a95d;
}
</style>
