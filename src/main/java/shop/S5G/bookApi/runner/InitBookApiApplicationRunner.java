package shop.S5G.bookApi.runner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import shop.S5G.bookApi.entity.Author;
import shop.S5G.bookApi.entity.AuthorType;
import shop.S5G.bookApi.entity.Book;
import shop.S5G.bookApi.entity.BookAuthor;
import shop.S5G.bookApi.entity.BookStatus;
import shop.S5G.bookApi.entity.Publisher;
import shop.S5G.bookApi.repository.*;

/**
 * 도서와 관련된 기초 데이터를 외부 API(알라딘)에서 받아오는 클래스
 *
 * @author Gyubin-Han
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class InitBookApiApplicationRunner implements ApplicationRunner {

    // 외부 API의 요청 주소 (알라딘 기준으로 구현)
    //     실행하기 전에 실행 매개변수에 값 설정 필수 (-Dshop.55g.api-key=<URL 값>)
    @Value("${shop.55g.api-key}")
    private String API_URL;

    private final ApplicationContext applicationContext;

    private final AuthorRepository authorRepository;
    private final AuthorTypeRepository authorTypeRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final BookStatusRepository bookStatusRepository;
    private final PublisherRepository publisherRepository;

    private RestTemplate restTemplate;

    /**
     * 출판사 데이터를 파싱하여 반환
     *
     * 출판사 명칭을 파싱한 후, 조회 수행
     * 데이터베이스 내 출판사 데이터가 존재한다면 해당 데이터의 Publisher 객체를 반환하고,
     * 데이터가 존재하지 않는다면 데이터베이스에 새로 저장하여 반환
     *
     * @param item 한 개의 도서 데이터를 담고 있는 JSON 객체
     * @return 데이터베이스 내 데이터가 있는 Publisher 객체
     */
    private Publisher getPublisher(JSONObject item) {
        Publisher publisher;
        String publisherName = (String) item.get("publisher");
        Optional<Publisher> publisherOptional = publisherRepository.findByPublisherName(
            publisherName);

        if (publisherOptional.isEmpty()) {
            publisher = publisherRepository.save(new Publisher(publisherName, true));
        } else {
            publisher = publisherOptional.get();
        }

        return publisher;
    }

    /**
     * JSON에서 주요 도서 정보를 파싱하여 데이터베이스에 저장한 후 반환
     *
     * @param item       한 개의 도서 데이터를 담고 있는 JSON 객체
     * @param publisher  출판사 정보를 담은 Publisher 객체
     * @param bookStatus 도서 상태 정보를 담은 BookStatus 객체
     * @return 데이터베이스에 저장한 도서 정보를 담은 Book 객체
     */
    private Book getBook(JSONObject item, Publisher publisher, BookStatus bookStatus) {
        LocalDateTime pubDate = LocalDate.parse((String) item.get("pubDate"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();

        Book book = bookRepository.save(Book.builder()
            .title((String) item.get("title"))
            .publisher(publisher)
            .description((String) item.get("description"))
            .publishedDate(pubDate)
            .isbn((String) item.get("isbn"))
            .price((Long) item.get("priceStandard"))
            .discountRate(new BigDecimal(0))
            .isPacked(false)
            .stock(100)
            .views(0)
            .bookStatus(bookStatus)
            .createdAt(LocalDateTime.now())
            .build()
        );

        return book;
    }

    /**
     * 주어진 문자열에서 이름 부분을 파싱하여 반환
     *
     * @param authorName 이름이 포함된 파싱할 문자열
     * @return 파싱된 이름 문자열
     */
    private String parseAuthorName(String authorName) {
        return authorName.split(" \\(")[0].trim();
    }

    /**
     * 저자 이름을 기준으로 저자 타입과 작가, 책을 조인한 데이터를 반환
     *
     * @param authorNameQueue 해당 타입에 해당하는 작가의 이름이 담긴 Queue 객체
     * @param book            해당 도서 객체
     * @param authorType      해당 저자 타입 객체
     */
    private void insertBookAuthor(Queue<String> authorNameQueue, Book book, AuthorType authorType) {
        while (!authorNameQueue.isEmpty()) {
            String authorName = authorNameQueue.poll();
            Optional<Author> authorOptional = authorRepository.findByName(authorName);

            Author author = null;
            if (authorOptional.isEmpty()) {
                author = authorRepository.save(
                    Author.builder().name(authorName).active(true).build());
            } else {
                author = authorOptional.get();
            }

            bookAuthorRepository.save(
                BookAuthor.builder().book(book).author(author).authorType(authorType)
                    .build());
        }
    }

    /**
     * JSON에서 작가 정보를 파싱하여 데이터베이스에 반영
     *
     * @param item 한 개의 도서 데이터를 담고 있는 JSON 객체
     * @param book 해당 도서 객체
     */
    private void parseAuthor(JSONObject item, Book book) {
        String[] authorSplit = ((String) item.get("author")).split(", ");
        Queue<String> authorNameQueue = new LinkedList<>();

        AuthorType authorType;
        for (String authorString : authorSplit) {
            authorNameQueue.add(parseAuthorName(authorString));

            if (authorString.contains("(지은이)") || authorString.contains("(글)")) {
                insertBookAuthor(authorNameQueue, book,
                    authorTypeRepository.findByTypeName("AUTHOR").get());
            } else if (authorString.contains("(엮은이)")) {
                insertBookAuthor(authorNameQueue, book,
                    authorTypeRepository.findByTypeName("COAUTHOR").get());
            } else if (authorString.contains("(옮긴이)")) {
                insertBookAuthor(authorNameQueue, book,
                    authorTypeRepository.findByTypeName("TRANSLATOR").get());
            } else if (authorString.contains("(그림)") || authorString.contains("(삽화)")) {
                insertBookAuthor(authorNameQueue, book,
                    authorTypeRepository.findByTypeName("ILLUSTRATOR").get());
            } else if (authorString.contains(" (")) {
                authorNameQueue.clear();
            }
        }
    }

    /**
     * 정적 데이터를 데이터베이스에 반영
     */
    public void insertInitStaticData() {
        // 저자 타입의 데이터를 사전 정의
        authorTypeRepository.save(AuthorType.builder().typeName("AUTHOR").active(true).build());
        authorTypeRepository.save(AuthorType.builder().typeName("COAUTHOR").active(true).build());
        authorTypeRepository.save(AuthorType.builder().typeName("TRANSLATOR").active(true).build());
        authorTypeRepository.save(
            AuthorType.builder().typeName("ILLUSTRATOR").active(true).build());
        authorTypeRepository.save(AuthorType.builder().typeName("SUPERVISED").active(true).build());

        // 도서 상태의 데이터를 사전 정의
        bookStatusRepository.save(BookStatus.builder().typeName("ONSALE").build());
        bookStatusRepository.save(BookStatus.builder().typeName("OUTOFPOINT").build());
        bookStatusRepository.save(BookStatus.builder().typeName("OUTOFSTOCK").build());
        bookStatusRepository.save(BookStatus.builder().typeName("DROP").build());
    }

    /**
     * 외부 API로부터 데이터를 받아와 데이터베이스에 저장
     *
     * @throws ParseException
     */
    public void insertInitApiData() throws ParseException {
        JSONArray array = (JSONArray) ((JSONObject) new JSONParser().parse(
            restTemplate.getForObject(API_URL, String.class))).get("item");

        for (Object object : array) {
            JSONObject item = (JSONObject) object;
            Publisher publisher = getPublisher(item);
            BookStatus bookStatus = bookStatusRepository.findByTypeName("ONSALE").get();
            Book book = getBook(item, publisher, bookStatus);
            parseAuthor(item, book);
        }
    }

    /**
     * 초기 데이터 설정 실행
     *
     * @param args
     * @throws ParseException
     */
    @Override
    public void run(ApplicationArguments args) throws ParseException {
        restTemplate = new RestTemplate();

        // 고정 데이터(저자 타입, 도서 상태 등)를 DB에 삽입
        insertInitStaticData();
        // 외부 API에서 데이터 불러와 삽입
        insertInitApiData();

        // Application 종료
        ((ConfigurableApplicationContext) applicationContext).close();
    }
}
