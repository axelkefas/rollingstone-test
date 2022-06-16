package com.axel.rollingstone.test.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@AllArgsConstructor
public class GlobalException extends RuntimeException implements Serializable {
    final StatusCode statusCode;

    final String message;
}
