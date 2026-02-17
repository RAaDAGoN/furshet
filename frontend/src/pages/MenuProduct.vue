<template>
  <div class="max-w-[1746px] mx-auto px-5 lg:px-0">

    <div v-if="category && category.active" class="mb-8">
      <h1 class="font-comfort text-3xl font-bold uppercase text-[#97AB94] my-[20px] md:my-[30px]">{{ category.name }}</h1>

      <ProductList :products="products"/>
    </div>

  </div>
</template>

<script setup>
import {onMounted, ref, watch} from "vue";

import {useRoute} from "vue-router";
import ProductList from "@/components/ProductList.vue";
import {slugify} from "@/utils/slugify.js";
import PageNotFound from "@/pages/staticPage/PageNotFound.vue";
import api from "@/utils/api";
import { useBreadcrumbsStore } from "@/components/stores/breadcrumbs.js";

const products = ref([])
const category = ref(null)
const isLoading = ref(false)
const error = ref('')
const route = useRoute()
const bcStore = useBreadcrumbsStore();

// Функция для получения ID категории по slug
const getCategoryIdBySlug = async (slug) => {
  try {
    const {data} = await api.get("/categories");

    // Ищем категорию по slug
    const foundCategory = data.find(cat => {
      // Создаем slug из названия категории для сравнения
      const categorySlug = slugify(cat.name);
      return categorySlug === slug;
    });

    return foundCategory ? foundCategory.id : null;
  } catch (err) {
    return null;
  }
}

// Получаем информацию о категории
const fetchCategory = async (categoryId) => {
  try {
    const {data} = await api.get("/categories");
    category.value = data.find(cat => cat.id === categoryId);

  } catch (err) {
    error.value = 'Ошибка загрузки категории';
  }
}

// Получаем товары категории
const fetchProducts = async (categoryId) => {
  isLoading.value = true;
  error.value = '';

  try {
    const {data} = await api.get(`/products/category/${categoryId}`);
    products.value = data;
  } catch (err) {
    products.value = [];
    category.value = null;
  } finally {
    isLoading.value = false;
  }
}

// Основная функция загрузки данных
const loadData = async (slug) => {
  isLoading.value = true;

  try {
    // Получаем ID категории по slug
    const categoryId = await getCategoryIdBySlug(slug);

    if (!categoryId) {
      category.value = null;
      products.value = [];
      return;
    }

    await fetchCategory(categoryId);

    if (!category.value.active) {
      products.value = []
      return;
    }

    bcStore.setCategory(category.value);
    bcStore.setProduct(null);

    await fetchProducts(categoryId);
  } catch (err) {
    error.value = 'Ошибка загрузки данных';
  } finally {
    isLoading.value = false;
  }
}


// Загружаем данные при монтировании
onMounted(async ()=>{
  await loadData(route.params.categorySlug)
})

// Следим за изменением slug в URL
watch(
    () => route.params.categorySlug,
    async (newSlug) => {
      await loadData(newSlug);
    }
)
</script>