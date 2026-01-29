<template>
  <div class="max-w-[1746px] mx-auto py-10 px-5">
    <MenuList v-if="!isLoading" :title="title" :categories="sort"/>

    <div v-else class="animate-pulse">
      <div class="flex flex-col md:flex-row md:items-center max-w-[1755px] mx-auto mb-[30px]">
        <div class="h-[50px] md:h-[70px] bg-gray-200 rounded-lg w-[150px] md:w-[200px] mr-7 mb-4 md:mb-0"></div>
        <div class="h-[30px] bg-gray-200 rounded-lg w-[250px]"></div>
      </div>

      <div
          class="grid grid-cols-2 auto-rows-fr md:grid-cols-4 gap-5 md:gap-11 text-base font-comfort font-medium text-white md:text-4xl min-h-[200px]">
        <div v-for="n in 8" :key="n" class="relative">
          <div class="w-full h-[100px] md:h-[200px] bg-gray-200 rounded-2xl"></div>

          <div class="absolute bottom-4 left-4 right-4">
            <div class="h-6 md:h-9 bg-gray-300 rounded-lg mb-2 w-3/4"></div>
            <div class="h-4 md:h-6 bg-gray-300 rounded-lg w-1/2"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

import MenuList from "@/components/main/MenuList.vue";
import axios from 'axios'
import {computed, onMounted, ref} from "vue";
import {slugify} from "@/utils/slugify.js";
import PageNotFound from "@/pages/staticPage/PageNotFound.vue";
import {API_URL} from "@/main.js";

const categories = ref([])

const isLoading = ref(false)

const props = defineProps({
  title: String,
  limit: {
    type: Number,
    default: null
  }
})

// функция получения списка категорий с сервера
const fetchMenu = async () => {
  try {
    isLoading.value = true
    const {data} = await axios.get(`${API_URL}/categories`);

    categories.value = data.filter(category => category.active)

  } catch (error) {
    console.log(error)
  } finally {
    isLoading.value = false
  }
}

// Отсортированные категории с добавлением слага
const sort = computed(() => {
  let result = [...categories.value]
      .sort((a, b) => a.id - b.id)
      .map(category => ({
        ...category,
        slug: slugify(category.name)
      }))

  if (props.limit) {
    result = result.slice(0, props.limit);
  }
  return result
})


onMounted(async () => {
  await fetchMenu()
})
</script>