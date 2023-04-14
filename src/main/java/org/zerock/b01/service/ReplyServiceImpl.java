package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Reply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResopneseDTO;
import org.zerock.b01.dto.RelpyDTO;
import org.zerock.b01.repository.ReplyRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    @Override
    public long register(RelpyDTO relpyDTO){
        Reply reply = modelMapper.map(relpyDTO, Reply.class);

        long rno = replyRepository.save(reply).getRno();

        return rno;
    }

    @Override
    public RelpyDTO read(long rno){
        Optional<Reply> replyOptional = replyRepository.findById(rno);

        Reply reply = replyOptional.orElseThrow();

        return modelMapper.map(reply, RelpyDTO.class);
    }

    @Override
    public void modify(RelpyDTO relpyDTO){
        Optional<Reply> replyOptional = replyRepository.findById(relpyDTO.getRno());

        Reply reply = replyOptional.orElseThrow();

        reply.changeText(relpyDTO.getReplyText()); // 댓글의 내용만 수정 가능

        replyRepository.save(reply);
    }

    @Override
    public void remove(long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResopneseDTO<RelpyDTO> getListOfBoard(long bno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        List<RelpyDTO> dtoList =
                result.getContent().stream().map(reply -> modelMapper.map(reply, RelpyDTO.class))
                        .collect(Collectors.toList());

        return PageResopneseDTO.<RelpyDTO>withALl()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
