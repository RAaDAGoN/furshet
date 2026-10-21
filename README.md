# 🍽️ Furshet (Фуршет) — Fullstack-платформа для кейтеринга и доставки фуршетных блюд

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-brightgreen?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?logo=postgresql&logoColor=white)
![Vue.js](https://img.shields.io/badge/Vue.js-3.5-4FC08D?logo=vuedotjs&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-7-646CFF?logo=vite&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.4-38B2AC?logo=tailwind-css&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-Reverse_Proxy-009639?logo=nginx&logoColor=white)

Коммерческий fullstack-проект для сервиса кейтеринга и доставки готовых фуршетных наборов. Проект включает в себя клиентское SPA-приложение для покупателей, REST API, встроенную панель администратора для управления каталогом и заказами, а также систему фоновых HTML-уведомлений по электронной почте.

---

## 📑 Содержание

- [✨ Особенности проекта](#-особенности-проекта)
- [🛠 Стек технологий](#-стек-технологий)
- [🏛 Архитектура и структура проекта](#-архитектура-и-структура-проекта)
- [🚀 Быстрый запуск](#-быстрый-запуск)
  - [1. Запуск через Docker Compose (рекомендуется)](#1-запуск-через-docker-compose-рекомендуется)
  - [2. Локальный запуск для разработки](#2-локальный-запуск-для-разработки)
- [🔒 Безопасность](#-безопасность)
- [📬 Контакты](#-контакты)

---

## ✨ Особенности проекта

### 🛍 Клиентская витрина (SPA)
- **Каталог и меню**: категоризация блюд, фильтрация, сезонные спецпредложения, просмотр готовых банкетных сетов.
- **Интерактивная карточка блюда**: галерея фотографий, бейджи («Хит», «Новинка»), состав, аллергены и динамические характеристики.
- **Корзина заказов**:
  - Быстрое изменение количества и удаление позиций.
  - Синхронизация и сохранение корзины в `localStorage`.
  - Оформление заказа с выбором способа получения (самовывоз / доставка по адресу) и способа оплаты.
- **Интерактивные формы**: заказ обратного звонка и отправка отзывов с валидацией.
- **UX/UI**:
  - Адаптивная верстка (Mobile-First, мобильное выкатное меню).
  - Слайдеры на Swiper.
  - Плавные микро-анимации на базе `@formkit/auto-animate`.
  - Всплывающие уведомления (Toasts) при действиях пользователя.
  - ЧПУ-ссылки (SEO-friendly транслитерация слагов категорий и блюд) и динамические meta-заголовки страниц.

### ⚙️ Панель администратора (Admin Dashboard)
- **Управление каталогом**: создание, редактирование и удаление товаров, категорий, меню и кейтеринг-услуг.
- **Медиа-менеджер**: загрузка и привязка нескольких изображений к товарам с сохранением на сервере.
- **Обработка заказов**: журнал входящих заказов, просмотр состава позиций, подсчет итоговой стоимости, контактных данных и адресов клиентов.
- **Модерация**: просмотр заявок на звонки и отзывов клиентов.
- **Управление уведомлениями**: настройка списка адресатов для получения оповещений.

### ✉️ Автоматизация и уведомления
- **Email-рассылка**: асинхронная отправка (`@Async`) сверстанных HTML-писем с детализацией заказов через Spring Mail и шаблонизатор Thymeleaf.
- **Telegram Bot**: заложенная архитектура для мгновенных оповещений администраторов в Telegram через Long Polling.

---

## 🛠 Стек технологий

### Backend
- **Язык**: Java 21
- **Фреймворк**: Spring Boot 4 / Spring Framework
- **Безопасность**: Spring Security (аутентификация администратора, защита CSRF, CORS-фильтрация, CSP-заголовки)
- **База данных и ORM**: PostgreSQL 17, Spring Data JPA, Hibernate ORM
- **Шаблонизация админки и писем**: Thymeleaf, Thymeleaf Layout Dialect
- **Почта**: Spring Boot Starter Mail (JavaMailSender)
- **Утилиты**: Project Lombok

### Frontend
- **Фреймворк**: Vue 3 (Composition API, `<script setup>`)
- **Сборщик**: Vite 7
- **Маршрутизация**: Vue Router 4 (HTML5 history mode, навигационные хуки для SEO-заголовков)
- **Стейт-менеджмент**: Pinia, Provide/Inject + LocalStorage
- **Стилизация**: Tailwind CSS 3, PostCSS, Autoprefixer
- **UI-библиотеки**: Swiper, FormKit AutoAnimate, Vue-Toastification, Axios

### Инфраструктура и DevOps
- **Docker & Docker Compose**: мульти-контейнерная оркестрация (PostgreSQL + Spring Boot + Vue SPA + Nginx)
- **Multi-stage builds**: раздельные фазы сборки для минимизации размера итоговых образов
- **Nginx**: обратный прокси-сервер (Reverse Proxy), обработка SSL/TLS сертификатов Let's Encrypt, маршрутизация поддоменов

---

## 🏛 Архитектура и структура проекта

```text
furshet/
├── backend/                  # Серверная часть на Spring Boot
│   ├── src/main/java/        # Исходный код Java
│   │   └── com/api/furshet/
│   │       ├── bot/          # Модуль интеграции с Telegram
│   │       ├── config/       # Конфигурация Spring Security, CORS, MVC
│   │       ├── controller/   # REST-контроллеры витрины и AdminController
│   │       ├── domain/       # JPA-сущности (Product, Order, Category и др.)
│   │       ├── dto/          # Data Transfer Objects
│   │       ├── mail/         # Сервис отправки HTML-писем
│   │       ├── repository/   # Spring Data JPA интерфейсы
│   │       └── service/      # Бизнес-логика приложения
│   ├── src/main/resources/
│   │   ├── templates/        # Шаблоны страниц админки и email-писем
│   │   ├── static/           # Статические стили и ассеты админки
│   │   └── application.properties
│   ├── Dockerfile            # Multi-stage сборка бэкенда (Maven + Temurin 21)
│   └── pom.xml
│
├── frontend/                 # Клиентская часть на Vue 3 + Vite
│   ├── src/
│   │   ├── assets/           # Глобальные стили и шрифты
│   │   ├── components/       # Переиспользуемые UI-компоненты и модальные окна
│   │   ├── pages/            # Страницы приложения (Главная, Меню, Корзина и др.)
│   │   ├── utils/            # Настройка Axios API и функции транслитерации слагов
│   │   ├── App.vue           # Корневой компонент и управление корзиной
│   │   └── main.js           # Точка входа, роутинг, глобальные плагины
│   ├── Dockerfile            # Multi-stage сборка (Node build + Nginx static)
│   └── package.json
│
├── nginx.conf                # Конфигурация Nginx для продакшена (SSL, проксирование)
└── docker-compose.yml        # Оркестрация сервисов (PostgreSQL, Backend, Frontend, Nginx)
```

---

## 🚀 Быстрый запуск

### 1. Запуск через Docker Compose (рекомендуется)

Убедитесь, что у вас установлены [Docker](https://www.docker.com/) и Docker Compose.

1. **Клонируйте репозиторий**:
   ```bash
   git clone https://github.com/RAaDAGoN/furshet.git
   cd furshet
   ```

2. **Запустите проект**:
   ```bash
   docker-compose up -d --build
   ```

3. **Сервисы будут доступны по следующим адресам**:
   - Клиентский сайт: `http://localhost` (или порт `3000`)
   - REST API & Панель администратора: `http://localhost:8000` (вход в админку: `/admin`)

---

### 2. Локальный запуск для разработки

#### База данных (PostgreSQL)
Создайте базу данных `furshet` в локальном экземпляре PostgreSQL:
```sql
CREATE DATABASE furshet;
```

#### Запуск Backend
1. Перейдите в каталог `backend`:
   ```bash
   cd backend
   ```
2. Убедитесь, что в `src/main/resources/application.properties` выбран dev-профиль:
   ```properties
   spring.profiles.active=dev
   ```
3. Соберите и запустите приложение (требуется Java 21 и Maven):
   ```bash
   ./mvnw spring-boot:run
   ```
   Сервер запустится на `http://localhost:8000`.

#### Запуск Frontend
1. Перейдите в каталог `frontend`:
   ```bash
   cd frontend
   ```
2. Установите зависимости:
   ```bash
   npm install
   ```
3. Запустите dev-сервер:
   ```bash
   npm run dev
   ```
   Интерфейс откроется по адресу `http://localhost:5173`.

---

## 🔒 Безопасность

- Аутентификация администратора защищена с использованием `BCryptPasswordEncoder`.
- Настроена защита от кликджекинга (`X-Frame-Options: DENY`) и гибкая политика `Content-Security-Policy`.
- Запросы на создание заказов и обратную связь защищены CORS-политикой и валидацией данных.

---

## 📬 Контакты

- **Автор**: Alex ([RAaDAGoN](https://github.com/RAaDAGoN))
- **GitHub**: [github.com/RAaDAGoN](https://github.com/RAaDAGoN)
- **Проект**: [furshet](https://github.com/RAaDAGoN/furshet)
