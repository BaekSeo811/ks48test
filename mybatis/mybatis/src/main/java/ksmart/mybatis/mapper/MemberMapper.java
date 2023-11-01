package ksmart.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;

@Mapper
public interface MemberMapper {
	// 판매자 현황조회
	public List<Member> getSellerList();
	
	// 검색조건에 따른 회원목록 조회
	public List<Member> getMemberListBySearch(String searchKey, String searchValue);
	
	// 특정회원 탈퇴
	public int removeMemberById(String memberId);
	
	// 특정회원의 로그인 이력삭제
	public int removeLoginHistoryById(String memberId);
	
	// 구매자가 구매한 이력삭제
	public int removeOrderByOrderId(String orderId);
	
	// 판매자가 등록한 상품을 주문한 이력삭제
	public int removeOrderBySellerId(String sellerId);
	
	// 판매자 등록한 상품삭제
	public int removeGoodsById(String memberId);
	
	// 특정회원 수정
	public int modifyMember(Member member);
	
	// 특정회원조회
	public Member getMemberInfoById(String memberId);
	
	// 회원가입
	public int addMember(Member member);
	
	// 회원아이디 중복체크
	public boolean idCheck(String memberId);
	
	// 회원 등급 조회
	public List<MemberLevel> getMemberLevelList();

	// 회원 목록 조회
	public List<Member> getMemberList();
	
	
}
