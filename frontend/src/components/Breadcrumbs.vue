<template>
  <nav class="breadcrumbs">
    <ol>
      <li v-for="(crumb, index) in breadcrumbs" :key="index">
        <router-link
            v-if="index < breadcrumbs.length - 1"
            :to="crumb.path"
            class="breadcrumb-link"
        >
          {{ crumb.title }}
        </router-link>
        <span v-else class="breadcrumb-current">
          {{ crumb.title }}
        </span>
        <span v-if="index < breadcrumbs.length - 1" class="separator">/</span>
      </li>
    </ol>
  </nav>
</template>

<script setup>
import {computed} from 'vue'
import {useRoute, useRouter} from 'vue-router'

const route = useRoute()
const router = useRouter()

const breadcrumbs = computed(() => {
  const crumbs = []
  const matchedRoutes = route.matched.filter(record => record.meta?.breadcrumb)

  matchedRoutes.forEach((record, index) => {
    const crumb = {
      title: record.meta.breadcrumb,
      path: record.path
    }

    // Для динамических маршрутов
    if (record.path.includes(':') && route.params) {
      const paramKeys = Object.keys(route.params)
      paramKeys.forEach(key => {
        crumb.path = crumb.path.replace(`:${key}`, route.params[key])
      })
    }

    crumbs.push(crumb)
  })

  return crumbs
})
</script>

