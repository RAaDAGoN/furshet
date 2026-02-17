<template>

  <footer class="bg-[#303030]">
    <div class="flex flex-col lg:flex-row px-5 justify-between max-w-[1746px] mx-auto pt-5 pb-5 md:pt-12 md:pb-16">

      <!-- контакты -->
      <div class="flex flex-col lg:flex-row md:gap-[20px] lg:gap-[100px]">
        <div class="font-montserrat">
          <h1 class="text-[55px] font-semibold text-white">Фуршет</h1>

          <div class="flex flex-col gap-5 mb-5 md:mb-9 text-white mt-5">
            <p><a class="flex gap-[15px]" href="https://yandex.ru/maps/-/CPAhzVjR" target="_blank">г. Вологда, ул. Мира 96</a></p>
            <p class="flex gap-[15px]">Круглосуточно</p>
          </div>

          <div class="flex gap-4 items-center mb-[20px]">
            <a href="https://vk.com/furshet_vol35" target="_blank">
              <svg class="w-[36px] h-[36px] text-[#595959] hover:text-[#97AB94] transition-colors">
                <use href="#vk"></use>
              </svg>
            </a>
            <a href="https://t.me/89005068151" target="_blank">
              <svg class="w-[36px] h-[36px] text-[#595959] hover:text-[#97AB94] transition-colors">
                <use href="#tg"></use>
              </svg>
            </a>
            <a href="https://web.max.ru/89005068151" target="_blank">
              <svg class="w-[36px] h-[36px] text-[#595959] hover:text-[#97AB94] transition-colors">
                <use href="#max"></use>
              </svg>
            </a>
          </div>
        </div>

        <nav>
          <ul class="hidden font-montserrat font-semibold text-[21px] md:flex flex-col gap-6 text-[#FFFFFF] ">
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
      </div>


      <div class="hidden font-montserrat font-medium text-[#A2A2A2] text-[18px] ">
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

      <div class="font-montserrat text-white text-lg md:text-2xl flex flex-col">
        <div>
          <a class="flex gap-[15px] mb-3" href="tel:89005068151">8 (900) 506-81-51</a>

          <a class="flex gap-[15px]" href="mailto:furshet_vol35@mail.ru">furshet_vol35@mail.ru</a>
        </div>

        <div class="mt-auto font-montserrat font-semibold text-[#A2A2A2] text-[18px] flex flex-col justify-between gap-[12px] pt-[20px]">
          <p>Сайт разработан: <a href="https://t.me/Radag0n0" target="_blank">@Radag0n0</a></p>
          <p>Дизайн создан: <a href="https://t.me/DarkAlice23" target="_blank">@DarkAlice23</a></p>
        </div>
      </div>


    </div>


  </footer>
</template>

<script setup>

import Button from "@/components/ui/Button.vue";
</script>