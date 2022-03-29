<template>
	<div>
		<h1>Edit reader</h1>
		<div id="reader-form">
			<form @submit.prevent="updateReader(reader)">
				<label>First name</label>
				<input
					v-model="reader.firstName"
					name="first-name"
					type="text"
					:class="{ 'has-error': submitting && invalidReader }"
					@focus="clearStatus"
					@keypress="clearStatus"
				/>
				<label>Last name</label>
				<input
					v-model="reader.lastName"
					name="last-name"
					type="text"
					:class="{ 'has-error': submitting && invalidReader }"
					@focus="clearStatus"
					@keypress="clearStatus"
				/>
				<button>Edit reader</button>
			</form>
			<p v-if="error && submitting" class="error-message">
				Please fill the form
			</p>
			<p v-if="errorWithServer" class="error-message">
				Couldn't load data from server
			</p>
		</div>
	</div>
</template>

<script>
export default {
	name: 'reader-update',
	computed: {
		invalidReader() {
			return this.reader.firstName === '' && this.reader.lastName === '';
		},
	},
	data() {
		return {
			submitting: false,
			error: false,
			errorWithServer: false,
			reader: {
				id: -1,
				firstName: '',
				lastName: '',
			},
		};
	},
	methods: {
		async getReader() {
			let authorId = this.$route.params.id;
			try {
				const response = await fetch(
					'http://localhost:8080/get/reader/' + authorId,
					{ method: 'GET' },
				);
				const data = await response.json();
				this.reader = data;
				this.errorWithServer = false;
			} catch (error) {
				this.errorWithServer = true;
				console.error(error);
			}
		},

		async updateReader(reader) {
			this.submitting = true;
			this.clearStatus();

			if (this.invalidReader) {
				this.error = true;
				return;
			}

			let readerToUpdateId = reader.id;

			try {
				fetch('http://localhost:8080/update/reader/' + readerToUpdateId, {
					method: 'PUT',
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
					body: new URLSearchParams(reader),
				});
				this.$router.push({
					name: 'readers',
				});
			} catch (error) {
				this.errorWithServer = true;
				console.error(error);
			}
		},
		clearStatus() {
			this.error = false;
		},
	},
	mounted() {
		this.getReader();
	},
};
</script>
