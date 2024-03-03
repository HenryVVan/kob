<template>
    <div class="matchground">
        <div class="row">
            <!-- 一行被分为12份 -->
            <div class="col-4">
              <div class="user-photo">
                  <img :src="$store.state.user.photo" alt="">
              </div>
              <div class="user-username">
                  {{$store.state.user.username}}
              </div>
            </div>
            <div class="col-4">
              <div class="user-select-bot">
                <!-- 双向绑定 -->
                <select v-model="select_bot" class="form-select">
                  <option value="-1" selected>亲自出马</option>
                  <option v-for="bot in bots" :key="bot.id" :value="bot.id">
                    {{bot.title}}
                  </option>
                </select>
              </div>
            </div>
            <div class="col-4">
                <div class="user-photo">
                <img :src="$store.state.pk.opponent_photo" alt="">
            </div>
            <div class="user-username">
                {{$store.state.pk.opponent_username}}
            </div>
            </div>
            <div class="col-12" style="text-align:center; padding-top:12vh">
              <button @click="click_match_btn" type="button" class="btn btn-warning btn-lg">
              {{match_btn_info}}
              </button>
            </div>
        </div>

     </div>
</template>

<script>
import { ref } from "vue";
import { useStore } from "vuex";
import $ from "jquery";

export default {
  setup() {
    let match_btn_info = ref("开始匹配");
    let bots = ref([]);
    let select_bot = ref("-1");
    const store = useStore();
    const click_match_btn = () => {
      if (match_btn_info.value === "开始匹配") {
        console.log(select_bot.value);
        match_btn_info.value = "取消";
        store.state.pk.socket.send(
          JSON.stringify({
            event: "start-matching",
            botId: select_bot.value
          })
        );
      } else {
        match_btn_info.value = "开始匹配";
        store.state.pk.socket.send(
          JSON.stringify({
            event: "stop-matching"
          })
        );
      }
    };
    const refresh_bots = () => {
      $.ajax({
        url: "https://app6617.acapp.acwing.com.cn/api/user/bot/query/",
        type: "get",
        // 注意这里是headers，一定要有s
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        success(resp) {
          bots.value = resp;
        },
        error(resp) {
          console.log(resp);
        }
      });
    };
    // 从云端获取bots
    refresh_bots();
    return {
      match_btn_info,
      click_match_btn,
      refresh_bots,
      select_bot,
      bots
    };
  }
};
</script>

<style scoped>
div.matchground {
  background-color: rgba(50, 50, 50, 0.5);
  width: 60vw;
  height: 70vh;
  margin: 50px auto;
}
div.user-photo {
  padding-top: 10vh;
  text-align: center;
}
div.user-photo > img {
  border-radius: 50%;
  width: 20vh;
}

div.user-username {
  padding-top: 2vh;
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  color: white;
}
div.user-select-bot {
  padding-top: 20vh;
}
div.user-select-bot > select {
  width: 60%;
  margin: 0 auto;
}
</style>
