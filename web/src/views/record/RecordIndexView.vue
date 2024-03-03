<template>
  <!-- <RecordContentView/> -->
    <ContentField>
      
      <table class="table table-striped table-hover" style="text-align:center">
      <thead>
        <tr>
          <th>A</th>
          <th>B</th>
          <th>对战结果</th>  
          <th>对战时间</th>
          <th>操作</th>
      </tr>
      </thead>
      <tbody>
        <tr v-for="record in records" :key="record.record.id">
          <td>
            <img :src="record.a_photo" alt="" class="record-user-photo">
            &nbsp;
            <span class="record-user-username">{{record.a_username}}</span>
          </td>
          <td>
            <img :src="record.b_photo" alt="" class="record-user-photo">
            &nbsp;
            <span class="record-user-username">{{record.b_username}}</span>
          </td>
          <td>
            {{record.result}}
          </td>
          <td>
            {{record.record.createTime}}
          </td>
          <td>
            <button @click="open_record_content(record.record.id)" type="button" class="btn btn-secondary"> 查看录像 </button>
          </td>
        </tr>
      </tbody>
      </table>
      <nav aria-label="Page navigation example" style="float: right">
        <ul class="pagination">
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
            <a class="page-link" href="#" >后一页</a>
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
import router from "../../router";
export default {
  components: {
    ContentField
  },
  setup() {
    const store = useStore();
    let current_page = 1;
    let records = ref([]);
    let total_record = 0; // 总对局数
    let pages = ref([]);

    const click_page = page => {
      if (page === -2) {
        page = current_page - 1;
      } else if (page === -1) {
        page = current_page + 1;
      }
      let max_pages = parseInt(Math.ceil(total_record / 9));
      if (page >= 1 && page <= max_pages) {
        pull_page(page);
      }
    };

    const update_pages = () => {
      let max_pages = parseInt(Math.ceil(total_record / 9));
      let new_pages = [];
      for (let i = current_page - 2; i <= current_page + 2; i++) {
        if (i >= 1 && i <= max_pages) {
          new_pages.push({
            number: i,
            is_active: i === current_page ? "active" : null
          });
        }
      }
      pages.value = new_pages;
    };

    // console.log(total_record);

    const pull_page = page => {
      current_page = page;
      $.ajax({
        url: "http://localhost:6221/record/getlist/",
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
          records.value = resp.records;
          total_record = resp.recordsCount;
          update_pages();
        },
        error(resp) {
          console.log(resp);
        }
      });
    };
    pull_page(current_page);

    const string2map = map => {
      let g = [];
      for (let i = 0, k = 0; i < 13; i++) {
        let line = [];
        for (let j = 0; j < 14; j++, k++) {
          console.log(map[k]);
          if (map[k] === "0") line.push(0);
          else line.push(1);
        }
        g.push(line);
      }
      return g;
    };

    const open_record_content = recordId => {
      for (const record of records.value) {
        if (record.record.id === recordId) {
          console.log(1111);
          store.commit("updateIsRecord", true);
          console.log(record.record.map);
          store.commit("updateGame", {
            map: string2map(record.record.map),
            a_id: record.record.aid,
            a_sx: record.record.asx,
            a_sy: record.record.asy,
            b_id: record.record.bid,
            b_sx: record.record.bsx,
            b_sy: record.record.bsy
          });
          store.commit("updateSteps", {
            a_steps: record.record.asteps,
            b_steps: record.record.bsteps
          });
          store.commit("updateRecordLoser", record.record.loser);
          // console.log(record);
          // 从当前页面跳转到路由名为:name的页面
          router.push({
            name: "record_content",
            params: {
              recordId
            }
          });
          break;
        }
      }
    };

    return {
      records,
      open_record_content,
      pages,
      click_page
      // string2map
    };
  }
};
</script>
<style scoped>
img.record-user-photo {
  width: 5vh;
  border-radius: 50%;
}
</style>
