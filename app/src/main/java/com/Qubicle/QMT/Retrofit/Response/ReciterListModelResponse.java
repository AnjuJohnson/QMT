package com.Qubicle.QMT.Retrofit.Response;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Reciter;

import java.util.List;

public class ReciterListModelResponse extends BaseResponse<Reciter> {

    public ReciterListModelResponse(List<Reciter> data) {
        super(data);
    }


}
