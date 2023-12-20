package com.crio.starter.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.exchange.MemeResponse;
import com.crio.starter.exchange.PostMemeResponse;
import com.crio.starter.service.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemeController {
    @Autowired
    private MemeService memeService;


    // Verify that API doesnt accept empty data in POST call
    // Verify that posting duplicate MEME return 409
    @PostMapping("/memes/")
    // @Valid annotation to make all javax.validation.constraints. validation
    // annotation work
    // remember to add the dependency
    // implementation('org.springframework.boot:spring-boot-starter-validation')
    public ResponseEntity<PostMemeResponse> addMeme(@Valid @RequestBody MemeEntity meme) {
        boolean duplicate = memeService.addMemetoRepo(meme); // Dupliate check by service class
                                                             // method.
        PostMemeResponse response = new PostMemeResponse(meme.getId()); // set Id value by all
                                                                        // argument constructor.

        if (duplicate == false) {
            return new ResponseEntity<>(response, HttpStatus.CREATED); // If not duplicate
        } else
            return new ResponseEntity<>(HttpStatus.CONFLICT); // If Duplicate.
    }

    // more than 100 MEME, make a GET call and ensure that it returns only latest
    // 100 MEME
    // If there are no memes available, an empty array shall be returned.
    @GetMapping("/memes/")
    public List<MemeEntity> GetMemes() {
        MemeResponse memelistobj = memeService.getMemes();
        List<MemeEntity> memelist = memelistobj.getMemelist();
        return memelist;
    }


    
  // Workin fine
  @GetMapping("/memes/{id}")
  public ResponseEntity<MemeEntity> GetMeme(@PathVariable("id") String memeid) {

    Optional<MemeEntity> meme = memeService.getMeme(memeid);

    if (meme.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else
      return new ResponseEntity<>(meme.get(), HttpStatus.CREATED);
  }


}
