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
				<tr v-for="record in mergedRentals" :key="record.id">
					<td>{{ record.title }}</td>
					<td>{{ record.pages }}</td>
					<td>{{ record.author }}</td>
					<td>
						<button @click="returnBook(record.bookId)">Return book</button>
					</td>
				</tr>
			</tbody>
		</table>
		<p v-if="error" class="error-message">Couldn't load data from server</p>
	</div>
</template>

<script>
export default {
	name: 'rental-table',
	data() {
		return {
			error: '',
			reader: {},
			rentals: [],
			authors: [],
			mergedRentals: [],
		};
	},
	methods: {
		async getRentals() {
			Promise.all([
				fetch('http://localhost:8080/rentals/' + this.$route.params.id)
					.then(res => (res.ok && res.json()) || Promise.reject(res))
					.catch(error => {
						this.error = true;
						console.error(error);
					}),
				fetch('http://localhost:8080/get/reader/' + this.$route.params.id)
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
				this.rentals = data[0];
				this.reader = data[1];
				this.authors = data[2];

				for (let i = 0; i < this.rentals.length; i++) {
					const author = this.authors.filter(
						author => author.id === this.rentals[i].authorId,
					)[0];
					this.mergedRentals.push({
						id: author.id + this.rentals[i].id,
						bookId: this.rentals[i].id,
						title: this.rentals[i].title,
						pages: this.rentals[i].pages,
						author: author.firstName + ' ' + author.lastName,
					});
				}
			});
		},
		async returnBook(bookId) {
			fetch('http://localhost:8080/return-book/' + bookId, {
				method: 'GET',
			})
				.then(() => {
					this.mergedRentals = this.mergedRentals.filter(rental => {
						return rental.bookId != bookId;
					});
					this.error = false;
				})
				.catch(error => {
					this.error = true;
					console.log(error);
				});
		},
	},
	mounted() {
		this.getRentals();
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
