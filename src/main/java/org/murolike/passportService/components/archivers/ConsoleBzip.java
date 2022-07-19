package org.murolike.passportService.components.archivers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с архивным файлом через консольное приложение bzip2 (unix)
 */
public class ConsoleBzip implements Archiver {
    /**
     * Архивный файл
     */
    protected File archive;

    public ConsoleBzip(File archive) {
        this.archive = archive;
    }

     // TODO Реализовать свои Exception под каждый код ошибки, чтобы удобнее было работать с классом
     // TODO Добавить проверку на unix систему и на наличия приложения bzip2
    /**
     * Распаковка файла
     *
     * @return Возвращает распакованный файл
     * @throws IOException          Возникает, когда есть проблема с файлом (архивом или созданием нового)
     * @throws InterruptedException Возникает, когда есть проблемы с запуском внешнего процесса
     */
    @Override
    public File unzip() throws IOException, InterruptedException {
        List<String> commands = new ArrayList<>();
        commands.add("bzip2");
        commands.add("-d");
        commands.add("-k");
        commands.add(archive.getAbsolutePath());

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);
        Process process = processBuilder.start();
        int exitValue = process.waitFor();

        if (exitValue != 0) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String reason = errorReader.readLine();
            errorReader.close();
            throw new RuntimeException("Не удалось распоковать архив. Код ошибки: " + exitValue + ". Причина: " + reason);
        }

        return new File(getUncompressedFilePath());
    }

    //TODO Можно совместить с методом из ApacheBzip2
    /**
     * Получить полный путь до файла без архивного расширения
     *
     * @return Возвращает полный путь до файла
     */
    protected String getUncompressedFilePath() {
        String path = this.archive.getAbsolutePath();
        return path.substring(0, path.lastIndexOf("."));
    }
}
