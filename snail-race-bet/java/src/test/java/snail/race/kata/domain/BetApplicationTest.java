package snail.race.kata.domain;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BetApplicationTest {
    BetApplication application;
    BetRepository repository;
    RaceResultProvider raceResultProvider;

    @BeforeEach
    void setup() {
        repository = new BetRepositoryFake();
        raceResultProvider = new RaceResultProviderFake();
        application = new BetApplication(repository, raceResultProvider);
    }

    @Test
    void registers_two_bets() {
        application.placeBet("Alex", 1000, 3, 2, 1);
        application.placeBet("Manuela", 1500, 1, 2, 3);

        assertThat(repository.findByDateRange(0, 2000))
                .containsExactly(
                        new Bet("Alex", new PodiumPronostic(3, 2, 1), 1000),
                        new Bet("Manuela", new PodiumPronostic(1, 2, 3), 1500));
    }

    @Test
    void matching_bet_exactly_three_seconds_before_race_wins() {
        application.placeBet("Alex", 17000, 3, 2, 1);

        assertThat(application.getWinnersForLastRace())
                .containsExactly(new Winner("Alex"));
    }

    @Test
    void wrong_podium_order_does_not_win() {
        application.placeBet("Manuela", 17000, 1, 2, 3);

        assertThat(application.getWinnersForLastRace()).isEmpty();
    }

    @Test
    void bet_less_than_three_seconds_before_race_does_not_win() {
        application.placeBet("Alex", 17001, 3, 2, 1);

        assertThat(application.getWinnersForLastRace()).isEmpty();
    }

    @Test
    void bet_for_previous_race_does_not_win_last_race() {
        application.placeBet("Manuela", 7000, 3, 2, 1);

        assertThat(application.getWinnersForLastRace()).isEmpty();
    }

}