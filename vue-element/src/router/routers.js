import Vue from 'vue';
import Router from 'vue-router'
import Login from '../page/admin/Login.vue'
import Home from '../page/admin/Home.vue'
import adminAccountmanage from  '../page/admin/HomeComponents/ContentComponents/adminAccountmanage'
import gamesInform from  '../page/admin/HomeComponents/ContentComponents/gamesInform'
import userAccountmanage from  '../page/admin/HomeComponents/ContentComponents/userAccountmanage'
import userInformstatistics from  '../page/admin/HomeComponents/ContentComponents/userInformstatistics'



Vue.use(Router);

export default new Router({
  routes:[
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/home',
      component:Home ,
      children:[
            {
              path: '/',//用户信息统计
              component:userInformstatistics
            },
            {
              path: '/adminAccountmanage',//管理员信息管理
              component:adminAccountmanage
            },
            {
              path: '/gamesInform',//对局信息查看
              component:gamesInform
            },
            {
              path: '/userAccountmanage',//用户信息管理
              component:userAccountmanage
            },
            {
              path: '/userInformstatistics',//用户信息统计
              component:userInformstatistics
            },

      ]
    },
    {
      path:'/login',
      component:Login
    },
  ]
});
