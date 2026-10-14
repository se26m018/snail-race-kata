package snail.race.kata.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BetRepositoryFake implements BetRepository {
    private List<Bet> bets = new ArrayList<>();

    @Override
    public void register(Bet bet) {
        this.bets.add(bet);
    }

    @Override
    public List<Bet> findByDateRange(long from, long to) {
        return this.bets.stream()
            .filter(bet -> bet.timestamp() >= from && bet.timestamp() <= to)
            .collect(Collectors.toUnmodifiableList());
    }
}
