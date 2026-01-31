<template>

  <div class="bg-[#303030]">
    <div class="flex flex-col md:flex-row px-5 justify-between max-w-[1746px] mx-auto pt-5 pb-5 md:pt-12 md:pb-16">

      <!-- контакты -->
      <div class="font-montserrat">
        <h1 class="text-[55px] font-semibold text-white">Фуршет</h1>

        <div class="flex flex-col gap-5 mb-5 md:mb-9 text-white">
          <p class="text-[22px]">г. Вологда, ул. Фуршетная</p>
          <p class="text-[18px] text-[#595959]">Ежедневно 24/7</p>
          <p class="text-[18px] text-[#595959]">Поддержка с 10:00 до 21:00</p>
        </div>

        <div class="hidden md:flex gap-4 items-center">
          <a href="https://vk.com/furshet_vol35" target="_blank">
            <img src="/image/icons/vk.svg" class="w-8 h-8 object-contain" alt="vk" />
          </a>

<!--          <img src="/image/icons/ok.svg" class="w-8 h-8 object-contain" />-->
<!--          <img src="/image/icons/dzen.svg" class="w-8 h-8 object-contain" />-->
<!--          <img src="/image/icons/telegram.svg" class="w-8 h-8 object-contain" />-->
<!--          <img src="/image/icons/whatsapp.svg" class="w-8 h-8 object-contain" />-->
<!--          <img src="/image/icons/viber.svg" class="w-8 h-8 object-contain" />-->
        </div>
      </div>

      <nav>
        <ul class="hidden md:block font-montserrat font-semibold text-[21px] md:flex flex-col gap-6 text-[#FFFFFF] ">
          <router-link to="/about">
            <li class="hover:opacity-50">О нас</li>
          </router-link>
          <router-link to="/catering">
            <li class="hover:opacity-50">Кейтеринг</li>
          </router-link>
          <router-link to="/menu">
            <li class="hover:opacity-50">Меню</li>
          </router-link>
          <router-link to="/delivery">
            <li class="hover:opacity-50">Доставка</li>
          </router-link>
          <router-link to="/contacts">
            <li class="hover:opacity-50">Контакты</li>
          </router-link>
          <router-link to="/cart">
            <li class="hover:opacity-50">Корзина</li>
          </router-link>
        </ul>
      </nav>

      <div class="font-montserrat font-medium text-[#A2A2A2] text-[18px] ">
        <h1 class="text-xl w-full border-b border-[#595959] mb-2 pb-2 text-white text-[21px]">Помощь</h1>

        <div class="flex flex-col gap-[30px] md:flex-row justify-between">
          <ul class="flex flex-col gap-[18px] ">
            <li class="hover:text-[#FFFFFF] cursor-pointer">Оплата</li>
            <li class="hover:text-[#FFFFFF] cursor-pointer">Гарантия качества</li>
            <li class="hover:text-[#FFFFFF] cursor-pointer">Возврат товара</li>
          </ul>

          <ul class="flex flex-col gap-[18px]">
            <li class="hover:text-[#FFFFFF] cursor-pointer">Персональные данные</li>
            <li class="hover:text-[#FFFFFF] cursor-pointer">Конфиденциальность</li>
            <li class="hover:text-[#FFFFFF] cursor-pointer">Публичная оферта</li>
          </ul>
        </div>
      </div>

      <div class="font-montserrat text-white mt-5 text-lg md:text-2xl">
        <a href="tel:70000000000">
          <h1 class="mb-3">+7 (817) 393-47-47</h1>
        </a>

        <a href="mailto:furshet@gmail.com" type="email">
          <h1>furshet@gmail.com</h1>
        </a>

      </div>
    </div>
  </div>


</template>

<script setup>

import Button from "@/components/ui/Button.vue";
</script>