package snail.race.kata.domain;

import java.util.List;

public class RaceResultProviderFake implements RaceResultProvider {

    @Override
    public SnailRaces races() {
        return new SnailRaces(
            List.of(
                new SnailRace(
                    1,
                    1,
                    new Podium(
                        new Snail(
                            1,
                            "test"), 
                            new Snail(0, null), 
                            new Snail(0, null)))));
    }
    
}
