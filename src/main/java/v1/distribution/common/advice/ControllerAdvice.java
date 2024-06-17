package v1.distribution.common.advice;

import static v1.distribution.common.DistributionErrors.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import v1.distribution.common.DistributionBadRequestException;
import v1.distribution.common.DistributionException;
import v1.distribution.common.DistributionNotFoundException;
import v1.distribution.common.ErrorResponse;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse error(Exception e) {
        log.error("[!] 예외발생 - {}", e.getMessage(), e);
        return new ErrorResponse(INTERNAL_SERVER_ERROR.getErrorMsg());
    }

    @ExceptionHandler(DistributionException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse distributionError(DistributionException e) {
        log.error("[!] 예외발생 - {}: {}", e.getError().name(), e.getError().getErrorMsg(), e);
        return new ErrorResponse(e.getError().getErrorMsg());
    }

    @ExceptionHandler(DistributionBadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse distributionBadRequestError(DistributionBadRequestException e) {
        log.warn("[!] 예외발생 - {}: {}", e.getError().name(), e.getError().getErrorMsg(), e);
        return new ErrorResponse(e.getError().getErrorMsg());
    }

    @ExceptionHandler(DistributionNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponse distributionNotFoundError(DistributionNotFoundException e) {
        log.info("[!] 예외발생 - {}: {}", e.getError().name(), e.getError().getErrorMsg());
        return new ErrorResponse(e.getError().getErrorMsg());
    }
}
