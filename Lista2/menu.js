const burger = document.querySelector('.burger');
const navigation = document.querySelector('nav');

burger.addEventListener('click', e => {
	burger.classList.toggle('cross');
	navigation.classList.toggle('show');
});
