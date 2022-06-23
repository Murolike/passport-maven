package org.murolike.passportService.services.link;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TODO: Возможно использовать File.createTemporaryFile чтобы избавиться от storagePath и от генерации имени
 */
public class Downloader {
    protected URL link;
    protected String storagePath;

    public Downloader(String link, String storagePath) throws MalformedURLException {
        this.link = new URL(link);
        this.storagePath = storagePath;

    }

    public File download() throws IOException {
        File file = this.createFile();
        ReadableByteChannel readableByteChannel = Channels.newChannel(this.link.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

        return file;
    }

    protected File createFile() throws IOException {
        File file = new File(this.storagePath + this.generateFileName());
        if (file.exists() && !file.delete()) {
            throw new IOException("Не могу удалить файл по пути: " + file.getAbsolutePath());
        }
        return file;
    }

    protected String getShortFileName() {
        File file = new File(this.link.getFile());
        return file.getName();
    }

    protected String generateFileName() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");

        return dateTime.format(dateTimeFormatter) + "_" + getShortFileName();
    }
}
