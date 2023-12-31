package com.app.togetheryoungback.controller;

import com.app.togetheryoungback.domain.*;
import com.app.togetheryoungback.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@RequestMapping("/message/*")
public class MessageController {
    private final MessageService messageService;

    // 메시지 작성으로 이동
    @GetMapping("write")
    public void goToMessageWriteForm(MessageDTO messageDTO){;}
    // 작성 후 보낸 메시지함으로 이동
    @PostMapping("write")
    // RequestParam
    public RedirectView write(MessageDTO messageDTO, HttpSession session){
        // 폼 태그로 submit을 통해 DTO에 작성된 정보가 자동으로 채워진 채 넘어옴
        // session에서 내 id 값만 주입하고 messageService로 DTO 넘겨주기
        messageDTO.setMemberSentId(((MemberVO)session.getAttribute("member")).getId());
        messageService.write(messageDTO);
        return new RedirectView("sent");
    }

    // 받은 메시지 목록으로 이동
    @GetMapping("received")
    public String goToReceivedMessagesForm(Pagination pagination, Model model, HttpSession session){
        if (session.getAttribute("member") == null) {
            return "redirect:/member/login";
        }
        MemberVO memberVO = (MemberVO) session.getAttribute("member");
        Long memberId = memberVO.getId();
        pagination.setTotal(messageService.getCountOfMessageReceived(memberId));
        pagination.progress();
        model.addAttribute("pagination", pagination);
        model.addAttribute("messagesReceived", messageService.getMessagesReceived(pagination, memberId));
        return "/message/received";
    }

    // 받은 메시지 상세보기로 이동
    @GetMapping("received-detail")
    public String goToMessageReceivedDetail(Long messageId, Model model, HttpSession session){
        if (session.getAttribute("member") == null) {
            return "redirect:/member/login";
        }
        model.addAttribute("messageReceived", messageService.getMessageReceived(messageId));
        return "/message/received-detail";
    }

    //    받은 메시지 삭제
    @GetMapping("remove-received-detail")
    public RedirectView deleteReceivedMessage(Long messageId){
        messageService.deleteReceived(messageId);
        return new RedirectView("/message/received");
    }

    //    보낸 메시지 목록으로 이동
    @GetMapping("sent")
    public String goToSentMessagesForm(Pagination pagination, Model model, HttpSession session){
        if (session.getAttribute("member") == null) {
            return "redirect:/member/login";
        }
        MemberVO memberVO = (MemberVO) session.getAttribute("member");
        Long memberId = memberVO.getId();
        pagination.setTotal(messageService.getCountOfMessageSent(memberId));
        pagination.progress();
        model.addAttribute("pagination", pagination);
        model.addAttribute("messagesSent", messageService.getMessagesSent(pagination, memberId));
        return "/message/sent";
    }

    // 보낸 메시지 상세보기로 이동
    @GetMapping("sent-detail")
    public String goToMessageSentDetail(Long messageId, Model model, HttpSession session){
        if (session.getAttribute("member") == null) {
            return "redirect:/member/login";
        }
        model.addAttribute("messageSent", messageService.getMessageSent(messageId));
        return "/message/sent-detail";
    }

    //    보낸 메시지 삭제
    @GetMapping("remove-sent-detail")
    public RedirectView deleteSentMessage(Long messageId){
        messageService.deleteSent(messageId);
        return new RedirectView("/message/sent");
    }

}