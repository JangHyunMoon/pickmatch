package com.kh.pickmatch.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.pickmatch.common.PageBarFactory;
import com.kh.pickmatch.model.service.CommunityService;
import com.kh.pickmatch.model.vo.FreeBoard;
import com.kh.pickmatch.model.vo.FreeBoardAttachment;

@Controller
public class CommunityController {

	private Logger logger = LoggerFactory.getLogger(CommunityController.class);
	
	@Autowired
	CommunityService service;
	
	@RequestMapping("/community/freeboard.do")
	public ModelAndView freeboard(@RequestParam(value="cPage", required=false, defaultValue="1")int cPage) {
		int numPerPage = 10;
		ModelAndView mv = new ModelAndView();
		List<FreeBoard> list = service.selectFreeBoardList(cPage, numPerPage);
		int totalList = service.selectFreeBoardCount();
	
		mv.addObject("list", list);
		mv.addObject("totalList", totalList);
		mv.addObject("pageBar", PageBarFactory.getPageBar(totalList, cPage, numPerPage, "/pickmatch/community/freeboard.do"));
		mv.setViewName("community/co-freeboard");
		return mv;
	}
	
	@RequestMapping("/community/freeboardView.do")
	public ModelAndView freeboardView(@RequestParam(value="boardNo", defaultValue="1") int boardNo) {
		ModelAndView mv = new ModelAndView();
	    mv.addObject("freeboard", service.selectOneFreeBoard(boardNo));
	    mv.addObject("attachmentList", service.selectAttachment(boardNo));
	    mv.setViewName("community/co-freeboardView");
	    return mv;
	}
	
	@RequestMapping("/community/freeboardForm.do")
	public String freeboardForm() {
		return "community/co-freeboardForm";
	}
	
	@RequestMapping("/community/freeboardFormEnd.do")
	public ModelAndView insertFreeboard(FreeBoard fb, MultipartFile[] upFile, HttpServletRequest re) {
		ModelAndView mv = new ModelAndView();
		String msg = "";
		String loc = "";
		String saveDir = re.getSession().getServletContext().getRealPath("/resources/upload/community-freeboard");
		File dir = new File(saveDir);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		List<FreeBoardAttachment> list = new ArrayList<>();
		for(MultipartFile f : upFile)
		{
			if(!f.isEmpty())
			{
				String oriFileName = f.getOriginalFilename();
				String ext = oriFileName.substring(oriFileName.indexOf("."));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				int rndNum = (int)(Math.random()*1000);
				String renamedFile = sdf.format(new Date(System.currentTimeMillis())) + "_" + rndNum + ext;
				try
				{
					f.transferTo(new File(saveDir + "/" + renamedFile));
				}catch(IOException e)
				{
					e.printStackTrace();
				}
				FreeBoardAttachment a = new FreeBoardAttachment();
				a.setOriginalFileName(oriFileName);
				a.setRenamedFileName(renamedFile);
				list.add(a);
			}
		}
		int result = service.insertFreeBoard(fb,list);
		if(result>0)
		{
			msg = "게시글이 등록되었습니다.";
			loc = "/community/freeboard.do";
		}
		else
		{
			msg = "게시글 등록이 실패했습니다. 다시 등록해주세요.";
			loc = "/community/freeboard.do";
		}
		mv.setViewName("common/msg");
		return mv;
	}
	
	@RequestMapping("/community/freeboardDown.do")
	public void fileDown(String oName, String rName, HttpServletRequest request, HttpServletResponse response)
	{
		BufferedInputStream bis = null;
		ServletOutputStream sos = null;
		
		String saveDir = request.getSession().getServletContext().getRealPath("/resources/upload/community-freeboard");
		try
		{
			FileInputStream fis = new FileInputStream(new File(saveDir+"/"+rName));
			bis = new BufferedInputStream(fis);
			sos = response.getOutputStream();
			String resFilename = "";
			boolean isMSIE = request.getHeader("user-agent").indexOf("MSIE") != -1 || request.getHeader("user-agent").indexOf("Trident") != -1;
			if(isMSIE)
			{
				resFilename = URLEncoder.encode(oName, "UTF-8");
				resFilename = resFilename.replaceAll("\\+", "%20");
			}
			else
			{
				resFilename = new String(oName.getBytes("UTF-8"),"ISO-8859-1");
			}
			
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=\""+resFilename+"\"");
			
			int read = 0;
			while((read = bis.read()) != -1)
			{
				sos.write(read);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				bis.close();
				sos.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}