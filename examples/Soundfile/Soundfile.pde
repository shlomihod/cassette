//Sound file from: http://soundbible.com/2083-Crickets-Chirping-At-Night.html

import processing.sound.android.*;

SoundFile music;

void setup() {
    size(1000, 1000);
    background(255, 0, 0);
  
    music = new SoundFile(this, "Crickets.mp3");
    music.loop();
    music.play();
}

void draw() {
}

void mousePressed() {
    music.stop();
} 