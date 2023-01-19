package com.demo.service;

import com.demo.dto.response.Response;

public interface EmailService {
    Response sendSimpleMessage(String to)throws Exception;
    /**
     * OOP의 다형성을 충족하기 위해서 Service - interface 분리했다고 함
     * 기술의 변경이 없다면 굳이 꼭 필요한 부분은 아니라고 생각하지만 그래도 사용했슴다.
     * 
     * 기술이 변경될 경우 구현체만 갈아끼면 되기 때문에 다른 코드에 영향이 없다는 장점이 있음
     * feat. 김영한 강사님
     */
}
