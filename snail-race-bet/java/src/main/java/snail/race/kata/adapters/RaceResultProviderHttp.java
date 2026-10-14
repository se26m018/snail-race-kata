package snail.race.kata.adapters;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import snail.race.kata.domain.RaceResultProvider;

public class RaceResultProviderHttp implements RaceResultProvider {
    record SnailDto(int number, String name, double duration) {
    }

    record SnailRaceDto(int raceId, long timestamp, List<SnailDto> snails) {
    }

    record SnailRacesDto(List<SnailRaceDto> races) {
    }

    @Override
    public SnailRaces races() {
        SnailRacesDto races = fetch();
        return toDomain(races);
    }

    private SnailRaces toDomain(SnailRacesDto racesDto) {
        if (racesDto == null || racesDto.races() == null) {
            return new SnailRaces(Collections.emptyList());
        }
        return new SnailRaces(
            racesDto.races().stream()
                .map(r -> new SnailRace(
                    r.raceId(),
                    r.timestamp(),
                    calculatePodium(r.snails)))
                .toList());
    }

    private Podium calculatePodium(List<SnailDto> snails) {
        if (snails == null) {
            return new Podium(null, null, null);
        }
        List<Snail> topSnails = snails.stream()
            .sorted((s1, s2) -> Double.compare(s1.duration, s2.duration))
            .limit(3)
            .map(s -> new Snail(s.number(), s.name()))
            .collect(Collectors.toList());
        return new Podium(
            !topSnails.isEmpty() ? topSnails.remove(0) : null, 
            !topSnails.isEmpty() ? topSnails.remove(0) : null,
            !topSnails.isEmpty() ? topSnails.remove(0) : null);
    }

    private SnailRacesDto fetch() {
        var httpClient = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8000/results"))
                .version(HttpClient.Version.HTTP_1_1).build();
        try {
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            return new ObjectMapper().readValue(response.body(), SnailRacesDto.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
