<template>
  <button
      class="font-montserrat font-semibold cursor-pointer
           px-6 py-3
           text-sm sm:text-xs md:text-2xl
           md:px-1 md:py-4 lg:px-8
           border-[2px] overflow-hidden relative
           transition-colors duration-300
           hover:bg-transparent
           hover:text-[#97AB94]
"
      :class="inCart
      ? 'bg-[#97AB94] text-white border-[#97AB94]'
      : 'bg-none text-[#97AB94] border-[#97AB94]'"
  >
    <span class="relative flex items-center justify-center">
      <img
          v-if="!inCart && product"
          src="/image/CheckMark.svg"
          alt=""
          class="absolute left-[40px] lg:left-0 w-5 h-5"
      />
      <span
          class="transition-transform duration-300"
          :class="!inCart && product ? 'translate-x-3' : 'translate-x-0'"
      >
        {{ title }}
      </span>
    </span>
  </button>
</template>


<script setup>
defineProps({
  title: String,
  inCart: Boolean,
  product: Boolean,
})
</script>



