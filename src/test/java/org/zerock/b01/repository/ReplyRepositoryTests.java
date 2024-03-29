package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;

import javax.transaction.Transactional;
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

        IntStream.rangeClosed(9, 50).forEach(i -> {
            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("댓글....." + i)
                    .replyer("replyer1" + i)
                    .build();

            replyRepository.save(reply);
        });

    }

    @Test
    @Transactional
    public void testBoardReplies() {
        long bno = 101L;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        result.getContent().forEach(reply -> {
            log.info(reply);

        });
    }
}
