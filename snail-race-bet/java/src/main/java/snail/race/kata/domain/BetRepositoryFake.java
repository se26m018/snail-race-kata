package snail.race.kata.domain;

import java.util.List;

public class BetRepositoryFake implements BetRepository {

    @Override
    public void register(Bet bet) {
    }

    @Override
    public List<Bet> findByDateRange(long from, long to) {
        return null;
    }
}
