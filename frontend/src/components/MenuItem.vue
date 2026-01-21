<template>

  <router-link v-if="active && !blocked" :to="to" :style="{ backgroundImage: image ? `url('${image}')` : 'url(\'/image/Menu1.png\')' }"
               class="bg-cover flex items-end cursor-pointer relative overflow-hidden group max-h-[405px] h-[213px] md:h-[540px]">
    <!-- Черный градиент (по умолчанию) -->
    <div class="absolute bottom-0 left-0 right-0 h-1/2 bg-gradient-to-t from-black/100 to-transparent opacity-100 group-hover:opacity-0 transition-opacity duration-500"></div>

    <!-- Зеленый градиент (при наведении) -->
    <div class="absolute bottom-0 left-0 right-0 h-1/2 bg-gradient-to-t from-[#97AB94] to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>

    <h1 class="px-3 py-3 md:px-[30px] md:py-[30px] z-10">
      {{title}}
    </h1>
  </router-link>

</template>

<script setup>

import PageNotFound from "@/pages/staticPage/PageNotFound.vue";

defineProps({
  title: String,
  image: String,
  active: Boolean,
  blocked: Boolean,
  to: {
    type: [String, Object],
  }
})
</script>