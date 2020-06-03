package com.Qubicle.QMT.Retrofit.Response;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.VerseDetail;

import java.util.List;

public class VerseDetailsListModelResponse extends BaseResponse<VerseDetail> {

    public VerseDetailsListModelResponse(List<VerseDetail> data) {
        super(data);
    }

}
