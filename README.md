## Описание

Это приложение на Spring Boot, которое предоставляет API для поиска N-го максимального числа в файле Excel (.xlsx). API позволяет отправлять запросы с путем к файлу и числом N, а затем возвращает N-е максимальное число из столбца с числами в файле.

## Требования

Перед запуском убедитесь, что у вас установлены:
-Java 21
-Maven

## Сборка и запуск приложения

1. Клонируйте репозиторий
2. При необходимости изменить порт:
  - Откройте файл "application.properties"
  - Измените строку: server.port='нужный адрес порта'
3. Соберите проект с помощью Maven: 'mvn clean install'
4. Запустите приложение: 'mvn spring-boot:run'

## Swagger UI
Swagger UI доступен по следующему адресу после запуска проекта:
- URL: http://localhost:8080/swagger-ui/index.html

## Приложение предоставляет один эндпоинт:

### Поиск N-го максимального числа из файла
- **URL**: `http://localhost:8080/search/max-n`
- **Метод**: `GET`
- **Параметры запроса**:
    - `filepath` (обязательный): Путь к файлу Excel (.xlsx) на вашем локальном компьютере.
    - `n` (обязательный): Число N для поиска N-го максимума.
  
Пример запроса с использованием `curl`:

'curl -X GET "http://localhost:8080/search/max-n?filepath=C:/dataForTest.xlsx&n=3"'
