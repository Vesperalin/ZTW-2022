<template>
	<div>
		<p>First Name</p>
		<p>{{ author.firstName }}</p>
		<p>Last Name</p>
		<p>{{ author.lastName }}</p>
		<p v-if="errorWithServer" class="error-message">
			Couldn't load data from server
		</p>
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
	},
	mounted() {
		this.getAuthor();
	},
};
</script>
