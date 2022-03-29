<template>
	<div>
		<h1>Add book</h1>
		<div>
			<form @submit.prevent="addBook">
				<label>Title</label>
				<input
					v-model="book.title"
					name="title"
					type="text"
					:class="{ 'has-error': submitting && invalidBook }"
					@focus="clearStatus"
					@keypress="clearStatus"
				/>
				<label>Pages</label>
				<input
					v-model="book.pages"
					name="pages"
					type="number"
					:class="{ 'has-error': submitting && invalidBook }"
					@focus="clearStatus"
					@keypress="clearStatus"
				/>
				<label>Author</label>
				<select name="authors" v-model="book.authorId">
					<option v-for="author in authors" :key="author.id" :value="author.id">
						{{ author.firstName + ' ' + author.lastName }}
					</option>
				</select>

				<button>Add author</button>
				<p v-if="error && submitting" class="error-message">
					Please fill the form
				</p>
				<p v-if="success" class="success-message">
					Successfully added new author
				</p>
				<p v-if="errorWithServer" class="error-message">
					Couldn't load data from server
				</p>
			</form>
		</div>
	</div>
</template>

<script>
export default {
	name: 'book-form',
	computed: {
		invalidBook() {
			return (
				this.book.title === '' ||
				this.book.pages <= 0 ||
				this.book.pages === undefined ||
				this.book.authorId <= -1
			);
		},
	},
	data() {
		return {
			submitting: false,
			error: false,
			errorWithServer: false,
			success: false,
			book: {
				title: '',
				pages: undefined,
				authorId: -1,
			},
			authors: [],
		};
	},
	methods: {
		async getAuthors() {
			try {
				this.error = false;
				const response = await fetch('http://localhost:8080/get/authors');
				const data = await response.json();
				this.authors = data;
			} catch (error) {
				this.error = true;
				console.error(error);
			}
		},
		async addBook() {
			this.submitting = true;
			this.clearStatus();

			if (this.invalidBook) {
				this.error = true;
				return;
			}

			try {
				fetch('http://localhost:8080/create/book', {
					method: 'POST',
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
					body: new URLSearchParams(this.book),
				});

				this.book.title = '';
				this.book.pages = undefined;
				this.book.authorId = -1;
				this.error = false;
				this.success = true;
				this.submitting = false;
				this.errorWithServer = false;
			} catch (error) {
				this.errorWithServer = true;
				console.error(error);
			}
		},
		clearStatus() {
			this.success = false;
			this.error = false;
		},
	},
	mounted() {
		this.getAuthors();
	},
};
</script>
