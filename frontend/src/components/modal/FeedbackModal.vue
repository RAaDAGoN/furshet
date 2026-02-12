<template>
  <div @click="()=>emit('closeFeedback')" class="fixed top-0 left-0 h-full w-full bg-black z-40 opacity-70 "></div>

  <div class="relative h-full w-full">
    <div class="bg-white fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-50 rounded-[20px] flex flex-col md:flex-row justify-between ">


      <div class="flex flex-col gap-[16px] flex-nowrap  max-w-[918px] p-5">
        <div class="flex items-center gap-[18px] font-montserrat font-medium text-[21px] text-[#212121] leading-[110%]">
          <div class="bg-[#EBF0E8] rounded-[30px] py-[14px] px-[19px] max-w-[58px]">
            {{ letter }}
          </div>
          <p>{{ name }}</p>
        </div>

        <div class="flex items-center gap-[16px]">
          <div>
            <RatingStars :rating="rating" />
          </div>
          <span>{{date}}</span>
        </div>

        <div class="font-montserrat font-normal text-[21px] text-[#212121] leading-[110%]">
            <span>
              {{comment}}
            </span>
        </div>
      </div>


      <div @click="()=>emit('closeFeedback')" class="absolute top-[15px] right-4">
        <img class="min-w-[22px] min-h-[22px] cursor-pointer" src="/image/icons/CloseBlack.svg" alt="">
      </div>
    </div>
  </div>
</template>

<script setup>

import RatingStars from "@/components/ui/RatingStars.vue";

const emit = defineEmits(["closeFeedback"])
defineProps(["letter", "name", "rating", "date", "comment"]);
</script>