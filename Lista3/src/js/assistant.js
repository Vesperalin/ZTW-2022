const assistantButton = document.querySelector('.assistant-button');
const assistantContent = document.querySelector('.assistant-content');

assistantButton.addEventListener('click', e => {
	if (!assistantContent.classList.contains('show')) {
		assistantContent.classList.add('show');
		assistantContent.classList.remove('close');
	} else {
		assistantContent.classList.remove('show');
		assistantContent.classList.add('close');
	}
});
