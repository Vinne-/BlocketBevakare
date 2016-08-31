package notification;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

/**
 * A soundbox to play sounds located in the sounds resource folder
 * If a sound need to be added the Sounds enum has to be updated accordingly.
 *
 * Date: 2016-08-30
 * @author Vincent
 *
 */
public class SoundBox {

    /**
     * Enum with all availible sounds.     *
     *
     */
    public enum Sounds {
        BELL("Bell0.mp3", "Bell"),
        THREE_TONE_NOTEFICATION("3 Tone Notification0.mp3", "3 Tone Notification"),
        COOL_NOTEFICATION_TWO("Cool Notification 20.mp3", "Cool Notification 2"),
        SCOOBY("Scooby Dooby Doo0.mp3", "Scooby");

        private String filename;
        private String name;

        private Sounds(String filename, String name) {
            this.filename = filename;
        }

        public String getFilename() {
            return this.filename;
        }

        public String getName() {
            return this.name;
        }

    }

    //the path to the media source directory.
    private static final String MEDIA_SRC = "src\\main\\resources\\sound\\";

    /**
     * Plays the specifed sound.
     *
     * @param sound
     */
    public void playSound(Sounds sound) {
        String filename = Paths.get(MEDIA_SRC + sound.getFilename()).toUri().toString();
        Media hit = new Media(filename);
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }

}
