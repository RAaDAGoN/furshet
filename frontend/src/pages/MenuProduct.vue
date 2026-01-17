<template>
  <div class="max-w-[1746px] mx-auto pt-10 px-5">
        <div v-if="category" class="mb-8">
          <h1 class="text-3xl font-bold text-[#97AB94]">{{ category.name }}</h1>
        </div>

    <ProductList :products="products" />
  </div>
</template>

<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import ProductList from "@/components/ProductList.vue";
import { slugify } from "@/utils/slugify.js";

const products = ref([])
const category = ref(null)
const isLoading = ref(false)
const error = ref('')
const route = useRoute()

// Функция для получения ID категории по slug
const getCategoryIdBySlug = async (slug) => {
  try {
    const { data } = await axios.get('/categories');

    // Ищем категорию по slug
    const foundCategory = data.find(cat => {
      // Создаем slug из названия категории для сравнения
      const categorySlug = slugify(cat.name);
      return categorySlug === slug;
    });

    return foundCategory ? foundCategory.id : null;
  } catch (err) {
    console.error('Ошибка загрузки категорий:', err);
    return null;
  }
}

// Получаем информацию о категории
const fetchCategory = async (categoryId) => {
  try {
    const { data } = await axios.get('/categories');
    category.value = data.find(cat => cat.id === categoryId);
    if (!category.value) {
      error.value = 'Категория не найдена';
    }
  } catch (err) {
    console.error('Ошибка загрузки категории:', err);
    error.value = 'Ошибка загрузки категории';
  }
}

// Получаем товары категории
const fetchProducts = async (categoryId) => {
  isLoading.value = true;
  error.value = '';

  try {
    const { data } = await axios.get(`/products/category/${categoryId}`);
    products.value = data;
  } catch (err) {
    console.error('Ошибка загрузки товаров:', err);
    error.value = 'Не удалось загрузить товары';
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

    if (categoryId) {
      await Promise.all([
        fetchCategory(categoryId),
        fetchProducts(categoryId)
      ]);
    } else {
      error.value = 'Категория не найдена';
      category.value = null;
      products.value = [];
    }
  } catch (err) {
    console.error('Ошибка загрузки данных:', err);
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