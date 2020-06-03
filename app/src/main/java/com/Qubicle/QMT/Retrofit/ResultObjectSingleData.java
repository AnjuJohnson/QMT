package com.Qubicle.QMT.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class ResultObjectSingleData<T> {

  @SerializedName("data")
  private T data;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
