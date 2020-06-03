package com.Qubicle.QMT.Retrofit.Response;
import com.Qubicle.QMT.Models.JuzDetail;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.VerseDetail;

import java.util.List;

public class JuzDetailsListModelResponse extends BaseResponse<JuzDetailNew> {

    public JuzDetailsListModelResponse(List<JuzDetailNew> data) {
        super(data);
    }

}
