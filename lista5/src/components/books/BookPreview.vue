<template>
	<div>
		<p>Title</p>
		<p>{{ book.title }}</p>
		<p>Pages</p>
		<p>{{ book.pages }}</p>
		<p>Author</p>
		<p>{{ book.authorName + ' ' + book.authorSurname }}</p>
		<p v-if="errorWithServer" class="error-message">
			Couldn't load data from server
		</p>
	</div>
</template>

<script>
export default {
	name: 'book-preview',
	data() {
		return {
			errorWithServer: false,
			book: {
				title: '',
				pages: undefined,
				authorName: '',
				authorSurname: '',
			},
		};
	},
	methods: {
		async getBookAndAuthor() {
			const bookId = this.$route.params.id;

			try {
				const responseBook = await fetch(
					'http://localhost:8080/get/book/' + bookId,
					{ method: 'GET' },
				);
				const book = await responseBook.json();

				const responseAuthor = await fetch(
					'http://localhost:8080/get/author/' + book.authorId,
					{ method: 'GET' },
				);
				const author = await responseAuthor.json();

				this.book.title = book.title;
				this.book.pages = book.pages;
				this.book.authorName = author.firstName;
				this.book.authorSurname = author.lastName;

				this.errorWithServer = false;
			} catch (error) {
				this.errorWithServer = true;
				console.error(error);
			}
		},
	},
	mounted() {
		this.getBookAndAuthor();
	},
};
</script>
