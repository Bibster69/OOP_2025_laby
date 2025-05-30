package music;

import java.util.ArrayList;


public class Playlist extends ArrayList<Song> {
    public Song atSecond(int second) {
        if (second < 0) {
            throw new IndexOutOfBoundsException("Piosenka z negatywnym czasem trwania");
        }

        int current = 0;
        for (Song song : this) {
            if (second < current + song.duration()) {
                return song;
            }
            current += song.duration();
        }

        throw new IndexOutOfBoundsException("Podany czas jest większy od czasu trwania całej pleylisty");
    }
}
