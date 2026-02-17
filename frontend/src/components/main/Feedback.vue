<template>
  <div class="max-w-[1746px] mx-auto h-full md:flex items-center justify-between mt-12 md:mt-[110px] px-5">
    <div class="flex flex-col gap-[30px] w-full">
      <div class="flex items-center justify-between w-full">
        <h1 class="font-comfort font-bold text-[#97AB94] text-[28px] 2xl:text-[60px] leading-[130%] uppercase">Отзывы</h1>

        <div class="flex flex-col md:flex-row items-center justify-between gap-[30px]">
          <router-link to="/feedbacks" class="font-montserrat font-medium text-[#97AB94] text-[18px] 2xl:text-[24px] leading-[140%] underline hover:no-underline">Смотреть все</router-link>
          <CartButton class="hidden lg:block" @click="openSendFeedback" title="Оставить отзыв" :in-cart="true"/>
        </div>
      </div>

      <div class="gap-[20px] md:grid md:grid-cols-2 xl:grid-cols-3">

        <div v-for="f in feedbacks" @click="selectFeedback(f)" :key="f.id"
             class="hidden 2xl:flex flex-col md:grid gap-[16px] 2xl:flex-nowrap border border-[#D5D9E4] rounded-[20px] w-full  max-w-[568px] max-h-[242px] p-5 cursor-pointer select-none ">
          <div class="flex items-center gap-[18px] font-montserrat font-medium text-[16px] 2xl:text-[21px] text-[#212121] leading-[110%]">
            <div class="bg-[#EBF0E8] rounded-[30px] py-[14px] px-[19px] max-w-[58px] uppercase">
              {{ f.FI?.charAt(0) }}
            </div>
            <p>{{ f.FI }}</p>
          </div>

          <div class="flex items-center gap-[16px]">
            <div>
              <RatingStars :rating="Number(parseFloat(f.rating))" />
            </div>
            <span class="text-[12px] md:text-[14px]">{{ formatDate(f.date) }}</span>
          </div>

          <div class="font-montserrat font-normal text-[16px] 2xl:text-[21px] text-[#212121] leading-[110%] line-clamp-4">
            <span>
              {{ f.comment }}
            </span>
          </div>
        </div>

        <div class="w-full overflow-hidden block md:hidden">
          <swiper
              @swiper="onSwiper"
              :slides-per-view="1.05"
              :space-between="10"
              :watch-slides-progress="true">
            <swiper-slide v-for="f in feedbacks"
                          :key="f.id">
              <div  @click="selectFeedback(f)" :key="f.id" class="flex flex-col gap-[16px] flex-nowrap border border-[#D5D9E4] rounded-[20px] w-full h-[222px] max-h-[242px] p-5 cursor-pointer select-none">
                <div class="flex items-center gap-[18px] font-montserrat font-medium text-[21px] text-[#212121] leading-[110%]">
                  <div class="bg-[#EBF0E8] rounded-[30px] py-[14px] px-[19px] max-w-[58px] uppercase">
                    {{ f.FI?.charAt(0) }}
                  </div>
                  <p>{{ f.FI }}</p>
                </div>

                <div class="flex items-center gap-[16px]">
                  <div>
                    <RatingStars :rating="Number(parseFloat(f.rating))" />
                  </div>
                  <span class="text-[12px] md:text-[14px]">{{ formatDate(f.date) }}</span>
                </div>

                <div class="font-montserrat font-normal text-[21px] text-[#212121] leading-[110%] line-clamp-3">
            <span>
              {{ f.comment }}
            </span>
                </div>
              </div>

            </swiper-slide>

          </swiper>
          <div class="flex justify-center gap-4 mt-6">
            <button
                @click="swiperRef?.slidePrev()"
                class="rounded-[50px] rotate-[180deg]"
            >
              <svg :class="isBeginning ? 'opacity-50' : 'opacity-100' " class="w-[48px] h-[48px] transition-colors text-[#EBF0E8]">
                <use href="#slider"></use>
              </svg>
            </button>

            <button
                @click="swiperRef?.slideNext()"
                class="rounded-[50px]"
            >
              <svg :class="isEnd ? 'opacity-50' : 'opacity-100' " class="w-[48px] h-[48px] text-[#EBF0E8] transition-colors">
                <use href="#slider"></use>
              </svg>
            </button>
          </div>


        </div>


      </div>
      <div class="flex justify-center gap-4 w-full lg:hidden">
        <CartButton class="" @click="openSendFeedback" title="Оставить отзыв" :in-cart="true"/>
      </div>

    </div>

  </div>

  <FeedbackModal
      @close-feedback="closeFeedback" v-if="selectedFeedback"
      :letter="selectedFeedback.FI?.charAt(0)"
      :name="selectedFeedback.FI"
      :rating="Number(parseFloat(selectedFeedback.rating))"
      :date="formatDate(selectedFeedback.date)"
      :comment="selectedFeedback.comment"
  />

  <SendFeedback @close-send-feedback="closeSendFeedback" v-if="sendFeedbackModal" />
</template>

<script setup>

import Button from "@/components/ui/Button.vue";
import CartButton from "@/components/ui/CartButton.vue";
import RatingStars from "@/components/ui/RatingStars.vue";
import FeedbackModal from "@/components/modal/FeedbackModal.vue";
import {onMounted, ref} from "vue";
import api from "@/utils/api.js";
import SendFeedback from "@/components/modal/SendFeedback.vue";
import { Swiper, SwiperSlide } from "swiper/vue"
import { Thumbs } from "swiper/modules"
import { Navigation } from 'swiper/modules'
import "swiper/css"
import "swiper/css/thumbs"

const sendFeedbackModal = ref(false);

const selectedFeedback = ref(null);
const feedbacks = ref([]);

const swiperRef = ref(null)
const isBeginning = ref(true)
const isEnd = ref(false)

const onSwiper = (swiper) => {
  swiperRef.value = swiper

  const updateState = () => {
    isBeginning.value = swiper.isBeginning
    isEnd.value = swiper.isEnd
  }

  updateState()
  swiper.on('slideChange', updateState)
}

const fetchFeedbacks = async () => {
  try {
    const {data} = await api.get("/feedbacks");
    feedbacks.value = data.slice(0, 9);
  } catch (e) {

  }
}

const formatDate = (date) => {
  if (!date) {
    return "";
  }

  const [day, month, year] = date.split(".");

  const months = [
    'января', 'февраля', 'марта', 'апреля', 'мая', 'июня',
    'июля', 'августа', 'сентября', 'октября', 'ноября', 'декабря'
  ]

  return `${parseInt(day)} ${months[parseInt(month) - 1]} ${year}`;
}

const selectFeedback = (feedback) => {
  selectedFeedback.value = feedback;
}

const closeFeedback = () => {
  selectedFeedback.value = null;
}

const openSendFeedback = () => {
  sendFeedbackModal.value = true;
}

const closeSendFeedback = () => {
  sendFeedbackModal.value = false;
}

onMounted(() => {
  fetchFeedbacks();
})
</script>
