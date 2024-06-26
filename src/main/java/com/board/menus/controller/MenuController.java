package com.board.menus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.menus.domain.MenuVo;
import com.board.menus.mapper.MenuMapper;

@Controller
@RequestMapping("/Menus") //.jsp에서 /Meuns있으면 다 여기로 온다.
public class MenuController {
	
//------------------------------------list------------------------------------------------
	@Autowired
	private  MenuMapper  menuMapper;
	
	// 메뉴 목록 조회  /Menus/List
	@RequestMapping("/List")   
	public   String   list( Model model ) {
		// 조회한결과를 ArrayList 로 돌려준다
		List<MenuVo> menuList = menuMapper.getMenuList();
				
		// 조회 결과를 넘겨준다 ( Model )
		model.addAttribute( "menuList", menuList );
		System.out.println( "MenuController list() menuList:" + menuList );
		
		return "menus/list";
	}
//------------------------writeForm -> write -------------------------------------	
	
	// 메뉴 입력받는 화면  /Menus/WriteForm
	//@RequestMapping("/Menus/WriteForm")
	@RequestMapping("/WriteForm")   // /Menus/WriteForm
	public   String   writeForm() {
		return "menus/write";  // /WEB-INF/views/ + menus/write + .jsp`
	}
	

		
	/* 메뉴 저장
	 /Menus/Write?menu_id=MENU02&menu_name=JSP&menu_seq=2
	@RequestMapping("/Menus/Write")
	@RequestMapping("/Write")  
	public   String   write(  String menu_id,
	                          String menu_name,
	                          int menu_seq ) { :인식안됨(error)
         menu_id 를 찾을 수 없으므로 Vo 로 작업해야한다} */
	@RequestMapping("/Write")
	public   String   write( MenuVo  menuVo) {   
		// 넘어온 데이터를 db 에 저장하고		
		menuMapper.insertMenu( menuVo );
//	    menuMapper.insertMenu(menu_id, menu_name, menu_seq); // error
//		List<MenuVo> menuList = menuMapper.getMenuList();
//		model.addAttribute("menuList", menuList);
		
		return "redirect:/Menus/List";    // menus/list.jsp  
	}

//-----------------------writeForm2 -> write2------------------------------------------
	
//   /Menus/WriteForm2
	@RequestMapping("/WriteForm2")
	public String writeForm2() {
		
		return "menus/write2";
	}
	
	@RequestMapping("/Write2")
	public String write2(MenuVo menuVo) {
		//저장
		menuMapper.insertMenuByName(menuVo);
		//조회로 이동
		return "redirect:/Menus/List";
	}
//-------------------------------------------------------------------------------------
	
/*	<메뉴삭제 Ver.1>
    /Menus/Delete?menu_id=MENU03
	@RequestMapping("/Delete")
	public String delete( MenuVo menuVo) {
		
		// MENU03 을 삭제
		menuMapper.deleteMenu( menuVo );
		
//		List<MenuVo> menuList = menuMapper.getMenuList();
//		model.addAttribute("menuList", menuList);
		
		//이동할 파일 redirect:
		return "redirect:/Menus/List";
	} */
	@RequestMapping("/Delete")
	@ResponseBody
	public String delete( MenuVo menuVo ) { 
		
		menuMapper.deleteMenu(menuVo);
		
		String html = "<script>";
		html       += "alert('삭제되었습니다');";
		html       += "location.href='/Menus/List';";
		html       += "</script>";
		return html;
		
	//	return "<script>alert('삭제되었습니다')</script>";
	}
//------------------------------------------------------------------
	//menu수정
	//  /Menus/UpdateForm?menu_id=${menu.menu_id}
	@RequestMapping("/UpdateForm")
	public String updateForm(MenuVo menuVo, Model model ){
		System.out.println("menuVo" + menuVo);
		String menu_id = menuVo.getMenu_id();
		
		//수정할 데이터를 menu_id 조회
		MenuVo menu = menuMapper.getMenu( menu_id );
		
		//조회한 내용을 모델에 담는다
		model.addAttribute("menu", menu);

		return "menus/update";
	}
	
	//Menus/Update?menu_id=MENU01&menu_name=자바&menu_seq=1
	@RequestMapping("/Update")
	public String update(MenuVo menuVo) {
		
		//수정 updateMenu->menuMapper.xml에서 정의한다
		menuMapper.updateMenu(menuVo);
		
		return "redirect:/Menus/List";
	}
	
}






