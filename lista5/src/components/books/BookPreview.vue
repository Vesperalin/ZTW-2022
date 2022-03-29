<template>
	<div class="panel-wrapper">
		<p class="title">Title</p>
		<p>{{ book.title }}</p>
		<p class="title separator">Pages</p>
		<p>{{ book.pages }}</p>
		<p class="title separator">Author</p>
		<p>{{ book.authorName + ' ' + book.authorSurname }}</p>
		<p v-if="errorWithServer" class="error-message separator">
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

<style scoped>
.panel-wrapper {
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
}
</style>
