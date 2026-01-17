<template>
  <input
      :name="name"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      class="outline-none border-b border-[#D2D2D2] bg-transparent py-3 pl-6 text-black placeholder-[#D2D2D2]/70 font-montserrat md:min-w-[350px]"
      :placeholder="placeholder"
      type="text"
  />
</template>

<script setup>
defineProps({
  name: String,
  placeholder: String,
  modelValue: {
    type: String,
    default: ""
  }
})

defineEmits(['update:modelValue'])
</script>
