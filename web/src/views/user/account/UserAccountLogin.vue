<template>
  <!-- 用一个flag来控制 本地有token时 不展示首页 -->
    <ContentFieldView v-if="!$store.state.user.pulling_info">
        <!-- grid将整个页面分成12格，这里需要放在中间3格 -->
        <div class="row justify-content-md-center">
            <div class="col-3">
            <form @submit.prevent="login">
                <div class="mb-3">
                <label for="username" class="form-label">用户名</label>
                <input v-model = "username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                </div>
                <div class="mb-3">
                <label for="password" class="form-label">密码</label>
                <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
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
import { useStore } from "vuex";
import { ref } from "vue";
import router from "@/router/index";

export default {
  components: {
    ContentFieldView
  },
  setup() {
    const store = useStore();
    let username = ref("");
    let password = ref("");
    let error_message = ref("");

    const jwt_token = localStorage.getItem("jwt_token");
    // 登录时判断一下本地缓存中是否有token
    if (jwt_token) {
      store.commit("updateToken", jwt_token);
      store.dispatch("getinfo", {
        success() {
          router.push({ name: "home" });
          store.commit("updatePullingInfo", false);
        },
        error() {
          store.commit("updatePullingInfo", false);
        }
      });
    }
    // 本地没有jwt token 也要展示登录页面
    else {
      store.commit("updatePullingInfo", false);
    }

    const login = () => {
      error_message.value = "";
      store.dispatch("login", {
        username: username.value,
        password: password.value,
        success() {
          store.dispatch("getinfo", {
            success() {
              router.push({ name: "home" });
              // console.log(store.state.user);
            }
          });
        },
        error() {
          error_message.value = "用户名或密码错误";
        }
      });
    };
    return {
      username,
      password,
      error_message,
      login
    };
  }
};
</script>
<style>
div.error-message {
  color: red;
}
button {
  width: 100%;
}
</style>
