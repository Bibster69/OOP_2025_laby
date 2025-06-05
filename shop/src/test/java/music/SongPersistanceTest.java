package music;

import database.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;



class SongPersistenceTest {
    static DatabaseConnection connection;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = new DatabaseConnection();
        connection.connect("src/main/resources/songs.db");
        Song.Persistence.setConnection(connection);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.disconnect();
    }

    // Zadanie 2b
    @Test
    void testReadValidIndex() {
        Optional<Song> result = Song.Persistence.read(1);
        assertTrue(result.isPresent());
        assertEquals("The Beatles", result.get().artist());
        assertEquals("Hey Jude", result.get().title());
        assertEquals(431, result.get().duration());
    }

    // Zadanie 2c
    @Test
    void testReadInvalidIndex() {
        Optional<Song> result = Song.Persistence.read(9999);
        assertTrue(result.isEmpty());
    }

    // Zadanie 2d
    static Stream<org.junit.jupiter.params.provider.Arguments> songData() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(1, new Song("The Beatles", "Hey Jude", 431)),
                org.junit.jupiter.params.provider.Arguments.of(2, new Song("The Rolling Stones", "(I Can't Get No) Satisfaction", 224)),
                org.junit.jupiter.params.provider.Arguments.of(3, new Song("Led Zeppelin", "Stairway to Heaven", 482))
        );
    }

    @ParameterizedTest
    @MethodSource("songData")
    void testReadParameterized(int index, Song expected) {
        Optional<Song> result = Song.Persistence.read(index);
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    // Zadanie 2e
    @ParameterizedTest
    @CsvFileSource(resources = "/songs.csv", numLinesToSkip = 1)
    void testReadFromCsvFile(int id, String artist, String title, int length) {
        Optional<Song> result = Song.Persistence.read(id);
        assertTrue(result.isPresent());
        assertEquals(artist, result.get().artist());
        assertEquals(title, result.get().title());
        assertEquals(length, result.get().duration());
    }

    // TDD
}