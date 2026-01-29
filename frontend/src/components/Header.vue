<template>
  <header class="bg-[#97AB94] text-white fixed top-0 left-0 right-0 w-full z-30" >
    <div class="border-b border-white border-opacity-50 hidden md:block ">
      <ul class="max-w-[1746px] mx-auto py-[10px] flex items-center justify-center gap-12 lg:justify-end sm:text-lg w-full font-montserrat font-normal px-4 sm:px-5">
        <li>Вологда</li>
        <li>+7 (817) 393-47-47</li>
        <li>Ежедневно 24/7</li>
      </ul>
    </div>

    <div class="max-w-[1746px] mx-auto py-[10px] flex items-center px-4 sm:px-5"
         :class="{
        'justify-normal': isActive,
        'justify-between': !isActive,
      }">
      <router-link to="/" @click="()=> emit('closeMenu')" class="font-comfort font-normal text-xl sm:text-2xl lg:text-4xl xl:text-5xl"
      :class="{
        'hidden md:block': isActive,
        'block': !isActive,
      }"
      >Фуршет</router-link>

      <!--Поиск     -->
      <div class="relative "
           :class="{
        'hidden md:block ml-[45px]': !isActive,
        'block ml-[0px] md:ml-[45px]': isActive,
      }"
      >
        <div class="relative">
          <input type="text" placeholder="Поиск" autocomplete="off"
                 id="search-input"
                 ref="element"
                 v-model="searchQuery"
                 :class="isActive ? 'w-[295px] md:w-[405px]' : 'w-[255px]'"
                 @click.stop="toggleClass"
                 class="bg-[#B1C0AF] transition-[width] duration-500 ease-in-out outline-none rounded-[10px] md:rounded-[20px] py-[18px] px-[20px] w-[255px] h-[36px] md:h-[60px] font-montserrat placeholder:font-normal text-[21px] placeholder:text-[#ECECEC] text-white font-medium">
          <i class="absolute right-[11px] top-[5px] md:right-[20px] md:top-[17px] bg-[url(/image/icons/Search.svg)] bg-no-repeat w-[22px] h-[22px] md:w-[26px] md:h-[26px]"></i>
        </div>

        <div v-if="searchQuery && isActive" class="absolute top-full mt-2 w-full bg-white text-black rounded-xl shadow-lg z-50 py-[16px] px-[24px]">

          <div v-if="filterProducts.length" class="font-montserrat ">
            <div class="flex w-full items-center justify-between mb-[5px]">
              <p class="text-[14px] font-medium text-[#97AB94] mr-1">Товар</p>
              <span class="border-b-[1px] border-[#97AB94] w-full h-[1px] opacity-50"></span>
            </div>
            <ul class="flex flex-col gap-[5px]" v-auto-animate>
              <router-link
                  v-for="p in filterProducts"
                  :key="p.id"
                  :to="{
                    path: `/menu/${slugify(p.category.name)}/${slugify(p.name)}`
                  }"
                  @click="clearSearch"
                  class="text-[18px] font-medium text-[#BEBEBE] hover:text-[#303030] transition-all duration-300 ease-in-out"
              >
                {{ p.name }}
              </router-link>
            </ul>
          </div>


          <div v-if="filterCategories.length" class="font-montserrat mt-[10px]">
            <div class="flex w-full items-center justify-between mb-[5px]">
              <p class="text-[14px] font-medium text-[#97AB94] mr-1">Категории</p>
              <span class="border-b-[1px] border-[#97AB94] w-full h-[1px] opacity-50"></span>
            </div>
            <ul class="flex gap-[10px]" v-auto-animate>
              <router-link
                  v-for="c in filterCategories"
                  :key="c.id"
                  :to="{
                    path: `/menu/${slugify(c.name)}`
                  }"
                  @click="clearSearch"
                  class="py-[3px] px-[10px] bg-[#EBF0E8]  rounded-[20px] text-[12px] font-medium text-[#97AB94] hover:bg-[#D2D2D2] hover:text-[#595959] transition-all duration-300 ease-in-out"
              >
                {{ c.name }}
              </router-link>
            </ul>
          </div>

          <div :class="searchQuery ? 'border-t-[1px] border-[#97AB94] border-opacity-50' : ''" class="mt-[10px]">
            <ul class="grid grid-rows-2 grid-cols-2 gap-[10px] font-montserrat text-[#BEBEBE] font-medium text-[18px] mt-[10px]">
              <router-link  to="" ><span class="hover:text-[#303030] transition-all duration-300 ease-in-out">Кейтеринг</span></router-link>
              <router-link to="" ><span class="hover:text-[#303030] transition-all duration-300 ease-in-out">Фуршет</span></router-link>
              <router-link to="" ><span class="hover:text-[#303030] transition-all duration-300 ease-in-out">Закуски</span></router-link>
              <router-link to="" ><span class="hover:text-[#303030] transition-all duration-300 ease-in-out">Гастробоксы</span></router-link>
            </ul>
          </div>

        </div>

      </div>

      <!--Список пунктов меню      -->
      <div class="flex flex-1 justify-end items-center gap-[0px] md:gap-4">
        <ul
            :class="isActive ? 'menu-open' : 'menu-close'"
            class="hidden md:block md:flex items-center justify-end gap-2 font-montserrat font-semibold text-sm md:gap-2 md:text-lg lg:text-xl lg:gap-[50px] transition-all duration-500 ease-in-out">
          <router-link to="/about">О нас</router-link>
          <router-link to="/catering">Кейтеринг</router-link>
          <router-link to="/menu">Меню</router-link>
          <router-link to="/delivery">Доставка</router-link>
          <router-link to="/contacts">Контакты</router-link>
        </ul>

        <button @click.stop="toggleClass" class="focus:outline-none md:hidden" :class="{
        'hidden md:block': isActive,
        'block': !isActive,
      }">
          <img src="/image/icons/Search.svg" alt="Search">
        </button>


        <router-link to="/cart"
                     :class="{
        'hidden md:block': isActive,
        'block': !isActive,
         }">
          <img class="mx-[25px]" src="/image/icons/cart.svg" alt="cart">
        </router-link>

        <div class="transition-all duration-200 ease-in-out transform">
          <img v-if="!menuOpen" class="md:hidden" src="/image/icons/burger.svg" alt="Menu1"
               @click="()=> emit('openMenu')">

          <img v-else class="md:hidden" src="/image/icons/closeBurger.svg" alt="Menu2" @click="()=> emit('closeMenu')">
        </div>


        <Button @click="()=> emit('cartOpen')" class="hidden md:block" title="Заказать звонок" />
      </div>
    </div>
  </header>
</template>

<script setup>

import Button from "@/components/ui/Button.vue";
import debounce from 'lodash.debounce'
import {computed, nextTick, onMounted, onUnmounted, ref, watch} from "vue";
import axios from "axios";
import {slugify} from "@/utils/slugify.js";
import {API_URL} from "@/main.js";

defineProps(["menuOpen", "totalPrice"]);
const emit = defineEmits(["openMenu", "closeMenu", "cartOpen"]);

const isActive = ref(false);
const element = ref(null);

// Для поиска
const searchQuery= ref("");
const categories = ref([])
const products = ref([])

const fetchCategories = async () => {
  try {
    const {data} = await axios.get(`${API_URL}/categories`);
    categories.value = data.filter(category => category.active)
  } catch (error) {
    console.log(error)
  }
}

const fetchProducts = async () => {
  try {
    const {data} = await axios.get(`${API_URL}/products`);
    products.value = data.filter(product => product.active)
  } catch (error) {
    console.log(error)
  }
}

const filterCategories = computed(() => {
  if (!searchQuery.value) return [];
  return categories.value.filter(category => category.name.toLowerCase().includes(searchQuery.value.toLowerCase())) || null;
})

const filterProducts = computed(() => {
  if (!searchQuery.value) return [];
  return products.value.filter(product => product.name.toLowerCase().includes(searchQuery.value.toLowerCase())) || null;
})

const clearSearch = () => {
  searchQuery.value = ""
  isActive.value = false
}

const toggleClass = () => {
  isActive.value = !isActive.value;

  if (isActive.value) {
    nextTick(() => {
      element.value?.focus();
    });
  }
}

const handleClickOutside = (event) => {
  if (element.value && !element.value.contains(event.target)) {
    isActive.value = false;
    searchQuery.value = "";
  }

}

onMounted(() => {
  document.addEventListener("click", handleClickOutside);
  fetchCategories();
  fetchProducts();
})

onUnmounted(() => {
  document.removeEventListener("click", handleClickOutside);
})


</script>

<style scoped>
.open-search{
  width: 405px;
}

.close-search{
  width: 255px;
}

.menu-open{
  gap: 20px;
}

.menu-close{
  gap: 50px;
}

.title-close{
  display: none;
}

.title-open{
  display: block;
}
</style>