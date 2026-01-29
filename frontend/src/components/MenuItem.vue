<template>
  <router-link
      v-if="active && !blocked"
      :to="to"
      :style="{ backgroundImage: image ? `url('${image}')` : 'url(\'/image/Menu1.png\')' }"
      class="relative block bg-cover overflow-hidden group max-w-[405px] h-[213px] md:h-[540px]"
  >
    <!-- Градиенты -->
    <div class="absolute inset-0">
      <div class="absolute bottom-0 left-0 right-0 h-1/2 bg-gradient-to-t from-black/100 to-transparent opacity-100 group-hover:opacity-0 transition-opacity duration-500"></div>
      <div class="absolute bottom-0 left-0 right-0 h-1/2 bg-gradient-to-t from-[#97AB94] to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
    </div>

    <!-- Основной контейнер -->
    <div class="relative z-10 w-full h-full flex flex-col mt-[6px] md:mt-[15px] ">
      <!-- Метки вверху слева - колонкой -->
      <div class="flex flex-col gap-[4px] md:gap-2 items-start">
        <span
            v-for="m in mark"
            :key="m.id"
            class="inline-block border border-transparent py-[4px] px-[6px] md:py-[7px] md:px-[10px] text-[8px] md:text-[14px] font-montserrat font-normal h-[17px] md:h-full leading-[9px] md:leading-[26px] align-middle whitespace-nowrap"
            :style="{ backgroundColor: '#' + m.label.color }"
        >
          {{ m.label.name }}
        </span>
      </div>

      <!-- Пустое пространство -->
      <div class="flex-grow"></div>

      <!-- Заголовок внизу -->
      <h1 class="text-white text-base md:text-3xl font-bold mt-auto mb-[10px] md:mb-[30px] mx-[10px] md:mx-[30px] ">
        {{ title }}
      </h1>
    </div>
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
  },
  mark: Array,
})
</script>