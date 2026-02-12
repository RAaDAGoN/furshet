<template>
  <div @click="()=>emit('closeSendFeedback')" class="fixed top-0 left-0 h-full w-full bg-black z-40 opacity-70 "></div>

  <div class="relative h-full w-full">
    <div
        class="bg-white fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-50 flex flex-col md:flex-row justify-between max-w-[560px] p-[40px]">
      <div class="flex flex-col gap-[20px]">
        <div class="text-[#393939] flex flex-col gap-[10px]">
          <h1 class="font-comfort font-bold text-[45px] leading-[140%]">Оставить отзыв</h1>
          <p class="font-montserrat font-normal text-[21px] leading-[110%]">Нам важно ваше мнение!<br/>
            Поделитесь впечатлениями о нашем сервисе и помогите нам стать лучше.</p>
        </div>

        <div>

          <form @submit.prevent="fetchFeedback" class="flex flex-col gap-[30px]" action="" method="post">
            <div class="flex flex-col gap-[20px]">
              <span class="font-montserrat font-normal text-[21px] leading-[110%]">Ваша оценка {{ rating }}</span>
              <div class="w-full max-w-md">
                <div class="relative">

                  <!-- Линия -->
                  <div class="absolute top-1/2 left-0 w-full h-1 bg-[#EBF0E8] -translate-y-1/2 rounded"></div>

                  <!-- Активная линия -->
                  <div
                      class="absolute top-1/2 left-0 h-1 bg-[#EBF0E8] -translate-y-1/2 rounded transition-all"
                      :style="{ width: (rating / 5) * 100 + '%' }"
                  ></div>

                  <!-- Точки -->
                  <div class="relative flex items-center justify-between">
                    <button
                        v-for="(value, index) in steps"
                        :key="value"
                        type="button"
                        @click="setRating(value)"
                        class="w-4 h-4 rounded-full border-2 transition-all"
                        :class="[value <= rating
                          ? 'bg-[#97AB94] border-[#97AB94]'
                          : 'bg-[#EBF0E8] border-[#EBF0E8]',
                          index % 2 === 0
                          ? 'w-[10px] h-[10px]'
                          : 'w-[14px] h-[14px]'

                        ]"
                    ></button>
                  </div>

                  <!-- Скрытый range (для формы) -->
                  <input
                      type="range"
                      min="0.5"
                      max="5"
                      step="0.5"
                      v-model="rating"
                      class="absolute inset-0 opacity-0 cursor-pointer"
                  />
                </div>
              </div>
            </div>

            <div class="flex flex-col gap-[20px]">
              <OrderInput placeholder="Фамилия и имя" name="FI" v-model="feedbackForm.FI"/>
              <OrderInput placeholder="Ваш отзыв" name="comment" v-model="feedbackForm.comment"/>
            </div>

            <div class="flex items-center gap-[40px]">
              <Button2 type="submit" title="Отправить отзыв" :in-cart="true"/>
              <p @click="()=>emit('closeSendFeedback')"
                 class="font-montserrat font-medium text-[#97AB94] text-[24px] leading-[140%] cursor-pointer underline">
                Закрыть</p>
            </div>
          </form>

        </div>
      </div>


    </div>
  </div>
</template>

<script setup>

import OrderInput from "@/components/ui/OrderInput.vue";
import {computed, reactive, ref} from "vue";
import Button from "@/components/ui/Button.vue";
import Button2 from "@/components/ui/Button2.vue";
import api from "@/utils/api.js";
import {useToast} from "vue-toastification";

const emit = defineEmits(["closeSendFeedback"])

const feedbackForm = reactive({
  FI: "",
  comment: ""
})

const rating = ref(5)
const toast = useToast();

const errors = reactive({
  FI: false,
  comment: false,
});

const inputClass = (hasError) => [
  "py-[18px] pl-[25px] border-b-2 placeholder:opacity-25 outline-none transition",
  hasError ? "border-red-500" : "border-[#D2D2D2]",
];

const validateForm = () => {
  errors.FI = !feedbackForm.FI.trim();
  errors.comment = feedbackForm.comment.trim().length < 5;

  return !Object.values(errors).some(Boolean);
};

const currentDate = () => {
  const date = new Date();

  return `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}`
}

const fetchFeedback = async () => {
  if (!validateForm()) {
    toast.error("Заполните поля корректно", {
      position: "bottom-right",
      timeout: 3000
    })
    return;
  }

  try {
    const payload = {
      FI: feedbackForm.FI,
      rating: rating.value,
      date: currentDate(),
      comment: feedbackForm.comment
    }

    await api.post("/feedbacks", payload)

    // очистка
    feedbackForm.FI = ""
    feedbackForm.comment = ""
    rating.value = 5

    emit("closeSendFeedback")

  } catch (e) {
    console.error(e.message)
  }
}


const steps = computed(() =>
    Array.from({length: 10}, (_, i) => (i + 1) * 0.5)
)

function setRating(value) {
  rating.value = value
}

</script>