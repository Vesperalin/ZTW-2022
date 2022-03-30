<template>
	<div class="content">
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
				<button class="button">Edit reader</button>
			</form>
			<p v-if="success" class="success-message">
				Successfully added new reader
			</p>
			<p v-if="error && submitting" class="error-message">
				Please fill the form correctly
			</p>
			<p v-if="errorWithServer" class="error-message">
				Couldn't load data from server
			</p>
		</div>
		<button class="back-button" @click="getBack()">&larr; Back</button>
	</div>
</template>

<script>
export default {
	name: 'reader-update',
	computed: {
		invalidReader() {
			return (
				this.reader.firstName.trim() === '' &&
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
			} catch (error) {
				this.errorWithServer = true;
				console.error(error);
			}
			this.success = true;
		},
		clearStatus() {
			this.success = false;
			this.error = false;
		},
		getBack() {
			this.$router.push('/readers');
		},
	},
	mounted() {
		this.getReader();
	},
};
</script>

<style scoped>
.content {
	text-align: center;
}

.back-button {
	padding: 10px 15px;
	margin: 10px;
	background-color: #dbcf26;
	color: #f7f7f7;
	border-radius: 10px;
	font-size: 0.75rem;
}

.back-button:hover {
	cursor: pointer;
	background-color: #cabe15;
}

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
