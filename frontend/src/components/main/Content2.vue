<template>
  <div class="max-w-[1746px] mx-auto h-full flex flex-col md:flex-row items-center justify-between mt-12 md:mt-[110px] ">

    <div class="px-4 sm:px-5">
      <img src="/image/Content2.png" alt="content1" />
    </div>

    <div class="max-w-[855px] flex flex-col  gap-5 px-4 sm:px-5">
      <div>
        <h1 class="font-comfort font-bold text-[#97AB94] text-[28px] md:text-[60px] uppercase mt-10 md:mt-0">Доставка по всей вологде</h1>
        <p class="font-montserrat font-medium text-[#3C3C3C] text-[18px] md:text-2xl">Мы понимаем, что время — это ценный ресурс. <br/>Именно поэтому предлагаем удобную и быструю доставку вашего фуршета прямо к месту проведения мероприятия.</p>
      </div>

      <div>
        <h1 class="font-comfort font-bold text-[#97AB94] text-[22px] md:text-[45px] mb-5">Преимущества нашей доставки</h1>

        <div class="grid md:grid-cols-2 md:grid-rows-2 gap-6">

          <div class="border border-[#97AB94] rounded-[15px] px-5 md:px-6 pt-[10px] pb-4 font-montserrat font-medium text-[#3C3C3C]">
            <h1 class="text-[21px]">Оперативность:</h1>
            <p class="text-[14px]">Наши курьеры доставят фуршет в точно указанное время, чтобы все было готово к началу вашего события.</p>
          </div>

          <div class="border border-[#97AB94] rounded-[15px] px-5 md:px-6 pt-[10px] pb-4 font-montserrat font-medium text-[#3C3C3C]">
            <h1 class="text-[21px]">Аккуратность:</h1>
            <p class="text-[14px]">Мы заботимся о сохранности каждого блюда, используя специальные контейнеры и упаковку.</p>
          </div>

          <div class="border border-[#97AB94] rounded-[15px] px-5 md:px-6 pt-[10px] pb-4 font-montserrat font-medium text-[#3C3C3C]">
            <h1 class="text-[21px]">Гибкость:</h1>
            <p class="text-[14px]">Мы готовы доставить фуршет в любое удобное для вас место и время,  чтобы вы могли сосредоточиться на мероприятии.</p>
          </div>

          <div class="md:flex items-center justify-center">
            <router-link to="/menu">
              <h1 class="font-comfort font-semibold text-[#97AB94] text-2xl flex justify-center items-center gap-[15px] group hover:cursor-pointer">
                Заказать доставку
                <img
                    width="36"
                    height="15"
                    src="/image/icons/arrow.svg"
                    alt="Next arrow"
                    class="transition-transform duration-300 group-hover:translate-x-2"
                >
              </h1>
            </router-link>

          </div>

        </div>


      </div>

    </div>


  </div>
</template>

<script setup>

</script>
