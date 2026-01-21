<template>
  <div class="max-w-[1746px] mx-auto pt-5 md:pt-10 px-4 md:px-5">

    <div v-if="product" class="flex flex-col">
      <div v-if="isLoading">
        <h1 class="text-lg md:text-xl">Загрузка ...</h1>
      </div>
      <!--Фото и цена-->
      <div v-else-if="product" class="flex flex-col lg:flex-row justify-between gap-6 md:gap-11">
        <!--      -->
        <div class="w-full lg:max-w-[50%]">
          <swiper
              v-if="product?.productImages?.length && thumbsSwiper"
              :key="product.id"
              :modules="modules"
              :slides-per-view="1"
              :space-between="20"
              :thumbs="{ swiper: thumbsSwiper }"
              @swiper="setMainSwiper"
              class="main-swiper mb-3 md:mb-4"
          >
            <swiper-slide
                v-for="image in product.productImages"
                :key="image.id"
            >
              <img
                  class="w-full h-[200px] md:h-[400px] lg:h-[555px] object-cover rounded-lg"
                  :src="`${API_URL}/media/${image.filename}`"
                  :alt="image.filename"
              />
            </swiper-slide>
          </swiper>

          <swiper
              v-if="product?.productImages?.length"
              :key="product.id + '-thumbs'"
              :modules="modules"
              :slides-per-view="4"
              :space-between="10"
              :watch-slides-progress="true"
              @swiper="setThumbsSwiper"
              class="thumbs-swiper hidden md:block"
          >
            <swiper-slide
                v-for="image in product.productImages"
                :key="image.id"
            >
              <img
                  class="thumb-image"
                  :src="`${API_URL}/media/${image.filename}`"
                  :alt="image.filename"
              />
            </swiper-slide>
          </swiper>
        </div>


        <div class="w-full flex flex-col justify-between py-4 md:py-8 space-y-4 md:space-y-6">
          <div class="space-y-3 md:space-y-4">
            <h1 class="font-comfort font-bold text-2xl md:text-3xl lg:text-4xl leading-[140%]">{{ product.name }}</h1>

            <p class="flex flex-col gap-1 font-montserrat font-medium text-base md:text-xl lg:text-2xl leading-[140%]">
              <span>{{ product.amount }} шт.</span>
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
              <Counter v-model="currentCount" :itemId="product.id"/>
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

        <!--      -->
      </div>
      <!--Описание-->
      <div class="mt-8 md:mt-16 lg:mt-[110px]">
        <h1 class="font-comfort font-bold text-xl md:2text-xl lg:text-4xl text-[#97AB94] uppercase text-center md:text-left">
          Описание
        </h1>
        <p class="text-lg md:text-xl">Как уже неоднократно упомянуто, диаграммы связей, инициированные исключительно
          синтетически, объединены в целые кластеры себе подобных. Равным образом, существующая теория обеспечивает
          широкому кругу (специалистов) участие в формировании переосмысления внешнеэкономических политик. В целом,
          конечно, укрепление и развитие внутренней структуры представляет собой интересный эксперимент проверки
          укрепления моральных ценностей. Кстати, непосредственные участники технического прогресса освещают чрезвычайно
          интересные особенности картины в целом, однако конкретные выводы, разумеется, описаны максимально подробно.
          Господа, высокое качество позиционных исследований однозначно фиксирует необходимость новых принципов
          формирования материально-технической и кадровой базы. Есть над чем задуматься: интерактивные прототипы
          представляют собой не что иное, как квинтэссенцию победы маркетинга над разумом и должны быть своевременно
          верифицированы.
        </p>
      </div>
      <!--Рекомендации-->
      <div v-if="recommendations && recommendations.length > 0" class="mt-8 md:mt-16 lg:mt-[110px]" v-auto-animate>
        <div v-if="!isLoading">
          <h1 class="font-comfort font-bold text-2xl md:text-4xl lg:text-6xl text-[#97AB94] uppercase text-center md:text-left">
            Рекомендуемые позиции
          </h1>
          <h2 class="font-comfort font-bold text-sm md:text-base mt-4 md:mt-6 text-center md:text-left">
            <ProductList :products="recommendations"/>
          </h2>
        </div>

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
    </div>

    <div v-else>
      <PageNotFound />
    </div>


  </div>


</template>

<script setup>
import { ref, onMounted, watch, inject, computed } from "vue"
import { useRoute } from "vue-router"
import axios from "axios"
import { slugify } from "@/utils/slugify.js"
import Counter from "@/components/ui/Counter.vue"
import CartButton from "@/components/ui/CartButton.vue"
import ProductList from "@/components/ProductList.vue"
import PageNotFound from "@/pages/staticPage/PageNotFound.vue"
import { Swiper, SwiperSlide } from "swiper/vue"
import { Thumbs } from "swiper/modules"
import "swiper/css"
import "swiper/css/thumbs"
import { API_URL } from "@/main.js"

const route = useRoute()
const product = ref(null)
const category = ref(null)
const categoryName = ref("Категория")
const recommendations = ref([])
const isLoading = ref(false)
const categoryId = ref(0)

const { addToCart, removeFromCart, cart } = inject("cart")
const currentCount = ref(1)
const modules = [Thumbs]
const thumbsSwiper = ref(null)
const mainSwiper = ref(null)

const isInCart = computed(() => cart.value.some(item => item.id === product.value?.id))

// ======== SWIPER HANDLERS ========
const setThumbsSwiper = (swiper) => { thumbsSwiper.value = swiper }
const setMainSwiper = (swiper) => { mainSwiper.value = swiper }

// ======== КОРЗИНА ========
const addToCartHandler = () => addToCart(product.value, currentCount.value)
const deleteHandler = () => { removeFromCart(product.value.id); currentCount.value = 1 }

// ======== ФУНКЦИИ ДЛЯ ЗАГРУЗКИ ДАННЫХ ========

// Получаем все продукты и фильтруем по активности
const fetchAllProducts = async () => {
  const { data } = await axios.get(`${API_URL}/products`)
  return data.filter(p => p.active)
}

// Получаем все категории
const fetchAllCategories = async () => {
  const { data } = await axios.get(`${API_URL}/categories`)
  return data
}

// Находим продукт по slug
const findProductBySlug = (products, slug) => {
  return products.find(p => slugify(p.name) === slug)
}

// Находим категорию по id
const findCategoryById = (categories, id) => {
  return categories.find(c => c.id === id && c.active)
}

// Получаем рекомендации (активные продукты категории, кроме текущего)
const fetchRecommendations = async (catId, excludeProductId) => {
  try {
    const { data } = await axios.get(`${API_URL}/products/category/${catId}`)
    let rec = data || []
    rec = rec.filter(p => p.id !== excludeProductId)
    // случайная сортировка
    for (let i = rec.length - 1; i >= 0; i--) {
      const j = Math.floor(Math.random() * rec.length)
      ;[rec[i], rec[j]] = [rec[j], rec[i]]
    }
    return rec.slice(0, 4)
  } catch {
    return []
  }
}

// Основная функция загрузки продукта и рекомендаций
const loadData = async () => {
  isLoading.value = true
  try {
    const slug = route.params.productSlug

    const allProducts = await fetchAllProducts()
    const foundProduct = findProductBySlug(allProducts, slug)

    if (!foundProduct) {
      product.value = null
      category.value = null
      recommendations.value = []
      categoryName.value = "Категория"
      return
    }

    const allCategories = await fetchAllCategories()
    const foundCategory = findCategoryById(allCategories, foundProduct.category?.id)

    if (!foundCategory) {
      // Если категория неактивна — показываем PageNotFound
      product.value = null
      category.value = null
      recommendations.value = []
      categoryName.value = "Категория"
      return
    }

    product.value = foundProduct
    category.value = foundCategory
    categoryId.value = foundCategory.id
    categoryName.value = foundCategory.name

    thumbsSwiper.value = null
    mainSwiper.value = null

    // Загружаем рекомендации
    recommendations.value = await fetchRecommendations(categoryId.value, product.value.id)
  } catch (err) {
    console.error("Ошибка загрузки товара:", err)
    product.value = null
    category.value = null
    recommendations.value = []
    categoryName.value = "Категория"
  } finally {
    isLoading.value = false
  }
}


onMounted(loadData)
watch(() => route.params.productSlug, loadData)
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