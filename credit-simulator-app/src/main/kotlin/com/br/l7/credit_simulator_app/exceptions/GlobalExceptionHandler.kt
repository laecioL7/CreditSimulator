//package com.br.l7.credit_simulator_app.exceptions
//
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.validation.FieldError
//import org.springframework.web.bind.MethodArgumentNotValidException
//import org.springframework.web.bind.annotation.ControllerAdvice
//import org.springframework.web.bind.annotation.ExceptionHandler
//
//
//@ControllerAdvice
//class GlobalExceptionHandler {
//
//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
//        val errors = mutableMapOf<String, String>()
//        ex.bindingResult.allErrors.forEach { error ->
//            val fieldName = (error as FieldError).field
//            val errorMessage = error.defaultMessage ?: "Invalid value"
//            errors[fieldName] = errorMessage
//        }
//        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
//    }
//}