package org.example;

import org.ui.LibraryGUI;
import org.connector.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        BookDBConnector connector = new BookDBConnector();
        MemberDBConnector memberConnector = new MemberDBConnector();
        SwingUtilities.invokeLater(() -> new LibraryGUI(connector, memberConnector));

        /*BookDBConnector bookDBConnector = new BookDBConnector();
        MemberDBConnector memberDBConnector = new MemberDBConnector();
        //System.out.println(memberDBConnector.joinMemeberRequest("1","2","3")); //회원가입
        System.out.println(memberDBConnector.loginMemberRequest("1","2")); //로그인 (반드시 있어야 기능사용가능)
        bookDBConnector.borrowBookRequest(2);
        //String temp = bookDBConnector.requestBookRequest(20231231,"qwer12","123345","2223"); //책 요청
        Object[][] temp = bookDBConnector.getBestBookRequest(); //빌린리스트 요청
        System.out.println(temp[0][0]);*/
    }
}