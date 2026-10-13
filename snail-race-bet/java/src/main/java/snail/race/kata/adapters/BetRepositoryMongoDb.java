package snail.race.kata.adapters;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import snail.race.kata.domain.Bet;
import snail.race.kata.domain.BetRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Sorts.ascending;

public class BetRepositoryMongoDb implements BetRepository {
    private static final String COLLECTION = "bet";
    private final MongoCollection<Bet> bets;

    public BetRepositoryMongoDb(MongoDatabase database) {
        this.bets = database.getCollection(COLLECTION, Bet.class);
    }

    @Override
    public void register(Bet bet) {
        bets.insertOne(bet);
    }

    @Override
    public List<Bet> findByDateRange(long from, long to) {
        return bets.find(and(gte("timestamp", from), lte("timestamp", to)))
                .sort(ascending("timestamp"))
                .into(new ArrayList<>());
    }
}
