<template>
	<div>
		<table>
			<thead>
				<tr>
					<th>Title</th>
					<th>Author</th>
					<th>Pages</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="person in personsSource" :key="person.id">
					<!-- :key to analogia do reacta - kiedy mapujemy tablicę elementów trzeba dawać key, żeby virtual dom ogarnął co jest czym -->
					<td>{{ person.name }}</td>
					<td>{{ person.email }}</td>
					<td>{{ person.phone }}</td>
				</tr>
			</tbody>
		</table>
	</div>
</template>

<script>
export default {
	name: 'book-table',
	data() {
		return {
			books: [],
		};
	},
	methods: {},
};
</script>
