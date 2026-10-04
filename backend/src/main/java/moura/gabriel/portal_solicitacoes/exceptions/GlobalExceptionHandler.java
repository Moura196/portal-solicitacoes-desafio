package moura.gabriel.portal_solicitacoes.exceptions;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import moura.gabriel.portal_solicitacoes.dtos.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException e) {
        ErrorResponseDTO erro = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalState(IllegalStateException e) {
        ErrorResponseDTO erro = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException e) {
        String mensagem = e.getBindingResult().getFieldErrors().stream()
            .map(field -> field.isBindingFailure()
                ? field.getField() + ": valor '" + field.getRejectedValue() + "' é inválido"
                : field.getField() + ": " + field.getDefaultMessage())
            .collect(Collectors.joining(", "));
        ErrorResponseDTO erro = new ErrorResponseDTO(mensagem, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleBodyInvalido(HttpMessageNotReadableException e) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
            "Corpo da requisição inválido ou com valor não reconhecido.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTipoParametro(MethodArgumentTypeMismatchException e) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
            "Valor inválido para o parâmetro '" + e.getPropertyName() + "'.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponseDTO> handleBind(BindException e) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
            "Parâmetro inválido na requisição.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenerico(Exception e) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
            "Ocorreu um erro interno no servidor.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

}
