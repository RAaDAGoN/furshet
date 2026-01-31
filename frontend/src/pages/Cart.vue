<template>
  <div class="max-w-[1746px] mx-auto p-5 pt-10">

    <h1 class="font-comfort font-bold text-4xl md:text-[60px] text-[#97AB94] mb-6 md:mb-10">Корзина</h1>

    <div v-if="localOrderSuccess === null">
      <div v-if="cart.length === 0" class="flex flex-col items-center justify-center text-sm md:text-[#3C3C3C]">
        <img class="mb-5" src="/image/CartEmpty.jpg" alt="">

        <h1 class="font-comfort font-bold text-2xl md:text-[35px] mb-4">В корзине пока пусто</h1>

        <p class="font-montserrat font-medium text-base md:text-2xl text-center leading-[130%] md:leading-[140%] mb-10">Вы можете воспользоваться каталогом, чтобы добавить нужную позицию.<br />Желаем приятных покупок!</p>

        <router-link to="/menu" >
          <CartButton title="В меню" />
        </router-link>
      </div>

      <div v-else class="flex flex-col md:flex-row justify-between ">
        <div class="w-full max-w-[1255px] border-t-2 pt-10 border-[#97AB94]">
          <div class="hidden md:grid md:grid-cols-6 gap-4 mb-4 bg-[#EBF0E8] py-[15px] font-montserrat font-medium text-lg">
            <p class="text-left">Ваш заказ</p>
            <p></p>
            <p class="">Цена</p>
            <p class="text-center">Количество</p>
            <p class="text-right">Сумма</p>
            <p></p>
          </div>

          <CartItem
              v-for="item in cart"
              :id="item.id"
              :key="item.id"
              :name="item.name"
              :price="item.price"
              :count="item.count"
          />
        </div>

        <div class="md:ml-12 mt-12 md:mt-0 border border-[#ECEBE4] rounded-[15px] p-[32px]">
          <h1 class="font-comfort font-bold text-16px md:text-[30px] text-[#2E2F2A] mb-3">Оформление заказа</h1>
          <div class="flex flex-col justify-between gap-5 py-5 border-y-[2px] border-[#ECEBE4] font-montserrat mb-10">
            <div class="flex justify-between text-base md:text-[22px]">
              <h2>Товары ({{ countCart }})</h2>
              <p>{{ totalPrice }} ₽</p>
            </div>

            <div class="flex justify-between text-base md:text-[22px]">
              <h2>Скидки</h2>
              <p>-</p>
            </div>
          </div>

          <div class="font-comfort font-bold flex justify-between text-base md:text-[22px] items-center">
            <h2 class="text-base md:text-[25px]">Итого:</h2>
            <p class="text-base md:text-[21px]">{{ totalPrice }} ₽</p>
          </div>
          <p class="font-montserrat font-medium text-xs md:text-lg text-[#929292]">Без учета стоимости доставки</p>

<!--          <CartButton @click="createOrder" class="mx-auto w-full mt-7" title="Оформить заказ"/>-->
          <router-link to="/cart/order" >
            <CartButton class="mx-auto w-full mt-7" title="Перейти к оформлению"/>
          </router-link>


        </div>
      </div>
    </div>



  </div>
</template>

<script setup>
import {computed, inject, onMounted, watch} from "vue";
import CartButton from "@/components/ui/CartButton.vue";
import CartItem from "@/pages/CartItem.vue";
const {cart, totalPrice, countCart, createOrder, orderSuccess} = inject("cart");

const localOrderSuccess = computed(() => orderSuccess.value);

onMounted(() => {
  orderSuccess.value = null;
});
</script>

