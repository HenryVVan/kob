<template>
    <ContentField>
      
      <table class="table table-striped table-hover" style="text-align:center">
      <thead>
        <tr>
          <th>玩家</th>
          <th>天梯积分</th>
      </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>
            <img :src="user.photo" alt="" class="ranklist-user-photo">
            &nbsp;
            <span class="ranklist-user-username">{{user.username}}</span>
          </td>
          <td>
            {{user.rating}}
          </td>
        </tr>
      </tbody>
      </table>
      <nav aria-label="" style="float: right">
        <ul class="pagination" style="float:right">
          <li class="page-item" @click="click_page(-2)">
            <a class="page-link" href="#" >前一页</a>
          </li>
          <!-- <li v-for="page in pages" :key="page.number" :class="{'page-item': true, 'active': page.is_active === 'active'}"> -->
          <li v-for="page in pages" :key="page.id" :class="'page-item ' + page.is_active" @click="click_page(page.number)">
            <a class="page-link" href="#" >
              {{page.number}}
            </a>
          </li>
          <li class="page-item" @click="click_page(-1)">
            <a class="page-link" href="#">后一页</a>
          </li>
        </ul>
      </nav>
    </ContentField>
</template>

<script>
import ContentField from "../../components/ContentField.vue";
import { useStore } from "vuex";
import $ from "jquery";
import { ref } from "vue";
// import router from "../../router";
export default {
  components: {
    ContentField
  },
  setup() {
    const store = useStore();
    let current_page = 1;
    let users = ref([]);
    let total_user = 0; // 总用户数
    let pages = ref([]);

    const click_page = page => {
      if (page === -2) {
        page = current_page - 1;
      } else if (page === -1) {
        page = current_page + 1;
      }
      let max_pages = parseInt(Math.ceil(total_user / 9));
      if (page >= 1 && page <= max_pages) {
        pull_page(page);
      }
    };

    const update_pages = () => {
      let max_pages = parseInt(Math.ceil(total_user / 9));
      let new_pages = [];
      for (let i = current_page - 1; i <= current_page + 1; i++) {
        if (i >= 1 && i <= max_pages) {
          new_pages.push({
            number: i,
            is_active: i === current_page ? "active" : null
          });
        }
      }
      pages.value = new_pages;
    };

    console.log(total_user);

    const pull_page = page => {
      current_page = page;
      $.ajax({
        url: "https://app6617.acapp.acwing.com.cn/api/ranklist/getlist/",
        data: {
          page
        },
        type: "get",
        // 注意这里是headers，一定要有s
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        success(resp) {
          // console.log(resp);
          users.value = resp.users;
          total_user = resp.usersCount;
          update_pages();
        },
        error(resp) {
          console.log(resp);
        }
      });
    };
    pull_page(current_page);

    return {
      users,
      total_user,
      pages,
      click_page
    };
  }
};
</script>
<style scoped>
img.ranklist-user-photo {
  width: 5vh;
  border-radius: 50%;
}
</style>
