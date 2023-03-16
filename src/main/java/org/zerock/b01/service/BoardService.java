package org.zerock.b01.service;

import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResopneseDTO;

public interface BoardService {

    long register(BoardDTO boardDTO);

    BoardDTO readOne(long bno);

    void modify(BoardDTO boardDTO);

    void remove(long bno);

    PageResopneseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
}
