package pl.vbstats.user.Controller;

import java.util.List;

public record ApiErrorResponse(boolean status, List<String> messages) {

}
