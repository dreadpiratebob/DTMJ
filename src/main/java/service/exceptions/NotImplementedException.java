package service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "the code followed a path that hasn't been implemented.")
public class NotImplementedException extends RuntimeException
{
}
