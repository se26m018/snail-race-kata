package snail.race.kata.domain;

import org.junit.jupiter.api.BeforeEach;

class RaceResultProviderFakeTest extends RaceResultProviderTest {
    private RaceResultProviderFake provider;

    @Override
    protected RaceResultProvider provider() {
        return provider;
    }
    
    @BeforeEach
    void setUp() {
        provider = new RaceResultProviderFake();
    }
}
