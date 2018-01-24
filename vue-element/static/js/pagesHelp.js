import {formatDuring} from "./timeTool";

/**
 *
 * @param thisVue 传入当前的vue
 * @param count 显示的条数
 * @param PageNum 表示要跳转的页码
 * @param api 表示要访问的接口
 */
export function pagesHelp(thisVue,count,PageNum1,api,callback) {

  var pages={  "pageNum":PageNum1,
    "count":count,
    "pages":0,
    "Pagetotal":0,
  };
  var jsonData = JSON.stringify(pages);
  this.$axios({
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',

    },
    url: '/ssm'+api,//listUserInform
    withCredentials : true,
    data: pages,
  }).then(function (response) {
   if(response.data=="") {
     outInform(thisVue);
    }else{
     if(callback=="dealInfom") {
       dealInfom(thisVue,response);
     }else if(callback=="dealUserLoginInfom"){
       dealUserLoginInfom(thisVue,response);
     }else if(callback=="dealGameInfom"){
       dealGameInfom(thisVue,response);
     }
   }
    //return response;
  })
    .catch(function (error) {
      console.log(error);
    });
}


export function LoginAxios(per,thisVue,api,callback) {
  var jsonData = JSON.stringify(per);
    this.$axios({
    method: 'post',
    headers: {
      'Content-type': 'application/json;charset=UTF-8'},
    withCredentials : true,
    url: '/ssm'+api,
    data: jsonData
  }).then(function (response){
      if(response.data=="") {
        outInform(thisVue);
      }else {
        if(callback=="adminLogin"){
          adminLogin(response,thisVue);
        }else{
          if(response.data=="") {
            thisVue.$alert('请重新登录', '消息', {
              confirmButtonText: '确定',
              callback: action => {
                thisVue.$router.push('/');
              }
            });
          }else if(callback=="checkAdmin"){
            if(response.data==="error"){
              callback="error";
            }
            callback="success";
          }
        }
      }

  }).catch(function (error) {
      console.log(error);
    });

}

export function UserserInform(pageNum,thisVue,api,callback) {

  thisVue.pagesInform.pageNum=pageNum;
  thisVue.dynamicValidateForm.page=thisVue.pagesInform;

  var jsonData = JSON.stringify(thisVue.dynamicValidateForm);
  this.$axios({
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',

    },
    withCredentials : true,
    url: '/ssm'+api,//listUserInform
    data: jsonData,
  }).then(function (response) {
    if(response.data=="") {
      outInform(thisVue);
    }else{
      if(callback=="dealInfom") {
        dealInfom(thisVue,response);
      }else if(callback=="dealUserLoginInfom"){
        dealUserLoginInfom(thisVue,response);
      }else if(callback=="dealGameInfom"){
        dealGameInfom(thisVue,response);
      }
    }

  })
    .catch(function (error) {
      console.log(error);
    });
}

/**
 * 用于更新每条数据
 * @param json 传入更新的json
 * @param api 传入访问的接口
 */
export function updataInform(json,api,messageFram,thisVue,callback){
  this.$axios({
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    url: '/ssm'+api,//listUserInform
    data: json,
  }).then(function (response) {
    if(response.data=="") {
      outInform(thisVue);
    }else {
      if (callback == "updateInfom") {
        updateInfom(thisVue, response, messageFram);
      } else if (callback == "updataAdminPassWord") {
        updataAdminPassWord(thisVue, response);
      } else if (callback = "addAdminInfrom") {
        addAdminInfrom(thisVue, response);
      }
    }
  })
    .catch(function (error) {
      console.log(error);

    });
}

function outInform(thisVue) {

    thisVue.$alert('请重新登录', '消息', {
      confirmButtonText: '确定',
      callback: action => {
        thisVue.$router.push('/');
      }
    });

}

function addAdminInfrom(thisVue,response) {
  if(response.data=="success"){
    thisVue.$message({
      message: '添加成功',
      type: 'success',

    });
  }else if(response.data=="error"){
    thisVue.$message({
      message: '添加失败',
      type: 'error',

    });
  }

}

function updataAdminPassWord(thisVue,response) {
  if(response.data!="error"){

    thisVue.logInOrOut(thisVue,"/logoutAdminInfrom","updataAdminPassWord");
  }else {
    thisVue.$message({
      message: '修改失败',
      type: 'error',

    });
  }
}



function adminLogin(response,thisVue) {
  if(response.data=="error"){
    thisVue.$alert('账号或密码错误', '消息', {
      confirmButtonText: '确定',
      callback: action => {
      }
    });
  }else if(response.data=="disable"){
    thisVue.$alert('该账户已禁用', '消息', {
      confirmButtonText: '确定',
      callback: action => {
      }
    });
  } else {
    thisVue.$router.push('/userInformstatistics')
  }
}

function dealGameInfom(thisVue,response) {
  for(var i in response.data[0]) {
    response.data[0][i].gameTime=thisVue.$formatDuring(response.data[0][i].gameTime);
    response.data[0][i].setStepTime=thisVue.$formatDuring(response.data[0][i].setStepTime);
    response.data[0][i].setTime=thisVue.$formatDuring(response.data[0][i].setTime);
    response.data[0][i].startTime=thisVue.$timeFormat(response.data[0][i].startTime,'yyyy-MM-dd HH:mm:ss');
    response.data[0][i].endTime=thisVue.$timeFormat(response.data[0][i].endTime,'yyyy-MM-dd HH:mm:ss');
    switch(response.data[0][i].gameState) {
      case 0:
        response.data[0][i].gameState="红方胜利（绝杀）"
        break;
      case 1:
        response.data[0][i].gameState="黑方胜利（绝杀）"
        break;
      case 2:
        response.data[0][i].gameState="红方胜利（黑方超步时）"
        break;
      case 3:
        response.data[0][i].gameState="黑方胜利（红方超步时）"
        break;
      case 4:
        response.data[0][i].gameState="红方胜利（黑方超局时）"
        break;
      case 5:
        response.data[0][i].gameState="黑方胜利（红方超局时）"
        break;
      case 6:
        response.data[0][i].gameState="红方胜利（黑方认输）"
        break;
      case 7:
        response.data[0][i].gameState="黑方胜利（红方认输）"
        break;
      case 8:
        response.data[0][i].gameState="和局"
        break;
    }
    thisVue.gameInform=response.data[0];
    thisVue.pagesInform=response.data[1];

  }
}

function dealUserLoginInfom(thisVue,response) {
  for(var i in response.data[0]) {

    response.data[0][i].allLoginTime=thisVue.$formatDuring(response.data[0][i].allLoginTime);
    response.data[0][i].winningRate=response.data[0][i].winningRate*100;
    response.data[0][i].winningRate=response.data[0][i].winningRate.toFixed(2)+"%";
    for(var j in response.data[0][i].loginInformList){

      response.data[0][i].loginInformList[j].logoutTime=thisVue.$timeFormat(response.data[0][i].loginInformList[j].logoutTime,'yyyy-MM-dd HH:mm:ss');

      response.data[0][i].loginInformList[j].loginTime=thisVue.$timeFormat(response.data[0][i].loginInformList[j].loginTime,'yyyy-MM-dd HH:mm:ss');
      response.data[0][i].loginInformList[j].time=thisVue.$formatDuring(response.data[0][i].loginInformList[j].time);
    }

  }
  thisVue.userInform=response.data[0];
  thisVue.pagesInform=response.data[1];
}

/**
 * 用于处理账号管理模块的两个表格信息（用户和管理员的表格）
 * @param thisVue
 * @param response
 */

function dealInfom(thisVue,response) {

  for(var i in response.data[0]) {
    if(response.data[0][i].sex!==undefined){
      //转换性别
      if(response.data[0][i].sex=="0"){
        response.data[0][i].sex="男";
      }else if(response.data[0][i].sex=="1"){
        response.data[0][i].sex="女";
      }else if(response.data[0][i].sex=="2"){
        response.data[0][i].sex="保密";
      }
    }

    //转换状态
    if(response.data[0][i].isDeleted=="0"){
      response.data[0][i].isDeleted="正常";
    }else{
      response.data[0][i].isDeleted="禁用";
    }

    response.data[0][i].registerTime=thisVue.$timeFormat(response.data[0][i].registerTime,'yyyy-MM-dd HH:mm:ss');

  }

  thisVue.listInform1=response.data[0];
  thisVue.pagesInform=response.data[1];

}

/**
 * 用于处理修改后的单条信息
 * @param thisVue
 * @param response
 * @param messageFram
 */
function updateInfom(thisVue,response,messageFram){

  if(response.data[0].sex!==undefined){
    //转换性别
    if(response.data[0].sex=="0"){
      response.data[0].sex="男";
    }else if(response.data[0].sex=="1"){
      response.data[0].sex="女";
    }else if(response.data[0].sex=="2"){
      response.data[0].sex="保密";
    }
  }
  //转换状态
  if(response.data[0].isDeleted=="0"){
    response.data[0].isDeleted="正常";
  }else{
    response.data[0].isDeleted="禁用";
  }

  response.data[0].registerTime=thisVue.$timeFormat(response.data[0].registerTime,'yyyy-MM-dd HH:mm:ss');

  for(var i in thisVue.listInform1){
    if(thisVue.listInform1[i].id==response.data[0].id) {

      thisVue.$set(thisVue.listInform1,i,response.data[0]);
      break;
    }
  }
  if(response.data[0].isDeleted=="正常"){
    messageFram.$notify({
      title: '成功',
      message: '已启用',
      type: 'success',
      position: 'bottom-right'
    });
  }else{
    messageFram.$message({
      type: 'success',
      message: '禁用成功!'
    });

  }

}
