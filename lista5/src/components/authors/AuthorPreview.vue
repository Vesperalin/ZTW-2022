<template>
	<div class="content">
		<h1>Preview</h1>
		<div class="panel-wrapper">
			<p class="title">First Name</p>
			<p>{{ author.firstName }}</p>
			<p class="title separator">Last Name</p>
			<p>{{ author.lastName }}</p>
			<p v-if="errorWithServer" class="error-message separator">
				Couldn't load data from server
			</p>
		</div>
		<button class="back-button" @click="getBack()">&larr; Back</button>
	</div>
</template>

<script>
export default {
	name: 'author-preview',
	data() {
		return {
			errorWithServer: false,
			author: {
				id: -1,
				firstName: '',
				lastName: '',
			},
		};
	},
	methods: {
		async getAuthor() {
			let authorId = this.$route.params.id;
			try {
				const response = await fetch(
					'http://localhost:8080/get/author/' + authorId,
					{ method: 'GET' },
				);
				const data = await response.json();
				this.author = data;
				this.errorWithServer = false;
			} catch (error) {
				this.errorWithServer = true;
				console.error(error);
			}
		},
		getBack() {
			this.$router.push('/authors');
		},
	},
	mounted() {
		this.getAuthor();
	},
};
</script>

<style scoped>
.content {
	text-align: center;
}

.back-button {
	padding: 10px 15px;
	margin: 10px;
	background-color: #207744;
	color: #f7f7f7;
	border-radius: 10px;
	font-size: 0.75rem;
}

.back-button:hover {
	cursor: pointer;
	background-color: #106633;
}

div h1 {
	margin-bottom: 10px;
	text-align: center;
}

.panel-wrapper {
	min-width: 300px;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
	background-color: #e6e2dd;
	padding: 25px;
	border-radius: 10px;
	box-shadow: 0px 0px 40px -28px rgba(0, 0, 0, 0.52);
	-webkit-box-shadow: 0px 0px 40px -28px rgba(0, 0, 0, 0.52);
	-moz-box-shadow: 0px 0px 40px -28px rgba(0, 0, 0, 0.52);
}

.separator {
	margin-top: 15px;
}

.title {
	font-size: 1.2rem;
	font-weight: bold;
	color: #106633;
}
</style>
