const assistantButton = document.querySelector('.assistant-button');
const assistantContent = document.querySelector('.assistant-content');

assistantButton.addEventListener('click', e => {
	assistantContent.classList.toggle('show');
});
