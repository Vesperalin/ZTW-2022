<template>
	<div class="table-wrapper">
		<table>
			<thead>
				<tr>
					<th>Title</th>
					<th>Pages</th>
					<th>Author</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="record in mergedBooksAndAuthors" :key="record.id">
					<td>{{ record.title }}</td>
					<td>{{ record.pages }}</td>
					<td>{{ record.authorFirstName + ' ' + record.authorLastName }}</td>
					<td>
						<button
							@click="
								$router.push({
									name: 'book-update',
									params: { id: record.id },
								})
							"
						>
							Edit
						</button>
						<button @click="deleteBook(record.id)">Delete</button>
						<button
							@click="
								$router.push({
									name: 'book-book',
									params: { id: record.id },
								})
							"
						>
							Preview
						</button>
					</td>
				</tr>
			</tbody>
		</table>
		<button
			@click="
				$router.push({
					name: 'book-add',
				})
			"
		>
			Add Author
		</button>
		<p v-if="error" class="error-message">Couldn't load data from server</p>
	</div>
</template>

<script>
export default {
	name: 'book-table',
	data() {
		return {
			error: '',
			books: [],
			authors: [],
			mergedBooksAndAuthors: [],
		};
	},
	methods: {
		getAuthorsAndBooks() {
			Promise.all([
				fetch('http://localhost:8080/get/authors')
					.then(res => (res.ok && res.json()) || Promise.reject(res))
					.catch(error => {
						this.error = true;
						console.error(error);
					}),
				fetch('http://localhost:8080/get/books')
					.then(res => (res.ok && res.json()) || Promise.reject(res))
					.catch(error => {
						this.error = true;
						console.error(error);
					}),
			]).then(data => {
				this.error = false;
				this.authors = data[0];
				this.books = data[1];

				for (let i = 0; i < this.books.length; i++) {
					const author = this.authors.filter(
						author => author.id === this.books[i].authorId,
					)[0];
					this.mergedBooksAndAuthors.push({
						id: this.books[i].id,
						title: this.books[i].title,
						pages: this.books[i].pages,
						authorFirstName: author.firstName,
						authorLastName: author.lastName,
					});
				}
			});
		},
		async deleteBook(bookID) {
			fetch('http://localhost:8080/delete/book/' + bookID, {
				method: 'DELETE',
			})
				.then(() => {
					this.mergedBooksAndAuthors = this.mergedBooksAndAuthors.filter(
						book => {
							return book.id != bookID;
						},
					);
					this.books = this.books.filter(book => {
						return book.id != bookID;
					});
				})
				.catch(error => {
					console.log(error);
				});
		},
	},
	mounted() {
		this.getAuthorsAndBooks();
	},
};
</script>

<style scoped>
.table-wrapper {
	text-align: center;
}

th {
	font-size: 1.2rem;
	padding-bottom: 10px;
	font-weight: bold;
}

table {
	background-color: #e6e2dd;
	padding: 15px;
	border-radius: 10px;
	margin-bottom: 30px;
	box-shadow: 0px 0px 40px -28px rgba(0, 0, 0, 0.52);
	-webkit-box-shadow: 0px 0px 40px -28px rgba(0, 0, 0, 0.52);
	-moz-box-shadow: 0px 0px 40px -28px rgba(0, 0, 0, 0.52);
}

th,
td {
	min-width: 200px;
}

td {
	font-size: 1.1rem;
	padding: 10px 0;
}

button {
	padding: 10px 15px;
	margin: 0 5px;
	background-color: #274c6b;
	color: #f7f7f7;
	border-radius: 10px;
	font-size: 0.95rem;
}

button:hover {
	cursor: pointer;
	background-color: #163b5a;
}
</style>
