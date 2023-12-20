package com.crio.starter.service;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.exchange.MemeResponse;
import com.crio.starter.repository.MemeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemeService {
    @Autowired
    private MemeRespository memeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    // Add data to database
    // POST
    public boolean addMemetoRepo(MemeEntity memedto) {
        Query query = new Query(); // Query object
        // comparing memedto values to the database values.
        // Adding values to the criteria object.
        query.addCriteria(Criteria.where("name").is(memedto.getName()));
        query.addCriteria(Criteria.where("url").is(memedto.getUrl()));
        query.addCriteria(Criteria.where("caption").is(memedto.getCaption()));
        // Duplicacy check
        boolean isMemeAlreadyExist = mongoTemplate.exists(query, "memes"); // mongo template.

        if (isMemeAlreadyExist == true) {
            return true;
        } else
            memeRepository.save(memedto);
        return false;
    }


    // GET memes
    public MemeResponse getMemes() {
        // Sorting and finding the collection
        // https://www.baeldung.com/spring-data-sorting
        List<MemeEntity> memedtolist = memeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        int size = memedtolist.size();

        if (memedtolist.isEmpty()) { // if meme is empty
            MemeResponse memeslist = new MemeResponse(memedtolist); // DTO ----> Exchange
                                                                    // class(Response)
            return memeslist;
        } else if (size < 100) { // if meme is < 100
            MemeResponse memeslist = new MemeResponse(memedtolist); // DTO ----> Exchange
                                                                    // class(Response)
            return memeslist;
        } else { // if meme is > 100
            List<MemeEntity> newlist = memedtolist.subList(0, 100); // 100 args index is exclusive.
            MemeResponse memeslist = new MemeResponse(newlist); // DTO ----> Exchange
                                                                // class(Response)
            return memeslist;
        }
    }

    // GET 1 meme
    public Optional<MemeEntity> getMeme(String memeid) {

        return memeRepository.findById(memeid);
    }
}
