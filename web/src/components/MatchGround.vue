<template>
    <div class="matchground">
        <div class="row">
            <!-- 一行被分为12份，各占6份 -->
            <div class="col-6">
            <div class="user-photo">
                <img :src="$store.state.user.photo" alt="">
            </div>
            <div class="user-username">
                {{$store.state.user.username}}
            </div>
            </div>
            <div class="col-6">
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

export default {
  setup() {
    let match_btn_info = ref("开始匹配");
    const store = useStore();
    const click_match_btn = () => {
      if (match_btn_info.value === "开始匹配") {
        match_btn_info.value = "取消";
        store.state.pk.socket.send(
          JSON.stringify({
            event: "start-matching"
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
    return {
      match_btn_info,
      click_match_btn
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
</style>
