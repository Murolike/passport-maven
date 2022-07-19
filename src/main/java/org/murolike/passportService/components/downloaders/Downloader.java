package org.murolike.passportService.components.downloaders;

import java.io.File;
import java.io.IOException;

public interface Downloader {
    /**
     * Скачать и сохранить файл
     *
     * @return Возвращает файл с данными
     * @throws IOException Возникает, когда есть проблемы со скачиванием файлом или сохранением
     */
    File download() throws IOException;
}
