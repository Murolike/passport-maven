package org.murolike.passportService.services.link;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.*;

public class Bzip2 {
    /**
     * Буфер
     */
    public static Integer BUFFER_SIZE = 2048;
    /**
     * Архивный файл
     */
    protected File archive;

    public Bzip2(File archive) {
        this.archive = archive;
    }

    /**
     * Метод для разорхивации файлов
     * TODO: при переходе на >8 версии, использовать transferTo
     * @return Возвращает ссылку на единственный файл внутри
     * @throws IOException Возникает когда файл не был найден
     */
    public File unzip() throws IOException {
        File file = this.createFile();
        BZip2CompressorInputStream bZipInputStream = new BZip2CompressorInputStream(new FileInputStream(this.archive), true);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        bZipInputStream.transferTo(fileOutputStream);

        final byte[] buffer = new byte[BUFFER_SIZE];
        int n;
        while (-1 != (n = bZipInputStream.read(buffer))) {
            fileOutputStream.write(buffer, 0, n);
        }

        fileOutputStream.close();
        bZipInputStream.close();

        return file;
    }

    /**
     * Создает файл, если он есть, то удалит
     *
     * @return Возвращает файл
     * @throws IOException Возникает, когда не удалось удалить существующий файл
     */
    protected File createFile() throws IOException {
        File file = new File(this.getUncompressedFilePath());
        if (file.exists() && !file.delete()) {
            throw new IOException("Не могу удалить файл по пути: " + file.getAbsolutePath());
        }
        return file;
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
}
