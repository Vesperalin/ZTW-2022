<template>
	<div>
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
				<button>Edit author</button>
			</form>
			<p v-if="error && submitting" class="error-message">
				Please fill the form
			</p>
			<p v-if="errorWithServer" class="error-message">
				Couldn't load data from server
			</p>
		</div>
	</div>
</template>

<script>
export default {
	name: 'author-update',
	computed: {
		invalidAuthor() {
			return this.author.firstName === '' && this.author.lastName === '';
		},
	},
	data() {
		return {
			submitting: false,
			error: false,
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
				});
				this.$router.push({
					name: 'authors',
				});
			} catch (error) {
				this.errorWithServer = true;
				console.error(error);
			}
		},
		clearStatus() {
			this.error = false;
		},
	},
	mounted() {
		this.getAuthor();
	},
};
</script>
