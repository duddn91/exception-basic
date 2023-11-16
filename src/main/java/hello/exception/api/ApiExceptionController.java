package hello.exception.api;

import hello.exception.BadRequestException;
import hello.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiExceptionController {

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if ("ex".equals(id)) {
            throw new RuntimeException("잘못된 사용자");
        }

        if ("bad".equals(id)) {
            throw new IllegalArgumentException("잘못된 사용자");
        }

        if ("user-ex".equals(id)) {
            throw new UserException("사용자 오");
        }

        return new MemberDto(id, "hello " + id);
    }

    @GetMapping("/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    @GetMapping("/response-status-ex2")
    public String responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }

    // DefaultHandlerExceptionResolver 에서 TypeMisMatch 에러 발생시 400으로 http 상태코드를 설정해줌
    // ex) data = qqq  => TypeMisMatch 발생
    @GetMapping("/default-handler-ex")
    public String defaultHandlerEx(@RequestParam Integer data) {
        return "ok";
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
