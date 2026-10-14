package snail.race.kata.adapters;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snail.race.kata.domain.Bet;
import snail.race.kata.domain.BetRepository;
import snail.race.kata.domain.PodiumPronostic;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class BetRepositoryMongoDbTest extends BetRepositoryContract {
    MongoClient mongoClient;
    BetRepositoryMongoDb repository;

    @BeforeEach
    void setUp() {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        mongoClient = MongoClients.create("mongodb://localhost");
        MongoDatabase database = mongoClient.getDatabase("contract_testing").withCodecRegistry(pojoCodecRegistry);

        database.getCollection("bet", Bet.class).drop();

        repository = new BetRepositoryMongoDb(database);

    }

    @AfterEach
    void tearDown() {
        mongoClient.close();
    }

    @Override
    protected BetRepository repository() {
        return repository;
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
