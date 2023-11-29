<template>
  <div>
    <div>bot昵称：{{ bot_name }}</div>
    <div>bot积分：{{ bot_rating }}</div>
  </div>
  <router-view></router-view>
</template>

<script>
import $ from "jquery";
import { ref } from "vue";

export default {
  name: "App",
  // 整个函数的入口
  setup: () => {
    let bot_name = ref("");
    let bot_rating = ref("");

    $.ajax({
      url: "http://localhost:6221/pk/getInfo/",
      type: "get",
      success: resp => {
        console.log(resp);
        bot_name.value = resp.name;
        bot_rating.value = resp.rating;
      }
    });

    return {
      bot_name,
      bot_rating
    };
  }
};
</script>

<style>
body {
  background-image: url("@/assets/background.png");
  background-size: cover;
  /* 百分之百填充 */
}
</style>
