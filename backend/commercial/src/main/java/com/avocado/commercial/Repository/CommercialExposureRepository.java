package com.avocado.commercial.Repository;

import com.avocado.commercial.Dto.response.item.Popup;
import com.avocado.commercial.Entity.CommercialExposure;
import org.springframework.data.repository.Repository;

public interface CommercialExposureRepository  extends Repository<CommercialExposure,Long> {

    void save(CommercialExposure commercialExposure);
}
