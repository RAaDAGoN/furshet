<template>
  <button class="bg-[#97AB94] text-white font-montserrat font-semibold cursor-pointer
      px-6 py-3
      text-sm
      sm:text-xs
      md:text-2xl
      md:px-1 md:py-4
      lg:px-8
      border-[1px]

">
    {{ title }}
  </button>
</template>

<script setup>
defineProps({
  title: String,
})
</script>

