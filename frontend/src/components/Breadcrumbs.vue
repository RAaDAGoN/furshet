<template>
  <nav
      v-if="breadcrumbs.length > 1"
      class="max-w-[1746px] mx-auto mt-[15px] md:mt-[20px] px-5 md:px-0 flex gap-[10px]"
  >
    <div v-for="(crumb, index) in breadcrumbs" :key="index" class="text-[#9C9C9C]">
      <router-link
          v-if="index !== breadcrumbs.length - 1"
          :to="crumb.path"
          class="font-montserrat font-medium text-[12px] md:text-[18px]"
      >
        {{ crumb.title }}
      </router-link>
      <span v-else class="font-montserrat font-medium text-[12px] md:text-[18px] text-[#3C3C3C]">
        {{ crumb.title }}
      </span>
      <span v-if="index !== breadcrumbs.length - 1" class=" text-[12px] md:text-[18px] "> > </span>
    </div>
  </nav>
</template>

<script setup>
import { computed, watch } from "vue";
import { useRoute } from "vue-router";
import { useBreadcrumbsStore } from "@/components/stores/breadcrumbs.js";

const route = useRoute();
const bcStore = useBreadcrumbsStore();

// чистим ТОЛЬКО при уходе с menu
watch(
    () => route.path,
    (path) => {
      // ушли вообще с menu
      if (!path.startsWith("/menu")) {
        bcStore.clear();
        return;
      }

      // ровно /menu
      if (path === "/menu") {
        bcStore.clear();
      }
    },
    { immediate: true }
);

const breadcrumbs = computed(() => {
  const items = [{ title: "Главная", path: "/" }];

  // статические страницы (НЕ menu)
  if (!route.path.startsWith("/menu")) {
    if (route.meta?.breadcrumb && route.path !== "/") {
      items.push({
        title: route.meta.breadcrumb,
        path: route.path,
      });
    }
    return items;
  }

  // /menu
  items.push({ title: "Меню", path: "/menu" });

  // /menu/:category
  if (bcStore.category) {
    items.push({
      title: bcStore.category.name,
      path: `/menu/${route.params.categorySlug}`,
    });
  }

  // /menu/:category/:product
  if (bcStore.product) {
    items.push({
      title: bcStore.product.name,
      path: route.fullPath,
    });
  }

  return items;
});
</script>
