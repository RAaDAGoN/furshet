import {createApp} from 'vue'
import App from './App.vue'
import {autoAnimatePlugin} from '@formkit/auto-animate/vue'
import Toast from "vue-toastification";
// Import the CSS or use your own!
import "vue-toastification/dist/index.css";

import './assets/main.css'

import {createRouter, createWebHistory} from 'vue-router'
import Main from "@/pages/Main.vue";
import AboutUs from "@/pages/AboutUs.vue";
import Menu from "@/pages/Menu.vue";
import Delivery from "@/pages/Delivery.vue";
import Contacts from "@/pages/Contacts.vue";
import Cart from "@/pages/Cart.vue";
import MenuProduct from "@/pages/MenuProduct.vue";
import Product from "@/pages/Product.vue";
import PageNotFound from "@/pages/PageNotFound.vue";
import Order from "@/pages/Order.vue";
// import Login from "@/pages/back/Login.vue";
import axios from 'axios';

const app = createApp(App)

const routes = [
    { path: '/', component: Main, name: 'Main', meta: { breadcrumb: 'Главная'} },
    { path: '/about', component: AboutUs, name: 'About', meta: { breadcrumb: 'О нас'} },
    { path: '/menu', component: Menu, name: 'Menu', meta: { breadcrumb: 'Меню'} },
    { path: '/delivery', component: Delivery, name: 'Delivery', meta: { breadcrumb: 'Доставка'} },
    { path: '/contacts', component: Contacts, name: 'Contacts', meta: { breadcrumb: 'Контакты'} },
    { path: '/cart', component: Cart, name: 'Cart', meta: { breadcrumb: 'Корзина'} },
    { path: '/cart/order', component: Order, name: 'Order', meta: { breadcrumb: 'Оформление заказа'} },
    // { path: '/Login', component: Login, name: 'Cart', meta: { breadcrumb: 'Корзина'} },
    // { path: '/menu/:id', component: MenuProduct, name: 'ProductLegacy', meta: { breadcrumb: 'Товар'} },


    { path: '/menu/:categorySlug', component: MenuProduct, name: 'MenuProduct', meta: { breadcrumb: 'Категория'} },
    { path: '/menu/:categorySlug/:productSlug', component: Product, name: 'Product', meta: { breadcrumb: 'Товар'} },
    { path: '/:pathMatch(.*)*', component: PageNotFound, name: '404'},
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        return { top: 0 }
    }
})

axios.defaults.baseURL = 'http://144.31.165.137/api';

app.use(router)
app.use(autoAnimatePlugin)
app.use(Toast);

app.mount('#app')
