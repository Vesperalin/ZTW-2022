<template>
	<div>
		<p>First Name</p>
		<p>{{ reader.firstName }}</p>
		<p>Last Name</p>
		<p>{{ reader.lastName }}</p>
		<p v-if="errorWithServer" class="error-message">
			Couldn't load data from server
		</p>
	</div>
</template>

<script>
export default {
	name: 'reader-preview',
	data() {
		return {
			errorWithServer: false,
			reader: {
				id: -1,
				firstName: '',
				lastName: '',
			},
		};
	},
	methods: {
		async getReader() {
			let readerId = this.$route.params.id;
			try {
				const response = await fetch(
					'http://localhost:8080/get/reader/' + readerId,
					{ method: 'GET' },
				);
				const data = await response.json();
				this.reader = data;
				this.errorWithServer = false;
			} catch (error) {
				this.errorWithServer = true;
				console.error(error);
			}
		},
	},
	mounted() {
		this.getReader();
	},
};
</script>
