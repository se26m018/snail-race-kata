package snail.race.kata.adapters;

import org.junit.jupiter.api.Test;
import snail.race.kata.domain.Bet;
import snail.race.kata.domain.BetRepository;
import snail.race.kata.domain.PodiumPronostic;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BetRepositoryContract {

    protected abstract BetRepository repository();

    @Test
    void register_a_bets() {
        BetRepository repo = repository();
        Bet bet = new Bet("alice", new PodiumPronostic(3, 1, 2), 1_000L);

        repo.register(bet);

        assertThat(repo.findByDateRange(0L, 2_000L)).containsExactly(bet);
    }

    @Test
    void retrieve_only_bets_inside_the_time_range() {
        BetRepository repo = repository();
        Bet before = new Bet("before", new PodiumPronostic(1, 2, 3), 500L);
        Bet inside = new Bet("inside", new PodiumPronostic(1, 2, 3), 1_500L);
        Bet after = new Bet("after", new PodiumPronostic(1, 2, 3), 2_500L);

        repo.register(before);
        repo.register(inside);
        repo.register(after);

        assertThat(repo.findByDateRange(1_000L, 2_000L)).containsExactly(inside);
    }
}