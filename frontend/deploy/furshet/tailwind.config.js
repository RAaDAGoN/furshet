/** @type {import('tailwindcss').Config} */

export default {
    content: [
        "./index.html",
        "./src/**/*.{vue,js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            fontFamily: {
                montserrat: ['Montserrat'],
                comfort: ['Comfortaa', 'sans-serif'],

            },
            screens: {
                'sm': '380px',
                'md': '768px',
                'lg': '1024px',
                'xl': '1440px',
            }
        },

    },
    plugins: [],
}

