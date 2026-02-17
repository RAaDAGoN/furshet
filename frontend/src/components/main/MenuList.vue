<template>
  <div class="max-w-[1746px] mx-auto px-5">
    <div v-if="categories.length > 0" class="">
      <div  class="flex flex-col md:flex-row md:items-center mx-auto my-[10px] md:my-[30px]">
        <h1 class="text-[#97AB94] font-comfort text-[28px] md:text-5xl mr-7 leading-[130%]">МЕНЮ</h1>
        <p class="font-montserrat text-[#3C3C3C] font-medium text-xl md:text-xl leading-[130%]">{{ title }}</p>
      </div>

      <div class="grid grid-cols-2 auto-rows-fr md:grid-cols-4 gap-5 md:gap-11 text-base font-comfort font-medium text-white md:text-4xl min-h-[200px]">
        <MenuItem  v-for="category in categories"
                   :key="category.id"
                   :title="category.name"
                   :active="category.active"
                   :image="category.filename?.length ? `${API_URL}/media/${category.filename}` : null"
                   :to="{name: 'MenuProduct', params: {categorySlug: category.slug}}"
                   :categoryId='category.id'
        />
      </div>
    </div>

    <PageNotFound v-else />

  </div>
</template>

<script setup>
import MenuItem from "@/components/MenuItem.vue";
import {API_URL} from "@/main.js";
import PageNotFound from "@/pages/staticPage/PageNotFound.vue";

defineProps({
      title: String,
      categories: Array,
    },
)
</script>