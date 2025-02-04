package Energeenot.TestTaskFromComfortSoft.controller;

import Energeenot.TestTaskFromComfortSoft.dto.ErrorResponse;
import Energeenot.TestTaskFromComfortSoft.dto.NthMaxRequest;
import Energeenot.TestTaskFromComfortSoft.service.SearcherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearcherController {

    private final SearcherService searcherService;
    private static final Logger log = LoggerFactory.getLogger(SearcherService.class);

    @Autowired
    public SearcherController(SearcherService searcherService) {
        this.searcherService = searcherService;
    }

    @GetMapping(value = "/max-n")
    @Operation(summary = "Поиск N-ого максимального числа из файла с расширением .xlsx",
            description = "Метод принимает путь к файлу и число N, затем ищет N-ый максимум")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный поиск N-го максимального числа",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(type = "integer", example = "123"))),

            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(contentMediaType = MediaType.APPLICATION_JSON_VALUE, example = """
                                    {
                                        "filepath": "Путь к файлу не должен быть пустым",
                                        "n": "N должно быть >= 1"
                                    }"""))),

            @ApiResponse(responseCode = "404", description = "Файл не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class, example = """
                                    {
                                        "error": "Файл не найден: java.io.FileNotFoundException: C:/data/numbers.xlsx (Не удается найти указанный файл)"
                                    }"""))),

            @ApiResponse(responseCode = "422", description = "В файле недостаточно числел",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class, example = """
                                    {
                                        "error": "Файл содержит меньше чем 60 чисел"
                                    }"""))),

            @ApiResponse(responseCode = "500", description = "Ошибка при обработке файла",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class, example = """
                                    {
                                                                        "error": "Произошла ошибка сервера"
                                    }""")))
    })
    public int getNthMax(@RequestBody @Valid NthMaxRequest request) {
        log.info("Запрос на поиск {} числа из файла с путём {}", request.getN(), request.getFilepath());
        return searcherService.findNthMaxNumber(request);
    }
}
