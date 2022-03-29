<template>
	<div>
		<h1>Add reader</h1>
		<div>
			<form @submit.prevent="addReader(reader)">
				<label>First Name</label>
				<input
					v-model="reader.firstName"
					name="firstName"
					type="text"
					:class="{ 'has-error': submitting && invalidAuthor }"
					@focus="clearStatus"
					@keypress="clearStatus"
				/>
				<label>Last Name</label>
				<input
					v-model="reader.lastName"
					name="lastName"
					type="text"
					:class="{ 'has-error': submitting && invalidAuthor }"
					@focus="clearStatus"
					@keypress="clearStatus"
				/>
				<button>Add reader</button>
				<p v-if="error && submitting" class="error-message">
					Please fill the form
				</p>
				<p v-if="success" class="success-message">
					Successfully added new author
				</p>
				<p v-if="errorWithServer" class="error-message">
					Couldn't load data from server
				</p>
			</form>
		</div>
	</div>
</template>

<script>
export default {
	name: 'reader-form',
	computed: {
		invalidReader() {
			return this.reader.firstName === '' || this.reader.lastName === '';
		},
	},
	data() {
		return {
			submitting: false,
			error: false,
			errorWithServer: false,
			success: false,
			reader: {
				firstName: '',
				lastName: '',
			},
			authors: [],
		};
	},
	methods: {
		async addReader(reader) {
			this.submitting = true;
			this.clearStatus();

			if (this.invalidReader) {
				this.error = true;
				return;
			}

			try {
				fetch('http://localhost:8080/create/reader', {
					method: 'POST',
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
					body: new URLSearchParams(reader),
				});

				this.reader.firstName = '';
				this.reader.lastName = '';
				this.error = false;
				this.success = true;
				this.submitting = false;
				this.errorWithServer = false;
			} catch (error) {
				this.errorWithServer = true;
				console.error(error);
			}
		},
		clearStatus() {
			this.success = false;
			this.error = false;
		},
	},
};
</script>
