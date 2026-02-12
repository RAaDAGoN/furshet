<template>
  <div>
    <div class="max-w-[1746px] mx-auto md:flex items-center justify-between mt-12 px-5 2xl:px-0">
      <div class="flex flex-col gap-[30px] w-full">
        <div class="flex items-center justify-between w-full">
          <h1 class="font-comfort font-bold text-[#97AB94] text-[28px] xl:text-[60px] leading-[130%] uppercase">Отзывы</h1>

          <div class="flex items-center justify-between gap-[30px]">
            <CartButton @click="openSendFeedback" title="Оставить отзыв" :in-cart="true"/>
          </div>
        </div>

        <div class="2xl:flex gap-[20px] 2xl:flex-wrap grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3">

          <div  v-for="f in feedbacks" @click="selectFeedback(f)" :key="f.id" class="flex flex-col gap-[16px] flex-nowrap border border-[#D5D9E4] rounded-[20px] w-full max-w-[568px] max-h-[242px] p-5 cursor-pointer select-none">
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
              <span>{{ formatDate(f.date) }}</span>
            </div>

            <div class="font-montserrat font-normal text-[21px] text-[#212121] leading-[110%] line-clamp-4">
            <span>
              {{ f.comment }}
            </span>
            </div>


          </div>

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
  </div>

</template>

<script setup>
import CartButton from "@/components/ui/CartButton.vue";
import RatingStars from "@/components/ui/RatingStars.vue";
import FeedbackModal from "@/components/modal/FeedbackModal.vue";
import {onMounted, ref} from "vue";
import api from "@/utils/api.js";
import SendFeedback from "@/components/modal/SendFeedback.vue";

const sendFeedbackModal = ref(false);

const selectedFeedback = ref(null);
const feedbacks = ref([]);

const fetchFeedbacks = async () => {
  try {
    const {data} = await api.get("/feedbacks");
    feedbacks.value = data
  } catch (e) {
    console.error(e);
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

<style scoped>

</style>