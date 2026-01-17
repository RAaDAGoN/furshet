<template>
  <OrderCall @cart-close="closeCart" v-if="cartOpen"/>

  <MobileMenu v-if="menuOpen" @closeMenu="closeMenu"/>

  <div class="flex flex-col ">
    <Header :total-price="totalPrice" @cart-open="openCart" :menuOpen="menuOpen" @openMenu="openMenu" @closeMenu="closeMenu"/>

    <div v-auto-animate class="min-h-svh">
      <div class="h-[35px] md:h-[135px]"></div>
      <router-view ></router-view>
    </div>

    <Callback />
    <Footer/>
  </div>
</template>

<script setup>

// функция для открытия корзины (по умолчанию корзина скрыта)
// меняется состояние при клике
import {computed, onMounted, provide, ref, watch} from "vue";
import MobileMenu from "@/components/MobileMenu.vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import OrderCall from "@/components/modal/OrderCall.vue";
import axios from "axios";
import Callback from "@/components/Callback.vue";

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

    const { data } = await axios.post('/orders', orderRequest);

    cart.value = [];
    localStorage.removeItem("cart");

    console.log(data);
    orderSuccess.value = 'finally';
    return data;
  } catch (error) {
    console.log('Ошибка при создании заказа:', error);
    orderSuccess.value = 'error';
    throw error;
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
}

const openMenu = () => {
  menuOpen.value = true
}

// закрыть заявку звонка
const closeCart = () => {
  cartOpen.value = false
  console.log(2)
}

// открыть заявку звонка
const openCart = () => {
  cartOpen.value = true
  console.log(1)
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