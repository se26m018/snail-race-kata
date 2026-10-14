package snail.race.kata.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class BetApplicationTest {
    @Test
    void registers_a_bet() {
        var repository = new BetRepositoryFake();
        var raceProvider = new RaceResultProviderFake();

        var application = new BetApplication(repository, raceProvider);

        application.placeBet("Alex", 1000, 3, 2, 1);

        assertThat(repository.findByDateRange(0, 2000))
                .containsExactly(new Bet("Alex", new PodiumPronostic(3, 2, 1), 1000));
    }
}