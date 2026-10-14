package snail.race.kata.domain;

import java.util.List;

import snail.race.kata.domain.RaceResultProvider.Podium;
import snail.race.kata.domain.RaceResultProvider.SnailRace;
import snail.race.kata.domain.RaceResultProvider.SnailRaces;

public class BetApplication {
    private final BetRepository betRepository;
    private final RaceResultProvider raceResultProvider;
    private static final long MIN_DELAY_BEFORE_RACE_MS = 3000;

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
        if (races == null || races.races() == null || races.races().isEmpty()) {
            return List.of();
        }

        List<SnailRace> sorted = races.races().stream()
                .sorted((r1, r2) -> Long.compare(r1.timestamp(), r2.timestamp()))
                .toList();

        SnailRace lastRace = sorted.get(sorted.size() - 1);
        long from = sorted.size() > 1 ? sorted.get(sorted.size() - 2).timestamp() : 0;
        long to = lastRace.timestamp() - MIN_DELAY_BEFORE_RACE_MS;

        return betRepository.findByDateRange(from, to).stream()
                .filter(bet -> pronosticMatchesResult(bet.pronostic(), lastRace.podium()))
                .map(bet -> new Winner(bet.gambler()))
                .toList();
    }

    private boolean pronosticMatchesResult(PodiumPronostic pronostic, Podium podium) {
        return pronostic.first() == podium.first().number()
                && pronostic.second() == podium.second().number()
                && pronostic.third() == podium.third().number();
    }
}
