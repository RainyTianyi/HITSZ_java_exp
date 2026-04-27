package edu.hitsz.music;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;

public class MusicThread extends Thread {


    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;

    private volatile boolean isPlaying = false;
    private volatile boolean loop = false;
    private SourceDataLine dataLine = null;

    public MusicThread(String filename) {
        this.filename = filename;
        reverseMusic();
    }

    public void reverseMusic() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException e) {
            System.err.println("不支持的音频文件格式: " + filename);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("读取音频文件失败: " + filename);
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            System.err.println("读取音频数据失败");
            e.printStackTrace();
        }
        return samples;
    }

    public void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        dataLine = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            System.err.println("无法获取音频行");
            e.printStackTrace();
            return;
        }

        isPlaying = true;
        dataLine.start();

        do {
            try {
                int numBytesRead = 0;
                while (numBytesRead != -1 && isPlaying) {
                    numBytesRead = source.read(buffer, 0, buffer.length);
                    if (numBytesRead != -1 && isPlaying) {
                        dataLine.write(buffer, 0, numBytesRead);
                    }
                }
            } catch (IOException ex) {
                System.err.println("播放音频时发生错误");
                ex.printStackTrace();
            }
        } while (loop && isPlaying);

        dataLine.drain();
        dataLine.close();
        dataLine = null;
        isPlaying = false;
    }

    @Override
    public void run() {
        InputStream stream = new ByteArrayInputStream(samples);
        play(stream);
    }

    public void stopPlaying() {
        isPlaying = false;
        if (dataLine != null) {
            dataLine.stop();
        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isLoop() {
        return loop;
    }
}
