package ksmart.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.mapper.MemberMapper;

/**
 * @Service => 비즈니스 로직 구현
 * @Transactional => 트랙잭션 : 논리적인 작업의 한단위, 특징: A(원자성)C(일관성)I(고립성)D(영속성)  
 */
@Service
@Transactional
public class MemberService {
	// DI(의존성 주입)
	private final MemberMapper memberMapper;
	
	// 생성자메소드를 통한 DI
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	/**
	 * 회원 탈퇴 처리
	 * @param memberId(회원아이디), memberLevel(회원등급)
	 */
	public void removeMember(String memberId, int memberLevel) {
		switch (memberLevel) {
			case 2:
				memberMapper.removeOrderBySellerId(memberId);
				memberMapper.removeGoodsById(memberId);
				break;
			case 3:
				memberMapper.removeOrderByOrderId(memberId);
				break;
		}
		memberMapper.removeLoginHistoryById(memberId);
		memberMapper.removeMemberById(memberId);
	}
	
	/**
	 * 회원 정보 일치 확인
	 * @param memberId, memberPw
	 * @return Map<String,Object> 일치여부(boolean), 일치하지 않을 경우("msg") 값을 전달
	 */
	public Map<String, Object> checkMemberInfo(String memberId, String memberPw){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isChecked = false;
		Member memberInfo = memberMapper.getMemberInfoById(memberId);
		if(memberInfo != null) {
			String checkPw = memberInfo.getMemberPw();
			if(checkPw.equals(memberPw)) {
				isChecked = true;
				resultMap.put("memberLevel", memberInfo.getMemberLevel());
				resultMap.put("memberName", memberInfo.getMemberName());
			}
		}
		
		if(!isChecked) resultMap.put("msg", "일치하는 회원의 정보가 없습니다.");
		
		resultMap.put("isChecked", isChecked);
		
		return resultMap;
	}
	
	/**
	 * 회원 수정
	 * @param member (수정정보가 담긴 MemberDTO)
	 */
	public void modifyMember(Member member) {
		memberMapper.modifyMember(member);
	}
	
	/**
	 * 특정 회원 조회
	 * @param memberId (회원아이디)
	 * @return Member memberInfo (회원 DTO)
	 */
	public Member getMemberInfoById(String memberId) {
		Member memberInfo = memberMapper.getMemberInfoById(memberId);
		return memberInfo; 
	}
	
	/**
	 * 회원가입
	 * @param member (회원정보)
	 * @return
	 */
	public void addMember(Member member) {
		memberMapper.addMember(member);
	}
	
	/**
	 * 아이디 중복체크
	 * @param memberId (회원아이디)
	 * @return 중복: true, 중복x: false (boolean)
	 */
	public boolean idCheck(String memberId) {
		boolean isDuplicate = memberMapper.idCheck(memberId);
		return isDuplicate;
	}
	
	/**
	 * 회원 등급 조회
	 * @return 회원등급리스트 List<MemberLevel>
	 */
	public List<MemberLevel> getMemberLevelList(){
		
		List<MemberLevel> memberLevelList = memberMapper.getMemberLevelList();
		
		return memberLevelList;
	}

	/**
	 * 검색어에 따른 회원의 목록 조회
	 */
	public List<Member> getMemberList(String searchKey, String searchValue) {
		switch (searchKey) {
			case "memberId":
				searchKey = "m.m_id";
				break;
			case "memberName":
				searchKey = "m.m_name";
				break;
			case "memberLevel":
				searchKey = "l.level_name";
				break;
			case "memberEmail":
				searchKey = "m.m_email";
				break;
		}
		List<Member> memberList = memberMapper.getMemberListBySearch(searchKey, searchValue);
		
		return memberList;
	}
	
	/**
	 * 회원 목록 조회
	 * @return 회원목록 List<Member>
	 */
	public List<Member> getMemberList() {
		List<Member> memberList = memberMapper.getMemberList();
		/*
		if(memberList != null) {
			for(Member member : memberList) {
				int memberLevel = member.getMemberLevel();
				String memberLevelName = "test";
				switch (memberLevel) {
					case 1:
						memberLevelName = "관리자";
						break;
					case 2:
						memberLevelName = "판매자";
						break;
					case 3:
						memberLevelName = "구매자";
						break;
				}
				member.setMemberLevelName(memberLevelName);
			}
		}
		*/
		return memberList;
	}
}






