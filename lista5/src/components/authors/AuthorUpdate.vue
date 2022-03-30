<template>
	<div class="content">
		<h1>Edit author</h1>
		<div id="author-form">
			<form @submit.prevent="updateAuthor(author)">
				<label>First name</label>
				<input
					v-model="author.firstName"
					name="first-name"
					type="text"
					:class="{ 'has-error': submitting && invalidAuthor }"
					@focus="clearStatus"
					@keypress="clearStatus"
				/>
				<label>Last name</label>
				<input
					v-model="author.lastName"
					name="last-name"
					type="text"
					:class="{ 'has-error': submitting && invalidAuthor }"
					@focus="clearStatus"
					@keypress="clearStatus"
				/>
				<button class="button">Edit author</button>
				<p v-if="success" class="success-message">
					Successfully updated the author
				</p>
				<p v-if="error && submitting" class="error-message">
					Please fill the form correctly
				</p>
				<p v-if="errorWithServer" class="error-message">
					{{ this.errorMessage }}
				</p>
			</form>
		</div>
		<button class="back-button" @click="getBack()">&larr; Back</button>
	</div>
</template>

<script>
export default {
	name: 'author-update',
	computed: {
		invalidAuthor() {
			return (
				this.author.firstName.trim() === '' &&
				this.author.lastName.trim() === ''
			);
		},
	},
	data() {
		return {
			submitting: false,
			error: false,
			errorWithServer: false,
			success: false,
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
		async updateAuthor(author) {
			this.submitting = true;
			this.clearStatus();

			if (this.invalidAuthor) {
				this.error = true;
				return;
			}

			let authorToUpdateId = author.id;

			try {
				fetch('http://localhost:8080/update/author/' + authorToUpdateId, {
					method: 'PUT',
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
					body: new URLSearchParams(author),
				})
					.then(response => {
						if (!response.ok) {
							this.success = false;
							throw response;
						}
						this.success = true;
					})
					.catch(error => {
						this.error = true;
						this.errorMessage = error.message;
						console.error(error);
					});
			} catch (error) {
				this.error = true;
				this.errorMessage = error.message;
			}
		},
		clearStatus() {
			this.error = false;
			this.success = false;
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
	text-align: center;
}
form {
	min-width: 300px;
	max-width: 500px;
	padding: 10px 20px;
	background-color: #e6e2dd;
	margin: 10px auto;
	padding: 20px;
	border-radius: 8px;
}

input {
	display: block;
	margin-bottom: 8px;
}

input,
select {
	background: #f7f7f7;
	border: none;
	border-radius: 4px;
	font-size: 15px;
	margin: 0;
	outline: 0;
	padding: 10px;
	width: 100%;
	box-sizing: border-box;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-webkit-box-shadow: 0 1px 0 rgba(0, 0, 0, 0.03) inset;
	box-shadow: 0 1px 0 rgba(0, 0, 0, 0.03) inset;
	margin-bottom: 30px;
}

select {
	-webkit-appearance: menulist-button;
	height: 35px;
}

.button {
	display: block;
	padding: 15px;
	color: #fff;
	margin: 0 auto;
	background-color: #207744;
	font-size: 18px;
	text-align: center;
	font-style: normal;
	width: 100%;
	border-radius: 10px;
	margin-bottom: 10px;
}

.button:hover {
	background-color: #106633;
}
</style>
