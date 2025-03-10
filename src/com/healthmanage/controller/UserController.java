package com.healthmanage.controller;

import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.User;
import com.healthmanage.service.UserService;
import com.healthmanage.utils.SHA256;
import com.healthmanage.view.UserView;

public class UserController {
	private UserService userService;

	private UserView userView;

	public UserController() {
		this.userService = UserService.getInstance();
		this.userView = new UserView();
	}

	public void start() {
		int key = 0;
		while ((key = Integer.parseInt(userView.selectMenu())) != 0) {
			switch (key) {
			case 1:

				/*
				 * case 1: addBook(); break; case 2: removeBook(); break; case 3: searchBook();
				 * break; case 4: listBook(); break; case 5: listISBN(); break; case 6: save();
				 * break; case 7: load(); break;
				 */
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
		System.out.println("종료합니다...");
	}

	public void registerUser() {
		String userId;
		while (true) {
			// 🔹 View에서 아이디 입력 받기
			userId = userView.getInput("ID 입력: ");

			// 🔹 아이디 중복 검사
			if (userService.checkId(userId)) {
				break;
			}
			userView.showMessage("이미 존재하는 ID입니다. 다시 입력해주세요.");
		}

		// 나머지 회원 정보 입력
		String name = userView.getInput("이름 입력: ");
		String password = userView.getInput("비밀번호 입력: ");
		String hashedPw = SHA256.encrypt(password);

		// DTO 생성 및 회원가입 진행
		UserSignUpDTO userDTO = new UserSignUpDTO(userId, name, hashedPw);
		userService.addUser(userDTO);
		userView.showMessage("회원가입 완료!");
	}



	public void loginUser() {
        String userId = userView.getInput("ID 입력: ");
        String password = userView.getInput("비밀번호 입력: ");
        String hashedPw = SHA256.encrypt(password);
        boolean loginSuccess = userService.userLogin(userId, hashedPw);
        if (loginSuccess) {
            userView.showMessage("로그인 성공!");
        } else {
            userView.showMessage("로그인 실패. 아이디 또는 비밀번호를 확인하세요.");
        }
    }


	public void couponUser() {
		String couponNumber = userView.getInput("쿠폰번호 입력: ");

		userView.showMessage(userService.useCoupon(couponNumber));

	}


	public void addCoinUser() {
		String inputMoney = userView.getInput("충전금액 입력: ");
		userView.showMessage(userService.addCoin(inputMoney));
	}

}
