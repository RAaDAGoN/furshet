<template>
  <div class="max-w-[1746px] mx-auto">
    <div v-if="products.length > 0"
         class="grid grid-cols-2 auto-rows-fr md:grid-cols-4 gap-5 md:gap-11 text-base font-comfort font-medium text-white md:text-4xl min-h-[200px]"
         >
      <MenuItem v-for="product in products"
                :key="product.id"
                :active="product.active"
                :block="product.blockedByCategory"
                :mark="product.productLabel"
                :title="product.name"
                :image="product.productImages?.length
                ? `${API_URL}/media/${product.productImages[0].filename}`
                : null"
                :to="{name: 'Product', params: { categorySlug: $route.params.slug, productSlug: slugify(product.name) }}"
      />
    </div>
  </div>
</template>

<script setup>
import MenuItem from "@/components/MenuItem.vue";
import { slugify } from "@/utils/slugify.js";
import {API_URL} from "@/main.js";
import PageNotFound from "@/pages/staticPage/PageNotFound.vue";

defineProps({
  products: Array,
})

</script>