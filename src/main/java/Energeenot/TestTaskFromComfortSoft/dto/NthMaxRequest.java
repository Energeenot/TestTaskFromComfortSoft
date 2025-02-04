package Energeenot.TestTaskFromComfortSoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class NthMaxRequest {

    @Schema(description = "Путь к файлу", example = "C:/data/numbers.xlsx")
    @NotBlank(message = "Путь к файлу не должен быть пустым")
    private String filepath;

    @Schema(description = "Какое по счёту максимальное число необходимо найти", example = "3")
    @Min(value = 1, message = "N должно быть >= 1")
    private int n;

    public NthMaxRequest(String filepath, int n) {
        this.filepath = filepath;
        this.n = n;
    }

    public String getFilepath() {
        return filepath;
    }

    public int getN() {
        return n;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setN(int n) {
        this.n = n;
    }
}