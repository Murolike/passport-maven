# Запуск тестов

mvn test -Ptest

# Запуск приложения для разработки

mvn spring-boot:run -Pdev

# Запуск боевого приложения

mvn spring-boot:run -Pprod

# Docker

Для правильно работы нужен образ с pgloader + bzip2 (к сожалению нет pgloader в alpine).

Для базового ф-циона реализован, необходимо выполнить следующее:

1. mvn clean install
2. docker-compose -f docker-compose.dev.yaml