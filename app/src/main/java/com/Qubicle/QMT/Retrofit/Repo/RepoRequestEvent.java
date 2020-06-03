package com.Qubicle.QMT.Retrofit.Repo;

import java.util.List;

/**
 * Created by Anjumol Johnson on 26/1/19.
 */
public class RepoRequestEvent<T> {
    private int requestType;

    private String chapterid;


    public RepoRequestEvent(int requestType) {
        this.requestType = requestType;
        this.chapterid = chapterid;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private T data;
    public List<T> getDatas() {
        return datas;
    }
    public RepoRequestEvent(int requestType, List<T> datas) {
        this.requestType = requestType;
        this.datas = datas;
    }
    public RepoRequestEvent(int requestType, T data) {
        this.requestType = requestType;
        this.data = data;
    }




    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    private List<T> datas;

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }
}
