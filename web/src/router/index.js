import { createRouter, createWebHistory } from 'vue-router'
import PKIndexView from '../views/pk/PKIndexView'
import RankListView from '../views/ranklist/RankListView'
import RecordIndexView from '../views/record/RecordIndexView'
import UserBotIndexView from '../views/user/bots/UserBotIndexView'
import NotFoundView from '../views/error/NotFoundView'
import UserAccountLoginView from '@/views/user/account/UserAccountLogin'
import UserAccountRegisterView from '@/views/user/account/UserAccountRegister'

const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/pk/",
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PKIndexView,
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RankListView,
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndexView,
  },
  {
    path: "/user/bot/",
    name: "user_bot_index",
    component: UserBotIndexView,
  },
  {
    path: "/user/account/login/",
    name: "user_account_login",
    component: UserAccountLoginView,
  },
  {
    path: "/user/account/register/",
    name: "user_account_register",
    component: UserAccountRegisterView,
  },
  {
    path: "/404/",
    name: "404",
    component: NotFoundView,
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

export default router
