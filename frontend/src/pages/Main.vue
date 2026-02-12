<template>
  <div>

    <Slider/>

    <Content1/>

    <div class="bg-[#EBF0E8] pt-[30px] pb-[60px] mt-[80px]">
      <div class="max-w-[1746px] mx-auto px-5 2xl:px-0">


        <div class="flex gap-[10px] md:gap-[30px] md:items-center flex-col md:flex-row mb-[20px] md:mb-[0px]">
          <h1 class="font-comfort font-bold text-[28px] md:text-[60px] text-[#97AB94] uppercase">Меню</h1>
          <p class="font-montserrat font-medium text-[16px] md:text-[21px] text-[#3C3C3C]">Ознакомьтесь с нашим разнообразным меню для
            вашего фуршетного стола</p>
        </div>

        <div class="flex flex-col lg:flex-row gap-[20px] md:gap-[45px]">
          <router-link to="/menu"
                       v-for="item in regular"
                       :key="item.id"
                       class="relative group overflow-hidden z-10 flex flex-col md:mt-[15px] w-full h-[240px] md:h-[420px]"
          >

            <MainMenuCard :filename="item.filename" />

            <!-- Градиенты -->
            <div class="absolute inset-0">
              <div class="absolute bottom-0 left-0 right-0 h-1/2 bg-gradient-to-t from-black/100 to-transparent opacity-100 group-hover:opacity-0 transition-opacity duration-500"></div>
              <div class="absolute bottom-0 left-0 right-0 h-1/2 bg-gradient-to-t from-[#97AB94] to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
            </div>

            <div class="flex-grow relative z-10"></div>

            <h1 class="text-white text-base md:text-3xl font-bold mt-auto mb-[10px] md:mb-[30px] mx-[10px] md:mx-[30px] relative z-10">
              {{ item.translate }}
            </h1>
          </router-link>

          <!-- SEASONAL меню -->
          <router-link to="/seasonal"
                       v-for="item in seasonal"
                       :key="item.id"
                       class="relative group overflow-hidden z-10 flex flex-col md:mt-[15px] w-full h-[240px] md:h-[420px]"
          >
            <MainMenuCard :filename="item.filename" />

            <!-- Градиенты -->
            <div class="absolute inset-0">
              <div class="absolute bottom-0 left-0 right-0 h-1/2 bg-gradient-to-t from-black/100 to-transparent opacity-100 group-hover:opacity-0 transition-opacity duration-500"></div>
              <div class="absolute bottom-0 left-0 right-0 h-1/2 bg-gradient-to-t from-[#97AB94] to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
            </div>

            <div class="flex-grow relative z-10"></div>

            <h1 class="text-white text-base md:text-3xl font-bold mt-auto mb-[10px] md:mb-[30px] mx-[10px] md:mx-[30px] relative z-10">
              {{ item.translate }}
            </h1>
          </router-link>

          <router-link to="/catering"
                       v-for="item in catering"
                       :key="item.id"
                       class="relative group overflow-hidden z-10 flex flex-col md:mt-[15px] w-full h-[240px] md:h-[420px]"
          >
            <MainMenuCard :filename="item.filename" />

            <!-- Градиенты -->
            <div class="absolute inset-0">
              <div class="absolute bottom-0 left-0 right-0 h-1/2 bg-gradient-to-t from-black/100 to-transparent opacity-100 group-hover:opacity-0 transition-opacity duration-500"></div>
              <div class="absolute bottom-0 left-0 right-0 h-1/2 bg-gradient-to-t from-[#97AB94] to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
            </div>

            <div class="flex-grow relative z-10"></div>

            <h1 class="text-white text-base md:text-3xl font-bold mt-auto mb-[10px] md:mb-[30px] mx-[10px] md:mx-[30px] relative z-10">
              {{ item.translate }}
            </h1>
          </router-link>
        </div>

      </div>

    </div>
    <div class="bg-[#30592A80] mt-[50px] lg:mt-[110px] relative ">
      <div class="max-w-[1755px] mx-auto flex flex-col justify-start ">
        <div class="text-white px-5 2xl:px-0 py-6">
          <h1 class="font-comfort font-semibold text-2xl lg:text-5xl">Не знаете что выбрать?</h1>
          <p class="font-montserrat text-base md:text-2xl">Поможем выбрать фуршетные боксы, которые понравятся всем!</p>
          <Button @click="openCart" class="mt-[30px]" title="Заказать звонок"/>
        </div>

        <img class="lg:absolute bottom-0 right-0 z-10 " src="/image/Food.png" alt="Food">
      </div>

    </div>

    <Content2/>

    <Content3/>

    <feedback />

    <Content4/>


  </div>
</template>

<script setup>

import Slider from "@/components/main/Slider.vue";
import Content1 from "@/components/main/Content1.vue";
import Button from "@/components/ui/Button.vue";
import Content2 from "@/components/main/Content2.vue";
import Content3 from "@/components/main/Content3.vue";
import Menu from "@/pages/Menu.vue";
import {inject, onMounted, ref} from "vue";
import OrderCall from "@/components/modal/OrderCall.vue";
import Content4 from "@/components/main/Content4.vue";
import api from "@/utils/api.js";
import {API_URL} from "@/main.js";
import MainMenuCard from "@/components/MainMenuCard.vue";
import Feedback from "@/components/main/Feedback.vue";

const {openCart} = inject("cart");

const menu = ref([])
const regular = ref([])
const seasonal = ref([])
const catering = ref([])

const fetchMenu = async () => {
  try {
    const {data} = await api.get("/menu");
    menu.value = data.filter(p => p.active);

    regular.value = [];
    seasonal.value = [];

    menu.value.forEach((item) => {
      if (item.name === "REGULAR") {
        regular.value.push(item);
      } else if (item.name === "SEASONAL") {
        seasonal.value.push(item);
      } else if (item.name === "CATERING") {
        catering.value.push(item);
      }
    })
  } catch (e) {

  }
}

onMounted(() => {
  fetchMenu();
})

</script>