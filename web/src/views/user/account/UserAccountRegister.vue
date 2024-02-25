<template>
    <ContentFieldView>
      <div class="row justify-content-md-center">
            <div class="col-3">
            <form @submit.prevent="register">
                <div class="mb-3">
                <label for="username" class="form-label">用户名</label>
                <input v-model = "username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                </div>
                <div class="mb-3">
                <label for="password" class="form-label">密码</label>
                <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
                </div>
                <div class="mb-3">
                <label for="password" class="form-label">确认密码</label>
                <input v-model="confirmedPassword" type="password" class="form-control" id="confirmedPassword" placeholder="请确认两次输入密码一致">
                </div>
                <div class="error-message">{{error_message}}</div>
                <button type="submit" class="btn btn-primary">提交</button>
            </form>
            </div>
        </div>  
        </ContentFieldView>
</template>

<script>
import ContentFieldView from "@/components/ContentField.vue";
import { ref } from "vue";
import router from "@/router/index";
import $ from "jquery";

export default {
  components: {
    ContentFieldView
  },
  setup() {
    // const store = useStore();
    // $.ajax({
    //   url:"http://localhost:6221/user/bot/add/",
    //   type:"post",
    //   data:{
    //     title:"test",
    //     description:"描述",
    //     content:"代码",
    //   },
    //   headers:{
    //     Authorization:"Bearer " + store.store.user.token,
    //   },
    //   success(resp){
    //     console.log(resp);
    //   },
    //   error(resp){
    //     console.log(resp);
    //   }
    // })

    let username = ref("");
    let password = ref("");
    let confirmedPassword = ref("");
    let error_message = ref("");

    const register = () => {
      $.ajax({
        url: "http://localhost:6221/user/account/register/",
        type: "post",
        data: {
          username: username.value,
          password: password.value,
          confirmedPassword: confirmedPassword.value
        },
        success(resp) {
          if (resp.error_message === "success") {
            router.push({ name: "user_account_login" });
          }
          // 将错误信息显示
          else {
            error_message.value = resp.error_message;
          }
          // console.log(resp);
        },
        error() {
          // console.log(resp);
        }
      });
    };

    return {
      username,
      password,
      confirmedPassword,
      error_message,
      register
    };
  }
};
</script>
<style scoped>
.error_message {
  color: red;
}
button {
  width: 100%;
}
</style>
