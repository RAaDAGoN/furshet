<template>
  <div class="flex justify-between items-center border-[1.5px] rounded-1 border-[#D2D2D2] py-[15px] px-[17px] md:py-3 md:px-[25px] max-h-[56px]  md:w-[140px] max-w-[140px]">
    <button @click="increment"><img src="/image/icons/+.svg" alt=""></button>
    <p class="text-lg md:text-2xl mx-5">{{ count }}</p>
    <button v-if="count < 1" @click="decrement"><img src="/image/icons/-.svg" alt=""></button>
    <button v-else @click="decrement"><img src="/image/icons/-.svg" alt=""></button>
  </div>
</template>

<script setup>

import {ref, watch, inject, onMounted} from "vue";

const props = defineProps({
  modelValue: {
    type: Number,
    default: 1
  },
  itemId: Number,
});

const { updateQuantity, getQuantity } = inject("cart");

const emit = defineEmits(['update:modelValue'])

const count = ref(1);

// Загружаем количество из корзины при монтировании
onMounted(() => {
  if (props.itemId) {
    count.value = getQuantity(props.itemId);
  }
});

watch(() => props.modelValue, (newVal) => {
  if (newVal !== undefined && newVal !== count.value) {
    count.value = newVal; // синхронизируем локальный count с родителем
  }
});

const increment = () => {
  count.value++
  emit('update:modelValue', props.modelValue + 1)
  updateQuantity(props.itemId, count.value);
}

const decrement = () => {
  if (count.value > 1) {
    count.value--;
    emit('update:modelValue', props.modelValue - 1)
    updateQuantity(props.itemId, count.value);
  }
}
</script>