<template>
	<div>
		<h1>Rent a book</h1>
		<div>
			<form @submit.prevent="rentBook">
				<label>Reader</label>
				<select name="readers" v-model="rentData.readerId">
					<option v-for="reader in readers" :key="reader.id" :value="reader.id">
						{{ reader.firstName + ' ' + reader.lastName }}
					</option>
				</select>

				<label>Book</label>
				<select name="book" v-model="rentData.bookId">
					<option
						v-for="bookWithAuthor in booksWithAuthors"
						:key="bookWithAuthor.bookId"
						:value="bookWithAuthor.bookId"
					>
						{{
							bookWithAuthor.title +
							' pages: ' +
							bookWithAuthor.pages +
							' by: ' +
							bookWithAuthor.author
						}}
					</option>
				</select>
				<button class="button">Rent book</button>
				<p v-if="error && submitting" class="error-message">
					Please fill the form correctly
				</p>
				<p v-if="success" class="success-message">
					Successfully added new rental
				</p>
				<p v-if="error" class="error-message">{{ this.errorMessage }}</p>
			</form>
		</div>
	</div>
</template>

<script>
export default {
	name: 'rental-add',
	data() {
		return {
			submitting: false,
			error: false,
			errorWithServer: false,
			success: false,
			errorMessage: '',
			readers: [],
			books: [],
			authors: [],
			booksWithAuthors: [],
			rentData: {
				bookId: -1,
				readerId: -1,
			},
		};
	},
	computed: {
		invalidRental() {
			return this.rentData.bookId <= 0 || this.rentData.readerId <= 0;
		},
	},
	methods: {
		async prepareRentals() {
			Promise.all([
				fetch('http://localhost:8080/get/books')
					.then(res => (res.ok && res.json()) || Promise.reject(res))
					.catch(error => {
						this.error = true;
						console.error(error);
					}),
				fetch('http://localhost:8080/get/readers')
					.then(res => (res.ok && res.json()) || Promise.reject(res))
					.catch(error => {
						this.error = true;
						console.error(error);
					}),
				fetch('http://localhost:8080/get/authors')
					.then(res => (res.ok && res.json()) || Promise.reject(res))
					.catch(error => {
						this.error = true;
						console.error(error);
					}),
			]).then(data => {
				this.error = false;
				this.books = data[0];
				this.readers = data[1];
				this.authors = data[2];

				for (let i = 0; i < this.books.length; i++) {
					const author = this.authors.filter(
						author => author.id === this.books[i].authorId,
					)[0];
					this.booksWithAuthors.push({
						bookId: this.books[i].id,
						title: this.books[i].title,
						pages: this.books[i].pages,
						author: author.firstName + ' ' + author.lastName,
					});
				}
			});
		},
		async rentBook() {
			this.submitting = true;
			this.clearStatus();

			if (this.invalidRental) {
				this.error = true;
				return;
			}

			try {
				fetch('http://localhost:8080/rent-book/' + this.rentData.bookId, {
					method: 'POST',
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
					body: new URLSearchParams(this.rentData),
				})
					.then(response => {
						if (!response.ok) {
							this.success = false;
							throw response;
						}
					})
					.catch(error => {
						this.error = true;
						this.errorMessage = error.message;
						console.error(error);
					});

				this.rentData.bookId = -1;
				this.rentData.readerId = -1;
				this.errorMessage = '';
				this.error = false;
				this.success = true;
				this.submitting = false;
				this.errorWithServer = false;
			} catch (error) {
				this.errorWithServer = true;
				this.errorMessage = error.message;
				console.error(error);
				console.log('dasdas');
			}
		},
		clearStatus() {
			this.success = false;
			this.error = false;
		},
	},
	mounted() {
		this.prepareRentals();
	},
};
</script>

<style scoped>
div h1 {
	text-align: center;
}

form {
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
	background-color: #9e2e82;
	font-size: 18px;
	text-align: center;
	font-style: normal;
	width: 100%;
	border-radius: 10px;
	margin-bottom: 10px;
}

.button:hover {
	background-color: #8d1d71;
}
</style>
