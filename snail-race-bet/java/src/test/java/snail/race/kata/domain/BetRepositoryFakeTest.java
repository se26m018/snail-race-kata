package snail.race.kata.domain;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BetRepositoryFakeTest {
    BetRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BetRepositoryFake();
    }

    @Test
    void register_a_bets() {
        Bet bet = new Bet("alice", new PodiumPronostic(3, 1, 2), 1_000L);

        repository.register(bet);

        assertThat(repository.findByDateRange(0L, 2_000L)).containsExactly(bet);
    }

    @Test
    void retrieve_only_bets_inside_the_time_range() {
        Bet before = new Bet("before", new PodiumPronostic(1, 2, 3), 500L);
        Bet inside = new Bet("inside", new PodiumPronostic(1, 2, 3), 1_500L);
        Bet after = new Bet("after", new PodiumPronostic(1, 2, 3), 2_500L);
        repository.register(before);
        repository.register(inside);
        repository.register(after);

        assertThat(repository.findByDateRange(1_000L, 2_000L)).containsExactly(inside);
    }
}
