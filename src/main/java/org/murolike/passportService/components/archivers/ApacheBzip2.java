package org.murolike.passportService.components.archivers;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Класс для работы с архивами с помощью функционала Apache Bzip2
 */
public class ApacheBzip2 implements Archiver {
    /**
     * Архивный файл
     */
    protected File archive;

    public ApacheBzip2(File archive) {
        this.archive = archive;
    }

    /**
     * Метод для разорхивации файлов
     *
     * @return Возвращает ссылку на единственный файл внутри
     * @throws IOException Возникает когда файл не был найден
     */
    @Override
    public File unzip() throws IOException {
        File file = this.createFile();
        BZip2CompressorInputStream bZipInputStream = new BZip2CompressorInputStream(new FileInputStream(this.archive), true);
        ReadableByteChannel readableByteChannel = Channels.newChannel(bZipInputStream);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

        fileOutputStream.close();
        bZipInputStream.close();


        return file;
    }

    /**
     * Создает временный файл с расширением
     *
     * @return Возвращает файл
     * @throws IOException Возникает, когда не удалось создать файл
     */
    protected File createFile() throws IOException {
        String extension = this.getExtensionFile();
        return File.createTempFile("tmp", extension, archive.getParentFile());
    }

    /**
     * Метод возвращает полный путь до файла без расширения архива
     *
     * @return Возвращает полный путь до файла
     */
    protected String getUncompressedFilePath() {
        String path = this.archive.getAbsolutePath();
        return path.substring(0, path.lastIndexOf("."));
    }

    protected String getExtensionFile() {
        String path = this.getUncompressedFilePath();
        return path.substring(path.lastIndexOf("."));
    }
}
