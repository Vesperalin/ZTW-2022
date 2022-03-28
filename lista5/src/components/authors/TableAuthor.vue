<template>
	<div>
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
							See
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
/*table {
	padding-top: 200px;
}*/
</style>
