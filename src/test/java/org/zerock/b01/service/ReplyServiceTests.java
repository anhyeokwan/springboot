package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.RelpyDTO;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testRegister() {
        RelpyDTO relpyDTO = RelpyDTO.builder()
                .replyText("ReplyDTO Text")
                .replyer("replyer")
                .bno(101L)
                .build();

        log.info(replyService.register(relpyDTO));
    }
}
