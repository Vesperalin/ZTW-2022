<template>
	<div>
		<h1>Preview</h1>
		<div class="panel-wrapper">
			<p class="title">First Name</p>
			<p>{{ reader.firstName }}</p>
			<p class="title separator">Last Name</p>
			<p>{{ reader.lastName }}</p>
			<p v-if="errorWithServer" class="error-message separator">
				Couldn't load data from server
			</p>
		</div>
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

<style scoped>
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
	color: #cabe15;
}
</style>
