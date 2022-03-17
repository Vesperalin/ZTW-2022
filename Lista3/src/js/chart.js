let busyChart = document.getElementById('bar-chart').getContext('2d');
let barChart = new Chart(busyChart, {
	type: 'bar',
	data: {
		labels: ['Poniedziałek', 'Wtorek', 'Środa', 'Czwartek', 'Piątek'],
		datasets: [
			{
				label: 'Popularne dni',
				data: ['150', '85', '102', '160', '113'],
				backgroundColor: '#2FA4FF',
			},
		],
	},
	options: {
		scales: {
			yAxes: [
				{
					ticks: {
						beginAtZero: true,
					},
				},
			],
		},
	},
});
