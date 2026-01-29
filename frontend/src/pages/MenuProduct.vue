<template>
  <div class="max-w-[1746px] mx-auto pt-10 px-5">

    <div v-if="category && category.active" class="mb-8">
      <h1 class="text-3xl font-bold text-[#97AB94]">{{ category.name }}</h1>

      <ProductList :products="products"/>
    </div>

    <div v-else>
      <PageNotFound />
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import ProductList from "@/components/ProductList.vue";
import {slugify} from "@/utils/slugify.js";
import PageNotFound from "@/pages/staticPage/PageNotFound.vue";
import {API_URL} from "@/main.js";

const products = ref([])
const category = ref(null)
const isLoading = ref(false)
const error = ref('')
const route = useRoute()

// Функция для получения ID категории по slug
const getCategoryIdBySlug = async (slug) => {
  try {
    const {data} = await axios.get(`${API_URL}/categories`);

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
    const {data} = await axios.get(`${API_URL}/categories`);
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
    const {data} = await axios.get(`${API_URL}/products/category/${categoryId}`);
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

    await fetchProducts(categoryId);
  } catch (err) {
    error.value = 'Ошибка загрузки данных';
  } finally {
    isLoading.value = false;
  }
}

// Загружаем данные при монтировании
onMounted(async () => {
  await loadData(route.params.categorySlug);
})

// Следим за изменением slug в URL
watch(
    () => route.params.categorySlug,
    async (newSlug) => {
      await loadData(newSlug);
    }
)
</script>