<template>
  <OrderCall @cart-close="closeCart" v-if="cartOpen"/>

  <MobileMenu v-if="menuOpen" @closeMenu="closeMenu"/>

  <div class="flex flex-col ">
    <Header :total-price="totalPrice" @cart-open="openCart" :menuOpen="menuOpen" @openMenu="openMenu" @closeMenu="closeMenu"/>


    <div class="min-h-svh">
      <div class="h-[52px] md:h-[56px] lg:h-[80px] xl:h-[128px]"></div>

      <Transition name="breadcrumb" mode="out-in">
        <Breadcrumbs />
      </Transition>



      <div class="relative overflow-hidden">
        <RouterView v-slot="{ Component }">
          <Transition name="page" mode="out-in">
            <component :is="Component" class="w-full" />
          </Transition>
        </RouterView>
      </div>


<!--      <RouterView />-->


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
import ScrollToTop from "@/components/ui/ScrollToTop.vue";

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
.page-wrapper {
  position: relative;
  overflow: hidden;
}

.page-enter-active,
.page-leave-active {
  transition: opacity 0.25s ease, transform 0.25s ease;
}

/* Вход */
.page-enter-from {
  opacity: 0;
  transform: translateY(8px);
}

.page-enter-to {
  opacity: 1;
  transform: translateY(0);
}

/* Выход */
.page-leave-from {
  opacity: 1;
  transform: translateY(0);
}

.page-leave-to {
  opacity: 0;
  transform: translateY(-8px);
}

/* появление */
.breadcrumb-enter-from {
  opacity: 0;
  transform: translateY(-20px);
}

.breadcrumb-enter-active {
  transition: opacity 0.35s ease, transform 0.35s ease;
}

.breadcrumb-enter-to {
  opacity: 1;
  transform: translateY(0);
}

/* исчезновение */
.breadcrumb-leave-from {
  opacity: 1;
  transform: translateY(0);
}

.breadcrumb-leave-active {
  transition: opacity 0.1s ease, transform 0.1s ease;
}

.breadcrumb-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}

</style>