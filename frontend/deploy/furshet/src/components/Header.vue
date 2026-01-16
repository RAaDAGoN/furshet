<template>
  <header class="bg-[#97AB94] text-white fixed top-0 left-0 right-0 w-full z-30" >
    <div class="border-b border-white border-opacity-50 hidden md:block ">
      <ul class="max-w-[1746px] mx-auto py-[10px] flex items-center justify-center gap-12 lg:justify-end sm:text-lg w-full font-montserrat font-normal px-4 sm:px-5">
        <li>Вологда</li>
        <li>+7 (817) 393-47-47</li>
        <li>Ежедневно 24/7</li>
      </ul>
    </div>

    <div class="max-w-[1746px] mx-auto py-[10px] flex justify-between items-center px-4 sm:px-5">
      <router-link to="/" @click="()=> emit('closeMenu')" class="font-comfort font-normal text-xl sm:text-2xl lg:text-4xl xl:text-5xl ">Фуршет</router-link>

      <div class="flex flex-1 justify-end items-center gap-4"  v-auto-animate>
        <ul class="hidden md:flex items-center justify-end gap-2 font-montserrat font-semibold text-sm md:gap-2 md:text-lg lg:text-xl lg:gap-6"  v-auto-animate>
          <router-link to="/about">О нас</router-link>
          <router-link to="/menu">Меню</router-link>
          <router-link to="/delivery">Доставка</router-link>
          <router-link to="/contacts">Контакты</router-link>
        </ul>

        <router-link to="/cart" ><img class="mx-[25px]" src="/image/icons/cart.svg" alt="cart"></router-link>

        <div class="transition-all duration-200 ease-in-out transform">
          <img v-if="!menuOpen" class="md:hidden" src="/image/icons/burger.svg" alt="Menu1"
               @click="()=> emit('openMenu')">

          <img v-else class="md:hidden" src="/image/icons/closeBurger.svg" alt="Menu2" @click="()=> emit('closeMenu')">
        </div>


        <Button @click="()=> emit('cartOpen')" class="hidden md:block" title="Заказать звонок"  v-auto-animate/>
      </div>
    </div>
  </header>
</template>

<script setup>

import Button from "@/components/ui/Button.vue";

defineProps(["menuOpen", "totalPrice"]);
const emit = defineEmits(["openMenu", "closeMenu", "cartOpen"]);



</script>