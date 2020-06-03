package com.Qubicle.QMT.Retrofit.Response;
import com.Qubicle.QMT.Models.ChapterList;

import java.util.List;

public class ChapterListModelResponse extends BaseResponse<ChapterList> {
    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public boolean isError;
    public ChapterListModelResponse(List<ChapterList> data) {
        super(data);
    }
    public ChapterListModelResponse(boolean isError) {
        super(isError);
    }
    public ChapterListModelResponse(List<ChapterList> data, boolean success, boolean isError) {
        super(data, success);
    }
}
