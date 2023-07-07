package com.example.springbootblogapplication.config;

import com.example.springbootblogapplication.models.Account;
import com.example.springbootblogapplication.models.Authority;
import com.example.springbootblogapplication.models.Post;
import com.example.springbootblogapplication.repositories.AuthorityRepository;
import com.example.springbootblogapplication.services.AccountService;
import com.example.springbootblogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if (posts.size() == 0) {

            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);

            Account account1 = new Account();
            Account account2 = new Account();

            account1.setFirstName("Average_Nihilist");
            account1.setLastName("Nietzsche");
            account1.setEmail("NietzscheFan123@gmail.com");
            account1.setPassword("password");
            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);


            account2.setFirstName("Carlos");
            account2.setLastName("Faulkner");
            account2.setEmail("drcjfaulkner@gmail.com");
            account2.setPassword("2004baby");

            Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            account2.setAuthorities(authorities2);

            accountService.save(account1);
            accountService.save(account2);

            Post post1 = new Post();
            post1.setTitle("When Nietzsche Wept");
            post1.setBody("Friedrich Nietzsche apparently witnessed the beating of a horse on the streets of Turin. He threw his arms around the horse's neck, sobbing, and then lost consciousness. He then had a mental breakdown from which he never recovered, and never wrote again");
            post1.setAccount(account1);

            Post post2 = new Post();
            post2.setTitle("Kafka, the Lost Doll and the Little Girl");
            post2.setBody("Franz Kafka, the story goes, encountered a little girl in the park where he went walking daily. She was crying. She had lost her doll and was desolate.\n" +
                    "\n" +
                    "Kafka offered to help her look for the doll and arranged to meet her the next day at the same spot. Unable to find the doll he composed a letter from the doll and read it to her when they met.\n" +
                    "\n" +
                    "“Please do not mourn me, I have gone on a trip to see the world. I will write you of my adventures.” This was the beginning of many letters. When he and the little girl met he read her from these carefully composed letters the imagined adventures of the beloved doll. The little girl was comforted.\n" +
                    "\n" +
                    "When the meetings came to an end Kafka presented her with a doll. She obviously looked different from the original doll. An attached letter explained: “my travels have changed me… “");
            post2.setAccount(account2);



            postService.save(post1);
            postService.save(post2);
        }
    }

}
