/**
 *
 * @param thisVue 传入当前的vue
 * @param count 显示的条数
 * @param PageNum 表示要跳转的页码
 * @param api 表示要访问的接口
 */
export function pagesHelp(thisVue,count,PageNum1,api,dealInfom) {

  var pages={  "pageNum":PageNum1,
                "count":count,
                "pages":0,
                "Pagetotal":0,
            };
  var jsonData = JSON.stringify(pages);
  this.$axios({
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    url: 'http://127.0.0.1:8081/ssm'+api,//listUserInform
    data: pages,
  }).then(function (response) {
    dealInfom(thisVue,response);
    //return response;
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
export function updataInform(json,api,messageFram,thisVue){
  this.$axios({
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    url: 'http://127.0.0.1:8081/ssm'+api,//listUserInform
    data: json,
  }).then(function (response) {
    //转换性别
    if(response.data[0].sex=="0"){
      response.data[0].sex="男";
    }else if(response.data[0].sex=="1"){
      response.data[0].sex="女";
    }else if(response.data[0].sex=="2"){
      response.data[0].sex="保密";
    }
    //转换状态
    if(response.data[0].isDeleted=="0"){
      response.data[0].isDeleted="正常";
    }else{
      response.data[0].isDeleted="禁用";
    }

    response.data[0].registerTime=thisVue.$timeFormat(response.data[0].registerTime,'yyyy-MM-dd HH:mm:ss');

    for(var i in thisVue.listUserInform1){
      if(thisVue.listUserInform1[i].id==response.data[0].id) {

        thisVue.$set(thisVue.listUserInform1,i,response.data[0])
        //thisVue.listUserInform1[i]=response.data[0];
        console.log(thisVue.listUserInform1[i]);
        break;
      }
    }

    messageFram.$message({
          type: 'success',
      message: '禁用成功!'
  });

  })
    .catch(function (error) {
      console.log(error);
      messageFram.$message({
            type: 'success',
            message: '禁用失败!'
          });
    });
}
