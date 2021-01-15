package com.amrindersingh.msscbeerservice.events;

import com.amrindersingh.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionId = -14235324;

    private final BeerDto beerDto;

}
