import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router/routers'
import axios from 'axios'
import {format,formatDuring} from "../static/js/timeTool.js"
import {pagesHelp,updataInform,UserserInform} from "../static/js/pagesHelp.js"

Vue.prototype.$axios = axios
Vue.prototype.$pagesHelp=pagesHelp
Vue.prototype.$UserserInform=UserserInform
Vue.prototype.$updataInform=updataInform
Vue.prototype.$timeFormat=format
Vue.prototype.$formatDuring=formatDuring;

// import  "/static/js/TweenLite.min.js"
// import "/static/js/EasePack.min.js"
// import "/static/js/rAF.js"
// import "/static/js/demo-1.js"
//
//
// require  ("../static/js/TweenLite.min.js")
// require("../static/js/EasePack.min.js")
// require("../static/js/rAF.js")
//require("../static/js/demo-1.js")

//import demo from "../static/js/demo-1.js"

// import "../static/css/normalize.css";
// import "../static/css/demo.css";
// import "../static/css/component.css";

Vue.use(ElementUI)


new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
