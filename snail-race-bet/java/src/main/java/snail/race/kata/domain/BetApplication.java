package snail.race.kata.domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import snail.race.kata.domain.RaceResultProvider.SnailRace;
import snail.race.kata.domain.RaceResultProvider.SnailRaces;

public class BetApplication {
    private BetRepository betRepository;
    private RaceResultProvider raceResultProvider;

    BetApplication(
            BetRepository betRepository,
            RaceResultProvider raceResultProvider) {
                this.betRepository = betRepository;
                this.raceResultProvider = raceResultProvider;
    }

    void placeBet(
             String gambler,
             long timestamp,
             int first,
             int second,
             int third) {
                PodiumPronostic pronostic = new PodiumPronostic(first, second, third);
                betRepository.register(new Bet(gambler, pronostic, timestamp));
    }

    List<Winner> getWinnersForLastRace() {
        SnailRaces races = raceResultProvider.races();
        if (races == null || races.races() == null) {
            return Collections.emptyList();
        }
        long currentTime = System.currentTimeMillis();
        Optional<SnailRace> nextRace = races.races().stream()
                                                .filter(r -> r.timestamp() > currentTime)
                                                .min((r1, r2) -> Long.compareUnsigned(r1.timestamp(), r2.timestamp()));

        return null;
    }
}
