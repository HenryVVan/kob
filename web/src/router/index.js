import { createRouter, createWebHistory } from 'vue-router'
import PKIndexView from '../views/pk/PKIndexView'
import RankListView from '../views/ranklist/RankListView'
import RecordIndexView from '../views/record/RecordIndexView'
import UserBotIndexView from '../views/user/bots/UserBotIndexView'
import NotFoundView from '../views/error/NotFoundView'
import UserAccountLoginView from '@/views/user/account/UserAccountLogin'
import UserAccountRegisterView from '@/views/user/account/UserAccountRegister'
import store from '@/store/user'

const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/pk/",
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PKIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RankListView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/bot/",
    name: "user_bot_index",
    component: UserBotIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/account/login/",
    name: "user_account_login",
    component: UserAccountLoginView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/user/account/register/",
    name: "user_account_register",
    component: UserAccountRegisterView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/404/",
    name: "404",
    component: NotFoundView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/",
  }

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 在路由生效前执行
router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !store.state.is_login) {
    next({ name: "user_account_login" });
  } else {
    next();
  }
})

export default router
