<template>
  <button
      v-show="isVisible"
      @click="scrollToTop"
      class="group  fixed bottom-6 right-6 w-[105px] h-[105px] bg-[#EBF0E8] hover:bg-[#97AB94] rounded-[50px] duration-300 flex items-center justify-center z-50">
    <svg class="w-[38px] h-[21px] text-[#97AB94] group-hover:text-[#EBF0E8] transition-colors">
      <use href="#scrollToUp"></use>
    </svg>
  </button>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";

const isVisible = ref(false);

const handleScroll = () => {
  isVisible.value = window.scrollY > 200; // показывать после 200px
};

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: "smooth",
  });
};

onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped>
button {
  opacity: 0.8;
}
button:hover {
  opacity: 1;
}
</style>
