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
				<button class="button">Add reader</button>
				<p v-if="error && submitting" class="error-message">
					Please fill the form
				</p>
				<p v-if="success" class="success-message">
					Successfully added new reader
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
			return (
				this.reader.firstName.trim() === '' ||
				this.reader.lastName.trim() === ''
			);
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

<style scoped>
div h1 {
	text-align: center;
}

form {
	min-width: 300px;
	max-width: 500px;
	padding: 10px 20px;
	background-color: #e6e2dd;
	margin: 10px auto;
	padding: 20px;
	border-radius: 8px;
}

input {
	display: block;
	margin-bottom: 8px;
	padding: 10px;
}

input,
select {
	background: #f7f7f7;
	border: none;
	border-radius: 4px;
	font-size: 15px;
	margin: 0;
	outline: 0;
	width: 100%;
	box-sizing: border-box;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-webkit-box-shadow: 0 1px 0 rgba(0, 0, 0, 0.03) inset;
	box-shadow: 0 1px 0 rgba(0, 0, 0, 0.03) inset;
	margin-bottom: 30px;
}

select {
	-webkit-appearance: menulist-button;
	height: 35px;
}

.button {
	display: block;
	padding: 15px;
	color: #fff;
	margin: 0 auto;
	background-color: #dbcf26;
	font-size: 18px;
	text-align: center;
	font-style: normal;
	width: 100%;
	border-radius: 10px;
	margin-bottom: 10px;
}

.button:hover {
	background-color: #cabe15;
}
</style>
