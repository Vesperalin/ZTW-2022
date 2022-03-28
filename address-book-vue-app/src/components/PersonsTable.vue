<template>
	<div id="persons-table">
		<table>
			<thead>
				<tr>
					<th>Imię i nazwisko</th>
					<th>Email</th>
					<th>Telefon</th>
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
	name: 'persons-table',
	props: {
		personsSource: Array,
	},
};
</script>

<style scoped></style>
