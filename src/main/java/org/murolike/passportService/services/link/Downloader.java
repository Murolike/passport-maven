package org.murolike.passportService.services.link;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * TODO: Возможно использовать File.createTemporaryFile
 * Low speed download
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
        return File.createTempFile("tmp", getExtensions(), new File(this.storagePath));
    }

    protected String getExtensions() {
        File file = new File(this.link.getFile());
        String name = file.getName();

        return name.substring(name.indexOf("."));
    }
}
