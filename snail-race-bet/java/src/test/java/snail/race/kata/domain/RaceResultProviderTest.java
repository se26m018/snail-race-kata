package snail.race.kata.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public abstract class RaceResultProviderTest {
    protected abstract RaceResultProvider provider();

    @Test
    void races() {
         RaceResultProvider.SnailRaces result = provider().races();

         assertNotNull(result);
         assertNotNull(result.races());
         assertThat(result.races()).isNotEmpty();
         RaceResultProvider.SnailRace race = result.races().get(0);
         assertNotNull(race);
         assertThat(race.raceId()).isNotZero();
         assertThat(race.timestamp()).isNotZero();
         RaceResultProvider.Podium podium = race.podium();
         assertNotNull(podium);
         assertNotNull(podium.first());
         assertNotNull(podium.second());
         assertNotNull(podium.third());
         RaceResultProvider.Snail snail = podium.first();
         assertThat(snail.number()).isNotZero();
         assertNotNull(snail.name());
         assertThat(snail.name()).isNotEmpty();
    }
}
