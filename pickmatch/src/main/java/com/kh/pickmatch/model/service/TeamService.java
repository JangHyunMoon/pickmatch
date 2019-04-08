﻿package com.kh.pickmatch.model.service;

import java.util.List;
import java.util.Map;

import com.kh.pickmatch.model.vo.MoneyHistory;
import com.kh.pickmatch.model.vo.TeamBoard;
import com.kh.pickmatch.model.vo.TeamOperationAccount;

public interface TeamService {
	
	List<Map<String, String>> selectTMemberList(Map<String, String> map);
	String selectTeamOne(String memberId);
	TeamOperationAccount selectAccountOne(String teamName);
	List<Map<String, Object>> selectMoneyHistoryList(String teamName);
	int updateTeamAccount(TeamOperationAccount toAccount);
	int insertTeamAccount(TeamOperationAccount toAccount);
	int insertMHistory(MoneyHistory mHistory);
	
	
	
	
	List<TeamBoard> selectList(int cPage, int numPerPage);

	int selectCount();

	Object selectTeamBoard(int boardNo);

	Object selectAttachment(int boardNo);

}