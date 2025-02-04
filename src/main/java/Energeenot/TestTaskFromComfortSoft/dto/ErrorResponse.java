package Energeenot.TestTaskFromComfortSoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponse {

    @Schema(description = "Описание ошибки", example = """
            {
            "error": "Файл не найден: java.io.FileNotFoundException: C:/data/numbers.xlsx (Не удается найти указанный файл)"
            }""")
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
