<template>
  <div v-if="type === 1" class="w-full h-full mb-[14px] last:mb-0">
    <img class="w-full h-full rounded-[8px] md:rounded-[20px]" :src="img" alt="img">
  </div>
  <div v-else-if="type === 2" class="w-full h-full mb-[14px] last:mb-0">
    <img class="w-full h-full rounded-[8px] md:rounded-[20px]" :src="img" alt="img">
  </div>
  <div v-else-if="type === 3" class="w-full h-full mb-[14px] last:mb-0">
    <img class="w-full h-full rounded-[8px] md:rounded-[20px]" :src="img" alt="img">
  </div>

</template>

<script setup>
defineProps(["type", "img"])


</script>
