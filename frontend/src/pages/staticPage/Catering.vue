<template>
  <div class="max-w-[1746px] mx-auto pt-10 px-5">
    <div class="font-montserrat font-medium text-base md:text-2xl leading-[140%] text-[#3C3C3C] flex flex-col">
      <h1 class="font-comfort font-bold text-[36px] md:text-[60px] text-[#97AB94] mb-10">Кейтеринг</h1>

      <div class="flex flex-col gap-[12px]">
        <p>Мы предлагаем профессиональные услуги кейтеринга для любых мероприятий: от корпоративных вечеринок до частных
          праздников. Доверяйте организацию вашего мероприятия профессионалам. Мы сделаем все, чтобы ваш фуршет стал
          настоящим украшением вашего праздника!</p>
        <p>Предлагаю ознакомиться с нашей организацией мероприятий!</p>
      </div>


      <div
          class="flex flex-col md:flex-row gap-4 font-montserrat font-medium text-[16px] md:text-[24px]  my-[40px] mb-[30px]"

      >
        <button
            @click="activeTab = 'tab1'"
            class="border-[2px] border-[#97AB94] rounded-[30px] py-[12px] px-[30px]"
            :class="activeTab === 'tab1' ?
        'bg-[#97AB94] text-white':
        'bg-white text-[#97AB94]'
          "
        >
          Организация кофебрейка
        </button>

        <button
            @click="activeTab = 'tab2'"
            class="border-[2px] border-[#97AB94] rounded-[30px] py-[12px] px-[30px]"
            :class="activeTab === 'tab2' ?
        'bg-[#97AB94] text-white':
        'bg-white text-[#97AB94]'
          "
        >
          Свадебные зоны
        </button>

        <button
            @click="activeTab = 'tab3'"
            class="border-[2px] border-[#97AB94] rounded-[30px] py-[12px] px-[30px]"
            :class="activeTab === 'tab3' ?
        'bg-[#97AB94] text-white':
        'bg-white text-[#97AB94]'
          "
        >
          День рождения
        </button>
      </div>

      <Transition :name="`slide-${direction}`" mode="out-in">
        <div :key="activeTab">
          <div v-if="activeTab === 'tab1'" class="columns-2 md:columns-4 gap-[28px]">
            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="1" img="/image/Catering1.png"/>
              <CateringImg :type="2" img="/image/Catering2.png"/>
              <CateringImg :type="3" img="/image/Catering3.png"/>
            </div>
            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="2" img="/image/Catering4.png"/>
              <CateringImg :type="3" img="/image/Catering5.png"/>
              <CateringImg :type="2" img="/image/Catering6.png"/>
            </div>


            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="3" img="/image/Catering7.png"/>
              <CateringImg :type="1" img="/image/Catering8.png"/>
              <CateringImg :type="1" img="/image/Catering9.png"/>
            </div>

            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="1" img="/image/Catering10.png"/>
              <CateringImg :type="2" img="/image/Catering11.png"/>
              <CateringImg :type="3" img="/image/Catering12.png"/>
            </div>
          </div>

          <div v-if="activeTab === 'tab2'" class="columns-2 md:columns-4 gap-[28px]">
            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="1" img="/image/Catering1.png"/>
              <CateringImg :type="2" img="/image/Catering2.png"/>
              <CateringImg :type="3" img="/image/Catering3.png"/>
            </div>
            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="2" img="/image/Catering4.png"/>
              <CateringImg :type="3" img="/image/Catering5.png"/>
              <CateringImg :type="2" img="/image/Catering6.png"/>
            </div>


            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="3" img="/image/Catering7.png"/>
              <CateringImg :type="1" img="/image/Catering8.png"/>
              <CateringImg :type="1" img="/image/Catering9.png"/>
            </div>

            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="1" img="/image/Catering10.png"/>
              <CateringImg :type="2" img="/image/Catering11.png"/>
              <CateringImg :type="3" img="/image/Catering12.png"/>
            </div>
          </div>

          <div v-if="activeTab === 'tab3'" class="columns-2 md:columns-4 gap-[28px]">
            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="1" img="/image/Catering1.png"/>
              <CateringImg :type="2" img="/image/Catering2.png"/>
              <CateringImg :type="3" img="/image/Catering3.png"/>
            </div>
            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="2" img="/image/Catering4.png"/>
              <CateringImg :type="3" img="/image/Catering5.png"/>
              <CateringImg :type="2" img="/image/Catering6.png"/>
            </div>


            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="3" img="/image/Catering7.png"/>
              <CateringImg :type="1" img="/image/Catering8.png"/>
              <CateringImg :type="1" img="/image/Catering9.png"/>
            </div>

            <div class="break-inside-avoid mb-[14px]">
              <CateringImg :type="1" img="/image/Catering10.png"/>
              <CateringImg :type="2" img="/image/Catering11.png"/>
              <CateringImg :type="3" img="/image/Catering12.png"/>
            </div>
          </div>

        </div>

      </Transition>

    </div>

  </div>
</template>

<script setup>
import CateringImg from "@/pages/staticPage/CateringImg.vue";
import {ref, watch} from "vue";

const activeTab = ref('tab1')
const prevTab = ref(activeTab.value)
const direction = ref('right') // right | left

const order = ['tab1', 'tab2', 'tab3']

watch(activeTab, (newVal, oldVal) => {
  direction.value =
      order.indexOf(newVal) > order.indexOf(oldVal)
          ? 'right'
          : 'left'

  prevTab.value = newVal
})


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
