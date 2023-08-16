<template>
  <div>
    <div>bot序号：{{ bot_id }}</div>
    <div>bot昵称：{{ bot_name }}</div>
  </div>
  <router-view></router-view>
</template>

<script>
import $ from 'jquery';
import { ref } from 'vue';

export default {
  name: "App",
  // 函数入口
  setup: () => {
    let bot_id = ref("");
    let bot_name = ref("");

    $.ajax({
      url: "http://localhost:6221/pk/getMap/",
      type: "get",
      success: resp => {
        console.log(resp);
        bot_id.value = resp.id;
        bot_name.value = resp.name;
      }
    });

    return {
      bot_id,
      bot_name
    }
  }
}
</script>

<style>
body {
  background-image: url("@/assets/background.png");
  background-size: cover;
}
</style>
