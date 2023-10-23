package com.juliusramonas.beertest.component.advice;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MethodExecutionDurations {

    private final Map<String, Long> methodDurations = new HashMap<>();

}
