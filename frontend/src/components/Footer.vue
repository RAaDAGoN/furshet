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
          <img src="/image/icons/vk.svg" class="w-8 h-8 object-contain" />
          <img src="/image/icons/ok.svg" class="w-8 h-8 object-contain" />
          <img src="/image/icons/dzen.svg" class="w-8 h-8 object-contain" />
          <img src="/image/icons/telegram.svg" class="w-8 h-8 object-contain" />
          <img src="/image/icons/whatsapp.svg" class="w-8 h-8 object-contain" />
          <img src="/image/icons/viber.svg" class="w-8 h-8 object-contain" />
        </div>
      </div>

      <nav>
        <ul class="hidden md:flex flex-col gap-6 text-[#F4F6FB] font-montserrat">
          <router-link to="/about">
            <li>О нас</li>
          </router-link>
          <router-link to="/menu">
            <li>Меню</li>
          </router-link>
          <router-link to="/delivery">
            <li>Доставка</li>
          </router-link>
          <router-link to="/contacts">
            <li>Контакты</li>
          </router-link>
          <router-link to="/cart">
            <li>Корзина</li>
          </router-link>
        </ul>
      </nav>

      <div class="font-montserrat text-[#595959]">
        <h1 class="text-xl w-full border-b border-[#5F6577] mb-2 pb-2 text-white">Помощь</h1>

        <div class="flex flex-col gap-4 md:flex-row justify-between">
          <ul class="flex flex-col gap-4">
            <li>Оплата</li>
            <li>Гарантия качества</li>
            <li>Возврат товара</li>
          </ul>

          <ul class="flex flex-col gap-4">
            <li>Персональные данные</li>
            <li>Конфиденциальность</li>
            <li>Публичная оферта</li>
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