package org.murolike.passportService.facades;

import org.murolike.passportService.components.archivers.Archiver;
import org.murolike.passportService.components.archivers.ConsoleBzip;
import org.murolike.passportService.components.downloaders.ChannelDownloader;
import org.murolike.passportService.components.downloaders.Downloader;

import java.io.File;
import java.io.IOException;

/**
 * Класс для загрузки и распаковки файла (фасад)
 */
public class PassportLoader {

    private final String passportUpdateFileLink;
    private final String storagePath;

    public PassportLoader(String passportUpdateFileLink, String storagePath) {
        this.passportUpdateFileLink = passportUpdateFileLink;
        this.storagePath = storagePath;
    }

    /**
     * Скачивает файл
     *
     * @return Возвращает скаченный файл
     * @throws IOException Возникает, когда не удалось создать или скачать файл
     */
    protected File download() throws IOException {
        Downloader downloader = new ChannelDownloader(passportUpdateFileLink, storagePath);
        return downloader.download();
    }

    /**
     * Распаковка архивного файл
     *
     * @param archive Упакованный файл
     * @return Возвращает распакованный файл
     * @throws IOException          Возникает, когда не удалось найти файл или есть проблемы с созданием
     * @throws InterruptedException Возникает, когда не удалось успешно запустить программу для распаковки
     */
    protected File unzip(File archive) throws IOException, InterruptedException {
        Archiver bzip2 = new ConsoleBzip(archive);
        return bzip2.unzip();
    }

    public File load() throws IOException, InterruptedException {
        File archive = download();
        File sourceFile = unzip(archive);

        if (!archive.delete()) {
            archive.deleteOnExit();
        }

        return sourceFile;
    }
}
