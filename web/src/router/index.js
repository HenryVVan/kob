import { createRouter, createWebHistory } from 'vue-router'
import PKIndexView from '../views/pk/PKIndexView'
import RankListView from '../views/ranklist/RankListView'
import RecordIndexView from '../views/record/RecordIndexView'
import UserBotIndexView from '../views/user/bots/UserBotIndexView'
import NotFoundView from '../views/error/NotFoundView'

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
