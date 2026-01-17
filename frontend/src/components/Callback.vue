<template>
  <div class="relative w-full bg-[url(/image/FooterMobile.png)] bg-cover bg-no-repeat bg-center aspect-[380/665] max-w-[380px] mx-auto md:bg-[url(/image/Food2.png)] md:max-w-none md:aspect-auto md:h-auto
               mt-12 md:mt-28 pt-5 md:pt-[55px] pb-16" >
    <div class="absolute inset-0 bg-black/30"></div>

    <div class="relative z-10 max-w-[1746px] mx-auto px-4 md:px-8 h-full flex ">
      <div class="py-5">
        <h1 class="text-white text-[22px] md:text-[45px] font-comfort font-bold mb-2 md:mb-6 leading-[130%]">
          Важное событие,<br class="hidden md:block" />
          но не знаете как рассчитать?
        </h1>

        <p class="text-white text-base md:text-2xl font-medium font-montserrat leading-[130%]">
          Заполните заявку и мы поможем рассчитать<br />меню для вашего мероприятия
        </p>

        <form @submit.prevent="callbackOrder" class="font-montserrat mt-[40px] ">
          <div class="flex flex-col ju md:flex-row gap-5 mb-[30px] text-[16px] md:text-[18px]">
            <input
                ref="name"
                v-model="orderData.FIO"
                name="userName"
                class="outline-none border-b border-white/40 bg-transparent pl-5 pb-3 text-white placeholder-white/70"
                placeholder="Ваше имя" type="text" required
            />
            <input
                ref="phone"
                v-model="orderData.phone"
                name="userPhone"
                class="outline-none border-b border-white/40 bg-transparent pl-5 pb-3 text-white placeholder-white/70"
                placeholder="Телефон" type="text" required
            />
          </div>

          <div class="flex flex-col ju md:flex-row gap-5 mb-[30px] text-[16px] md:text-[18px]">
            <input
                ref="amount"
                v-model="orderData.amountPeople"
                name="userCount"
                class="outline-none border-b border-white/40 bg-transparent pl-5 pb-3 text-white placeholder-white/70"
                placeholder="Количество человек" type="text" required
            />
            <input
                ref="data"
                v-model="orderData.data"
                name="userDate"
                class="outline-none border-b border-white/40 bg-transparent pl-5 pb-3 text-white placeholder-white/70"
                placeholder="Дата" type="text" required
            />
          </div>
          <Button type="submit" class="mt-10 " title="Отправить заявку" />
        </form>


      </div>
    </div>
  </div>
</template>

<script setup>
import Button from "@/components/ui/Button.vue";
import axios from "axios";
import {reactive, ref} from "vue";
import {useToast} from "vue-toastification";

const orderData = reactive({
  FIO: "",
  phone: "",
  amountPeople: "",
  data: "",
})

const toast = useToast();

const callbackOrder = async () => {
  try {
    const orderRequest = {
      FIO: orderData.FIO,
      phone: orderData.phone,
      amountPeople: orderData.amountPeople,
      data: orderData.data,
      typeCallbackRequest: 'CALCULATION'
    };

    const { data } = await axios.post('/callbacks', orderRequest);

    Object.keys(orderData).forEach(key => {
      orderData[key] = '';
    });

    toast.success("Заявка успешно отправлена", {
      position: "bottom-right",
      timeout: 3000,
    });

  } catch (e) {
    console.error(e);
    toast.error("Ошибка отправки заявки", {
      position: "bottom-right",
      timeout: 3000,
    });
  }
}
</script>
