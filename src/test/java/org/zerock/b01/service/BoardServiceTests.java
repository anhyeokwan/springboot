package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user00")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("bno : " + bno);
    }

    @Test
    public void testModify(){
        // 변경에 필요한 데이터만
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("Update....101")
                .content("Update content 101....")
                .build();

        // 첨부파일을 하나 추가
        boardDTO.setFileName(Arrays.asList(UUID.randomUUID() + "_zzz.jpg"));

        boardService.modify(boardDTO);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("twc")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResopneseDTO<BoardDTO> resopneseDTO = boardService.list(pageRequestDTO);

        log.info(resopneseDTO);
    }

    @Test
    public void testRegisterWithImages() {
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("File...Sample title...")
                .content("Sample Content...")
                .writer("user00")
                .build();

        boardDTO.setFileName(
                Arrays.asList(
                        UUID.randomUUID() + "_aaa.jpg",
                        UUID.randomUUID() + "_bbb.jpg",
                        UUID.randomUUID() + "_ccc.jpg"
                )
        );

        long bno = boardService.register(boardDTO);

        log.info("bno : " + bno);
    }

    @Test
    public void testReadAll() {
        long bno = 101L;

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        for (String filename : boardDTO.getFileName()) {
            log.info(filename);
        } // end for
    }

    @Test
    public void testRemovalAll() {
        long bno = 1L;

        boardService.remove(bno);
    }

    @Test
    public void testListWithAll() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResopneseDTO<BoardListAllDTO> resopneseDTO = boardService.listWithAll(pageRequestDTO);

        List<BoardListAllDTO> dtoList = resopneseDTO.getDtoList();

        dtoList.forEach(boardListAllDTO -> {
            log.info(boardListAllDTO.getBno() + " : " + boardListAllDTO.getBoardImages());

            if (boardListAllDTO.getBoardImages() != null) {
                for (BoardImageDTO boardImage : boardListAllDTO.getBoardImages()) {
                    log.info(boardImage);

                }
            }

            log.info("---------------------------------");
        });
    }
}
