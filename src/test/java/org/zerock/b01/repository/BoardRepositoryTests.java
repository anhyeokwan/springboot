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

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO : " + result.getBno());
        });
    }

    @Test
    public void testSelect(){
        long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);
    }

    @Test
    public void testUpdate(){
        long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        board.change("update..title 100", "update content 100");

        boardRepository.save(board);
    }

    @Test
    public void testDelete(){
        long bno = 100L;

        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging(){
        //1 page order by bno desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> page = boardRepository.findAll(pageable);

        log.info("total count : " + page.getTotalElements());
        log.info("total pages : " + page.getTotalPages());
        log.info("page number : " + page.getNumber());
        log.info("page size : " + page.getSize());

        List<Board> todoList = page.getContent();

        todoList.forEach(board -> log.info(board));
    }

    @Test
    public void testSearch1(){
        // 2 page order by bno desc
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        boardRepository.search1(pageable);
    }
}
