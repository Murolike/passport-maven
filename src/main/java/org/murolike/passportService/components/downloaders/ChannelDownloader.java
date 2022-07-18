package org.murolike.passportService.components.downloaders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Скачивание файла с помощью J8
 */
public class ChannelDownloader implements Downloader {
    protected URL link;
    protected String storagePath;

    public ChannelDownloader(String link, String storagePath) throws MalformedURLException {
        this.link = new URL(link);
        this.storagePath = storagePath;

    }

    /**
     * @return Возвращает скаченный файл
     * @throws IOException Возникает, когда есть проблемы с созданием временного файла
     */
    public File download() throws IOException {
        File file = this.createFile();
        ReadableByteChannel readableByteChannel = Channels.newChannel(this.link.openStream());

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

        return file;
    }

    /**
     * Создает временный файл с расширением в папке указанной данному классу
     *
     * @return Возвращает временный файл с расширением
     * @throws IOException Возникает, когда не удалось создать файл по пути
     */
    protected File createFile() throws IOException {
        return File.createTempFile("tmp", getExtensions(), new File(this.storagePath));
    }

    /**
     * Получает расширение файл
     *
     * @return Возвращает расширение с точкой
     */
    protected String getExtensions() {
        File file = new File(this.link.getFile());
        String name = file.getName();

        return name.substring(name.indexOf("."));
    }
}
