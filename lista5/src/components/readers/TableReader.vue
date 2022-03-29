<template>
	<div class="table-wrapper">
		<h1>Readers</h1>
		<table>
			<thead>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="reader in readers" :key="reader.id">
					<td>{{ reader.firstName }}</td>
					<td>{{ reader.lastName }}</td>
					<td>
						<button
							@click="
								$router.push({
									name: 'reader-update',
									params: { id: reader.id },
								})
							"
						>
							Edit
						</button>
						<button @click="deleteReader(reader.id)">Delete</button>
						<button
							@click="
								$router.push({
									name: 'readers-reader',
									params: { id: reader.id },
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
					name: 'reader-add',
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
	name: 'reader-table',
	data() {
		return {
			error: false,
			readers: [],
		};
	},
	methods: {
		async getReaders() {
			try {
				this.error = false;
				const response = await fetch('http://localhost:8080/get/readers');
				const data = await response.json();
				this.readers = data;
			} catch (error) {
				this.error = true;
				console.error(error);
			}
		},
		async deleteReader(readerId) {
			fetch('http://localhost:8080/delete/reader/' + readerId, {
				method: 'DELETE',
			})
				.then(() => {
					this.readers = this.readers.filter(reader => {
						return reader.id != readerId;
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
		this.getReaders();
	},
};
</script>

<style scoped>
.table-wrapper {
	text-align: center;
}

.table-wrapper h1 {
	margin-bottom: 10px;
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
	background-color: #dbcf26;
	color: #f7f7f7;
	border-radius: 10px;
	font-size: 0.95rem;
}

button:hover {
	cursor: pointer;
	background-color: #cabe15;
}
</style>
