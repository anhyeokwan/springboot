package org.zerock.b01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResopneseDTO;
import org.zerock.b01.dto.RelpyDTO;
import org.zerock.b01.service.ReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor // 의존성 주입을 위한
@Log4j2
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    @ApiOperation(value = "Replies POST", notes = "POST 방식으로 댓글등록")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(
            @Valid @RequestBody RelpyDTO relpyDTO
            , BindingResult bindingResult) throws BindException {
        log.info(relpyDTO);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>(); // Map.of는 put 같은 느낌
        long rno = replyService.register(relpyDTO);

        resultMap.put("rno", rno);

        return resultMap;  // ResponseEntity.ok(resultMap) -> 괄호안에 있는 맵을 ResponseEntity에 담는다
    }

    @ApiOperation(value = "Replies of Board", notes = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{bno}")
    public PageResopneseDTO<RelpyDTO> getList(@PathVariable("bno") long bno, PageRequestDTO pageRequestDTO) {
        PageResopneseDTO<RelpyDTO> resopneseDTO = replyService.getListOfBoard(bno, pageRequestDTO);

        return resopneseDTO;
    }

    @ApiOperation(value = "Read Reply", notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping("/{rno}")
    public RelpyDTO getReplyDTO(@PathVariable("rno") long rno) {

        RelpyDTO relpyDTO = replyService.read(rno);

        return relpyDTO;

    }

    @ApiOperation(value = "Delete Reply", notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping("/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") long rno) {
        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }

    @ApiOperation(value = "Modify Reply", notes = "PUT 방식으로 특정댓글 수정")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(@PathVariable("rno") long rno, @RequestBody RelpyDTO relpyDTO) {
        relpyDTO.setRno(rno);

        replyService.modify(relpyDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }
}
