package music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {

    @Test
    public void testNewPlaylistIsEmpty() {
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());
    }

    // Zadanie 1c.
    @Test
    public void testPlaylistSizeAfterAddingOneSong() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("TestArtist", "TestTitle", 120));
        assertEquals(1, playlist.size());
    }

    // Zadanie 1d.
    @Test
    public void testSongInPlaylistIsSameObject() {
        Playlist playlist = new Playlist();
        Song song = new Song("TestArtist", "TestTitle", 120);
        playlist.add(song);
        assertSame(song, playlist.get(0));
    }

    // Zadanie 1e.
    @Test
    public void testSongInPlaylistIsEqual() {
        Playlist playlist = new Playlist();
        Song song = new Song("TestArtist", "TestTitle", 120);
        playlist.add(song);
        assertEquals(new Song("TestArtist", "TestTitle", 120), playlist.get(0));
    }

    // Zadanie 1f.
    @Test
    public void testAtSecondReturnsCorrectSong() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("TestArtist1", "TestSong1", 100));
        playlist.add(new Song("TestArtist2", "TestSong2", 200));
        assertEquals("TestSong2", playlist.atSecond(150).title());
    }

    // Zadanie 1g.
    @Test
    public void testAtSecondThrowsIfTooLate() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("TestArtist", "TestSong", 100));
        assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(150));
    }

    // Zadanie 1h. - negatywny czas trwania
    @Test
    public void testAtSecondThrowsWithNegativeMessage() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("TestArtist", "TestSong", 100));
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(-10));
        assertEquals("Piosenka z negatywnym czasem trwania", e.getMessage());
    }

    // Zadanie 1h. - zbyt duży czas trwania
    @Test
    public void testAtSecondThrowsWithTooLateMessage() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("TestArtist", "TestSong", 100));
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(200));
        assertEquals("Podany czas jest większy od czasu trwania całej pleylisty", e.getMessage());
    }
}