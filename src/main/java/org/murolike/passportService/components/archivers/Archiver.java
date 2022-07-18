package org.murolike.passportService.components.archivers;

import java.io.File;
import java.io.IOException;

public interface Archiver {

    /**
     * Распаковка файла
     *
     * @return Возвращает распакованный файл
     * @throws IOException          Возникает, когда есть проблема с файлом (архивом или созданием нового)
     * @throws InterruptedException Возникает, когда есть проблемы с запуском внешнего процесса
     */
    File unzip() throws IOException, InterruptedException;
}
