package com.chinesechess.util;

import com.github.pagehelper.PageInfo;

import java.awt.*;
import java.util.List;

public class Page {

    //表示当前页码
    int pageNum=0;
    //表示当前每页的条数
    int count = 0;
    //表示页总数量
    int pages=0;
    //表示记录总数量
    long pagetotal=0;

    /*从从Pagehelper获取需要的分页信息*/
    public void setPageInform(List list){
        PageInfo pageInfo = new PageInfo(list);
        //获取当前的总条数
        this.pagetotal=pageInfo.getTotal();

        //获取当前分页的总页数
        this.pages=pageInfo.getPages();

        //获取当前页码
        this.pageNum=pageInfo.getPageNum();

        this.count=pageInfo.getPageSize();

        return ;
    }


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getPagetotal() {
        return pagetotal;
    }

    public void setPagetotal(long pagetotal) {
        this.pagetotal = pagetotal;
    }
}