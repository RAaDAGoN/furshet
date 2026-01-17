<template>
  <div class="bg-[#97AB94] h-full w-full fixed right-0 top-14 z-50  text-white flex flex-col items-center">

    <ul class="max-w-[1746px] mx-auto flex items-center justify-center gap-9 text-[12px] md:text-lg w-full font-montserrat font-normal mt-3">
      <li>Вологда</li>
      <li>+7 (817) 393-47-47</li>
      <li>Ежедневно 24/7</li>
    </ul>

    <ul class="flex flex-col items-center gap-6 text-lg font-montserrat font-semibold lg:gap-12 md:gap-8 mt-12 ">
      <router-link to="/about" @click="()=> emit('closeMenu')">О нас</router-link>
      <router-link to="/menu" @click="()=> emit('closeMenu')">Меню</router-link>
      <router-link to="/delivery" @click="()=> emit('closeMenu')">Доставка</router-link>
      <router-link to="/contacts" @click="()=> emit('closeMenu')">Контакты</router-link>
    </ul>

  </div>
</template>

<script setup>


const emit = defineEmits(["closeMenu"]);
</script>

