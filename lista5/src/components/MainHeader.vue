<template>
	<header>
		<router-link class="title" to="/">Bookery</router-link>
		<nav class="navbar">
			<router-link to="/">Home</router-link>
			<router-link to="/books">Books</router-link>
			<router-link to="/authors">Authors</router-link>
			<router-link to="/readers">Readers</router-link>
			<router-link to="/rentals">Rentals</router-link>
		</nav>
	</header>
</template>

<script>
export default {
	name: 'main-header',
};
</script>

<style scoped>
header {
	height: 70px;
	overflow: hidden;
	background-color: #496d8d;
	position: fixed;
	top: 0;
	width: 100%;
	left: 0;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.title {
	color: #f7f7f7;
	font-size: 2.5rem;
	text-decoration: none;
}

nav {
	display: flex;
}

.navbar a {
	display: block;
	color: #f7f7f7;
	font-size: 1.2rem;
	text-align: center;
	height: 100%;
	text-decoration: none;
	padding: 0 15px;
}

.navbar a:hover {
	color: #d3d3d3;
	text-decoration: underline;
	text-decoration-thickness: 0.1rem;
	text-decoration-color: #d3d3d3;
}
</style>
