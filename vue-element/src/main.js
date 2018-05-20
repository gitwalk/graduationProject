import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router/routers'
import axios from 'axios'
import BoardValue from "../static/js/chessBoardValue.js"
import Ipaddress from "../static/js/Ipaddress.js"
import $ from "jquery"
import animate from 'animate.css'
import  Velocity from 'velocity-animate'
import {format,formatDuring} from "../static/js/timeTool.js"
import {pagesHelp,updataInform,UserserInform,LoginAxios} from "../static/js/pagesHelp.js"
import '../static/icon/iconfont.css'


axios.defaults.withCredentials=true;

Vue.prototype.BoardValue=BoardValue;
Vue.prototype.Ipaddress=Ipaddress;
Vue.prototype.$LoginAxios=LoginAxios;
Vue.prototype.$axios = axios;
Vue.prototype.$pagesHelp=pagesHelp
Vue.prototype.$UserserInform=UserserInform
Vue.prototype.$updataInform=updataInform
Vue.prototype.$timeFormat=format
Vue.prototype.$formatDuring=formatDuring;


Vue.use(ElementUI)


new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
