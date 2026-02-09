<template>
  <div @click="()=>emit('cartClose')" class="fixed top-0 left-0 h-full w-full bg-black z-40 opacity-70 "></div>

  <div class="relative h-full w-full">
    <div class="bg-white w-[340px] md:w-[1100px] h-[610px] fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 md:p-6 z-50 rounded-sm flex flex-col md:flex-row justify-between ">
      <picture class="min-w-[50%]">
        <!-- Мобильная версия -->
        <source srcset="/image/OrderCall2.jpg" media="(max-width: 640px)" />

        <!-- Планшет -->
        <source srcset="/image/OrderCall2.jpg" media="(max-width: 1024px)" />

        <!-- Десктоп -->
        <img
            src="/image/OrderCall.jpg"
            alt="Slide 1"
            class=""
        />
      </picture>



      <div>
        <h1 class="font-comfort font-bold text-[22px] md:text-[45px] text-[#393939] leading-[140%]">Заказать звонок</h1>
        <p class="font-montserrat font-regular text-base md:text-[21px] text-[#393939] leading-[110%]">Хотите получить персональное предложение? Оставьте данные и мы свяжемся с вами в ближайшее время!</p>

        <form @submit.prevent="callbackOrder" class="flex flex-col gap-[10px] md:gap-5 font-montserrat font-medium text-base md:text-lg text-[#393939] leading-[110%]" action="">
          <input
              class="py-[18px] pl-[25px] border-b-2 border-[#D2D2D2] placeholder:opacity-25 outline-none"
              type="text"
              placeholder="Имя и фамилия"
              v-model="orderData.FIO"
              :class="inputClass(errors.FIO)"
          >
          <input
              class="py-[18px] pl-[25px] border-b-2 border-[#D2D2D2] placeholder:opacity-25 outline-none"
              type="text"
              placeholder="Номер телефона"
              v-model="orderData.phone"
              :class="inputClass(errors.phone)"
          >
          <input
              class="py-[18px] pl-[25px] border-b-2 border-[#D2D2D2] placeholder:opacity-25 outline-none"
              type="email"
              placeholder="Email"
              v-model="orderData.email"
              :class="inputClass(errors.email)"
          >

          <CartButton type="submit" class="mt-4 md:mt-5 mb-2 md:mb-4" title="Оставить заявку" />
        </form>

        <div class="flex items-start space-x-3">
          <input v-model="personalData" class="appearance-none w-6 h-6 border-2 border-[#97AB94] checked:bg-[#97AB94] cursor-pointer mt-1" type="checkbox" value="1">
          <p class="font-montserrat font-medium text-xs md:text-lg text-[#3C3C3C] leading-[110%]">
            Отправляя форму, вы соглашаетесь<br/>
            <span class="text-[#97AB94] underline cursor-pointer">на обработку персональных данных</span>
          </p>
        </div>

      </div>

      <div class="absolute top-[15px] right-4">
        <img @click="()=>emit('cartClose')" class="min-w-[22px] min-h-[22px] cursor-pointer" src="/image/icons/CloseBlack.svg" alt="">
      </div>
    </div>
  </div>


</template>

<script setup>
import CartButton from "@/components/ui/CartButton.vue";
import {reactive, ref} from "vue";
import api from "@/utils/api";
import { useToast } from "vue-toastification";


const emit = defineEmits(["cartClose"])

const personalData = ref(false);

const orderData = reactive({
  FIO: "",
  phone: "",
  email: "",
})

const errors = reactive({
  FIO: false,
  phone: false,
  email: false,
});

const inputClass = (hasError) => [
  "py-[18px] pl-[25px] border-b-2 placeholder:opacity-25 outline-none transition",
  hasError ? "border-red-500" : "border-[#D2D2D2]",
];

const validateForm = () => {
  errors.FIO = !orderData.FIO.trim();
  errors.phone = orderData.phone.replace(/\D/g, "").length !== 11;
  errors.email = !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(orderData.email);

  return !Object.values(errors).some(Boolean);
};

const toast = useToast();

const callbackOrder = async () => {
  if (!validateForm()) {
    toast.error("Заполните поля корректно", {
      position: "bottom-right",
      timeout: 3000
    })
    return;
  }

  if (!personalData) {
    toast.error("Пункт не отмечен", {
      position: "bottom-right",
      timeout: 3000
    })
    return;
  }

  try {
    const orderRequest = {
      FIO: orderData.FIO,
      phone: orderData.phone,
      email: orderData.email,
      typeCallbackRequest: 'SIMPLE'
    };

    const { data } = await api.post("/callbacks", orderRequest);

    Object.keys(orderData).forEach(key => {
      orderData[key] = '';
    });

    toast.success("Заявка успешно отправлена", {
      position: "bottom-right",
      timeout: 3000,
    });

    emit("cartClose");
  } catch (e) {
    console.error(e);
    toast.error("Ошибка отправки заявки", {
      position: "bottom-right",
      timeout: 3000,
    });
  }
}

</script>