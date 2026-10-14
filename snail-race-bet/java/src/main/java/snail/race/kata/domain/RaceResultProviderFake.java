package snail.race.kata.domain;

import java.util.List;

public class RaceResultProviderFake implements RaceResultProvider {
    private final SnailRaces results;

    public RaceResultProviderFake() {
        this(new SnailRaces(
                List.of(
                        new SnailRace(
                                1,
                                1,
                                new Podium(
                                        new Snail(
                                                1,
                                                "test"),
                                        new Snail(0, null),
                                        new Snail(0, null))))));
    }

    public RaceResultProviderFake(SnailRaces results) {
        this.results = results;
    }

    @Override
    public SnailRaces races() {
        return results;
    }

}
