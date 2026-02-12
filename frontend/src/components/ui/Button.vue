<template>
  <button class="bg-white text-[#97AB94] font-montserrat font-semibold cursor-pointer
      px-6 py-3
      text-sm
      sm:text-xs
      md:text-[16px]
      lg:text-[18px]
      2xl:text-[24px]
      md:px-5 md:py-4
      2xl:px-8
      border-[1px]
      border-white
      hover:bg-transparent
      hover:text-white
      transition
">
    {{ title }}
  </button>
</template>

<script setup>
defineProps({
  title: String,
})
</script>

