import {createApp} from 'vue'
import App from './App.vue'
import {autoAnimatePlugin} from '@formkit/auto-animate/vue'
import Toast from "vue-toastification";

import "vue-toastification/dist/index.css";

import './assets/main.css'

import {createRouter, createWebHistory} from 'vue-router'
import Main from "@/pages/Main.vue";
import AboutUs from "@/pages/staticPage/AboutUs.vue";
import Delivery from "@/pages/staticPage/Delivery.vue";
import Contacts from "@/pages/staticPage/Contacts.vue";
import Cart from "@/pages/Cart.vue";
import MenuProduct from "@/pages/MenuProduct.vue";
import Product from "@/pages/Product.vue";
import PageNotFound from "@/pages/staticPage/PageNotFound.vue";
import { createPinia } from "pinia";
import Order from "@/pages/Order.vue";
import axios from 'axios';
import Catering from "@/pages/staticPage/Catering.vue";
import {slugify} from "@/utils/slugify.js";
import api from "@/utils/api.js";
import AllMenu from "@/pages/AllMenu.vue";
import Feedbacks from "@/pages/Feedbacks.vue";

const app = createApp(App)

const routes = [
    { path: '/', component: Main, name: 'Main', meta: { breadcrumb: 'Главная'} },
    { path: '/about', component: AboutUs, name: 'About', meta: { breadcrumb: 'О нас'} },
    // { path: '/menu', component: Menu, name: 'Menu', meta: { breadcrumb: 'Меню'} },
    { path: '/menu', component: AllMenu, name: 'Menu', meta: { breadcrumb: 'Меню'} },
    { path: '/seasonal', component: AllMenu, name: 'Seasonal', meta: { breadcrumb: 'Сезонное меню'} },
    { path: '/delivery', component: Delivery, name: 'Delivery', meta: { breadcrumb: 'Доставка'} },
    { path: '/contacts', component: Contacts, name: 'Contacts', meta: { breadcrumb: 'Контакты'} },
    { path: '/cart', component: Cart, name: 'Cart', meta: { breadcrumb: 'Корзина'} },
    { path: '/cart/order', component: Order, name: 'Order', meta: { breadcrumb: 'Оформление заказа'} },
    { path: '/catering', component: Catering, name: 'Catering', meta: { breadcrumb: 'Кейтеринг' } },
    { path: '/feedbacks', component: Feedbacks, name: 'Feedback', meta: { breadcrumb: 'Отзывы' } },


    { path: '/menu/:categorySlug', component: MenuProduct, name: 'MenuProduct' },
    { path: '/menu/:categorySlug/:productSlug', component: Product, name: 'Product' },
    { path: '/:pathMatch(.*)*', component: PageNotFound, name: '404'},
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        return { top: 0 }
    }
})

// Функция для поиска категории по slug
async function getCategoryNameBySlug(slug) {
    try {
        const { data } = await api.get("/categories");

        const foundCategory = data.find(cat => slugify(cat.name) === slug);

        return foundCategory ? foundCategory.name : slug; // если не нашли, оставляем слаг
    } catch (err) {
        console.error(err);
        return slug;
    }
}

async function getProductNameBySlug(slug) {
    try {
        const { data } = await api.get("/products");

        const foundProduct = data.find(
            product => slugify(product.name) === slug
        );

        return foundProduct ? foundProduct.name : slug;
    } catch (err) {
        console.error(err);
        return slug;
    }
}

// Перед каждой навигацией
router.beforeEach(async (to, from, next) => {
    // 1. Страница продукта
    if (to.params.productSlug) {
        const productName = await getProductNameBySlug(to.params.productSlug);
        document.title = `${productName} | Фуршет`;
    }
    // 2. Страница категории
    else if (to.params.categorySlug) {
        const categoryName = await getCategoryNameBySlug(to.params.categorySlug);
        document.title = `${categoryName} | Фуршет`;
    }
    // 3. Обычные страницы
    else {
        document.title = `${to.meta.breadcrumb || ''} | Фуршет`;
    }

    next();
});

// axios.defaults.baseURL = 'http://144.31.165.137/api';
axios.defaults.baseURL = 'http://localhost:8080';

const pinia = createPinia();

app.use(pinia);
app.use(router)
app.use(Toast);

app.mount('#app')

axios.defaults.withCredentials = true
axios.defaults.xsrfCookieName = 'XSRF-TOKEN'
axios.defaults.xsrfHeaderName = 'X-CSRF-TOKEN'

export const API_URL = import.meta.env.VITE_API_URL;