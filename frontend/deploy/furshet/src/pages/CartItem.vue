<template>
  <div class="hidden md:grid md:grid-cols-4 gap-4 items-center py-4 border-b border-gray-200">
    <div class="flex items-center gap-4">
      <img class="w-[105px] h-[105px]" src="/image/Menu2.png" alt="">
      <h1 class="font-comfort font-bold text-[28px]">{{ name }}</h1>
    </div>

    <div class="text-center">
      <p class="text-[18px] font-medium text-[#929292]">{{ price }} ₽</p>
    </div>

    <div class="text-center">
      <Counter :itemId="id"/>
    </div>

    <div class="flex justify-between items-center gap-4">
      <div class="">
        <h1 class="text-[30px] font-bold text-[#2E2F2A]">{{ itemTotalPrice }} ₽</h1>
      </div>
      <div class="max-w-[35px] max-h-[35px]">
        <img class="cursor-pointer" @click="removeFromCart(id)" src="/image/icons/cartDelete.svg" alt="">
      </div>
    </div>
  </div>

  <div class="md:hidden flex flex-col gap-4 py-4 border-b border-gray-200">
    <div class="flex items-center gap-4">
      <img class="w-20 h-20" src="/image/Menu2.png" alt="">
      <div class="flex-1">
        <h1 class="font-comfort font-bold text-xl">{{ name }}</h1>
        <p class="text-[16px] font-medium text-[#929292]">{{ price }} ₽</p>
      </div>
    </div>

    <div class="flex justify-between items-center">
      <div>
        <p class="text-sm font-medium text-[#929292] mb-1">Количество</p>
        <Counter :itemId="id"/>
      </div>

      <div class="text-center">
        <p class="text-sm font-medium text-[#929292]">Итого:</p>
        <h1 class="text-lg font-bold text-[#2E2F2A]">{{ itemTotalPrice }} ₽</h1>
      </div>

      <div class="w-8 h-8">
        <img class="cursor-pointer" @click="removeFromCart(id)" src="/image/icons/cartDelete.svg" alt="">
      </div>
    </div>
  </div>
</template>

<script setup>
import Counter from "@/components/ui/Counter.vue";
import {computed, inject} from "vue";

const {removeFromCart, cart} = inject("cart");

const props = defineProps({
  id: Number,
  name: String,
  price: Number,
  count: Number,
})

const itemTotalPrice = computed(() => {
  const item = cart.value.find(item => item.id === props.id);
  return item ? item.price * item.quantity : 0;
})
</script>