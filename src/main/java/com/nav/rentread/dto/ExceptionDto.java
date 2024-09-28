package com.nav.rentread.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDto<T> {

    T error;

}
