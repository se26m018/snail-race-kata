package snail.race.kata.adapters;

import org.junit.jupiter.api.BeforeEach;

import snail.race.kata.domain.RaceResultProvider;
import snail.race.kata.domain.RaceResultProviderTest;

public class RaceResultProviderHttpTest extends RaceResultProviderTest {
    private RaceResultProviderHttp provider;

    @Override
    protected RaceResultProvider provider() {
        return provider;
    }
    
    @BeforeEach
    void setUp() {
        provider = new RaceResultProviderHttp();
    }
}
