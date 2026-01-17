<template>
  <div class="max-w-[1746px] mx-auto pt-10 px-5">
    <div class="font-montserrat font-medium text-2xl leading-[140%] text-[#3C3C3C] flex flex-col md:flex-row justify-between gap-3 md:gap-8">

      <!-- Первый блок -->
      <div class="flex flex-col gap-5 w-full md:min-w-[340px] md:max-w-[500px]">
        <h1 class="font-comfort font-bold text-4xl md:text-[60px] text-[#97AB94] mb-6 md:mb-10">Контакты</h1>

        <h1 class="font-comfort font-bold text-2xl md:text-[45px] border-b-[1.5px] pb-3 md:pb-5 border-[#3C3C3C]">г. Вологда</h1>

        <ul class="flex flex-col gap-4 md:gap-6 text-lg md:text-2xl">
          <li>ул. Фуршетная, 2, оф. 55</li>
          <li>10:00–21:00</li>
          <li>+7 (817) 393-47-47</li>
          <li>furshet@gmail.ru</li>
        </ul>

        <div class="flex gap-4">
          <img src="/image/icons/telegram.svg" alt="" class="w-8 h-8 md:w-auto md:h-auto">
          <img src="/image/icons/whatsapp.svg" alt="" class="w-8 h-8 md:w-auto md:h-auto">
          <img src="/image/icons/viber.svg" alt="" class="w-8 h-8 md:w-auto md:h-auto">
        </div>

      </div>

      <!-- Блок с картой - ИСПРАВЛЕННЫЙ -->
      <div class="w-full md:flex-1 mt-6 md:mt-0">
        <div class="relative w-full h-[400px] md:h-full rounded-lg">
          <div class="absolute inset-0 w-full h-full">
            <a href="https://yandex.ru/maps/21/vologda/?utm_medium=mapframe&utm_source=maps" class="text-[#eee] text-xs absolute top-0 left-0 pointer-events-none">Вологда</a>
            <a href="https://yandex.ru/maps/21/vologda/?ll=39.883733%2C59.207420&mode=whatshere&utm_medium=mapframe&utm_source=maps&whatshere%5Bpoint%5D=39.883139%2C59.207343&whatshere%5Bzoom%5D=18.6&z=18.6" class="text-[#eee] text-xs absolute top-[14px] left-0 pointer-events-none">Площадь Бабушкина — Яндекс Карты</a>
            <iframe
                src="https://yandex.ru/map-widget/v1/?ll=39.883733%2C59.207420&mode=whatshere&whatshere%5Bpoint%5D=39.883139%2C59.207343&whatshere%5Bzoom%5D=18.6&z=18.6"
                class="absolute top-0 left-0 w-full h-full border-0"
                allowfullscreen
            ></iframe>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>