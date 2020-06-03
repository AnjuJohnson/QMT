package com.Qubicle.QMT.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class ResultObject<T> {

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  List<T> data;

}
