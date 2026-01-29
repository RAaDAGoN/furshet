<template>
  <div v-if="type === 1" class="w-[164px] h-[218px] md:w-[418px] md:h-[557px]  mb-[14px] last:mb-0">
    <img class="w-full h-full rounded-[8px] md:rounded-[20px]" :src="img" alt="img">
  </div>
  <div v-else-if="type === 2" class="w-[164px] h-[162px] md:w-[418px] md:h-[415px] mb-[14px] last:mb-0">
    <img class="w-full h-full rounded-[8px] md:rounded-[20px]" :src="img" alt="img">
  </div>
  <div v-else-if="type === 3" class="w-[164px] h-[108px] md:w-[418px] md:h-[280px] mb-[14px] last:mb-0">
    <img class="w-full h-full rounded-[8px] md:rounded-[20px]" :src="img" alt="img">
  </div>

</template>

<script setup>
defineProps(["type", "img"])


</script>
