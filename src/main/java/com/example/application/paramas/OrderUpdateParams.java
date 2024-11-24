package com.example.application.paramas;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateParams {

    private String number;
    private LocalDateTime creationDate;
    private LocalDateTime dateReceived;
    private Float total;
}
