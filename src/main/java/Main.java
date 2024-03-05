import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

public class Main {
    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static final String DELETE_URL = "http://94.198.50.185:7081/api/users/{id}";

    public static void main(String[] args) {
        StringBuilder finalHashCode = new StringBuilder();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        //GET request
        ResponseEntity<User[]> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, httpEntity, User[].class);
        Arrays.stream(Objects.requireNonNull(responseEntity.getBody())).forEach(System.out::println);

        final String cookie = responseEntity.getHeaders().get("set-cookie").get(0);
        headers.add("Cookie", cookie);
        User user = new User((long) (3), "James", "Brown", (byte) 36);

        //POST request
        HttpEntity<User> httpEntity2 = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity2 = restTemplate.exchange(URL, HttpMethod.POST, httpEntity2, String.class);
        finalHashCode.append(responseEntity2.getBody());

        user.setName("Thomas");
        user.setLastName("Shelby");

        //PUT request
        HttpEntity<User> httpEntity3 = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity3 = restTemplate.exchange(URL, HttpMethod.PUT, httpEntity3, String.class);
        finalHashCode.append(responseEntity3.getBody());

        //DELETE request
        HttpEntity<User> httpEntity4 = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity4 = restTemplate.exchange(DELETE_URL, HttpMethod.DELETE, httpEntity4, String.class, 3);
        finalHashCode.append(responseEntity4.getBody());
        System.out.println("Result: " + finalHashCode);
        System.out.println("Size: " + finalHashCode.length());
    }
}