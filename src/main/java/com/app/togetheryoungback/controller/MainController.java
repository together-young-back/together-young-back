package com.app.togetheryoungback.controller;

import com.app.togetheryoungback.domain.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/main/*")
public class MainController {

    // 로그인 페이지로 이동
    @GetMapping("login")
    public void goToLoginForm(MemberVO memberVO){;}

    // 북마크 페이지로 이동
    @GetMapping("bookmark")
    public void goToBookmark(){;}

    // 메세지 목록으로  이동
    @GetMapping("message-list")
    public void goToMessageList(){;}

    // 마이 페이지로 이동
    @GetMapping("my-page")
    public void goToMyPage(){;}

    // 일반 게시판으로  이동
    @GetMapping("general-community")
    public void goToGeneralCommunity(){;}

    // 일반 게시글로 이동
    @GetMapping("general-post/*")
    public void goToGeneralPost(){;}

    // 모임 게시판으로  이동
    @GetMapping("meeting-community")
    public void goToMeetingCommunity(){;}

    // 모임 게시글로 이동
    @GetMapping("meeting-post/*")
    public void goToMeetingPost(){;}

    // 나의 일정으로  이동
    @GetMapping("my-schedule")
    public void goToMySchedule(){;}

    // 검색창으로  이동
    @GetMapping("search")
    public void goToSearch(){;}

    // 친구 목록으로 이동
    @GetMapping("friend-list")
    public void goToFriendList(){;}
}