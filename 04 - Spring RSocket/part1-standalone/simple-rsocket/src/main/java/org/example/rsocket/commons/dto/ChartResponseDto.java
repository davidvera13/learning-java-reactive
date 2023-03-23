package org.example.rsocket.commons.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ChartResponseDto {
    private int input;
    private int output;

    @Override
    public String toString() {
        String graphFormat = formatString(this.output);
        return String.format(graphFormat, this.input, "X");
    }

    private String formatString(int value) {
        // we we a fist part with 3 chars a pipe and value elements
        return "%3s|%" + value + "s";
    }
}
