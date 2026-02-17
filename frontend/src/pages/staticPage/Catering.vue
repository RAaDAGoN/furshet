<template>
  <div class="max-w-[1746px] mx-auto pt-10 px-5">
    <div class="font-montserrat font-medium text-base xl:text-[22px] 3xl:text-[24px] leading-[140%] text-[#3C3C3C] flex flex-col">
      <h1 class="font-comfort font-bold text-[36px] 2xl:text-[45px] 3xl:text-[60px] text-[#97AB94] mb-5">Кейтеринг</h1>

      <div class="flex flex-col gap-[12px] ">
        <p>Мы предлагаем профессиональные услуги кейтеринга для любых мероприятий: от корпоративных вечеринок до частных
          праздников. Доверяйте организацию вашего мероприятия профессионалам. Мы сделаем все, чтобы ваш фуршет стал
          настоящим украшением вашего праздника!</p>
        <p>Предлагаю ознакомиться с нашей организацией мероприятий!</p>
      </div>


      <div
          class="flex flex-col flex-wrap md:flex-row gap-4 font-montserrat font-medium text-[16px] md:text-[24px]  my-[40px] mb-[30px]"

      >
        <button
            v-for="point in points"
            :key="point.id"
            @click="activeTab = point.id"
            class="border-[2px] border-[#97AB94] rounded-[30px] py-[12px] px-[30px]"
            :class="activeTab === point.id ?
        'bg-[#97AB94] text-white':
        'bg-white text-[#97AB94]'
          "
        >
          {{point.name}}
        </button>

      </div>

      <Transition :name="`slide-${direction}`" mode="out-in">
        <div
            :key="activeTab"
            class="columns-2 lg:columns-4 gap-[28px]"
        >
          <div
              v-for="item in filteredCatering"
              :key="item.id"
              class="break-inside-avoid mb-[14px]"
          >
            <CateringImg
                :type="typeMap[item.typePhoto]"
                :img="`${API_URL}/media/${item.filename}`"
            />
          </div>
        </div>
      </Transition>


    </div>

  </div>
</template>

<script setup>
import CateringImg from "@/pages/staticPage/CateringImg.vue";
import { ref, computed, onMounted, watch } from "vue";
import api from "@/utils/api.js";
import {API_URL} from "@/main.js";

const catering = ref([])
const activeTab = ref(null)
const direction = ref('right')

/* ===== FETCH ===== */
const fetchCatering = async () => {
  try {
    const { data } = await api.get("/caterings")
    catering.value = data

    // активная вкладка — первый point
    if (points.value.length && !activeTab.value) {
      activeTab.value = points.value[0].id
    }
  } catch (e) {
    console.error(e)
  }
}

onMounted(fetchCatering)

/* ===== UNIQUE POINTS ===== */
const points = computed(() => {
  const map = new Map()

  catering.value.forEach(item => {
    if (item.point) {
      map.set(item.point.id, item.point)
    }
  })

  return Array.from(map.values())
})

/* ===== FILTER BY POINT ===== */
const filteredCatering = computed(() =>
    catering.value.filter(item => item.point?.id === activeTab.value)
)

/* ===== ANIMATION DIRECTION ===== */
watch(activeTab, (newVal, oldVal) => {
  if (!oldVal) return
  const newIndex = points.value.findIndex(p => p.id === newVal)
  const oldIndex = points.value.findIndex(p => p.id === oldVal)

  direction.value = newIndex > oldIndex ? 'right' : 'left'
})

/* ===== TYPE MAP ===== */
const typeMap = {
  SMALL: 3,
  MEDIUM: 2,
  BIG: 1
}
</script>

<style scoped>
.slide-right-enter-active,
.slide-right-leave-active,
.slide-left-enter-active,
.slide-left-leave-active {
  transition: all 0.35s ease;
}

.slide-right-enter-from {
  transform: translateX(40px);
  opacity: 0;
}
.slide-right-leave-to {
  transform: translateX(-40px);
  opacity: 0;
}

.slide-left-enter-from {
  transform: translateX(-40px);
  opacity: 0;
}
.slide-left-leave-to {
  transform: translateX(40px);
  opacity: 0;
}
</style>
