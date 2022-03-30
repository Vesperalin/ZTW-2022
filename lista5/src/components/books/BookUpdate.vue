<template>
	<div class="content">
		<h1>Edit book</h1>
		<div>
			<form @submit.prevent="updateBook">
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
				<button class="button">Edit book</button>
				<p v-if="error && submitting" class="error-message">
					Please fill the form correctly
				</p>
				<p v-if="success" class="success-message">
					Successfully updated the book
				</p>
				<p v-if="errorWithServer" class="error-message">
					Couldn't load data from server
				</p>
			</form>
		</div>
		<button class="back-button" @click="getBack()">&larr; Back</button>
	</div>
</template>

<script>
export default {
	name: 'book-form',
	computed: {
		invalidBook() {
			return (
				this.book.title.trim() === '' ||
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
			errorMessage: '',
			book: {
				title: '',
				pages: undefined,
				authorId: -1,
			},
			authors: [],
		};
	},
	methods: {
		async getBookAuthors() {
			const bookId = this.$route.params.id;

			const responseBook = await fetch(
				'http://localhost:8080/get/book/' + bookId,
				{ method: 'GET' },
			);
			const book = await responseBook.json();
			this.book = book;

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
		async updateBook() {
			const bookId = this.$route.params.id;
			this.submitting = true;
			this.clearStatus();

			if (this.invalidBook) {
				this.error = true;
				return;
			}

			try {
				fetch('http://localhost:8080/update/book/' + bookId, {
					method: 'PUT',
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
					body: new URLSearchParams(this.book),
				})
					.then(response => {
						if (!response.ok) {
							this.success = false;
							throw response;
						}
					})
					.catch(error => {
						this.error = true;
						this.errorWithServer = true;
						this.errorMessage = error.message;
						console.error(error);
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
		getBack() {
			this.$router.push('/books');
		},
	},
	mounted() {
		this.getBookAuthors();
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
	background-color: #274c6b;
	color: #f7f7f7;
	border-radius: 10px;
	font-size: 0.75rem;
}

.back-button:hover {
	cursor: pointer;
	background-color: #163b5a;
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
	padding: 10px;
}

input,
select {
	background: #f7f7f7;
	border: none;
	border-radius: 4px;
	font-size: 15px;
	margin: 0;
	outline: 0;

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
	background-color: #274c6b;
	font-size: 18px;
	text-align: center;
	font-style: normal;
	width: 100%;
	border-radius: 10px;
	margin-bottom: 10px;
}

.button:hover {
	background-color: #163b5a;
}
</style>
