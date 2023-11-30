<template>
  <NavBar/>
  <!-- 路由配置 -->
  <router-view></router-view>
</template>

<script>
import NavBar from "./components/NavBar.vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap";

export default {
  components: {
    NavBar
  }
};
</script>

<style>
body {
  background-image: url("./assets/images/bear.jpg");
  background-size: cover; /* 百分之百填充 */
}
</style>
