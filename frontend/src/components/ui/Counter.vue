<template>
  <div class="flex justify-between items-center border-[1.5px] rounded-1 border-[#D2D2D2] py-[15px] px-[12px] md:py-[17px] md:px-[16px] max-h-[48px] w-[100px] md:w-[120px] max-w-[120px]">
    <button @click="increment">
      <img src="/image/icons/PlusActive.svg" alt="">
    </button>

    <p class="font-montserrat font-medium text-[18px] md:text-[21px]">{{ count }}</p>

    <button
        @click="decrement"
        :disabled="count <= 1"
    >
      <img :src="count > 1
        ? '/image/icons/MinusActive.svg'
        : '/image/icons/-.svg' " alt="">
    </button>

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

onMounted(() => {
  if (props.itemId) {
    count.value = getQuantity(props.itemId);
  }
});

watch(() => props.modelValue, (newVal) => {
  if (newVal !== undefined && newVal !== count.value) {
    count.value = newVal;
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