<template>
  <div class="max-w-[1746px] mx-auto p-5 2xl:pt-5 3xl:pt-10" >
    <h1 class="font-comfort font-bold text-[27px] 2xl:text-[45px] 3xl:text-[60px] text-[#97AB94] mb-[10px] md:mb-10">Оформление заказа</h1>

    <div v-if="localOrderSuccess === null">
      <div v-if="cart.length === 0" class="flex flex-col items-center justify-center text-sm md:text-[#3C3C3C]">
        <img class="mb-5" src="/image/CartEmpty.jpg" alt="">

        <h1 class="font-comfort font-bold text-2xl md:text-[35px] mb-4">В корзине пока пусто</h1>

        <p class="font-montserrat font-medium text-base md:text-2xl text-center leading-[130%] md:leading-[140%] mb-10">
          Вы можете воспользоваться каталогом, чтобы добавить нужную позицию.<br/>Желаем приятных покупок!</p>

        <router-link to="/menu">
          <CartButton title="В меню"/>
        </router-link>
      </div>

      <div v-else class="flex flex-col xl:flex-row justify-between md:items-start">
        <div class="w-full max-w-[1255px]">
          <!--          Форма-->
          <div class="flex flex-col gap-5">

            <div class="border border-[#D2D2D2] rounded-[20px] py-[24px] px-[20px] 2xl:py-[35px] 2xl:px-[40px]">
              <h2 class="font-comfort font-bold text-[20px] md:text-4xl text-[#2E2F2A] mb-2 md:mb-[30px]">1. Ваши контактные данные</h2>
              <div class="flex flex-col 3xl:flex-row justify-between 2xl:gap-[20px]">
                <OrderInput placeholder="Фамилия и имя" name="FIO" v-model="orderForm.FIO" :class="inputClass(errors.FIO)"/>
                <OrderInput placeholder="Телефон" name="phone" v-model="orderForm.phone" :class="inputClass(errors.phone)"/>
                <OrderInput placeholder="Почта" name="email" v-model="orderForm.email" :class="inputClass(errors.email)"/>
              </div>
            </div>

            <div class="border border-[#D2D2D2] rounded-[20px] py-[24px] px-[20px] 2xl:py-[35px] 2xl:px-[40px]">
              <h2 class="font-comfort font-bold text-[20px] md:text-4xl text-[#2E2F2A] mb-2 md:mb-[30px]">2. Адрес доставки</h2>

              <div class="flex flex-col justify-between md:flex-row gap-5 lg:gap-11 lg:justify-normal">
                <button
                    @click="orderForm.delivery = 'PICKUP'"
                    :class="[
                      'border-2 rounded-2xl font-montserrat text-xl 2xl:text-2xl py-[15px] px-[25px] flex flex-col min-w-[0px] md:min-w-[330px] 2xl:min-w-[350px] justify-center items-start min-h-[100px]',
                      orderForm.delivery === 'PICKUP'
                        ? 'bg-[#EBF0E8] border-[#97AB94]'
                        : 'bg-white border-[#D2D2D2]'
                    ]">

                  <span>Самовывоз</span>
                  <span class="font-comfort text-[18px] mt-[10px]">Бесплатно</span>
                </button>

                <button
                    @click="orderForm.delivery = 'COURIER'"
                    :class="[
                      'border-2 rounded-2xl font-montserrat text-xl 2xl:text-2xl py-[15px] px-[25px] flex flex-col min-w-[0px] md:min-w-[330px] 2xl:min-w-[350px] justify-center items-start min-h-[100px]',
                      orderForm.delivery === 'COURIER'
                        ? 'bg-[#EBF0E8] border-[#97AB94]'
                        : 'bg-white border-[#D2D2D2]'
                    ]">
                  <span>Курьером</span>
                </button>
              </div>

              <div class="flex flex-col gap-5 max-w-[1000px] mt-[30px]">
                <div v-if="orderForm.delivery === 'COURIER'" class="flex flex-col justify-between gap-5">
                  <OrderInput placeholder="Город / населённый пункт" name="city" v-model="orderForm.city" :class="inputClass(errors.city)"/>
                  <OrderInput placeholder="Адрес" name="address" v-model="orderForm.address" :class="inputClass(errors.address)"/>
                </div>

                <OrderInput placeholder="Комментарий" name="comment" v-model="orderForm.comment"/>
              </div>
            </div>

            <div class="border border-[#D2D2D2] rounded-[20px] py-[24px] px-[20px] 2xl:py-[35px] 2xl:px-[40px]">
              <h2 class="font-comfort font-bold text-[20px] md:text-4xl text-[#2E2F2A] mb-2 md:mb-[30px]">3. Способы оплаты</h2>
              <div class="flex flex-col justify-between md:flex-row gap-5 lg:gap-11 lg:justify-normal">
                <button
                    @click="orderForm.paymentMethod = 'CASH'"
                    :class="[
                      'border-2 rounded-2xl font-montserrat text-xl 2xl:text-2xl py-[15px] px-[25px] flex flex-col min-w-[0px] md:min-w-[330px] 2xl:min-w-[350px] justify-center items-start min-h-[100px]',
                      orderForm.paymentMethod === 'CASH'
                        ? 'bg-[#EBF0E8] border-[#97AB94]'
                        : 'bg-white border-[#D2D2D2]'
                    ]">
                  <span>Наличными средствами</span>
                </button>

                <button
                    @click="orderForm.paymentMethod = 'CARD'"
                    :class="[
                     'border-2 rounded-2xl font-montserrat text-xl 2xl:text-2xl py-[15px] px-[25px] flex flex-col min-w-[0px] md:min-w-[330px] 2xl:min-w-[350px] justify-center items-start min-h-[100px]',
                      orderForm.paymentMethod === 'CARD'
                        ? 'bg-[#EBF0E8] border-[#97AB94]'
                        : 'bg-white border-[#D2D2D2]'
                    ]">
                  <span>Банковской картой</span>
                </button>
              </div>
            </div>

          </div>
        </div>

        <div class="xl:ml-12 mt-[30px] xl:mt-0 border border-[#ECEBE4] rounded-[15px] p-[32px] w-full 2xl:min-w-[455px] 2xl:max-w-[500px]">
          <h1 class="font-comfort font-bold text-16px md:text-[30px] text-[#2E2F2A] mb-3">Состав заказа</h1>
          <div class="flex flex-col justify-between gap-5 py-5 border-y-[2px] border-[#ECEBE4] font-montserrat mb-10 text-[#2E2F2A]">
            <div class="flex justify-between text-base md:text-[22px]">

              <table class="w-full">
                <tbody class="w-full">
                  <tr class="flex justify-between"  v-for="item in cart" :key="item.id" >
                    <td class="text-[14px] md:text-[21px]">
                      {{item.name}}
                    </td>
                    <td>
                      <div class="font-montserrat text-[15px] md:text-lg">
                        {{item.quantity}} шт. х
                        <span class="font-bold">{{formatPrice(item.price)}} ₽</span>
                      </div>

                    </td>

                  </tr>

                </tbody>
              </table>
            </div>

            <div class="flex justify-between text-base md:text-[22px]" v-if="orderForm.delivery === 'COURIER'">
              <table class="w-full">
                <tbody class="w-full" >
                <tr class="flex justify-between">
                  <td>
                    Доставка:
                  </td>
                  <td class="font-montserrat font-bold">
                    250 ₽
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>

          <div class="font-comfort font-bold flex justify-between text-base md:text-[22px] items-center">

            <table class="w-full">
              <tbody class="w-full">
              <tr class="flex justify-between">
                <td class="text-base md:text-[25px]">
                  Итого:
                </td>
                <td class="font-montserrat text-base sm:text-[18px] md:text-[24px] font-semibold">
                  {{ totalCartPrice(totalPrice) }} ₽
                </td>
              </tr>
              </tbody>
            </table>
          </div>

          <div class="flex items-start space-x-3 mt-4">
            <div class="relative inline-flex items-center">
              <input
                  v-model="personalData"
                  class="appearance-none w-6 h-6 border-2 border-[#97AB94] rounded checked:bg-[#97AB94] cursor-pointer mt-1"
                  type="checkbox"
                  value="1"
                  id="personal-data"
              >
              <!-- Кастомная галочка -->
              <svg
                  v-if="personalData"
                  class="absolute w-4 h-4 left-1 top-[8px] text-white pointer-events-none"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="3"
              >
                <path d="M20 6L9 17L4 12" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </div>
            <label for="personal-data" class="font-montserrat font-medium text-xs md:text-lg text-[#3C3C3C] leading-[110%] cursor-pointer">
              Оформляя заказ, вы соглашаетесь, что ознакомлены с
              <router-link to="/delivery" class="text-[#97AB94] underline cursor-pointer ml-1">
                условиями доставки
              </router-link>
            </label>
          </div>

          <CartButton @click="submitOrder" :in-cart="false" class="mx-auto w-full mt-7" title="Оформить заказ"/>
        </div>
      </div>
    </div>

    <div v-else-if="localOrderSuccess === 'finally'">
      <div class="flex flex-col items-center justify-center text-sm md:text-[#3C3C3C]">
        <img class="mb-5" src="/image/icons/OrderSuccess.svg" alt="">

        <h1 class="font-comfort font-bold text-2xl md:text-[35px] mb-4">Заявка отправлена!</h1>

        <p class="font-montserrat font-medium text-base md:text-2xl text-center leading-[130%] md:leading-[140%] mb-10">
          Наш менеджер свяжется с вами перед отправкой!</p>

        <router-link to="/">
          <CartButton title="На главную"/>
        </router-link>

      </div>
    </div>

    <div v-else-if="localOrderSuccess === 'error'">
      <div class="flex flex-col items-center justify-center text-sm md:text-[#3C3C3C]">
        <img class="mb-5" src="/image/icons/OrderError.svg" alt="">

        <h1 class="font-comfort font-bold text-2xl md:text-[35px] mb-4">Заявка не отправлена!</h1>

        <p class="font-montserrat font-medium text-base md:text-2xl text-center leading-[130%] md:leading-[140%] mb-10">
          Произошла ошибка, попробуйте ещё раз</p>

        <router-link to="/">
          <CartButton title="На главную"/>
        </router-link>

      </div>
    </div>

  </div>
</template>

<script setup>
import {computed, inject, onMounted, reactive, ref, watch} from "vue";
import CartButton from "@/components/ui/CartButton.vue";
import OrderInput from "@/components/ui/OrderInput.vue";
import {useToast} from "vue-toastification";

const {cart, totalPrice, countCart, createOrder, orderSuccess} = inject("cart");
const personalData = ref(false);
const localOrderSuccess = computed(() => orderSuccess.value);
const toast = useToast();

const orderForm = reactive({
  FIO: "",
  phone: "",
  email: "",
  delivery: "PICKUP",
  city: "",
  address: "",
  comment: "",
  paymentMethod: "CASH",
})

// Ошибки
const errors = reactive({
  FIO: false,
  phone: false,
  email: false,
  city: false,
  address: false,
});

// Класс для input
const inputClass = (hasError) => [
  "py-[18px] pl-[25px] border-b-2 outline-none transition",
  hasError ? "border-red-500" : "border-[#D2D2D2]",
];

// Валидация формы
const validateForm = () => {
  errors.FIO = !orderForm.FIO.trim();
  errors.phone = orderForm.phone.replace(/\D/g, "").length !== 11; // только 11 цифр
  errors.email = !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(orderForm.email);

  // если доставка курьером
  if (orderForm.delivery === "COURIER") {
    errors.city = !orderForm.city.trim();
    errors.address = !orderForm.address.trim();
  } else {
    errors.city = false;
    errors.address = false;
  }

  return !Object.values(errors).some(Boolean);
};

// Отправка заказа
const submitOrder = async () => {
  if (!validateForm()) {
    toast.error("Пожалуйста, заполните все обязательные поля корректно.", { position: "bottom-right" });
    return;
  }

  if (!personalData.value) {
    toast.error("Пункт не отмечен", {
      position: "bottom-right",
      timeout: 3000
    })
    return;
  }

  try {
    await createOrder(orderForm); // вызываем функцию из inject
    toast.success("Заказ успешно оформлен!", { position: "bottom-right" });

    // очищаем форму
    Object.keys(orderForm).forEach(key => {
      orderForm[key] = key === "delivery" ? "PICKUP" : key === "paymentMethod" ? "CASH" : key === "personalData" ? false : "";
    });
  } catch (e) {
    toast.error("Ошибка отправки заказа. Попробуйте ещё раз.", { position: "bottom-right" });
  }
};

onMounted(() => {
  orderSuccess.value = null;

  const savedForm = localStorage.getItem("orderForm");
  if (savedForm) {
    Object.assign(orderForm, JSON.parse(savedForm));
  }
});

watch(orderForm, () => {
  localStorage.setItem("orderForm", JSON.stringify(orderForm));
}, {deep: true});

const formatPrice = (price) => {
  return new Intl.NumberFormat('ru-RU').format(price)
}

const totalCartPrice = (price) => {
  if (orderForm.delivery === "COURIER"){
    return formatPrice(price + 250);
  }
  return formatPrice(price);
}

</script>

