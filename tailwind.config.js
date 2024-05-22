/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./resources/public/js/main.js"],
  daisyui: {
    themes: ["light", "dark", "aqua", "synthwave", "retro"],
  },
  theme: {
    extend: {},
  },
  plugins: [require("@tailwindcss/forms"), require("daisyui")],
};
