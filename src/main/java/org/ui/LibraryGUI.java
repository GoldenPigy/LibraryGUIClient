package org.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.connector.*;

public class LibraryGUI extends JFrame {
    private BookDBConnector connector;
    private MemberDBConnector memberConnector;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private final int screenWidth = 1200;
    private final int screenHeight = 800;

    public LibraryGUI(BookDBConnector connector, MemberDBConnector memberConnector) {
        this.connector = connector;
        this.memberConnector = memberConnector;
        initialize();
    }

    private void initialize() {
        setTitle("도서관 관리 시스템");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 메인 메뉴 패널
        JPanel mainMenuPanel = new JPanel();
        JButton loginButton = new JButton("로그인");
        JButton registerButton = new JButton("회원가입");
        JButton searchButton = new JButton("도서 검색");
        JButton borrowButton = new JButton("도서 대출");
        JButton returnButton = new JButton("도서 반납");
        JButton checkoutButton = new JButton("대출한 도서 조회");
        JButton requestButton = new JButton("도서 요청");
        JButton bestButton = new JButton("인기 도서");

        mainMenuPanel.add(loginButton);
        mainMenuPanel.add(registerButton);
        mainMenuPanel.add(searchButton);
        mainMenuPanel.add(borrowButton);
        mainMenuPanel.add(returnButton);
        mainMenuPanel.add(checkoutButton);
        mainMenuPanel.add(requestButton);
        mainMenuPanel.add(bestButton);

        // 로그인 패널
        JPanel loginPanel = createLoginPanel();

        // 회원가입 패널
        JPanel registerPanel = createRegisterPanel();

        // 도서 검색 패널
        JPanel searchPanel = createSearchPanel();

        // 도서 대출 패널
        JPanel borrowPanel = createBorrowPanel();

        // 도서 반납 패널
        JPanel returnPanel = createReturnPanel();

        //빌린 책 조회 패널
        JPanel checkoutPanel = createCheckoutPanel();

        //도서 요청 패널
        JPanel requestPanel = createRequestPanel();

        JPanel bestPanel = createBestBookListPanel();

        // 카드 패널에 각 패널 추가
        cardPanel.add(mainMenuPanel, "Main Menu");
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(registerPanel, "Register");
        cardPanel.add(searchPanel, "Search");
        cardPanel.add(borrowPanel, "Borrow");
        cardPanel.add(returnPanel, "Return");
        cardPanel.add(checkoutPanel, "CheckOut");
        cardPanel.add(requestPanel, "Request");
        cardPanel.add(bestPanel, "Best");

        // 버튼 이벤트 리스너
        //로그인 필요 없는 기능
        loginButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));
        searchButton.addActionListener(e -> cardLayout.show(cardPanel, "Search"));
        bestButton.addActionListener(e -> cardLayout.show(cardPanel, "Best"));


        //로그인이 필요한 기능
        addLoginButton(borrowButton, "Borrow");
        addLoginButton(returnButton, "Return");
        addLoginButton(checkoutButton, "CheckOut");
        addLoginButton(requestButton, "Request");

        add(cardPanel);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout());
        loginPanel.setBorder(BorderFactory
                .createEmptyBorder(screenWidth / 4, screenWidth / 4, screenWidth / 4, screenWidth / 4));

        // 로그인 타이틀
        JLabel titleLabel = new JLabel("Sign In", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginPanel.add(titleLabel, BorderLayout.NORTH);

        // ID와 Password 입력 필드
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        fieldsPanel.add(idLabel);
        fieldsPanel.add(idField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        loginPanel.add(fieldsPanel, BorderLayout.CENTER);

        // 로그인 및 취소 버튼
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("로그인");
        JButton cancelButton = new JButton("취소");
        buttonsPanel.add(loginButton);
        buttonsPanel.add(cancelButton);
        loginPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // 이벤트 리스너 추가 (실제 로직은 connector를 통해 구현)
        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String pw = new String(passwordField.getPassword());
            String loginResult = memberConnector.loginMemberRequest(id, pw);
            JOptionPane.showMessageDialog(this, loginResult);
        });

        cancelButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Main Menu"); // 메인 메뉴로 돌아가기
        });

        return loginPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BorderLayout());
        registerPanel.setBorder(BorderFactory
                .createEmptyBorder(screenWidth / 4, screenWidth / 4, screenWidth / 4, screenWidth / 4));

        // 로그인 타이틀
        JLabel titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        registerPanel.add(titleLabel, BorderLayout.NORTH);

        // ID와 Password 입력 필드
        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        fieldsPanel.add(idLabel);
        fieldsPanel.add(idField);
        fieldsPanel.add(nameLabel);
        fieldsPanel.add(nameField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        registerPanel.add(fieldsPanel, BorderLayout.CENTER);

        // 회원가입 및 취소 버튼
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("회원가입");
        JButton cancelButton = new JButton("취소");
        buttonsPanel.add(registerButton);
        buttonsPanel.add(cancelButton);
        registerPanel.add(buttonsPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String pw = new String(passwordField.getPassword());
            String registerResult = memberConnector.joinMemeberRequest(id, pw, name);
            JOptionPane.showMessageDialog(this, registerResult);
        });

        cancelButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Main Menu"); // 메인 메뉴로 돌아가기
        });

        return registerPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel nameSearchLabel = new JLabel("도서명: ");
        JTextField nameSearchField = new JTextField(15);
        JLabel writerSearchLabel = new JLabel("저자: ");
        JTextField writerSearchField = new JTextField(15);
        JLabel publisherSearchLabel = new JLabel("출판사: ");
        JTextField publisherSearchField = new JTextField(15);
        JButton searchButton = new JButton("검색");
        JButton cancelButton = new JButton("취소");

        Object[][] data = connector.searchBookRequest("", "", "");
        JTable table = setTable(data);

        JScrollPane scrollPane = new JScrollPane(table);

        // 검색 패널 상단에 라벨, 검색 필드, 검색 버튼, 취소 버튼 추가
        JPanel topPanel = new JPanel();
        topPanel.add(nameSearchLabel);
        topPanel.add(nameSearchField);
        topPanel.add(writerSearchLabel);
        topPanel.add(writerSearchField);
        topPanel.add(publisherSearchLabel);
        topPanel.add(publisherSearchField);
        topPanel.add(searchButton);
        topPanel.add(cancelButton);
        searchPanel.add(topPanel, BorderLayout.NORTH);

        // 테이블에 스크롤 패널 추가
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        // 검색 버튼 이벤트 리스너
        searchButton.addActionListener(e -> {
            String book = nameSearchField.getText().trim();
            String writer = writerSearchField.getText().trim();
            String publisher = publisherSearchField.getText().trim();

            Object[][] data1 = connector.searchBookRequest(book, writer, publisher);
            setTable(data1, table);
        });

        cancelButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Main Menu"); // 메인 메뉴로 돌아가기
        });

        return searchPanel;
    }


    private JPanel createBorrowPanel() {
        JPanel borrowPanel = new JPanel(new BorderLayout());
        JLabel borrowLabel = new JLabel("대출할 도서 번호:");
        JTextField borrowField = new JTextField(20);
        JButton borrowButton = new JButton("대출");
        JButton cancelButton = new JButton("취소");

        // 검색 패널 상단에 라벨, 검색 필드, 검색 버튼, 취소 버튼 추가
        JPanel topPanel = new JPanel();
        topPanel.add(borrowLabel);
        topPanel.add(borrowField);
        topPanel.add(borrowButton);
        topPanel.add(cancelButton);
        borrowPanel.add(topPanel, BorderLayout.CENTER);

        borrowButton.addActionListener(e -> {
            try {
                int bookNum = Integer.parseInt(borrowField.getText().trim());
                String result = connector.borrowBookRequest(bookNum);
                JOptionPane.showMessageDialog(borrowPanel, result);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(borrowPanel, "input book num");
                throw new RuntimeException(ex);
            }

        });

        cancelButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Main Menu"); // 메인 메뉴로 돌아가기
        });

        return borrowPanel;
    }

    private JPanel createReturnPanel() {
        JPanel returnPanel = new JPanel(new BorderLayout());
        JButton cancelButton;
        JLabel returnLabel = new JLabel("반납할 도서 번호:");
        JTextField returnField = new JTextField(20);
        JButton returnButton = new JButton("반납");
        cancelButton = new JButton("취소");

        // 검색 패널 상단에 라벨, 검색 필드, 검색 버튼, 취소 버튼 추가
        JPanel topPanel = new JPanel();
        topPanel.add(returnLabel);
        topPanel.add(returnField);
        topPanel.add(returnButton);
        topPanel.add(cancelButton);
        returnPanel.add(topPanel, BorderLayout.CENTER);

        returnButton.addActionListener(e -> {
            try {
                int bookNum = Integer.parseInt(returnField.getText().trim());
                String result = connector.returnBookRequest(bookNum);
                JOptionPane.showMessageDialog(returnPanel, result);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(returnPanel, "input book num");
                throw new RuntimeException(ex);
            }

        });

        cancelButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Main Menu"); // 메인 메뉴로 돌아가기
        });
        return returnPanel;
    }

    private JPanel createCheckoutPanel() {
        JPanel checkoutPanel = new JPanel(new BorderLayout());
        JButton cancelButton = new JButton("취소");

        /*NonEditableModel model  = new NonEditableModel(new Object[]{"고유번호", "도서명", "저자", "출판사"}, 0);
        Object[][] data = connector.borrowBookListRequest();
        for (Object[] row : data) { model.addRow(row);}*/

        JTable table = setTable(connector.borrowBookListRequest());

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel topPanel = new JPanel();
        topPanel.add(cancelButton);
        checkoutPanel.add(topPanel, BorderLayout.NORTH);

        // 테이블에 스크롤 패널 추가
        checkoutPanel.add(scrollPane, BorderLayout.CENTER);

        // 검색 버튼 이벤트 리스너
        cancelButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Main Menu"); // 메인 메뉴로 돌아가기
        });

        return checkoutPanel;
    }

    private JPanel createRequestPanel() {
        JPanel requestPanel = new JPanel();
        requestPanel.setLayout(new BorderLayout());
        requestPanel.setBorder(BorderFactory
                .createEmptyBorder(screenWidth / 4, screenWidth / 4, screenWidth / 4, screenWidth / 4));

        // 로그인 타이틀
        JLabel titleLabel = new JLabel("Book Request", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        requestPanel.add(titleLabel, BorderLayout.NORTH);

        // ID와 Password 입력 필드
        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel bookLabel = new JLabel("Book Name:");
        JTextField bookField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JLabel explainLabel = new JLabel("Explain:");
        JTextField explainField = new JTextField();
        explainField.setSize(300,50);
        fieldsPanel.add(bookLabel);
        fieldsPanel.add(bookField);
        fieldsPanel.add(authorLabel);
        fieldsPanel.add(authorField);
        fieldsPanel.add(explainLabel);
        fieldsPanel.add(explainField);
        requestPanel.add(fieldsPanel, BorderLayout.CENTER);

        // 회원가입 및 취소 버튼
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton requestButton = new JButton("요청");
        JButton cancelButton = new JButton("취소");
        buttonsPanel.add(requestButton);
        buttonsPanel.add(cancelButton);
        requestPanel.add(buttonsPanel, BorderLayout.SOUTH);

        requestButton.addActionListener(e -> {
            String book = bookField.getText();
            String author = authorField.getText();
            String explain = explainField.getText();

            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedTime = today.format(formatter);

            String requestResult = connector.requestBookRequest(formattedTime, book, author, explain);
            JOptionPane.showMessageDialog(this, requestResult);
        });

        cancelButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Main Menu"); // 메인 메뉴로 돌아가기
        });
        return requestPanel;
    }

    private JPanel createBestBookListPanel() {
        JPanel bestPanel = new JPanel(new BorderLayout());
        JButton cancelButton = new JButton("취소");

        JTable table = setBestTable(connector.getBestBookRequest());

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel topPanel = new JPanel();
        topPanel.add(cancelButton);
        bestPanel.add(topPanel, BorderLayout.NORTH);

        // 테이블에 스크롤 패널 추가
        bestPanel.add(scrollPane, BorderLayout.CENTER);

        // 검색 버튼 이벤트 리스너
        cancelButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Main Menu"); // 메인 메뉴로 돌아가기
        });

        return bestPanel;
    }

    //로그인 유저만 사용할 수 있는 버튼
    private void addLoginButton(JButton button, String panelName) {
        button.addActionListener(e -> {
            if (BookDBConnector.isLoggedIn()) {
                cardLayout.show(cardPanel, panelName);
            } else {
                JOptionPane.showMessageDialog(this, "Login Please");
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
    }

    static class NonEditableModel extends DefaultTableModel {
        public NonEditableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }
        // 모든 셀을 변경 불가능하게 설정
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // 셀을 편집할 수 없도록 false 반환
        }
    }

    public JTable setTable(Object[][] data){
        if (data == null) {
            // null인 경우 예외 처리
            return null;
        } else {
            JTable table = new JTable();
            NonEditableModel model  = new NonEditableModel(new Object[]{"고유번호", "도서명", "저자", "출판사"}, 0);
            for (Object[] row : data) { model.addRow(row);}
            table.setModel(model);
            return table;
        }
    }

    public void setTable(Object[][] data, JTable table) {
        if(!(data == null)) {
            NonEditableModel model = new NonEditableModel(new Object[]{"고유번호", "도서명", "저자", "출판사"}, 0);
            for (Object[] row : data) { model.addRow(row); }
            table.setModel(model);
        }
    }

    public JTable setBestTable(Object[][] data){
        if (data == null) {
            // null인 경우 예외 처리
            return null;
        } else {
            JTable table = new JTable();
            NonEditableModel model  = new NonEditableModel(new Object[]{"순위", "고유번호", "도서명", "저자", "출판사"}, 0);
            for (Object[] row : data) { model.addRow(row);}
            table.setModel(model);
            return table;
        }
    }

}


