package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.*;
import org.zerock.b01.service.BoardService;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller // 컨트롤러
@RequestMapping("/board") // 기본 경로 설정
@Log4j2 // 로깅
@RequiredArgsConstructor // autowired를 생성안하게 해주는 어노테이션
public class BoardController {

    @Value("${org.zerock.upload.path}") // import 시에 springFramwork으로 시작하는 value
    private String uploadPath;
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
//        PageResopneseDTO<BoardDTO> resopneseDTO = boardService.list(pageRequestDTO);

//        PageResopneseDTO<BoardListReplyCountDTO> resopneseDTO = boardService.listWithReplyCount(pageRequestDTO);

        PageResopneseDTO<BoardListAllDTO> resopneseDTO = boardService.listWithAll(pageRequestDTO);

        log.info("resopneseDTO : " + resopneseDTO);

        model.addAttribute("resopneseDTO", resopneseDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/register")
    public void registerGET() {

    }

    @PostMapping("/register")
    public String registerPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("board POST register.......");

        log.info("첨부파일 : " + boardDTO.getFileName());

        if (bindingResult.hasErrors()) {
            log.info("has error......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/board/register";
        }

        log.info(boardDTO);

        long bno = boardService.register(boardDTO);
        log.info(bno);
        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long bno, PageRequestDTO pageRequestDTO, Model model) {
        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO
            , @Valid BoardDTO boardDTO
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {
        log.info("board modify post........" + boardDTO);

        if (bindingResult.hasErrors()) {
            log.info("has error.....");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/modify?" + link;
        }

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        long bno = boardDTO.getBno();
        log.info("remove post......" + bno);

        boardService.remove(bno);

        // 게시물이 데이터베이스상에서 삭제되었다면 첨부파일 삭제
        log.info(boardDTO.getFileName());
        List<String> fileNames = boardDTO.getFileName();
        if (fileNames != null && fileNames.size() > 0) {
            removeFiles(fileNames);
        }
        redirectAttributes.addFlashAttribute("result", "remove");

        return "redirect:/board/list";
    }

    public void removeFiles(List<String> files) {
        for (String fileName : files) {
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

            String resouceName = resource.getFilename();

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());

                resource.getFile().delete();

                // 썸네일이 존재한다면
                if (contentType.startsWith("image")) {
                    File thumnailFile = new File(uploadPath + File.separator + "s_" + fileName);

                    thumnailFile.delete();
                }
            } catch (Exception e) {
                log.error(e.getMessage());

            }
        } // end for
    }
}
