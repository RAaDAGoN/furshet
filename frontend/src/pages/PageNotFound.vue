<template>
  <div class="min-h-[100svh] flex items-center justify-center ">
    <div class="flex flex-col items-center justify-center text-sm md:text-[#3C3C3C]">
      <img class="mb-5" src="/image/404.jpg" alt="">

      <h1 class="font-comfort font-bold text-2xl md:text-[35px] mb-4"></h1>

      <p class="font-montserrat font-medium text-base md:text-2xl text-center leading-[130%] md:leading-[140%] mb-10">Возможно данной страницы не существует или она была удалена.
        Попробуйте вернуться на главную страницу!</p>

      <router-link to="/" >
        <CartButton title="На главную" />
      </router-link>

    </div>
  </div>
</template>

<script setup>

import CartButton from "@/components/ui/CartButton.vue";
</script>
