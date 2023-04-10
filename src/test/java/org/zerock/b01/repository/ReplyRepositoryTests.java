package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert() {
        // 실제 DB에 있는 bno
        long bno = 101L;

        Board board = Board.builder().bno(bno).build();

        IntStream.rangeClosed(1, 4).forEach(i -> {
            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("댓글....." + i)
                    .replyer("replyer1" + i)
                    .build();

            replyRepository.save(reply);
        });

    }
}
