<template>
  <div class="max-w-[1746px] mx-auto pt-5 md:pt-10 px-4 md:px-5">

    <div v-if="isLoading">
      <h1 class="text-lg md:text-xl">Загрузка ...</h1>
    </div>

    <div v-else-if="product" class="flex flex-col lg:flex-row justify-between gap-6 md:gap-11">
      <!-- Галерея -->
      <div class="w-full lg:max-w-[50%]">
        <!-- Основной слайдер -->
        <swiper
            :modules="modules"
            :slides-per-view="1"
            :space-between="20"
            :thumbs="{ swiper: thumbsSwiper }"
            @swiper="setMainSwiper"
            class="main-swiper mb-3 md:mb-4"
        >
          <swiper-slide v-for="(slide, index) in slides" :key="'main-' + index">
            <img class="w-full h-[200px] md:h-[400px] lg:h-[555px] object-cover rounded-lg"
                 :src="slide.text"
                 :alt="'Изображение ' + (index + 1)">
          </swiper-slide>
        </swiper>

        <!-- Слайдер миниатюр -->
        <swiper
            :modules="modules"
            :slides-per-view="4"
            :space-between="10"
            :watch-slides-progress="true"
            @swiper="setThumbsSwiper"
            class="thumbs-swiper hidden md:block"
        >
          <swiper-slide v-for="(slide, index) in slides" :key="'thumb-' + index">
            <img class="thumb-image" :src="slide.text" :alt="'Миниатюра ' + (index + 1)">
          </swiper-slide>
        </swiper>
      </div>

      <div class="w-full flex flex-col justify-between py-4 md:py-8 space-y-4 md:space-y-6">
        <div class="space-y-3 md:space-y-4">
          <h1 class="font-comfort font-bold text-2xl md:text-3xl lg:text-4xl leading-[140%]">{{ product.name }}</h1>

          <p class="flex flex-col gap-1 font-montserrat font-medium text-base md:text-xl lg:text-2xl leading-[140%]">
            <span>{{product.amount}} шт.</span>
            <span>2 вида</span>
          </p>

          <div class="font-comfort leading-[140%] space-y-2">
            <h2 class="font-bold text-xl md:text-2xl lg:text-4xl">Состав:</h2>
            <ul class="font-medium text-base md:text-xl lg:text-2xl space-y-1">
              <li>Бекон с томатами/8 шт</li>
              <li>Курица с грибами/8 шт</li>
            </ul>
          </div>
        </div>

        <div class="space-y-4 md:space-y-6">
          <div class="font-comfort flex flex-col sm:flex-row gap-4 md:gap-[70px] items-start sm:items-center">
            <h2 class="font-bold text-2xl md:text-3xl lg:text-4xl leading-[140%]">{{ product.price }} ₽</h2>
            <Counter v-model="currentCount" :itemId="product.id" />
          </div>

          <div class="flex justify-center sm:justify-start">
            <CartButton v-if="!isInCart"
                        class="w-full sm:max-w-[255px]"
                        title="В корзину"
                        @click="addToCartHandler"
            />

            <CartButton v-else
                        class="w-full sm:max-w-[255px]"
                        title="В корзине"
                        @click="deleteHandler"
            />
          </div>
        </div>
      </div>
    </div>

    <div v-else>
      <h1 class="text-lg md:text-xl text-center">Товар не найден</h1>
    </div>

    <div class="mt-8 md:mt-16 lg:mt-[110px]" v-auto-animate>
      <h1 class="font-comfort font-bold text-2xl md:text-4xl lg:text-6xl text-[#97AB94] uppercase text-center md:text-left">
        Рекомендуемые позиции
      </h1>
      <h2 class="font-comfort font-bold text-sm md:text-base mt-4 md:mt-6 text-center md:text-left">
        Блок скоро появится ...
      </h2>
    </div>

  </div>
</template>

<script setup>
import {Swiper, SwiperSlide} from 'swiper/vue'
import {Thumbs} from 'swiper/modules'
import 'swiper/css'
import 'swiper/css/thumbs'

import {ref, onMounted, watch, inject, computed} from 'vue'
import {useRoute} from 'vue-router'
import axios from 'axios'
import {slugify} from '@/utils/slugify.js'
import Counter from "@/components/ui/Counter.vue";
import CartButton from "@/components/ui/CartButton.vue";

const route = useRoute()
const product = ref(null)
const categoryName = ref('')
const isLoading = ref(false)

const {addToCart, getQuantity, removeFromCart, cart} = inject("cart");

const currentCount = ref(1)

function addToCartHandler() {
  addToCart(product.value, currentCount.value);
}

function deleteHandler() {
  removeFromCart(product.value.id)
  currentCount.value = 1;
}

const isInCart = computed(() => {
  return cart.value.some(item => item.id === product.value?.id);
});

// Swiper Thumbs
const modules = [Thumbs];
const thumbsSwiper = ref(null);
const mainSwiper = ref(null);

const setThumbsSwiper = (swiper) => {
  thumbsSwiper.value = swiper;
};

const setMainSwiper = (swiper) => {
  mainSwiper.value = swiper;
};

const slides = [
  {text: '/image/PMenu.jpg'},
  {text: '/image/Menu2.png'},
  {text: '/image/Menu3.png'},
]

// Получаем информацию о товаре
const fetchProduct = async () => {
  isLoading.value = true

  try {
    const {data: products} = await axios.get('http://localhost:8080/products')
    const foundProduct = products.find(p => {
      const productSlug = slugify(p.name)
      return productSlug === route.params.productSlug
    })

    if (foundProduct) {
      product.value = foundProduct
      const {data: categories} = await axios.get('http://localhost:8080/categories')
      const category = categories.find(c => c.id === foundProduct.category.id)
      categoryName.value = category ? category.name : 'Категория'
    }
  } catch (error) {
    console.error('Ошибка загрузки товара:', error)
  } finally {
    isLoading.value = false
  }
}

// Загружаем данные при монтировании
onMounted(() => {
  fetchProduct()
})

// Следим за изменением параметров маршрута
watch(() => route.params, () => {
  fetchProduct()
})
</script>

<style scoped>
.main-swiper {
  height: 200px;
}

@media (min-width: 768px) {
  .main-swiper {
    height: 400px;
  }
}

@media (min-width: 1024px) {
  .main-swiper {
    height: 555px;
  }
}

/* Слайдер миниатюр */
.thumbs-swiper {
  height: 80px;
  margin-top: 8px;
}

@media (min-width: 768px) {
  .thumbs-swiper {
    height: 100px;
    margin-top: 10px;
  }
}

.thumb-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 6px;
  cursor: pointer;
  opacity: 0.6;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

/* Стиль для активной миниатюры */
:deep(.swiper-slide-thumb-active) .thumb-image {
  opacity: 1;
  border: 2px solid #97AB94;
  transform: scale(1.05);
}

:deep(.swiper-wrapper) {
  align-items: center;
}
</style>