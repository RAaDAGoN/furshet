<template>
  <header :class="[
      'bg-[#97AB94] text-white fixed top-0 left-0 right-0 w-full z-30 transition-transform duration-300 ease-in-out',
      isHeaderHidden ? '-translate-y-full lg:translate-y-0' : 'translate-y-0'
    ]"
  >
    <div class="border-b border-white border-opacity-50 hidden xl:block ">
      <ul class="max-w-[1746px] mx-auto py-[10px] flex items-center justify-center gap-12 xl:justify-end sm:text-lg w-full font-montserrat font-normal px-5 ">
        <li><a class="flex gap-[15px]" href="https://yandex.ru/maps/-/CPAhzVjR" target="_blank">г. Вологда, ул. Мира
          96</a></li>
        <li><a class="flex gap-[15px]" href="tel:89005068151">8 (900) 506-81-51</a></li>
        <li>Круглосуточно</li>
      </ul>
    </div>

    <div class="max-w-[1746px] mx-auto py-[10px] flex items-center justify-between px-5 2xl:px-0">

      <div class="flex items-center">
        <router-link to="/" @click="()=> emit('closeMenu')"
                     class="font-comfort font-normal text-xl sm:text-2xl lg:text-4xl xl:text-[42px]"
                     :class="{
        'hidden md:block': isActive,
        'block': !isActive,
      }">
          Фуршет
        </router-link>

        <!--Поиск     -->
        <div class="relative "
             :class="{
        'hidden lg:block ml-[20px] 2xl:ml-[45px]': !isActive,
        'block ml-[20px] 2xl:ml-[45px]': isActive,
      }">
          <div class="relative">
            <input type="text" placeholder="Поиск" autocomplete="off"
                   id="search-input"
                   ref="element"
                   v-model="searchQuery"
                   :class="isActive ? 'w-[215px] sm:w-[315px] md:w-[405px]' : 'w-[255px]'"
                   @click.stop="toggleClass"
                   class="bg-[#B1C0AF] transition-[width] duration-500 ease-in-out outline-none rounded-[10px] lg:rounded-[20px] py-[18px] px-[20px] w-[255px] h-[36px] lg:h-[60px] font-montserrat placeholder:font-normal text-[21px] placeholder:text-[#ECECEC] text-white font-medium">
            <i class="absolute right-[11px] top-[5px] md:right-[20px] lg:top-[17px] bg-[url(/image/icons/Search.svg)] bg-no-repeat w-[22px] h-[22px] md:w-[26px] md:h-[26px]"></i>
          </div>

          <div v-if="searchQuery && isActive"
               class="absolute top-full mt-2 w-full bg-white text-black rounded-xl shadow-lg z-50 py-[16px] px-[24px]">

            <div v-if="filterProducts.length" class="font-montserrat ">
              <div class="flex w-full items-center justify-between mb-[5px]">
                <p class="text-[14px] font-medium text-[#97AB94] mr-1">Товар</p>
                <span class="border-b-[1px] border-[#97AB94] w-full h-[1px] opacity-50"></span>
              </div>
              <ul class="flex flex-col gap-[5px]">
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
              <ul class="flex gap-[10px] flex-wrap">
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
              <ul class="grid grid-rows-1 grid-cols-1 md:grid-rows-2 md:grid-cols-2 gap-[10px] font-montserrat text-[#BEBEBE] font-medium text-[18px] mt-[10px]">
                <router-link to="/catering">
                  <span class="hover:text-[#303030] transition-all duration-300 ease-in-out">Кейтеринг</span>
                </router-link>
                <router-link
                    v-for="c in isOutputToSearch"
                    :to="{
                    path: `/menu/${slugify(c.name)}`
                  }">
                  <span class="hover:text-[#303030] transition-all duration-300 ease-in-out">{{ c.name }}</span>
                </router-link>
              </ul>
            </div>

          </div>

        </div>
      </div>


      <!--Список пунктов меню      -->
      <div class="flex justify-end items-center gap-[0px] md:gap-4">
        <ul
            :class="isActive ? 'xl:gap-[10px] 2xl:gap-[20px]' : 'xl:gap-[20px] 2xl:gap-[50px]'"
            class="hidden xl:flex items-center justify-end gap-2 md:gap-2 lg:gap-[36px]  font-montserrat font-semibold text-sm md:text-lg 2xl:text-xl  transition-all duration-500 ease-in-out">
          <router-link to="/about">О нас</router-link>
          <router-link to="/catering">Кейтеринг</router-link>
          <router-link to="/menu">Меню</router-link>
          <router-link to="/delivery">Доставка</router-link>
          <router-link to="/contacts">Контакты</router-link>
        </ul>

        <button @click.stop="toggleClass" class="focus:outline-none lg:hidden" :class="{
        'hidden lg:block': isActive,
        'block': !isActive,
      }">
          <img src="/image/icons/Search.svg" alt="Search">
        </button>


        <router-link to="/cart" :class="{'hidden md:block': isActive, 'block': !isActive,}">
          <img v-if="cart.length === 0" class="mx-[15px] 2xl:mx-[25px]" src="/image/icons/cart.svg" alt="cart">

          <div v-else class="relative mx-[25px]">
            <span class="absolute top-0 right-0 -mt-1 -mr-2 flex size-3">
              <span class="absolute inline-flex h-full w-full animate-ping rounded-full bg-[#FFC013] opacity-75"></span>
              <span class="relative inline-flex size-3 rounded-full bg-[#FFC013]"></span>
            </span>
            <img class="" src="/image/icons/cart.svg" alt="cart">
          </div>

        </router-link>

        <div class="transition-all duration-200 ease-in-out transform">
          <img v-if="!menuOpen" class="xl:hidden" src="/image/icons/burger.svg" alt="Menu1"
               @click="()=> emit('openMenu')">

          <img v-else class="xl:hidden" src="/image/icons/closeBurger.svg" alt="Menu2" @click="()=> emit('closeMenu')">
        </div>


        <Button @click="()=> emit('cartOpen')" class="hidden xl:block" title="Заказать звонок"/>
      </div>
    </div>
  </header>
</template>

<script setup>

import Button from "@/components/ui/Button.vue";
import {computed, inject, nextTick, onMounted, onUnmounted, ref} from "vue";
import {slugify} from "@/utils/slugify.js";
import api from "@/utils/api";

const {cart} = inject("cart");

defineProps(["menuOpen", "totalPrice"]);
const emit = defineEmits(["openMenu", "closeMenu", "cartOpen"]);

const isActive = ref(false);
const element = ref(null);

// Для скрытия/показа header при скролле
const isHeaderHidden = ref(false);
let lastScrollTop = 0;
const headerHeight = 100; // Примерная высота header, можно измерить точнее

const handleScroll = () => {
  const scrollTop = window.pageYOffset || document.documentElement.scrollTop;

  if (scrollTop > lastScrollTop && scrollTop > headerHeight) {
    // Скроллим вниз - скрываем header
    isHeaderHidden.value = true;
  } else {
    // Скроллим вверх - показываем header
    isHeaderHidden.value = false;
  }

  lastScrollTop = scrollTop;
};

// Для поиска
const searchQuery = ref("");
const categories = ref([])
const products = ref([])


const fetchCategories = async () => {
  try {
    const {data} = await api.get("/categories");
    categories.value = data.filter(category => category.active)
  } catch (error) {
    console.log(error)
  }
}

const fetchProducts = async () => {
  try {
    const {data} = await api.get("/products");
    products.value = data.filter(product => product.active)
  } catch (error) {
    console.log(error)
  }
}

const isOutputToSearch = computed(() => {
  return categories.value.filter(category => category.outputToSearch)
})

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
  window.addEventListener("scroll", handleScroll);
  fetchCategories();
  fetchProducts();
})

onUnmounted(() => {
  document.removeEventListener("click", handleClickOutside);
  window.removeEventListener("scroll", handleScroll);
})


</script>

<style scoped>
.open-search {
  width: 405px;
}

.close-search {
  width: 255px;
}



.title-close {
  display: none;
}

.title-open {
  display: block;
}
</style>