import Vue from 'vue';
import Router from 'vue-router'
import Login from '../page/admin/Login.vue'
import Home from '../page/admin/Home.vue'
import adminAccountmanage from  '../page/admin/HomeComponents/ContentComponents/adminAccountmanage'
import gamesInform from  '../page/admin/HomeComponents/ContentComponents/gamesInform'
import userAccountmanage from  '../page/admin/HomeComponents/ContentComponents/userAccountmanage'
import userInformstatistics from  '../page/admin/HomeComponents/ContentComponents/userInformstatistics'

import userLogin from '../page/user/Login.vue'
import chessPlay from '../page/user/chessPlay.vue'
import userHome from '../page/user/Home.vue'
import gameLobby from '../page/user/MComponets/gameLobby.vue'
import userInform from '../page/user/MComponets/userInform.vue'
import Record from '../page/user/MComponets/Record.vue'
import gamePlay from '../page/user/gamePlay.vue'

Vue.use(Router);

export default new Router({
  routes:[
    {
      path:'/user',//普通用户登录
      component:userLogin
    },
    // {
    //   path: '/chessPlay',//对局房间
    //   component:chessPlay
    // },
    {
      path:'/gamePlay',//普通用户登录
      component:gamePlay
    },
    {
      path: '/userhome',
      component:userHome ,
      children:[
        {
          path: '/gameLobby',//游戏大厅
          component:gameLobby
        },

        {
          path: '/userInform',//个人信息
          component:userInform
        },
        {
          path: '/Record',//对局记录
          component:Record
        },

      ]
    },

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
