<template>
	<div class="table-wrapper">
		<table>
			<thead>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="author in authors" :key="author.id">
					<td>{{ author.firstName }}</td>
					<td>{{ author.lastName }}</td>
					<td>
						<button
							@click="
								$router.push({
									name: 'author-update',
									params: { id: author.id },
								})
							"
						>
							Edit
						</button>
						<button @click="deleteAuthor(author.id)">Delete</button>
						<button
							@click="
								$router.push({
									name: 'authors-author',
									params: { id: author.id },
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
					name: 'author-add',
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
	name: 'author-table',
	data() {
		return {
			error: false,
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
		async deleteAuthor(authorId) {
			fetch('http://localhost:8080/delete/author/' + authorId, {
				method: 'DELETE',
			})
				.then(() => {
					this.authors = this.authors.filter(author => {
						return author.id != authorId;
					});
				})
				.catch(error => {
					console.log(error);
				});
		},
	},
	mounted() {
		this.getAuthors();
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
