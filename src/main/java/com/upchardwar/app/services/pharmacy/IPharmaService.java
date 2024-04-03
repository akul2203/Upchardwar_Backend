package com.upchardwar.app.services.pharmacy;


import com.upchardwar.app.entity.payload.PharmaRequest;
import com.upchardwar.app.entity.payload.PharmaResponse;

public interface IPharmaService {

	public PharmaResponse registerPharma(PharmaRequest pharmaRequest);
}
