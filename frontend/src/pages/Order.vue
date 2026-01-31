<template>
  <div class="max-w-[1746px] mx-auto p-5 pt-10" >
    <h1 class="font-comfort font-bold text-[27px] md:text-[60px] text-[#97AB94] mb-[10px] md:mb-10">Оформление заказа</h1>

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

      <div v-else class="flex flex-col md:flex-row justify-between md:items-start">
        <div class="w-full max-w-[1255px]">
          <!--          Форма-->
          <div class="flex flex-col gap-5">

            <div class="border border-[#D2D2D2] rounded-[20px] py-4 px-4 md:py-[35px] md:px-[40px]">
              <h2 class="font-comfort font-bold text-[20px] md:text-4xl text-[#2E2F2A] mb-2 md:mb-[30px]">1. Ваши контактные данные</h2>
              <div class="flex flex-col md:flex-row justify-between">
                <OrderInput placeholder="Фамилия и имя" name="FIO" v-model="orderForm.FIO"/>
                <OrderInput placeholder="Телефон" name="phone" v-model="orderForm.phone"/>
                <OrderInput placeholder="Почта" name="email" v-model="orderForm.email"/>
              </div>
            </div>

            <div class="border border-[#D2D2D2] rounded-[20px] py-4 px-4 md:py-[35px] md:px-[40px]">
              <h2 class="font-comfort font-bold text-[20px] md:text-4xl text-[#2E2F2A] mb-2 md:mb-[30px]">2. Адрес доставки</h2>

              <div class="flex flex-col md:flex-row gap-4 md:gap-11">
                <button
                    @click="orderForm.delivery = 'PICKUP'"
                    :class="[
                      'border-2 rounded-2xl font-montserrat text-xl md:text-2xl py-[18px] px-[25px] flex flex-col md:min-w-[350px] justify-center items-start min-h-[100px]',
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
                      'border-2 rounded-2xl font-montserrat text-xl md:text-2xl py-[18px] px-[25px] flex flex-col md:min-w-[350px] justify-center items-start min-h-[100px]',
                      orderForm.delivery === 'COURIER'
                        ? 'bg-[#EBF0E8] border-[#97AB94]'
                        : 'bg-white border-[#D2D2D2]'
                    ]">
                  <span>Курьером</span>
                </button>
              </div>

              <div class="flex flex-col gap-5 max-w-[1000px] mt-[30px]">
                <div v-if="orderForm.delivery === 'COURIER'" class="flex flex-col justify-between gap-5">
                  <OrderInput placeholder="Город / населённый пункт" name="city" v-model="orderForm.city"/>
                  <OrderInput placeholder="Адрес" name="address" v-model="orderForm.address"/>
                </div>

                <OrderInput placeholder="Комментарий" name="comment" v-model="orderForm.comment"/>
              </div>
            </div>

            <div class="border border-[#D2D2D2] rounded-[20px] py-4 px-4 md:py-[35px] md:px-[40px]">
              <h2 class="font-comfort font-bold text-[20px] md:text-4xl text-[#2E2F2A] mb-2 md:mb-[30px]">3. Способы оплаты</h2>
              <div class="flex flex-col md:flex-row gap-4 md:gap-11">
                <button
                    @click="orderForm.paymentMethod = 'CASH'"
                    :class="[
                      'border-2 rounded-2xl font-montserrat text-xl md:text-2xl py-[18px] px-[25px] flex flex-col md:min-w-[350px] justify-center items-start min-h-[100px] text-left',
                      orderForm.paymentMethod === 'CASH'
                        ? 'bg-[#EBF0E8] border-[#97AB94]'
                        : 'bg-white border-[#D2D2D2]'
                    ]">
                  <span>Наличными средствами</span>
                </button>

                <button
                    @click="orderForm.paymentMethod = 'CARD'"
                    :class="[
                      'border-2 rounded-2xl font-montserrat text-xl md:text-2xl py-[18px] px-[25px] flex flex-col md:min-w-[350px] justify-center items-start min-h-[100px] text-left',
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

        <div class="md:ml-12 mt-12 md:mt-0 border border-[#ECEBE4] rounded-[15px] p-[32px] md:min-w-[455px]">
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
                      <div class="font-montserrat text-[14px] md:text-lg">
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
                    200 ₽
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
                <td class="font-montserrat text-base md:text-[24px] font-semibold">
                  {{ formatPrice(totalPrice) }} ₽
                </td>
              </tr>
              </tbody>
            </table>
          </div>


          <CartButton @click="createOrder(orderForm)" class="mx-auto w-full mt-7" title="Оформить заказ"/>

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
import {computed, inject, onMounted, reactive, watch} from "vue";
import CartItem from "@/pages/CartItem.vue";
import CartButton from "@/components/ui/CartButton.vue";
import OrderInput from "@/components/ui/OrderInput.vue";

const {cart, totalPrice, countCart, createOrder, orderSuccess} = inject("cart");

const localOrderSuccess = computed(() => orderSuccess.value);


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

</script>

