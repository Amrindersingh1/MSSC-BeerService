package com.amrindersingh.msscbeerservice.events;

import com.amrindersingh.msscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent{

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }

}
