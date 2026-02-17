<template>
  <div class="bg-[#97AB94] h-full w-full fixed right-0 top-[52px] lg:top-[80px] z-40 text-white flex flex-col items-center px-5 lg:px-0">

    <ul class="max-w-[1746px] mx-auto flex items-center flex-wrap justify-center gap-[10px] md:gap-[40px] text-[14px] md:text-lg w-full font-montserrat font-normal mt-3">
      <li><a class="flex gap-[15px]" href="https://yandex.ru/maps/-/CPAhzVjR" target="_blank">г. Вологда, ул. Мира 96</a></li>
      <li><a class="flex gap-[15px]" href="tel:89005068151">8 (900) 506-81-51</a></li>
      <li>Круглосуточно</li>
    </ul>

    <ul class="flex flex-col items-center gap-6 text-lg font-montserrat font-semibold lg:gap-12 md:gap-8 mt-12 w-full">
      <router-link to="/about" class="w-full text-center" @click="()=> emit('closeMenu')">О нас</router-link>
      <router-link to="/catering" class="w-full text-center" @click="()=> emit('closeMenu')">Кейтеринг</router-link>
      <router-link to="/menu" class="w-full text-center" @click="()=> emit('closeMenu')">Меню</router-link>
      <router-link to="/seasonal" class="w-full text-center" @click="()=> emit('closeMenu')">Сезонное меню</router-link>
      <router-link to="/delivery" class="w-full text-center" @click="()=> emit('closeMenu')">Доставка</router-link>
      <router-link to="/contacts" class="w-full text-center" @click="()=> emit('closeMenu')">Контакты</router-link>

    </ul>

  </div>
</template>

<script setup>


const emit = defineEmits(["closeMenu"]);
</script>

