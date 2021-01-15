package com.amrindersingh.msscbeerservice.services.Brewing;

import com.amrindersingh.msscbeerservice.services.inventory.BeerInventoryService;
import com.amrindersingh.msscbeerservice.config.JmsConfig;
import com.amrindersingh.msscbeerservice.domain.Beer;
import com.common.events.BrewBeerEvent;
import com.amrindersingh.msscbeerservice.repositories.BeerRepository;
import com.amrindersingh.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 6000) //every 5sec
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnhandInventory(beer.getId());

            log.debug("Min ohHand is: " + beer.getMinOnHand());
            log.debug("Inventory is: "+ invQOH);

            if(beer.getMinOnHand() >= invQOH) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE , new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }

        });
    }

}
