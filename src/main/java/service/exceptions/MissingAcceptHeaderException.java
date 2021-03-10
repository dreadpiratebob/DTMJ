package service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The 'accept' header was missing.")
public class MissingAcceptHeaderException extends RuntimeException
{
}
