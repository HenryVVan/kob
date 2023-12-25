<template>
  <NavBar/>
  <!-- 路由配置 -->
  <router-view></router-view>
</template>

<script>
import NavBar from "./components/NavBar.vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap";
// import $ from "jquery";

export default {
  components: {
    NavBar
  },
  setup() {
    // $.ajax({
    //   url: "http://localhost:6221/user/account/info/",
    //   type: "get",
    //   headers: {
    //     Authorization:
    //       "Bearer " +
    //       "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjAwN2NhMDEyYmQ0MWVhODZmNTQ4ZGM0MjJhZTcwOCIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTcwMzI1MjAxNiwiZXhwIjoxNzA0NDYxNjE2fQ.POx9UNUY5RwtseuD4CyxP0fTx8m6UHHaeRJVBPWYFxs"
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }
    // });
    // $.ajax({
    //   url: "http://localhost:6221/user/account/register/",
    //   type: "post",
    //   data: {
    //     username: "wh2",
    //     password: "1234",
    //     confirmedPassword: "1234"
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }
    // });
  }
};
</script>

<style>
body {
  background-image: url("./assets/images/bear.jpg");
  background-size: cover; /* 百分之百填充 */
}
</style>
