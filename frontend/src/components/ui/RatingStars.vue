<script setup>
import { computed } from 'vue'

const props = defineProps({
  rating: {
    type: Number,
    required: true
  }
})

// Нормализуем рейтинг до шага 0.5
const normalizedRating = computed(() => {
  return Math.round(props.rating * 2) / 2
})

// Массив значений для каждой звезды (0, 0.5 или 1)
const stars = computed(() => {
  const rating = normalizedRating.value
  return Array.from({ length: 5 }, (_, i) => {
    const starIndex = i + 1

    if (rating >= starIndex) {
      return 1
    } else if (rating >= starIndex - 0.5) {
      return 0.5
    } else {
      return 0
    }
  })
})
</script>

<template>
  <div class="flex items-center gap-1">
    <div
        v-for="(starValue, index) in stars"
        :key="index"
        class="relative w-5 h-5"
    >
      <svg class="absolute inset-0 w-5 h-5 text-[#FFB800] fill-none">
        <use href="#starRating" />
      </svg>

      <svg
          class="absolute inset-0 w-5 h-5 text-[#FFB800] fill-[#FFB800]"
          :style="{
          clipPath: starValue === 0.5
            ? 'inset(0 50% 0 0)'
            : starValue === 1
              ? 'inset(0 0 0 0)'
              : 'inset(0 100% 0 0)'
        }"
      >
        <use href="#starRating" />
      </svg>
    </div>

  </div>
</template>