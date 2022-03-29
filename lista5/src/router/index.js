import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../components/HomeView.vue';

const routes = [
	{
		path: '/',
		name: 'home',
		component: HomeView,
	},
	{
		path: '/books',
		name: 'books',
		component: () => import('@/components/books/TableBook.vue'),
	},
	{
		path: '/books/update/:id',
		name: 'book-update',
		component: () => import('@/components/books/BookUpdate.vue'),
	},
	{
		path: '/books/add',
		name: 'book-add',
		component: () => import('@/components/books/BookAdd.vue'),
	},
	{
		path: '/books/book/:id',
		name: 'book-book',
		component: () => import('@/components/books/BookPreview.vue'),
	},
	{
		path: '/authors',
		name: 'authors',
		component: () => import('@/components/authors/TableAuthor.vue'),
	},
	{
		path: '/authors/author/:id',
		name: 'authors-author',
		component: () => import('@/components/authors/AuthorPreview.vue'),
	},
	{
		path: '/authors/update/:id',
		name: 'author-update',
		component: () => import('@/components/authors/AuthorUpdate.vue'),
	},
	{
		path: '/authors/add',
		name: 'author-add',
		component: () => import('@/components/authors/AuthorAdd.vue'),
	},
	{
		path: '/readers',
		name: 'readers',
		component: () => import('@/components/readers/TableReader.vue'),
	},
	{
		path: '/readers/update/:id',
		name: 'reader-update',
		component: () => import('@/components/readers/ReaderUpdate.vue'),
	},
	{
		path: '/readers/add',
		name: 'reader-add',
		component: () => import('@/components/readers/ReaderAdd.vue'),
	},
	{
		path: '/readers/reader/:id',
		name: 'readers-reader',
		component: () => import('@/components/readers/ReaderPreview.vue'),
	},
	{
		path: '/rentals',
		name: 'rentals',
		component: () => import('@/components/rentals/TableRental.vue'),
	},
	{
		path: '/rentals/update/:id',
		name: 'rental-update',
		component: () => import('@/components/rentals/RentalUpdate.vue'),
	},
	{
		path: '/rentals/add',
		name: 'rental-add',
		component: () => import('@/components/rentals/RentalAdd.vue'),
	},
	{
		path: '/rentals/rental/:id',
		name: 'rental-rental',
		component: () => import('@/components/rentals/RentalPreview.vue'),
	},
];

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes,
});

export default router;
