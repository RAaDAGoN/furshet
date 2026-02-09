<template>
  <div
      class="relative w-full bg-black bg-[url(/image/FooterMobile.png)] bg-cover bg-bottom bg-no-repeat aspect-[380/665] lg:bg-[url(/image/Food2.png)] lg:aspect-auto md:max-h-[896px] lg:min-h-[600px] mt-12 lg:mt-28 ">
    <div class="absolute inset-0 bg-black/30">
      <div class="absolute inset-0 bg-black/30"></div>

      <div class="relative z-10 max-w-[1746px] mx-auto px-5 2xl:px-0  flex py-[0px] pt-[20px] md:py-[60px]">
        <div class="">
          <h1 class="text-white text-[22px] md:text-[45px] font-comfort font-bold mb-2 md:mb-6 leading-[130%]">
            Важное событие,<br class="hidden md:block"/>
            но не знаете как рассчитать?
          </h1>

          <p class="text-white text-base md:text-2xl font-medium font-montserrat leading-[130%]">
            Заполните заявку и мы поможем рассчитать<br/>меню для вашего мероприятия
          </p>

          <form @submit.prevent="callbackOrder" class="font-montserrat mt-[40px] ">
            <div class="flex flex-col ju md:flex-row gap-[30px] mb-[30px] text-[16px] md:text-[18px]">
              <input
                  ref="name"
                  v-model="orderData.FIO"
                  :class="inputClass(errors.FIO)"
                  name="userName"
                  class="outline-none border-b border-white/40 bg-transparent pl-5 text-white placeholder-white/70"
                  placeholder="Ваше имя" type="text"
              />
              <input
                  ref="phone"
                  v-model="orderData.phone"
                  :class="inputClass(errors.phone)"
                  name="userPhone"
                  class="outline-none border-b border-white/40 bg-transparent pl-5 text-white placeholder-white/70"
                  placeholder="Телефон" type="text"
              />
            </div>

            <div class="flex flex-col ju md:flex-row gap-[30px] text-[16px] md:text-[18px]">
              <input
                  ref="amount"
                  v-model="orderData.amountPeople"
                  :class="inputClass(errors.amountPeople)"
                  name="userCount"
                  class="outline-none border-b border-white/40 bg-transparent pl-5 text-white placeholder-white/70"
                  placeholder="Количество человек" type="text"
              />
              <input
                  ref="data"
                  v-model="orderData.data"
                  :class="inputClass(errors.data)"
                  name="userDate"
                  class="outline-none border-b border-white/40 bg-transparent pl-5 text-white placeholder-white/70"
                  placeholder="Дата" type="text"
              />
            </div>
            <Button type="submit" class="mt-10 " title="Отправить заявку"/>
          </form>


        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Button from "@/components/ui/Button.vue";
import {reactive, ref} from "vue";
import {useToast} from "vue-toastification";
import api from "@/utils/api";

const orderData = reactive({
  FIO: "",
  phone: "",
  amountPeople: "",
  data: "",
})

const errors = reactive({
  FIO: false,
  phone: false,
  amountPeople: false,
  data: false,
});

const toast = useToast();

const inputClass = (hasError) => [
  "outline-none border-b bg-transparent pl-5 pb-3 text-white placeholder-white/70 transition",
  hasError ? "border-red-500" : "border-white/40",
];


const validateForm = () => {
  errors.FIO = !orderData.FIO.trim();
  errors.phone = orderData.phone.replace(/\D/g, "").length !== 11;
  errors.amountPeople = !orderData.amountPeople.trim();
  errors.data = !orderData.data.trim();

  return !Object.values(errors).some(Boolean);
};

const callbackOrder = async () => {
  if (!validateForm()) {
    toast.error("Заполните поля корректно", {
      position: "bottom-right",
      timeout: 3000
    })

    return;
  }

  try {
    const orderRequest = {
      FIO: orderData.FIO,
      phone: orderData.phone,
      amountPeople: orderData.amountPeople,
      data: orderData.data,
      typeCallbackRequest: 'CALCULATION'
    };

    const {data} = await api.post("/callbacks", orderRequest);

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
