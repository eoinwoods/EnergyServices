package com.artechra.datahog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by eoin on 21/10/2017.
 */
public interface DataItemRepository extends MongoRepository<DataItem, String> {

        public int countItems() ;
}
