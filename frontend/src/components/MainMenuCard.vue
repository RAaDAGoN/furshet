<template>
  <div
      class="absolute inset-0 bg-cover bg-center"
      :style="{
                backgroundImage: filename
                  ? `url('${API_URL}/media/${filename}')`
                  : `url('/image/NotFound.svg')`,
                ...(!filename && {
                  backgroundSize: '30%',
                  backgroundRepeat: 'no-repeat',
                  backgroundColor: '#ECEFEB'
                })
              }">

  </div>
</template>

<script setup>

import {API_URL} from "@/main.js";

defineProps({
  filename: String,
})
</script>
