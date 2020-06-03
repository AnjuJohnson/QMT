package com.Qubicle.QMT.Retrofit.Response;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.JuzList;

import java.util.List;

public class JuzListModelResponse extends BaseResponse<JuzList> {

    public JuzListModelResponse(List<JuzList> data) {
        super(data);
    }

}
