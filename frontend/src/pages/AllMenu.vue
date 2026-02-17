<template>
  <div class="max-w-[1746px] mx-auto px-5">
    <h1 class="text-[#97AB94] font-comfort text-[28px] mt-[20px] md:mt-[40px] md:text-5xl mr-7 leading-[130%]">МЕНЮ</h1>

    <div class="flex flex-wrap gap-4 font-montserrat font-medium text-[16px] md:text-[24px] text-[#97AB94]  my-[20px] mb-[30px]">
      <button  v-for="category in categoriesWithProducts" @click="scrollToCategory(category.slug)" class="border-[2px] border-[#97AB94] rounded-[30px] py-[12px] px-[30px]">{{ category.name }}</button>
    </div>


    <div v-for="category in categoriesWithProducts" :key="category.id" :id="`category-${category.slug}`" class="mb-8 w-full">

      <h1 class="text-[22px] md:text-[45px] font-bold text-[#97AB94] my-[20px] md:my-[30px] w-full flex items-center">
        <span class="whitespace-nowrap">
          {{ category.name }}
        </span>
        <span class="border-b-[1.5px] md:border-b-[3px] border-[#97AB94] rounded w-full ml-4"></span>
      </h1>


      <ProductList :products="category.products"/>
    </div>
    <ScrollToTop/>
  </div>

</template>

<script setup>
import {computed, onMounted, ref, watch} from "vue";
import api from "@/utils/api.js";
import ProductList from "@/components/ProductList.vue";
import {slugify} from "@/utils/slugify.js";
import {useRoute} from "vue-router";
import ScrollToTop from "@/components/ui/ScrollToTop.vue";

const route = useRoute();
const products = ref([]);

const typeMenu = computed(() => {
  if(route.name === "Seasonal"){
    return "SEASONAL";
  } else return "REGULAR"

})

const scrollToCategory = (slug) => {
  const el = document.getElementById(`category-${slug}`);
  if (!el) return;

  el.scrollIntoView({
    behavior: "smooth",
    block: "start"
  });
};


const fetchProducts = async () => {
  try {
    const {data} = await api.get("/products");
    products.value = data.filter(p => p.active && !p.blockedByCategory && p.typeMenu === typeMenu.value )
  } catch (e) {
  }
}

// группировка по категориям
const categoriesWithProducts = computed(() => {
  const map = {};

  products.value.forEach(product => {
    const category = product.category;
    if (!category) return;

    if (!map[category.id]) {
      map[category.id] = {
        id: category.id,
        name: category.name,
        slug: slugify(category.name),
        products: []
      };
    }

    map[category.id].products.push(product);
  });

  return Object.values(map);
});


onMounted(() => {
  fetchProducts()
})

watch(
    () => route.name,
    () => {
      fetchProducts();
    }
);
</script>
