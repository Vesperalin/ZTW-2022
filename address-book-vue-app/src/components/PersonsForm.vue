<template>
	<div id="person-form">
		<form @submit.prevent="handleSubmit">
			<!-- wiązemy akcję formularza z naszą metodą-->
			<label>Imię i nazwisko</label>
			<!-- :class nada klasę has error korzystając z conditional renderingu
            na focusie i keypress czyścimy błędy, żeby user sobie spokojnie wpisywał nowe dane
      -->
			<input
				v-model="person.name"
				type="text"
				:class="{ 'has-error': submitting && invalidName }"
				@focus="clearStatus"
				@keypress="clearStatus"
			/>
			<!-- v-model łączy inputy ze stanem w data-->
			<label>Email</label>
			<input
				v-model="person.email"
				type="text"
				:class="{ 'has-error': submitting && invalidEmail }"
				@focus="clearStatus"
				@keypress="clearStatus"
			/>
			<label>Telefon</label>
			<input
				v-model="person.phone"
				type="text"
				:class="{ 'has-error': submitting && invalidPhone }"
				@focus="clearStatus"
				@keypress="clearStatus"
			/>
			<!-- v-if umożliwia conditional rendering -->
			<p v-if="error && submitting" class="error-message">
				Proszę wypełnić wskazane pola formularza
			</p>
			<p v-if="success" class="success-message">Dane poprawnie zapisano</p>

			<button>Dodaj kontakt</button>
		</form>
	</div>
</template>

<script>
export default {
	name: 'person-form',
	data() {
		return {
			submitting: false,
			error: false,
			success: false,

			person: {
				name: '',
				email: '',
				phone: '',
			},
		};
	},
	methods: {
		handleSubmit() {
			this.submitting = true;
			this.clearStatus();

			//check form fields
			if (this.invalidName || this.invalidEmail || this.invalidPhone) {
				this.error = true;
				return;
			}

			this.$emit('add:person', this.person); //wyśle dane do komponentu nadrzędnego: dane i nazwa eventu

			//clear form fields
			this.person = {
				name: '',
				email: '',
				phone: '',
			};

			this.error = false;
			this.success = true;
			this.submitting = false;
		},
		clearStatus() {
			this.success = false;
			this.error = false;
		},
	},
	computed: {
		invalidName() {
			return this.person.name === '';
		},
		invalidEmail() {
			return this.person.email === '';
		},
		invalidPhone() {
			return this.person.phone === '';
		},
	},
};
</script>

<style scoped>
form {
	margin-bottom: 2rem;
}
form {
	margin-bottom: 2rem;
}

[class*='-message'] {
	font-weight: 500;
}
.error-message {
	color: #d33c40;
}
.success-message {
	color: #32a95d;
}
</style>
