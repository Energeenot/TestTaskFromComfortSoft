package Energeenot.TestTaskFromComfortSoft.service;

import Energeenot.TestTaskFromComfortSoft.dto.NthMaxRequest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.PriorityQueue;

@Service
public class SearcherService {

    private static final Logger log = LoggerFactory.getLogger(SearcherService.class);

    public int findNthMaxNumber(NthMaxRequest request) {
        int n = request.getN();
        String filepath = request.getFilepath();

        log.info("Попытка открыть файл и считать значения ячеек");
        try (FileInputStream fis = new FileInputStream(filepath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            PriorityQueue<Integer> maxNumber = new PriorityQueue<>(n);

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell.getCellType() == CellType.NUMERIC) {
                    log.debug("Обрабатывается строка с значением {}", cell.getNumericCellValue());
                    int value = (int) cell.getNumericCellValue();
                    if (maxNumber.size() < n) {
                        maxNumber.offer(value);
                    } else if (!maxNumber.isEmpty() && value > maxNumber.peek()) {
                        maxNumber.poll();
                        maxNumber.offer(value);
                    }
                } else {
                    log.debug("Строка не содержит валидное число");
                }
            }

            if (maxNumber.size() < n) {
                log.error("Файл содержит меньше чем {} чисел", n);
                throw new IllegalArgumentException("Файл содержит меньше чем " + n + " чисел");
            }

            log.info("Поиск завершён, {}-е максимальное число {}", n, maxNumber.peek());
            return Optional.ofNullable(maxNumber.peek()).orElseThrow(() -> new IllegalArgumentException("Что-то пошло не так"));
        } catch (FileNotFoundException ex) {
            log.error("Файл не найден, путь к файлу {}", filepath, ex);
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            log.error("Произошла ошибка при обработке файла: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}
