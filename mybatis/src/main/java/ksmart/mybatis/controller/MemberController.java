package ksmart.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ksmart.mybatis.dto.LoginHistory;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
		
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/loginHistory")
	@SuppressWarnings("unchecked")
	public String getLoginHistory(Model model, @RequestParam(name="currentPage", required=false, defaultValue="1") int currentPage) {
		
		Map<String, Object> resultMap = memberService.getLoginHistory(currentPage);
		
		List<LoginHistory> loginHistory = (List<LoginHistory>) resultMap.get("loginHistory");
		int lastPage = (int) resultMap.get("lastPage");
		int startPageNum = (int) resultMap.get("startPageNum");
		int endPageNum = (int) resultMap.get("endPageNum");
		
		model.addAttribute("title", "로그인 이력조회");
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("loginHistory", loginHistory);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		
		
		return "login/loginHistory";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		//세션 초기화
		session.invalidate();
		return "redirect:/member/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(name="memberId") String memberId
					   ,@RequestParam(name="memberPw") String memberPw
					   ,HttpSession session
					   ,RedirectAttributes reAttr) {
		Map<String, Object> resultMap = memberService.checkMemberInfo(memberId, memberPw);
		boolean isChecked = (boolean) resultMap.get("isChecked");
		if(isChecked) {
			// 로그인 처리
			int memberLevel = (int) resultMap.get("memberLevel");
			String memberName = (String) resultMap.get("memberName");
			
			session.setAttribute("SID", memberId);
			session.setAttribute("SNAME", memberName);
			session.setAttribute("SLEVEL", memberLevel);
			
			// 로그인 처리 후에는 메인화면으로 전환
			return "redirect:/";
		}
		reAttr.addFlashAttribute("msg", "일치하는 회원의 정보가 없습니다.");
		return "redirect:/member/login";
	}
	
	@GetMapping("/login")
	public String login(Model model, @RequestParam(name="msg", required = false) String msg) {
		
		model.addAttribute("title", "로그인");
		if(msg != null) model.addAttribute("msg", msg);
		
		return "login/login";
	}
	
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam(value="memberId") String memberId
							  ,@RequestParam(value="memberPw") String memberPw
							  ,RedirectAttributes reAttr) {
		Map<String, Object> resultMap = memberService.checkMemberInfo(memberId, memberPw);
		boolean isChecked = (boolean) resultMap.get("isChecked");
		if(isChecked) {
			// 삭제 처리
			int memberLevel = (int) resultMap.get("memberLevel");
			memberService.removeMember(memberId, memberLevel);
			
			// 삭제 처리 후에는 회원목록 페이지로 전환
			return "redirect:/member/memberList";
		}
		
		reAttr.addAttribute("memberId", memberId);
		reAttr.addAttribute("msg", resultMap.get("msg"));
		
		// 일치하지 않은 경우 기존 페이지(회원탈퇴 화면)로 전환
		return "redirect:/member/removeMember";
	}
	
	/**
	 * 회원탈퇴 폼
	 * @param memberId
	 * 
	 * @return 특정회원 탈퇴 화면
	 */
	@GetMapping("/removeMember")
	public String removeMember(@RequestParam(value="memberId") String memberId
							  ,@RequestParam(value="msg", required = false) String msg
							  ,Model model) {
		
		model.addAttribute("title", "회원탈퇴");
		model.addAttribute("memberId", memberId);
		
		if(msg != null) model.addAttribute("msg", msg);
		
		return "member/removeMember";
	}
	
	/**
	 * 회원 수정 처리
	 * @param member (커맨드 객체)
	 * @return redirect:/member/memberList => 회원리스트 리디렉션 요청
	 */
	@PostMapping("/modifyMember")
	public String modifyMember(Member member) {
		log.info("회원수정 Member: {}", member);
		
		memberService.modifyMember(member);
		
		return "redirect:/member/memberList";
	}
	
	/**
	 * 회원 수정 폼
	 * @param @RequestParam String memberId (회원아이디)
	 * @return 특정회원정보수정 페이지
	 */
	@GetMapping("/modifyMember")
	public String modifyMember(@RequestParam(name="memberId") String memberId
							  ,Model model) {
		
		log.info("memberId: {}", memberId);
		Member memberInfo = memberService.getMemberInfoById(memberId);
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원수정");
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("memberLevelList", memberLevelList);
		
		return "member/modifyMember";
	}
	
	/**
	 * 회원가입 처리
	 * @param Member member (컨트롤러에서 매개변수로 DTO 선언을 하면 커맨드객체)
	 * @return "redirect:/member/memberList" -> 처리후 새로운 주소 요청
	 */
	@PostMapping("/addMember")
	public String addMember(Member member) {
		
		log.info("회원가입 Member: {}", member);
		//System.out.println("회원가입시 입력한 데이터 Member: " + member);
		memberService.addMember(member);
		
		return "redirect:/member/memberList";
	}
	
	/**
	 * 아이디 중복체크 (ajax 요청 응답)
	 * @param memberId (클라이언트에서 입력한 아이디)
	 * @return @ResponseBody 응답시 body 영역에 응답한 데이터를 전달
	 */
	@PostMapping("/idCheck")
	@ResponseBody
	public boolean idCheck(@RequestParam(name="memberId") String memberId) {
		
		log.info("memberId : {}", memberId);
		//System.out.println("memberId : " + memberId);
		
		boolean isduplicate = memberService.idCheck(memberId);
		
		return isduplicate;
	}

	/**
	 * 회원가입폼
	 * @param model
	 * @return view => member/addMember 회원가입폼페이지
	 */
	@GetMapping("/addMember")
	public String addMember(Model model) {
		
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();

		model.addAttribute("title", "회원가입");
		model.addAttribute("memberLevelList", memberLevelList);
		
		return "member/addMember";
	}
	
	/**
	 * 회원목록 조회
	 * @param model
	 * @return view => member/memberList 회원목록페이지
	 */
	@GetMapping("/memberList")
	public String getMemberList(Model model
							   ,@RequestParam(name="searchKey", required = false) String searchKey
							   ,@RequestParam(name="searchValue", required = false, defaultValue = "") String searchValue) {
		
		List<Member> memberList = null;
		
		if(searchKey != null) {
			memberList = memberService.getMemberList(searchKey, searchValue);
		}else {			
			memberList = memberService.getMemberList();
		}
		
		log.info("회원목록 {}", memberList);
		log.info("searchKey {}", searchKey);
		log.info("searchValue {}", searchValue);
		//System.out.println(memberList);
		
		model.addAttribute("title", "회원목록");
		model.addAttribute("memberList", memberList);
		
		return "member/memberList";
	}
	
}
