package Energeenot.TestTaskFromComfortSoft.controller;

import Energeenot.TestTaskFromComfortSoft.dto.ErrorResponse;
import Energeenot.TestTaskFromComfortSoft.service.SearcherService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
        info = @Info(
                title = "API для поиска N-ого максимального числа",
                description = "Этот API позволяет загрузить файл в формате xlsx, содержащий целые числа, и найти N-ое максимальное число в этом файле. " +
                        "Метод поддерживает валидацию входных данных и предоставляет информативные ошибки в случае неверного ввода."
        )
)

@RestController
@RequestMapping("/search")
@Validated
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
    public int getNthMax(@RequestParam  @NotBlank(message = "Путь к файлу не должен быть пустым") @Parameter(description = "Путь к файлу", required = true, example = "C:/data/numbers.xlsx")String filepath,
                         @RequestParam  @Min(value = 1, message = "N должно быть >= 1") @Parameter(description = "Число N для поиска максимума", required = true, example = "3") int n) {
        log.info("Запрос на поиск {} числа из файла с путём {}", n, filepath);
        return searcherService.findNthMaxNumber(filepath, n);
    }
}
