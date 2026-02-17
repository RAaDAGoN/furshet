<template>
  <div class="max-w-[1746px] mx-auto pt-10 px-5">
    <div
        class="font-montserrat font-medium text-2xl leading-[140%] text-[#3C3C3C] flex flex-col lg:flex-row justify-between gap-3 md:gap-8">

      <div class="flex flex-col gap-5 w-full md:min-w-[340px] md:max-w-[500px]">
        <h1 class="font-comfort font-bold text-4xl md:text-[60px] text-[#97AB94] mb-6 md:mb-10">Контакты</h1>

        <h1 class="font-comfort font-bold text-2xl md:text-[45px] border-b-[1.5px] pb-3 md:pb-5 border-[#3C3C3C]">г.
          Вологда</h1>

        <ul class="flex flex-col gap-4 md:gap-6 text-lg md:text-2xl">
          <li><a class="flex gap-[15px]" href="https://yandex.ru/maps/-/CPAhzVjR" target="_blank"><img src="/image/icons/Map.svg" alt="MAP">г. Вологда, ул. Мира 96</a> </li>
          <li class="flex gap-[15px]"><img src="/image/icons/Time.svg" alt="TIME">Круглосуточно</li>
          <li><a class="flex gap-[15px]" href="tel:89005068151"><img src="/image/icons/Phone.svg" alt="PHONE">8 (900) 506-81-51</a></li>
          <li><a class="flex gap-[15px]" href="mailto:furshet_vol35@mail.ru"><img src="/image/icons/Mail.svg" alt="MAIL">furshet_vol35@mail.ru</a></li>
        </ul>

        <div class="flex gap-4">
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

      <div class="w-full lg:flex-1 mt-6 md:mt-0">
        <div class="relative w-full h-[400px] lg:h-full rounded-lg">
          <div class="absolute inset-0 w-full h-full">
            <a href="https://yandex.ru/maps/21/vologda/?utm_medium=mapframe&utm_source=maps"
               class="text-[#eee] text-xs absolute top-0 left-0 pointer-events-none"></a>
            <a href="https://yandex.ru/maps/21/vologda/house/ulitsa_mira_96/Z0AYfg9gSUIPQFtjfX5xeH5nZA==/?ll=39.881146%2C59.208909&utm_medium=mapframe&utm_source=maps&z=18.2"
               class="text-[#eee] text-xs absolute top-[14px] left-0 pointer-events-none"></a>
            <iframe
                src="https://yandex.ru/map-widget/v1/?ll=39.881146%2C59.208909&mode=whatshere&whatshere%5Bpoint%5D=39.879918%2C59.209084&whatshere%5Bzoom%5D=17&z=18.2"
                class="absolute top-0 left-0 w-full h-full border-0"
                allowfullscreen
            ></iframe>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>
<script setup lang="ts">
</script>