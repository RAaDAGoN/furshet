<template>
  <OrderCall @cart-close="closeCart" v-if="cartOpen"/>

  <MobileMenu v-if="menuOpen" @closeMenu="closeMenu"/>

  <div class="flex flex-col ">
    <Header :total-price="totalPrice" @cart-open="openCart" :menuOpen="menuOpen" @openMenu="openMenu" @closeMenu="closeMenu"/>


    <div class="min-h-svh">
      <div class="h-[56px] md:h-[135px]"></div>
      <Breadcrumbs />

      <RouterView v-slot="{ Component }">
        <Transition name="page" mode="out-in">
          <component :is="Component" />
        </Transition>
      </RouterView>


    </div>

    <Callback />
    <Footer/>
  </div>
</template>

<script setup>

// функция для открытия корзины (по умолчанию корзина скрыта)
// меняется состояние при клике
import {computed, onMounted, provide, ref, watch} from "vue";
import api from "@/utils/api";
import MobileMenu from "@/components/MobileMenu.vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import OrderCall from "@/components/modal/OrderCall.vue";
import Callback from "@/components/Callback.vue";
import Breadcrumbs from "@/components/Breadcrumbs.vue";

const menuOpen = ref(false)

const orderSuccess = ref(null);

const cartOpen = ref(false)

const cart = ref([]);

// создание заказа и отправка на сервер
const createOrder = async (orderData) => {
  try {
    const orderItems = cart.value.map(item => ({
      productId: item.id,
      quantity: item.quantity || 1
    }));

    const orderRequest = {
      FIO: orderData.FIO || "MyFio",
      phone: orderData.phone || "MyPhone",
      email: orderData.email || "MyEmail",
      delivery: orderData.delivery || "PICKUP",
      city: orderData.city || "MyCity",
      address: orderData.address || "MyAddress",
      comment: orderData.comment || "MyComment",
      paymentMethod: orderData.paymentMethod || "CASH",
      orderItems: orderItems
    };

    const { data } = await api.post("/orders", orderRequest);

    cart.value = [];
    localStorage.removeItem("cart");

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    })

    orderSuccess.value = 'finally';
    return data;
  } catch (error) {
    console.log('Ошибка при создании заказа:', error);
    orderSuccess.value = 'error';
  }
}


const addToCart = (item, count) => {
  const existingItemIndex = cart.value.findIndex(cartItem => cartItem.id === item.id);

  if (existingItemIndex !== -1) {
    // Обновляем количество существующего товара
    cart.value[existingItemIndex].quantity = count;
  } else {
    cart.value.push({
      ...item,
      quantity: count,
      isAdded: true
    });
  }
}

const updateQuantity = (itemId, newQuantity) => {
  const itemIndex = cart.value.findIndex(item => item.id === itemId);
  if (itemIndex !== -1) {
    if (newQuantity > 0) {
      cart.value[itemIndex].quantity = newQuantity;
    } else {
      cart.value.splice(itemIndex, 1);
    }
  }
}

const getQuantity = (itemId) => {
  const item = cart.value.find(item => item.id === itemId)
  return item ? item.quantity : 1;
}

const removeFromCart = (itemId) => {
  const idx = cart.value.findIndex(item => item.id === itemId);
  if (idx !== -1) {
    cart.value.splice(idx, 1);
  }
}

// цена за все товары
const totalPrice = computed(() => {
  return cart.value.reduce((total, item) => total + item.price * item.quantity, 0);
})

const countCart = computed(() => {
  return cart.value.length;
})

const closeMenu = () => {
  menuOpen.value = false
  document.body.style.overflow = "";
}

const openMenu = () => {
  menuOpen.value = true
  document.body.style.overflow = "hidden";
}

// закрыть заявку звонка
const closeCart = () => {
  cartOpen.value = false
  document.body.style.overflow = "";
}

// открыть заявку звонка
const openCart = () => {
  cartOpen.value = true
  document.body.style.overflow = "hidden";
}

watch(cart, ()=>{
  localStorage.setItem("cart", JSON.stringify(cart.value))
}, {deep: true})

provide('menuOpen', menuOpen)

provide('cart', {
  cartOpen,
  closeCart,
  openCart,
  cart,
  addToCart,
  updateQuantity,
  getQuantity,
  removeFromCart,
  totalPrice,
  countCart,
  createOrder,
  orderSuccess
})

onMounted(async () => {
  const localCart = localStorage.getItem('cart')
  cart.value = localCart ? JSON.parse(localCart) : []
})
</script>

<style>
/* Tailwind + CSS для перехода */
.page-enter-active,
.page-leave-active {
  @apply transition duration-500 ease-in-out;
}

.page-enter-from {
  @apply opacity-0 translate-x-10;
}

.page-enter-to {
  @apply opacity-100 translate-x-0;
}

.page-leave-from {
  @apply opacity-100 translate-x-0;
}

.page-leave-to {
  @apply opacity-0 -translate-x-10;
}
</style>