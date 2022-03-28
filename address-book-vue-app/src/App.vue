// mały pokaz Read w CRUD
<template>
	<div id="app" class="small-container">
		<h1>Znajomi</h1>
		<person-form @add:person="addPerson" />
		<!-- @add:person="addPerson" przechwytywanie danych z komponentu niżej-->
		<persons-table :personsSource="persons" />
		<!-- Przekazywanie przez propsy -->
	</div>
</template>

<script>
import PersonsTable from '@/components/PersonsTable.vue';
import PersonForm from '@/components/PersonsForm.vue';

export default {
	name: 'App', // name musi być zawsze
	components: {
		PersonsTable,
		PersonForm,
	},
	data() {
		// analogia do state w react
		return {
			persons: [
				{
					id: 1,
					name: 'Adam Słodowy',
					email: 'adam.slodowy@zrobtosam.pl',
					phone: '+48 787 774 664',
				},
				{
					id: 2,
					name: 'Michał Studencki',
					email: 'ms@student.pwr.edu.pl',
					phone: '+48 600 565 454',
				},
				{
					id: 3,
					name: 'Kamila Napokaz',
					email: 'kami2003@h2.pl',
					phone: '+48 609 554 987',
				},
			],
		};
	},
	mounted() {
		// uruchamiane po zamontowaniu komponentu do dom
		this.getPersons();
	},
	methods: {
		addPerson(person) {
			this.persons = [...this.persons, person];
		},
		async getPersons() {
			try {
				const response = await fetch(
					'https://jsonplaceholder.typicode.com/users',
				);

				const data = await response.json();
				this.persons = data;
			} catch (error) {
				console.error(error);
			}
		},
	},
};
</script>

<style>
/* jak zrobimy <style scoped> to styl będzie tylko dla danego komponentu - analogia do css modules w react*/
button {
	background: #009435;
	border: 1px solid #009435;
}

.small-container {
	max-width: 680px;
}
</style>
