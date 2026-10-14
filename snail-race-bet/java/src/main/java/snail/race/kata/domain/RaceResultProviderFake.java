package snail.race.kata.domain;

import java.util.ArrayList;

public class RaceResultProviderFake implements RaceResultProvider {

    @Override
    public SnailRaces races() {
        Podium previousPodium = new Podium(
        new Snail(1, "Schnecke eins"),
        new Snail(2, "Schnecke zwei"),
        new Snail(3, "Schnecke drei"));

        Podium lastPodium = new Podium(
                new Snail(3, "Schnecke drei"),
                new Snail(2, "Schnecke zwei"),
                new Snail(1, "Schnecke eins"));

        ArrayList<SnailRace> races = new ArrayList<>();
        races.add(new SnailRace(1, 10000, previousPodium));
        races.add(new SnailRace(2, 20000, lastPodium));

        return new SnailRaces(races);
    }
    
}
